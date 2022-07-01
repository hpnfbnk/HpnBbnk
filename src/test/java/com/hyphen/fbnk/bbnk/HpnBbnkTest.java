package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.dto.*;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HpnBbnkTest {
    HpnBbnk hpnBbnk;

    @Before
    public void setup(){
        this.hpnBbnk = new HpnBbnk();    //기본
        //this.hpnBbnk = new HpnBbnk(true, false);    //압축
    }

    //@Test
    public void sendData() {
        //송신
        boolean result = hpnBbnk.sendData("A001","0181", "R00", "./sample/sample_R00_SND.txt", "T");
        if(result) System.out.println("hpnBbnk.sendData() : SUCCESS");
        else System.out.println("hpnBbnk.sendData() : FAIL");
        assertTrue(result);
    }

    //@Test
    public void getRecvList() {
        //일반적인 조건으로 수신목록 조회(최근1주일사이에 조회자가 아직 한번도 수신하지 않은 것들에 대한 수신목록 조회요청)
        List<DtoSRList> dtoSRLists = hpnBbnk.getRecvList("A001", "T");
        if(dtoSRLists.isEmpty())
            System.out.println("hpnBbnk.getRecvList : NO_DATA");
        else for (DtoSRList dtoSRList : dtoSRLists)
            System.out.println("hpnBbnk.getRecvList : "+dtoSRList.toString());
        assertNotNull(dtoSRLists);
    }

    //@Test
    public void recvList() {
        //목록조회
        List<DtoSRList> dtoSRLists = hpnBbnk.recvList("A001", "9999", "ZZZ", "20220310", "20220317", "M", "E", "T");
        if(dtoSRLists.isEmpty())
            System.out.println("hpnBbnk.recvList : NO_DATA");
        else
            for (DtoSRList dtoSRList : dtoSRLists) System.out.println("hpnBbnk.recvList : "+dtoSRList.toString());
        assertNotNull(dtoSRLists);
    }

    //@Test
    public void recvData() {
        //수신
        boolean result = hpnBbnk.recvData("0181", "A001", "R00", "001", "20220315", "./sample/sample_R00_RCV.txt", "T");
        if(result) System.out.println("hpnBbnk.recvData : SUCCESS");
        else System.out.println("hpnBbnk.recvData : FAIL");
        assertTrue(result);
    }

    //@Test
    public void sendDataMulti() {
        List<DtoFileList> sendDataLists = new ArrayList<>();
        sendDataLists.add(new DtoFileList("20220323", "R00", "A001", "0181", "001", "./sample/ABRQ20190323_B10_011_123456_KB1.001", false));
        sendDataLists.add(new DtoFileList("20220323", "200", "A002", "0182", "001", "./sample/ABRQ20190323_C10_004_BK123456_KB3.001", false));
        sendDataLists.add(new DtoFileList("20220323", "Y00", "A001", "0181", "001", "./sample/prf.dat", false));

        List<DtoFileList> resultLists = hpnBbnk.sendDataMulti("A001", sendDataLists, "", "T");
        for (DtoFileList resultList : resultLists)
            System.out.println("hpnBbnk.sendDataMulti : "+resultList);
        assertNotNull(resultLists);
    }

    //@Test
    public void sendDataMultiKEDU() {
        List<DtoFileList> sendDataLists = new ArrayList<>();
        sendDataLists.add(new DtoFileList("20190323", "KB1", "B10", "011", "001", "./sample/ABRQ20190323_B10_011_123456_KB1.001", false));
        sendDataLists.add(new DtoFileList("20190323", "KB3", "C10", "004", "001", "./sample/ABRQ20190323_C10_004_BK123456_KB3.001", false));
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
        List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti("A001", "9999", "ZZZ", "20220315", "20220315", "A", "", "./sample", "T");
        if(dtoFileLists.isEmpty())
            System.out.println("hpnBbnk.recvDataMulti : NO_DATA");
        else
            for(DtoFileList dtoFileList : dtoFileLists) System.out.println("hpnBbnk.recvDataMulti : "+dtoFileList);
        assertNotNull(dtoFileLists);
    }

    //@Test
    public void recvDataMultiKEDU(){
        List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti("KEDU", "9999", "ZZZ", "20220410", "20220414", "A", "KEDU", "./sample", "T");
        if(dtoFileLists.isEmpty())
            System.out.println("hpnBbnk.recvDataMulti : NO_DATA");
        else
            for(DtoFileList dtoFileList : dtoFileLists) System.out.println("hpnBbnk.recvDataMulti : "+dtoFileList);
        assertNotNull(dtoFileLists);
    }

    //@Test
    public void recvDataDB(){
        String filePath = "./sample/CocaC01.001";
        String infoCd = "C01";
        String dbDriver = "org.gjt.mm.mysql.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/test";
        String dbUser = "myid";
        String dbPass = "mypwd";
        boolean result = hpnBbnk.set2DB(filePath, infoCd, dbDriver, dbUrl, dbUser, dbPass);
        assertTrue(result);
    }

    //@Test
    public void recvData2DB(){
        String sendCd   = "1096";
        String recvCd   = "A001";
        String infoCd   = "C01";
        String seqNo    = "001";
        String sendDt   = "20220510";
        String filePath = "./sample/"+infoCd+sendCd+recvCd+".txt";
        String runMode  = "T";
        String dbDriver = "org.gjt.mm.mysql.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Seoul&useSSL=false";
        String dbUser = "myid";
        String dbPass = "mypwd";
        boolean result = hpnBbnk.recvData2DB(sendCd, recvCd, infoCd, seqNo, sendDt, filePath, runMode, dbDriver, dbUrl, dbUser, dbPass);
        assertTrue(result);
    }

    //@Test
    public void recvDataMulti2DB(){
        String sendCd   = "9999";
        String recvCd   = "A001";
        String infoCd   = "ZZZ";
        String sendDt   = "20220510";
        String runMode  = "T";
        String dbDriver = "org.gjt.mm.mysql.Driver";
        String dbUrl = "jdbc:mysql://localhost:3306/test";
        String dbUser = "myid";
        String dbPass = "mypwd";
        List<DtoFileList> dtoFileLists = hpnBbnk.recvDataMulti2DB(recvCd, sendCd, infoCd, sendDt, sendDt, "E", "", "./sample", runMode, dbDriver, dbUrl, dbUser, dbPass);
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
        List<DtoPay> dtoPayList = new ArrayList<>();
        dtoPayList.add(new DtoPay("HPN00387", "081", "20220628", "123456789012", "", "", "", "1", "20220629-1",
                "003", "12345678901234", 1500L, "", "", 0L, "", "11월-급여", "", "당근농장A"));
        dtoPayList.add(new DtoPay("HPN00387", "081", "20220628", "123456789012", "", "", "", "1", "20220629-1",
                "004", "123456789004", 1500L, "", "", 0L, "", "12월-당기성과급여", "", "당근농장B"));
        result = hpnBbnk.makeDataFile("300", dtoPayList, "./sample/make300.txt");

        assertTrue(result);
    }

    //@Test
    public void sendDataDto(){
        boolean result;
        /*
        List<DtoReg> dtoRegList = new ArrayList<>();
        dtoRegList.add(new DtoReg("HPN001", "089", "", "004", "123465789012", "1", "", "", "110203", "ABBT0001", "업무PPK1", "1", "1", ""));
        dtoRegList.add(new DtoReg("HPN001", "089", "", "011", "883465789012", "1", "", "", "110203", "ABBT0002", "업무PPK2", "1", "1", ""));
        result = hpnBbnk.sendDataDto("A004", "0084", "R00", dtoRegList, "./sample", "T");
        */
        /*
        List<DtoBill> dtoBillList = new ArrayList<>();
        dtoBillList.add(new DtoBill("HPN00387", "081", "20220628", "123456789012", "", "", "", "20220629-1",
                "003", "12345678901234", 1500L, "", "", 0L, "NTP0001", "", "당근10kg", "", "당근농장A"));
        dtoBillList.add(new DtoBill("HPN00387", "081", "20220628", "123456789012", "", "", "", "20220629-1",
                "004", "123456789004", 1500L, "", "", 0L, "NTP0002", "", "당근102kg사주세요", "", "당근농장B"));
        result = hpnBbnk.sendDataDto("A004", "0084", "200", dtoBillList, "./sample", "T");
        */
        List<DtoPay> dtoPayList = new ArrayList<>();
        dtoPayList.add(new DtoPay("HPN00387", "081", "20220628", "123456789012", "", "", "", "1", "20220629-1",
                "003", "12345678901234", 1500L, "", "", 0L, "", "11월-급여", "", "당근농장A"));
        dtoPayList.add(new DtoPay("HPN00387", "081", "20220628", "123456789012", "", "", "", "1", "20220629-1",
                "004", "123456789004", 1500L, "", "", 0L, "", "12월-당기성과급여", "", "당근농장B"));
        result = hpnBbnk.sendDataDto("A004", "0081", "300", dtoPayList, "./sample", "T");

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
        /*
        List<DtoReg> dtoRegList = (List<DtoReg>) hpnBbnk.recvDataDto("0084", "A004", "R00", "001", "20220628", "./sample", "T");
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
        List<DtoPay> dtoPayList = (List<DtoPay>) hpnBbnk.recvDataDto("0081", "A004", "300", "001", "20220701", "./sample", "T");
        if(dtoPayList.isEmpty())    System.out.println("hpnBbnk.recvDataDto : NO_DATA");
        else
            for (DtoPay dtoPay : dtoPayList) System.out.println("hpnBbnk.recvDataDto : "+dtoPay);

        assertNotNull(dtoPayList);
    }


}