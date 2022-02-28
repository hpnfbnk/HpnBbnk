package com.hyphen.fbnk.bbnk.crypt;

import com.hyphen.fbnk.bbnk.SocketClient;
import com.hyphen.fbnk.bbnk.define.Define;
import com.hyphen.fbnk.bbnk.define.EncTp;
import com.hyphen.fbnk.bbnk.define.MsgCode;
import com.hyphen.fbnk.bbnk.define.RtnCode;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Random;

import static java.lang.System.arraycopy;

public class EncInfo {
    private static final Log log = LogFactory.getLog(EncInfo.class);

    private byte[] seedKey = null;
    private byte[] kEcbTrno = null, kEcbKey = null, kEcbIv = null, kEcbCtrBk = null;
    private int kEcbRndIdx = 0;

    private final SocketClient socketClnt;
    private final EncTp encTp;

    public EncInfo(SocketClient sockClnt) throws Exception {
        this.socketClnt = sockClnt;
        this.encTp = sockClnt.getEncTp();

        if(encTp==EncTp.SEED)       setRSASeed();
        else if(encTp==EncTp.KECB)  setKECB();
    }

    private void setRSASeed() throws Exception {
        this.seedKey = generateKey();
        byte[] sessionKey = ks_rsa_encrypt(this.seedKey);

        socketClnt.writeMsgSs(sessionKey);
        byte[] rcvMsg = socketClnt.readMsg();

        if(!new String(rcvMsg, MsgCode.MSG_ENCODE.getCode()).substring(1).equals(MsgCode.MSG_OK.getCode()))
            throw new Exception("[setRSASeed] fail to set sessionkey");
    }

    private void setKECB() throws Exception {
        Well512.getInstance().initWELL();
        //Set kEcbKey
        this.kEcbKey = make_session_key();
        //log.debug("[setKECB] kEcbKey=["+new String(this.kEcbKey, MsgCode.MSG_ENCODE.getCode())+"]");
        byte[] kbuf = encrypt_rsa_2048(this.kEcbKey);

        byte[] sbuf = new byte[4+1+2+2+1+256+1];
        int tlen = sbuf.length-4;
        arraycopy("0000$C1000".getBytes(), 0, sbuf, 0, 10);
        arraycopy(kbuf, 0, sbuf, 10, kbuf.length);
        sbuf[0] = Define.C_STX.getByteValue();
        sbuf[1]	= (byte) ((tlen >>> 8) & 0xff);
        sbuf[2]	= (byte) (tlen & 0xff);
        sbuf[3] = calculate_lrc(sbuf, 3);
        sbuf[sbuf.length-1] = Define.C_ETX.getByteValue();
        socketClnt.write(sbuf);

        byte[] rbuf = socketClnt.read(4+1+2+2+16+16+1);
        //log.debug("[setKECB] rbuf=["+new String(rbuf, MsgCode.MSG_ENCODE.getCode())+"]("+rbuf.length+")");

        byte[] respCode = new byte[2];
        arraycopy(rbuf, 7, respCode, 0, 2);
        if(RtnCode.fromCode(new String(respCode, MsgCode.MSG_ENCODE.getCode())) != RtnCode.FINE)
            throw new Exception("[setKECB] fail to set sessionkey");

        //Set kEcbTrno
        this.kEcbTrno = new byte[16];
        arraycopy(rbuf, 7+2, kEcbTrno, 0, 16);
        //log.debug("[setKECB] kEcbTrno=["+new String(this.kEcbTrno, MsgCode.MSG_ENCODE.getCode())+"]");

        //Set kEcbIv
        byte[] encIv = new byte[16];
        arraycopy(rbuf, 7+2+16, encIv, 0, 16);
        this.kEcbIv = aes_128_ecb_decrypt(encIv, 0, 16);
        //log.debug("[setKECB] kEcbIv=["+new String(this.kEcbIv, MsgCode.MSG_ENCODE.getCode())+"]");

        //Set kEcbCtrBk
        int t1 = 0, t2 = 0;
        byte[] nonce = new byte[16];
        for(int i=0; i<7; i++){
            t1 = i*2;
            t2 = t1+1;
            nonce[i] = (byte)(this.kEcbIv[t1] ^ this.kEcbIv[t2]);
        }
        byte uc1 = 0;
        byte uc2 = 0;
        if (this.kEcbIv[14] != (uc1 = calculate_lrc(this.kEcbIv, 14)) || this.kEcbIv[15] != (uc2 = (byte) (this.kEcbKey[13] ^ this.kEcbIv[13])))
            throw new IllegalStateException("ERROR invalid IV");
        nonce[7] = (byte) (this.kEcbIv[14] ^ this.kEcbIv[15]);
        byte[] pCtrBlocks = new byte[1024];
        for (int i = 0; i < pCtrBlocks.length; i += 16) {
            arraycopy(nonce, 0, pCtrBlocks, i, 8);
            pCtrBlocks[i + 15] = (byte) (i & 0xff);
        }
        this.kEcbCtrBk = aes_128_cbc_encrypt(this.kEcbKey, this.kEcbIv, pCtrBlocks, 0, pCtrBlocks.length);
    }

