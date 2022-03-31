package com.hyphen.fbnk.bbnk.dto;

public class DtoFileList {
    private String sendDt;
    private String infoCd;
    private String sendCd;
    private String recvCd;
    private String seqNo;
    private String filePath;
    private boolean retYn;

    /**
     * 송수신파일목록
     * @param sendDt
     * @param infoCd
     * @param sendCd
     * @param recvCd
     * @param seqNo
     * @param filePath
     * @param retYn
     */
    public DtoFileList(String sendDt, String infoCd, String sendCd, String recvCd, String seqNo, String filePath, boolean retYn) {
        this.sendDt = sendDt;
        this.infoCd = infoCd;
        this.sendCd = sendCd;
        this.recvCd = recvCd;
        this.seqNo = seqNo;
        this.filePath = filePath;
        this.retYn = retYn;
    }

    public String getSendDt() {return sendDt;}
    public void setSendDt(String sendDt) {this.sendDt = sendDt;}
    public String getInfoCd() {return infoCd;}
    public void setInfoCd(String infoCd) {this.infoCd = infoCd;}
    public String getSendCd() {return sendCd;}
    public void setSendCd(String sendCd) {this.sendCd = sendCd;}
    public String getRecvCd() {return recvCd;}
    public void setRecvCd(String recvCd) {this.recvCd = recvCd;}
    public String getSeqNo() {return seqNo;}
    public void setSeqNo(String seqNo) {this.seqNo = seqNo;}
    public String getFilePath() {return filePath;}
    public void setFilePath(String filePath) {this.filePath = filePath;}
    public boolean isRetYn() {return retYn;}
    public void setRetYn(boolean retYn) {this.retYn = retYn;}

    @Override
    public String toString() {
        return "DtoFileList{" +
                "sendDt='" + sendDt + '\'' +
                ", infoCd='" + infoCd + '\'' +
                ", sendCd='" + sendCd + '\'' +
                ", recvCd='" + recvCd + '\'' +
                ", seqNo='" + seqNo + '\'' +
                ", filePath='" + filePath + '\'' +
                ", retCd='" + retYn + '\'' +
                '}';
    }
}
