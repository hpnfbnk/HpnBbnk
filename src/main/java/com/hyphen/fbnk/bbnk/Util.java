package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.define.Define;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Util {
    private static final Log log = LogFactory.getLog(Util.class);

    public static String getCurDtTm(){
        int yyyy, mm, dd, hh, min, ss;
        Calendar cal = Calendar.getInstance();

        yyyy    = cal.get(Calendar.YEAR);
        mm 		= cal.get(Calendar.MONTH);
        dd 		= cal.get(Calendar.DATE);
        hh 	    = cal.get(Calendar.HOUR_OF_DAY);
        min 	= cal.get(Calendar.MINUTE);
        ss 	    = cal.get(Calendar.SECOND);

        StringBuffer sb = new StringBuffer();
        sb.append(yyyy).append(mm < 9 ? "0" : "").append(mm + 1).append(dd < 10 ? "0" : "").append(dd).append(hh < 10 ? "0" : "").append(hh);
        sb.append(min < 10 ? "0" : "").append(min).append(ss < 10 ? "0" : "").append(ss);

        return sb.toString();
    }

    public static String spanCurDt(int befDay){
        Calendar cal = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        cal.add(Calendar.DATE, befDay);

        return format.format(cal.getTime());
    }

    public static void deleteFile(String fPath){
        File del = new File(fPath);
        del.delete();
    }

    public static long getFileSize(String fPath){
        File file = new File(fPath);
        return file.length();
    }

    /**
     * 최대전송속도설정
     * @param netBps 최대전송속도
     * @return 최대전송 Cps
     */
    public static long getMaxCps(String netBps){
        long maxCps = 0L;

        if (netBps.length() < 2 || netBps.length() > 10) {
            log.debug("[controlSpeed] Abnormal netBps+["+netBps+"]");
            netBps = "1M";
        }
        log.debug("[controlSpeed] MAX_BPS=["+netBps+"]");

        if (netBps.substring(netBps.length() - 1, netBps.length()).equals("M") || netBps.substring(netBps.length() - 1, netBps.length()).equals("m"))
            maxCps = Integer.parseInt(netBps.substring(0, netBps.length() - 1)) * 128 * 1024; // M = 1024*1024
        else if (netBps.substring(netBps.length() - 1, netBps.length()).equals("K") || netBps.substring(netBps.length() - 1, netBps.length()).equals("k"))
            maxCps = Integer.parseInt(netBps.substring(0, netBps.length() - 1)) * 128;
        else {
            if (Integer.parseInt(netBps.substring(0, netBps.length() - 1)) > (1024 * 512))
                maxCps = (1024 * 512) / 8;
            else
                maxCps = Integer.parseInt(netBps.substring(0, netBps.length() - 1)) / 8;
        }
        if (maxCps <= 0 || maxCps > (Define.MAX_KBPS.getValue() * 128)) maxCps = Define.MAX_KBPS.getValue() * 128;

        return (maxCps * 9) / 10;
    }

    /**
     * 전송속도 제한
     * @param maxCps
     * @param sTime
     * @param oLen
     * @param sLen
     * @return 기전송 data size
     */
    public static long controlSpeed(long maxCps, long sTime, long oLen, int sLen) throws InterruptedException {
        long cTime 		= 0;
        long waitSecs 	= 0;

        /* millisec -> sec */
        cTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        waitSecs = ((oLen + sLen) / maxCps) + sTime - cTime;
        if (waitSecs > 0L) {
            //log.trace("[controlSpeed] MAX BPS(" + ((maxCps * 10) / 9)/128 + "Kbps) above -> wait(" + waitSecs + ")=((" + oLen + "+" + sLen + ")/" + maxCps + ")-(" + (cTime - sTime) + ")");
            log.trace("[controlSpeed] MAX BPS(" + ((maxCps * 10) / 9)/128 + "Kbps) above -> wait("+waitSecs+")");
            Thread.sleep(waitSecs * 1000);
        }

        return oLen + sLen;
    }

    public static boolean isAscii(byte chkByte){
        return (0x80 & chkByte) == 0;
    }

    public static String byteFormat(String str, int len, String encodeTp) throws UnsupportedEncodingException {
        String rt_val;
        byte[] strBytes = str.getBytes(encodeTp);
        if(encodeTp.equals("euc-kr")||encodeTp.equals("EUC-KR")||encodeTp.equals("euc_kr")||encodeTp.equals("EUC_KR")){
            int cntSize, ttLen;
            //한글깨지지않게..
            for(ttLen = 0 ; ttLen < strBytes.length && ttLen < len ;){
                if(isAscii(strBytes[ttLen]))    cntSize = 1;
                else cntSize = 2;
                if(ttLen+cntSize > strBytes.length || ttLen+cntSize > len) break;
                ttLen += cntSize;
            }
            byte[] tgtBytes = new byte[ttLen];
            System.arraycopy(strBytes, 0, tgtBytes, 0, ttLen);

            rt_val = new String(tgtBytes, encodeTp);
            if(ttLen < len){
                int pad_len = len - tgtBytes.length;
                StringBuilder pad_sb = new StringBuilder();
                for(int i=0 ; i<pad_len ; i++)	pad_sb.append(" ");
                rt_val += pad_sb;
            }
        }
        else{
            if(strBytes.length > len) rt_val = new String(strBytes, 0, len, encodeTp);
            else {
                int pad_len = len - strBytes.length;
                StringBuilder pad_sb = new StringBuilder();
                for(int i=0 ; i<pad_len ; i++)	pad_sb.append(" ");
                rt_val = str + pad_sb;
            }
        }

        return rt_val;
    }

    public static byte[] readLineByte(FileInputStream fis) throws IOException{
        byte[] tmpByte = new byte[1024*2];
        int iByte = 0, byteCnt = 0, chkByte = 0;

        while(true){
            iByte = fis.read();
            //log.debug("[readLineByte] iByte="+iByte);
            if(iByte<0)	break;	//EOF

            if(iByte==10)   break;  //LF
            else if(iByte==13){     //CR&LF
                chkByte = fis.read();
                if(chkByte==10) break;
                else{
                    tmpByte[byteCnt] = (byte) iByte;
                    byteCnt++;
                    iByte = chkByte;
                }
            }

            tmpByte[byteCnt] = (byte) iByte;
            byteCnt++;
            if(byteCnt>(1024*2))	break;
        }
        byte[] resultByte = new byte[byteCnt];
        System.arraycopy(tmpByte, 0, resultByte, 0, byteCnt);

        return resultByte;
    }


}
