package com.hyphen.fbnk.bbnk.msg;

import com.hyphen.fbnk.bbnk.Util;
import com.hyphen.fbnk.bbnk.dto.DtoReg;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FfmRegAg {
    private static final Log log = LogFactory.getLog(FfmRegAg.class);
    private final String encodeTp = "euc-kr";

    private String hRecTp   = "H";          //1
    private String hDataSeq = "00000000";   //8
    private String hFacCd   = "";           //10
    private String hFileNm   = "";           //8
    private String sendDt   = "";           //6
    private String rndNo    = "";           //10
    private String hFiller  = "";           //77

    private String dRecTp   = "R";      //1
    private String dDataSeq = "";       //8
    private String dFacCd   = "";       //10
    private String reqDt    = "";       //6
    private String regTp    = "";       //1
    private String napbuNo  = "";       //10
    private String nFiller  = "";       //10
    private String bankCd   = "";       //3
    private String bFiller  = "0000";   //4
    private String acctNo   = "";       //16
    private String socId    = "";       //16
    private String branchCd = "";       //4
    private String amtTp    = "";       //2
    private String result   = "";       //1
    private String rtnCd    = "";       //4
    private String dFiller1 = "";       //1
    private String tellNo   = "";       //12
    private String socIdTp  = "";       //1
    private String prfTp    = "";       //1
    private String dFiller  = "";       //9

    private String tRecTp   = "T";          //1
    private String tDataSeq = "99999999";   //8
    private String tFacCd   = "";           //10
    private String tFileNm  = "";           //8
    private String reqCnt   = "";           //8
    private String regCnt   = "";           //8
    private String cngCnt   = "";           //8
    private String disCnt   = "";           //8
    private String cancCnt  = "";           //8
    private String tFiller  = "";           //43
    private String macId    = "";           //10

    public FfmRegAg() {}
    public FfmRegAg(String facCd, String rndNo, int dSeq, String reqDt, String regTp, String napbuNo, String bankCd, String acctNo, String socId, String result, String rtnCd, String socIdTp, String prfTp) {
        this.hFacCd = facCd;
        this.sendDt = Util.getCurDtTm().substring(2, 8);
        this.rndNo = rndNo;
        this.dDataSeq = String.format("%08d", dSeq);;
        this.dFacCd = facCd;
        this.reqDt = reqDt.length()==8 ? reqDt.substring(2) : reqDt;
        this.regTp = regTp;
        this.napbuNo = napbuNo;
        try{
            this.bankCd = String.format("%03d", Integer.parseInt(bankCd));
        } catch (NumberFormatException e){this.bankCd = bankCd;}
        this.acctNo = acctNo;
        this.socId = socId;
        this.result = result;
        this.rtnCd = rtnCd;
        this.socIdTp = socIdTp;
        this.prfTp = prfTp;
        this.tFacCd = facCd;
    }

    public FfmRegAg(byte[] dataRec, byte[] headRec) {
        hRecTp   = new String(headRec, 0, 1);
        hDataSeq = new String(headRec, 1, 8);
        hFacCd   = new String(headRec, 1+8, 10);
        hFileNm  = new String(headRec, 1+8+10, 8);
        sendDt   = new String(headRec, 1+8+10+8, 6);
        rndNo    = new String(headRec, 1+8+10+8+6, 10);
        hFiller  = new String(headRec, 1+8+10+8+6+10, 77);

        dRecTp   = new String(dataRec, 0, 1);
        dDataSeq = new String(dataRec, 1, 8);
        dFacCd   = new String(dataRec, 1+8, 10);
        reqDt    = new String(dataRec, 1+8+10, 6);
        regTp    = new String(dataRec, 1+8+10+6, 1);
        napbuNo  = new String(dataRec, 1+8+10+6+1, 10);
        nFiller  = new String(dataRec, 1+8+10+6+1+10, 10);
        bankCd   = new String(dataRec, 1+8+10+6+1+10+10, 3);
        bFiller  = new String(dataRec, 1+8+10+6+1+10+10+3, 4);
        acctNo   = new String(dataRec, 1+8+10+6+1+10+10+3+4, 16);
        socId    = new String(dataRec, 1+8+10+6+1+10+10+3+4+16, 16);
        branchCd = new String(dataRec, 1+8+10+6+1+10+10+3+4+16+16, 4);
        amtTp    = new String(dataRec, 1+8+10+6+1+10+10+3+4+16+16+4, 2);
        result   = new String(dataRec, 1+8+10+6+1+10+10+3+4+16+16+4+2, 1);
        rtnCd    = new String(dataRec, 1+8+10+6+1+10+10+3+4+16+16+4+2+1, 4);
        dFiller1 = new String(dataRec, 1+8+10+6+1+10+10+3+4+16+16+4+2+1+4, 1);
        tellNo   = new String(dataRec, 1+8+10+6+1+10+10+3+4+16+16+4+2+1+4+1, 12);
        socIdTp  = new String(dataRec, 1+8+10+6+1+10+10+3+4+16+16+4+2+1+4+1+12, 1);
        prfTp    = new String(dataRec, 1+8+10+6+1+10+10+3+4+16+16+4+2+1+4+1+12+1, 1);
        dFiller  = new String(dataRec, 1+8+10+6+1+10+10+3+4+16+16+4+2+1+4+1+12+1+1, 9);
    }

    public byte[] getHead(){
        String recBuf = String.format("%-1s", hRecTp)+String.format("%-8s", hDataSeq)+String.format("%-10s", hFacCd)+String.format("%-8s", hFileNm)+
                String.format("%-6s", sendDt)+String.format("%-10s", rndNo)+String.format("%-77s", hFiller)+"\r\n";

        return recBuf.getBytes();
    }

    public byte[] getData(){
        byte[] rtnBuf = null;
        String recBuf = String.format("%-1s", dRecTp)+String.format("%-8s", dDataSeq)+String.format("%-10s", dFacCd)+String.format("%-6s", reqDt)+
                String.format("%-1s", regTp)+String.format("%-10s", napbuNo)+String.format("%-10s", nFiller)+String.format("%-3s", bankCd)+
                String.format("%-4s", bFiller)+String.format("%-16s", acctNo)+String.format("%-16s", socId)+String.format("%-4s", branchCd)+
                String.format("%-2s", amtTp)+String.format("%-1s", result)+String.format("%-4s", rtnCd)+String.format("%-1s", dFiller1)+
                String.format("%-12s", tellNo)+String.format("%-1s", socIdTp)+String.format("%-1s", prfTp)+String.format("%-9s", dFiller)+"\r\n";
        try {rtnBuf = recBuf.getBytes(encodeTp);} catch (UnsupportedEncodingException ignored) {}

        return rtnBuf;
    }

    public byte[] getEnd(int dataCnt, int regCnt, int disCnt){
        this.reqCnt  = String.format("%08d", dataCnt);
        this.regCnt  = String.format("%08d", regCnt);
        this.disCnt  = String.format("%08d", disCnt);
        String recBuf = String.format("%-1s", tRecTp)+String.format("%-8s", tDataSeq)+String.format("%-10s", tFacCd)+String.format("%-8s", tFileNm)+
                String.format("%-8s", reqCnt)+String.format("%-8s", this.regCnt)+String.format("%-8s", cngCnt)+String.format("%-8s", this.disCnt)+
                String.format("%-8s", cancCnt)+String.format("%-43s", tFiller)+String.format("%-10s", macId)+"\r\n";

        return recBuf.getBytes();
    }

    public boolean makeFile(List<DtoReg> dtoRegList, String desFilePath){
        FileOutputStream fout = null;
        FfmRegAg regAg;
        try {
            fout = new FileOutputStream(desFilePath);
            int dataSeq = 0, regCnt=0, disCnt=0;
            for (DtoReg dtoReg : dtoRegList) {
                dataSeq++;
                regAg = new FfmRegAg(dtoReg.getFacCd(), dtoReg.gethExtra(), dataSeq, Util.getCurDtTm().substring(2, 8), dtoReg.getRegTp(), dtoReg.getNapbuNo(),
                        dtoReg.getdBankCd(), dtoReg.getAcctNo(), dtoReg.getSocId(), "", "", dtoReg.getSocIdTp(), dtoReg.getPrfTp());
                if(dtoReg.getRegTp().equals("1"))  regCnt++;
                else if(dtoReg.getRegTp().equals("2")) disCnt++ ;
                //make head
                if(dataSeq==1) fout.write(regAg.getHead());
                //make data
                fout.write(regAg.getData());
                //make end
                if(dataSeq == dtoRegList.size()) fout.write(regAg.getEnd(dataSeq, regCnt, disCnt));
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
        FfmRegAg regAg;
        byte[] headBuf=null, dataBuf;
        List<DtoReg> regList = new ArrayList<>();
        try {
            fin = new FileInputStream(srcFilePath);
            while (true){
                dataBuf = Util.readLineByte(fin);
                if(dataBuf.length==0)	break;

                if(new String(dataBuf, 0, 1).equals("H")){
                    headBuf = dataBuf;
                    log.debug("[makeDtoList] headBuf=["+new String(headBuf)+"]("+headBuf.length+")");
                }
                else if(new String(dataBuf, 0, 1).equals("R")) {
                    regAg = new FfmRegAg(dataBuf, headBuf);
                    regList.add(new DtoReg(regAg.gethFacCd().trim(), regAg.getBankCd().trim(), regAg.getRndNo().trim(), regAg.getBankCd().trim(), regAg.getAcctNo().trim(),
                            regAg.getRegTp().trim(), regAg.getResult().trim(), regAg.getRtnCd().trim(), regAg.getSocId().trim(), regAg.getNapbuNo().trim(), "",
                            regAg.getSocIdTp().trim(), regAg.getPrfTp().trim(), ""));
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

    public String gethDataSeq() {
        return hDataSeq;
    }

    public void sethDataSeq(String hDataSeq) {
        this.hDataSeq = hDataSeq;
    }

    public String gethFacCd() {
        return hFacCd;
    }

    public void sethFacCd(String hFacCd) {
        this.hFacCd = hFacCd;
    }

    public String gethFileNm() {
        return hFileNm;
    }

    public void sethFileNm(String hFileNm) {
        this.hFileNm = hFileNm;
    }

    public String getSendDt() {
        return sendDt;
    }

    public void setSendDt(String sendDt) {
        this.sendDt = sendDt;
    }

    public String getRndNo() {
        return rndNo;
    }

    public void setRndNo(String rndNo) {
        this.rndNo = rndNo;
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

    public String getdDataSeq() {
        return dDataSeq;
    }

    public void setdDataSeq(String dDataSeq) {
        this.dDataSeq = dDataSeq;
    }

    public String getdFacCd() {
        return dFacCd;
    }

    public void setdFacCd(String dFacCd) {
        this.dFacCd = dFacCd;
    }

    public String getReqDt() {
        return reqDt;
    }

    public void setReqDt(String reqDt) {
        this.reqDt = reqDt;
    }

    public String getRegTp() {
        return regTp;
    }

    public void setRegTp(String regTp) {
        this.regTp = regTp;
    }

    public String getNapbuNo() {
        return napbuNo;
    }

    public void setNapbuNo(String napbuNo) {
        this.napbuNo = napbuNo;
    }

    public String getnFiller() {
        return nFiller;
    }

    public void setnFiller(String nFiller) {
        this.nFiller = nFiller;
    }

    public String getBankCd() {
        return bankCd;
    }

    public void setBankCd(String bankCd) {
        this.bankCd = bankCd;
    }

    public String getbFiller() {
        return bFiller;
    }

    public void setbFiller(String bFiller) {
        this.bFiller = bFiller;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getSocId() {
        return socId;
    }

    public void setSocId(String socId) {
        this.socId = socId;
    }

    public String getBranchCd() {
        return branchCd;
    }

    public void setBranchCd(String branchCd) {
        this.branchCd = branchCd;
    }

    public String getAmtTp() {
        return amtTp;
    }

    public void setAmtTp(String amtTp) {
        this.amtTp = amtTp;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRtnCd() {
        return rtnCd;
    }

    public void setRtnCd(String rtnCd) {
        this.rtnCd = rtnCd;
    }

    public String getdFiller1() {
        return dFiller1;
    }

    public void setdFiller1(String dFiller1) {
        this.dFiller1 = dFiller1;
    }

    public String getTellNo() {
        return tellNo;
    }

    public void setTellNo(String tellNo) {
        this.tellNo = tellNo;
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

    public String gettDataSeq() {
        return tDataSeq;
    }

    public void settDataSeq(String tDataSeq) {
        this.tDataSeq = tDataSeq;
    }

    public String gettFacCd() {
        return tFacCd;
    }

    public void settFacCd(String tFacCd) {
        this.tFacCd = tFacCd;
    }

    public String gettFileNm() {
        return tFileNm;
    }

    public void settFileNm(String tFileNm) {
        this.tFileNm = tFileNm;
    }

    public String getReqCnt() {
        return reqCnt;
    }

    public void setReqCnt(String reqCnt) {
        this.reqCnt = reqCnt;
    }

    public String getRegCnt() {
        return regCnt;
    }

    public void setRegCnt(String regCnt) {
        this.regCnt = regCnt;
    }

    public String getCngCnt() {
        return cngCnt;
    }

    public void setCngCnt(String cngCnt) {
        this.cngCnt = cngCnt;
    }

    public String getDisCnt() {
        return disCnt;
    }

    public void setDisCnt(String disCnt) {
        this.disCnt = disCnt;
    }

    public String getCancCnt() {
        return cancCnt;
    }

    public void setCancCnt(String cancCnt) {
        this.cancCnt = cancCnt;
    }

    public String gettFiller() {
        return tFiller;
    }

    public void settFiller(String tFiller) {
        this.tFiller = tFiller;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }
}
