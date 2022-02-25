package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.crypt.EncInfo;
import com.hyphen.fbnk.bbnk.define.Define;
import com.hyphen.fbnk.bbnk.define.EncTp;
import com.hyphen.fbnk.bbnk.define.MsgCode;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import static java.lang.System.arraycopy;

public class SocketClient {
    private static final Log log = LogFactory.getLog(SocketClient.class);

    private Socket              sockfd = null;
    private DataOutputStream    dout = null;
    private DataInputStream     din = null;
    private String  ipAddr = null;
    private int     port = -1;
    private EncTp   encTp = null;

    private EncInfo encInfo = null;

    //4_RSASeed
    //private byte[] seedKey = null;

    //4_KEcb

    private SocketClient() {}
    public SocketClient(String ipAddr, int port, EncTp encTp) throws SocketException {
        this.ipAddr = ipAddr;
        this.port   = port;
        this.encTp  = encTp;
        this.sockfd = new Socket();
        this.sockfd.setSoTimeout(Define.READ_TIMEOUT.getValue());
    }

    public boolean connect() throws IOException {
        //System.out.println("SocketClient.connect()");
        this.sockfd.connect(new InetSocketAddress(ipAddr, port), Define.CONNECTION_TIMEOUT.getValue());
        this.dout    = new DataOutputStream(sockfd.getOutputStream());
        this.din     = new DataInputStream(sockfd.getInputStream());
        return true;
    }

    public boolean close(){
        log.debug("SocketClient.close()");
        if(this.din!=null) {try {this.din.close();} catch (Exception e) {e.printStackTrace();} finally {this.din=null;}}
        if(this.dout!=null) {try {this.dout.close();} catch (Exception e) {e.printStackTrace();} finally {this.dout=null;}}
        if(!this.sockfd.isClosed()) {try {this.sockfd.close();} catch (Exception e) {} finally {this.sockfd=null;}}
        return true;
    }

    public void write(byte[] msg) throws IOException {
        this.dout.write(msg);
        this.dout.flush();
    }

    public void writeMsgSs(byte[] msg) throws IOException {
        byte[] sBuf = new byte[4+1+msg.length];
        String hMsg = String.format("%04d%s", msg.length+1, EncTp.RSA_HD_S.getCode());

        arraycopy(hMsg.getBytes(), 0, sBuf, 0, hMsg.getBytes().length);
        arraycopy(msg, 0, sBuf, hMsg.getBytes().length, msg.length);
        write(sBuf);
    }

    public void writeMsg(byte[] msg) throws Exception {
        byte[] eBuf = null, sBuf = null, dBuf = null;
        String hMsg = null;

        if(encTp==EncTp.SEED){
            eBuf = encInfo.ks_seed_encrypt(msg);
            sBuf = new byte[4+1+eBuf.length];
            hMsg = String.format("%04d%s", eBuf.length+1, EncTp.RSA_HD_D.getCode());
            arraycopy(hMsg.getBytes(), 0, sBuf, 0, hMsg.getBytes().length);
            arraycopy(eBuf, 0, sBuf, hMsg.getBytes().length, eBuf.length);
        }else if(encTp==EncTp.KECB){

            dBuf = new byte[4+msg.length];
            hMsg = String.format("%04d", msg.length);
            arraycopy(hMsg.getBytes(), 0, dBuf, 0, hMsg.getBytes().length);
            arraycopy(msg, 0, dBuf, hMsg.getBytes().length, msg.length);

            byte[] rndBytes = encInfo.mem_rnd_to_msg();
            byte[] encCounter = encInfo.aes_128_ecb_encrypt(rndBytes, 0 , 16);



        }else{
            sBuf = new byte[4+msg.length];
            hMsg = String.format("%04d", msg.length);
            arraycopy(hMsg.getBytes(), 0, sBuf, 0, hMsg.getBytes().length);
            arraycopy(msg, 0, sBuf, hMsg.getBytes().length, msg.length);
        }
        //log.debug("[writeMsg] sBuf=["+new String(sBuf)+"]");
        write(sBuf);
    }

    public byte[] read(int len) throws IOException {
        byte[] msg = new byte[len];
        this.din.readFully(msg);
        return msg;
    }

    public byte[] readMsg() throws IOException {
        byte[] lenMsg = new byte[Define.MSG_LENGTH.getValue()];
        byte[] tBuf = null, eBuf = null, rBuf = null;
        int msgLen = 0;

        lenMsg = read(lenMsg.length);
        if(this.encTp==EncTp.SEED){
            msgLen = Integer.parseInt(new String(lenMsg, MsgCode.MSG_ENCODE.getCode()));
            tBuf = new byte[msgLen];
            tBuf = read(tBuf.length);

            if(new String(tBuf, MsgCode.MSG_ENCODE.getCode()).substring(0,1).equals(EncTp.RSA_HD_S.getCode())){
                rBuf = tBuf;
            }else{
                eBuf = new byte[msgLen-1];
                arraycopy(tBuf, 1, eBuf, 0, msgLen-1);
                rBuf = encInfo.ks_seed_decrypt(eBuf);
            }

        }else if(this.encTp==EncTp.KECB){

        }else{
            msgLen = Integer.parseInt(new String(lenMsg, MsgCode.MSG_ENCODE.getCode()));
            rBuf = read(msgLen);
        }

        return rBuf;
    }

    public void setEnc() throws Exception {
        this.encInfo = new EncInfo(this);
    }

    public EncTp getEncTp() {
        return encTp;
    }
}
