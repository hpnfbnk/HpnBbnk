package com.hyphen.fbnk.bbnk.dto;

public class DtoAftPrf {
    private String repBankCd = "";
    private String facCd    = "";
    private String regDt    = "";
    private String vanId    = "";
    private String reqTp    = "";
    private String bussNo   = "";
    private String napbuNo  = "";
    private String bankCd   = "";
    private String acctNo   = "";
    private String reqDt    = "";
    private String prfTp    = "";
    private String prfYn    = "";
    private String expNm    = "";
    private String prfPath  = "";
    private String rtnCd    = "";

    public DtoAftPrf() {}
    /**
     * 증빙자료 사후점검 요청/제출
     * @param repBankCd 대표은행코드
     * @param facCd 이용기관코드(제출시 요청에 수록된값 사용)
     * @param regDt 처리기준일자(제출시 요청에 수록된값 사용)
     * @param vanId 펌뱅킹VAN사 이용기관식별ID(제출시 요청에 수록된값 사용)
     * @param reqTp 요청구분 1:사후점검요청, 2:고객열람요청
     * @param bussNo 사업자번호
     * @param napbuNo 납부자번호(제출시 요청에 수록된값 사용)
     * @param bankCd 은행코드(제출시 요청에 수록된값 사용)
     * @param acctNo 계좌번호(제출시 요청에 수록된값 사용)
     * @param reqDt 고객이 신청한 일자
     * @param prfTp 동의자료구분 서면:1, 공인인증:2, 일반인증:3, 녹취:4, ARS:5, 기타:6
     * @param prfYn 동의자료유무 Y:존재, N:없음
     * @param expNm 동의자료 확장자
     * @param prfPath 증빙자료 경로(./proof/RP000042_prf.jpg 등...)(제출시만 필요)
     * @param rtnCd 응답코드               
     */
    public DtoAftPrf(String repBankCd, String facCd, String regDt, String vanId, String reqTp, String bussNo, String napbuNo, String bankCd, String acctNo, String reqDt, String prfTp, String prfYn, String expNm, String prfPath, String rtnCd) {
        this.repBankCd = repBankCd;
        this.facCd = facCd;
        this.regDt = regDt;
        this.vanId = vanId;
        this.reqTp = reqTp;
        this.bussNo = bussNo;
        this.napbuNo = napbuNo;
        this.bankCd = bankCd;
        this.acctNo = acctNo;
        this.reqDt = reqDt;
        this.prfTp = prfTp;
        this.prfYn = prfYn;
        this.expNm = expNm;
        this.prfPath = prfPath;
        this.rtnCd = rtnCd;
    }


    public String getRepBankCd() {
        return repBankCd;
    }

    public void setRepBankCd(String repBankCd) {
        this.repBankCd = repBankCd;
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

    public String getVanId() {
        return vanId;
    }

    public void setVanId(String vanId) {
        this.vanId = vanId;
    }

    public String getReqTp() {
        return reqTp;
    }

    public void setReqTp(String reqTp) {
        this.reqTp = reqTp;
    }

    public String getBussNo() {
        return bussNo;
    }

    public void setBussNo(String bussNo) {
        this.bussNo = bussNo;
    }

    public String getNapbuNo() {
        return napbuNo;
    }

    public void setNapbuNo(String napbuNo) {
        this.napbuNo = napbuNo;
    }

    public String getBankCd() {
        return bankCd;
    }

    public void setBankCd(String bankCd) {
        this.bankCd = bankCd;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getReqDt() {
        return reqDt;
    }

    public void setReqDt(String reqDt) {
        this.reqDt = reqDt;
    }

    public String getPrfTp() {
        return prfTp;
    }

    public void setPrfTp(String prfTp) {
        this.prfTp = prfTp;
    }

    public String getPrfYn() {
        return prfYn;
    }

    public void setPrfYn(String prfYn) {
        this.prfYn = prfYn;
    }

    public String getExpNm() {
        return expNm;
    }

    public void setExpNm(String expNm) {
        this.expNm = expNm;
    }

    public String getPrfPath() {
        return prfPath;
    }

    public void setPrfPath(String prfPath) {
        this.prfPath = prfPath;
    }

    public String getRtnCd() {return rtnCd;}

    public void setRtnCd(String rtnCd) {this.rtnCd = rtnCd;}

    @Override
    public String toString() {
        return "DtoAftPrf{" +
                "repBankCd='" + repBankCd + '\'' +
                ", facCd='" + facCd + '\'' +
                ", regDt='" + regDt + '\'' +
                ", vanId='" + vanId + '\'' +
                ", reqTp='" + reqTp + '\'' +
                ", bussNo='" + bussNo + '\'' +
                ", napbuNo='" + napbuNo + '\'' +
                ", bankCd='" + bankCd + '\'' +
                ", acctNo='" + acctNo + '\'' +
                ", reqDt='" + reqDt + '\'' +
                ", prfTp='" + prfTp + '\'' +
                ", prfYn='" + prfYn + '\'' +
                ", expNm='" + expNm + '\'' +
                ", prfPath='" + prfPath + '\'' +
                ", rtnCd='" + rtnCd + '\'' +
                '}';
    }
}
