package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.define.Define;
import com.hyphen.fbnk.bbnk.define.FNmTp;
import com.hyphen.fbnk.bbnk.define.MsgCode;
import com.hyphen.fbnk.bbnk.define.RtnCode;

import java.io.UnsupportedEncodingException;

import static java.lang.System.arraycopy;

public class MsgNego {
    public static final int msgSize = 96;

    private String fType    = "000";                        //3
    private String msgType  = "";                           //7
    private String sendCd;                                  //10
    private String recvCd;                                  //10
    private String sendTm;                                  //12
    private String recCnt   = "00";                         //2
    private String blockNo  = "0000";                       //4
    private String seqNo    = "000";                        //3
    private String rtnCd    = RtnCode.FINE.getCode();       //2
    private String recLen   = "000";                        //3
    private String srType   = "";                           //1
    private String missCnt  = "000";                        //3
    private String fromDate = "";                           //6
    private String toDate   = "";                           //6
    private String reqType  ="";                            //1
    private String filler1  = "";                           //10
    private String ebcYn    ="";                            //1
    private String encYn    ="";                            //1
    private String zipYn    ="";                            //1
    private String binLen   = "0000";                       //4
    private String encRem   = "0";                          //1
    private String binYn    = MsgCode.MSG_TP_BIN.getCode(); //1
    private String filler2  = "";                           //4

    //4 make msg
    public MsgNego(String sendCd, String recv_cd) {
        this.sendCd = sendCd;
        this.recvCd = recv_cd;
        this.sendTm = Util.getCurDtTm().substring(2);
    }

    //4 split msg
    public MsgNego(byte[] msg){
        this.fType      = new String(msg, 0, 3);
        this.msgType    = new String(msg, 3, 7);
        this.sendCd     = new String(msg, 3+7, 10);
        this.recvCd     = new String(msg, 3+7+10, 10);
        this.sendTm     = new String(msg, 3+7+10+10, 12);
        this.recCnt     = new String(msg, 3+7+10+10+12, 2);
        this.blockNo    = new String(msg, 3+7+10+10+12+2, 4);
        this.seqNo      = new String(msg, 3+7+10+10+12+2+4, 3);
        this.rtnCd      = new String(msg, 3+7+10+10+12+2+4+3, 2);
        this.recLen     = new String(msg, 3+7+10+10+12+2+4+3+2, 3);
        this.srType     = new String(msg, 3+7+10+10+12+2+4+3+2+3, 1);
        this.missCnt    = new String(msg, 3+7+10+10+12+2+4+3+2+3+1, 3);
        this.fromDate   = new String(msg, 3+7+10+10+12+2+4+3+2+3+1+3, 6);
        this.toDate     = new String(msg, 3+7+10+10+12+2+4+3+2+3+1+3+6, 6);
        this.reqType    = new String(msg, 3+7+10+10+12+2+4+3+2+3+1+3+6+6, 1);
        this.filler1    = new String(msg, 3+7+10+10+12+2+4+3+2+3+1+3+6+6+1, 10);
        this.ebcYn      = new String(msg, 3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10, 1);
        this.encYn      = new String(msg, 3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10+1, 1);
        this.zipYn      = new String(msg, 3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10+1+1, 1);
        this.binLen     = new String(msg, 3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10+1+1+1, 4);
        this.encRem     = new String(msg, 3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10+1+1+1+4, 1);
        this.binYn      = new String(msg, 3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10+1+1+1+4+1, 1);
        this.filler2    = new String(msg, 3+7+10+10+12+2+4+3+2+3+1+3+6+6+1+10+1+1+1+4+1+1, 4);
    }

    public byte[] getMsgOpenReq() throws UnsupportedEncodingException {
        this.msgType    = MsgCode.MSG_OPEN_REQ.getCode();
        this.recvCd     = Define.HYPHEN.getCode();

        return getMsg();
    }

    public byte[] getMsgSendReq(String fType, long fSize, boolean zipYn) throws UnsupportedEncodingException {
        this.fType      = fType;
        this.msgType    = MsgCode.MSG_TRANS_REQ.getCode();
        this.srType     = MsgCode.MSG_TP_SND_REQ.getCode();
        this.filler1    = String.format("%010d", fSize);
        if(zipYn)   this.zipYn = MsgCode.MSG_TP_ZIP.getCode();

        return getMsg();
    }

    public byte[] getMsgSendReq(String fType, long fSize, boolean zipYn, String passwd) throws UnsupportedEncodingException {
        this.fType      = fType;
        this.msgType    = MsgCode.MSG_TRANS_REQ.getCode();
        this.srType     = MsgCode.MSG_TP_SND_REQ.getCode();
        this.filler1    = String.format("%010d", fSize);
        if(zipYn)   this.zipYn = MsgCode.MSG_TP_ZIP.getCode();

        byte[] bPassWd = passwd.getBytes(MsgCode.MSG_ENCODE.getCode());
        byte[] sndBuf = new byte[MsgNego.msgSize + bPassWd.length];
        arraycopy(getMsg(), 0, sndBuf, 0, MsgNego.msgSize);
        arraycopy(bPassWd, 0, sndBuf, MsgNego.msgSize, bPassWd.length);

        return sndBuf;
    }

