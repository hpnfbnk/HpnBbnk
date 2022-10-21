package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.dto.DtoBill;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FfmBillCom {
    private static final Log log = LogFactory.getLog(FfmBillCom.class);
    private final String encodeTp = "euc-kr";

    private String hRecTp   = "S";  //1
    private String jobTp    = "B2"; //2
    private String facCd    = "";   //10
    private String hBankCd  = "";   //3
    private String acctSDt  = "";   //8
    private String acctEDt  = "";   //8
    private String hAcctNo  = "";   //16
    private String pwd      = "";   //10
    private String repFacCd = "";   //10
    private String copNo    = "";   //6
    private String amtTp    = "";   //1
    private String roundNo  = "";   //10
    private String hFiller  = "";   //35

    private String dRecTp   = "D";  //1
    private String dataSeq  = "";   //7
    private String dBankCd  = "";   //3
    private String dAcctNo  = "";   //16
    private String reqAmt   = "0";  //13
    private String result   = "";   //1
    private String errCd    = "";   //4
    private String failAmt  = "0";  //13
    private String napbuNo  = "";   //20
    private String itemCd   = "";   //8
    private String bankBook = "";   //14
    private String mBankCd  = "";   //3
    private String tmpInfo  = "";   //15
    private String dFiller  = "";   //2

    private String tRecTp   = "E";  //1
    private String tRecCnt  = "";   //7
    private String tReqCnt  = "";   //7
    private String tReqAmt  = "";   //15
    private String tFineCnt = "";   //7
    private String tFineAmt = "";   //15
    private String tPartCnt = "";   //7
    private String tPartAmt = "";   //15
    private String tFailCnt = "";   //7
    private String tFailAmt = "";   //15
    private String tFiller  = "";   //24

    public FfmBillCom() {}
    public FfmBillCom(String facCd, String hBankCd, String acctSDt, String hAcctNo, String pwd, String repFacCd, String copNo, String roundNo, int dSeq, String dBankCd, String dAcctNo, long reqAmt, String napbuNo, String itemCd, String bankBook, String mBankCd, String tmpInfo) {
        this.facCd = facCd;
        try{
            this.hBankCd = String.format("%03d", Integer.parseInt(hBankCd));
        } catch (NumberFormatException e){this.hBankCd = hBankCd;}
        this.acctSDt = acctSDt;
        this.acctEDt = acctSDt;
        this.hAcctNo = hAcctNo;
        this.pwd = pwd;
        this.repFacCd = repFacCd;
        this.copNo = copNo;
        this.roundNo = roundNo;
        this.dataSeq = String.format("%07d", dSeq);
        try{
            this.dBankCd = String.format("%03d", Integer.parseInt(dBankCd));
        } catch (NumberFormatException e){this.dBankCd = dBankCd;}
        this.dAcctNo = dAcctNo;
        this.reqAmt = String.format("%d", reqAmt);
        this.napbuNo = napbuNo;
        this.itemCd = itemCd;
        this.bankBook = bankBook;
        try{
            this.mBankCd = String.format("%03d", Integer.parseInt(mBankCd));
        } catch (NumberFormatException e){this.mBankCd = mBankCd;}
        this.tmpInfo = tmpInfo;
    }

    public FfmBillCom(byte[] dataRec, byte[] headRec) {
        hRecTp      = new String(headRec, 0, 1);
        jobTp       = new String(headRec, 1, 2);
        facCd       = new String(headRec, 1+2, 10);
        hBankCd     = new String(headRec, 1+2+10, 3);
        acctSDt     = new String(headRec, 1+2+10+3, 8);
        acctEDt     = new String(headRec, 1+2+10+3+8, 8);
        hAcctNo     = new String(headRec, 1+2+10+3+8+8, 16);
        pwd         = new String(headRec, 1+2+10+3+8+8+16, 10);
        repFacCd    = new String(headRec, 1+2+10+3+8+8+16+10, 10);
        copNo       = new String(headRec, 1+2+10+3+8+8+16+10+10, 6);
        amtTp       = new String(headRec, 1+2+10+3+8+8+16+10+10+6, 1);
        hFiller     = new String(headRec, 1+2+10+3+8+8+16+10+10+6+1+10, 35);

        dRecTp      = new String(dataRec, 0, 1);
        dataSeq     = new String(dataRec, 1, 7);
        dBankCd     = new String(dataRec, 1+7, 3);
        dAcctNo     = new String(dataRec, 1+7+3, 16);
        reqAmt      = new String(dataRec, 1+7+3+16, 13);
        result      = new String(dataRec, 1+7+3+16+13, 1);
        errCd       = new String(dataRec, 1+7+3+16+13+1, 4);
        failAmt     = new String(dataRec, 1+7+3+16+13+1+4, 13);
        napbuNo     = new String(dataRec, 1+7+3+16+13+1+4+13, 20);
        itemCd      = new String(dataRec, 1+7+3+16+13+1+4+13+20, 8);
        try {
            roundNo     = new String(headRec, 1+2+10+3+8+8+16+10+10+6+1, 10);
            bankBook    = new String(dataRec, 1+7+3+16+13+1+4+13+20+8, 14, encodeTp);
            tmpInfo     = new String(dataRec, 1+7+3+16+13+1+4+13+20+8+14+3, 15, encodeTp);
        } catch (UnsupportedEncodingException ignored) {}
        mBankCd     = new String(dataRec, 1+7+3+16+13+1+4+13+20+8+14, 3);
        dFiller     = new String(dataRec, 1+7+3+16+13+1+4+13+20+8+14+3+15, 2);
    }

    public byte[] getHead(){
        String recBuf;
        byte[] rtnBuf = null;
        try {
            recBuf = String.format("%-1s", hRecTp)+String.format("%-2s", jobTp)+String.format("%-10s", facCd)+
                    String.format("%-3s", hBankCd)+String.format("%-8s", acctSDt)+String.format("%-8s", acctEDt)+String.format("%-16s", hAcctNo)+
                    String.format("%-10s", pwd)+String.format("%-10s", repFacCd)+String.format("%-6s", copNo)+String.format("%-1s", amtTp)+
                    Util.byteFormat(roundNo, 10, encodeTp)+String.format("%-35s", hFiller)+"\r\n";
            rtnBuf = recBuf.getBytes(encodeTp);
        } catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public byte[] getData(){
        String recBuf;
        byte[] rtnBuf = null;
        try {
            recBuf = String.format("%-1s", dRecTp)+String.format("%-7s", dataSeq)+String.format("%-3s", dBankCd)+ String.format("%-16s", dAcctNo)+
                    String.format("%013d", Long.parseLong("0"+reqAmt))+String.format("%-1s", result)+String.format("%-4s", errCd)+
                    String.format("%013d", Long.parseLong("0"+failAmt))+String.format("%-20s", napbuNo)+String.format("%-8s", itemCd)+
                    Util.byteFormat(bankBook, 14, encodeTp)+String.format("%-3s", mBankCd)+Util.byteFormat(tmpInfo, 15, encodeTp)+
                    String.format("%-2s", dFiller)+"\r\n";
            rtnBuf = recBuf.getBytes(encodeTp);
        } catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public byte[] getEnd(int tReqCnt, long tReqAmt){
        this.tRecCnt  = String.format("%07d", tReqCnt+2);
        this.tReqCnt  = String.format("%07d", tReqCnt);
        this.tReqAmt  = String.format("%015d", tReqAmt);
        this.tFineCnt  = String.format("%07d", 0);
        this.tFineAmt  = String.format("%015d", 0);
        this.tPartCnt  = String.format("%07d", 0);
        this.tPartAmt  = String.format("%015d", 0);
        this.tFailCnt  = String.format("%07d", 0);
        this.tFailAmt  = String.format("%015d", 0);
        String recBuf = String.format("%-1s", tRecTp)+this.tRecCnt+this.tReqCnt+this.tReqAmt+ this.tFineCnt+ this.tFineAmt+
                this.tPartCnt+ this.tPartAmt+ this.tFailCnt+ this.tFailAmt+String.format("%-24s", tFiller)+"\r\n";
        byte[] rtnBuf = new byte[0];
        try {rtnBuf = recBuf.getBytes(encodeTp);} catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public boolean makeFile(List<DtoBill> dtoBillList, String desFilePath){
        FileOutputStream fout = null;
        FfmBillCom billCom;
        try {
            fout = new FileOutputStream(desFilePath);
            int dataSeq = 0;
            long ttReqAmt = 0L;
            for (DtoBill dtoBill : dtoBillList) {
                dataSeq++;
                billCom = new FfmBillCom(dtoBill.getFacCd(), dtoBill.gethBankCd(), dtoBill.getAcctDt(), dtoBill.gethAcctNo(), dtoBill.getPwd(),
                        dtoBill.getRepFacCd(), dtoBill.getCopNo(), dtoBill.getRoundNo(), dataSeq, dtoBill.getdBankCd(), dtoBill.getdAcctNo(),
                        dtoBill.getReqAmt(), dtoBill.getNapbuNo(), dtoBill.getItemCd(), dtoBill.getBankBook(), dtoBill.getmBankCd(), dtoBill.getTmpInfo());
                ttReqAmt += dtoBill.getReqAmt();
                //make head
                if(dataSeq==1) fout.write(billCom.getHead());
                //make data
                fout.write(billCom.getData());
                //make end
                if(dataSeq == dtoBillList.size()) fout.write(billCom.getEnd(dataSeq, ttReqAmt));
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
        FfmBillCom billCom;
        byte[] headBuf=null, dataBuf;
        List<DtoBill> billList = new ArrayList<>();
        try {
            fin = new FileInputStream(srcFilePath);
            while (true){
                dataBuf = Util.readLineByte(fin);
                if(dataBuf.length==0)	break;

                if(new String(dataBuf, 0, 1).equals("S")){
                    headBuf = dataBuf;
                    log.debug("[makeDtoList] headBuf=["+new String(headBuf)+"]("+headBuf.length+")");
                }
                else if(new String(dataBuf, 0, 1).equals("D")) {
                    billCom = new FfmBillCom(dataBuf, headBuf);
                    billList.add(new DtoBill(billCom.getFacCd().trim(), billCom.gethBankCd().trim(), billCom.getAcctSDt().trim(), billCom.gethAcctNo().trim(),
                            billCom.getPwd().trim(), billCom.getRepFacCd().trim(), billCom.getCopNo().trim(), billCom.getRoundNo().trim(),
                            billCom.getdBankCd().trim(), billCom.getdAcctNo().trim(), Long.parseLong(billCom.getReqAmt()), billCom.getResult().trim(),
                            billCom.getErrCd().trim(), Long.parseLong(billCom.getFailAmt()), billCom.getNapbuNo().trim(), billCom.getItemCd().trim(),
                            billCom.getBankBook().trim(), billCom.getmBankCd().trim(), billCom.getTmpInfo().trim()));
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

    public String getJobTp() {
        return jobTp;
    }

    public void setJobTp(String jobTp) {
        this.jobTp = jobTp;
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
        try{
            this.hBankCd = String.format("%03d", Integer.parseInt(hBankCd));
        } catch (NumberFormatException e){this.hBankCd = hBankCd;}
    }

    public String getAcctSDt() {
        return acctSDt;
    }

    public void setAcctSDt(String acctSDt) {
        this.acctSDt = acctSDt;
    }

    public String getAcctEDt() {
        return acctEDt;
    }

    public void setAcctEDt(String acctEDt) {
        this.acctEDt = acctEDt;
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

    public String getDataSeq() {
        return dataSeq;
    }

    public void setDataSeq(String dataSeq) {
        this.dataSeq = dataSeq;
    }

    public String getdBankCd() {
        return dBankCd;
    }

    public void setdBankCd(String dBankCd) {
        try{
            this.dBankCd = String.format("%03d", Integer.parseInt(dBankCd));
        } catch (NumberFormatException e){this.dBankCd = dBankCd;}
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

    public String getFailAmt() {
        return failAmt;
    }

    public void setFailAmt(String failAmt) {
        this.failAmt = failAmt;
    }

    public String getNapbuNo() {
        return napbuNo;
    }

    public void setNapbuNo(String napbuNo) {
        this.napbuNo = napbuNo;
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
        try{
            this.mBankCd = String.format("%03d", Integer.parseInt(mBankCd));
        } catch (NumberFormatException e){this.mBankCd = mBankCd;}
    }

    public String getTmpInfo() {
        return tmpInfo;
    }

    public void setTmpInfo(String tmpInfo) {
        this.tmpInfo = tmpInfo;
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

    public String gettRecCnt() {
        return tRecCnt;
    }

    public void settRecCnt(String tRecCnt) {
        this.tRecCnt = tRecCnt;
    }

    public String gettReqCnt() {
        return tReqCnt;
    }

    public void settReqCnt(String tReqCnt) {
        this.tReqCnt = tReqCnt;
    }

    public String gettReqAmt() {
        return tReqAmt;
    }

    public void settReqAmt(String tReqAmt) {
        this.tReqAmt = tReqAmt;
    }

    public String gettFineCnt() {
        return tFineCnt;
    }

    public void settFineCnt(String tFineCnt) {
        this.tFineCnt = tFineCnt;
    }

    public String gettFineAmt() {
        return tFineAmt;
    }

    public void settFineAmt(String tFineAmt) {
        this.tFineAmt = tFineAmt;
    }

    public String gettPartCnt() {
        return tPartCnt;
    }

    public void settPartCnt(String tPartCnt) {
        this.tPartCnt = tPartCnt;
    }

    public String gettPartAmt() {
        return tPartAmt;
    }

    public void settPartAmt(String tPartAmt) {
        this.tPartAmt = tPartAmt;
    }

    public String gettFailCnt() {
        return tFailCnt;
    }

    public void settFailCnt(String tFailCnt) {
        this.tFailCnt = tFailCnt;
    }

    public String gettFailAmt() {
        return tFailAmt;
    }

    public void settFailAmt(String tFailAmt) {
        this.tFailAmt = tFailAmt;
    }

    public String gettFiller() {
        return tFiller;
    }

    public void settFiller(String tFiller) {
        this.tFiller = tFiller;
    }
}
