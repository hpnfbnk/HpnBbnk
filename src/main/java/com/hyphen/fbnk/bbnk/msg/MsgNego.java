package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.define.Define;
import com.hyphen.fbnk.bbnk.define.MsgCode;
import com.hyphen.fbnk.bbnk.define.RtnCode;

import java.io.UnsupportedEncodingException;

public class MsgNego {
    private static final int msgSize = 96;

    private String  fType       = "000";                //3
    private MsgCode msgType     = MsgCode.DEFAULT;      //7
    private String  sendCd;                             //10
    private String  recvCd;                             //10
    private String  sendTm;                             //12
    private int     recCnt      = 0;                    //2
    private int     blockNo     = 0;                    //4
    private int     seqNo       = 0;                    //3
    private RtnCode rtnCd       = RtnCode.FINE;         //2
    private int     recLen      = 0;                    //3
    private MsgCode srType      = MsgCode.DEFAULT;      //1
    private int     missCnt     = 0;                    //3
    private String  fromDate    = "";                   //6
    private String  toDate      = "";                   //6
    private MsgCode reqType     = MsgCode.DEFAULT;      //1
    private String  filler1     = "";                   //10
    private MsgCode ebcYn       = MsgCode.DEFAULT;      //1
    private MsgCode encYn       = MsgCode.DEFAULT;      //1
    private MsgCode zipYn       = MsgCode.DEFAULT;      //1
    private int     binLen      = 0;                    //4
    private int     encRem      = 0;                    //1
    private MsgCode binYn       = MsgCode.MSG_TP_BIN;   //1
    private String  filler2     = "";                   //4

    //4 make msg
    public MsgNego(String sendCd, String recv_cd) {
        this.sendCd = sendCd;
        this.recvCd = recv_cd;
        this.sendTm = Util.getCurDtTm().substring(2);
    }

    //4 split msg
    public MsgNego(byte[] msg){
        this.fType      = new String(msg, 0, 3);
        this.msgType    = MsgCode.fromCode(new String(msg, 0+3, 7));
        this.sendCd     = new String(msg, 0+3+7, 10);
        this.recvCd     = new String(msg, 0+3+7+10, 10);
        this.sendTm     = new String(msg, 0+3+7+10+10, 12);
        this.recCnt     = Integer.parseInt(new String(msg, 0+3+7+10+10+12, 2));
        this.blockNo    = Integer.parseInt(new String(msg, 0+3+7+10+10+12+2, 4));
        this.seqNo      = Integer.parseInt(new String(msg, 0+3+7+10+10+12+2+4, 3));
        this.rtnCd      = RtnCode.fromCode(new String(msg, 0+3+7+10+10+12+2+4+3, 2));
        this.recLen     = Integer.parseInt(new String(msg, 0+3+7+10+10+12+2+4+3+2, 3));
        this.srType     = MsgCode.fromCode(new String(msg, 0+3+7+10+10+12+2+4+3+2+3, 1));
        this.missCnt    = Integer.parseInt(new String(msg, 0+3+7+10+10+12+2+4+3+2+3+1, 3));
        this.fromDate   = new String(msg, 0+3+7+10+10+12+2+4+3+2+3+1+3, 6);
        this.toDate     = new String(msg, 0+3+7+10+10+12+2+4+3+2+3+1+3+6, 6);
        this.reqType    = MsgCode.fromCode(new String(msg, 0+3+7+10+10+12+2+4+3+2+3+1+3+6+6, 1));
        this.filler1    = new String(msg, 0+3+7+10+10+12+2+4+3+2+3+1+3+6+6+1, 10);
        this.ebcYn      = MsgCode.fromCode(new String(msg, 0+3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10, 1));
        this.encYn      = MsgCode.fromCode(new String(msg, 0+3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10+1, 1));
        this.zipYn      = MsgCode.fromCode(new String(msg, 0+3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10+1+1, 1));
        this.binLen     = Integer.parseInt(new String(msg, 0+3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10+1+1+1, 4));
        this.encRem     = Integer.parseInt(new String(msg, 0+3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10+1+1+1+4, 1));
        this.binYn      = MsgCode.fromCode(new String(msg, 0+3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10+1+1+1+4+1, 1));
        this.filler2    = new String(msg, 0+3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10+1+1+1+4+1+1, 4);
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
                .append(String.format("%-6s", this.toDate))
                .append(String.format("%-1s", this.reqType.getCode()))
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

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public MsgCode getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgCode msgType) {
        this.msgType = msgType;
    }