    public int getInnerMsgLen(byte[] msg) throws Exception {
        if(msg[0] != Define.C_STX.getByteValue())       throw new Exception("Abnormal C_STX");
        else if(msg[3] != calculate_lrc(msg, 3))    throw new Exception("Abnormal calculate_lrc");

        return (msg[2] | (msg[1] << 8)) ;
    }

    public byte[] speed_ctr_encrypt(byte[] sbuf, int slen){
        return speed_ctr_decrypt(sbuf, slen);
    }

    public byte[] speed_ctr_decrypt(byte[] sbuf, int slen){
        int bidx = 0, eidx = 0;
        int rno  = 0, ridx = 0;

        byte[] tbuf = new byte[slen];
        byte[] ctr_blocks = this.kEcbCtrBk;
        int tlen = 0;
        for(int i=0; i<slen && tlen < slen; i+= 80){
            int[] rinfos = Well512.getInstance().getWELL512();
            rno 	= rinfos[0];
            ridx	= rinfos[1];
            for(int j=0; j<5; j++){
                eidx	= rno & 0x3f;
                rno		= rno >>> 6;
                bidx	= eidx * 16;
                for(int k=0; (k < 16 && tlen < slen); k++, tlen++){
                    tbuf[tlen] = (byte) (ctr_blocks[bidx+k] ^ sbuf[tlen]);
                }
            }
        }
        this.kEcbRndIdx = ridx;

        return tbuf;
    }

    public byte[] mem_rnd_to_msg(){
        byte[]	tmp_bytes	= new byte[16];
        Random	random		= new Random();
        String rnd_msg	= String.valueOf(1000000000+kEcbRndIdx);
        System.arraycopy(rnd_msg.getBytes(), 0, tmp_bytes, 5, 10);
        tmp_bytes[5]	= '0';
        tmp_bytes[0]	= (byte) 0xff	;						// 0 - FF
        tmp_bytes[1]	= (byte) (random.nextInt(255) & 0xff);	// 1 - RN1
        tmp_bytes[2]	= (byte) (random.nextInt(255) & 0xff);	// 2 - RN2
        tmp_bytes[3]	= (byte) (random.nextInt(255) & 0xff);	// 3 - RN3
        tmp_bytes[4]	= (byte) (random.nextInt(255) & 0xff);	// 4 - RN4

        byte 	crc				= calculate_lrc(tmp_bytes, 15);
        byte[]	rnd_bytes		= new byte[16];

        arraycopy(tmp_bytes, 0, rnd_bytes, 0, 5	);
        rnd_bytes[5] = crc;
        arraycopy(tmp_bytes, 5, rnd_bytes, 6, 10	);

        return rnd_bytes;
    }

    public boolean msg_to_mem_rnd(byte[] rnd_info){
        int 	xcnt		= 0;
        byte[]	tmp_bytes	= new byte[16];
        byte crc1 = 0, crc2 = 0;

        if(((byte) 0xff ^ rnd_info[0]) != 0 || rnd_info[6] != '0') return false;

        crc1	= rnd_info[5];
        System.arraycopy(rnd_info, 0, tmp_bytes, 0, 5 );
        System.arraycopy(rnd_info, 6, tmp_bytes, 5, 10);

        crc2	= calculate_lrc(tmp_bytes, 15);
        if((crc1 ^ crc2) != 0) return false;
        int rnd_sidx = Integer.parseInt(new String(tmp_bytes, 5, 10));
        int cidx = Well512.getInstance().countWELL512();
        if(cidx != this.kEcbRndIdx || (xcnt = rnd_sidx-this.kEcbRndIdx) < 0) return false;
        int[]	wnos	= null;
        for(int i =0; i<xcnt ;i++)
            wnos	= Well512.getInstance().getWELL512();

        return true;
    }

