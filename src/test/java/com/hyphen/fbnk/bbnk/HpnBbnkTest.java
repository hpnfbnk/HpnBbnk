package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.dto.DtoFileList;
import com.hyphen.fbnk.bbnk.dto.DtoSRList;
import org.junit.Before;
import org.junit.Test;

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


}