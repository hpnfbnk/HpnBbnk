package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.define.EncTp;
import com.hyphen.fbnk.bbnk.define.InfoCdPrf;
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

    private boolean zipYn       = false;    //압축여부 default=비압축
    private int limmitKBps      = 1024;     //전송제한속도 default=1Mbps
    private boolean delnYn      = false;    //전용선사용여부 default=비사용
    
    private boolean customInfoYn = false;   //사용자정의 접속정보 사용여부

    private static final Log log = LogFactory.getLog(HpnBbnk.class);
    //private static final Logger logger = LoggerFactory.getLogger(HpnBbnk.class);

    public static void main(String[] args) {
        //System.out.println("com.hyphen.fbnk.bbnk.HpnBbnk");
        //log.debug("***** test custom logging debug");
        //log.trace("***** test custom logging trace");

        HpnBbnk hpnBbnk = new HpnBbnk();
        //HpnBbnk hpnBbnk = new HpnBbnk(true);
        //HpnBbnk hpnBbnk = new HpnBbnk("208.181.28.149", "209.181.28.99", 29994, "109.138.30.10", 29995, true, 2048, false);

        for(int i=1 ; i<=1 ;i++){
           new Thread(""+i){
                @Override
                public void run() {
                    boolean result = hpnBbnk.sendData("A00"+getName(),"008"+getName(), "R00", "./sam.txt", "T");
                    if(result) System.out.println("[thid:"+getName()+"] hpnBbnk.sendData : SUCCESS");
                    else System.out.println("[thid:"+getName()+"] hpnBbnk.sendData : FAIL");
                }
            }.start();
            try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {}
        }
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
     * 접속정보를 임의로 지정하여 Instance 생성합니다.
     * @param hpnSvrIpProd Hyphen 운영서버 IP
     * @param hpnSvrIpTest Hyphen Test서버 IP
     * @param hpnSvrPort Hyphen 서버 Port
     * @param hpnSvrIpPrf Hyphen 증빙서버 IP
     * @param hpnSvrPortPrf Hyphen 증빙서버 Port
     * @param zipYn 압축여부 default=비압축
     * @param limmitKBps 전송제한속도 default=1Mbps
     * @param delnYn 전용선사용여부 default=비사용
     */
    public HpnBbnk(String hpnSvrIpProd, String hpnSvrIpTest, int hpnSvrPort, String hpnSvrIpPrf, int hpnSvrPortPrf, boolean zipYn, int limmitKBps, boolean delnYn) {
        this.customInfoYn = true;

        this.hpnSvrIpProd = hpnSvrIpProd;
        this.hpnSvrIpTest = hpnSvrIpTest;
        this.hpnSvrPort = hpnSvrPort;
        this.hpnSvrIpPrf = hpnSvrIpPrf;
        this.hpnSvrPortPrf = hpnSvrPortPrf;
        this.zipYn = zipYn;
        this.limmitKBps = limmitKBps;
        this.delnYn = delnYn;
    }

    /**
     * Hyphen으로 요청파일 전송
     * @param sendCd 송신자코드
     * @param recvCd 수신자코드
     * @param infoCd 파일종류구분코드
     * @param filePath 전송대상파일 위치
     * @param runMode 동작모드 Y:운영 T:test
     * @return true:성공 false:실패
     */
    public boolean sendData(String sendCd, String recvCd, String infoCd, String filePath, String runMode){
        boolean result = false;
        log.debug("[sendData] sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", filePath="+filePath+", runMode="+runMode);

        String ipAddr   = getUseIpAddr(infoCd, runMode);
        int port        = getUsePort(infoCd, runMode);
        EncTp encTp     = getUseEncTp(infoCd);

        UpdnLib updnLib = new UpdnLib();
        result = updnLib.sndData(ipAddr, port, sendCd, recvCd, infoCd, filePath, this.zipYn, encTp, this.limmitKBps);

        log.debug("result="+result);
        return result;
    }

    /**
     * 목록조회
     */
    public List<String> recvList(){

        return null;
    }

    /**
     * 수신
     */
    public List<String> recvData(){

        return null;
    }

    private String getUseIpAddr(String infoCd, String runMode){
        String useIpAddr = null;
        if(!customInfoYn){
            //일반&Internet
            if(!InfoCdPrf.isValue(infoCd)&&!delnYn)
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpProd : this.hpnSvrIpTest;
            //증빙&Internet
            else if(InfoCdPrf.isValue(infoCd)&&!delnYn)
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpPrf : this.hpnSvrIpTest;
            //일반&전용선
            else if(!InfoCdPrf.isValue(infoCd)&&delnYn)
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpProdDeln : this.hpnSvrIpTestDeln;
            //증빙&전용선
            else if(InfoCdPrf.isValue(infoCd)&&delnYn)
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpPrfDeln : this.hpnSvrIpTestDeln;
        }
        else{
            //일반
            if(!InfoCdPrf.isValue(infoCd))
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpProd : this.hpnSvrIpTest;
            //증빙
            else if(InfoCdPrf.isValue(infoCd))
                useIpAddr = runMode.equals("Y") ? this.hpnSvrIpPrf : this.hpnSvrIpTest;
        }

        return useIpAddr;
    }

    private int getUsePort(String infoCd, String runMode){
        int usePort = 0;
        if(!customInfoYn){
            //일반&Internet
            if(!InfoCdPrf.isValue(infoCd)&&!delnYn)
                usePort = this.hpnSvrPort;
            //증빙&Internet
            else if(InfoCdPrf.isValue(infoCd)&&!delnYn)
                usePort = this.hpnSvrPortPrf;
            //일반&전용선
            else if(!InfoCdPrf.isValue(infoCd)&&delnYn)
                usePort = this.hpnSvrPortDeln;
            //증빙&전용선
            else if(InfoCdPrf.isValue(infoCd)&&delnYn)
                usePort = this.hpnSvrPortPrfDeln;
        }
        else{
            //일반
            if(!InfoCdPrf.isValue(infoCd))
                usePort = this.hpnSvrPort;
            //증빙
            else if(InfoCdPrf.isValue(infoCd))
                usePort = this.hpnSvrPortPrf;
        }

        return usePort;
    }

    private EncTp getUseEncTp(String infoCd){
        EncTp useEncTp = EncTp.NONE;

        //일반&Internet
        if(!InfoCdPrf.isValue(infoCd)&&!delnYn)     useEncTp = EncTp.SEED;
        //증빙&Internet
        else if(InfoCdPrf.isValue(infoCd)&&!delnYn) useEncTp = EncTp.KECB;

        return useEncTp;
    }










}
