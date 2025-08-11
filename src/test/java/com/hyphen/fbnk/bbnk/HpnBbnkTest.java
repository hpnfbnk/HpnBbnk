package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.dto.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HpnBbnkTest {
    HpnBbnk hpnBbnk;

    private String dbDriver = "org.gjt.mm.mysql.Driver";
    private String dbUrl = "jdbc:mysql://172.32.12.117:3306/test_db";
    private String dbUser = "root";
    private String dbPass = "mypwd";

    @Before
    public void setup(){
        this.hpnBbnk = new HpnBbnk();    //기본
        //this.hpnBbnk = new HpnBbnk(true, false);    //압축
        //this.hpnBbnk = new HpnBbnk("121.138.30.10", "121.138.30.31", 29994, "121.138.30.10", 29995, false, "512K", false);
    }

    //@Test
    public void sendData() {
        String sendCd   = "A002";
        String recvCd   = "0181";
        String infoCd   = "R00";
        String filePath = "./sample/sample_R00_SND.txt";
        String runMode  = "T";

        //송신
        //boolean result = hpnBbnk.sendData("A002","0181", "R00", "./sample/sample_R00_SND.txt", "T", "");
        //boolean result = hpnBbnk.sendData("A002","0181", "R00", "./sample/sample_R00_SND.txt", "T", "tcUF3A2WRsW1BdQNlrZlsQ");
        boolean result = hpnBbnk.sendData(sendCd,recvCd, infoCd, filePath, runMode);
        if(result) System.out.println("hpnBbnk.sendData() : SUCCESS");
        else System.out.println("hpnBbnk.sendData() : FAIL");

        //송신내역 DB 기록
        /*
        DtoDBSRHst srhst = new DtoDBSRHst(infoCd, sendCd, recvCd, "001", new File(filePath).getName(), "S", result);
        System.out.println(srhst);
        result = new ProcBbdata().srHst2DB(srhst, dbDriver, dbUrl, dbUser, dbPass);
        if(result) System.out.println("hpnBbnk.srHst2DB() : SUCCESS");
        else System.out.println("hpnBbnk.srHst2DB() : FAIL");
         */

        assertTrue(result);
    }

    //@Test
    public void sendDataMulti() {
        boolean result;
        List<DtoFileList> sendDataLists = new ArrayList<>();
        sendDataLists.add(new DtoFileList("20240415", "R00", "A002", "0181", "001", "./sample/ABRQ20190323_B10_011_123456_KB1.001", false, ""));
        sendDataLists.add(new DtoFileList("20240415", "200", "A002", "0181", "001", "./sample/ABRQ20190323_C10_004_BK123456_KB3.001", false, ""));
        //sendDataLists.add(new DtoFileList("20240415", "Y00", "A002", "0181", "001", "./sample/prf.dat", false, ""));
        //sendDataLists.add(new DtoFileList("20220323", "R00", "A002", "0181", "001", "./sample/ABRQ20190323_B10_011_123456_KB1.001", false, "tcUF3A2WRsW1BdQNlrZlsQ"));
        //sendDataLists.add(new DtoFileList("20220323", "200", "A003", "0181", "001", "./sample/ABRQ20190323_C10_004_BK123456_KB3.001", false, "123456789"));
        //sendDataLists.add(new DtoFileList("20220323", "Y00", "A002", "0181", "001", "./sample/prf.dat", false));
        //sendDataLists.add(new DtoFileList("20220323", "R00", "A001", "0181", "001", "./sample/ABRQ20190323_B10_011_123456_KB1.001", false));
        //sendDataLists.add(new DtoFileList("20220323", "200", "A002", "0181", "001", "./sample/ABRQ20190323_C10_004_BK123456_KB3.001", false));
        //sendDataLists.add(new DtoFileList("20220323", "Y00", "A001", "0181", "001", "./sample/prf.dat", false));
        List<DtoFileList> resultLists = hpnBbnk.sendDataMulti("A002", sendDataLists, "", "T");
        for (DtoFileList resultList : resultLists){
            System.out.println("hpnBbnk.sendDataMulti : "+resultList);
            //송신내역 DB 기록
            //result = new ProcBbdata().srHst2DB(resultList, "S", dbDriver, dbUrl, dbUser, dbPass);
            //if(result) System.out.println("hpnBbnk.srHst2DB() : SUCCESS"); else System.out.println("hpnBbnk.srHst2DB() : FAIL");
        }

        assertNotNull(resultLists);
    }

    //@Test
    public void getRecvList() {
        //일반적인 조건으로 수신목록 조회(최근1주일사이에 조회자가 아직 한번도 수신하지 않은 것들에 대한 수신목록 조회요청)
        List<DtoSRList> dtoSRLists = hpnBbnk.getRecvList("A002", "T", "");
        //List<DtoSRList> dtoSRLists = hpnBbnk.getRecvList("A002", "T", "tcUF3A2WRsW1BdQNlrZlsQ");
        //List<DtoSRList> dtoSRLists = hpnBbnk.getRecvList("A002", "T");
        if(dtoSRLists.isEmpty())
            System.out.println("hpnBbnk.getRecvList : NO_DATA");
        else for (DtoSRList dtoSRList : dtoSRLists)
            System.out.println("hpnBbnk.getRecvList : "+dtoSRList.toString());
        assertNotNull(dtoSRLists);
    }

    //@Test
    public void recvList() {
        //목록조회
        List<DtoSRList> dtoSRLists = hpnBbnk.recvList("4802", "9999", "ZZZ", "20250708", "20250708", "L", "A", "T", "");
        //List<DtoSRList> dtoSRLists = hpnBbnk.recvList("A002", "9999", "ZZZ", "20230606", "20230613", "M", "E", "T", "tcUF3A2WRsW1BdQNlrZlsQ");
        //List<DtoSRList> dtoSRLists = hpnBbnk.recvList("A002", "9999", "ZZZ", "20230606", "20230613", "M", "E", "T");
        if(dtoSRLists.isEmpty())
            System.out.println("hpnBbnk.recvList : NO_DATA");
        else
            for (DtoSRList dtoSRList : dtoSRLists) System.out.println("hpnBbnk.recvList : "+dtoSRList.toString());
        assertNotNull(dtoSRLists);
    }

    //@Test
    public void recvData() {
        //수신
        boolean result = hpnBbnk.recvData("0181", "A002", "R00", "001", "20240611", "./sample/A002_RCV.txt", "T", "");
        //boolean result = hpnBbnk.recvData("0090", "TC01", "R00", "001", "20240828", "./sample/TC01_RCV.txt", "T", "");
        //boolean result = hpnBbnk.recvData("0181", "A002", "R00", "001", "20230613", "./sample/A002_RCV.txt", "T", "tcUF3A2WRsW1BdQNlrZlsQ");
        //boolean result = hpnBbnk.recvData("0181", "A002", "R00", "001", "20230613", "./sample/A002_RCV.txt", "T");
        if(result) System.out.println("hpnBbnk.recvData : SUCCESS");
        else System.out.println("hpnBbnk.recvData : FAIL");
        //수신내역 DB 기록
        /*
        DtoDBSRHst srhst = new DtoDBSRHst("R00", "0011", "A002", "001", new File("./sample/A002_RCV.txt").getName(), "R", result);
        System.out.println(srhst);
        result = new ProcBbdata().srHst2DB(srhst, dbDriver, dbUrl, dbUser, dbPass);
        if(result) System.out.println("hpnBbnk.srHst2DB() : SUCCESS");
        else System.out.println("hpnBbnk.srHst2DB() : FAIL");
        */
        assertTrue(result);
    }

    //@Test
    public void sendDataMultiKEDU() {
        List<DtoFileList> sendDataLists = new ArrayList<>();
        sendDataLists.add(new DtoFileList("20190323", "KB1", "B10", "011", "001", "./sample/ABRQ20190323_B10_011_123456_KB1.001", false, ""));
        sendDataLists.add(new DtoFileList("20190323", "KB3", "C10", "004", "001", "./sample/ABRQ20190323_C10_004_BK123456_KB3.001", false, ""));
        //sendDataLists.add(new DtoFileList("20220331", "KB3", "C10", "004", "001", "./sample/ABRQ20220331_C10_004_BK123456_KB3.001", false));
        //sendDataLists.add(new DtoFileList("20220331", "KB1", "B10", "011", "001", "./sample/ABRQ20220331_B10_0011_123456_KB1.001", false));
        //sendDataLists.add(new DtoFileList("20220331", "KB3", "B10", "011", "001", "./sample/ABRQ20220331_B10_0011_123456_KB3.001", false));
        //sendDataLists.add(new DtoFileList("20220331", "KB3", "B10", "012", "001", "./sample/ABRQ20220331_B10_012_0123401234_KB3.001", false));

        List<DtoFileList> resultLists = hpnBbnk.sendDataMulti("KEDU", sendDataLists, "KEDU", "T");
        for (DtoFileList resultList : resultLists)
            System.out.println("hpnBbnk.sendDataMultiKEDU : "+resultList);
        assertNotNull(resultLists);
    }

    //@Test
    public void recvDataMulti(){
        boolean result;
        List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti("A002", "1096", "C01", "20250625", "20250625", "A", "", "./sample", "T", "");
        //List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti("A002", "1096", "C01", "20250625", "20250625", "A", "", "./sample", "T");
        //List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti("A002", "9999", "ZZZ", "20230613", "20230613", "A", "", "./sample", "T", "tcUF3A2WRsW1BdQNlrZlsQ");
        //List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti("A002", "9999", "ZZZ", "20230613", "20230613", "A", "", "./sample", "T");
        if(dtoFileLists.isEmpty())
            System.out.println("hpnBbnk.recvDataMulti : NO_DATA");
        else {
            for (DtoFileList dtoFileList : dtoFileLists) {
                System.out.println("hpnBbnk.recvDataMulti : " + dtoFileList);
                //수신내역 DB 기록
                result = new ProcBbdata().srHst2DB(dtoFileList, "R", dbDriver, dbUrl, dbUser, dbPass);
                if (result) System.out.println("hpnBbnk.srHst2DB() : SUCCESS");
                else System.out.println("hpnBbnk.srHst2DB() : FAIL");
            }
            assertNotNull(dtoFileLists);
        }
    }

    //@Test
    public void recvDataMultiKEDU(){
        List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti("KEDU", "9999", "ZZZ", "20220410", "20220414", "A", "KEDU", "./sample", "T", "");
        if(dtoFileLists.isEmpty())
            System.out.println("hpnBbnk.recvDataMulti : NO_DATA");
        else
            for(DtoFileList dtoFileList : dtoFileLists) System.out.println("hpnBbnk.recvDataMulti : "+dtoFileList);
        assertNotNull(dtoFileLists);
    }

    //@Test
    public void recvDataDB(){
        String filePath = "./sample/C011096A002.txt";
        String infoCd = "C01";
        String dbDriver = "org.gjt.mm.mysql.Driver";
        //String dbUrl = "jdbc:mysql://localhost:3306/test";
        String dbUrl = "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Seoul&useSSL=false";
        String dbUser = "myid";
        //String dbPass = "mypwd";
        String dbPass = "Mypwd`0203";
        boolean result = hpnBbnk.set2DB(filePath, infoCd, dbDriver, dbUrl, dbUser, dbPass);
        assertTrue(result);
    }

    //@Test
    public void recvData2DB(){
        String sendCd   = "1096";
        String recvCd   = "A002";
        String infoCd   = "C01";
        String seqNo    = "001";
        String sendDt   = "20240617";
        String filePath = "./sample/"+infoCd+sendCd+recvCd+".txt";
        String runMode  = "T";
        String dbDriver = "org.gjt.mm.mysql.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Seoul&useSSL=false";
        //String dbUrl = "jdbc:mysql://localhost:3306/test";
        String dbUser = "myid";
        String dbPass = "Mypwd`0203";

        boolean result = hpnBbnk.recvData2DB(sendCd, recvCd, infoCd, seqNo, sendDt, filePath, runMode, dbDriver, dbUrl, dbUser, dbPass, "");
        //boolean result = hpnBbnk.recvData2DB(sendCd, recvCd, infoCd, seqNo, sendDt, filePath, runMode, dbDriver, dbUrl, dbUser, dbPass, "tcUF3A2WRsW1BdQNlrZlsQ");
        //boolean result = hpnBbnk.recvData2DB(sendCd, recvCd, infoCd, seqNo, sendDt, filePath, runMode, dbDriver, dbUrl, dbUser, dbPass);
        assertTrue(result);
    }

    //@Test
    public void recvDataMulti2DB(){
        String sendCd   = "1096";
        String recvCd   = "A001";
        String infoCd   = "ZZZ";
        String sendDt   = "20250811";
        String runMode  = "T";
        String dbDriver = "org.gjt.mm.mysql.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Seoul&useSSL=false";
        String dbUser = "myid";
        String dbPass = "Mypwd`0203";
        String dbType   = "DZN";
        String dbTblNm  = "";

        List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti2DB(recvCd, sendCd, infoCd, sendDt, sendDt, "E", "", "./sample", runMode, dbDriver, dbUrl, dbUser, dbPass, "");
        //List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti2DB(recvCd, sendCd, infoCd, sendDt, sendDt, "E", "", "./sample", runMode, dbDriver, dbUrl, dbUser, dbPass, "tcUF3A2WRsW1BdQNlrZlsQ");
        //List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti2DB(recvCd, sendCd, infoCd, sendDt, sendDt, "E", "", "./sample", runMode, dbDriver, dbUrl, dbUser, dbPass);
        //List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti2DB(recvCd, sendCd, infoCd, sendDt, sendDt, "E", "", "./sample", runMode, dbDriver, dbUrl, dbUser, dbPass, "", dbType, dbTblNm, "", "");
        if(dtoFileLists.isEmpty())
            System.out.println("hpnBbnk.recvDataMulti2DB : NO_DATA");
        else
            for(DtoFileList dtoFileList : dtoFileLists) System.out.println("hpnBbnk.recvDataMulti2DB : "+dtoFileList);
        assertNotNull(dtoFileLists);
    }

    //@Test
    public void makeDataFile() {
        boolean result;
        /*
        List<DtoReg> dtoRegList = new ArrayList<>();
        dtoRegList.add(new DtoReg("HPN001", "089", "", "004", "123465789012", "1", "", "", "110203", "ABBT0001", "업무PPK1", "1", "1", ""));
        dtoRegList.add(new DtoReg("HPN001", "089", "", "011", "883465789012", "1", "", "", "110203", "ABBT0002", "업무PPK2", "1", "1", ""));
        result = hpnBbnk.makeDataFile("R00", dtoRegList, "./sample/R00.test");
        */
        /*
        List<DtoBill> dtoBillList = new ArrayList<>();
        dtoBillList.add(new DtoBill("HPN00387", "081", "20220628", "123456789012", "", "", "", "20220629-1",
                "003", "12345678901234", 1500L, "", "", 0L, "NTP0001", "", "당근10kg", "", "당근농장A"));
        dtoBillList.add(new DtoBill("HPN00387", "081", "20220628", "123456789012", "", "", "", "20220629-1",
                "004", "123456789004", 1500L, "", "", 0L,"NTP0002", "", "당근102kg사주세요", "", "당근농장B"));
        result = hpnBbnk.makeDataFile("200", dtoBillList, "./sample/make200.txt");
         */
        /*
        List<DtoPay> dtoPayList = new ArrayList<>();
        dtoPayList.add(new DtoPay("HPN00387", "081", "20220628", "123456789012", "", "", "", "1", "20220629-1",
                "003", "12345678901234", 1500L, "", "", 0L, "", "11월-급여", "", "당근농장A"));
        dtoPayList.add(new DtoPay("HPN00387", "081", "20220628", "123456789012", "", "", "", "1", "20220629-1",
                "004", "123456789004", 1500L, "", "", 0L, "", "12월-당기성과급여", "", "당근농장B"));
        result = hpnBbnk.makeDataFile("300", dtoPayList, "./sample/make300.txt");
        */
        /*
        List<DtoPay> dtoPayList = new ArrayList<>();
        dtoPayList.add(new DtoPay("HPN00387", "081", "20220628", "123456789012", "", "", "", "1", "20220629-1",
                "003", "12345678901234", 1500L, "", "", 0L, "", "11월-급여", "", "당근농장A", 1, "(주)VBC투자은행"));
        dtoPayList.add(new DtoPay("HPN00387", "081", "20220628", "123456789012", "", "", "", "1", "20220629-1",
                "004", "123456789004", 1500L, "", "", 0L, "", "12월-당기성과급여", "", "당근농장B", 2, "(주)VBC투자은행"));
        result = hpnBbnk.makeDataFile("BR3", dtoPayList, "./sample/makeBR3.txt");
        */

        List<DtoPrf> dtoPrfList = new ArrayList<>();
        dtoPrfList.add(new DtoPrf("FE00032", "20221011", "20221011", "0", "930115", "N", "KP20221011-1",
                "003", "123456789012", "1", "png", "N", "./sample/prf.png", ""));
        dtoPrfList.add(new DtoPrf("FE00032", "20221011", "20221011", "0", "930115", "N", "KP20221011-2",
                "004", "123456789014", "1", "", "Y", "", ""));
        dtoPrfList.add(new DtoPrf("FE00032", "20221011", "20221011", "0", "830815", "N", "KP20221011-3",
                "005", "123456789015", "1", "txt", "N", "./sample/prf.txt", ""));
        result = hpnBbnk.makeDataFile("Y00", dtoPrfList, "./sample/makeY00.prf");

        /*
        List<DtoAftPrf> dtoAftPrfList = new ArrayList<>();
        dtoAftPrfList.add(new DtoAftPrf("011", "FE00032", "20221019", "KSA001", "1", "1008800023", "KP20221011-1",
                "011", "123456789012", "20221017", "1", "Y", "png", "./sample/prf.png", ""));
        dtoAftPrfList.add(new DtoAftPrf("011", "FE00032", "20221019", "KSA001", "1", "1008800028", "KP20221011-2",
                "012", "123456789013", "20221015", "6", "Y", "txt", "./sample/prf.txt", ""));
        dtoAftPrfList.add(new DtoAftPrf("011", "FE00032", "20221019", "KSA001", "1", "1008800031", "KP20221011-3",
                "011", "123456789014", "20221016", "1", "Y", "png", "./sample/prf.png", ""));
        result = hpnBbnk.makeDataFile("Y06", dtoAftPrfList, "./sample/makeY06.prf");
        */
        assertTrue(result);
    }

    //@Test
    public void sendDataDto(){
        boolean result = false;
        /*
        List<DtoReg> dtoRegList = new ArrayList<>();
        dtoRegList.add(new DtoReg("HPN001", "089", "", "004", "123465789012", "1", "", "", "110203", "ABBT0001", "업무PPK1", "1", "1", ""));
        dtoRegList.add(new DtoReg("HPN001", "089", "", "011", "883465789012", "1", "", "", "110203", "ABBT0002", "업무PPK2", "1", "1", ""));
        result = hpnBbnk.sendDataDto("A004", "0084", "R00", dtoRegList, "./sample", "T", "");
        */
        /*
        List<DtoBill> dtoBillList = new ArrayList<>();
        dtoBillList.add(new DtoBill("HPN00387", "081", "20220628", "123456789012", "", "", "", "20220629-1",
                "003", "12345678901234", 1500L, "", "", 0L, "NTP0001", "", "당근10kg", "", "당근농장A"));
        dtoBillList.add(new DtoBill("HPN00387", "081", "20220628", "123456789012", "", "", "", "20220629-1",
                "004", "123456789004", 1500L, "", "", 0L, "NTP0002", "", "당근102kg사주세요", "", "당근농장B"));
        result = hpnBbnk.sendDataDto("A004", "0084", "200", dtoBillList, "./sample", "T", "");
        */
        /*
        List<DtoPay> dtoPayList = new ArrayList<>();
        dtoPayList.add(new DtoPay("HPN00387", "081", "20220628", "123456789012", "", "", "", "1", "20220629-1",
                "003", "12345678901234", 1500L, "", "", 0L, "", "11월-급여", "", "당근농장A"));
        dtoPayList.add(new DtoPay("HPN00387", "081", "20220628", "123456789012", "", "", "", "1", "20220629-1",
                "004", "123456789004", 1500L, "", "", 0L, "", "12월-당기성과급여", "", "당근농장B"));
        result = hpnBbnk.sendDataDto("A004", "0081", "300", dtoPayList, "./sample", "T", "");
        */
        /*
        List<DtoPay> dtoPayList = new ArrayList<>();
        dtoPayList.add(new DtoPay("HPN00387", "081", "", "123456789012", "", "", "", "", "20220629-1",
                "003", "12345678901234", 1300L, "", "", 0L, "", "11월-급여", "", "당근농장A",
                46587, "(주)당근ENCG-코퍼레이션"));
        dtoPayList.add(new DtoPay("HPN00387", "081", "", "123456789012", "", "", "", "", "20220629-1",
                "004", "123456789004", 1400L, "", "", 0L, "", "12월-당기성과급여", "", "당근농장B",
                46588, "(주)당근ENCG-코퍼레이션"));
        result = hpnBbnk.sendDataDto("A002", "0995", "BR3", dtoPayList, "./sample", "T", "");
        */
        //result = hpnBbnk.sendDataDto("A002", "0995", "BR3", dtoPayList, "./sample", "T", "tcUF3A2WRsW1BdQNlrZlsQ");
        //result = hpnBbnk.sendDataDto("A002", "0995", "BR3", dtoPayList, "./sample", "T");
        /*
        List<DtoPrf> dtoPrfList = new ArrayList<>();
        dtoPrfList.add(new DtoPrf("FE00032", "20221011", "20221011", "0", "930115", "N", "KP20221011-1",
                "081", "123456789012", "1", "png", "N", "./sample/prf.png", ""));
        dtoPrfList.add(new DtoPrf("FE00032", "20221011", "20221011", "0", "930115", "N", "KP20221011-2",
                "081", "123456789014", "1", "", "Y", "", ""));
        dtoPrfList.add(new DtoPrf("FE00032", "20221011", "20221011", "0", "830815", "N", "KP20221011-3",
                "081", "123456789015", "1", "txt", "N", "./sample/prf.txt", ""));
        result = hpnBbnk.sendDataDto("A002", "0181", "Y00", dtoPrfList, "./sample", "T", "");
        */

        List<DtoPrf> dtoPrfList = new ArrayList<>();
        dtoPrfList.add(new DtoPrf("08000180", "20230908", "20230908", "0", "820716", "N", "_NKC001",
                "039", "600220154278", "1", "png", "N", "./sample/prf.png", ""));
        dtoPrfList.add(new DtoPrf("08000180", "20230908", "20230908", "0", "510315", "N", "_NKC002",
                "039", "537220096995", "1", "txt", "N", "./sample/prf.txt", ""));
        dtoPrfList.add(new DtoPrf("08000180", "20230908", "20230908", "0", "530528", "N", "_NKC003",
                "039", "600220104143", "1", "png", "N", "./sample/prf.png", ""));
        dtoPrfList.add(new DtoPrf("08000180", "20230908", "20230908", "0", "721001", "N", "_NKC004",
                "039", "600210164997", "1", "png", "N", "./sample/prf.png", ""));
        dtoPrfList.add(new DtoPrf("08000180", "20230908", "20230908", "0", "660503", "N", "_NKC005",
                "039", "548220334870", "1", "png", "N", "./sample/prf.png", ""));
        dtoPrfList.add(new DtoPrf("08000180", "20230908", "20230908", "0", "921001", "N", "_NKC006",
                "039", "910210164997", "1", "png", "Y", "", ""));
        result = hpnBbnk.sendDataDto("7297", "0997", "AY0", dtoPrfList, "./sample", "T", "");

        /*
        List<DtoAftPrf> dtoAftPrfList = new ArrayList<>();
        dtoAftPrfList.add(new DtoAftPrf("011", "FE00032", "20221019", "KSA001", "1", "1008800023", "KP20221011-1",
                "011", "123456789012", "20221017", "1", "Y", "png", "./sample/prf.png", ""));
        dtoAftPrfList.add(new DtoAftPrf("011", "FE00032", "20221019", "KSA001", "1", "1008800028", "KP20221011-2",
                "012", "123456789013", "20221015", "6", "Y", "txt", "./sample/prf.txt", ""));
        dtoAftPrfList.add(new DtoAftPrf("011", "FE00032", "20221019", "KSA001", "1", "1008800031", "KP20221011-3",
                "011", "123456789014", "20221016", "1", "Y", "png", "./sample/prf.png", ""));
        result = hpnBbnk.sendDataDto("A001", "0081", "Y06", dtoAftPrfList, "./sample", "T", "");
        */

        //계좌변경접수요청(FB0211) 수신data를 받아 응답값 세팅후 계좌변경접수결과(FB0221) 생성.
        /*
        List<DtoShift> dtoShiftList = (List<DtoShift>) hpnBbnk.recvDataDto("0998", "A001", "IY1", "001", "20221101", "./sample", "T");
        if(dtoShiftList.isEmpty())   System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else for (DtoShift dtoShift  : dtoShiftList) System.out.println("hpnBbnk.recvDataDto : "+dtoShift);
        System.out.println("Reply 4 shift request ...");
        int dataSeq = 0;
        for (DtoShift dtoShift : dtoShiftList) {
            dataSeq++;
            if(dataSeq == 2){
                dtoShift.setAcceptDt(Util.getCurDtTm().substring(0, 8));
                dtoShift.setRtnCd("7107");
            } else {
                dtoShift.setAcceptDt(Util.getCurDtTm().substring(0, 8));
                dtoShift.setRtnCd("0000");
                dtoShift.setAftFacCd("aft0123");
                dtoShift.setAftNapbuNo(dtoShift.getBefNapbuNo());
            }
            System.out.println("hpnBbnk.sendDataDto : "+dtoShift);
        }
        result = hpnBbnk.sendDataDto("A001", "0998", "IY1", dtoShiftList, "./sample", "T", "");
         */
        /*
        List<DtoReg> dtoRegList = new ArrayList<>();
        dtoRegList.add(new DtoReg("HPN001", "089", "", "004", "123465789012", "1", "", "", "110203", "ABBT0001", "", "1", "1", ""));
        dtoRegList.add(new DtoReg("HPN001", "089", "", "011", "883465789012", "1", "", "", "110203", "ABBT0002", "", "1", "1", ""));
        result = hpnBbnk.sendDataDto("A004", "0997", "A0R", dtoRegList, "./sample", "T");
        */
        /*
        List<DtoBill> dtoBillList = new ArrayList<>();
        dtoBillList.add(new DtoBill("08000138", "", "20221117", "", "", "", "", "20221115-1",
                "003", "12345678901234", 1500L, "", "", 0L, "NTP0001", "", "당근10kg", "", "ABCDEFG"));
        dtoBillList.add(new DtoBill("08000138", "", "20221117", "", "", "", "", "20221115-1",
                "004", "123456789004", 1600L, "", "", 0L, "NTP0002", "", "당근102kg사주세요", "", "당근농장B"));
        result = hpnBbnk.sendDataDto("A001", "0997", "A02", dtoBillList, "./sample", "T", "");
        */

        assertTrue(result);
    }

    //@Test
    public void makeDtoList(){
        /*
        String infoCd = "R00";
        String srcFilePath = "./sample/R00ComRecv.txt";
        List<DtoReg> dtoRegList = (List<DtoReg>) hpnBbnk.makeDtoList(infoCd, srcFilePath);
        if(dtoRegList.isEmpty()) System.out.println("hpnBbnk.makeDtoList : NO_DATA");
        else
            for (DtoReg dtoReg : dtoRegList) System.out.println("hpnBbnk.makeDtoList : "+dtoReg);
        */
        String infoCd = "200";
        String srcFilePath = "./sample/200ComRecv.txt";
        List<DtoBill> dtoBillList = (List<DtoBill>) hpnBbnk.makeDtoList(infoCd, srcFilePath);
        if(dtoBillList.isEmpty()) System.out.println("hpnBbnk.makeDtoList : NO_DATA");
        else
            for (DtoBill dtoBill : dtoBillList) System.out.println("hpnBbnk.makeDtoList : "+dtoBill);

        assertNotNull(dtoBillList);
    }

    //@Test
    public void recvDataDto(){
        List<?> objects = null;
        /*
        List<DtoReg> dtoRegList = (List<DtoReg>) hpnBbnk.recvDataDto("0026", "A002", "R00", "001", "20230614", "./sample", "T", "");
        //List<DtoReg> dtoRegList = (List<DtoReg>) hpnBbnk.recvDataDto("0026", "A002", "R00", "001", "20230614", "./sample", "T", "tcUF3A2WRsW1BdQNlrZlsQ");
        //List<DtoReg> dtoRegList = (List<DtoReg>) hpnBbnk.recvDataDto("0026", "A002", "R00", "001", "20230614", "./sample", "T");
        if(dtoRegList.isEmpty())    System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else
            for (DtoReg dtoReg : dtoRegList) System.out.println("hpnBbnk.recvDataDto : "+dtoReg);
        */
        /*
        List<DtoBill> dtoBillList = (List<DtoBill>) hpnBbnk.recvDataDto("0084", "A004", "200", "001", "20220630", "./sample", "T");
        if(dtoBillList.isEmpty())    System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else
            for (DtoBill dtoBill : dtoBillList) System.out.println("hpnBbnk.recvDataDto : "+dtoBill);
         */
        /*
        List<DtoPay> dtoPayList = (List<DtoPay>) hpnBbnk.recvDataDto("0081", "A004", "300", "001", "20220701", "./sample", "T");
        if(dtoPayList.isEmpty())    System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else
            for (DtoPay dtoPay : dtoPayList) System.out.println("hpnBbnk.recvDataDto : "+dtoPay);
        */
        /*
        objects = hpnBbnk.recvDataDto("0081", "A001", "Y00", "001", "20221019", "./sample", "T");
        if(objects.isEmpty())   System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else
            for (DtoPrf dtoPrf : (List<DtoPrf>) objects) System.out.println("hpnBbnk.recvDataDto : "+dtoPrf);
        */
        /*
        objects = hpnBbnk.recvDataDto("0023", "A001", "Y05", "001", "20221019", "./sample", "T");
        if(objects.isEmpty())   System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else
            for (DtoAftPrf dtoAftPrf : (List<DtoAftPrf>) objects) System.out.println("hpnBbnk.recvDataDto : "+dtoAftPrf);
        */
        /*
        objects = hpnBbnk.recvDataDto("0031", "A001", "Y06", "001", "20221021", "./sample", "T");
        if(objects.isEmpty())   System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else
            for (DtoAftPrf dtoAftPrf : (List<DtoAftPrf>) objects) System.out.println("hpnBbnk.recvDataDto : "+dtoAftPrf);
        */
        /*
        objects = hpnBbnk.recvDataDto("0998", "A001", "IY3", "001", "20221024", "./sample", "T");
        if(objects.isEmpty())   System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else
            for (DtoCanc dtoCanc : (List<DtoCanc>) objects) System.out.println("hpnBbnk.recvDataDto : "+dtoCanc);
         */
        /*
        objects = hpnBbnk.recvDataDto("0998", "A001", "IY1", "001", "20221101", "./sample", "T");
        if(objects.isEmpty())   System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else
            for (DtoShift dtoShift  : (List<DtoShift>) objects) System.out.println("hpnBbnk.recvDataDto : "+dtoShift);
         */
        /*
        objects = hpnBbnk.recvDataDto("0084", "A001", "Y02", "001", "20221110", "./sample", "T");
        if(objects.isEmpty())   System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else for (DtoShift dtoShift  : (List<DtoShift>) objects) System.out.println("hpnBbnk.recvDataDto : "+dtoShift);
         */
        /*
        objects = hpnBbnk.recvDataDto("0997", "A004", "A0R", "001", "20221111", "./sample", "T");
        if(objects.isEmpty())   System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else for (DtoReg dtoReg  : (List<DtoReg>) objects) System.out.println("hpnBbnk.recvDataDto : "+dtoReg);
        */
        /*
        objects = hpnBbnk.recvDataDto("0997", "A004", "A02", "001", "20221116", "./sample", "T");
        if(objects.isEmpty())    System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else for (DtoBill dtoBill : (List<DtoBill>) objects) System.out.println("hpnBbnk.recvDataDto : "+dtoBill);
         */
        objects = hpnBbnk.recvDataDto("0997", "7297", "A02", "001", "20230911", "./sample", "T");
        if(objects.isEmpty())    System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else for (DtoBill dtoBill : (List<DtoBill>) objects) System.out.println("hpnBbnk.recvDataDto : "+dtoBill);
        /*
        objects = hpnBbnk.recvDataDto("0997", "A001", "AY0", "001", "20221116", "./sample", "T");
        if(objects.isEmpty())   System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else for (DtoPrf dtoPrf : (List<DtoPrf>) objects) System.out.println("hpnBbnk.recvDataDto : "+dtoPrf);
        */
        /*
        objects = hpnBbnk.recvDataDto("0995", "A004", "BR3", "001", "20230315", "./sample", "T");
        if(objects.isEmpty())   System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else for (DtoPay dtoPay : (List<DtoPay>) objects) System.out.println("hpnBbnk.recvDataDto : "+dtoPay);
        */
        assertNotNull(objects);
    }


}