    public String getSendCd() {
        return sendCd;
    }

    public void setSendCd(String sendCd) {
        this.sendCd = sendCd;
    }

    public String getRecvCd() {
        return recvCd;
    }

    public void setRecvCd(String recvCd) {
        this.recvCd = recvCd;
    }

    public String getSendTm() {
        return sendTm;
    }

    public void setSendTm(String sendTm) {
        this.sendTm = sendTm;
    }

    public int getRecCnt() {
        return recCnt;
    }

    public void setRecCnt(int recCnt) {
        this.recCnt = recCnt;
    }

    public int getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(int blockNo) {
        this.blockNo = blockNo;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public RtnCode getRtnCd() {
        return rtnCd;
    }

    public void setRtnCd(RtnCode rtnCd) {
        this.rtnCd = rtnCd;
    }

    public int getRecLen() {
        return recLen;
    }

    public void setRecLen(int recLen) {
        this.recLen = recLen;
    }

    public MsgCode getSrType() {
        return srType;
    }

    public void setSrType(MsgCode srType) {
        this.srType = srType;
    }

    public int getMissCnt() {
        return missCnt;
    }

    public void setMissCnt(int missCnt) {
        this.missCnt = missCnt;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toData) {
        this.toDate = toData;
    }

    public MsgCode getReqType() {
        return reqType;
    }

    public void setReqType(MsgCode reqType) {
        this.reqType = reqType;
    }

    public String getFiller1() {
        return filler1;
    }

    public void setFiller1(String filler1) {
        this.filler1 = filler1;
    }

    public MsgCode getEbcYn() {
        return ebcYn;
    }

    public void setEbcYn(MsgCode ebcYn) {
        this.ebcYn = ebcYn;
    }

    public MsgCode getEncYn() {
        return encYn;
    }

    public void setEncYn(MsgCode encYn) {
        this.encYn = encYn;
    }

    public MsgCode getZipYn() {
        return zipYn;
    }

    public void setZipYn(MsgCode zipYn) {
        this.zipYn = zipYn;
    }

    public int getBinLen() {
        return binLen;
    }

    public void setBinLen(int binLen) {
        this.binLen = binLen;
    }

    public int getEncRem() {
        return encRem;
    }

    public void setEncRem(int encRem) {
        this.encRem = encRem;
    }

    public MsgCode getBinYn() {
        return binYn;
    }

    public void setBinYn(MsgCode binYn) {
        this.binYn = binYn;
    }

    public String getFiller2() {
        return filler2;
    }

    public void setFiller2(String filler2) {
        this.filler2 = filler2;
    }

    @Override
    public String toString() {
        return "MsgNego{" +
                "fType='" + fType + '\'' +
                ", msgType=" + msgType +
                ", sendCd='" + sendCd + '\'' +
                ", recvCd='" + recvCd + '\'' +
                ", sendTm='" + sendTm + '\'' +
                ", recCnt=" + recCnt +
                ", blockNo=" + blockNo +
                ", seqNo=" + seqNo +
                ", rtnCd=" + rtnCd +
                ", recLen=" + recLen +
                ", srType=" + srType +
                ", missCnt=" + missCnt +
                ", fromDate='" + fromDate + '\'' +
                ", toData='" + toDate + '\'' +
                ", reqType='" + reqType + '\'' +
                ", filler1='" + filler1 + '\'' +
                ", ebcYn=" + ebcYn +
                ", encYn=" + encYn +
                ", zipYn=" + zipYn +
                ", binLen=" + binLen +
                ", encRem=" + encRem +
                ", binYn=" + binYn +
                ", filler2='" + filler2 + '\'' +
                '}';
    }
}
