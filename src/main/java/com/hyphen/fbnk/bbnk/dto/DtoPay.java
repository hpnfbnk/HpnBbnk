package com.hyphen.fbnk.bbnk.dto;

public class DtoPay {
    private String facCd    = "";
    private String hBankCd  = "";
    private String acctDt   = "";
    private String hAcctNo  = "";
    private String pwd      = "";
    private String repFacCd = "";
    private String copNo    = "";
    private String amtTp    = "";
    private String roundNo  = "";
    private String dBankCd  = "";
    private String dAcctNo  = "";
    private long reqAmt     = 0L;
    private String result   = "";
    private String errCd    = "";
    private long failAmt    = 0L;
    private String itemCd   = "";
    private String bankBook = "";
    private String mBankCd  = "";
    private String tmpInfo  = "";

    private int msgNo           = 0;
    private String hBankBook    = "";

    public DtoPay() {}
    /**
     * 입금이체용 Dto 생성
     * @param facCd 은행에서 발급받은 기관코드 (통합처리-I03 시 불필요)
     * @param hBankCd 모계좌은행(업체모계좌은행) (통합처리-I03 시 불필요)
     * @param acctDt 이체일
     * @param hAcctNo 모계좌번호
     * @param pwd 비밀번호
     * @param repFacCd 대표기관코드 (필요시사용)
     * @param copNo 회사번호 (필요시사용-하나은행)
     * @param amtTp 자금구분(필요시사용 1:급여, 2:상여, 3:수당 등..)
     * @param roundNo 업체사용영역 (보낸대로 돌려받을수 있음)
     * @param dBankCd 고객계좌은행
     * @param dAcctNo 고객계좌번호
     * @param reqAmt 요청금액
     * @param itemCd 통장기장항목코드 (필요시사용)
     * @param bankBook 은행에따라 최대사용가능길이다름(최대길이초과시 뒷부분 짤려서 처리됨). 신한,농협,하나:12byte 등..
     * @param mBankCd 보낼은행코드(통합처리-I03 시 만 필요)
     * @param tmpInfo 업체사용정보(결과데이터에 보낸대로 포함되 돌아옴)
     */
    public DtoPay(String facCd, String hBankCd, String acctDt, String hAcctNo, String pwd, String repFacCd, String copNo, String amtTp,
                  String roundNo, String dBankCd, String dAcctNo, long reqAmt, String result, String errCd, long failAmt, String itemCd,
                  String bankBook, String mBankCd, String tmpInfo) {
        this.facCd = facCd;
        this.hBankCd = hBankCd;
        this.acctDt = acctDt;
        this.hAcctNo = hAcctNo;
        this.pwd = pwd;
        this.repFacCd = repFacCd;
        this.copNo = copNo;
        this.amtTp = amtTp;
        this.roundNo = roundNo;
        this.dBankCd = dBankCd;
        this.dAcctNo = dAcctNo;
        this.reqAmt = reqAmt;
        this.result = result;
        this.errCd = errCd;
        this.failAmt = failAmt;
        this.itemCd = itemCd;
        this.bankBook = bankBook;
        this.mBankCd = mBankCd;
        this.tmpInfo = tmpInfo;
    }

