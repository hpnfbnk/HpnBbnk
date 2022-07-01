package com.hyphen.fbnk.bbnk.dto;

public class DtoReg {
    private String facCd    = "";
    private String hBankCd  = "";
    private String hExtra   = "";
    private String dBankCd  = "";
    private String acctNo   = "";
    private String regTp    = "";
    private String result   = "";
    private String errCd    = "";
    private String socId    = "";
    private String napbuNo  = "";
    private String tmpInfo  = "";
    private String socIdTp  = "";
    private String prfTp    = "";
    private String mBankCd  = "";

    public DtoReg() {}
    /**
     * 출금동의계좌등록용 Dto 생성
     * @param facCd 은행에서 발급받은 기관코드 (통합처리-I0R 시 불필요)
     * @param hBankCd 집금모계좌은행(업체모계좌은행) (통합처리-I0R 시 불필요)
     * @param hExtra 부가정보(특수용도로만 사용)
     * @param dBankCd 출금고객계좌은행
     * @param acctNo 출금계좌번호
     * @param regTp 신청구분 1:등록신청, 2:해지신청
     * @param result 처리여부 Y:성공, N:실패
     * @param errCd 오류코드 0000:성공
     * @param socId 실명번호(주민번호-앞6자리, 사업자번호 등..)
     * @param napbuNo 납부자번호(출금요청시 기등록된 납부자번호와 같아야 출금됨)(최대20자)(한글불가)
     * @param tmpInfo 업체사용정보(결과데이터에 보낸대로 포함되 돌아옴)
     * @param socIdTp 예금주실명번호종류(증빙자료제출 일부면제업체만 사용) 0:개인고객 또는 외국인, 1:사업자, 2:여권번호
     * @param prfTp 동의자료구분(증빙자료제출 일부면제업체만 사용) 서면:1, 공인인증:2, 일반인증:3, 녹취:4, ARS:5, 기타:6
     * @param mBankCd 보낼은행코드(통합처리-I0R 시 만 필요)
     */
    public DtoReg(String facCd, String hBankCd, String hExtra, String dBankCd, String acctNo, String regTp, String result, String errCd, String socId, String napbuNo, String tmpInfo, String socIdTp, String prfTp, String mBankCd) {
        this.facCd = facCd;
        this.hBankCd = hBankCd;
        this.hExtra = hExtra;
        this.dBankCd = dBankCd;
        this.acctNo = acctNo;
        this.regTp = regTp;
        this.result = result;
        this.errCd = errCd;
        this.socId = socId;
        this.napbuNo = napbuNo;
        this.tmpInfo = tmpInfo;
        this.socIdTp = socIdTp;
        this.prfTp = prfTp;
        this.mBankCd = mBankCd;
    }

    public String getFacCd() {
        return facCd;
    }

    public void setFacCd(String facCd) {
        this.facCd = facCd;
    }

    public String gethBankCd() {
        return hBankCd;
    }

    public void sethBankCd(String hBankCd) {
        this.hBankCd = hBankCd;
    }

    public String gethExtra() {
        return hExtra;
    }

    public void sethExtra(String hExtra) {
        this.hExtra = hExtra;
    }

    public String getdBankCd() {
        return dBankCd;
    }

    public void setdBankCd(String dBankCd) {
        this.dBankCd = dBankCd;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getRegTp() {
        return regTp;
    }

    public void setRegTp(String regTp) {
        this.regTp = regTp;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrCd() {
        return errCd;
    }

    public void setErrCd(String errCd) {
        this.errCd = errCd;
    }

    public String getSocId() {
        return socId;
    }

    public void setSocId(String socId) {
        this.socId = socId;
    }

    public String getNapbuNo() {
        return napbuNo;
    }

    public void setNapbuNo(String napbuNo) {
        this.napbuNo = napbuNo;
    }

    public String getTmpInfo() {
        return tmpInfo;
    }

    public void setTmpInfo(String tmpInfo) {
        this.tmpInfo = tmpInfo;
    }

    public String getSocIdTp() {
        return socIdTp;
    }

    public void setSocIdTp(String socIdTp) {
        this.socIdTp = socIdTp;
    }

    public String getPrfTp() {
        return prfTp;
    }

    public void setPrfTp(String prfTp) {
        this.prfTp = prfTp;
    }

    public String getmBankCd() {
        return mBankCd;
    }

    public void setmBankCd(String mBankCd) {
        this.mBankCd = mBankCd;
    }

    @Override
    public String toString() {
        return "DtoReg{" +
                "facCd='" + facCd + '\'' +
                ", hBankCd='" + hBankCd + '\'' +
                ", hExtra='" + hExtra + '\'' +
                ", dBankCd='" + dBankCd + '\'' +
                ", acctNo='" + acctNo + '\'' +
                ", regTp='" + regTp + '\'' +
                ", result='" + result + '\'' +
                ", errCd='" + errCd + '\'' +
                ", socId='" + socId + '\'' +
                ", napbuNo='" + napbuNo + '\'' +
                ", tmpInfo='" + tmpInfo + '\'' +
                ", socIdTp='" + socIdTp + '\'' +
                ", prfTp='" + prfTp + '\'' +
                ", mBankCd='" + mBankCd + '\'' +
                '}';
    }
}
