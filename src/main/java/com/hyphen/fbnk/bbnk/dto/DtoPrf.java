package com.hyphen.fbnk.bbnk.dto;

public class DtoPrf {
    private String facCd    = "";
    private String regDt    = "";
    private String msgNo    = "";
    private String reqDt   = "";
    private String socTp    = "";
    private String socId    = "";
    private String rtYn     = "";
    private String napbuNo  = "";
    private String bankCd  = "";
    private String acctNo   = "";
    private String prfTp    = "";
    private String expNm    = "";
    private String disYn    = "";
    private String prfPath  = "";
    private String rtnCd    = "";

    public DtoPrf() {}
    /**
     * 증빙자료정보/등록결과
     * @param facCd 은행에서 발급받은 기관코드 (통합처리-IY0 시 불필요)
     * @param regDt 처리기준일자(계좌등록일자)
     * @param reqDt 신청일자(고객이 신청한 일자)
     * @param socTp 예금주 실명번호 종류 0:개인고객 또는 외국인, 1:사업자, 2:여권번호
     * @param socId 예금주 실명번호
     * @param rtYn 실시간등록여부(realtime으로 계좌등록한 경우 Y)
     * @param napbuNo 납부자번호
     * @param bankCd 은행코드
     * @param acctNo 계좌번호
     * @param prfTp 증빙구분 서면:1, 공인인증:2, 일반인증:3, 녹취:4, ARS:5, 기타:6
     * @param expNm 확장자명(jpg, mp3, pdf 등..)
     * @param disYn 해지구분(계좌등록해지요청인경우 Y)(해지요청시 증빙자료는 필요없음.)
     * @param prfPath 증빙자료 경로(./proof/RP000042_prf.jpg 등...)(요청시만 필요)
     * @param rtnCd 응답코드 정상:0000
     */
    public DtoPrf(String facCd, String regDt, String reqDt, String socTp, String socId, String rtYn, String napbuNo, String bankCd, String acctNo, String prfTp, String expNm, String disYn, String prfPath, String rtnCd) {
        this.facCd = facCd;
        this.regDt = regDt;
        this.reqDt = reqDt;
        this.socTp = socTp;
        this.socId = socId;
        this.rtYn = rtYn;
        this.napbuNo = napbuNo;
        this.bankCd = bankCd;
        this.acctNo = acctNo;
        this.prfTp = prfTp;
        this.expNm = expNm;
        this.disYn = disYn;
        this.prfPath = prfPath;
        this.rtnCd = rtnCd;
    }

    public String getFacCd() {
        return facCd;
    }

    public void setFacCd(String facCd) {
        this.facCd = facCd;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(String msgNo) {
        this.msgNo = msgNo;
    }

    public String getReqDt() {
        return reqDt;
    }

    public void setReqDt(String reqDt) {
        this.reqDt = reqDt;
    }

    public String getSocTp() {
        return socTp;
    }

    public void setSocTp(String socTp) {
        this.socTp = socTp;
    }

    public String getSocId() {
        return socId;
    }

    public void setSocId(String socId) {
        this.socId = socId;
    }

    public String getRtYn() {
        return rtYn;
    }

    public void setRtYn(String rtYn) {
        this.rtYn = rtYn;
    }

    public String getNapbuNo() {
        return napbuNo;
    }

    public void setNapbuNo(String napbuNo) {
        this.napbuNo = napbuNo;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getPrfTp() {
        return prfTp;
    }

    public void setPrfTp(String prfTp) {
        this.prfTp = prfTp;
    }

    public String getExpNm() {
        return expNm;
    }

    public void setExpNm(String expNm) {
        this.expNm = expNm;
    }

    public String getDisYn() {
        return disYn;
    }

    public void setDisYn(String disYn) {
        this.disYn = disYn;
    }

    public String getPrfPath() {
        return prfPath;
    }

    public void setPrfPath(String prfPath) {
        this.prfPath = prfPath;
    }

    public String getBankCd() {
        return bankCd;
    }

    public void setBankCd(String bankCd) {
        this.bankCd = bankCd;
    }

    public String getRtnCd() {
        return rtnCd;
    }

    public void setRtnCd(String rtnCd) {
        this.rtnCd = rtnCd;
    }

    @Override
    public String toString() {
        return "DtoPrf{" +
                "facCd='" + facCd + '\'' +
                ", regDt='" + regDt + '\'' +
                ", msgNo='" + msgNo + '\'' +
                ", reqDt='" + reqDt + '\'' +
                ", socTp='" + socTp + '\'' +
                ", socId='" + socId + '\'' +
                ", rtYn='" + rtYn + '\'' +
                ", napbuNo='" + napbuNo + '\'' +
                ", bankCd='" + bankCd + '\'' +
                ", acctNo='" + acctNo + '\'' +
                ", prfTp='" + prfTp + '\'' +
                ", expNm='" + expNm + '\'' +
                ", disYn='" + disYn + '\'' +
                ", prfPath='" + prfPath + '\'' +
                ", rtnCd='" + rtnCd + '\'' +
                '}';
    }
}
