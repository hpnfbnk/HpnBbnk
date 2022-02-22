package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.crypt.EncInfo;
import com.hyphen.fbnk.bbnk.define.EncTp;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.util.concurrent.TimeUnit;

public class UpdnLib {
    private static final Log log = LogFactory.getLog(UpdnLib.class);

    public boolean sndData(String ipAddr, int port, String sendCd, String recvCd, String infoCd, String filePath, boolean zipYn, EncTp encTp, int netBps){
        boolean result = false;
        SocketClient sockClnt = null;
        byte[] sndMsg=null, rcvMsg=null;

        log.debug("[sndData] ipAddr="+ipAddr+", port="+port+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", filePath="+filePath+", zipYn="+zipYn+", encTp="+encTp+", netKBps="+netBps);

        try {
            sockClnt = new SocketClient(ipAddr, port, encTp);
            sockClnt.connect();
            sockClnt.setEnc();

            //개시전문전송
            sndMsg = new MsgNego(sendCd, recvCd).getMsgOpenReq();
            log.trace("[sndData] sndMsg=["+new String(sndMsg)+"]");

            //TimeUnit.SECONDS.sleep(2);

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        } finally {
            if(sockClnt!=null)  sockClnt.close();
        }

        return result;
    }




}
