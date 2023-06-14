package com.hyphen.fbnk.bbnk.dto;

public class DtoFileList {
    private String sendDt;
    private String infoCd;
    private String sendCd;
    private String recvCd;
    private String seqNo;
    private String filePath;
    private boolean retYn;
    private String sendCdPwd;

    /**
     * 송수신파일목록
     * @param sendDt 송수신일자
     * @param infoCd 파일종류구분코드
     * @param sendCd 송신자코드
     * @param recvCd 수신자코드
     * @param seqNo 순번
     * @param filePath 저장파일경로
     * @param retYn 송수신결과
     */
    public DtoFileList(String sendDt, String infoCd, String sendCd, String recvCd, String seqNo, String filePath, boolean retYn) {
        this.sendDt = sendDt;
        this.infoCd = infoCd;
        this.sendCd = sendCd;
        this.recvCd = recvCd;
        this.seqNo = seqNo;
        this.filePath = filePath;
        this.retYn = retYn;
        this.sendCdPwd = "";
    }

    /**
     * 송수신파일목록
     * @param sendDt 송수신일자
     * @param infoCd 파일종류구분코드
     * @param sendCd 송신자코드
     * @param recvCd 수신자코드
     * @param seqNo 순번
     * @param filePath 저장파일경로
     * @param retYn 송수신결과
     * @param sendCdPwd 송신요청시 송신자 통신비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
     */
    public DtoFileList(String sendDt, String infoCd, String sendCd, String recvCd, String seqNo, String filePath, boolean retYn, String sendCdPwd) {
        this.sendDt = sendDt;
        this.infoCd = infoCd;
        this.sendCd = sendCd;
        this.recvCd = recvCd;
        this.seqNo = seqNo;
        this.filePath = filePath;
        this.retYn = retYn;
        this.sendCdPwd = sendCdPwd;
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
    public String getSendCdPwd() {return sendCdPwd;}
    public void setSendCdPwd(String sendCdPwd) {this.sendCdPwd = sendCdPwd;}

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
