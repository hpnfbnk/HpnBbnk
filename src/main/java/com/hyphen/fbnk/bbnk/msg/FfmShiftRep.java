package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.dto.DtoBill;
import com.hyphen.fbnk.bbnk.dto.DtoShift;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class FfmShiftRep {
    private static final Log log = LogFactory.getLog(FfmShiftRep.class);
    private final String encodeTp = "euc-kr";

    private String hRecTp       = "H";      //1
    private String hFileTp      = "FB0221"; //6
    private String vanCd        = "VKN";    //3
    private String hFacCd       = "";       //20
    private String facTp        = "";       //1
    private String hRegDt       = "";       //8
    private String vanId        = "";       //30
    private String hBankCd      = "";       //3
    private String hDataCnt     = "";       //10
    private String hFiller      = "";       //318

    private String dRecTp       = "D";      //1
    private String dataSeq      = "";       //10
    private String shiftGb      = "";       //1
    private String shiftTm      = "";       //14
    private String acceptDt     = "";       //8
    private String dFiller1     = "";       //1
    private String socId        = "";       //13
    private String rtnCd        = "";       //4
    private String befFacCd     = "";       //20
    private String befCompCd    = "";       //10
    private String befNapbuNo   = "";       //30
    private String befBankCd    = "";       //3
    private String befAcctNo    = "";       //20
    private String aftFacCd     = "";       //20
    private String aftCompCd    = "";       //10
    private String aftNapbuNo   = "";       //30
    private String aftBankCd    = "";       //3
    private String aftAcctNo    = "";       //20
    private String compName     = "";       //80
    private String compTel      = "";       //20
    private String billSvcTp    = "";       //1
    private String dFiller2  = "";       //81

    private String tRecTp       = "T";      //1
    private String tFileTp      = "FB0221"; //6
    private String tDataCnt     = "";       //10
    private String tFineCnt     = "";       //10
    private String tFailCnt     = "";       //10
    private String tFiller      = "";       //363

    public FfmShiftRep() {}
    public FfmShiftRep(String facTp, String hRegDt, String vanId, int dataCnt, int dSeq, String shiftGb, String shiftTm, String acceptDt, String socId, String rtnCd, String befFacCd, String befNapbuNo, String befBankCd, String befAcctNo, String aftFacCd, String aftNapbuNo, String aftBankCd, String aftAcctNo, String billSvcTp) {
        this.hFacCd = befFacCd;
        this.facTp = facTp;
        this.hRegDt = hRegDt;
        this.vanId = vanId;
        try{
            this.hBankCd = String.format("%03d", Integer.parseInt(befBankCd));
        } catch (NumberFormatException e){this.hBankCd = befBankCd;}
        this.hDataCnt = String.format("%010d", dataCnt);
        this.dataSeq = String.format("%010d", dSeq);
        this.shiftGb = shiftGb;
        this.shiftTm = shiftTm;
        this.acceptDt = acceptDt;
        this.socId = socId;
        this.rtnCd = rtnCd;
        this.befFacCd = befFacCd;
        this.befNapbuNo = befNapbuNo;
        this.befBankCd = befBankCd;
        this.befAcctNo = befAcctNo;
        this.aftFacCd = aftFacCd;
        this.aftNapbuNo = aftNapbuNo;
        this.aftBankCd = aftBankCd;
        this.aftAcctNo = aftAcctNo;
        this.billSvcTp = billSvcTp;
        this.tDataCnt = this.hDataCnt;
    }

    public byte[] getHead() {
        byte[] rtnBuf = null;
        String recBuf = String.format("%-1s", hRecTp)+String.format("%-6s", hFileTp)+String.format("%-3s", vanCd)+
                String.format("%-20s", hFacCd)+String.format("%-1s", facTp)+String.format("%-8s", hRegDt)+String.format("%-30s", vanId)+
                String.format("%-3s", hBankCd)+String.format("%-10s", hDataCnt)+String.format("%-318s", hFiller)+"\r\n";
        try {rtnBuf = recBuf.getBytes(encodeTp);} catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public byte[] getEnd(int dataCnt, int fineCnt, int failCnt){
        this.tDataCnt  = String.format("%010d", dataCnt);
        this.tFineCnt  = String.format("%010d", fineCnt);
        this.tFailCnt  = String.format("%010d", failCnt);
        byte[] rtnBuf = null;
        String recBuf = String.format("%-1s", tRecTp)+String.format("%-6s", tFileTp)+this.tDataCnt+this.tFineCnt+this.tFailCnt+
                String.format("%-363s", tFiller)+"\r\n";
        try {rtnBuf = recBuf.getBytes(encodeTp);} catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public byte[] getData(){
        byte[] rtnBuf = null;
        String recBuf = String.format("%-1s", dRecTp)+String.format("%-1s", dataSeq)+String.format("%-1s", shiftGb)+String.format("%-14s", shiftTm)+
                String.format("%-8s", acceptDt)+String.format("%-1s", dFiller1)+String.format("%-13s", socId)+String.format("%-4s", rtnCd)+
                String.format("%-20s", befFacCd)+String.format("%-10s", befCompCd)+String.format("%-30s", befNapbuNo)+String.format("%-3s", befBankCd)+
                String.format("%-20s", befAcctNo)+String.format("%-20s", aftFacCd)+String.format("%-10s", aftCompCd)+String.format("%-30s", aftNapbuNo)+
                String.format("%-3s", aftBankCd)+String.format("%-20s", aftAcctNo)+String.format("%-80s", compName)+String.format("%-20s", compTel)+
                String.format("%-1s", billSvcTp)+String.format("%-81s", dFiller2)+"\r\n";
        try {rtnBuf = recBuf.getBytes(encodeTp);} catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public boolean makeFile(List<DtoShift> dtoShiftList, String desFilePath){
        FileOutputStream fout = null;
        FfmShiftRep shiftRep;
        try {
            fout = new FileOutputStream(desFilePath);
            int dataSeq = 0, fineCnt=0, failCnt=0;
            for (DtoShift dtoShift : dtoShiftList) {
                dataSeq++;
                shiftRep = new FfmShiftRep(dtoShift.getBefFacTp(), dtoShift.getWorkDt(), dtoShift.getVanId(), dtoShiftList.size(), dataSeq, dtoShift.getShiftGb(),
                        dtoShift.getReqDt(), dtoShift.getAcceptDt(), dtoShift.getSocId(), dtoShift.getRtnCd(), dtoShift.getBefFacCd(), dtoShift.getBefNapbuNo(),
                        dtoShift.getBefBankCd(), dtoShift.getBefAcctNo(), dtoShift.getAftFacCd(), dtoShift.getAftNapbuNo(), dtoShift.getAftBankCd(),
                        dtoShift.getAftAcctNo(), dtoShift.getBillSvcTp());
                if(dtoShift.getRtnCd().equals("0000"))  fineCnt++; else failCnt++ ;
                //make head
                if(dataSeq==1) fout.write(shiftRep.getHead());
                //make data
                fout.write(shiftRep.getData());
                //make end
                if(dataSeq == dtoShiftList.size()) fout.write(shiftRep.getEnd(dataSeq, fineCnt, failCnt));
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

    public String getFacTp() {
        return facTp;
    }

    public void setFacTp(String facTp) {
        this.facTp = facTp;
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

    public String getShiftGb() {
        return shiftGb;
    }

    public void setShiftGb(String shiftGb) {
        this.shiftGb = shiftGb;
    }

    public String getShiftTm() {
        return shiftTm;
    }

    public void setShiftTm(String shiftTm) {
        this.shiftTm = shiftTm;
    }

    public String getAcceptDt() {
        return acceptDt;
    }

    public void setAcceptDt(String acceptDt) {
        this.acceptDt = acceptDt;
    }

    public String getdFiller1() {
        return dFiller1;
    }

    public void setdFiller1(String dFiller1) {
        this.dFiller1 = dFiller1;
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

    public String getBefCompCd() {
        return befCompCd;
    }

    public void setBefCompCd(String befCompCd) {
        this.befCompCd = befCompCd;
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

    public String getAftCompCd() {
        return aftCompCd;
    }

    public void setAftCompCd(String aftCompCd) {
        this.aftCompCd = aftCompCd;
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

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompTel() {
        return compTel;
    }

    public void setCompTel(String compTel) {
        this.compTel = compTel;
    }

    public String getBillSvcTp() {
        return billSvcTp;
    }

    public void setBillSvcTp(String billSvcTp) {
        this.billSvcTp = billSvcTp;
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

    public String gettFineCnt() {
        return tFineCnt;
    }

    public void settFineCnt(String tFineCnt) {
        this.tFineCnt = tFineCnt;
    }

    public String gettFailCnt() {
        return tFailCnt;
    }

    public void settFailCnt(String tFailCnt) {
        this.tFailCnt = tFailCnt;
    }

    public String gettFiller() {
        return tFiller;
    }

    public void settFiller(String tFiller) {
        this.tFiller = tFiller;
    }
}
