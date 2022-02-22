package com.hyphen.fbnk.bbnk.crypt;

import com.hyphen.fbnk.bbnk.SocketClient;
import com.hyphen.fbnk.bbnk.define.EncTp;
import com.hyphen.fbnk.bbnk.define.MsgCode;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

public class EncInfo {
    private static final Log log = LogFactory.getLog(EncInfo.class);

    private byte[] seedKey = null;

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
            throw new Exception("fail to set sessionkey");
    }

    private void setKECB(){
        log.debug("[setKECB]");

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


    public byte[] getSeedKey() {
        return seedKey;
    }
}
