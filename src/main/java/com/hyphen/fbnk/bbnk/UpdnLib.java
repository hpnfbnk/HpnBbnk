package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.define.EncTp;
import com.hyphen.fbnk.bbnk.define.MsgCode;
import com.hyphen.fbnk.bbnk.define.RtnCode;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;
import com.hyphen.fbnk.bbnk.msg.MsgNego;

public class UpdnLib {
    private static final Log log = LogFactory.getLog(UpdnLib.class);

    public boolean sndData(String ipAddr, int port, String sendCd, String recvCd, String infoCd, String filePath, boolean zipYn, EncTp encTp, int netBps){
        boolean result = false;
        SocketClient sockClnt = null;
        byte[] sndMsg = null, rcvMsg = null;

        log.debug("[sndData] ipAddr="+ipAddr+", port="+port+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", filePath="+filePath+", zipYn="+zipYn+", encTp="+encTp+", netKBps="+netBps);

        try {
            sockClnt = new SocketClient(ipAddr, port, encTp);
            sockClnt.connect();
            if(encTp != EncTp.NONE) sockClnt.setEnc();

            //개시전문전송
            sndMsg = new MsgNego(sendCd, recvCd).getMsgOpenReq();
            log.trace("[sndData] sndMsg=["+new String(sndMsg)+"]");
            sockClnt.writeMsg(sndMsg);
            //응답전문점검
            chkRplyMsg(sockClnt, MsgCode.MSG_OPEN_REP);




            //TimeUnit.SECONDS.sleep(2);
            log.debug("[sndData] ... continue next job ...");

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        } finally {
            log.debug("[sndData] finally");
            if(sockClnt!=null)  sockClnt.close();
        }

        return result;
    }

    public void chkRplyMsg(SocketClient sockClnt, MsgCode msgType) throws Exception {
        byte[] rcvMsg = sockClnt.readMsg();
        log.trace("[chkRplyMsg] rcvMsg=["+new String(rcvMsg)+"]");

        MsgNego rplyNegoMsg = new MsgNego(rcvMsg);
        //log.debug("[chkRplyMsg] rplyNegoMsg="+rplyNegoMsg);
        if(rplyNegoMsg.getRtnCd() != RtnCode.FINE)
            throw new Exception("Abnormal RtnCode:["+rplyNegoMsg.getRtnCd().getCode()+"]");
        else if(rplyNegoMsg.getMsgType() != msgType)
            throw new Exception("[chkRplyMsg] Abnormal MsgType:["+rplyNegoMsg.getMsgType().getCode()+"]");
    }



}
