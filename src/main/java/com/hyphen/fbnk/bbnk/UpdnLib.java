package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.define.*;
import com.hyphen.fbnk.bbnk.dto.DtoFileList;
import com.hyphen.fbnk.bbnk.dto.DtoSRList;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;
import com.hyphen.fbnk.bbnk.msg.FnmTpKEduFine;
import com.hyphen.fbnk.bbnk.msg.MsgNego;
import com.hyphen.fbnk.bbnk.msg.MsgSRList;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static java.lang.System.*;

public class UpdnLib {
    private static final Log log = LogFactory.getLog(UpdnLib.class);

    //목록조회
    public List<DtoSRList> rcvList(String ipAddr, int port, String finderCd, String targetCd, String infoCd, String fromDt, String toDt, MsgCode tpList, MsgCode fRng, EncTp encTp){
        log.debug("[rcvList] ipAddr="+ipAddr+", port="+port+", sendCd="+finderCd+", recvCd="+targetCd+", infoCd="+infoCd+", fromDt="+fromDt+", toDt="+toDt+", tpList="+tpList+", fRng="+fRng+", encTp="+encTp);
        List<DtoSRList> dtoSRLists = new ArrayList<>();
        SocketClient sockClnt = null;
        byte[] sndMsg, rcvMsg, listRec;
        MsgNego negoMsg;
        MsgSRList msgSRList;

        try {
            sockClnt = new SocketClient(ipAddr, port, encTp);
            sockClnt.connect();
            if(encTp != EncTp.NONE) sockClnt.setEnc();

            //개시전문전송
            sndMsg = new MsgNego(finderCd, targetCd).getMsgOpenReq();
            log.trace("[rcvList]    sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
            sockClnt.writeMsg(sndMsg);
            //응답전문점검
            chkRplyMsg(sockClnt, MsgCode.MSG_OPEN_REP);
            //자료수신요청
            sndMsg = new MsgNego(finderCd, targetCd).getMsgRecvReq(infoCd, fromDt, toDt, tpList, fRng, "", false);
            log.trace("[rcvList]    sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
            sockClnt.writeMsg(sndMsg);
            //응답전문점검
            chkRplyMsg(sockClnt, MsgCode.MSG_TRANS_REP);

            int recCnt = 0, recLen = 0, i = 0;
            while(true){
                rcvMsg = sockClnt.readMsg();
                log.trace("[rcvList]    rcvMsg=["+new String(rcvMsg)+"]("+rcvMsg.length+")");
                negoMsg = new MsgNego(rcvMsg);
                //자료송신인가..
                if(negoMsg.getMsgType()==MsgCode.MSG_DATA_REQ){
                    recCnt = negoMsg.getRecCnt();
                    recLen = negoMsg.getRecLen();
                    //log.debug("[rcvList] recCnt="+recCnt+", recLen="+recLen);
                    listRec = new byte[recLen];
                    for(i=0 ; i<recCnt ; i++){
                        arraycopy(rcvMsg, MsgNego.msgSize+(recLen*i), listRec, 0, recLen);
                        msgSRList = new MsgSRList(listRec);
                        //log.debug("[rcvList] msgSRList="+msgSRList.toString());
                        dtoSRLists.add(new DtoSRList(msgSRList.getInfoCd().trim(), msgSRList.getSendCd().trim(), msgSRList.getRecvCd().trim(), msgSRList.getSeqNo().trim(), msgSRList.getSendTm().trim(), msgSRList.getRecvTm().trim(), Long.parseLong(msgSRList.getFileSize())));
                    }
                }
                //개별종료요청
                else if(negoMsg.getMsgType()==MsgCode.MSG_PARTEND_REQ)
                    sndRplyMsg(sockClnt, negoMsg, MsgCode.MSG_PARTEND_REP, RtnCode.FINE);
                //전체종료요청
                else if(negoMsg.getMsgType()==MsgCode.MSG_CLOSE_REQ)
                    break;
                else
                    throw new Exception("Abnormal MsgType:"+negoMsg.getMsgType().getCode());
            }
            //전체종료응답
            sndRplyMsg(sockClnt, negoMsg, MsgCode.MSG_CLOSE_REP, RtnCode.FINE);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        } finally {
            if(sockClnt!=null)  sockClnt.close();
        }

        return dtoSRLists;
    }

    public void sndRplyMsg(SocketClient sockClnt, MsgNego msgNego, MsgCode msgType, RtnCode rtnCode) throws Exception {
        msgNego.setMsgType(msgType);
        String sendCd   = msgNego.getSendCd();
        String recvCd   = msgNego.getRecvCd();
        msgNego.setSendCd(recvCd);
        msgNego.setRecvCd(sendCd);
        msgNego.setSendTm(Util.getCurDtTm().substring(2));
        msgNego.setRtnCd(rtnCode);
        byte[] sndMsg = msgNego.getMsg();
        log.trace("[sndRplyMsg] sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
        sockClnt.writeMsg(sndMsg);
    }

    public void sndMissRplyMsg(SocketClient sockClnt, MsgNego msgNego, byte[] missList, int missCnt) throws Exception {
        msgNego.setMsgType(MsgCode.MSG_MISS_REP);
        String sendCd   = msgNego.getSendCd();
        String recvCd   = msgNego.getRecvCd();
        msgNego.setSendCd(recvCd);
        msgNego.setRecvCd(sendCd);
        msgNego.setSendTm(Util.getCurDtTm().substring(2));
        msgNego.setRtnCd(RtnCode.FINE);
        msgNego.setMissCnt(missCnt);
        byte[] sndMsg = msgNego.getMsgMissRep(missList);
        log.trace("[sndMissRplyMsg] sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
        sockClnt.writeMsg(sndMsg);
    }

    //수신
    public boolean rcvData(String ipAddr, int port, String sendCd, String recvCd, String infoCd, String seqNo, String sendDt, String filePath, boolean zipYn, EncTp encTp){
        log.debug("[rcvData] ipAddr="+ipAddr+", port="+port+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", seqNo="+seqNo+", sendDt="+sendDt+", filePath="+filePath+", zipYn="+zipYn+", encTp="+encTp);
        boolean result = false;
        SocketClient sockClnt = null;
        byte[] sndMsg, rcvMsg;
        MsgNego negoMsg;
        RandomAccessFile dataFp = null;

        String finderCd = recvCd;
        String targetCd = sendCd;

        try {
            sockClnt = new SocketClient(ipAddr, port, encTp);
            sockClnt.connect();
            if(encTp != EncTp.NONE) sockClnt.setEnc();

            //개시전문전송
            sndMsg = new MsgNego(finderCd, targetCd).getMsgOpenReq();
            log.trace("[rcvData]    sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
            sockClnt.writeMsg(sndMsg);
            //응답전문점검
            chkRplyMsg(sockClnt, MsgCode.MSG_OPEN_REP);
            //자료수신요청
            sndMsg = new MsgNego(finderCd, targetCd).getMsgRecvReq(infoCd, sendDt, sendDt, MsgCode.MSG_TP_RCV_REQ, MsgCode.MSG_TP_REQ_ALL, seqNo, zipYn);
            log.trace("[rcvData]    sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
            sockClnt.writeMsg(sndMsg);
            //응답전문점검
            RtnCode transRtnCode = chkRplyMsg(sockClnt, MsgCode.MSG_TRANS_REP);

            long dataMsgCnt = 0L;
            String tmpfPath = null;
            byte[] dataBuf = new byte[Define.MAX_DATA_MSG_SIZE.getValue()];
            byte[] missList = new byte[Define.MAX_SEQ_CNT.getValue()];
            Arrays.fill(missList, (byte) 0x30);
            while(true) {
                rcvMsg = sockClnt.readMsg();
                negoMsg = new MsgNego(rcvMsg);
                if(negoMsg.getMsgType()==MsgCode.MSG_DATA_REQ||negoMsg.getMsgType()==MsgCode.MSG_MISS_DATA)
                    log.trace("[rcvData]    rcvMsg=["+new String(rcvMsg).substring(0, MsgNego.msgSize)+"...]("+rcvMsg.length+")["+negoMsg.getBlockNo()+":"+negoMsg.getSeqNo()+"]");
                else
                    log.trace("[rcvData]    rcvMsg=[" + new String(rcvMsg) + "](" + rcvMsg.length + ")");

                //자료송신일때...
                if(negoMsg.getMsgType()==MsgCode.MSG_DATA_REQ) {
                    dataMsgCnt++;
                    //송신정보해석(첫번째 자료송신전문일때만 송신정보 세팅)
                    if(dataMsgCnt==1L){
                        Random rand = new Random();
                        tmpfPath = filePath+"."+rand.nextInt(999999);
                        dataFp = new RandomAccessFile(tmpfPath, "rw");
                        log.debug("[rcvData] receive to "+tmpfPath);
                    }
                    arraycopy(rcvMsg, MsgNego.msgSize, dataBuf, 0, negoMsg.getBinLen());
                    dataFp.write(dataBuf, 0, negoMsg.getBinLen());
                    missList[negoMsg.getSeqNo()-1] = '1';
                }
                //결번보고일때...
                else if(negoMsg.getMsgType()==MsgCode.MSG_MISS_DATA){
                    arraycopy(rcvMsg, MsgNego.msgSize, dataBuf, 0, negoMsg.getBinLen());
                    insMissData(dataFp, dataBuf, negoMsg.getBlockNo(), negoMsg.getSeqNo(), negoMsg.getBinLen());
                    missList[negoMsg.getSeqNo()-1] = '1';
                }
                //결번확인요청
                else if(negoMsg.getMsgType()==MsgCode.MSG_MISS_REQ){
                    int missCnt = 0;
                    for(int i=0 ; i<negoMsg.getSeqNo() ; i++) if(missList[i]=='0')    missCnt++;

                    sndMissRplyMsg(sockClnt, negoMsg, missList, missCnt);
                    log.debug("[rcvData] sndMissRply BlockNo="+negoMsg.getBlockNo()+", seqNo="+negoMsg.getSeqNo()+", missCnt="+missCnt);
                    //결번이 없으면 결번목록초기화
                    if(missCnt==0)  Arrays.fill(missList, (byte) 0x30);
                }
                //개별종료요청(파일정상수신 후)
                else if(negoMsg.getMsgType()==MsgCode.MSG_PARTEND_REQ && transRtnCode==RtnCode.FINE){
                    if(dataFp!=null)    dataFp.close();
                    if(zipYn)   decompressFile(tmpfPath, filePath);
                    else{
                        File srcFile = new File(tmpfPath);
                        File desFile = new File(filePath);
                        if(desFile.exists()) {
                            if(!desFile.delete())   throw new Exception("Fail delete file["+filePath+"]");
                        }
                        if(!srcFile.renameTo(desFile))  throw new Exception("Fail rename file["+tmpfPath+"=>"+filePath+"]");
                        log.debug("[rcvData] "+tmpfPath+" =rename=> "+filePath);
                    }
                    sndRplyMsg(sockClnt, negoMsg, MsgCode.MSG_PARTEND_REP, RtnCode.FINE);
                    result = true;
                }
                //개별종료요청(수신대상없음 후)
                else if(negoMsg.getMsgType()==MsgCode.MSG_PARTEND_REQ && transRtnCode==RtnCode.NO_DATA){
                    sndRplyMsg(sockClnt, negoMsg, MsgCode.MSG_PARTEND_REP, RtnCode.FINE);
                    result = false;
                }
                //전체종료요청
                else if(negoMsg.getMsgType()==MsgCode.MSG_CLOSE_REQ)
                    break;
                else
                    throw new Exception("Abnormal MsgType:"+negoMsg.getMsgType().getCode());
            }
            //전체죵료응답
            sndRplyMsg(sockClnt, negoMsg, MsgCode.MSG_CLOSE_REP, RtnCode.FINE);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        } finally {
            if(sockClnt!=null)  sockClnt.close();
            if(dataFp!=null) {try {dataFp.close();} catch (Exception e) {e.printStackTrace();}}
        }

        return result;
    }

    public void insMissData(RandomAccessFile dataFp, byte[] dataBuf, int blockNo, int seqNo, int binLen) throws Exception {
        log.debug("[insMissData] blockNo="+blockNo+", seqNo="+seqNo+", binLen="+binLen);
        //낑겨넣을 위치 계산
        int insPosit = ((blockNo-1)*Define.MAX_SEQ_CNT.getValue()*Define.MAX_DATA_MSG_SIZE.getValue()) + ((seqNo-1)*Define.MAX_DATA_MSG_SIZE.getValue());
        //해당위치로 file offset 이동
        dataFp.seek(insPosit);
        //나머지데이터 옮기기
        byte[] shiftBuf = new byte[Define.MAX_DATA_MSG_SIZE.getValue()*Define.MAX_SEQ_CNT.getValue()];
        int shiftLen = dataFp.read(shiftBuf);
        //file offset 제자리로
        dataFp.seek(insPosit);
        //결번데이터 낑겨넣기
        dataFp.write(dataBuf, 0, binLen);
        //나머지데이터 제자리로
        dataFp.write(shiftBuf, 0, shiftLen);
    }

    //송신
    public boolean sndData(String ipAddr, int port, String sendCd, String recvCd, String infoCd, String filePath, boolean zipYn, EncTp encTp, String maxBps){
        log.debug("[sndData] ipAddr="+ipAddr+", port="+port+", sendCd="+sendCd+", recvCd="+recvCd+", infoCd="+infoCd+", filePath="+filePath+", zipYn="+zipYn+", encTp="+encTp+", maxBps="+maxBps);
        boolean result = false;
        SocketClient sockClnt = null;
        byte[] sndMsg;

        try {
            sockClnt = new SocketClient(ipAddr, port, encTp);
            sockClnt.connect();
            if(encTp != EncTp.NONE) sockClnt.setEnc();

            long fSize = Util.getFileSize(filePath);
            //개시전문전송
            sndMsg = new MsgNego(sendCd, recvCd).getMsgOpenReq();
            log.trace("[sndData]    sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
            sockClnt.writeMsg(sndMsg);
            //응답전문점검
            chkRplyMsg(sockClnt, MsgCode.MSG_OPEN_REP);
            //자료송신요청
            sndMsg = new MsgNego(sendCd, recvCd).getMsgSendReq(infoCd, fSize, zipYn);
            log.trace("[sndData]    sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
            sockClnt.writeMsg(sndMsg);
            //응답전문점검
            chkRplyMsg(sockClnt, MsgCode.MSG_TRANS_REP);
            //파일전송
            upLoadProcess(sockClnt, sendCd, recvCd, infoCd, filePath, zipYn, maxBps);
            //개별종료요청
            sndMsg = new MsgNego(sendCd, recvCd).getMsgPartEndReq(infoCd, fSize);
            log.trace("[sndData]    sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
            sockClnt.writeMsg(sndMsg);
            //응답전문점검
            chkRplyMsg(sockClnt, MsgCode.MSG_PARTEND_REP);
            //전체종료요청
            sndMsg = new MsgNego(sendCd, recvCd).getMsgCloseReq();
            log.trace("[sndData]    sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
            sockClnt.writeMsg(sndMsg);
            //응답전문점검
            chkRplyMsg(sockClnt, MsgCode.MSG_CLOSE_REP);

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        } finally {
            if(sockClnt!=null)  sockClnt.close();
        }

        return result;
    }

    public synchronized void upLoadProcess(SocketClient sockClnt, String sendCd, String recvCd, String infoCd, String filePath, boolean zipYn, String maxBps) throws Exception {
        String sendFile;
        if(zipYn) sendFile  = compressFile(filePath);
        else    sendFile    = filePath;
        log.debug("[upLoadProcess] sendFile=["+sendFile+"]");

        MsgNego msgNego = new MsgNego(sendCd, recvCd);
        RandomAccessFile dataFp = null;
        try {
            dataFp = new RandomAccessFile(sendFile, "r");
            //전송속도제한
            long maxCps = Util.getMaxCps(maxBps);
            long sTime  = TimeUnit.MILLISECONDS.toSeconds(currentTimeMillis());
            long oLen   = 0L;

            byte[] sndMsg;
            byte[] dataBuf = new byte[Define.MAX_DATA_MSG_SIZE.getValue()];
            boolean eofFlag = false;
            int seqNo = 0, blockNo = 1, readLen = 0;
            while(true){
                Arrays.fill(dataBuf, (byte)0x00);
                readLen = dataFp.read(dataBuf);

                if(readLen <= 0)   eofFlag = true;
                if(readLen > 0){
                    seqNo++;
                    if(seqNo > Define.MAX_SEQ_CNT.getValue()){
                        blockNo++;
                        seqNo = 1;
                    }
                    //Data전문 전송
                    sndMsg = msgNego.getMsgSendData(infoCd, MsgCode.MSG_DATA_REQ, blockNo, seqNo, zipYn, readLen, dataBuf);
                    log.trace("[upLoadProcess] sndMsg=["+new String(sndMsg).substring(0, MsgNego.msgSize)+"...]("+sndMsg.length+")["+blockNo+":"+seqNo+"]");
                    sockClnt.writeMsg(sndMsg);
                    oLen = Util.controlSpeed(maxCps, sTime, oLen, sndMsg.length);
                }
                if(seqNo == Define.MAX_SEQ_CNT.getValue() || eofFlag){
                    if(seqNo == Define.MAX_SEQ_CNT.getValue() && eofFlag)   seqNo = 0;
                    chkMissData(sockClnt, dataFp, sendCd, recvCd, infoCd, blockNo, seqNo, zipYn);
                }
                if(eofFlag) break;
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            dataFp.close();
            if(zipYn)   Util.deleteFile(sendFile);
        }
    }

    public void chkMissData(SocketClient sockClnt, RandomAccessFile dataFp, String sendCd, String recvCd, String infoCd, int blockNo, int seqNo, boolean zipYn) throws Exception {
        byte[] sndMsg, rcvMsg, missList;
        MsgNego missRepMsg;
        int missCnt = 0;

        MsgNego msgNego = new MsgNego(sendCd, recvCd);
        for(int i=0; i<Define.MAX_MISS_REQ_RTRY.getValue(); i++){
            if(i>0) Thread.sleep(5000);
            //결번확인요청
            sndMsg = msgNego.getMsgMissReq(infoCd, blockNo, seqNo);
            log.trace("[chkMissData] sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")["+blockNo+":"+seqNo+"]");
            sockClnt.writeMsg(sndMsg);
            //결번확인통보 수신
            rcvMsg = sockClnt.readMsg();
            log.trace("[chkMissData] rcvMsg=["+new String(rcvMsg)+"]("+rcvMsg.length+")");

            missRepMsg = new MsgNego(rcvMsg);
            if(missRepMsg.getRtnCd() != RtnCode.FINE)
                throw new Exception("Abnormal RtnCode:["+missRepMsg.getRtnCd().getCode()+"]");
            else if(missRepMsg.getMsgType() != MsgCode.MSG_MISS_REP)
                throw new Exception("[chkMissData] Abnormal MsgType:["+missRepMsg.getMsgType().getCode()+"]");

            missCnt = missRepMsg.getMissCnt();
            log.debug("[chkMissData] blockNo="+blockNo+", seqNo="+seqNo+", missCnt="+missCnt);
            if(missCnt==0)  break;

            //결번리스트 취함
            missList = new byte[seqNo];
            arraycopy(rcvMsg, MsgNego.msgSize, missList, 0, missList.length);
            log.debug("[chkMissData] missList=["+new String(missList)+"]("+missList.length+")");
            //결번보고
            sendMissData(sockClnt, dataFp, sendCd, recvCd, infoCd, blockNo, seqNo, zipYn, missList);
        }
    }

    public void sendMissData(SocketClient sockClnt, RandomAccessFile dataFp, String sendCd, String recvCd, String infoCd, int blockNo, int seqNo, boolean zipYn, byte[] missList) throws Exception {
        byte[] dataBuf = new byte[Define.MAX_DATA_MSG_SIZE.getValue()], sndMsg;
        long curPosit = dataFp.getFilePointer(), fPosit=0L;
        int readLen = 0;

        MsgNego msgNego = new MsgNego(sendCd, recvCd);
        for(int i=0 ; i<seqNo ; i++){
            if(missList[i]=='0'){
                fPosit = (long) (blockNo - 1) * Define.MAX_SEQ_CNT.getValue() * Define.MAX_DATA_MSG_SIZE.getValue() + (long) i * Define.MAX_DATA_MSG_SIZE.getValue();
                dataFp.seek(fPosit);
                readLen = dataFp.read(dataBuf);
                //결번보고
                sndMsg = msgNego.getMsgSendData(infoCd, MsgCode.MSG_MISS_DATA, blockNo, i+1, zipYn, readLen, dataBuf);
                log.trace("[sendMissData] sndMsg=["+new String(sndMsg).substring(0, MsgNego.msgSize)+"...]("+sndMsg.length+")["+blockNo+":"+(i+1)+"]");
                sockClnt.writeMsg(sndMsg);
            }
        }
        dataFp.seek(curPosit);
    }

    public synchronized void decompressFile(String srcPath, String desPath) throws Exception {
        GZIPInputStream     gzIn = null;
        FileOutputStream    desOut = null;

        File desFile = new File(desPath);
        if(desFile.exists()) {
            if(!desFile.delete())   throw new Exception("Fail delete file["+desPath+"]");
        }

        try {
            gzIn    = new GZIPInputStream(new FileInputStream(srcPath));
            desOut  = new FileOutputStream(desPath);

            byte[] zBuf = new byte[1024*8];
            int len = 0;
            while((len = gzIn.read(zBuf)) != -1) desOut.write(zBuf, 0, len);
            desOut.flush();
            gzIn.close();
            desOut.close();

            File sFile = new File(srcPath);
            if(!sFile.delete()) throw new Exception("Fail delete file["+srcPath+"]");
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            if(gzIn!=null)      gzIn.close();
            if(desOut!=null)    desOut.close();
        }
        log.debug("[decompressFile] "+srcPath+" =decompress=> "+desPath);
    }

    public synchronized String compressFile(String srcPath) throws Exception {
        Random rand = new Random();
        String zipPath = srcPath+"."+rand.nextInt(999999)+ Define.EXP_ZIP.getCode();

        FileInputStream srcIn   = null;
        GZIPOutputStream gzOut  = null;
        try {
            srcIn   = new FileInputStream(srcPath);
            gzOut   = new GZIPOutputStream(new FileOutputStream(zipPath));

            byte[] zBuf = new byte[1024*8];
            int len = 0;
            while((len = srcIn.read(zBuf)) > 0) gzOut.write(zBuf, 0, len);
            gzOut.finish();
        } catch (IOException e) {
            throw new Exception(e);
        } finally {
            if(srcIn!=null) srcIn.close();
            if(gzOut!=null) gzOut.close();
        }
        log.debug("[compressFile] "+srcPath+" =compress=> "+zipPath);

        return zipPath;
    }

    public RtnCode chkRplyMsg(SocketClient sockClnt, MsgCode msgType) throws Exception {
        byte[] rcvMsg = sockClnt.readMsg();
        log.trace("[chkRplyMsg] rcvMsg=["+new String(rcvMsg)+"]("+rcvMsg.length+")");

        MsgNego rplyNegoMsg = new MsgNego(rcvMsg);

        if(rplyNegoMsg.getRtnCd() == RtnCode.NO_DATA)
            log.debug("[chkRplyMsg] NO_DATA");
        else if(rplyNegoMsg.getRtnCd() != RtnCode.FINE)
            throw new Exception("Abnormal RtnCode:["+rplyNegoMsg.getRtnCd().getCode()+"]");
        else if(rplyNegoMsg.getMsgType() != msgType)
            throw new Exception("[chkRplyMsg] Abnormal MsgType:["+rplyNegoMsg.getMsgType().getCode()+"]");

        return rplyNegoMsg.getRtnCd();
    }

    //송신(한번에 여러개)
    public List<DtoFileList> sndDataMulti(String ipAddr, int port, String sendCd, List<DtoFileList> fileLists, boolean zipYn, EncTp encTp, String maxBps, FNmTp fNmTp){
        log.debug("[sndDataMulti] ipAddr="+ipAddr+", port="+port+", sendCd="+sendCd+", fileListCnt="+fileLists.size()+", zipYn="+zipYn+", encTp="+encTp+", maxBps="+maxBps+", fNmTp="+fNmTp.getCode());

        SocketClient sockClnt = null;
        byte[] sndMsg;
        try {
            sockClnt = new SocketClient(ipAddr, port, encTp);
            sockClnt.connect();
            if(encTp != EncTp.NONE) sockClnt.setEnc();
            //개시전문전송
            sndMsg = new MsgNego(sendCd, Define.HYPHEN.getCode()).getMsgOpenReq();
            log.trace("[sndDataMulti]    sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
            sockClnt.writeMsg(sndMsg);
            //응답전문점검
            chkRplyMsg(sockClnt, MsgCode.MSG_OPEN_REP);

            //보낼 파일갯수만큼 반복
            String dInfoCd, dSendCd, dRecvCd;
            for (DtoFileList fileList : fileLists) {
                try{
                    log.debug("[sndDataMulti]("+(fileLists.indexOf(fileList)+1)+"/"+fileLists.size()+") "+fileList);
                    //사용자정의파일명 처리
                    if(fNmTp==FNmTp.KEDUFIN){
                        dInfoCd = fileList.getInfoCd();
                        dSendCd = getHpnCdKEduFine(sockClnt, fileList);
                        dRecvCd = "0"+fileList.getRecvCd();
                    }else{
                        dInfoCd = fileList.getInfoCd();
                        dSendCd = fileList.getSendCd();
                        dRecvCd = fileList.getRecvCd();
                    }
                    long fSize = Util.getFileSize(fileList.getFilePath());
                    //자료송신요청
                    sndMsg = new MsgNego(dSendCd, dRecvCd).getMsgSendReq(dInfoCd, fSize, zipYn);
                    log.trace("[sndDataMulti]    sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
                    sockClnt.writeMsg(sndMsg);
                    //응답전문점검
                    chkRplyMsg(sockClnt, MsgCode.MSG_TRANS_REP);
                    //파일전송
                    upLoadProcess(sockClnt, dSendCd, dRecvCd, dInfoCd, fileList.getFilePath(), zipYn, maxBps);
                    //개별종료요청
                    sndMsg = new MsgNego(dSendCd, dRecvCd).getMsgPartEndReq(dInfoCd, fSize);
                    log.trace("[sndDataMulti]    sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
                    sockClnt.writeMsg(sndMsg);
                    //응답전문점검
                    chkRplyMsg(sockClnt, MsgCode.MSG_PARTEND_REP);

                    fileList.setRetYn(true);
                }catch (Exception e) {
                    log.error(e.toString());
                    continue;
                }
            }
            //전체종료요청
            sndMsg = new MsgNego(sendCd, Define.HYPHEN.getCode()).getMsgCloseReq();
            log.trace("[sndDataMulti]    sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
            sockClnt.writeMsg(sndMsg);
            //응답전문점검
            chkRplyMsg(sockClnt, MsgCode.MSG_CLOSE_REP);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        } finally {
            if(sockClnt!=null)  sockClnt.close();
        }

        return fileLists;
    }

    private String getHpnCdKEduFine(SocketClient sockClnt, DtoFileList fileList) throws Exception {
        String infoCd = fileList.getInfoCd();
        String sendCd = FNmTp.KEDUFIN.getCode();
        String recvCd = "0"+fileList.getRecvCd();
        //사용자정의파일명 조회
        FnmTpKEduFine fnmTpKEduFine = new FnmTpKEduFine(new File(fileList.getFilePath()).getName());
        String fnmInfo = fnmTpKEduFine.getSendCd()+"-"+fnmTpKEduFine.getFacCd();
        byte[] sndMsg = new MsgNego(sendCd, recvCd).getMsgFindFnmReq(infoCd, MsgCode.MSG_TP_SND_REQ, fnmInfo, FNmTp.KEDUFIN);
        log.trace("[getHpnCdKEduFine] sndMsg=["+new String(sndMsg)+"]("+sndMsg.length+")");
        sockClnt.writeMsg(sndMsg);

        byte[] rcvMsg = sockClnt.readMsg();
        log.trace("[getHpnCdKEduFine] rcvMsg=["+new String(rcvMsg)+"]("+rcvMsg.length+")");

        MsgNego fnmRepMsg = new MsgNego(rcvMsg);

        //log.debug("[getHpnCdKEduFine] RtnCode="+fnmRepMsg.getRtnCd());

        if(fnmRepMsg.getRtnCd() != RtnCode.FINE)
            throw new Exception("Abnormal RtnCode:["+fnmRepMsg.getRtnCd().getCode()+"]");
        else if(fnmRepMsg.getMsgType() != MsgCode.MSG_FINDFNM_REP)
            throw new Exception("[getHpnCdKEduFine] Abnormal MsgType:["+fnmRepMsg.getMsgType().getCode()+"]");

        String hPnCd = fnmRepMsg.getSendCd();
        log.debug("[getHpnCdKEduFine] fnmInfo="+fnmInfo+" => hPnCd="+hPnCd);

        return hPnCd;
    }

}