    public byte[] getMsgFindFnmReq(String fType, MsgCode srType, String fnmInfo, FNmTp fNmTp) throws UnsupportedEncodingException {
        this.fType      = fType;
        this.msgType    = MsgCode.MSG_FINDFNM_REQ.getCode();
        this.srType     = srType.getCode();
        this.fromDate   = fNmTp.getCode();

        return getMsgUseFiller(fnmInfo);
    }

    public byte[] getMsgRecvReq(String fType, String fromDt, String toDt, MsgCode tpReq, MsgCode fRng, String seqNo, boolean zipYn) throws UnsupportedEncodingException {
        this.fType      = fType;
        this.msgType    = MsgCode.MSG_TRANS_REQ.getCode();
        this.srType     = tpReq.getCode();
        this.fromDate   = fromDt;
        this.toDate     = toDt;
        this.reqType    = fRng.getCode();
        this.filler1    = seqNo;
        if(zipYn)   this.zipYn = MsgCode.MSG_TP_ZIP.getCode();

        return getMsg();
    }

    public byte[] getMsgRecvReq(String fType, String fromDt, String toDt, MsgCode tpReq, MsgCode fRng, String seqNo, boolean zipYn, String passwd) throws UnsupportedEncodingException {
        this.fType      = fType;
        this.msgType    = MsgCode.MSG_TRANS_REQ.getCode();
        this.srType     = tpReq.getCode();
        this.fromDate   = fromDt;
        this.toDate     = toDt;
        this.reqType    = fRng.getCode();
        this.filler1    = seqNo;
        if(zipYn)   this.zipYn = MsgCode.MSG_TP_ZIP.getCode();

        byte[] bPassWd = passwd.getBytes(MsgCode.MSG_ENCODE.getCode());
        byte[] sndBuf = new byte[MsgNego.msgSize + bPassWd.length];
        arraycopy(getMsg(), 0, sndBuf, 0, MsgNego.msgSize);
        arraycopy(bPassWd, 0, sndBuf, MsgNego.msgSize, bPassWd.length);

        return sndBuf;
    }

    public byte[] getMsgSendData(String fType, MsgCode msgType, int blockNo, int seqNo, boolean zipYn, int binLen, byte[] dataBuf) throws UnsupportedEncodingException {
        this.fType      = fType;
        this.msgType    = msgType.getCode();
        setBlockNo(blockNo);
        setSeqNo(seqNo);
        if(zipYn)   this.zipYn = MsgCode.MSG_TP_ZIP.getCode();
        setBinLen(binLen);

        byte[] sndBuf = new byte[MsgNego.msgSize + binLen];
        arraycopy(getMsg(), 0, sndBuf, 0, MsgNego.msgSize);
        arraycopy(dataBuf, 0, sndBuf, MsgNego.msgSize, binLen);

        return sndBuf;
    }

    public byte[] getMsgMissReq(String fType, int blockNo, int seqNo) throws UnsupportedEncodingException {
        this.fType      = fType;
        this.msgType    = MsgCode.MSG_MISS_REQ.getCode();
        setBlockNo(blockNo);
        setSeqNo(seqNo);

        return getMsg();
    }

    public byte[] getMsgPartEndReq(String fType, long fSize) throws UnsupportedEncodingException {
        this.fType = fType;
        this.msgType    = MsgCode.MSG_PARTEND_REQ.getCode();
        this.filler1 = String.format("%010d", fSize);

        return getMsg();
    }

    public byte[] getMsgCloseReq() throws UnsupportedEncodingException {
        this.msgType    = MsgCode.MSG_CLOSE_REQ.getCode();
        this.recvCd     = Define.HYPHEN.getCode();

        return getMsg();
    }

    public byte[] getMsgMissRep(byte[] missList) throws UnsupportedEncodingException {
        byte[] sndBuf = new byte[MsgNego.msgSize+missList.length];
        arraycopy(getMsg(), 0, sndBuf, 0, MsgNego.msgSize);
        arraycopy(missList, 0, sndBuf, MsgNego.msgSize, missList.length);

        return sndBuf;
    }

    public byte[] getMsg() throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%-3s", this.fType))
                .append(String.format("%-7s", this.msgType))
                .append(String.format("%-10s", this.sendCd))
                .append(String.format("%-10s", this.recvCd))
                .append(this.sendTm)
                .append(String.format("%02d", getRecCnt()))
                .append(String.format("%04d", getBlockNo()))
                .append(String.format("%03d", getSeqNo()))
                .append(String.format("%-2s", this.rtnCd))
                .append(String.format("%03d", getRecLen()))
                .append(String.format("%-1s", this.srType))
                .append(String.format("%03d", getMissCnt()))
                .append(String.format("%-6s", this.fromDate))
                .append(String.format("%-6s", this.toDate))
                .append(String.format("%-1s", this.reqType))
                .append(String.format("%-10s", this.filler1))
                .append(String.format("%-1s", this.ebcYn))
                .append(String.format("%-1s", this.encYn))
                .append(String.format("%-1s", this.zipYn))
                .append(String.format("%04d", getBinLen()))
                .append(String.format("%01d", getEncRem()))
                .append(String.format("%-1s", this.binYn))
                .append(String.format("%-4s", this.filler2));

