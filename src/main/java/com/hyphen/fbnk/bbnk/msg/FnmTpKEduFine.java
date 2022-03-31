package com.hyphen.fbnk.bbnk.msg;


public class FnmTpKEduFine {
    private String charSet;
    private String fileFlag;
    private String sendDt;
    private String sendCd;
    private String recvCd;
    private String facCd;
    private String infoCd;
    private String fileSeq;

    public FnmTpKEduFine(String fileName) {
        String[] splitList = fileName.split("_");
        this.charSet    = splitList[0].substring(0, 1);
        this.fileFlag   = splitList[0].substring(1, 4);
        this.sendDt     = splitList[0].substring(4);
        this.sendCd     = splitList[1];
        this.recvCd     = splitList[2];
        this.facCd      = splitList[3];
        this.infoCd     = splitList[4].substring(0, 3);
        this.fileSeq    = splitList[4].substring(4);
    }

    public FnmTpKEduFine(String charSet, String fileFlag, String sendDt, String sendCd, String recvCd, String facCd, String infoCd, String fileSeq) {
        this.charSet = charSet;
        this.fileFlag = fileFlag;
        this.sendDt = sendDt;
        this.sendCd = sendCd;
        this.recvCd = recvCd;
        this.facCd = facCd;
        this.infoCd = infoCd;
        this.fileSeq = fileSeq;
    }

    public String getFileName(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.charSet).append(this.fileFlag).append(this.sendDt).append("_").append(this.sendCd).append("_").append(this.recvCd)
                .append("_").append(this.facCd).append("_").append(this.infoCd).append(".").append(this.fileSeq);
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
    public String getFacCd() {return facCd;}
    public void setFacCd(String facCd) {this.facCd = facCd;}
    public String getInfoCd() {return infoCd;}
    public void setInfoCd(String infoCd) {this.infoCd = infoCd;}
    public String getFileSeq() {return fileSeq;}
    public void setFileSeq(String fileSeq) {this.fileSeq = fileSeq;}

    @Override
    public String toString() {
        return "FnmTpKEduFine{" +
                "charSet='" + charSet + '\'' +
                ", fileFlag='" + fileFlag + '\'' +
                ", sendDt='" + sendDt + '\'' +
                ", sendCd='" + sendCd + '\'' +
                ", recvCd='" + recvCd + '\'' +
                ", facCd='" + facCd + '\'' +
                ", infoCd='" + infoCd + '\'' +
                '}';
    }
}
