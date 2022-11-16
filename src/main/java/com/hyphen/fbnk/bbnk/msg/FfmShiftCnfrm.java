package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.dto.DtoShift;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FfmShiftCnfrm {
    private static final Log log = LogFactory.getLog(FfmShiftCnfrm.class);
    private final String encodeTp = "euc-kr";

    private String hRecTp       = "H";      //1
    private String hFileTp      = "FB0222"; //6
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
    private String cancRtnCd    = "";       //4
    private String cancDt       = "";       //8
    private String aftFacCd     = "";       //20
    private String aftCompCd    = "";       //10
    private String aftNapbuNo   = "";       //30
    private String aftBankCd    = "";       //3
    private String aftAcctNo    = "";       //20
    private String newRtnCd     = "";       //4
    private String newDt        = "";       //8
    private String billSvcTp    = "";       //1
    private String dFiller      = "";       //157

    public FfmShiftCnfrm() {}
    public FfmShiftCnfrm(byte[] dataRec, byte[] headRec) {
        hRecTp       = new String(headRec, 0, 1);
        hFileTp      = new String(headRec, 1, 6);
        vanCd        = new String(headRec, 1+6, 3);
        hFacCd       = new String(headRec, 1+6+3, 20);
        facTp        = new String(headRec, 1+6+3+20, 1);
        hRegDt       = new String(headRec, 1+6+3+20+1, 8);
        vanId        = new String(headRec, 1+6+3+20+1+8, 30);
        hBankCd      = new String(headRec, 1+6+3+20+1+8+30, 3);
        hDataCnt     = new String(headRec, 1+6+3+20+1+8+30+3, 10);
        hFiller      = new String(headRec, 1+6+3+20+1+8+30+3+10, 318);

        dRecTp       = new String(dataRec, 0, 1);
        dataSeq      = new String(dataRec, 1, 10);
        shiftGb      = new String(dataRec, 1+10, 1);
        shiftTm      = new String(dataRec, 1+10+1, 14);
        acceptDt     = new String(dataRec, 1+10+1+14, 8);
        dFiller1     = new String(dataRec, 1+10+1+14+8, 1);
        socId        = new String(dataRec, 1+10+1+14+8+1, 13);
        rtnCd        = new String(dataRec, 1+10+1+14+8+1+13, 4);
        befFacCd     = new String(dataRec, 1+10+1+14+8+1+13+4, 20);
        befCompCd    = new String(dataRec, 1+10+1+14+8+1+13+4+20, 10);
        befNapbuNo   = new String(dataRec, 1+10+1+14+8+1+13+4+20+10, 30);
        befBankCd    = new String(dataRec, 1+10+1+14+8+1+13+4+20+10+30, 3);
        befAcctNo    = new String(dataRec, 1+10+1+14+8+1+13+4+20+10+30+3, 20);
        cancRtnCd    = new String(dataRec, 1+10+1+14+8+1+13+4+20+10+30+3+20, 4);
        cancDt       = new String(dataRec, 1+10+1+14+8+1+13+4+20+10+30+3+20+4, 8);
        aftFacCd     = new String(dataRec, 1+10+1+14+8+1+13+4+20+10+30+3+20+4+8, 20);
        aftCompCd    = new String(dataRec, 1+10+1+14+8+1+13+4+20+10+30+3+20+4+8+20, 10);
        aftNapbuNo   = new String(dataRec, 1+10+1+14+8+1+13+4+20+10+30+3+20+4+8+20+10, 30);
        aftBankCd    = new String(dataRec, 1+10+1+14+8+1+13+4+20+10+30+3+20+4+8+20+10+30, 3);
        aftAcctNo    = new String(dataRec, 1+10+1+14+8+1+13+4+20+10+30+3+20+4+8+20+10+30+3, 20);
        newRtnCd     = new String(dataRec, 1+10+1+14+8+1+13+4+20+10+30+3+20+4+8+20+10+30+3+20, 4);
        newDt        = new String(dataRec, 1+10+1+14+8+1+13+4+20+10+30+3+20+4+8+20+10+30+3+20+4, 8);
        billSvcTp    = new String(dataRec, 1+10+1+14+8+1+13+4+20+10+30+3+20+4+8+20+10+30+3+20+4+8, 1);
        dFiller      = new String(dataRec, 1+10+1+14+8+1+13+4+20+10+30+3+20+4+8+20+10+30+3+20+4+8+1, 157);
    }

    public List<DtoShift> makeDtoList(String srcFilePath){
        FileInputStream fin = null;
        FfmShiftCnfrm ffmShiftCnfrm;
        byte[] headBuf = null, dataBuf;
        List<DtoShift> shiftList = new ArrayList<>();
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
                    ffmShiftCnfrm = new FfmShiftCnfrm(dataBuf, headBuf);
                    shiftList.add(new DtoShift(ffmShiftCnfrm.gethRegDt().trim(), ffmShiftCnfrm.getVanId().trim(), ffmShiftCnfrm.getShiftGb().trim(), ffmShiftCnfrm.getShiftTm().trim(),
                            ffmShiftCnfrm.getAcceptDt().trim(), ffmShiftCnfrm.getSocId().trim(), ffmShiftCnfrm.getRtnCd().trim(), ffmShiftCnfrm.getFacTp().trim(),
                            ffmShiftCnfrm.getBefFacCd().trim(), ffmShiftCnfrm.getBefNapbuNo().trim(), ffmShiftCnfrm.getBefBankCd().trim(), ffmShiftCnfrm.getBefAcctNo().trim(),
                            ffmShiftCnfrm.getAftFacCd().trim(), ffmShiftCnfrm.getAftNapbuNo().trim(), ffmShiftCnfrm.getAftBankCd().trim(), ffmShiftCnfrm.getAftAcctNo().trim(),
                            ffmShiftCnfrm.getCancRtnCd().trim(), ffmShiftCnfrm.getCancDt().trim(), ffmShiftCnfrm.getNewRtnCd().trim(), ffmShiftCnfrm.getNewDt().trim(),
                            ffmShiftCnfrm.getBillSvcTp().trim()));
                }
            }//while end.
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[makeDtoList] "+e);
            return null;
        } finally {
            if(fin!=null) try {fin.close();} catch (IOException ignored) {}
        }

        return shiftList;
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

    public String getCancRtnCd() {
        return cancRtnCd;
    }

    public void setCancRtnCd(String cancRtnCd) {
        this.cancRtnCd = cancRtnCd;
    }

    public String getCancDt() {
        return cancDt;
    }

    public void setCancDt(String cancDt) {
        this.cancDt = cancDt;
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

    public String getNewRtnCd() {
        return newRtnCd;
    }

    public void setNewRtnCd(String newRtnCd) {
        this.newRtnCd = newRtnCd;
    }

    public String getNewDt() {
        return newDt;
    }

    public void setNewDt(String newDt) {
        this.newDt = newDt;
    }

    public String getBillSvcTp() {
        return billSvcTp;
    }

    public void setBillSvcTp(String billSvcTp) {
        this.billSvcTp = billSvcTp;
    }

    public String getdFiller() {
        return dFiller;
    }

    public void setdFiller(String dFiller) {
        this.dFiller = dFiller;
    }
}
