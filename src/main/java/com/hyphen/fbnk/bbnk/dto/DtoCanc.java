package com.hyphen.fbnk.bbnk.dto;

public class DtoCanc {
    private String facCd    = "";
    private String facTp    = "";
    private String regDt    = "";
    private String bankCd   = "";
    private String cancGb   = "";
    private String cancDt   = "";
    private String socId    = "";
    private String napbuno  = "";
    private String acctNo   = "";
    private String cancTp   = "";

    public DtoCanc() {}

    /**
     * 출금통합관리(금결원) 해내역통보
     * @param facCd 이용기관코드
     * @param facTp 이용기관구분코드 배치:1, 실시간:2
     * @param regDt 처리기준일자
     * @param bankCd 은행코드
     * @param cancGb 해지발생기관구분 1:금융회사, 4:통합관리시스템(금결원payinfo)
     * @param cancDt 금융회사 원장반영일
     * @param socId 예금주 실명번호
     * @param napbuno 납부자번호
     * @param acctNo 계좌번호
     * @param cancTp 해지구분 0:고객 요청, 1:금융회사 임의 해지, 2:금융회사 계좌 변경, 9:기타, N:증빙자료 부재
     */
    public DtoCanc(String facCd, String facTp, String regDt, String bankCd, String cancGb, String cancDt, String socId, String napbuno, String acctNo, String cancTp) {
        this.facCd = facCd;
        this.facTp = facTp;
        this.regDt = regDt;
        this.bankCd = bankCd;
        this.cancGb = cancGb;
        this.cancDt = cancDt;
        this.socId = socId;
        this.napbuno = napbuno;
        this.acctNo = acctNo;
        this.cancTp = cancTp;
    }

    public String getFacCd() {
        return facCd;
    }

    public void setFacCd(String facCd) {
        this.facCd = facCd;
    }

    public String getFacTp() {
        return facTp;
    }

    public void setFacTp(String facTp) {
        this.facTp = facTp;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getBankCd() {
        return bankCd;
    }

    public void setBankCd(String bankCd) {
        this.bankCd = bankCd;
    }

    public String getCancGb() {
        return cancGb;
    }

    public void setCancGb(String cancGb) {
        this.cancGb = cancGb;
    }

    public String getCancDt() {
        return cancDt;
    }

    public void setCancDt(String cancDt) {
        this.cancDt = cancDt;
    }

    public String getSocId() {
        return socId;
    }

    public void setSocId(String socId) {
        this.socId = socId;
    }

    public String getNapbuno() {
        return napbuno;
    }

    public void setNapbuno(String napbuno) {
        this.napbuno = napbuno;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getCancTp() {
        return cancTp;
    }

    public void setCancTp(String cancTp) {
        this.cancTp = cancTp;
    }

    @Override
    public String toString() {
        return "DtoCanc{" +
                "facCd='" + facCd + '\'' +
                ", facTp='" + facTp + '\'' +
                ", regDt='" + regDt + '\'' +
                ", bankCd='" + bankCd + '\'' +
                ", cancGb='" + cancGb + '\'' +
                ", cancDt='" + cancDt + '\'' +
                ", socId='" + socId + '\'' +
                ", napbuno='" + napbuno + '\'' +
                ", acctNo='" + acctNo + '\'' +
                ", cancTp='" + cancTp + '\'' +
                '}';
    }
}
