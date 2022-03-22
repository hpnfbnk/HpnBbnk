package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.define.Define;
import com.hyphen.fbnk.bbnk.define.EncTp;
import com.hyphen.fbnk.bbnk.define.InfoCdPrf;
import com.hyphen.fbnk.bbnk.define.MsgCode;
import com.hyphen.fbnk.bbnk.dto.DtoSRList;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HpnBbnk {
    //default setting
    private String hpnSvrIpProd = "210.181.28.143"; //Hyphen 운영서버 IP (Internet)
    private String hpnSvrIpTest = "210.181.28.96";  //Hyphen Test서버 IP (Internet)
    private int hpnSvrPort      = 29994;            //Hyphen 서버 Port (Internet)
    private String hpnSvrIpPrf  = "121.138.30.10";  //Hyphen 증빙서버 IP (Internet)
    private int hpnSvrPortPrf   = 29995;            //Hyphen 증빙서버 Port (Internet)

    private String hpnSvrIpProdDeln = "129.200.9.11"; //Hyphen 운영서버 IP (전용선)
    private String hpnSvrIpTestDeln = "129.200.9.18"; //Hyphen Test서버 IP (전용선)
    private int hpnSvrPortDeln      = 50001;          //Hyphen 서버 Port (전용선)
    private String hpnSvrIpPrfDeln  = "129.200.9.11"; //Hyphen 증빙서버 IP (전용선)
    private int hpnSvrPortPrfDeln   = 50005;          //Hyphen 증빙서버 Port (전용선)

    private boolean zipYn   = false;    //압축여부 default=비압축
    private String maxBps   = "2M";     //전송제한속도 default=1Mbps
    private boolean delnYn  = false;    //전용선사용여부 default=비사용
    
    private boolean customInfoYn = false;   //사용자정의 접속정보 사용여부

    private static final Log log = LogFactory.getLog(HpnBbnk.class);

    public static void main(String[] args) {
        /*
        HpnBbnk hpnBbnk = new HpnBbnk();
        //HpnBbnk hpnBbnk = new HpnBbnk(true);
        //HpnBbnk hpnBbnk = new HpnBbnk(true, false);
        //HpnBbnk hpnBbnk = new HpnBbnk(true, "128k", false);
        //HpnBbnk hpnBbnk = new HpnBbnk("208.181.28.149", "209.181.28.99", 29994, "109.138.30.10", 29995, true, "512k", false);
        for(int i=1 ; i<=1 ;i++){
           new Thread(""+i){
                @Override
                public void run() {
                    boolean result = false;
                    //송신
                    result = hpnBbnk.sendData("A00"+getName(),"018"+getName(), "R00", "./snd.txt", "T");
                    if(result) System.out.println("[thid:"+getName()+"] hpnBbnk.sendData : SUCCESS");
                    else System.out.println("[thid:"+getName()+"] hpnBbnk.sendData : FAIL");
                    //목록조회
                    List<DtoSRList> dtoSRLists = hpnBbnk.recvList("A001", "9999", "ZZZ", "20220308", "20220310", "M", "E", "T");
                    if(dtoSRLists.isEmpty())    System.out.println("[thid:"+getName()+"] hpnBbnk.recvList : NO_DATA");
                    else for (DtoSRList dtoSRList : dtoSRLists)  System.out.println("[thid:"+getName()+"] hpnBbnk.recvList : "+dtoSRList.toString());
                    //수신
                    result = hpnBbnk.recvData("0081", "A001", "R00", "001", "20220310", "./rcv.txt", "T");
                    if(result) System.out.println("[thid:"+getName()+"] hpnBbnk.recvData : SUCCESS");
                    else System.out.println("[thid:"+getName()+"] hpnBbnk.recvData : FAIL");
                }
            }.start();
            try {TimeUnit.SECONDS.sleep(3);} catch (Exception e) {}
        }
        */
    }

    /**
     * 기본접속정보(Internet)를 사용하여 Instance 생성합니다.
     */
    public HpnBbnk(){
    }

    /**
     * 전용선사용여부를 지정하여 Instance 생성합니다.
     * @param delnYn 전용선사용여부 default=비사용
     */
    public HpnBbnk(boolean delnYn){
        this.delnYn = delnYn;
    }

    /**
     * 압축여부, 전용선사용여부를 지정하여 Instance 생성합니다.
     * @param zipYn 압축여부 default=비압축
     * @param delnYn 전용선사용여부 default=비사용
     */
    public HpnBbnk(boolean zipYn, boolean delnYn){
        this.zipYn      = zipYn;
        this.delnYn     = delnYn;
    }

    /**
     * 압축여부, 전송제한속도, 전용선사용여부를 지정하여 Instance 생성합니다.
     * @param zipYn 압축여부 default=비압축
     * @param maxBps 전송제한속도 default=2Mbps
     * @param delnYn 전용선사용여부 default=비사용
     */
    public HpnBbnk(boolean zipYn, String maxBps, boolean delnYn){
        this.zipYn      = zipYn;
        this.maxBps     = maxBps;
        this.delnYn     = delnYn;
    }

    /**
     * 접속정보를 임의로 지정하여 Instance 생성합니다.
     * @param hpnSvrIpProd Hyphen 운영서버 IP
     * @param hpnSvrIpTest Hyphen Test서버 IP
     * @param hpnSvrPort Hyphen 서버 Port
     * @param hpnSvrIpPrf Hyphen 증빙서버 IP
     * @param hpnSvrPortPrf Hyphen 증빙서버 Port
     * @param zipYn 압축여부 default=비압축
     * @param maxBps 전송제한속도 default=2Mbps
     * @param delnYn 전용선사용여부 default=비사용
     */
    public HpnBbnk(String hpnSvrIpProd, String hpnSvrIpTest, int hpnSvrPort, String hpnSvrIpPrf, int hpnSvrPortPrf, boolean zipYn, String maxBps, boolean delnYn) {
        this.customInfoYn = true;

        this.hpnSvrIpProd = hpnSvrIpProd;
        this.hpnSvrIpTest = hpnSvrIpTest;
        this.hpnSvrPort = hpnSvrPort;
        this.hpnSvrIpPrf = hpnSvrIpPrf;
        this.hpnSvrPortPrf = hpnSvrPortPrf;
        this.zipYn = zipYn;
        this.maxBps = maxBps;
        this.delnYn = delnYn;
    }

    /**
     * Hyphen으로 요청파일 송신
     * @param sendCd 송신자코드 Hyphen에서 발급한 업체코드
     * @param recvCd 수신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011 등..
     * @param infoCd 파일종류구분코드 계좌등록:R00, 자동이체:200, 지급이체(송금):300, 증빙자료:Y00 등..
     * @param filePath 송신대상파일 위치
     * @param runMode 동작모드 Y:운영 T:test
     * @return true:성공 false:실패
     */
    public boolean sendData(String sendCd, String recvCd, String infoCd, String filePath, String runMode){
        boolean result = false;
        log.debug("[sendData](START) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", filePath="+filePath+", runMode="+runMode);

        String ipAddr   = getUseIpAddr(infoCd, runMode, true);
        int port        = getUsePort(infoCd, runMode, true);
        EncTp encTp     = getUseEncTp(infoCd, true);

        UpdnLib updnLib = new UpdnLib();
        result = updnLib.sndData(ipAddr, port, sendCd, recvCd, infoCd, filePath, this.zipYn, encTp, this.maxBps);

        if(result)  log.debug("[sendData](SUCCESS) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", filePath="+filePath+", runMode="+runMode);
        else        log.debug("[sendData](FAIL) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", filePath="+filePath+", runMode="+runMode);

        return result;
    }

    /**
     * 일반적인 조건으로 수신목록 조회 :
     * 최근1주일사이에 조회자가 아직 한번도 수신하지 않은 것들에 대한 수신목록 조회요청
     * @param finderCd 조회자코드
     * @param runMode 동작모드 Y:운영 T:test
     * @return 수신목록
     */
    public List<DtoSRList> getRecvList(String finderCd, String runMode){
        log.debug("[getRecvList] finderCd="+finderCd+", runMode="+runMode);
        String fromDt   = Util.spanCurDt(-7).substring(2, 8);
        String toDt     = Util.getCurDtTm().substring(2, 8);

        return recvList(finderCd, Define.TARGETCD_ALL.getCode(), Define.INFOCD_ALL.getCode(), fromDt, toDt, MsgCode.MSG_TP_RCV_LST.getCode(), MsgCode.MSG_TP_REQ_YET.getCode(), runMode);
    }

    /**
     * 송수신 목록조회
     * @param finderCd 조회자코드
     * @param targetCd 조회대상자코드 모든대상자:9999)
     * @param infoCd 조회대상파일종류 모든종류:ZZZ 계좌등록:R00, 자동이체:200, 지급이체(송금):300, 증빙자료:Y00 등..
     * @param fromDt 조회범위-시작일자 YYYYMMDD
     * @param toDt 조회범위-종료일자 YYYYMMDD
     * @param listTp 목록종류 수신목록:M 송신목록:L
     * @param findRng 조회범위-수신여부 미수신건만:E 모두:A
     * @param runMode 동작모드 Y:운영 T:test
     * @return 송수신목록
     */
    public List<DtoSRList> recvList(String finderCd, String targetCd, String infoCd, String fromDt, String toDt, String listTp, String findRng, String runMode){
        log.debug("[recvList](zzzSTART) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", tpList="+listTp+", runMode="+runMode);

        String ipAddr   = getUseIpAddr(infoCd, runMode, false);
        int port        = getUsePort(infoCd, runMode, false);
        EncTp encTp     = getUseEncTp(infoCd, false);

        fromDt  = fromDt.length()==8 ? fromDt.substring(2) : fromDt ;
        toDt    = toDt.length()==8 ? toDt.substring(2) : toDt ;
        MsgCode tpList  = listTp.equals(MsgCode.MSG_TP_SND_LST.getCode()) ? MsgCode.MSG_TP_SND_LST : MsgCode.MSG_TP_RCV_LST;
        MsgCode fRng    = findRng.equals(MsgCode.MSG_TP_REQ_ALL.getCode()) ? MsgCode.MSG_TP_REQ_ALL : MsgCode.MSG_TP_REQ_YET;

        UpdnLib updnLib = new UpdnLib();
        List<DtoSRList> dtoSRLists = updnLib.rcvList(ipAddr, port, finderCd, targetCd, infoCd, fromDt, toDt, tpList, fRng, encTp);

        if(dtoSRLists.isEmpty())
            log.debug("[recvList](NO_DATA) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", tpList="+listTp+", runMode="+runMode);
        else{
            log.debug("[recvList](SUCCESS) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", tpList="+listTp+", runMode="+runMode);
            for(DtoSRList dtoSRList : dtoSRLists)   log.debug("[recvList] "+dtoSRList.toString());
        }

        return dtoSRLists;
    }

    /**
     * Hyphen에서 결과파일 수신
     * @param sendCd 송신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011 등..
     * @param recvCd 수신자코드 Hyphen에서 발급한 업체코드
     * @param infoCd 파일종류구분코드 계좌등록:R00, 자동이체:200, 지급이체(송금):300, 증빙자료:Y00 등..
     * @param seqNo 파일순번
     * @param sendDt 송신일자
     * @param filePath 수신대상파일 저장위치
     * @param runMode 동작모드 Y:운영 T:test
     * @return true:성공 false:실패
     */
    public boolean recvData(String sendCd, String recvCd, String infoCd, String seqNo, String sendDt, String filePath, String runMode){
        boolean result = false;
        log.debug("[recvData](START) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", sendDt="+sendDt+", filePath="+filePath+", runMode="+runMode);

        String ipAddr   = getUseIpAddr(infoCd, runMode, false);
        int port        = getUsePort(infoCd, runMode, false);
        EncTp encTp     = getUseEncTp(infoCd, false);

        sendDt  = sendDt.length()==8 ? sendDt.substring(2) : sendDt ;
        sendDt  = sendDt.length()==14 ? sendDt.substring(2, 8) : sendDt ;

        UpdnLib updnLib = new UpdnLib();
        result = updnLib.rcvData(ipAddr, port, sendCd, recvCd, infoCd, seqNo, sendDt, filePath, this.zipYn, encTp);

        if(result)  log.debug("[recvData](SUCCESS) sendDt="+sendDt+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", filePath="+filePath+", runMode="+runMode);
        else        log.debug("[recvData](FAIL) sendDt="+sendDt+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", filePath="+filePath+", runMode="+runMode);

        return result;
    }

    private String getUseIpAddr(String infoCd, String runMode, boolean sndYn){
        String useIpAddr = null;
        if(!customInfoYn){
            //일반&Internet
            if(!InfoCdPrf.isValue(infoCd)&&!delnYn)
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpProd : this.hpnSvrIpTest;
            //증빙&Internet&송신
            else if(InfoCdPrf.isValue(infoCd)&&!delnYn&&sndYn)
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpPrf : this.hpnSvrIpTest;
            //증빙&Internet&수신
            else if(InfoCdPrf.isValue(infoCd)&&!delnYn&&!sndYn)
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpProd : this.hpnSvrIpTest;
            //일반&전용선
            else if(!InfoCdPrf.isValue(infoCd)&&delnYn)
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpProdDeln : this.hpnSvrIpTestDeln;
            //증빙&전용선&송신
            else if(InfoCdPrf.isValue(infoCd)&&delnYn&&sndYn)
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpPrfDeln : this.hpnSvrIpTestDeln;
            //증빙&전용선&수신
            else if(InfoCdPrf.isValue(infoCd)&&delnYn&&!sndYn)
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpProdDeln : this.hpnSvrIpTestDeln;
        }
        else{
            //일반
            if(!InfoCdPrf.isValue(infoCd))
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpProd : this.hpnSvrIpTest;
            //증빙&송신
            else if(InfoCdPrf.isValue(infoCd)&&sndYn)
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpPrf : this.hpnSvrIpTest;
            //증빙&수신
            else if(InfoCdPrf.isValue(infoCd)&&!sndYn)
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpProd : this.hpnSvrIpTest;
        }

        return useIpAddr;
    }

    private int getUsePort(String infoCd, String runMode, boolean sndYn){
        int usePort = 0;
        if(!customInfoYn){
            //일반&Internet
            if(!InfoCdPrf.isValue(infoCd)&&!delnYn)
                usePort = this.hpnSvrPort;
            //증빙&Internet&송신
            else if(InfoCdPrf.isValue(infoCd)&&!delnYn&&sndYn)
                usePort = this.hpnSvrPortPrf;
            //증빙&Internet&수신
            else if(InfoCdPrf.isValue(infoCd)&&!delnYn&&!sndYn)
                usePort = this.hpnSvrPort;
            //일반&전용선
            else if(!InfoCdPrf.isValue(infoCd)&&delnYn)
                usePort = this.hpnSvrPortDeln;
            //증빙&전용선&송신
            else if(InfoCdPrf.isValue(infoCd)&&delnYn&&sndYn)
                usePort = this.hpnSvrPortPrfDeln;
            //증빙&전용선&수신
            else if(InfoCdPrf.isValue(infoCd)&&delnYn&&!sndYn)
                usePort = this.hpnSvrPortDeln;
        }
        else{
            //일반
            if(!InfoCdPrf.isValue(infoCd))
                usePort = this.hpnSvrPort;
            //증빙&송신
            else if(InfoCdPrf.isValue(infoCd)&&sndYn)
                usePort = this.hpnSvrPortPrf;
            //증빙&수신
            else if(InfoCdPrf.isValue(infoCd)&&!sndYn)
                usePort = this.hpnSvrPort;
        }

        return usePort;
    }

    private EncTp getUseEncTp(String infoCd, boolean sndYn){
        EncTp useEncTp = EncTp.NONE;

        //일반&Internet
        if(!InfoCdPrf.isValue(infoCd)&&!delnYn)             useEncTp = EncTp.SEED;
        //증빙&Internet&송신
        else if(InfoCdPrf.isValue(infoCd)&&!delnYn&&sndYn)  useEncTp = EncTp.KECB;
        //증빙&Internet&수신
        else if(InfoCdPrf.isValue(infoCd)&&!delnYn&&!sndYn) useEncTp = EncTp.SEED;

        return useEncTp;
    }


    /**
     * 압축여부를 지정합니다.
     * @param zipYn 압축여부 default=비압축
     */
    //public void setZipYn(boolean zipYn) {this.zipYn = zipYn;}

    /**
     * 전송제한속도를 지정합니다.
     * @param maxBps 전송제한속도 default=1Mbps
     */
    //public void setMaxBps(String maxBps) {this.maxBps = maxBps;}

    /**
     * 전용선사용여부를 지정합니다.
     * @param delnYn 전용선사용여부 default=비사용
     */
    //public void setDelnYn(boolean delnYn) {this.delnYn = delnYn;}

}
