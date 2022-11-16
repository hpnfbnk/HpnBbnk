package com.hyphen.fbnk.bbnk.dto;

public class DtoShift {
    private String workDt       = "";   //처리기준일자(금결원 출통시스템에서 접수한 일자)
    private String vanId        = "";   //VAN ID(변경접수요청시 받은값을 그대로 입력)

    private String shiftGb      = "";   //변경접수구분 1:금융회사, 4:통합관리시스템(금결원payinfo)
    private String reqDt        = "";   //변경신청일자(고객이 신청한 일자)
    private String acceptDt     = "";   //변경접수일자(이용기관이 첩수한 일자, 변경접수결과시 입력 필요)
    private String socId        = "";   //실명번호
    private String rtnCd        = "";   //응답코드(변경접수결과시 입력 필요)

    private String befFacTp     = "";   //변경전 이용기관 구분코드
    private String befFacCd     = "";   //변경전 이용기관코드
    private String befNapbuNo   = "";   //변경전 납부자번호
    private String befBankCd    = "";   //변경전 금융회사코드
    private String befAcctNo    = "";   //변경전 계좌번호

    private String aftFacCd     = "";   //변경후 이용기관코드(변경접수결과시 입력 필요)
    private String aftNapbuNo   = "";   //변경후 납부자번호(변경접수결과시 입력 필요)
    private String aftBankCd    = "";   //변경후 금융회사코드
    private String aftAcctNo    = "";   //변경후 계좌번호

    private String cancRtnCd    = "";   //해지처리 응답코드(변경처리결과시만 수록)
    private String cancDt       = "";   //해지처리일자(변경처리결과시만 수록)
    private String regRtnCd     = "";   //신규처리 응답코드(변경처리결과시만 수록)
    private String regDt        = "";   //신규처리일자(변경처리결과시만 수록)
    private String billSvcTp    = "";   //자동이체서비스종류(변경후이용기관코드의 서비스종류 1:지로, 2:금결원CMS, 3,공백:펌뱅킹)

    public DtoShift() {}

    /**
     * 출금통합관리(금결원) 계좌변경접수요청(FB0211), 변경접수결과(FB0221), 변경처리결과(FB0222)
     * @param workDt 처리기준일자(금결원 출통시스템에서 접수한 일자)
     * @param vanId VAN ID(변경접수요청시 받은값을 그대로 입력)
     * @param shiftGb 변경접수구분 1:금융회사, 4:통합관리시스템(금결원payinfo)
     * @param reqDt 변경신청일자(고객이 신청한 일자)
     * @param acceptDt 변경접수일자(이용기관이 첩수한 일자, 변경접수결과시 입력 필요)
     * @param socId 실명번호
     * @param rtnCd 응답코드(변경접수결과시 입력 필요)
     * @param befFacTp 변경전 이용기관 구분코드 배치:1, 실시간:2
     * @param befFacCd 변경전 이용기관 구분코드
     * @param befNapbuNo 변경전 납부자번호
     * @param befBankCd 변경전 금융회사코드
     * @param befAcctNo 변경전 계좌번호
     * @param aftFacCd 변경후 이용기관코드(변경접수결과시 입력 필요)
     * @param aftNapbuNo 변경후 납부자번호(변경접수결과시 입력 필요)
     * @param aftBankCd 변경후 금융회사코드
     * @param aftAcctNo 변경후 계좌번호
     * @param cancRtnCd 해지처리 응답코드(변경처리결과시만 수록)
     * @param cancDt 해지처리일자(변경처리결과시만 수록)
     * @param regRtnCd 신규처리 응답코드(변경처리결과시만 수록)
     * @param regDt 신규처리일자(변경처리결과시만 수록)
     * @param billSvcTp 자동이체서비스종류(변경후이용기관코드의 서비스종류 1:지로, 2:금결원CMS, 3,공백:펌뱅킹)
     */
    public DtoShift(String workDt, String vanId, String shiftGb, String reqDt, String acceptDt, String socId, String rtnCd, String befFacTp, String befFacCd, String befNapbuNo, String befBankCd, String befAcctNo, String aftFacCd, String aftNapbuNo, String aftBankCd, String aftAcctNo, String cancRtnCd, String cancDt, String regRtnCd, String regDt, String billSvcTp) {
        this.workDt = workDt;
        this.vanId = vanId;
        this.shiftGb = shiftGb;
        this.reqDt = reqDt;
        this.acceptDt = acceptDt;
        this.socId = socId;
        this.rtnCd = rtnCd;
        this.befFacTp = befFacTp;
        this.befFacCd = befFacCd;
        this.befNapbuNo = befNapbuNo;
        this.befBankCd = befBankCd;
        this.befAcctNo = befAcctNo;
        this.aftFacCd = aftFacCd;
        this.aftNapbuNo = aftNapbuNo;
        this.aftBankCd = aftBankCd;
        this.aftAcctNo = aftAcctNo;
        this.cancRtnCd = cancRtnCd;
        this.cancDt = cancDt;
        this.regRtnCd = regRtnCd;
        this.regDt = regDt;
        this.billSvcTp = billSvcTp;
    }

