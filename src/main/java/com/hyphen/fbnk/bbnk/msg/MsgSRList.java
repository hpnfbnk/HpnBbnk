package com.hyphen.fbnk.bbnk.msg;

public class MsgSRList {
    public static final int msgSize = 62;

    private final String infoCd;
    private final String sendCd;
    private final String recvCd;
    private final String seqNo;
    private final String sendTm;
    private final String recvTm;
    private final String fileSize;

    //4 split msg
    public MsgSRList(byte[] msg) {
        this.infoCd     = new String(msg, 0, 5);
        this.sendCd     = new String(msg, 5, 5);
        this.recvCd     = new String(msg, 5+5, 5);
        this.seqNo      = new String(msg, 5+5+5, 5);
        this.sendTm     = new String(msg, 5+5+5+5, 14);
        this.recvTm     = new String(msg, 5+5+5+5+14, 14);
        this.fileSize   = new String(msg, 5+5+5+5+14+14, 10);
    }

    public String getInfoCd() {
        return infoCd;
    }

    public String getSendCd() {
        return sendCd;
    }

    public String getRecvCd() {
        return recvCd;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public String getSendTm() {
        return sendTm;
    }

    public String getRecvTm() {
        return recvTm;
    }

    public String getFileSize() {
        return fileSize;
    }

    @Override
    public String toString() {
        return "SRList{" +
                "infoCd='" + infoCd + '\'' +
                ", sendCd='" + sendCd + '\'' +
                ", recvCd='" + recvCd + '\'' +
                ", seqNo='" + seqNo + '\'' +
                ", sendTm='" + sendTm + '\'' +
                ", recvTm='" + recvTm + '\'' +
                ", fileSize='" + fileSize + '\'' +
                '}';
    }
}
