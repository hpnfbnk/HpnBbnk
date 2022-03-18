package com.hyphen.fbnk.bbnk.dto;

public class DtoSRList {
    private String infoCd;
    private String sendCd;
    private String recvCd;
    private String seqNo;
    private String sendTm;
    private String recvTm;
    private long fileSize;

    /**
     * 송수신목록
     * @param infoCd 파일종류구분코드 계좌등록:R00, 자동이체:200, 지급이체(송금):300, 증빙자료:Y00 등..
     * @param sendCd 송신자코드
     * @param recvCd 수신자코드
     * @param seqNo 파일순번
     * @param sendTm 송신시간
     * @param recvTm 수신시간
     * @param fileSize 파일크기
     */
    public DtoSRList(String infoCd, String sendCd, String recvCd, String seqNo, String sendTm, String recvTm, long fileSize) {
        this.infoCd = infoCd;
        this.sendCd = sendCd;
        this.recvCd = recvCd;
        this.seqNo = seqNo;
        this.sendTm = sendTm;
        this.recvTm = recvTm;
        this.fileSize = fileSize;
    }

    public String getInfoCd() {
        return infoCd;
    }

    public void setInfoCd(String infoCd) {
        this.infoCd = infoCd;
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

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getSendTm() {
        return sendTm;
    }

    public void setSendTm(String sendTm) {
        this.sendTm = sendTm;
    }

    public String getRecvTm() {
        return recvTm;
    }

    public void setRecvTm(String recvTm) {
        this.recvTm = recvTm;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "DtoSRList{" +
                "infoCd='" + infoCd + '\'' +
                ", sendCd='" + sendCd + '\'' +
                ", recvCd='" + recvCd + '\'' +
                ", seqNo='" + seqNo + '\'' +
                ", sendTm='" + sendTm + '\'' +
                ", recvTm='" + recvTm + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
