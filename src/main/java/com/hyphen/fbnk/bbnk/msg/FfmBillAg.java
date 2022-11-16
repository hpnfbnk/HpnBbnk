package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.dto.DtoBill;
import com.hyphen.fbnk.bbnk.dto.DtoReg;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FfmBillAg {
    private static final Log log = LogFactory.getLog(FfmBillAg.class);
    private final String encodeTp = "euc-kr";

    private String hRecTp   = "H";          //1
    private String hDataSeq = "00000000";   //8
    private String hFacCd   = "";           //10
    private String hFileNm  = "";           //8
    private String acctDt   = "";           //6
    private String hBankCd  = "";           //3
    private String hbFiller = "0000";       //4
    private String mAcctNo  = "";           //16
    private String rndNo    = "";           //10
    private String hFiller  = "";           //84

    private String dRecTp   = "R";      //1
    private String dDataSeq = "";       //8
    private String dFacCd   = "";       //10
    private String dBankCd  = "";       //3
    private String dbFiller = "0000";   //4
    private String dAcctNo  = "";       //16
    private String reqAmt   = "";       //13
    private String socId    = "";       //13
    private String result   = "";       //1
    private String rtnCd    = "";       //4
    private String bankBook = "";       //16
    private String amtTp    = "";       //2
    private String napbuNo  = "";       //10
    private String nnFiller = "";       //10
    private String tmpInfo  = "";       //5
    private String billTp   = "";       //1
    private String tellNo   = "";       //12
    private String dFiller  = "";       //21

    private String tRecTp       = "T";          //1
    private String tDataSeq     = "99999999";   //8
    private String tFacCd       = "";           //10
    private String tFileNm      = "";           //8
    private String dataCnt      = "";           //8
    private String reqCntAll    = "";           //8
    private String reqAmtAll    = "";           //13
    private String reqCntPart   = "";           //8
    private String reqAmtPart   = "";           //13
    private String tFiller      = "";           //63
    private String macId        = "";           //10

    public FfmBillAg() {}
    public FfmBillAg(String facCd, String acctDt, String mAcctNo, String rndNo, int dSeq, String bankCd, String dAcctNo, long reqAmt, String socId, String bankBook, String amtTp, String napbuNo, String tmpInfo) {
        this.hFacCd = facCd;
        this.acctDt = acctDt.length()==8 ? acctDt.substring(2) : acctDt;
        this.mAcctNo = mAcctNo;
        this.rndNo = rndNo;
        this.dDataSeq = String.format("%08d", dSeq);
        this.dFacCd = facCd;
        try{
            this.dBankCd = String.format("%03d", Integer.parseInt(bankCd));
        } catch (NumberFormatException e){this.dBankCd = bankCd;}
        this.dAcctNo = dAcctNo;
        this.reqAmt = String.format("%013d", reqAmt);
        this.socId = socId;
        this.bankBook = bankBook;
        this.amtTp = amtTp;
        this.napbuNo = napbuNo;
        this.tmpInfo = tmpInfo;
        this.tFacCd = facCd;
    }

    public FfmBillAg(byte[] dataRec, byte[] headRec) {
        hRecTp      = new String(headRec, 0, 1);
        hDataSeq    = new String(headRec, 1, 8);
        hFacCd      = new String(headRec, 1+8, 10);
        hFileNm     = new String(headRec, 1+8+10, 8);
        acctDt      = new String(headRec, 1+8+10+8, 6);
        hBankCd     = new String(headRec, 1+8+10+8+6, 3);
        hbFiller    = new String(headRec, 1+8+10+8+6+3, 4);
        mAcctNo     = new String(headRec, 1+8+10+8+6+3+4, 16);
        rndNo       = new String(headRec, 1+8+10+8+6+3+4+16, 10);
        hFiller     = new String(headRec, 1+8+10+8+6+3+4+16+10, 84);

        dRecTp      = new String(dataRec, 0, 1);
        dDataSeq    = new String(dataRec, 1, 8);
        dFacCd      = new String(dataRec, 1+8, 10);
        dBankCd     = new String(dataRec, 1+8+10, 3);
        dbFiller    = new String(dataRec, 1+8+10+3, 4);
        dAcctNo     = new String(dataRec, 1+8+10+3+4, 16);
        reqAmt      = new String(dataRec, 1+8+10+3+4+16, 13);
        socId       = new String(dataRec, 1+8+10+3+4+16+13, 13);
        result      = new String(dataRec, 1+8+10+3+4+16+13+13, 1);
        rtnCd       = new String(dataRec, 1+8+10+3+4+16+13+13+1, 4);
        try {
            bankBook    = new String(dataRec, 1+8+10+3+4+16+13+13+1+4, 16, encodeTp);
            tmpInfo     = new String(dataRec, 1+8+10+3+4+16+13+13+1+4+16+2+10+10, 5, encodeTp);
        } catch (UnsupportedEncodingException e) {}
        amtTp       = new String(dataRec, 1+8+10+3+4+16+13+13+1+4+16, 2);
        napbuNo     = new String(dataRec, 1+8+10+3+4+16+13+13+1+4+16+2, 10);
        nnFiller    = new String(dataRec, 1+8+10+3+4+16+13+13+1+4+16+2+10, 10);
        //tmpInfo     = new String(dataRec, 1+8+10+3+4+16+13+13+1+4+16+2+10+10, 5);
        billTp      = new String(dataRec, 1+8+10+3+4+16+13+13+1+4+16+2+10+10+5, 1);
        tellNo      = new String(dataRec, 1+8+10+3+4+16+13+13+1+4+16+2+10+10+5+1, 12);
        dFiller     = new String(dataRec, 1+8+10+3+4+16+13+13+1+4+16+2+10+10+5+1+12, 21);
    }

    public byte[] getHead(){
        String recBuf = String.format("%-1s", hRecTp)+String.format("%-8s", hDataSeq)+String.format("%-10s", hFacCd)+String.format("%-8s", hFileNm)+
                String.format("%-1s", acctDt)+String.format("%-3s", hBankCd)+String.format("%-4s", hbFiller)+String.format("%-16s", mAcctNo)+
                String.format("%-10s", rndNo)+String.format("%-84s", hFiller)+"\r\n";

        return recBuf.getBytes();
    }

    public byte[] getData(){
        byte[] rtnBuf = null;
        String recBuf = null;
        try {
            recBuf = String.format("%-1s", dRecTp)+String.format("%-8s", dDataSeq)+String.format("%-10s", dFacCd)+String.format("%-3s", dBankCd)+
                    String.format("%-4s", dbFiller)+String.format("%-16s", dAcctNo)+String.format("%-13s", reqAmt)+String.format("%-13s", socId)+
                    String.format("%-1s", result)+String.format("%-4s", rtnCd)+Util.byteFormat(bankBook, 16, encodeTp)+String.format("%-2s", amtTp)+
                    String.format("%-10s", napbuNo)+String.format("%-10s", nnFiller)+Util.byteFormat(tmpInfo, 5, encodeTp)+
                    String.format("%-1s", billTp)+String.format("%-12s", tellNo)+String.format("%-21s", dFiller)+"\r\n";
            rtnBuf = recBuf.getBytes(encodeTp);
        } catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public byte[] getEnd(int tReqCnt, long tReqAmt){
        this.dataCnt  = String.format("%08d", tReqCnt);
        this.reqCntAll  = String.format("%08d", tReqCnt);
        this.reqAmtAll  = String.format("%013d", tReqAmt);
        String recBuf = String.format("%-1s", tRecTp)+String.format("%-8s", tDataSeq)+String.format("%-10s", tFacCd)+String.format("%-8s", tFileNm)+String.format("%-8s", dataCnt)+
                String.format("%-8s", reqCntAll)+String.format("%-13s", reqAmtAll)+String.format("%-8s", reqCntPart)+String.format("%-13s", reqAmtPart)+
                String.format("%-63s", tFiller)+String.format("%-10s", macId)+"\r\n";

        return recBuf.getBytes();
    }

    public boolean makeFile(List<DtoBill> dtoBillList, String desFilePath){
        FileOutputStream fout = null;
        FfmBillAg billAg;
        try {
            fout = new FileOutputStream(desFilePath);
            int dataSeq = 0;
            long ttReqAmt = 0L;
            for (DtoBill dtoBill : dtoBillList) {
                dataSeq++;
                billAg = new FfmBillAg(dtoBill.getFacCd(), dtoBill.getAcctDt(), dtoBill.gethAcctNo(), dtoBill.getRoundNo(), dataSeq, dtoBill.getdBankCd(),
                        dtoBill.getdAcctNo(), dtoBill.getReqAmt(), "", dtoBill.getBankBook(), "", dtoBill.getNapbuNo(), dtoBill.getTmpInfo());
                ttReqAmt += dtoBill.getReqAmt();
                //make head
                if(dataSeq==1) fout.write(billAg.getHead());
                //make data
                fout.write(billAg.getData());
                //make end
                if(dataSeq == dtoBillList.size()) fout.write(billAg.getEnd(dataSeq, ttReqAmt));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[makeFile] "+ e);
            return false;
        } finally {
            if(fout!=null) try {fout.close();} catch (IOException ignored) {}
        }

        return true;
    }

    public List<DtoBill> makeDtoList(String srcFilePath){
        FileInputStream fin = null;
        FfmBillAg billAg;
        byte[] headBuf=null, dataBuf;
        List<DtoBill> billList = new ArrayList<>();
        try {
            fin = new FileInputStream(srcFilePath);
            while (true){
                dataBuf = Util.readLineByte(fin);
                if(dataBuf.length==0)	break;

                if(new String(dataBuf, 0, 1).equals("H")){
                    headBuf = dataBuf;
                    log.debug("[makeDtoList] headBuf=["+new String(headBuf)+"]("+headBuf.length+")");
                }
                else if(new String(dataBuf, 0, 1).equals("R")) {
                    billAg = new FfmBillAg(dataBuf, headBuf);
                    billList.add(new DtoBill(billAg.gethFacCd().trim(), billAg.gethBankCd().trim(), billAg.getAcctDt().trim(), billAg.getmAcctNo().trim(),
                            "", "", "", billAg.getRndNo().trim(), billAg.getdBankCd().trim(), billAg.getdAcctNo().trim(),
                            0L, billAg.getResult().trim(), billAg.getRtnCd().trim(), Long.parseLong(billAg.getReqAmt()), billAg.getNapbuNo().trim(),
                            "", billAg.getBankBook().trim(), "", billAg.getTmpInfo().trim()));
                }
            }//while end.
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[makeDtoList] "+e);
            return null;
        } finally {
            if(fin!=null) try {fin.close();} catch (IOException ignored) {}
        }

        return billList;
    }

    public String gethRecTp() {
        return hRecTp;
    }

    public void sethRecTp(String hRecTp) {
        this.hRecTp = hRecTp;
    }

    public String gethDataSeq() {
        return hDataSeq;
    }

    public void sethDataSeq(String hDataSeq) {
        this.hDataSeq = hDataSeq;
    }

    public String gethFacCd() {
        return hFacCd;
    }

    public void sethFacCd(String hFacCd) {
        this.hFacCd = hFacCd;
    }

    public String gethFileNm() {
        return hFileNm;
    }

    public void sethFileNm(String hFileNm) {
        this.hFileNm = hFileNm;
    }

    public String getAcctDt() {
        return acctDt;
    }

    public void setAcctDt(String acctDt) {
        this.acctDt = acctDt;
    }

    public String gethBankCd() {
        return hBankCd;
    }

    public void sethBankCd(String hBankCd) {
        this.hBankCd = hBankCd;
    }

    public String getHbFiller() {
        return hbFiller;
    }

    public void setHbFiller(String hbFiller) {
        this.hbFiller = hbFiller;
    }

    public String getmAcctNo() {
        return mAcctNo;
    }

    public void setmAcctNo(String mAcctNo) {
        this.mAcctNo = mAcctNo;
    }

    public String getRndNo() {
        return rndNo;
    }

    public void setRndNo(String rndNo) {
        this.rndNo = rndNo;
    }

    public String gethFiller() {
        return hFiller;
    }

    public void sethFiller(String hFiller) {
        this.hFiller = hFiller;
    }

    public String getdRecTp() {
        return dRecTp;
    }

    public void setdRecTp(String dRecTp) {
        this.dRecTp = dRecTp;
    }

    public String getdDataSeq() {
        return dDataSeq;
    }

    public void setdDataSeq(String dDataSeq) {
        this.dDataSeq = dDataSeq;
    }

    public String getdFacCd() {
        return dFacCd;
    }

    public void setdFacCd(String dFacCd) {
        this.dFacCd = dFacCd;
    }

    public String getdBankCd() {
        return dBankCd;
    }

    public void setdBankCd(String dBankCd) {
        this.dBankCd = dBankCd;
    }

    public String getDbFiller() {
        return dbFiller;
    }

    public void setDbFiller(String dbFiller) {
        this.dbFiller = dbFiller;
    }

    public String getdAcctNo() {
        return dAcctNo;
    }

    public void setdAcctNo(String dAcctNo) {
        this.dAcctNo = dAcctNo;
    }

    public String getReqAmt() {
        return reqAmt;
    }

    public void setReqAmt(String reqAmt) {
        this.reqAmt = reqAmt;
    }

    public String getSocId() {
        return socId;
    }

    public void setSocId(String socId) {
        this.socId = socId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRtnCd() {
        return rtnCd;
    }

    public void setRtnCd(String rtnCd) {
        this.rtnCd = rtnCd;
    }

    public String getBankBook() {
        return bankBook;
    }

    public void setBankBook(String bankBook) {
        this.bankBook = bankBook;
    }

    public String getAmtTp() {
        return amtTp;
    }

    public void setAmtTp(String amtTp) {
        this.amtTp = amtTp;
    }

    public String getNapbuNo() {
        return napbuNo;
    }

    public void setNapbuNo(String napbuNo) {
        this.napbuNo = napbuNo;
    }

    public String getNnFiller() {
        return nnFiller;
    }

    public void setNnFiller(String nnFiller) {
        this.nnFiller = nnFiller;
    }

    public String getTmpInfo() {
        return tmpInfo;
    }

    public void setTmpInfo(String tmpInfo) {
        this.tmpInfo = tmpInfo;
    }

    public String getBillTp() {
        return billTp;
    }

    public void setBillTp(String billTp) {
        this.billTp = billTp;
    }

    public String getTellNo() {
        return tellNo;
    }

    public void setTellNo(String tellNo) {
        this.tellNo = tellNo;
    }

    public String getdFiller() {
        return dFiller;
    }

    public void setdFiller(String dFiller) {
        this.dFiller = dFiller;
    }

    public String gettRecTp() {
        return tRecTp;
    }

    public void settRecTp(String tRecTp) {
        this.tRecTp = tRecTp;
    }

    public String gettDataSeq() {
        return tDataSeq;
    }

    public void settDataSeq(String tDataSeq) {
        this.tDataSeq = tDataSeq;
    }

    public String gettFacCd() {
        return tFacCd;
    }

    public void settFacCd(String tFacCd) {
        this.tFacCd = tFacCd;
    }

    public String gettFileNm() {
        return tFileNm;
    }

    public void settFileNm(String tFileNm) {
        this.tFileNm = tFileNm;
    }

    public String getDataCnt() {
        return dataCnt;
    }

    public void setDataCnt(String dataCnt) {
        this.dataCnt = dataCnt;
    }

    public String getReqCntAll() {
        return reqCntAll;
    }

    public void setReqCntAll(String reqCntAll) {
        this.reqCntAll = reqCntAll;
    }

    public String getReqAmtAll() {
        return reqAmtAll;
    }

    public void setReqAmtAll(String reqAmtAll) {
        this.reqAmtAll = reqAmtAll;
    }

    public String getReqCntPart() {
        return reqCntPart;
    }

    public void setReqCntPart(String reqCntPart) {
        this.reqCntPart = reqCntPart;
    }

    public String getReqAmtPart() {
        return reqAmtPart;
    }

    public void setReqAmtPart(String reqAmtPart) {
        this.reqAmtPart = reqAmtPart;
    }

    public String gettFiller() {
        return tFiller;
    }

    public void settFiller(String tFiller) {
        this.tFiller = tFiller;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }
}