        return sb.toString().getBytes(MsgCode.MSG_ENCODE.getCode());
    }

    public byte[] getMsgUseFiller(String filler19) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%-3s", this.fType))
                .append(String.format("%-7s", this.msgType))
                .append(String.format("%-10s", this.sendCd))
                .append(String.format("%-10s", this.recvCd))
                .append(this.sendTm)
                .append(String.format("%02d", getRecCnt()))
                .append(String.format("%04d", getBlockNo()))
                .append(String.format("%03d", getSeqNo()))
                .append(String.format("%-2s", this.rtnCd))
                .append(String.format("%03d", getRecLen()))
                .append(String.format("%-1s", this.srType))
                .append(String.format("%03d", getMissCnt()))
                .append(String.format("%-6s", this.fromDate))
                .append(String.format("%-6s", this.toDate))
                .append(String.format("%-1s", this.reqType))
                /*
                .append(String.format("%-10s", this.filler1))
                .append(String.format("%-1s", this.ebcYn))
                .append(String.format("%-1s", this.encYn))
                .append(String.format("%-1s", this.zipYn))
                .append(String.format("%04d", getBinLen()))
                .append(String.format("%01d", getEncRem()))
                .append(String.format("%-1s", this.binYn))
                 */
                .append(String.format("%-19s", filler19))
                .append(String.format("%-4s", this.filler2));

        return sb.toString().getBytes(MsgCode.MSG_ENCODE.getCode());
    }

    public MsgCode getMsgType() {
        //return msgType;
        return MsgCode.fromCode(this.msgType);
    }

    public void setMsgType(MsgCode msgType) {
        this.msgType = msgType.getCode();
    }

    public String getfType() {return fType;}

    public void setfType(String fType) {
        this.fType = fType;
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
        return Integer.parseInt("0"+this.recCnt.trim());
    }

    public void setRecCnt(int recCnt) {
        this.recCnt = String.format("%02d", recCnt);
    }

    public int getBlockNo() {
        return Integer.parseInt("0"+this.blockNo.trim());
    }

    public void setBlockNo(int blockNo) {
        this.blockNo = String.format("%04d", blockNo);
    }

    public int getSeqNo() {
        return Integer.parseInt("0"+this.seqNo.trim());
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = String.format("%03d", seqNo);
    }

    public RtnCode getRtnCd() {
        return RtnCode.fromCode(this.rtnCd);
    }

    public void setRtnCd(RtnCode rtnCd) {
        this.rtnCd = rtnCd.getCode();
    }

    public int getRecLen() {
        return Integer.parseInt("0"+this.recLen.trim());
    }

    public void setRecLen(int recLen) {
        this.recLen = String.format("%03d", recLen);
    }

    public MsgCode getSrType() {
        return MsgCode.fromCode(this.srType);
    }

    public void setSrType(MsgCode srType) {
        this.srType = srType.getCode();
    }

    public int getMissCnt() {
        return Integer.parseInt("0"+this.missCnt.trim());
    }

    public void setMissCnt(int missCnt) {
        this.missCnt = String.format("%03d", missCnt);
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
        return MsgCode.fromCode(this.reqType);
    }

    public void setReqType(MsgCode reqType) {
        this.reqType = reqType.getCode();
    }

    public String getFiller1() {
        return filler1;
    }

    public void setFiller1(String filler1) {
        this.filler1 = filler1;
    }

    public MsgCode getEbcYn() {
        return MsgCode.fromCode(this.ebcYn);
    }

    public void setEbcYn(MsgCode ebcYn) {
        this.ebcYn = ebcYn.getCode();
    }

    public MsgCode getEncYn() {
        return MsgCode.fromCode(this.encYn);
    }

    public void setEncYn(MsgCode encYn) {
        this.encYn = encYn.getCode();
    }

    public MsgCode getZipYn() {
        return MsgCode.fromCode(this.zipYn);
    }

    public void setZipYn(MsgCode zipYn) {
        this.zipYn = zipYn.getCode();
    }

    public int getBinLen() {
        return Integer.parseInt("0"+this.binLen.trim());
    }

    public void setBinLen(int binLen) {
        this.binLen = String.format("%04d", binLen);
    }

    public int getEncRem() {
        return Integer.parseInt("0"+this.encRem.trim());
    }

    public void setEncRem(int encRem) {
        this.encRem = String.format("%01d", encRem);
    }

    public MsgCode getBinYn() {
        return MsgCode.fromCode(this.binYn);
    }

    public void setBinYn(MsgCode binYn) {
        this.binYn = binYn.getCode();
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
