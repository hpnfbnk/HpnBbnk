package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.define.Define;
import com.hyphen.fbnk.bbnk.define.MsgCode;
import com.hyphen.fbnk.bbnk.define.RtnCode;

import java.io.UnsupportedEncodingException;

public class MsgNego {
    private static final int msgSize = 96;

    private String  fType       = "000";                 //3
    private MsgCode msgType     = MsgCode.DEFAULT;                 //7
    private String  sendCd;                             //10
    private String  recvCd;                            //10
    private String  sendTm;                         //12
    private int     recCnt      = 0;                 //2
    private int     blockNo     = 0;                 //4
    private int     seqNo       = 0;                 //3
    private RtnCode rtnCd       = RtnCode.FINE;         //2
    private int     recLen      = 0;                 //3
    private MsgCode srType      = MsgCode.DEFAULT;                 //1
    private int     missCnt     = 0;                 //3
    private String  fromDate    = "";                 //6
    private String  toData      = "";                 //6
    private String  reqType     = "";                 //1
    private String  filler1     = "";                 //10
    private MsgCode ebcYn       = MsgCode.DEFAULT;                 //1
    private MsgCode encYn       = MsgCode.DEFAULT;                 //1
    private MsgCode zipYn       = MsgCode.DEFAULT;                 //1
    private int     binLen      = 0;                 //4
    private int     encRem      = 0;                 //1
    private MsgCode binYn       = MsgCode.MSG_TP_BIN;   //1
    private String  filler2     = "";                   //4

    public MsgNego(String sendCd, String recv_cd) {
        this.sendCd = sendCd;
        this.recvCd = recv_cd;
        this.sendTm = Util.getCurDtTm().substring(2);
    }

    public byte[] getMsgOpenReq() throws UnsupportedEncodingException {
        this.msgType    = MsgCode.MSG_OPEN_REQ;
        this.recvCd     = Define.HYPHEN.getCode();

        return getMsg();
    }

    private byte[] getMsg() throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%-3s", this.fType))
                .append(String.format("%-7s", this.msgType.getCode()))
                .append(String.format("%-10s", this.sendCd))
                .append(String.format("%-10s", this.recvCd))
                .append(this.sendTm)
                .append(String.format("%02d", this.recCnt))
                .append(String.format("%04d", this.blockNo))
                .append(String.format("%03d", this.seqNo))
                .append(this.rtnCd.getCode())
                .append(String.format("%03d", this.recLen))
                .append(String.format("%-1s", this.srType.getCode()))
                .append(String.format("%03d", this.missCnt))
                .append(String.format("%-6s", this.fromDate))
                .append(String.format("%-6s", this.toData))
                .append(String.format("%-1s", this.reqType))
                .append(String.format("%-10s", this.filler1))
                .append(String.format("%-1s", this.ebcYn.getCode()))
                .append(String.format("%-1s", this.encYn.getCode()))
                .append(String.format("%-1s", this.zipYn.getCode()))
                .append(String.format("%04d", this.binLen))
                .append(String.format("%01d", this.encRem))
                .append(String.format("%-1s", this.binYn.getCode()))
                .append(String.format("%-4s", this.filler2));

        return sb.toString().getBytes(MsgCode.MSG_ENCODE.getCode());
    }


}