    /**
     * 배치성-실시간 입금이체용 Dto 생성
     * @param facCd 은행에서 발급받은 기관코드 (통합처리-I03 시 불필요)
     * @param hBankCd 모계좌은행(업체모계좌은행) (통합처리-I03 시 불필요)
     * @param acctDt 불필요(즉시처리)
     * @param hAcctNo 모계좌번호
     * @param pwd 비밀번호
     * @param repFacCd 대표기관코드 (필요시사용)
     * @param copNo CMS코드(필요시사용)
     * @param amtTp 자금구분(필요시사용 1:급여, 2:상여, 3:수당 등..)
     * @param roundNo 업체사용영역 (보낸대로 돌려받을수 있음)
     * @param dBankCd 고객계좌은행
     * @param dAcctNo 고객계좌번호
     * @param reqAmt 요청금액
     * @param itemCd 통장기장항목코드 (필요시사용)
     * @param bankBook 은행에따라 최대사용가능길이다름(최대길이초과시 뒷부분 짤려서 처리됨). 신한,농협,하나:12byte 등..
     * @param mBankCd 보낼은행코드(통합처리-I03 시 만 필요)
     * @param tmpInfo 업체사용정보(결과데이터에 보낸대로 포함되 돌아옴)
     * @param msgNo 전문번호(기관코드+모계좌은행+전문번호의 조합은 유일해야 함)(700,000번 이상은 사용불가)
     * @param hBankBook 업체모계좌적요
     */
    public DtoPay(String facCd, String hBankCd, String acctDt, String hAcctNo, String pwd, String repFacCd, String copNo, String amtTp,
                  String roundNo, String dBankCd, String dAcctNo, long reqAmt, String result, String errCd, long failAmt, String itemCd,
                  String bankBook, String mBankCd, String tmpInfo, int msgNo, String hBankBook) {
        this.facCd = facCd;
        this.hBankCd = hBankCd;
        this.acctDt = acctDt;
        this.hAcctNo = hAcctNo;
        this.pwd = pwd;
        this.repFacCd = repFacCd;
        this.copNo = copNo;
        this.amtTp = amtTp;
        this.roundNo = roundNo;
        this.dBankCd = dBankCd;
        this.dAcctNo = dAcctNo;
        this.reqAmt = reqAmt;
        this.result = result;
        this.errCd = errCd;
        this.failAmt = failAmt;
        this.itemCd = itemCd;
        this.bankBook = bankBook;
        this.mBankCd = mBankCd;
        this.tmpInfo = tmpInfo;
        this.msgNo = msgNo;
        this.hBankBook = hBankBook;
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

    public String getAcctDt() {
        return acctDt;
    }

    public void setAcctDt(String acctDt) {
        this.acctDt = acctDt;
    }

    public String gethAcctNo() {
        return hAcctNo;
    }

    public void sethAcctNo(String hAcctNo) {
        this.hAcctNo = hAcctNo;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRepFacCd() {
        return repFacCd;
    }

    public void setRepFacCd(String repFacCd) {
        this.repFacCd = repFacCd;
    }

    public String getCopNo() {
        return copNo;
    }

    public void setCopNo(String copNo) {
        this.copNo = copNo;
    }

    public String getAmtTp() {
        return amtTp;
    }

    public void setAmtTp(String amtTp) {
        this.amtTp = amtTp;
    }

    public String getRoundNo() {
        return roundNo;
    }

    public void setRoundNo(String roundNo) {
        this.roundNo = roundNo;
    }

    public String getdBankCd() {
        return dBankCd;
    }

    public void setdBankCd(String dBankCd) {
        this.dBankCd = dBankCd;
    }

    public String getdAcctNo() {
        return dAcctNo;
    }

    public void setdAcctNo(String dAcctNo) {
        this.dAcctNo = dAcctNo;
    }

    public long getReqAmt() {
        return reqAmt;
    }

    public void setReqAmt(long reqAmt) {
        this.reqAmt = reqAmt;
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

    public long getFailAmt() {
        return failAmt;
    }

    public void setFailAmt(long failAmt) {
        this.failAmt = failAmt;
    }

    public String getItemCd() {
        return itemCd;
    }

    public void setItemCd(String itemCd) {
        this.itemCd = itemCd;
    }

    public String getBankBook() {
        return bankBook;
    }

    public void setBankBook(String bankBook) {
        this.bankBook = bankBook;
    }

    public String getmBankCd() {
        return mBankCd;
    }

    public void setmBankCd(String mBankCd) {
        this.mBankCd = mBankCd;
    }

    public String getTmpInfo() {
        return tmpInfo;
    }

    public void setTmpInfo(String tmpInfo) {
        this.tmpInfo = tmpInfo;
    }

    public int getMsgNo() {return msgNo;}

    public void setMsgNo(int msgNo) {this.msgNo = msgNo;}

    public String gethBankBook() {return hBankBook;}

    public void sethBankBook(String hBankBook) {this.hBankBook = hBankBook;}

    @Override
    public String toString() {
        return "DtoPay{" +
                "facCd='" + facCd + '\'' +
                ", hBankCd='" + hBankCd + '\'' +
                ", acctDt='" + acctDt + '\'' +
                ", hAcctNo='" + hAcctNo + '\'' +
                ", pwd='" + pwd + '\'' +
                ", repFacCd='" + repFacCd + '\'' +
                ", copNo='" + copNo + '\'' +
                ", amtTp='" + amtTp + '\'' +
                ", roundNo='" + roundNo + '\'' +
                ", dBankCd='" + dBankCd + '\'' +
                ", dAcctNo='" + dAcctNo + '\'' +
                ", reqAmt=" + reqAmt +
                ", result='" + result + '\'' +
                ", errCd='" + errCd + '\'' +
                ", failAmt=" + failAmt +
                ", itemCd='" + itemCd + '\'' +
                ", bankBook='" + bankBook + '\'' +
                ", mBankCd='" + mBankCd + '\'' +
                ", tmpInfo='" + tmpInfo + '\'' +
                ", msgNo='" + msgNo + '\'' +
                ", hBankBook='" + hBankBook + '\'' +
                '}';
    }
}
