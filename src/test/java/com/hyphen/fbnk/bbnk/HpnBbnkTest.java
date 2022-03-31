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

    @Test
    public void sendData() {
        //송신
        boolean result = hpnBbnk.sendData("A001","0181", "R00", "./sample/sample_R00_SND.txt", "T");
        if(result) System.out.println("hpnBbnk.sendData() : SUCCESS");
        else System.out.println("hpnBbnk.sendData() : FAIL");
        assertTrue(result);
    }

    @Test
    public void getRecvList() {
        //일반적인 조건으로 수신목록 조회(최근1주일사이에 조회자가 아직 한번도 수신하지 않은 것들에 대한 수신목록 조회요청)
        List<DtoSRList> dtoSRLists = hpnBbnk.getRecvList("A001", "T");
        if(dtoSRLists.isEmpty())
            System.out.println("hpnBbnk.getRecvList : NO_DATA");
        else for (DtoSRList dtoSRList : dtoSRLists)
            System.out.println("hpnBbnk.getRecvList : "+dtoSRList.toString());
        assertNotNull(dtoSRLists);
    }

    @Test
    public void recvList() {
        //목록조회
        List<DtoSRList> dtoSRLists = hpnBbnk.recvList("A001", "9999", "ZZZ", "20220310", "20220317", "M", "E", "T");
        if(dtoSRLists.isEmpty())
            System.out.println("hpnBbnk.recvList : NO_DATA");
        else for (DtoSRList dtoSRList : dtoSRLists)
            System.out.println("hpnBbnk.recvList : "+dtoSRList.toString());
        assertNotNull(dtoSRLists);
    }

    @Test
    public void recvData() {
        //수신
        boolean result = hpnBbnk.recvData("0181", "A001", "R00", "001", "20220315", "./sample/sample_R00_RCV.txt", "T");
        if(result) System.out.println("hpnBbnk.recvData : SUCCESS");
        else System.out.println("hpnBbnk.recvData : FAIL");
        assertTrue(result);
    }


    @Test
    public void sendDataMulti() {
        List<DtoFileList> sendDataLists = new ArrayList<>();
        sendDataLists.add(new DtoFileList("20220323", "R00", "A001", "0181", "001", "./sample/ABRQ20220323_B10_011_123456_KB1.001", false));
        sendDataLists.add(new DtoFileList("20220323", "200", "A002", "0182", "001", "./sample/ABRQ20220323_C10_004_BK123465_KB2.001", false));
        sendDataLists.add(new DtoFileList("20220323", "Y00", "A001", "0181", "001", "./sample/prf.dat", false));

        List<DtoFileList> resultLists = hpnBbnk.sendDataMulti("A001", sendDataLists, "", "T");
        for (DtoFileList resultList : resultLists)
            System.out.println("hpnBbnk.sendDataMulti : "+resultList);
        assertNotNull(resultLists);
    }

    @Test
    public void sendDataMultiKEDU() {
        List<DtoFileList> sendDataLists = new ArrayList<>();
        sendDataLists.add(new DtoFileList("20220323", "KB1", "B10", "011", "001", "./sample/ABRQ20220323_B10_011_123456_KB1.001", false));
        //sendDataLists.add(new DtoFileList("20220323", "KB3", "C10", "004", "001", "./sample/ABRQ20220323_C10_004_BK123465_KB2.001", false));

        List<DtoFileList> resultLists = hpnBbnk.sendDataMulti("KEDU", sendDataLists, "KEDU", "T");
        for (DtoFileList resultList : resultLists)
            System.out.println("hpnBbnk.sendDataMultiKEDU : "+resultList);
        assertNotNull(resultLists);
    }


}