package com.hyphen.fbnk.bbnk.msg;

public class FnmTpKsnet {
    public static final String fFlagReq = "BRQ";
    public static final String fFlagRep = "BRR";

    private String charSet = "A";
    private String fileFlag;
    private String sendDt;
    private String sendCd;
    private String recvCd;
    private String infoCd;
    private String fileSeq;

    public FnmTpKsnet(String fileName) {
        this.charSet    = fileName.substring(0, 1);
        this.fileFlag   = fileName.substring(1, 4);
        this.sendDt     = fileName.substring(4, 12);
        this.sendCd     = fileName.substring(13, 17);
        this.recvCd     = fileName.substring(17, 21);
        this.infoCd     = fileName.substring(21, 24);
        this.fileSeq    = fileName.substring(25);
    }

    public FnmTpKsnet(String fileFlag, String sendDt, String sendCd, String recvCd, String infoCd, String fileSeq) {
        this.fileFlag = fileFlag;
        this.sendDt = sendDt;
        this.sendCd = sendCd;
        this.recvCd = recvCd;
        this.infoCd = infoCd;
        this.fileSeq = fileSeq;
    }

    public String getFileName(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.charSet).append(this.fileFlag).append(this.sendDt).append("_").append(this.sendCd).append(this.recvCd)
                .append(this.infoCd).append(".").append(this.fileSeq);
        return sb.toString();
    }

    public String getCharSet() {return charSet;}
    public void setCharSet(String charSet) {this.charSet = charSet;}
    public String getFileFlag() {return fileFlag;}
    public void setFileFlag(String fileFlag) {this.fileFlag = fileFlag;}
    public String getSendDt() {return sendDt;}
    public void setSendDt(String sendDt) {this.sendDt = sendDt;}
    public String getSendCd() {return sendCd;}
    public void setSendCd(String sendCd) {this.sendCd = sendCd;}
    public String getRecvCd() {return recvCd;}
    public void setRecvCd(String recvCd) {this.recvCd = recvCd;}
    public String getInfoCd() {return infoCd;}
    public void setInfoCd(String infoCd) {this.infoCd = infoCd;}
    public String getFileSeq() {return fileSeq;}
    public void setFileSeq(String fileSeq) {this.fileSeq = fileSeq;}

    @Override
    public String toString() {
        return "FnmTpKsnet{" +
                "charSet='" + charSet + '\'' +
                ", fileFlag='" + fileFlag + '\'' +
                ", sendDt='" + sendDt + '\'' +
                ", sendCd='" + sendCd + '\'' +
                ", recvCd='" + recvCd + '\'' +
                ", infoCd='" + infoCd + '\'' +
                ", fileSeq='" + fileSeq + '\'' +
                '}';
    }
}
