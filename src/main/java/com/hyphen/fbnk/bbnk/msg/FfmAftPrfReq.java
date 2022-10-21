package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.dto.DtoAftPrf;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FfmAftPrfReq {
    private static final Log log = LogFactory.getLog(FfmAftPrfReq.class);
    private final String encodeTp = "euc-kr";

    private String hRecTp   = "H";      //1
    private String hFileTp  = "FB0410"; //6
    private String vanCd    = "VKN";    //3
    private String hFacCd   = "";       //20
    private String hRegDt   = "";       //8
    private String vanId    = "";       //30
    private String hBankCd  = "";       //3
    private String hDataCnt = "";       //10
    private String hFiller  = "";       //319

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
    private String dFiller2 = "";       //286

    public FfmAftPrfReq() {}
    public FfmAftPrfReq(byte[] dataRec, byte[] headRec) {
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
        dFiller2 = new String(dataRec, 1+10+1+10+20+10+30+3+20+8+1, 286);
    }

    public List<DtoAftPrf> makeDtoList(String srcFilePath){
        FileInputStream fin = null;
        FfmAftPrfReq aftPrfReq;
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
                    aftPrfReq = new FfmAftPrfReq(dataBuf, headBuf);
                    aftPrfList.add(new DtoAftPrf(aftPrfReq.gethBankCd().trim(), aftPrfReq.gethFacCd().trim(), aftPrfReq.gethRegDt().trim(),
                            aftPrfReq.getVanId().trim(), aftPrfReq.getReqTp().trim(), aftPrfReq.getBussNo().trim(), aftPrfReq.getNapbuNo().trim(),
                            aftPrfReq.getdBankCd().trim(), aftPrfReq.getAcctNo().trim(), aftPrfReq.getdReqDt().trim(), aftPrfReq.getPrfTp().trim(),
                            "", "", "", ""));
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

    public String getdFiller2() {
        return dFiller2;
    }

    public void setdFiller2(String dFiller2) {
        this.dFiller2 = dFiller2;
    }

}
