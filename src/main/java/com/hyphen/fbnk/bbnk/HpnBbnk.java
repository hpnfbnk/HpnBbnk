package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.define.*;
import com.hyphen.fbnk.bbnk.dto.*;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;
import com.hyphen.fbnk.bbnk.msg.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        for(int i=1 ; i<=1 ;i++){
           new Thread(""+i){
               //HpnBbnk hpnBbnk = new HpnBbnk();
               HpnBbnk hpnBbnk = new HpnBbnk(true);
               //HpnBbnk hpnBbnk = new HpnBbnk(true, false);
               //HpnBbnk hpnBbnk = new HpnBbnk(true, "128k", false);
               //HpnBbnk hpnBbnk = new HpnBbnk("208.181.28.149", "209.181.28.99", 29994, "109.138.30.10", 29995, true, "512k", false);

                @Override
                public void run() {
                    boolean result = false;
                    //송신
                    //result = hpnBbnk.sendData("A00"+getName(),"018"+getName(), "R00", "./snd.txt", "T");
                    //if(result) System.out.println("[thid:"+getName()+"] hpnBbnk.sendData : SUCCESS");
                    //else System.out.println("[thid:"+getName()+"] hpnBbnk.sendData : FAIL");
                    //목록조회
                    List<DtoSRList> dtoSRLists = hpnBbnk.recvList("A001", "9999", "ZZZ", "20220308", "20220310", "M", "E", "T");
                    if(dtoSRLists.isEmpty())    System.out.println("[thid:"+getName()+"] hpnBbnk.recvList : NO_DATA");
                    else for (DtoSRList dtoSRList : dtoSRLists)  System.out.println("[thid:"+getName()+"] hpnBbnk.recvList : "+dtoSRList.toString());
                    //수신
                    //result = hpnBbnk.recvData("0081", "A001", "R00", "001", "20220310", "./rcv.txt", "T");
                    //if(result) System.out.println("[thid:"+getName()+"] hpnBbnk.recvData : SUCCESS");
                    //else System.out.println("[thid:"+getName()+"] hpnBbnk.recvData : FAIL");
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
     * @param recvCd 수신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011, HYPHEN통합서버:0998, HYPHEN배치대행서버:0997 등..
     * @param infoCd 파일종류구분코드 계좌등록:R00(I0R), 자동이체:200(I02), 지급이체(송금):300(I03), 증빙자료:Y00(IY0, AY0), 증빙자료-사후점검:Y06(IY6), 계좌변경접수결과:Y01(IY1),
     *               배치대행-증빙등록(AY0), 배치대행-자동이체(A02), 배치대행-계좌등록(A0R), 배치성실시간-송금이체(BR3) 등..
     * @param filePath 송신대상파일 위치
     * @param runMode 동작모드 Y:운영 T:test
     * @param passwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
     * @return true:성공 false:실패
     */
    public boolean sendData(String sendCd, String recvCd, String infoCd, String filePath, String runMode, String passwd){
        boolean result = false;
        log.debug("[sendData](START) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", filePath="+filePath+", runMode="+runMode);

        String ipAddr   = getUseIpAddr(infoCd, runMode, true);
        int port        = getUsePort(infoCd, runMode, true);
        EncTp encTp     = getUseEncTp(infoCd, true);

        UpdnLib updnLib = new UpdnLib();
        result = updnLib.sndData(ipAddr, port, sendCd, recvCd, infoCd, filePath, this.zipYn, encTp, this.maxBps, passwd);

        if(result)  log.debug("[sendData](SUCCESS) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", filePath="+filePath+", runMode="+runMode);
        else        log.debug("[sendData](FAIL) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", filePath="+filePath+", runMode="+runMode);

        return result;
    }

    /**
     * Hyphen으로 요청파일 송신
     * @param sendCd 송신자코드 Hyphen에서 발급한 업체코드
     * @param recvCd 수신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011, HYPHEN통합서버:0998, HYPHEN배치대행서버:0997 등..
     * @param infoCd 파일종류구분코드 계좌등록:R00(I0R), 자동이체:200(I02), 지급이체(송금):300(I03), 증빙자료:Y00(IY0, AY0), 증빙자료-사후점검:Y06(IY6), 계좌변경접수결과:Y01(IY1),
     *               배치대행-증빙등록(AY0), 배치대행-자동이체(A02), 배치대행-계좌등록(A0R), 배치성실시간-송금이체(BR3) 등..
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
     * Hyphen으로 여러개의 파일 송신
     * @param sendCd 송신자코드 Hyphen에서 발급한 업체코드
     * @param sendLists 송신파일 목록
     * @param sFNmTp 파일명타입 KEDU:k-edufine타입 등..
     * @param runMode 동작모드 Y:운영 T:test
     * @return 송신파일 처리결과 목록
     */
    public List<DtoFileList> sendDataMulti(String sendCd, List<DtoFileList> sendLists, String sFNmTp, String runMode){
        List<DtoFileList> prfSendList = new ArrayList<>();
        List<DtoFileList> nrmSendList = new ArrayList<>();
        List<DtoFileList> prfResultList, nrmResultList;
        List<DtoFileList> resultLists = new ArrayList<>();

        for (DtoFileList sendList : sendLists) {
            log.debug("[sendDataMulti](START) "+sendList+", fNmTp=" + sFNmTp + ", runMode=" + runMode);
            if(InfoCdPrf.isValue(sendList.getInfoCd())) prfSendList.add(sendList);
            else nrmSendList.add(sendList);
        }

        UpdnLib updnLib = new UpdnLib();
        String ipAddr, repInfoCd;
        int port;
        EncTp encTp;
        //파일명타입
        FNmTp fNmTp;
        if(sFNmTp.equals(FNmTp.KEDUFIN.getCode()))  fNmTp = FNmTp.KEDUFIN;
        else fNmTp = FNmTp.DEFAULT;
        //증빙
        if(!prfSendList.isEmpty()){
            repInfoCd = InfoCdPrf.NML_PRF.getCode();
            ipAddr  = getUseIpAddr(repInfoCd, runMode, true);
            port    = getUsePort(repInfoCd, runMode, true);
            encTp   = getUseEncTp(repInfoCd, true);
            prfResultList = updnLib.sndDataMulti(ipAddr, port, sendCd, prfSendList, this.zipYn, encTp, this.maxBps, fNmTp);
            resultLists.addAll(prfResultList);
        }
        //일반
        if(!nrmSendList.isEmpty()){
            repInfoCd = "R00";
            ipAddr  = getUseIpAddr(repInfoCd, runMode, true);
            port    = getUsePort(repInfoCd, runMode, true);
            encTp   = getUseEncTp(repInfoCd, true);
            nrmResultList = updnLib.sndDataMulti(ipAddr, port, sendCd, nrmSendList, this.zipYn, encTp, this.maxBps, fNmTp);
            resultLists.addAll(nrmResultList);
        }
        for (DtoFileList resultList : resultLists)  log.debug("[sendDataMulti](RESULT) "+resultList);

        return resultLists;
    }

    /**
     * 일반적인 조건으로 수신목록 조회 :
     * 최근1주일사이에 조회자가 아직 한번도 수신하지 않은 것들에 대한 수신목록 조회요청
     * @param finderCd 조회자코드
     * @param runMode 동작모드 Y:운영 T:test
     * @param passwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
     * @return 수신목록
     */
    public List<DtoSRList> getRecvList(String finderCd, String runMode, String passwd){
        log.debug("[getRecvList] finderCd="+finderCd+", runMode="+runMode);
        String fromDt   = Util.spanCurDt(-7).substring(2, 8);
        String toDt     = Util.getCurDtTm().substring(2, 8);

        return recvList(finderCd, Define.TARGETCD_ALL.getCode(), Define.INFOCD_ALL.getCode(), fromDt, toDt, MsgCode.MSG_TP_RCV_LST.getCode(), MsgCode.MSG_TP_REQ_YET.getCode(), runMode, passwd);
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
     * @param infoCd 조회대상파일종류 모든종류:ZZZ 계좌등록결과:R00(I0R), 자동이체결과:200(I02), 지급이체(송금)결과:300(I03), 증빙자료등록결과:Y00(IY0, AY0), 증빙자료-사후점검:Y05(IY5), 증빙자료-사후점검-검증결과:Y06(IY6),
     *               해지통보:Y03(IY3), 계좌변경접수요청:Y01(IY1), 계좌변경처리결과:Y02(IY2), 배치대행-증빙등록결과(AY0), 배치대행-계좌등록결과(A0R), 배치대행-자동이체결과(A02), 배치성실시간-송금이체(BR3) 등..
     * @param fromDt 조회범위-시작일자 YYYYMMDD
     * @param toDt 조회범위-종료일자 YYYYMMDD
     * @param listTp 목록종류 수신목록:M 송신목록:L
     * @param findRng 조회범위-수신여부 미수신건만:E 모두:A
     * @param runMode 동작모드 Y:운영 T:test
     * @param passwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
     * @return 송수신목록
     */
    public List<DtoSRList> recvList(String finderCd, String targetCd, String infoCd, String fromDt, String toDt, String listTp, String findRng, String runMode, String passwd){
        log.debug("[recvList](START) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", tpList="+listTp+", runMode="+runMode);

        String ipAddr   = getUseIpAddr(infoCd, runMode, false);
        int port        = getUsePort(infoCd, runMode, false);
        EncTp encTp     = getUseEncTp(infoCd, false);

        fromDt  = fromDt.length()==8 ? fromDt.substring(2) : fromDt ;
        toDt    = toDt.length()==8 ? toDt.substring(2) : toDt ;
        MsgCode tpList  = listTp.equals(MsgCode.MSG_TP_SND_LST.getCode()) ? MsgCode.MSG_TP_SND_LST : MsgCode.MSG_TP_RCV_LST;
        MsgCode fRng    = findRng.equals(MsgCode.MSG_TP_REQ_ALL.getCode()) ? MsgCode.MSG_TP_REQ_ALL : MsgCode.MSG_TP_REQ_YET;

        UpdnLib updnLib = new UpdnLib();
        List<DtoSRList> dtoSRLists = updnLib.rcvList(ipAddr, port, finderCd, targetCd, infoCd, fromDt, toDt, tpList, fRng, encTp, passwd);

        if(dtoSRLists.isEmpty())
            log.debug("[recvList](NO_DATA) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", tpList="+listTp+", runMode="+runMode);
        else{
            log.debug("[recvList](SUCCESS) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", tpList="+listTp+", runMode="+runMode);
            for(DtoSRList dtoSRList : dtoSRLists)   log.debug("[recvList] "+dtoSRList.toString());
        }

        return dtoSRLists;
    }

    /**
     * 송수신 목록조회
     * @param finderCd 조회자코드
     * @param targetCd 조회대상자코드 모든대상자:9999)
     * @param infoCd 조회대상파일종류 모든종류:ZZZ 계좌등록결과:R00(I0R), 자동이체결과:200(I02), 지급이체(송금)결과:300(I03), 증빙자료등록결과:Y00(IY0, AY0), 증빙자료-사후점검:Y05(IY5), 증빙자료-사후점검-검증결과:Y06(IY6),
     *               해지통보:Y03(IY3), 계좌변경접수요청:Y01(IY1), 계좌변경처리결과:Y02(IY2), 배치대행-증빙등록결과(AY0), 배치대행-계좌등록결과(A0R), 배치대행-자동이체결과(A02), 배치성실시간-송금이체(BR3) 등..
     * @param fromDt 조회범위-시작일자 YYYYMMDD
     * @param toDt 조회범위-종료일자 YYYYMMDD
     * @param listTp 목록종류 수신목록:M 송신목록:L
     * @param findRng 조회범위-수신여부 미수신건만:E 모두:A
     * @param runMode 동작모드 Y:운영 T:test
     * @return 송수신목록
     */
    public List<DtoSRList> recvList(String finderCd, String targetCd, String infoCd, String fromDt, String toDt, String listTp, String findRng, String runMode){
        log.debug("[recvList](START) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", tpList="+listTp+", runMode="+runMode);

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
     * @param sendCd 송신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011, HYPHEN통합서버:0998, HYPHEN배치대행서버:0997 등..
     * @param recvCd 수신자코드 Hyphen에서 발급한 업체코드
     * @param infoCd 파일종류구분코드 계좌등록결과:R00(I0R), 자동이체결과:200(I02), 지급이체(송금)결과:300(I03), 증빙자료등록결과:Y00(IY0, AY0), 증빙자료-사후점검:Y05(IY5), 증빙자료-사후점검-검증결과:Y06(IY6),
     *               해지통보:Y03(IY3), 계좌변경접수요청:Y01(IY1), 계좌변경처리결과:Y02(IY2), 배치대행-증빙등록결과(AY0), 배치대행-계좌등록결과(A0R), 배치대행-자동이체결과(A02), 배치성실시간-송금이체(BR3) 등..
     * @param seqNo 파일순번
     * @param sendDt 송신일자
     * @param filePath 수신대상파일 저장위치
     * @param runMode 동작모드 Y:운영 T:test
     * @param passwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
     * @return true:성공 false:실패
     */
    public boolean recvData(String sendCd, String recvCd, String infoCd, String seqNo, String sendDt, String filePath, String runMode, String passwd){
        boolean result = false;
        log.debug("[recvData](START) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", sendDt="+sendDt+", filePath="+filePath+", runMode="+runMode);

        String ipAddr   = getUseIpAddr(infoCd, runMode, false);
        int port        = getUsePort(infoCd, runMode, false);
        EncTp encTp     = getUseEncTp(infoCd, false);

        sendDt  = sendDt.length()==8 ? sendDt.substring(2) : sendDt ;
        sendDt  = sendDt.length()==14 ? sendDt.substring(2, 8) : sendDt ;

        UpdnLib updnLib = new UpdnLib();
        result = updnLib.rcvData(ipAddr, port, sendCd, recvCd, infoCd, seqNo, sendDt, filePath, this.zipYn, encTp, passwd);

        if(result)  log.debug("[recvData](SUCCESS) sendDt="+sendDt+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", filePath="+filePath+", runMode="+runMode);
        else        log.debug("[recvData](FAIL) sendDt="+sendDt+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", filePath="+filePath+", runMode="+runMode);

        return result;
    }

    /**
     * Hyphen에서 결과파일 수신
     * @param sendCd 송신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011, HYPHEN통합서버:0998, HYPHEN배치대행서버:0997 등..
     * @param recvCd 수신자코드 Hyphen에서 발급한 업체코드
     * @param infoCd 파일종류구분코드 계좌등록결과:R00(I0R), 자동이체결과:200(I02), 지급이체(송금)결과:300(I03), 증빙자료등록결과:Y00(IY0, AY0), 증빙자료-사후점검:Y05(IY5), 증빙자료-사후점검-검증결과:Y06(IY6),
     *               해지통보:Y03(IY3), 계좌변경접수요청:Y01(IY1), 계좌변경처리결과:Y02(IY2), 배치대행-증빙등록결과(AY0), 배치대행-계좌등록결과(A0R), 배치대행-자동이체결과(A02), 배치성실시간-송금이체(BR3) 등..
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

    /**
     * Hyphen에서 여러개 파일 수신
     * @param finderCd 조회자코드
     * @param targetCd 조회대상자코드 모든대상자:9999)
     * @param infoCd 조회대상파일종류 모든종류:ZZZ 계좌등록결과:R00(I0R), 자동이체결과:200(I02), 지급이체(송금)결과:300(I03), 증빙자료:Y00(IY0), 증빙자료등록결과:Y00(IY0, AY0), 증빙자료-사후점검:Y05(IY5), 증빙자료-사후점검-검증결과:Y06(IY6),
     *               해지통보:Y03(IY3), 계좌변경접수요청:Y01(IY1), 계좌변경처리결과:Y02(IY2), 배치대행-증빙등록결과(AY0), 배치대행-계좌등록결과(A0R), 배치대행-자동이체결과(A02), 배치성실시간-송금이체(BR3) 등..
     * @param fromDt 조회범위-시작일자 YYYYMMDD
     * @param toDt 조회범위-종료일자 YYYYMMDD
     * @param findRng 조회범위-수신여부 미수신건만:E 모두:A
     * @param sFNmTp 파일명타입 KSNET타입:"", K-edufine타입:KEDU
     * @param recvDir 수신파일저장 디렉토리
     * @param runMode 동작모드 Y:운영 T:test
     * @param passwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
     * @return 수신파일목록
     */
    public List<DtoFileList> recvDataMulti(String finderCd, String targetCd, String infoCd, String fromDt, String toDt, String findRng, String sFNmTp, String recvDir, String runMode, String passwd){
        log.debug("[recvDataMulti](START) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", findRng="+findRng+", sFNmTp="+sFNmTp+", recvDir="+recvDir+", runMode="+runMode);

        String ipAddr   = getUseIpAddr(infoCd, runMode, false);
        int port        = getUsePort(infoCd, runMode, false);
        EncTp encTp     = getUseEncTp(infoCd, false);

        fromDt  = fromDt.length()==8 ? fromDt.substring(2) : fromDt ;
        toDt    = toDt.length()==8 ? toDt.substring(2) : toDt ;
        MsgCode fRng    = findRng.equals(MsgCode.MSG_TP_REQ_ALL.getCode()) ? MsgCode.MSG_TP_REQ_ALL : MsgCode.MSG_TP_REQ_YET;
        //파일명타입
        FNmTp fNmTp;
        if(sFNmTp.equals(FNmTp.KEDUFIN.getCode()))  fNmTp = FNmTp.KEDUFIN;
        else fNmTp = FNmTp.DEFAULT;

        UpdnLib updnLib = new UpdnLib();
        List<DtoFileList> dtoFileLists = updnLib.rcvDataMulti(ipAddr, port, finderCd, targetCd, infoCd, fromDt, toDt, fRng, fNmTp, recvDir, this.zipYn, encTp, passwd);

        if(dtoFileLists.isEmpty())
            log.debug("[recvDataMulti](NO_DATA) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", findRng="+findRng+", runMode="+runMode);
        else{
            log.debug("[recvDataMulti](SUCCESS) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", findRng="+findRng+", runMode="+runMode);
            for(DtoFileList dtoFileList : dtoFileLists) log.debug("[recvDataMulti] "+dtoFileList);
        }

        return dtoFileLists;
    }

    /**
     * Hyphen에서 여러개 파일 수신
     * @param finderCd 조회자코드
     * @param targetCd 조회대상자코드 모든대상자:9999)
     * @param infoCd 조회대상파일종류 모든종류:ZZZ 계좌등록결과:R00(I0R), 자동이체결과:200(I02), 지급이체(송금)결과:300(I03), 증빙자료:Y00(IY0), 증빙자료등록결과:Y00(IY0, AY0), 증빙자료-사후점검:Y05(IY5), 증빙자료-사후점검-검증결과:Y06(IY6),
     *               해지통보:Y03(IY3), 계좌변경접수요청:Y01(IY1), 계좌변경처리결과:Y02(IY2), 배치대행-증빙등록결과(AY0), 배치대행-계좌등록결과(A0R), 배치대행-자동이체결과(A02), 배치성실시간-송금이체(BR3) 등..
     * @param fromDt 조회범위-시작일자 YYYYMMDD
     * @param toDt 조회범위-종료일자 YYYYMMDD
     * @param findRng 조회범위-수신여부 미수신건만:E 모두:A
     * @param sFNmTp 파일명타입 KSNET타입:"", K-edufine타입:KEDU
     * @param recvDir 수신파일저장 디렉토리
     * @param runMode 동작모드 Y:운영 T:test
     * @return 수신파일목록
     */
    public List<DtoFileList> recvDataMulti(String finderCd, String targetCd, String infoCd, String fromDt, String toDt, String findRng, String sFNmTp, String recvDir, String runMode){
        log.debug("[recvDataMulti](START) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", findRng="+findRng+", sFNmTp="+sFNmTp+", recvDir="+recvDir+", runMode="+runMode);

        String ipAddr   = getUseIpAddr(infoCd, runMode, false);
        int port        = getUsePort(infoCd, runMode, false);
        EncTp encTp     = getUseEncTp(infoCd, false);

        fromDt  = fromDt.length()==8 ? fromDt.substring(2) : fromDt ;
        toDt    = toDt.length()==8 ? toDt.substring(2) : toDt ;
        MsgCode fRng    = findRng.equals(MsgCode.MSG_TP_REQ_ALL.getCode()) ? MsgCode.MSG_TP_REQ_ALL : MsgCode.MSG_TP_REQ_YET;
        //파일명타입
        FNmTp fNmTp;
        if(sFNmTp.equals(FNmTp.KEDUFIN.getCode()))  fNmTp = FNmTp.KEDUFIN;
        else fNmTp = FNmTp.DEFAULT;

        UpdnLib updnLib = new UpdnLib();
        List<DtoFileList> dtoFileLists = updnLib.rcvDataMulti(ipAddr, port, finderCd, targetCd, infoCd, fromDt, toDt, fRng, fNmTp, recvDir, this.zipYn, encTp);

        if(dtoFileLists.isEmpty())
            log.debug("[recvDataMulti](NO_DATA) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", findRng="+findRng+", runMode="+runMode);
        else{
            log.debug("[recvDataMulti](SUCCESS) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", findRng="+findRng+", runMode="+runMode);
            for(DtoFileList dtoFileList : dtoFileLists) log.debug("[recvDataMulti] "+dtoFileList);
        }

        return dtoFileLists;
    }

    /**
     * @param filePath 처리대상파일 위치
     * @param infoCd 파일종류구분코드 C01:법인카드-승인내역, C02:법인카드-매입, C03:법인카드-청구, C04:법인카드-카드기본정보, C05:법인카드-결재정보, C06:법인카드-한도정보
     * @param dbDriver JDBC Driver
     * @param dbUrl JDBC Url
     * @param dbUser DB user id
     * @param dbPass DB user password
     * @return true:성공 false:실패
     */
    public boolean set2DB(String filePath, String infoCd, String dbDriver, String dbUrl, String dbUser, String dbPass){
        boolean result = false;
        ProcBbdata procBbdata = new ProcBbdata();
        result = procBbdata.corpCard2DB(filePath, infoCd, dbDriver, dbUrl, dbUser, dbPass);

        if(result)  log.debug("[set2DB](SUCCESS) filePath:"+filePath+", infoCd:"+infoCd);
        else        log.debug("[set2DB](FAIL) filePath:"+filePath+", infoCd:"+infoCd);

        return result;
    }

    /**
     * Hyphen에서 결과파일 수신하여 DB에 insert (법인카드사용내역에 한하여..)
     * @param sendCd 송신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011 등..
     * @param recvCd 수신자코드 Hyphen에서 발급한 업체코드
     * @param infoCd 파일종류구분코드 C01:법인카드-승인내역, C02:법인카드-매입, C03:법인카드-청구, C04:법인카드-카드기본정보, C05:법인카드-결재정보, C06:법인카드-한도정보
     * @param seqNo 파일순번
     * @param sendDt 송신일자
     * @param filePath 수신대상파일 저장위치
     * @param runMode 동작모드 Y:운영 T:test
     * @param dbDriver JDBC Driver
     * @param dbUrl JDBC Url
     * @param dbUser DB user id
     * @param dbPass DB user password
     * @param senderPwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
     * @return true:성공 false:실패
     */
    public boolean recvData2DB(String sendCd, String recvCd, String infoCd, String seqNo, String sendDt, String filePath, String runMode, String dbDriver, String dbUrl, String dbUser, String dbPass, String senderPwd){
        boolean result = false;
        log.debug("[recvData2DB](START) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", sendDt="+sendDt+", filePath="+filePath+", runMode="+runMode);

        if(!infoCd.substring(0,2).equals("C0")){
            log.error("[recvData2DB] Undefined infoCd : "+infoCd);
            return false;
        }
        String ipAddr   = getUseIpAddr(infoCd, runMode, false);
        int port        = getUsePort(infoCd, runMode, false);
        EncTp encTp     = getUseEncTp(infoCd, false);

        sendDt  = sendDt.length()==8 ? sendDt.substring(2) : sendDt ;
        sendDt  = sendDt.length()==14 ? sendDt.substring(2, 8) : sendDt ;
        UpdnLib updnLib = new UpdnLib();
        result = updnLib.rcvData(ipAddr, port, sendCd, recvCd, infoCd, seqNo, sendDt, filePath, this.zipYn, encTp, senderPwd);
        if(result)  log.debug("[recvData2DB](SUCCESS) sendDt="+sendDt+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", filePath="+filePath+", runMode="+runMode);
        else{
            log.debug("[recvData2DB](FAIL) sendDt="+sendDt+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", filePath="+filePath+", runMode="+runMode);
            return false;
        }
        ProcBbdata procBbdata = new ProcBbdata();
        result = procBbdata.corpCard2DB(filePath, infoCd, dbDriver, dbUrl, dbUser, dbPass);

        return result;
    }

    /**
     * Hyphen에서 결과파일 수신하여 DB에 insert (법인카드사용내역에 한하여..)
     * @param sendCd 송신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011 등..
     * @param recvCd 수신자코드 Hyphen에서 발급한 업체코드
     * @param infoCd 파일종류구분코드 C01:법인카드-승인내역, C02:법인카드-매입, C03:법인카드-청구, C04:법인카드-카드기본정보, C05:법인카드-결재정보, C06:법인카드-한도정보
     * @param seqNo 파일순번
     * @param sendDt 송신일자
     * @param filePath 수신대상파일 저장위치
     * @param runMode 동작모드 Y:운영 T:test
     * @param dbDriver JDBC Driver
     * @param dbUrl JDBC Url
     * @param dbUser DB user id
     * @param dbPass DB user password
     * @return true:성공 false:실패
     */
    public boolean recvData2DB(String sendCd, String recvCd, String infoCd, String seqNo, String sendDt, String filePath, String runMode, String dbDriver, String dbUrl, String dbUser, String dbPass){
        boolean result = false;
        log.debug("[recvData2DB](START) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", sendDt="+sendDt+", filePath="+filePath+", runMode="+runMode);

        if(!infoCd.substring(0,2).equals("C0")){
            log.error("[recvData2DB] Undefined infoCd : "+infoCd);
            return false;
        }
        String ipAddr   = getUseIpAddr(infoCd, runMode, false);
        int port        = getUsePort(infoCd, runMode, false);
        EncTp encTp     = getUseEncTp(infoCd, false);

        sendDt  = sendDt.length()==8 ? sendDt.substring(2) : sendDt ;
        sendDt  = sendDt.length()==14 ? sendDt.substring(2, 8) : sendDt ;
        UpdnLib updnLib = new UpdnLib();
        result = updnLib.rcvData(ipAddr, port, sendCd, recvCd, infoCd, seqNo, sendDt, filePath, this.zipYn, encTp);
        if(result)  log.debug("[recvData2DB](SUCCESS) sendDt="+sendDt+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", filePath="+filePath+", runMode="+runMode);
        else{
            log.debug("[recvData2DB](FAIL) sendDt="+sendDt+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", filePath="+filePath+", runMode="+runMode);
            return false;
        }
        ProcBbdata procBbdata = new ProcBbdata();
        result = procBbdata.corpCard2DB(filePath, infoCd, dbDriver, dbUrl, dbUser, dbPass);

        return result;
    }

    /**
     * Hyphen에서 여러개 파일 수신하여 DB에 insert (법인카드사용내역에 한하여..)
     * @param finderCd 조회자코드
     * @param targetCd 조회대상자코드 모든대상자:9999)
     * @param infoCd 조회대상파일종류 모든종류:ZZZ 계좌등록:R00, 자동이체:200, 지급이체(송금):300, 증빙자료:Y00 등..
     * @param fromDt 조회범위-시작일자 YYYYMMDD
     * @param toDt 조회범위-종료일자 YYYYMMDD
     * @param findRng 조회범위-수신여부 미수신건만:E 모두:A
     * @param sFNmTp 파일명타입 KSNET타입:"", K-edufine타입:KEDU
     * @param recvDir 수신파일저장 디렉토리
     * @param runMode 동작모드 Y:운영 T:test
     * @param dbDriver JDBC Driver
     * @param dbUrl JDBC Url
     * @param dbUser DB user id
     * @param dbPass DB user password
     * @param senderPwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
     * @return 수신처리결과목록
     */
    public List<DtoFileList> recvDataMulti2DB(String finderCd, String targetCd, String infoCd, String fromDt, String toDt, String findRng, String sFNmTp, String recvDir, String runMode, String dbDriver, String dbUrl, String dbUser, String dbPass, String senderPwd) {
        log.debug("[recvDataMulti2DB](START) sendCd=" + finderCd + ", recvCd=" + targetCd + ", infoCd=" + infoCd + ", fromDt=" + fromDt + ", toDt=" + toDt + ", findRng=" + findRng + ", sFNmTp=" + sFNmTp + ", recvDir=" + recvDir + ", runMode=" + runMode);

        String ipAddr   = getUseIpAddr(infoCd, runMode, false);
        int port        = getUsePort(infoCd, runMode, false);
        EncTp encTp     = getUseEncTp(infoCd, false);

        fromDt  = fromDt.length()==8 ? fromDt.substring(2) : fromDt ;
        toDt    = toDt.length()==8 ? toDt.substring(2) : toDt ;
        MsgCode fRng    = findRng.equals(MsgCode.MSG_TP_REQ_ALL.getCode()) ? MsgCode.MSG_TP_REQ_ALL : MsgCode.MSG_TP_REQ_YET;
        //파일명타입
        FNmTp fNmTp;
        if(sFNmTp.equals(FNmTp.KEDUFIN.getCode()))  fNmTp = FNmTp.KEDUFIN;
        else fNmTp = FNmTp.DEFAULT;

        UpdnLib updnLib = new UpdnLib();
        List<DtoFileList> dtoFileLists = updnLib.rcvDataMulti(ipAddr, port, finderCd, targetCd, infoCd, fromDt, toDt, fRng, fNmTp, recvDir, this.zipYn, encTp, senderPwd);

        ProcBbdata procBbdata = new ProcBbdata();
        boolean dbResult = false;
        if(dtoFileLists.isEmpty())
            log.debug("[recvDataMulti2DB](NO_DATA) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", findRng="+findRng+", runMode="+runMode);
        else{
            log.debug("[recvDataMulti2DB](SUCCESS) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", findRng="+findRng+", runMode="+runMode);

            for(DtoFileList dtoFileList : dtoFileLists){
                dbResult = procBbdata.corpCard2DB(dtoFileList.getFilePath(), dtoFileList.getInfoCd(), dbDriver, dbUrl, dbUser, dbPass);
                dtoFileList.setRetYn(dbResult);
                log.debug("[recvDataMulti2DB] "+dtoFileList);
            }
        }

        return dtoFileLists;
    }

    /**
     * Hyphen에서 여러개 파일 수신하여 DB에 insert (법인카드사용내역에 한하여..)
     * @param finderCd 조회자코드
     * @param targetCd 조회대상자코드 모든대상자:9999)
     * @param infoCd 조회대상파일종류 모든종류:ZZZ 계좌등록:R00, 자동이체:200, 지급이체(송금):300, 증빙자료:Y00 등..
     * @param fromDt 조회범위-시작일자 YYYYMMDD
     * @param toDt 조회범위-종료일자 YYYYMMDD
     * @param findRng 조회범위-수신여부 미수신건만:E 모두:A
     * @param sFNmTp 파일명타입 KSNET타입:"", K-edufine타입:KEDU
     * @param recvDir 수신파일저장 디렉토리
     * @param runMode 동작모드 Y:운영 T:test
     * @param dbDriver JDBC Driver
     * @param dbUrl JDBC Url
     * @param dbUser DB user id
     * @param dbPass DB user password
     * @return 수신처리결과목록
     */
    public List<DtoFileList> recvDataMulti2DB(String finderCd, String targetCd, String infoCd, String fromDt, String toDt, String findRng, String sFNmTp, String recvDir, String runMode, String dbDriver, String dbUrl, String dbUser, String dbPass) {
        log.debug("[recvDataMulti2DB](START) sendCd=" + finderCd + ", recvCd=" + targetCd + ", infoCd=" + infoCd + ", fromDt=" + fromDt + ", toDt=" + toDt + ", findRng=" + findRng + ", sFNmTp=" + sFNmTp + ", recvDir=" + recvDir + ", runMode=" + runMode);

        String ipAddr   = getUseIpAddr(infoCd, runMode, false);
        int port        = getUsePort(infoCd, runMode, false);
        EncTp encTp     = getUseEncTp(infoCd, false);

        fromDt  = fromDt.length()==8 ? fromDt.substring(2) : fromDt ;
        toDt    = toDt.length()==8 ? toDt.substring(2) : toDt ;
        MsgCode fRng    = findRng.equals(MsgCode.MSG_TP_REQ_ALL.getCode()) ? MsgCode.MSG_TP_REQ_ALL : MsgCode.MSG_TP_REQ_YET;
        //파일명타입
        FNmTp fNmTp;
        if(sFNmTp.equals(FNmTp.KEDUFIN.getCode()))  fNmTp = FNmTp.KEDUFIN;
        else fNmTp = FNmTp.DEFAULT;

        UpdnLib updnLib = new UpdnLib();
        List<DtoFileList> dtoFileLists = updnLib.rcvDataMulti(ipAddr, port, finderCd, targetCd, infoCd, fromDt, toDt, fRng, fNmTp, recvDir, this.zipYn, encTp);

        ProcBbdata procBbdata = new ProcBbdata();
        boolean dbResult = false;
        if(dtoFileLists.isEmpty())
            log.debug("[recvDataMulti2DB](NO_DATA) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", findRng="+findRng+", runMode="+runMode);
        else{
            log.debug("[recvDataMulti2DB](SUCCESS) sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", findRng="+findRng+", runMode="+runMode);

            for(DtoFileList dtoFileList : dtoFileLists){
                dbResult = procBbdata.corpCard2DB(dtoFileList.getFilePath(), dtoFileList.getInfoCd(), dbDriver, dbUrl, dbUser, dbPass);
                dtoFileList.setRetYn(dbResult);
                log.debug("[recvDataMulti2DB] "+dtoFileList);
            }
        }

        return dtoFileLists;
    }

    /**
     * Dto 리스트를 받아 요청파일로 생성
     * @param infoCd 파일종류구분코드 계좌등록:R00(I0R), 자동이체:200(I02), 지급이체(송금):300(I03), 증빙자료:Y00(IY0, AY0), 증빙자료-사후점검:Y06(IY6), 계좌변경접수결과:Y01(IY1),
     *               배치대행-증빙등록(AY0), 배치대행-자동이체(A02), 배치대행-계좌등록(A0R), 배치성실시간-송금이체(BR3)
     * @param dtoList Dto리스트 계좌등록:DtoReg (infoCd:R00, I0R, A0R 용), 자동이체:DtoBill (infoCd:200, I02, A02 용), 지급이체:DtoPay (infoCd:300, I03, BR3 용),
     *               증빙자료:DtoPrf(infoCd:Y00, IY0. AY0 용), 증빙자료-사후점검:DtoAftPrf(infoCd:Y06, IY6 용), 계좌변경접수결과:DtoShift(infoCd:Y01, IY1 용)
     * @param desFilePath 요청파일저장경로
     * @return true:성공 false:실패
     */
    public boolean makeDataFile(String infoCd, List<?> dtoList, String desFilePath){
        boolean result = false;

        if(dtoList.isEmpty()){
            log.error("[makeDataFile] dtoList is Empty~!!");
            return false;
        }
        log.debug("[makeDataFile] infoCd="+infoCd+", dto="+dtoList.get(0).getClass()+", desFilePath="+desFilePath);

        switch (infoCd) {
            case "R00":
            case "I0R":
                //dtoList type 점검
                if (!(dtoList.get(0) instanceof DtoReg)) {
                    log.error("[makeDataFile] incorrect dtoList~!!");
                    return false;
                }
                List<DtoReg> dtoRegList = new ArrayList<>();
                for (Object dto : dtoList) dtoRegList.add((DtoReg) dto);
                FfmRegCom ffmRegCom = new FfmRegCom();
                result = ffmRegCom.makeFile(dtoRegList, desFilePath);
                break;
            case "200":
            case "I02":
                if (!(dtoList.get(0) instanceof DtoBill)) {
                    log.error("[makeDataFile] incorrect dtoList~!!");
                    return false;
                }
                List<DtoBill> dtoBillList = new ArrayList<>();
                for (Object dto : dtoList) dtoBillList.add((DtoBill) dto);
                FfmBillCom ffmBillCom = new FfmBillCom();
                result = ffmBillCom.makeFile(dtoBillList, desFilePath);
                break;
            case "300":
            case "I03":
                if (!(dtoList.get(0) instanceof DtoPay)) {
                    log.error("[makeDataFile] incorrect dtoList~!!");
                    return false;
                }
                List<DtoPay> dtoPayList = new ArrayList<>();
                for (Object dto : dtoList) dtoPayList.add((DtoPay) dto);
                FfmPayCom ffmPayCom = new FfmPayCom();
                result = ffmPayCom.makeFile(dtoPayList, desFilePath);
                break;
            case "BR3":
                if (!(dtoList.get(0) instanceof DtoPay)) {
                    log.error("[makeDataFile] incorrect dtoList~!!");
                    return false;
                }
                List<DtoPay> dtoPayBtrList = new ArrayList<>();
                for (Object dto : dtoList) dtoPayBtrList.add((DtoPay) dto);
                FfmPayBtr ffmPayBtr = new FfmPayBtr();
                result = ffmPayBtr.makeFile(dtoPayBtrList, desFilePath);
                break;
            case "Y00":
            case "IY0":
            case "AY0":
                if(!(dtoList.get(0) instanceof DtoPrf)){
                    log.error("[makeDataFile] incorrect dtoList~!!");
                    return false;
                }
                List<DtoPrf> dtoPrfList = new ArrayList<>();
                for (Object dto : dtoList) dtoPrfList.add((DtoPrf) dto);
                FfmPrfReq ffmPrfReq = new FfmPrfReq();
                result = ffmPrfReq.makeFile(dtoPrfList, desFilePath);
                break;
            case "Y06":
            case "IY6":
                if(!(dtoList.get(0) instanceof DtoAftPrf)){
                    log.error("[makeDataFile] incorrect dtoList~!!");
                    return false;
                }
                List<DtoAftPrf> dtoAftPrfList = new ArrayList<>();
                for (Object dto : dtoList) dtoAftPrfList.add((DtoAftPrf) dto);
                FfmAftPrfInf ffmAftPrfInf = new FfmAftPrfInf();
                result = ffmAftPrfInf.makeFile(dtoAftPrfList, desFilePath);
                break;
            case "Y01":
            case "IY1":
                if(!(dtoList.get(0) instanceof DtoShift)){
                    log.error("[makeDataFile] incorrect dtoList~!!");
                    return false;
                }
                List<DtoShift> dtoShiftList = new ArrayList<>();
                for (Object dto : dtoList) dtoShiftList.add((DtoShift) dto);
                FfmShiftRep ffmShiftRep = new FfmShiftRep();
                result = ffmShiftRep.makeFile(dtoShiftList, desFilePath);
                break;
            case "A0R":
                if(!(dtoList.get(0) instanceof DtoReg)){
                    log.error("[makeDataFile] incorrect dtoList~!!");
                    return false;
                }
                List<DtoReg> dtoRegAgList = new ArrayList<>();
                for (Object dto : dtoList) dtoRegAgList.add((DtoReg) dto);
                FfmRegAg ffmRegAg = new FfmRegAg();
                result = ffmRegAg.makeFile(dtoRegAgList, desFilePath);
                break;
            case "A02":
                if(!(dtoList.get(0) instanceof DtoBill)){
                    log.error("[makeDataFile] incorrect dtoList~!!");
                    return false;
                }
                List<DtoBill> dtoBillAgList = new ArrayList<>();
                for (Object dto : dtoList) dtoBillAgList.add((DtoBill) dto);
                FfmBillAg ffmBillAg = new FfmBillAg();
                result = ffmBillAg.makeFile(dtoBillAgList, desFilePath);
                break;
        }

        return result;
    }

    /**
     * Dto 리스트를 받아 요청파일로 생성하여 HYPHEN으로 송신
     * @param sendCd sendCd 송신자코드 Hyphen에서 발급한 업체코드
     * @param recvCd recvCd 수신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011, HYPHEN통합서버:0998, HYPHEN배치대행서버:0997 등..
     * @param infoCd 파일종류구분코드 계좌등록:R00(I0R), 자동이체:200(I03), 지급이체(송금):300(I03), 증빙자료:Y00(IY0, AY0), 증빙자료-사후점검:Y06(IY6), 계좌변경접수결과:Y01(IY1),
     *               배치대행-증빙등록(AY0), 배치대행-자동이체(A02), 배치대행-계좌등록(A0R), 배치성실시간-송금이체(BR3)
     * @param dtoList Dto리스트 계좌등록:DtoReg (infoCd:R00, I0R, A0R 용), 자동이체:DtoBill (infoCd:200, I02, A02 용), 지급이체:DtoPay (infoCd:300, I03, BR3 용),
     *                증빙자료:DtoPrf(infoCd:Y00, IY0. AY0 용), 증빙자료-사후점검:DtoAftPrf(infoCd:Y06, IY6 용), 계좌변경접수결과:DtoShift(infoCd:Y01, IY1 용)
     * @param saveDir 생성돤파일 저장할 디렉토리
     * @param runMode 동작모드 Y:운영 T:test
     * @param passwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
     * @return true:성공 false:실패
     */
    public boolean sendDataDto(String sendCd, String recvCd, String infoCd, List<?> dtoList, String saveDir, String runMode, String passwd){
        boolean result = false;
        log.debug("[sendDataDto](START) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", saveDir="+saveDir+", runMode="+runMode);
        //저장파일경로 조립
        int saveFileSeq = 1;
        FnmTpKsnet fnmTpKsnet = new FnmTpKsnet(FnmTpKsnet.fFlagReq, Util.getCurDtTm().substring(0, 8), sendCd, recvCd, infoCd, String.format("%03d", saveFileSeq));
        String saveFilePath = saveDir+"/"+fnmTpKsnet.getFileName();
        StringBuilder sb = new StringBuilder(saveFilePath);
        for(int i=0 ; i<999 ; i++){
            if(new File(saveFilePath).exists()) saveFilePath = sb.replace(saveFilePath.length()-3, saveFilePath.length(), String.format("%03d", ++saveFileSeq)).toString();
            else break;
        }
        //파일생성
        if(!makeDataFile(infoCd, dtoList, saveFilePath)){
            log.debug("[sendDataDto](FAIL) ERROR makeDataFile()~!!");
            return false;
        }

        String ipAddr   = getUseIpAddr(infoCd, runMode, true);
        int port        = getUsePort(infoCd, runMode, true);
        EncTp encTp     = getUseEncTp(infoCd, true);

        UpdnLib updnLib = new UpdnLib();
        result = updnLib.sndData(ipAddr, port, sendCd, recvCd, infoCd, saveFilePath, this.zipYn, encTp, this.maxBps, passwd);

        if(result)  log.debug("[sendDataDto](SUCCESS) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", saveFilePath="+saveFilePath+", runMode="+runMode);
        else        log.debug("[sendDataDto](FAIL) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", saveFilePath="+saveFilePath+", runMode="+runMode);

        return result;
    }

    /**
     * Dto 리스트를 받아 요청파일로 생성하여 HYPHEN으로 송신
     * @param sendCd sendCd 송신자코드 Hyphen에서 발급한 업체코드
     * @param recvCd recvCd 수신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011, HYPHEN통합서버:0998, HYPHEN배치대행서버:0997 등..
     * @param infoCd 파일종류구분코드 계좌등록:R00(I0R), 자동이체:200(I03), 지급이체(송금):300(I03), 증빙자료:Y00(IY0, AY0), 증빙자료-사후점검:Y06(IY6), 계좌변경접수결과:Y01(IY1),
     *               배치대행-증빙등록(AY0), 배치대행-자동이체(A02), 배치대행-계좌등록(A0R), 배치성실시간-송금이체(BR3)
     * @param dtoList Dto리스트 계좌등록:DtoReg (infoCd:R00, I0R, A0R 용), 자동이체:DtoBill (infoCd:200, I02, A02 용), 지급이체:DtoPay (infoCd:300, I03, BR3 용),
     *                증빙자료:DtoPrf(infoCd:Y00, IY0. AY0 용), 증빙자료-사후점검:DtoAftPrf(infoCd:Y06, IY6 용), 계좌변경접수결과:DtoShift(infoCd:Y01, IY1 용)
     * @param saveDir 생성돤파일 저장할 디렉토리
     * @param runMode 동작모드 Y:운영 T:test
     * @return true:성공 false:실패
     */
    public boolean sendDataDto(String sendCd, String recvCd, String infoCd, List<?> dtoList, String saveDir, String runMode){
        boolean result = false;
        log.debug("[sendDataDto](START) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", saveDir="+saveDir+", runMode="+runMode);
        //저장파일경로 조립
        int saveFileSeq = 1;
        FnmTpKsnet fnmTpKsnet = new FnmTpKsnet(FnmTpKsnet.fFlagReq, Util.getCurDtTm().substring(0, 8), sendCd, recvCd, infoCd, String.format("%03d", saveFileSeq));
        String saveFilePath = saveDir+"/"+fnmTpKsnet.getFileName();
        StringBuilder sb = new StringBuilder(saveFilePath);
        for(int i=0 ; i<999 ; i++){
            if(new File(saveFilePath).exists()) saveFilePath = sb.replace(saveFilePath.length()-3, saveFilePath.length(), String.format("%03d", ++saveFileSeq)).toString();
            else break;
        }
        //파일생성
        if(!makeDataFile(infoCd, dtoList, saveFilePath)){
            log.debug("[sendDataDto](FAIL) ERROR makeDataFile()~!!");
            return false;
        }

        String ipAddr   = getUseIpAddr(infoCd, runMode, true);
        int port        = getUsePort(infoCd, runMode, true);
        EncTp encTp     = getUseEncTp(infoCd, true);

        UpdnLib updnLib = new UpdnLib();
        result = updnLib.sndData(ipAddr, port, sendCd, recvCd, infoCd, saveFilePath, this.zipYn, encTp, this.maxBps);

        if(result)  log.debug("[sendDataDto](SUCCESS) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", saveFilePath="+saveFilePath+", runMode="+runMode);
        else        log.debug("[sendDataDto](FAIL) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", saveFilePath="+saveFilePath+", runMode="+runMode);

        return result;
    }

    /**
     * 결과파일을 Dto 리스트로 변환
     * @param infoCd 파일종류구분코드 계좌등록결과:R00(I0R), 자동이체결과:200(I03), 지급이체(송금)결과:300(I03), 증빙자료등록결과:Y00(IY0, AY0), 증빙자료-사후점검:Y05(IY5), 증빙자료-사후점검-검증결과:Y06(IY6),
     *               해지통보:Y03(IY3), 계좌변경접수요청:Y01(IY1), 계좌변경처리결과:Y02(IY2), 배치대행-증빙등록결과(AY0), 배치대행-계좌등록결과(A0R), 배치대행-자동이체결과(A02), 배치성실시간-송금이체(BR3)
     * @param srcFilePath 결과파일위치
     * @return Dto리스트 계좌등록:DtoReg (infoCd:R00, I0R, A0R 용), 자동이체:DtoBill (infoCd:200, I02, A02 용), 지급이체:DtoPay (infoCd:300, I03, BR3 용),
     *                  증빙자료:DtoPrf(infoCd:Y00, IY0. AY0 용), 증빙자료-사후점검:DtoAftPrf(infoCd:Y05, IY5, Y06, IY6 용),  해지통보:DtoCanc(Y03, IY3 용)
     *                  계좌변경접수/처리결과:DtoShift(infoCd:Y01, IY1, Y02, IY2 용)
     */
    public List<?> makeDtoList(String infoCd, String srcFilePath){
        List<?> dtoList = null ;
        switch (infoCd) {
            case "R00":
            case "I0R":
                FfmRegCom regCom = new FfmRegCom();
                dtoList = regCom.makeDtoList(srcFilePath);
                break;
            case "200":
            case "I02":
                FfmBillCom billCom = new FfmBillCom();
                dtoList = billCom.makeDtoList(srcFilePath);
                break;
            case "300":
            case "I03":
                FfmPayCom payCom = new FfmPayCom();
                dtoList = payCom.makeDtoList(srcFilePath);
                break;
            case "BR3":
                FfmPayBtr payBtr = new FfmPayBtr();
                dtoList = payBtr.makeDtoList(srcFilePath);
                break;
            case "Y00":
            case "IY0":
            case "AY0":
                FfmPrfRep prfRep = new FfmPrfRep();
                dtoList = prfRep.makeDtoList(srcFilePath);
                break;
            case "Y05":
            case "IY5":
                FfmAftPrfReq aftPrfReq = new FfmAftPrfReq();
                dtoList = aftPrfReq.makeDtoList(srcFilePath);
                break;
            case "Y06":
            case "IY6":
                FfmAftPrfInf aftPrfInf = new FfmAftPrfInf();
                dtoList = aftPrfInf.makeDtoList(srcFilePath);
                break;
            case "Y03":
            case "IY3":
                FfmCanc canc = new FfmCanc();
                dtoList = canc.makeDtoList(srcFilePath);
                break;
            case "Y01":
            case "IY1":
                FfmShiftReq shiftReq = new FfmShiftReq();
                dtoList = shiftReq.makeDtoList(srcFilePath);
                break;
            case "Y02":
            case "IY2":
                FfmShiftCnfrm shiftCnfrm = new FfmShiftCnfrm();
                dtoList = shiftCnfrm.makeDtoList(srcFilePath);
                break;
            case "A0R":
                FfmRegAg regAg = new FfmRegAg();
                dtoList = regAg.makeDtoList(srcFilePath);
                break;
            case "A02":
                FfmBillAg billAg = new FfmBillAg();
                dtoList = billAg.makeDtoList(srcFilePath);
                break;
        }

        return dtoList;
    }

    /**
     * Hyphen에서 결과파일 수신하여 Dto 리스트로 변환
     * @param sendCd 송신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011, HYPHEN통합서버:0998, HYPHEN배치대행서버:0997 등..
     * @param recvCd 수신자코드 Hyphen에서 발급한 업체코드
     * @param infoCd 파일종류구분코드 계좌등록결과:R00(I0R), 자동이체결과:200(I03), 지급이체(송금)결과:300(I03), 증빙자료등록결과:Y00(IY0), 증빙자료-사후점검:Y05(IY5), 증빙자료-사후점검-검증결과:Y06(IY6),
     *               해지통보:Y03(IY3), 계좌변경접수요청:Y01(IY1), 계좌변경처리결과:Y02(IY2), 배치대행-증빙등록결과(AY0), 배치대행-계좌등록결과(A0R), 배치대행-자동이체결과(A02), 배치성실시간-송금이체(BR3)
     * @param seqNo 파일순번
     * @param sendDt 송신일자
     * @param saveDir 수신파일보관경로
     * @param runMode runMode 동작모드 Y:운영 T:test
     * @param passwd 통신용 비밀번호(기본적으로 통신용 별도비번 필요없음, 필요시 HYPHEN에 등록요청 후 사용)
     * @return Dto리스트 계좌등록:DtoReg (infoCd:R00, I0R, A0R 용), 자동이체:DtoBill (infoCd:200, I02, A02 용), 지급이체:DtoPay (infoCd:300, I03, BR3 용),
     *                  증빙자료:DtoPrf(infoCd:Y00, IY0. AY0 용), 증빙자료-사후점검:DtoAftPrf(infoCd:Y05, IY5, Y06, IY6 용),  해지통보:DtoCanc(Y03, IY3 용)
     *                  계좌변경접수/처리결과:DtoShift(infoCd:Y01, IY1, Y02, IY2 용)
     */
    public List<?> recvDataDto(String sendCd, String recvCd, String infoCd, String seqNo, String sendDt, String saveDir, String runMode, String passwd){
        log.debug("[recvDataDto](START) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", sendDt="+sendDt+", saveDir="+saveDir+", runMode="+runMode);

        String ipAddr   = getUseIpAddr(infoCd, runMode, false);
        int port        = getUsePort(infoCd, runMode, false);
        EncTp encTp     = getUseEncTp(infoCd, false);
        sendDt  = sendDt.length()==8 ? sendDt.substring(2) : sendDt ;
        sendDt  = sendDt.length()==14 ? sendDt.substring(2, 8) : sendDt ;
        //수신파일명조립
        int saveFileSeq = 1;
        FnmTpKsnet fnmTpKsnet = new FnmTpKsnet(FnmTpKsnet.fFlagRep, Util.getCurDtTm().substring(0, 8), sendCd, recvCd, infoCd, String.format("%03d", saveFileSeq));
        String saveFilePath = saveDir+"/"+fnmTpKsnet.getFileName();
        StringBuilder sb = new StringBuilder(saveFilePath);
        for(int i=0 ; i<999 ; i++){
            if(new File(saveFilePath).exists()) saveFilePath = sb.replace(saveFilePath.length()-3, saveFilePath.length(), String.format("%03d", ++saveFileSeq)).toString();
            else break;
        }

        UpdnLib updnLib = new UpdnLib();
        boolean result = updnLib.rcvData(ipAddr, port, sendCd, recvCd, infoCd, seqNo, sendDt, saveFilePath, this.zipYn, encTp, passwd);
        if(result)  log.debug("[recvDataDto](SUCCESS) sendDt="+sendDt+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", saveFilePath="+saveFilePath+", runMode="+runMode);
        else{
            log.debug("[recvDataDto](FAIL) sendDt="+sendDt+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", saveFilePath="+saveFilePath+", runMode="+runMode);
            return null;
        }

        return makeDtoList(infoCd, saveFilePath);
    }

    /**
     * Hyphen에서 결과파일 수신하여 Dto 리스트로 변환
     * @param sendCd 송신자코드 '0'+3자리은행코드, 하나은행:0081, 농협:0011, HYPHEN통합서버:0998, HYPHEN배치대행서버:0997 등..
     * @param recvCd 수신자코드 Hyphen에서 발급한 업체코드
     * @param infoCd 파일종류구분코드 계좌등록결과:R00(I0R), 자동이체결과:200(I03), 지급이체(송금)결과:300(I03), 증빙자료등록결과:Y00(IY0), 증빙자료-사후점검:Y05(IY5), 증빙자료-사후점검-검증결과:Y06(IY6),
     *               해지통보:Y03(IY3), 계좌변경접수요청:Y01(IY1), 계좌변경처리결과:Y02(IY2), 배치대행-증빙등록결과(AY0), 배치대행-계좌등록결과(A0R), 배치대행-자동이체결과(A02), 배치성실시간-송금이체(BR3)
     * @param seqNo 파일순번
     * @param sendDt 송신일자
     * @param saveDir 수신파일보관경로
     * @param runMode runMode 동작모드 Y:운영 T:test
     * @return Dto리스트 계좌등록:DtoReg (infoCd:R00, I0R, A0R 용), 자동이체:DtoBill (infoCd:200, I02, A02 용), 지급이체:DtoPay (infoCd:300, I03, BR3 용),
     *                  증빙자료:DtoPrf(infoCd:Y00, IY0. AY0 용), 증빙자료-사후점검:DtoAftPrf(infoCd:Y05, IY5, Y06, IY6 용),  해지통보:DtoCanc(Y03, IY3 용)
     *                  계좌변경접수/처리결과:DtoShift(infoCd:Y01, IY1, Y02, IY2 용)
     */
    public List<?> recvDataDto(String sendCd, String recvCd, String infoCd, String seqNo, String sendDt, String saveDir, String runMode){
        log.debug("[recvDataDto](START) sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", sendDt="+sendDt+", saveDir="+saveDir+", runMode="+runMode);

        String ipAddr   = getUseIpAddr(infoCd, runMode, false);
        int port        = getUsePort(infoCd, runMode, false);
        EncTp encTp     = getUseEncTp(infoCd, false);
        sendDt  = sendDt.length()==8 ? sendDt.substring(2) : sendDt ;
        sendDt  = sendDt.length()==14 ? sendDt.substring(2, 8) : sendDt ;
        //수신파일명조립
        int saveFileSeq = 1;
        FnmTpKsnet fnmTpKsnet = new FnmTpKsnet(FnmTpKsnet.fFlagRep, Util.getCurDtTm().substring(0, 8), sendCd, recvCd, infoCd, String.format("%03d", saveFileSeq));
        String saveFilePath = saveDir+"/"+fnmTpKsnet.getFileName();
        StringBuilder sb = new StringBuilder(saveFilePath);
        for(int i=0 ; i<999 ; i++){
            if(new File(saveFilePath).exists()) saveFilePath = sb.replace(saveFilePath.length()-3, saveFilePath.length(), String.format("%03d", ++saveFileSeq)).toString();
            else break;
        }

        UpdnLib updnLib = new UpdnLib();
        boolean result = updnLib.rcvData(ipAddr, port, sendCd, recvCd, infoCd, seqNo, sendDt, saveFilePath, this.zipYn, encTp);
        if(result)  log.debug("[recvDataDto](SUCCESS) sendDt="+sendDt+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", saveFilePath="+saveFilePath+", runMode="+runMode);
        else{
            log.debug("[recvDataDto](FAIL) sendDt="+sendDt+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", saveFilePath="+saveFilePath+", runMode="+runMode);
            return null;
        }

        return makeDtoList(infoCd, saveFilePath);
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
