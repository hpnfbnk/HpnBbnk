package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.dto.DtoAftPrf;
import com.hyphen.fbnk.bbnk.dto.DtoCanc;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FfmCanc {
    private static final Log log = LogFactory.getLog(FfmCanc.class);
    private final String encodeTp = "euc-kr";

    private String hRecTp   = "H";      //1
    private String hFileTp  = "FB0320"; //6
    private String vanCd    = "VKN";    //3
    private String hFacCd   = "";       //20
    private String facTp    = "";       //1
    private String hRegDt   = "";       //8
    private String vanId    = "";       //30
    private String hBankCd  = "";       //3
    private String hDataCnt = "";       //10
    private String hFiller  = "";       //318

    private String dRecTp   = "D";      //1
    private String dataSeq  = "";       //10
    private String cancGb   = "";       //1
    private String cancDt   = "";       //8
    private String dFiller1 = "";       //1
    private String socId    = "";       //13
    private String dFacCd   = "";       //20
    private String dCompCd  = "";       //10
    private String napbuNo  = "";       //30
    private String dBankCd  = "";       //3
    private String acctNo   = "";       //20
    private String cancTp   = "";       //1
    private String dFiller2  = "";      //282

    public FfmCanc() {}
    public FfmCanc(byte[] dataRec, byte[] headRec) {
        hRecTp   = new String(headRec, 0, 1);
        hFileTp  = new String(headRec, 1, 6);
        vanCd    = new String(headRec, 1+6, 3);
        hFacCd   = new String(headRec, 1+6+3, 20);
        facTp    = new String(headRec, 1+6+3+20, 1);
        hRegDt   = new String(headRec, 1+6+3+20+1, 8);
        vanId    = new String(headRec, 1+6+3+20+1+8, 30);
        hBankCd  = new String(headRec, 1+6+3+20+1+8+30, 3);
        hDataCnt = new String(headRec, 1+6+3+20+1+8+30+3, 10);
        hFiller  = new String(headRec, 1+6+3+20+1+8+30+3+10, 318);

        dRecTp   = new String(dataRec, 0, 1);
        dataSeq  = new String(dataRec, 1, 10);
        cancGb   = new String(dataRec, 1+10, 1);
        cancDt   = new String(dataRec, 1+10+1, 8);
        dFiller1 = new String(dataRec, 1+10+1+8, 1);
        socId    = new String(dataRec, 1+10+1+8+1, 13);
        dFacCd   = new String(dataRec, 1+10+1+8+1+13, 20);
        dCompCd  = new String(dataRec, 1+10+1+8+1+13+20, 10);
        napbuNo  = new String(dataRec, 1+10+1+8+1+13+20+10, 30);
        dBankCd  = new String(dataRec, 1+10+1+8+1+13+20+10+30, 3);
        acctNo   = new String(dataRec, 1+10+1+8+1+13+20+10+30+3, 20);
        cancTp   = new String(dataRec, 1+10+1+8+1+13+20+10+30+3+20, 1);
        dFiller2 = new String(dataRec, 1+10+1+8+1+13+20+10+30+3+20+1, 282);
    }

    public List<DtoCanc> makeDtoList(String srcFilePath){
        FileInputStream fin = null;
        FfmCanc ffmCanc;
        byte[] headBuf = null, dataBuf;
        List<DtoCanc> cancList = new ArrayList<>();
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
                    ffmCanc = new FfmCanc(dataBuf, headBuf);
                    cancList.add(new DtoCanc(ffmCanc.getdFacCd().trim(), ffmCanc.getFacTp().trim(), ffmCanc.gethRegDt().trim(), ffmCanc.getdBankCd().trim(), ffmCanc.getCancGb().trim(),
                            ffmCanc.getCancDt().trim(), ffmCanc.getSocId().trim(), ffmCanc.getNapbuNo().trim(), ffmCanc.getAcctNo(), ffmCanc.getCancTp()));
                }
            }//while end.
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[makeDtoList] "+e);
            return null;
        } finally {
            if(fin!=null) try {fin.close();} catch (IOException ignored) {}
        }

        return cancList;
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

    public String getCancGb() {
        return cancGb;
    }

    public void setCancGb(String cancGb) {
        this.cancGb = cancGb;
    }

    public String getCancDt() {
        return cancDt;
    }

    public void setCancDt(String cancDt) {
        this.cancDt = cancDt;
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

    public String getdFacCd() {
        return dFacCd;
    }

    public void setdFacCd(String dFacCd) {
        this.dFacCd = dFacCd;
    }

    public String getdCompCd() {
        return dCompCd;
    }

    public void setdCompCd(String dCompCd) {
        this.dCompCd = dCompCd;
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

    public String getCancTp() {
        return cancTp;
    }

    public void setCancTp(String cancTp) {
        this.cancTp = cancTp;
    }

    public String getdFiller2() {
        return dFiller2;
    }

    public void setdFiller2(String dFiller2) {
        this.dFiller2 = dFiller2;
    }
}