    public String getWorkDt() {
        return workDt;
    }

    public void setWorkDt(String workDt) {
        this.workDt = workDt;
    }

    public String getVanId() {
        return vanId;
    }

    public void setVanId(String vanId) {
        this.vanId = vanId;
    }

    public String getBefFacTp() {
        return befFacTp;
    }

    public void setBefFacTp(String befFacTp) {
        this.befFacTp = befFacTp;
    }

    public String getShiftGb() {
        return shiftGb;
    }

    public void setShiftGb(String shiftGb) {
        this.shiftGb = shiftGb;
    }

    public String getReqDt() {
        return reqDt;
    }

    public void setReqDt(String reqDt) {
        this.reqDt = reqDt;
    }

    public String getAcceptDt() {
        return acceptDt;
    }

    public void setAcceptDt(String acceptDt) {
        this.acceptDt = acceptDt;
    }

    public String getSocId() {
        return socId;
    }

    public void setSocId(String socId) {
        this.socId = socId;
    }

    public String getRtnCd() {
        return rtnCd;
    }

    public void setRtnCd(String rtnCd) {
        this.rtnCd = rtnCd;
    }

    public String getBefFacCd() {
        return befFacCd;
    }

    public void setBefFacCd(String befFacCd) {
        this.befFacCd = befFacCd;
    }

    public String getBefNapbuNo() {
        return befNapbuNo;
    }

    public void setBefNapbuNo(String befNapbuNo) {
        this.befNapbuNo = befNapbuNo;
    }

    public String getBefBankCd() {
        return befBankCd;
    }

    public void setBefBankCd(String befBankCd) {
        this.befBankCd = befBankCd;
    }

    public String getBefAcctNo() {
        return befAcctNo;
    }

    public void setBefAcctNo(String befAcctNo) {
        this.befAcctNo = befAcctNo;
    }

    public String getAftFacCd() {
        return aftFacCd;
    }

    public void setAftFacCd(String aftFacCd) {
        this.aftFacCd = aftFacCd;
    }

    public String getAftNapbuNo() {
        return aftNapbuNo;
    }

    public void setAftNapbuNo(String aftNapbuNo) {
        this.aftNapbuNo = aftNapbuNo;
    }

    public String getAftBankCd() {
        return aftBankCd;
    }

    public void setAftBankCd(String aftBankCd) {
        this.aftBankCd = aftBankCd;
    }

    public String getAftAcctNo() {
        return aftAcctNo;
    }

    public void setAftAcctNo(String aftAcctNo) {
        this.aftAcctNo = aftAcctNo;
    }

    public String getCancRtnCd() {
        return cancRtnCd;
    }

    public void setCancRtnCd(String cancRtnCd) {
        this.cancRtnCd = cancRtnCd;
    }

    public String getCancDt() {
        return cancDt;
    }

    public void setCancDt(String cancDt) {
        this.cancDt = cancDt;
    }

    public String getRegRtnCd() {
        return regRtnCd;
    }

    public void setRegRtnCd(String regRtnCd) {
        this.regRtnCd = regRtnCd;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getBillSvcTp() {
        return billSvcTp;
    }

    public void setBillSvcTp(String billSvcTp) {
        this.billSvcTp = billSvcTp;
    }

    @Override
    public String toString() {
        return "DtoShift{" +
                "workDt='" + workDt + '\'' +
                ", vanId='" + vanId + '\'' +
                ", facTp='" + befFacTp + '\'' +
                ", shiftGb='" + shiftGb + '\'' +
                ", reqDt='" + reqDt + '\'' +
                ", acceptDt='" + acceptDt + '\'' +
                ", socId='" + socId + '\'' +
                ", rtnCd='" + rtnCd + '\'' +
                ", befFacCd='" + befFacCd + '\'' +
                ", befNapbuNo='" + befNapbuNo + '\'' +
                ", befBankCd='" + befBankCd + '\'' +
                ", befAcctNo='" + befAcctNo + '\'' +
                ", aftFacCd='" + aftFacCd + '\'' +
                ", aftNapbuNo='" + aftNapbuNo + '\'' +
                ", aftBankCd='" + aftBankCd + '\'' +
                ", aftAcctNo='" + aftAcctNo + '\'' +
                ", cancRtnCd='" + cancRtnCd + '\'' +
                ", cancDt='" + cancDt + '\'' +
                ", regRtnCd='" + regRtnCd + '\'' +
                ", regDt='" + regDt + '\'' +
                ", billSvcTp='" + billSvcTp + '\'' +
                '}';
    }
}
