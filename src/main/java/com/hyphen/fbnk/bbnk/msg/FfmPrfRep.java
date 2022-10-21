package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.dto.DtoPrf;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FfmPrfRep {
    private static final Log log = LogFactory.getLog(FfmPrfRep.class);
    private final String encodeTp = "euc-kr";

    private String hRecTp   = "H";      //1
    private String hFileTp  = "FB0110"; //6
    private String vanCd    = "VKN";    //3
    private String hFacCd   = "";       //20
    private String hRegDt   = "";       //8
    private String vanId    = "";       //30
    private String hBankCd  = "";       //3
    private String hDataCnt = "";       //10
    private String hFiller  = "";       //319

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
    private String rtnCd    = "";       //4
    private String dFiller  = "";       //237

    public FfmPrfRep() {}
    public FfmPrfRep(byte[] dataRec, byte[] headRec) {
        hRecTp      = new String(headRec, 0, 1);
        hFileTp     = new String(headRec, 1, 6);
        vanCd       = new String(headRec, 1+6, 3);
        hFacCd      = new String(headRec, 1+6+3, 20);
        hRegDt      = new String(headRec, 1+6+3+20, 8);
        vanId       = new String(headRec, 1+6+3+20+8, 30);
        hBankCd     = new String(headRec, 1+6+3+20+8+30, 3);
        hDataCnt    = new String(headRec, 1+6+3+20+8+30+3, 10);
        hFiller     = new String(headRec, 1+6+3+20+8+30+3+10, 319);

        dRecTp      = new String(dataRec, 0, 1);
        dataSeq     = new String(dataRec, 1, 10);
        msgNo       = new String(dataRec, 1+10, 30);
        dRegDt      = new String(dataRec, 1+10+30, 8);
        socTp       = new String(dataRec, 1+10+30+8, 1);
        socId       = new String(dataRec, 1+10+30+8+1, 13);
        dFacCd      = new String(dataRec, 1+10+30+8+1+13, 20);
        rtYn        = new String(dataRec, 1+10+30+8+1+13+20, 1);
        compCode    = new String(dataRec, 1+10+30+8+1+13+20+1, 10);
        napbuNo     = new String(dataRec, 1+10+30+8+1+13+20+1+10, 30);
        dBankCd     = new String(dataRec, 1+10+30+8+1+13+20+1+10+30, 3);
        acctNo      = new String(dataRec, 1+10+30+8+1+13+20+1+10+30+3, 20);
        prfTp       = new String(dataRec, 1+10+30+8+1+13+20+1+10+30+3+20, 1);
        expNm       = new String(dataRec, 1+10+30+8+1+13+20+1+10+30+3+20+1, 4);
        prfSize     = new String(dataRec, 1+10+30+8+1+13+20+1+10+30+3+20+1+4, 7);
        rtnCd       = new String(dataRec, 1+10+30+8+1+13+20+1+10+30+3+20+1+4+7, 4);
        dFiller     = new String(dataRec, 1+10+30+8+1+13+20+1+10+30+3+20+1+4+7+4, 237);
    }

    public List<DtoPrf> makeDtoList(String srcFilePath){
        FileInputStream fin = null;
        FfmPrfRep prfRep;
        byte[] headBuf = null, dataBuf;
        List<DtoPrf> prfList = new ArrayList<>();
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
                    prfRep = new FfmPrfRep(dataBuf, headBuf);
                    prfList.add(new DtoPrf(prfRep.gethFacCd().trim(), prfRep.gethRegDt().trim(), prfRep.getdRegDt().trim(), prfRep.getSocTp().trim(),
                            prfRep.getSocId().trim(), prfRep.getRtYn().trim(), prfRep.getNapbuNo().trim(), prfRep.getdBankCd().trim(),
                            prfRep.getAcctNo().trim(), prfRep.getPrfTp().trim(), prfRep.getExpNm().trim(), "", "", prfRep.getRtnCd().trim()));
                }
            }//while end.
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[makeDtoList] "+e);
            return null;
        } finally {
            if(fin!=null) try {fin.close();} catch (IOException ignored) {}
        }

        return prfList;
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

    public String getRtnCd() {
        return rtnCd;
    }

    public void setRtnCd(String rtnCd) {
        this.rtnCd = rtnCd;
    }

    public String getdFiller() {
        return dFiller;
    }

    public void setdFiller(String dFiller) {
        this.dFiller = dFiller;
    }
}
