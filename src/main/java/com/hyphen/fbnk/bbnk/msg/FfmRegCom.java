package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.dto.DtoReg;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FfmRegCom {
    private static final Log log = LogFactory.getLog(FfmRegCom.class);
    private final String encodeTp = "euc-kr";

    private String hRecTp   = "S";  //1
    private String jobTp    = "A1"; //2
    private String facCd    = "";   //10
    private String hBankCd  = "";   //3
    private String sendDt   = "";   //8
    private String resultTp = "1";  //1
    private String regDt    = "";   //8
    private String repFacCd = "";   //10
    private String hExtra   = "";   //10
    private String hFiller  = "";   //57

    private String dRecTp   = "D";  //1
    private String dataSeq = "";    //7
    private String dBankCd  = "";   //3
    private String acctNo   = "";   //16
    private String regTp    = "";   //1
    private String autoDt   = "";   //2
    private String branchCd = "";   //6
    private String cRegDt   = "";   //8
    private String result   = "";   //1
    private String errCd    = "";   //4
    private String socidChk = "Y";  //1
    private String socId    = "";   //13
    private String napbuNo  = "";   //20
    private String tmpInfo  = "";   //14
    private String socIdTp  = "";   //1
    private String prfTp    = "";   //1
    private String mBankCd  = "";   //3
    private String dFiller  = "";   //8

    private String tRecTp   = "E";  //1
    private String ttRecCnt = "";   //7
    private String rReqCnt  = "";   //7
    private String dReqCnt  = "";   //7
    private String fineCnt  = "";   //7
    private String failCnt  = "";   //7
    private String rFineCnt = "";   //7
    private String dFineCnt = "";   //7
    private String tFiller  = "";   //60

    public FfmRegCom(){}
    public FfmRegCom(String facCd, String hBankCd, String hExtra, int dSeq, String dBankCd, String acctNo, String regTp, String socId, String napbuNo, String tmpInfo, String socIdTp, String prfTp, String mBankCd) {
        this.facCd = facCd;
        this.hBankCd = hBankCd;
        this.sendDt = Util.getCurDtTm().substring(0, 8);
        this.hExtra = hExtra;
        this.dataSeq = String.format("%07d", dSeq);
        this.dBankCd = dBankCd;
        this.acctNo = acctNo;
        this.regTp = regTp;
        this.socId = socId;
        this.napbuNo = napbuNo;
        this.tmpInfo = tmpInfo;
        this.socIdTp = socIdTp;
        this.prfTp = prfTp;
        this.mBankCd = mBankCd;
    }

    public FfmRegCom(byte[] dataRec, byte[] headRec){
        hRecTp   = new String(headRec, 0, 1);
        jobTp    = new String(headRec, 1, 2);
        facCd    = new String(headRec, 1+2, 10);
        hBankCd  = new String(headRec, 1+2+10, 3);
        sendDt   = new String(headRec, 1+2+10+3, 8);
        resultTp = new String(headRec, 1+2+10+3+8, 1);
        regDt    = new String(headRec, 1+2+10+3+8+1, 8);
        repFacCd = new String(headRec, 1+2+10+3+8+1+8, 10);
        hExtra   = new String(headRec, 1+2+10+3+8+1+8+10, 10);
        hFiller  = new String(headRec, 1+2+10+3+8+1+8+10+10, 57);

        dRecTp   = new String(dataRec, 0, 1);
        dataSeq  = new String(dataRec, 1, 7);
        dBankCd  = new String(dataRec, 1+7, 3);
        acctNo   = new String(dataRec, 1+7+3, 16);
        regTp    = new String(dataRec, 1+7+3+16, 1);
        autoDt   = new String(dataRec, 1+7+3+16+1, 2);
        branchCd = new String(dataRec, 1+7+3+16+1+2, 6);
        cRegDt   = new String(dataRec, 1+7+3+16+1+2+6, 8);
        result   = new String(dataRec, 1+7+3+16+1+2+6+8, 1);
        errCd    = new String(dataRec, 1+7+3+16+1+2+6+8+1, 4);
        socidChk = new String(dataRec, 1+7+3+16+1+2+6+8+1+4, 1);
        socId    = new String(dataRec, 1+7+3+16+1+2+6+8+1+4+1, 13);
        napbuNo  = new String(dataRec, 1+7+3+16+1+2+6+8+1+4+1+13, 20);
        try {
            tmpInfo  = new String(dataRec, 1+7+3+16+1+2+6+8+1+4+1+13+20, 14, encodeTp);
        } catch (UnsupportedEncodingException ignored) {}
        socIdTp  = new String(dataRec, 1+7+3+16+1+2+6+8+1+4+1+13+20+14, 1);
        prfTp    = new String(dataRec, 1+7+3+16+1+2+6+8+1+4+1+13+20+14+1, 1);
        mBankCd  = new String(dataRec, 1+7+3+16+1+2+6+8+1+4+1+13+20+14+1+1, 3);
        dFiller  = new String(dataRec, 1+7+3+16+1+2+6+8+1+4+1+13+20+14+1+1+3, 8);
    }

    public byte[] getHead(){
        String recBuf = String.format("%-1s", hRecTp)+String.format("%-2s", jobTp)+String.format("%-10s", facCd)+
                String.format("%-3s", hBankCd)+String.format("%-8s", sendDt)+String.format("%-1s", resultTp)+
                String.format("%-8s", regDt)+String.format("%-10s", repFacCd)+String.format("%-10s", hExtra)+
                String.format("%-57s", hFiller)+"\r\n";

        return recBuf.getBytes();
    }

    public byte[] getData(){
        String recBuf;
        byte[] rtnBuf = null;
        try {
            recBuf = String.format("%-1s", dRecTp)+String.format("%-7s", dataSeq)+String.format("%-3s", dBankCd)+
                    String.format("%-16s", acctNo)+String.format("%-1s", regTp)+String.format("%-2s", autoDt)+String.format("%-6s", branchCd)+
                    String.format("%-8s", cRegDt)+String.format("%-1s", result)+String.format("%-4s", errCd)+String.format("%-1s", socidChk)+
                    String.format("%-13s", socId)+String.format("%-20s", napbuNo)+Util.byteFormat(tmpInfo, 14, encodeTp)+
                    String.format("%-1s", socIdTp)+String.format("%-1s", prfTp)+String.format("%-3s", mBankCd)+
                    String.format("%-8s", dFiller)+"\r\n";
            rtnBuf = recBuf.getBytes(encodeTp);
        } catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public byte[] getEnd(int ttRecCnt, int regReqCnt, int disReqCnt){
        this.ttRecCnt = String.format("%07d", ttRecCnt);
        this.rReqCnt  = String.format("%07d", regReqCnt);
        this.dReqCnt  = String.format("%07d", disReqCnt);
        this.fineCnt  = String.format("%07d", 0);
        this.failCnt  = String.format("%07d", 0);
        this.rFineCnt = String.format("%07d", 0);
        this.dFineCnt = String.format("%07d", 0);
        String recBuf = String.format("%-1s", tRecTp)+this.ttRecCnt+this.rReqCnt+this.dReqCnt+this.fineCnt+this.failCnt+
                this.rFineCnt+this.dFineCnt+String.format("%-60s", tFiller)+"\r\n";

        return recBuf.getBytes();
    }

    public boolean makeFile(List<DtoReg> dtoRegList, String desFilePath){
        FileOutputStream fout = null;
        FfmRegCom regCom;
        try {
            fout = new FileOutputStream(desFilePath);
            int dataSeq = 0, regCnt = 0, disCnt = 0;
            for (DtoReg dtoReg : dtoRegList) {
                dataSeq++;
                if(dtoReg.getRegTp().equals("1"))       regCnt++;
                else if(dtoReg.getRegTp().equals("2"))   disCnt++;

                regCom = new FfmRegCom(dtoReg.getFacCd(), dtoReg.gethBankCd(), dtoReg.gethExtra(), dataSeq, dtoReg.getdBankCd(), dtoReg.getAcctNo(),
                        dtoReg.getRegTp(), dtoReg.getSocId(), dtoReg.getNapbuNo(), dtoReg.getTmpInfo(), dtoReg.getSocIdTp(), dtoReg.getPrfTp(), dtoReg.getmBankCd());

                //make head
                if(dataSeq==1) fout.write(regCom.getHead());
                //make data
                fout.write(regCom.getData());
                //make end
                if(dataSeq== dtoRegList.size()) fout.write(regCom.getEnd(dataSeq+2, regCnt, disCnt));
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

    public List<DtoReg> makeDtoList(String srcFilePath){
        FileInputStream fin = null;
        FfmRegCom regCom;
        byte[] headBuf=null, dataBuf;
        List<DtoReg> regList = new ArrayList<>();
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
                    regCom = new FfmRegCom(dataBuf, headBuf);
                    regList.add(new DtoReg(regCom.getFacCd().trim(), regCom.gethBankCd().trim(), regCom.gethExtra().trim(), regCom.getdBankCd().trim(),
                            regCom.getAcctNo().trim(), regCom.getRegTp().trim(), regCom.getResult().trim(), regCom.getErrCd().trim(), regCom.getSocId().trim(),
                            regCom.getNapbuNo().trim(), regCom.getTmpInfo().trim(), regCom.getSocIdTp().trim(), regCom.getPrfTp().trim(),
                            regCom.getmBankCd().trim()));
                }
            }//while end.
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[makeDtoList] "+e);
            return null;
        } finally {
            if(fin!=null) try {fin.close();} catch (IOException ignored) {}
        }

        return regList;
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
        this.hBankCd = hBankCd;
    }

    public String getSendDt() {
        return sendDt;
    }

    public void setSendDt(String sendDt) {
        this.sendDt = sendDt;
    }

    public String getResultTp() {
        return resultTp;
    }

    public void setResultTp(String resultTp) {
        this.resultTp = resultTp;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getRepFacCd() {
        return repFacCd;
    }

    public void setRepFacCd(String repFacCd) {
        this.repFacCd = repFacCd;
    }

    public String gethExtra() {
        return hExtra;
    }

    public void sethExtra(String hExtra) {
        this.hExtra = hExtra;
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

    public String getAutoDt() {
        return autoDt;
    }

    public void setAutoDt(String autoDt) {
        this.autoDt = autoDt;
    }

    public String getBranchCd() {
        return branchCd;
    }

    public void setBranchCd(String branchCd) {
        this.branchCd = branchCd;
    }

    public String getcRegDt() {
        return cRegDt;
    }

    public void setcRegDt(String cRegDt) {
        this.cRegDt = cRegDt;
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

    public String getSocidChk() {
        return socidChk;
    }

    public void setSocidChk(String socidChk) {
        this.socidChk = socidChk;
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

    public String getTtRecCnt() {
        return ttRecCnt;
    }

    public void setTtRecCnt(String ttRecCnt) {
        this.ttRecCnt = ttRecCnt;
    }

    public String getrReqCnt() {
        return rReqCnt;
    }

    public void setrReqCnt(String rReqCnt) {
        this.rReqCnt = rReqCnt;
    }

    public String getdReqCnt() {
        return dReqCnt;
    }

    public void setdReqCnt(String dReqCnt) {
        this.dReqCnt = dReqCnt;
    }

    public String getFineCnt() {
        return fineCnt;
    }

    public void setFineCnt(String fineCnt) {
        this.fineCnt = fineCnt;
    }

    public String getFailCnt() {
        return failCnt;
    }

    public void setFailCnt(String failCnt) {
        this.failCnt = failCnt;
    }

    public String getrFineCnt() {
        return rFineCnt;
    }

    public void setrFineCnt(String rFineCnt) {
        this.rFineCnt = rFineCnt;
    }

    public String getdFineCnt() {
        return dFineCnt;
    }

    public void setdFineCnt(String dFineCnt) {
        this.dFineCnt = dFineCnt;
    }

    public String gettFiller() {
        return tFiller;
    }

    public void settFiller(String tFiller) {
        this.tFiller = tFiller;
    }
}
