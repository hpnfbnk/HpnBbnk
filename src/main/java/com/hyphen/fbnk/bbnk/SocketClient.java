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
    //4_RSASeed
    private byte[] seedKey = null;

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
        //System.out.println("SocketClient.close()");
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
        byte[] sbuf = new byte[4+1+msg.length];
        String hMsg = String.format("%04d%s", msg.length+1, MsgCode.MSG_TP_S.getCode());

        arraycopy(hMsg.getBytes(), 0, sbuf, 0, hMsg.getBytes().length);
        arraycopy(msg, 0, sbuf, hMsg.getBytes().length, msg.length);
        write(sbuf);
    }

    public void writeMsg(byte[] msg){

        if(encTp==EncTp.SEED){



        }else if(encTp==EncTp.KECB){



        }

    }

    public byte[] read(int len) throws IOException {
        byte[] msg = new byte[len];
        this.din.readFully(msg);
        return msg;
    }

    public byte[] readMsg() throws IOException {
        byte[] lenMsg = new byte[Define.MSG_LENGTH.getValue()];
        byte[] msg = null;
        int msgLen = 0;

        lenMsg = read(lenMsg.length);


        if(this.encTp==EncTp.SEED){
            msgLen = Integer.parseInt(new String(lenMsg, MsgCode.MSG_ENCODE.getCode()));
            msg = new byte[msgLen];
            msg = read(msg.length);

            if(new String(msg, MsgCode.MSG_ENCODE.getCode()).equals(MsgCode.MSG_TP_S.getCode())){

            }else{

            }

        }else if(this.encTp==EncTp.KECB){

        }


        return msg;
    }


    public void setEnc() throws Exception {
        EncInfo encInfo = new EncInfo(this);
        this.seedKey = encInfo.getSeedKey();
        log.debug("seedKey="+new String(encInfo.getSeedKey()));

    }

    public EncTp getEncTp() {
        return encTp;
    }
}
