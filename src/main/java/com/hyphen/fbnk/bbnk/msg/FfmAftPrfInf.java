package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.dto.DtoAftPrf;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FfmAftPrfInf {
    private static final Log log = LogFactory.getLog(FfmAftPrfInf.class);
    private final String encodeTp = "euc-kr";
    private final int prfULen = 1024;

    private String hRecTp   = "H";      //1
    private String hFileTp  = "FB0420"; //6
    private String vanCd    = "VKN";    //3
    private String hFacCd   = "";       //20
    private String hRegDt   = "";       //8
    private String vanId    = "";       //30
    private String hBankCd  = "";       //3
    private String hDataCnt = "";       //10
    private String hFiller  = "";       //943

    private String dRecTp   = "D";      //1
    private String dataSeq  = "";       //10
    private String reqTp    = "";       //1
    private String bussNo   = "";       //10
    private String dFacCd   = "";       //20
    private String dFiller1 = "";       //10
    private String napbuNo  = "";       //30
    private String dBankCd  = "";       //3
    private String acctNo   = "";       //20
    private String dReqDt   = "";       //8
    private String prfTp    = "";       //1
    private String prfYn    = "";       //1
    private String expNm    = "";       //4
    private String prfSize  = "";       //7
    private String rtnCd    = "";       //4
    private String dFiller2 = "";       //270

    private String tRecTp   = "T";      //1
    private String tFileTp  = "FB0420"; //6
    private String tDataCnt = "";       //10
    private String tFiller  = "";       //1007

    public FfmAftPrfInf() {}
    public FfmAftPrfInf(String facCd, String regDt, String vanId, String hBankCd, int dataCnt, int dSeq, String reqTp, String bussNo, String napbuNo, String acctNo, String dReqDt, String prfTp, String prfYn, String expNm, String rtnCd) {
        this.hFacCd = facCd;
        this.hRegDt = regDt;
        this.vanId = vanId;
        try{
            this.hBankCd = String.format("%03d", Integer.parseInt(hBankCd));
        } catch (NumberFormatException e){this.hBankCd = hBankCd;}
        this.hDataCnt = String.format("%010d", dataCnt);
        this.dataSeq = String.format("%010d", dSeq);
        this.reqTp = reqTp;
        this.bussNo = bussNo;
        this.dFacCd = facCd;
        this.napbuNo = napbuNo;
        this.dBankCd = this.hBankCd;
        this.acctNo = acctNo;
        this.dReqDt = dReqDt;
        this.prfTp = prfTp;
        this.prfYn = prfYn;
        this.expNm = expNm;
        this.rtnCd = rtnCd;
        this.tDataCnt = this.hDataCnt;
    }

    public FfmAftPrfInf(byte[] dataRec, byte[] headRec) {
        hRecTp   = new String(headRec, 0, 1);
        hFileTp  = new String(headRec, 1, 6);
        vanCd    = new String(headRec, 1+6, 3);
        hFacCd   = new String(headRec, 1+6+3, 20);
        hRegDt   = new String(headRec, 1+6+3+20, 8);
        vanId    = new String(headRec, 1+6+3+20+8, 30);
        hBankCd  = new String(headRec, 1+6+3+20+8+30, 3);
        hDataCnt = new String(headRec, 1+6+3+20+8+30+3, 10);
        hFiller  = new String(headRec, 1+6+3+20+8+30+3+10, 319);

        dRecTp   = new String(dataRec, 0, 1);
        dataSeq  = new String(dataRec, 1, 10);
        reqTp    = new String(dataRec, 1+10, 1);
        bussNo   = new String(dataRec, 1+10+1, 10);
        dFacCd   = new String(dataRec, 1+10+1+10, 20);
        dFiller1 = new String(dataRec, 1+10+1+10+20, 10);
        napbuNo  = new String(dataRec, 1+10+1+10+20+10, 30);
        dBankCd  = new String(dataRec, 1+10+1+10+20+10+30, 3);
        acctNo   = new String(dataRec, 1+10+1+10+20+10+30+3, 20);
        dReqDt   = new String(dataRec, 1+10+1+10+20+10+30+3+20, 8);
        prfTp    = new String(dataRec, 1+10+1+10+20+10+30+3+20+8, 1);
        prfYn    = new String(dataRec, 1+10+1+10+20+10+30+3+20+8+1, 1);
        expNm    = new String(dataRec, 1+10+1+10+20+10+30+3+20+8+1+1, 4);
        prfSize  = new String(dataRec, 1+10+1+10+20+10+30+3+20+8+1+1+4, 7);
        rtnCd    = new String(dataRec, 1+10+1+10+20+10+30+3+20+8+1+1+4+7, 4);
        dFiller2 = new String(dataRec, 1+10+1+10+20+10+30+3+20+8+1+1+4+7+4, 270);
    }

    public List<DtoAftPrf> makeDtoList(String srcFilePath){
        FileInputStream fin = null;
        FfmAftPrfInf aftPrfInf;
        byte[] headBuf = null, dataBuf;
        List<DtoAftPrf> aftPrfList = new ArrayList<>();
        try {
            fin = new FileInputStream(srcFilePath);
            while(true){
                dataBuf = Util.readLineByte(fin);
                if(dataBuf.length==0)	break;

                if(new String(dataBuf, 0, 1).equals("H")){
                    headBuf = dataBuf;
                    log.debug("[makeDtoList] headBuf=["+new String(headBuf)+"]("+headBuf.length+")");
                }
                else if(new String(dataBuf, 0, 1).equals("D")) {
                    aftPrfInf = new FfmAftPrfInf(dataBuf, headBuf);
                    aftPrfList.add(new DtoAftPrf(aftPrfInf.gethBankCd().trim(), aftPrfInf.gethFacCd().trim(), aftPrfInf.gethRegDt().trim(),
                            aftPrfInf.getVanId().trim(), aftPrfInf.getReqTp().trim(), aftPrfInf.getBussNo().trim(), aftPrfInf.getNapbuNo().trim(),
                            aftPrfInf.getdBankCd().trim(), aftPrfInf.getAcctNo().trim(), aftPrfInf.getdReqDt().trim(), aftPrfInf.getPrfTp().trim(),
                            aftPrfInf.getPrfYn().trim(), aftPrfInf.getExpNm(), "", aftPrfInf.getRtnCd()));
                }
            }//while end.
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[makeDtoList] "+e);
            return null;
        } finally {
            if(fin!=null) try {fin.close();} catch (IOException ignored) {}
        }

        return aftPrfList;
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
        String recBuf = String.format("%-1s", dRecTp)+String.format("%-10s", dataSeq)+String.format("%-1s", reqTp)+String.format("%-10s", bussNo)
                +String.format("%-20s", dFacCd)+String.format("%-10s", dFiller1) +String.format("%-30s", napbuNo)+String.format("%-3s", dBankCd)
                +String.format("%-20s", acctNo)+String.format("%-8s", dReqDt)+String.format("%-1s", prfTp)+String.format("%-1s", prfYn)
                +String.format("%-4s", expNm)+String.format("%-7s", this.prfSize)+String.format("%-4s", rtnCd)+String.format("%-270s", dFiller2);
        try {rtnBuf = recBuf.getBytes(encodeTp);} catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public boolean writePrfFile(FileOutputStream fout, String prfPath) throws IOException {
        byte[] paddBuf;
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

    public boolean makeFile(List<DtoAftPrf> dtoAftPrfList, String desFilePath) {
        FileOutputStream fout = null;
        FfmAftPrfInf aftPrfInf;
        try {
            fout = new FileOutputStream(desFilePath);
            int dataSeq = 0;
            for (DtoAftPrf dtoAftPrf : dtoAftPrfList) {
                dataSeq++;
                aftPrfInf = new FfmAftPrfInf(dtoAftPrf.getFacCd(), dtoAftPrf.getRegDt(), dtoAftPrf.getVanId(), dtoAftPrf.getRepBankCd(), dtoAftPrfList.size(),
                    dataSeq, dtoAftPrf.getReqTp(), dtoAftPrf.getBussNo(), dtoAftPrf.getNapbuNo(), dtoAftPrf.getAcctNo(), dtoAftPrf.getReqDt(), dtoAftPrf.getPrfTp(),
                    dtoAftPrf.getPrfYn(), dtoAftPrf.getExpNm(), "");
                //make head
                if(dataSeq==1)  fout.write(aftPrfInf.getHead());
                //make data
                fout.write(aftPrfInf.getData(Util.getFileSize(dtoAftPrf.getPrfPath())));
                //make proof
                if(!writePrfFile(fout, dtoAftPrf.getPrfPath())) throw new Exception("Error writePrfFile() ~!!");
                //make end
                if(dataSeq == dtoAftPrfList.size())    fout.write(aftPrfInf.getEnd());
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
        this.hBankCd = hBankCd;
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

    public String getdFacCd() {
        return dFacCd;
    }

    public void setdFacCd(String dFacCd) {
        this.dFacCd = dFacCd;
    }

    public String getdFiller1() {
        return dFiller1;
    }

    public void setdFiller1(String dFiller1) {
        this.dFiller1 = dFiller1;
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

    public String getdReqDt() {
        return dReqDt;
    }

    public void setdReqDt(String dReqDt) {
        this.dReqDt = dReqDt;
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

    public String getPrfSize() {
        return prfSize;
    }

    public void setPrfSize(String prfSize) {
        this.prfSize = prfSize;
    }

    public String getRtnCd() {
        return rtnCd;
    }

    public void setRtnCd(String rtnCd) {
        this.rtnCd = rtnCd;
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
