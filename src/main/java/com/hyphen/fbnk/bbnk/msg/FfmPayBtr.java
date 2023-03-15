package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.dto.DtoPay;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

//배치성-실시간 송금이체
public class FfmPayBtr {
    private static final Log log = LogFactory.getLog(FfmPayBtr.class);
    private final String encodeTp = "euc-kr";

    private String hRecTp       = "S";  //1
    private String repfacCd     = "";   //10
    private String sendDt       = "";   //8
    private String repMBankCd   = "";   //3
    private String repMAcctNo   = "";   //16
    private String repMPwd      = "";   //8
    private String repMBankBook = "";   //20
    private String roundNo      = "";   //20
    private String hFiller      = "";   //114

    private String dRecTp       = "D";  //1
    private String msgNo        = "";   //6
    private String cBankCd      = "";   //3
    private String cAcctNo      = "";   //16
    private String reqAmt       = "0";  //13
    private String mSign        = "";   //1
    private String remnAmt      = "0";  //13
    private String fee          = "0";  //9
    private String result       = "";   //1
    private String errCd        = "";   //4
    private String cmsCd        = "";   //16
    private String scramCd      = "";   //6
    private String salTp        = "";   //1
    private String facCd        = "";   //10
    private String mBankcd      = "";   //3
    private String mAcctNo      = "";   //16
    private String mPwd         = "";   //8
    private String mBankBook    = "";   //20
    private String cBankBook    = "";   //20
    private String dFiller      = "";   //33

    private String tRecTp   = "E";  //1
    private String tReqCnt  = "0";   //7
    private String tReqAmt  = "0";   //13
    private String tFineCnt = "0";   //7
    private String tFineAmt = "0";   //13
    private String tFailCnt = "0";   //7
    private String tFailAmt = "0";   //13
    private String tFiller  = "";   //139

    public FfmPayBtr() {}
    public FfmPayBtr(String roundNo, int msgNo, String cBankCd, String cAcctNo, long reqAmt, String cmsCd, String salTp, String facCd,
                     String mBankcd, String mAcctNo, String mPwd, String mBankBook, String cBankBook, String tmpInfo) throws Exception {
        this.sendDt = Util.getCurDtTm().substring(0, 8);
        this.roundNo = roundNo;
        if(msgNo > 700000)  throw new Exception("abnormal msgNo : "+msgNo);
        this.msgNo = String.format("%06d", msgNo);
        try{
            this.cBankCd = String.format("%03d", Integer.parseInt(cBankCd));
        } catch (NumberFormatException e){this.cBankCd = cBankCd;}
        this.cAcctNo = cAcctNo;
        this.reqAmt = String.format("%d", reqAmt);
        this.cmsCd = cmsCd;
        this.salTp = salTp;
        this.facCd = facCd;
        try{
            this.mBankcd = String.format("%03d", Integer.parseInt(mBankcd));
        } catch (NumberFormatException e){this.mBankcd = mBankcd;}
        this.mAcctNo = mAcctNo;
        this.mPwd = mPwd;
        this.mBankBook = mBankBook;
        this.cBankBook = cBankBook;
        this.dFiller = tmpInfo;
    }

    public FfmPayBtr(byte[] dataRec, byte[] headRec) {
        hRecTp       = new String(headRec, 0, 1);
        repfacCd     = new String(headRec, 1, 10);
        sendDt       = new String(headRec, 1+10, 8);
        repMBankCd   = new String(headRec, 1+10+8, 3);
        repMAcctNo   = new String(headRec, 1+10+8+2, 16);
        repMPwd      = new String(headRec, 1+10+8+2+16, 8);
        try {
            repMBankBook = new String(headRec, 1+10+8+2+16+8, 20, encodeTp);
            roundNo      = new String(headRec, 1+10+8+2+16+8+20, 20, encodeTp);
        } catch (UnsupportedEncodingException ignored) {}
        hFiller      = new String(headRec, 1+10+8+2+16+8+20+20, 114);

        dRecTp       = new String(dataRec, 0, 1);
        msgNo        = new String(dataRec, 1, 6);
        cBankCd      = new String(dataRec, 1+6, 3);
        cAcctNo      = new String(dataRec, 1+6+3, 16);
        reqAmt       = new String(dataRec, 1+6+3+16, 13);
        mSign        = new String(dataRec, 1+6+3+16+13, 1);
        remnAmt      = new String(dataRec, 1+6+3+16+13+1, 13);
        fee          = new String(dataRec, 1+6+3+16+13+1+13, 9);
        result       = new String(dataRec, 1+6+3+16+13+1+13+9, 1);
        errCd        = new String(dataRec, 1+6+3+16+13+1+13+9+1, 4);
        cmsCd        = new String(dataRec, 1+6+3+16+13+1+13+9+1+4, 16);
        scramCd      = new String(dataRec, 1+6+3+16+13+1+13+9+1+4+16, 6);
        salTp        = new String(dataRec, 1+6+3+16+13+1+13+9+1+4+16+6, 1);
        facCd        = new String(dataRec, 1+6+3+16+13+1+13+9+1+4+16+6+1, 10);
        mBankcd      = new String(dataRec, 1+6+3+16+13+1+13+9+1+4+16+6+1+10, 3);
        mAcctNo      = new String(dataRec, 1+6+3+16+13+1+13+9+1+4+16+6+1+10+3, 16);
        mPwd         = new String(dataRec, 1+6+3+16+13+1+13+9+1+4+16+6+1+10+3+16, 8);
        try {
            mBankBook    = new String(dataRec, 1+6+3+16+13+1+13+9+1+4+16+6+1+10+3+16+8, 20, encodeTp);
            cBankBook    = new String(dataRec, 1+6+3+16+13+1+13+9+1+4+16+6+1+10+3+16+8+20, 20, encodeTp);
            dFiller      = new String(dataRec, 1+6+3+16+13+1+13+9+1+4+16+6+1+10+3+16+8+20+20, 33, encodeTp);
        } catch (UnsupportedEncodingException ignored) {}
    }

