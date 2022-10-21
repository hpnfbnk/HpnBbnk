package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.dto.DtoPrf;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FfmPrfReq {
    private static final Log log = LogFactory.getLog(FfmPrfReq.class);
    private final String encodeTp = "euc-kr";
    private final int prfULen = 1024;

    private String hRecTp   = "H";      //1
    private String hFileTp  = "FB0110"; //6
    private String vanCd    = "VKN";    //3
    private String hFacCd   = "";       //20
    private String hRegDt   = "";       //8
    private String vanId    = "";       //30
    private String hBankCd  = "";       //3
    private String hDataCnt = "";       //10
    private String hFiller  = "";       //943

    private String dRecTp   = "D";      //1
    private String dataSeq  = "";       //10
    private String msgNo    = "";       //30
    private String dRegDt   = "";       //8
    private String socTp    = "";       //1
    private String socId    = "";       //13
    private String dFacCd   = "";       //20
    private String rtYn     = "N";      //1
    private String compCode = "";       //10
    private String napbuNo  = "";       //30
    private String dBankCd  = "";       //3
    private String acctNo   = "";       //20
    private String prfTp    = "";       //1
    private String expNm    = "";       //4
    private String prfSize  = "";       //7
    private String dFiller1 = "";       //200
    private String disYn    = "";       //1
    private String dFiller2 = "";       //40

    private String tRecTp   = "T";      //1
    private String tFileTp  = "FB0110"; //6
    private String tDataCnt = "";       //10
    private String tFiller  = "";       //1007

    public FfmPrfReq() {}
    public FfmPrfReq(String hFacCd, String hRegDt, String hBankCd, int dataCnt, int dSeq, String dRegDt, String socTp, String socId, String rtYn, String napbuNo, String acctNo, String prfTp, String expNm, String disYn) {
        this.hFacCd = hFacCd;
        this.hRegDt = hRegDt;
        try{
            this.hBankCd = String.format("%03d", Integer.parseInt(hBankCd));
        } catch (NumberFormatException e){this.hBankCd = hBankCd;}
        this.hDataCnt = String.format("%010d", dataCnt);
        this.dataSeq = String.format("%010d", dSeq);
        this.dRegDt = dRegDt;
        this.socTp = socTp;
        this.socId = socId;
        this.dFacCd = hFacCd;
        this.rtYn = rtYn;
        this.napbuNo = napbuNo;
        this.dBankCd = this.hBankCd;
        this.acctNo = acctNo;
        this.prfTp = prfTp;
        this.expNm = expNm;
        this.disYn = disYn;
        this.tDataCnt = this.hDataCnt;
    }

    public byte[] getHead(){
        byte[] rtnBuf = null;
        String recBuf = String.format("%-1s", hRecTp)+String.format("%-6s", hFileTp)+String.format("%-3s", vanCd)+String.format("%-20s", hFacCd)
                +String.format("%-8s", hRegDt)+String.format("%-30s", vanId)+String.format("%-3s", hBankCd) +String.format("%-10s", hDataCnt)
                +String.format("%-943s", hFiller);
        try {rtnBuf = recBuf.getBytes(encodeTp);} catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public byte[] getEnd(){
        byte[] rtnBuf = null;
        String recBuf = String.format("%-1s", tRecTp)+String.format("%-6s", tFileTp)+String.format("%-10s", hDataCnt)+String.format("%-1007s", tFiller);
        try {rtnBuf = recBuf.getBytes(encodeTp);} catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public byte[] getData(long prfSize) throws Exception {
        if(prfSize > prfULen*300)   throw new Exception("proof file size should be less than 300Kbyte~!!");
        this.prfSize = String.format("%07d", prfSize);
        byte[] rtnBuf = null;
        String recBuf = String.format("%-1s", dRecTp)+String.format("%-10s", dataSeq)+String.format("%-30s", msgNo)+String.format("%-8s", dRegDt)
                +String.format("%-1s", socTp)+String.format("%-13s", socId)+String.format("%-20s", dFacCd)+String.format("%-1s", rtYn)
                +String.format("%-10s", compCode)+String.format("%-30s", napbuNo)+String.format("%-3s", dBankCd)+String.format("%-20s", acctNo)
                +String.format("%-1s", prfTp)+String.format("%-4s", expNm)+String.format("%-7s", this.prfSize)+String.format("%-200s", dFiller1)
                +String.format("%-1s", disYn)+String.format("%-40s", dFiller2);
        try {rtnBuf = recBuf.getBytes(encodeTp);} catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public boolean writePrfFile(FileOutputStream fout, String prfPath, String disYn) throws IOException {
        byte[] paddBuf;
        if(disYn.equals("Y")){
            paddBuf = new byte[prfULen - 400];
            Arrays.fill(paddBuf, (byte)0x20);
            fout.write(paddBuf);
            return true;
        }

        FileInputStream prfFs = null;
        try {
            long pFileSize = Util.getFileSize(prfPath);
            prfFs = new FileInputStream(prfPath);

            byte[] prfBuf = new byte[prfULen];
            boolean isFirst = true;
            int unitLen=0, dataLen=0;
            //int chkCnt=0;
            while (true){
                if(isFirst){
                    if(pFileSize < (prfULen - 400)) unitLen = (int) pFileSize;
                    else unitLen = prfULen - 400;
                }
                else unitLen = prfULen;

                Arrays.fill(prfBuf, (byte) 0x00);
                dataLen = prfFs.read(prfBuf, 0, unitLen);
                if(dataLen <= 0)    break;

                fout.write(prfBuf, 0, dataLen);
                //log.debug("[writePrfFile] fout.write "+chkCnt+++" : "+dataLen);
                //padding 처리
                if(!isFirst && dataLen != prfULen){
                    paddBuf = new byte[prfULen - dataLen];
                    Arrays.fill(paddBuf, (byte)0x20);
                    fout.write(paddBuf);
                    //log.debug("[writePrfFile](padding.1) fout.write : "+paddBuf.length);
                }
                else if(isFirst && (unitLen == pFileSize)  ){
                    paddBuf = new byte[prfULen - 400 - dataLen];
                    Arrays.fill(paddBuf, (byte)0x20);
                    fout.write(paddBuf);
                    //log.debug("[writePrfFile](padding.2) fout.write : "+paddBuf.length);
                }
                if(isFirst) isFirst = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[writePrfFile] "+ e);
            return false;
        } finally {
            if(prfFs!=null) try {prfFs.close();} catch (IOException ignored) {}
        }

        return true;
    }

    public boolean makeFile(List<DtoPrf> dtoPrfList, String desFilePath) {
        FileOutputStream fout = null;
        FfmPrfReq prfReq;
        try {
            fout = new FileOutputStream(desFilePath);
            int dataSeq = 0;
            for (DtoPrf dtoPrf : dtoPrfList) {
                dataSeq++;
                prfReq = new FfmPrfReq(dtoPrf.getFacCd(), dtoPrf.getRegDt(), dtoPrf.getBankCd(), dtoPrfList.size(), dataSeq, dtoPrf.getReqDt(), dtoPrf.getSocTp()
                        , dtoPrf.getSocId(), dtoPrf.getRtYn(), dtoPrf.getNapbuNo(), dtoPrf.getAcctNo(), dtoPrf.getPrfTp(), dtoPrf.getExpNm(), dtoPrf.getDisYn());
                //make head
                if(dataSeq==1)  fout.write(prfReq.getHead());
                //make data
                fout.write(prfReq.getData(Util.getFileSize(dtoPrf.getPrfPath())));
                //make proof
                if(!writePrfFile(fout, dtoPrf.getPrfPath(), dtoPrf.getDisYn())) throw new Exception("Error writePrfFile() ~!!");
                //make end
                if(dataSeq == dtoPrfList.size())    fout.write(prfReq.getEnd());
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

    public String getEncodeTp() {
        return encodeTp;
    }

    public String gethRecTp() {
        return hRecTp;
    }

    public void sethRecTp(String hRecTp) {
        this.hRecTp = hRecTp;
    }

    public String gethFileTp() {
        return hFileTp;
    }

    public void sethFileTp(String hFileTp) {
        this.hFileTp = hFileTp;
    }

    public String getVanCd() {
        return vanCd;
    }

    public void setVanCd(String vanCd) {
        this.vanCd = vanCd;
    }

    public String gethFacCd() {
        return hFacCd;
    }

    public void sethFacCd(String hFacCd) {
        this.hFacCd = hFacCd;
    }

    public String gethRegDt() {
        return hRegDt;
    }

    public void sethRegDt(String hRegDt) {
        this.hRegDt = hRegDt;
    }

    public String getVanId() {
        return vanId;
    }

    public void setVanId(String vanId) {
        this.vanId = vanId;
    }

    public String gethBankCd() {
        return hBankCd;
    }

    public void sethBankCd(String hBankCd) {
        try{
            this.hBankCd = String.format("%03d", Integer.parseInt(hBankCd));
        } catch (NumberFormatException e){this.hBankCd = hBankCd;}
    }

    public String gethDataCnt() {
        return hDataCnt;
    }

    public void sethDataCnt(String hDataCnt) {
        this.hDataCnt = hDataCnt;
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

    public String getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(String msgNo) {
        this.msgNo = msgNo;
    }

    public String getdRegDt() {
        return dRegDt;
    }

    public void setdRegDt(String dRegDt) {
        this.dRegDt = dRegDt;
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

    public String getdFacCd() {
        return dFacCd;
    }

    public void setdFacCd(String dFacCd) {
        this.dFacCd = dFacCd;
    }

    public String getRtYn() {
        return rtYn;
    }

    public void setRtYn(String rtYn) {
        this.rtYn = rtYn;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getNapbuNo() {
        return napbuNo;
    }

    public void setNapbuNo(String napbuNo) {
        this.napbuNo = napbuNo;
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

    public String getPrfSize() {
        return prfSize;
    }

    public void setPrfSize(String prfSize) {
        this.prfSize = prfSize;
    }

    public String getdFiller1() {
        return dFiller1;
    }

    public void setdFiller1(String dFiller1) {
        this.dFiller1 = dFiller1;
    }

    public String getDisYn() {
        return disYn;
    }

    public void setDisYn(String disYn) {
        this.disYn = disYn;
    }

    public String getdFiller2() {
        return dFiller2;
    }

    public void setdFiller2(String dFiller2) {
        this.dFiller2 = dFiller2;
    }

    public String gettRecTp() {
        return tRecTp;
    }

    public void settRecTp(String tRecTp) {
        this.tRecTp = tRecTp;
    }

    public String gettFileTp() {
        return tFileTp;
    }

    public void settFileTp(String tFileTp) {
        this.tFileTp = tFileTp;
    }

    public String gettDataCnt() {
        return tDataCnt;
    }

    public void settDataCnt(String tDataCnt) {
        this.tDataCnt = tDataCnt;
    }

    public String gettFiller() {
        return tFiller;
    }

    public void settFiller(String tFiller) {
        this.tFiller = tFiller;
    }
}