    public byte[] aes_128_cbc_encrypt(byte[] k16, byte[] iv, byte[] pbuf, int idx, int len) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(k16, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");	//"AES"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv));

        return cipher.doFinal(pbuf, idx, len);
    }


    //public byte[] aes_128_ecb_encrypt(byte[] k16, byte[] pbuf, int idx, int len) throws Exception {
    public byte[] aes_128_ecb_encrypt(byte[] pbuf, int idx, int len) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(this.kEcbKey, "AES");	// AES/ECB/NoPadding
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");	//AES
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

        return cipher.doFinal(pbuf, idx, len);
    }

    //public byte[] aes_128_ecb_decrypt(byte[] k16, byte[] pbuf, int idx, int len) throws Exception {
    public byte[] aes_128_ecb_decrypt(byte[] pbuf, int idx, int len) throws Exception {
        SecretKeySpec skeySpec	= new SecretKeySpec(this.kEcbKey, "AES");		//AES/ECB/NoPadding
        Cipher cipher	= Cipher.getInstance("AES/ECB/NoPadding");		//AES
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);

        return cipher.doFinal(pbuf, idx, len);
    }

    public byte calculate_lrc(byte[]src, int slen){
        byte c = 0x00;
        for(int i=0; i<slen; i++) c ^= src[i];

        return c;
    }

    public byte[] encrypt_rsa_2048(byte[] aes_key) throws Exception {
        BigInteger modulus 			= new BigInteger("00ef5cd77cc2e16c7c86b216143ce973c05a1ab5717851250ac56cb1ca6bc450118b0e37939049c459bdb8a109b13101952025efb32646271b2616b7fe956ccd8792e60f57155d1ac9d36fa961f7b36776881334506039cca83e34a0e7a684639c6236d09c810cbedb950cdc9295ead4203381861c0eff68d12d193444991df1644f5f7ac4c5a20d3ef418448f238f255627633b4d3dfe0287ada528cf00c46ba452f93cbec551d8c388b32a222b36700c030aefedbb64e49073abe6bf23df66ddbb7a0aab63bcabf5c80b234113016098b5a008a141efa90fdebbddf5032019af3b943e436fa1e0a033d5bd5618c5d08e1f5b7968d55182d21cea8441ac3a75f1",16);
        BigInteger publicExponent 	= new BigInteger("010001", 16);

        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus, publicExponent);
        KeyFactory keyfactory   = KeyFactory.getInstance("RSA");
        PublicKey publickey 	= keyfactory.generatePublic(pubKeySpec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publickey);

        return cipher.doFinal(aes_key);
    }

    public byte[] make_session_key(){
        byte[] 	k16		= new byte[16];
        Random random	= new Random();

        random.nextBytes(k16);
        k16[15]	= 0;
        for(int i=0; i < 15; i++)   k16[15]	= (byte) (k16[15] ^ k16[i]);

        return k16;
    }

    private byte[] generateKey() throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        return sb.append(System.currentTimeMillis()).append(Long.MAX_VALUE).substring(0, 16).getBytes("ksc5601");
    }

    //public static byte[] ks_rsa_encrypt(byte[] sbuf) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    public byte[] ks_rsa_encrypt(byte[] sbuf) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        BigInteger modulus			= new BigInteger("d4846c2b8228dddfab9e614da2a324c1cc7b29d848cc005624d3a09667a2aab9073290bace6aa536ddceb3c47ddda78d9954da06c83aa65b939c5ec773a3787e71bec5a1c077bb446c06b393d2537967645d386b4b0b4ec21372fdc728c56693028c1c3915c1c4279793eb3dccefd6bf49b86cc7d88a47b0d44aba9e73750fcd",16);
        BigInteger publicExponent	= new BigInteger("0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010001",16);
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus, publicExponent);
        KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        PublicKey publickey = keyfactory.generatePublic(pubKeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publickey);

        return cipher.doFinal(sbuf);
    }

    public byte[] ks_seed_encrypt(byte[] mbuf) {
        byte tdata[] = new KSBankSeed(this.seedKey).cbc_encrypt(mbuf);
        return tdata;
    }

    public byte[] ks_seed_decrypt(byte[] mbuf) {
        byte tdata[] = new KSBankSeed(this.seedKey).cbc_decrypt(mbuf);
        return tdata;
    }


}
