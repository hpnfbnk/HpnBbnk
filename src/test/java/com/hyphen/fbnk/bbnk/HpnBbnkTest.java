package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.dto.DtoSRList;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class HpnBbnkTest {
    HpnBbnk hpnBbnk;

    @Before
    public void setup(){
        hpnBbnk = new HpnBbnk();
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
            System.out.println("hpnBbnk.recvList : NO_DATA");
        else for (DtoSRList dtoSRList : dtoSRLists)
            System.out.println("hpnBbnk.recvList : "+dtoSRList.toString());
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

}