    public byte[] getHead(){
        String recBuf;
        byte[] rtnBuf = null;
        try {
            recBuf = String.format("%-1s", hRecTp)+String.format("%-10s", repfacCd)+String.format("%-8s", sendDt)+String.format("%-3s", repMBankCd)+
                    String.format("%-16s", repMAcctNo)+String.format("%-8s", repMPwd)+Util.byteFormat(repMBankBook, 20, encodeTp)+
                    Util.byteFormat(roundNo, 20, encodeTp)+String.format("%-114s", hFiller)+"\r\n";
            rtnBuf = recBuf.getBytes(encodeTp);
        } catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public byte[] getData(){
        String recBuf;
        byte[] rtnBuf = null;
        try {
            recBuf = String.format("%-1s", dRecTp)+String.format("%-6s", msgNo)+String.format("%-3s", cBankCd)+String.format("%-16s", cAcctNo)+
                    String.format("%013d", Long.parseLong("0"+reqAmt))+String.format("%-1s", mSign)+String.format("%013d", Long.parseLong("0"+remnAmt))+
                    String.format("%09d", Long.parseLong("0"+fee))+String.format("%-1s", result)+String.format("%-4s", errCd)+String.format("%-16s", cmsCd)+
                    String.format("%-6s", scramCd)+String.format("%-1s", salTp)+String.format("%-10s", facCd)+String.format("%-3s", mBankcd)+
                    String.format("%-16s", mAcctNo)+String.format("%-8s", mPwd)+Util.byteFormat(mBankBook, 20, encodeTp)+
                    Util.byteFormat(cBankBook, 20, encodeTp)+Util.byteFormat(dFiller, 33, encodeTp) +"\r\n";
            rtnBuf = recBuf.getBytes(encodeTp);
        } catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public byte[] getEnd(int tReqCnt, long tReqAmt){
        this.tReqCnt  = String.format("%07d", tReqCnt);
        this.tReqAmt  = String.format("%013d", tReqAmt);
        tFineCnt = String.format("%07d", 0);
        tFineAmt = String.format("%013d", 0);
        tFailCnt = String.format("%07d", 0);
        tFailAmt = String.format("%013d", 0);
        String recBuf = String.format("%-1s", tRecTp)+this.tReqCnt+this.tReqAmt+tFailCnt+tFineAmt+tFailCnt+tFailAmt+String.format("%-139s", tFiller)+"\r\n";

        return recBuf.getBytes();
    }

    public boolean makeFile(List<DtoPay> dtoPayList, String desFilePath){
        FileOutputStream fout = null;
        FfmPayBtr payBtr;
        try {
            fout = new FileOutputStream(desFilePath);
            int dataSeq = 0;
            long ttReqAmt = 0L;
            for (DtoPay dtoPay : dtoPayList) {
                dataSeq++;
                payBtr = new FfmPayBtr(dtoPay.getRoundNo(), dtoPay.getMsgNo(), dtoPay.getdBankCd(), dtoPay.getdAcctNo(), dtoPay.getReqAmt(), dtoPay.getCopNo(),
                        dtoPay.getAmtTp(), dtoPay.getFacCd(), dtoPay.gethBankCd(), dtoPay.gethAcctNo(), dtoPay.getPwd(), dtoPay.gethBankBook(), dtoPay.getBankBook(), dtoPay.getTmpInfo());
                ttReqAmt += dtoPay.getReqAmt();
                //make head
                if(dataSeq==1) fout.write(payBtr.getHead());
                //make data
                fout.write(payBtr.getData());
                //make end
                if(dataSeq== dtoPayList.size()) fout.write(payBtr.getEnd(dataSeq, ttReqAmt));
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

    public List<DtoPay> makeDtoList(String srcFilePath){
        FileInputStream fin = null;
        FfmPayBtr payBtr;
        byte[] headBuf=null, dataBuf;
        List<DtoPay> payList = new ArrayList<>();
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
                    payBtr = new FfmPayBtr(dataBuf, headBuf);
                    payList.add(new DtoPay(payBtr.getFacCd().trim(), payBtr.getmBankcd().trim(), payBtr.getSendDt().trim(), payBtr.getmAcctNo().trim(),
                            payBtr.getmPwd().trim(), "", payBtr.getCmsCd().trim(), payBtr.getSalTp().trim(), payBtr.getRoundNo().trim(), payBtr.getcBankCd().trim(),
                            payBtr.getcAcctNo().trim(), Long.parseLong(payBtr.getReqAmt()), payBtr.getResult().trim(), payBtr.getErrCd().trim(),
                            payBtr.getResult().equals("Y") ? 0L : Long.parseLong(payBtr.getReqAmt()),
                            "", payBtr.getcBankBook().trim(), "", payBtr.getdFiller().trim(), Integer.parseInt(payBtr.getMsgNo()), payBtr.getmBankBook().trim()));
                }
            }//while end.
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[makeDtoList] "+e);
            return null;
        } finally {
            if(fin!=null) try {fin.close();} catch (IOException ignored) {}
        }

        return payList;
    }

    public String gethRecTp() {
        return hRecTp;
    }

    public void sethRecTp(String hRecTp) {
        this.hRecTp = hRecTp;
    }

    public String getRepfacCd() {
        return repfacCd;
    }

    public void setRepfacCd(String repfacCd) {
        this.repfacCd = repfacCd;
    }

    public String getSendDt() {
        return sendDt;
    }

    public void setSendDt(String sendDt) {
        this.sendDt = sendDt;
    }

    public String getRepMBankCd() {
        return repMBankCd;
    }

    public void setRepMBankCd(String repMBankCd) {
        this.repMBankCd = repMBankCd;
    }

    public String getRepMAcctNo() {
        return repMAcctNo;
    }

    public void setRepMAcctNo(String repMAcctNo) {
        this.repMAcctNo = repMAcctNo;
    }

    public String getRepMPwd() {
        return repMPwd;
    }

    public void setRepMPwd(String repMPwd) {
        this.repMPwd = repMPwd;
    }

    public String getRepMBankBook() {
        return repMBankBook;
    }

    public void setRepMBankBook(String repMBankBook) {
        this.repMBankBook = repMBankBook;
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

    public String getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(String msgNo) {
        this.msgNo = msgNo;
    }

    public String getcBankCd() {
        return cBankCd;
    }

    public void setcBankCd(String cBankCd) {
        this.cBankCd = cBankCd;
    }

    public String getcAcctNo() {
        return cAcctNo;
    }

    public void setcAcctNo(String cAcctNo) {
        this.cAcctNo = cAcctNo;
    }

    public String getReqAmt() {
        return reqAmt;
    }

    public void setReqAmt(String reqAmt) {
        this.reqAmt = reqAmt;
    }

    public String getmSign() {
        return mSign;
    }

    public void setmSign(String mSign) {
        this.mSign = mSign;
    }

    public String getRemnAmt() {
        return remnAmt;
    }

    public void setRemnAmt(String remnAmt) {
        this.remnAmt = remnAmt;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
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

    public String getCmsCd() {
        return cmsCd;
    }

    public void setCmsCd(String cmsCd) {
        this.cmsCd = cmsCd;
    }

    public String getScramCd() {
        return scramCd;
    }

    public void setScramCd(String scramCd) {
        this.scramCd = scramCd;
    }

    public String getSalTp() {
        return salTp;
    }

    public void setSalTp(String salTp) {
        this.salTp = salTp;
    }

    public String getFacCd() {
        return facCd;
    }

    public void setFacCd(String facCd) {
        this.facCd = facCd;
    }

    public String getmBankcd() {
        return mBankcd;
    }

    public void setmBankcd(String mBankcd) {
        this.mBankcd = mBankcd;
    }

    public String getmAcctNo() {
        return mAcctNo;
    }

    public void setmAcctNo(String mAcctNo) {
        this.mAcctNo = mAcctNo;
    }

    public String getmPwd() {
        return mPwd;
    }

    public void setmPwd(String mPwd) {
        this.mPwd = mPwd;
    }

    public String getmBankBook() {
        return mBankBook;
    }

    public void setmBankBook(String mBankBook) {
        this.mBankBook = mBankBook;
    }

    public String getcBankBook() {
        return cBankBook;
    }

    public void setcBankBook(String cBankBook) {
        this.cBankBook = cBankBook;
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
