package com.hyphen.fbnk.bbnk;

import com.hyphen.fbnk.bbnk.dto.*;
import com.hyphen.fbnk.bbnk.logging.Log;
import com.hyphen.fbnk.bbnk.logging.LogFactory;
import com.hyphen.fbnk.bbnk.msg.FfmRegCom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProcBbdata {
    private static final Log log = LogFactory.getLog(ProcBbdata.class);
	private final String TBL_C01 = "COCA_APPR";
	private final String TBL_C02 = "COCA_ACQU";
	private final String TBL_C03 = "COCA_BILL";
	private final String TBL_C04 = "COCA_INFO";
	private final String TBL_C05 = "COCA_SETT";
	private final String TBL_C06 = "COCA_LIMI";
	private final String TBL_SRHST = "HYPHEN_BBNK_HST";

	public boolean srHst2DB(DtoDBSRHst srHst, String db_driver, String db_url, String db_user, String db_pass){
		boolean result = true;
		Connection db_con;
		PreparedStatement pst_hst = null;

		//log.debug("[srHst2DB] "+srHst);
		//log.debug("[srHst2DB] db_driver=["+db_driver+"], db_url=["+db_url+"], db_user=["+db_user+"], db_pass=["+db_pass+"]");
		db_con = Connect2DB(db_driver, db_url, db_user, db_pass);
		if(db_con == null)	return false;
		try {
			db_con.setAutoCommit(true);
			String Qry = "INSERT INTO " + TBL_SRHST + " (SEND_DATE, SEND_TIME, BT_INFO_CODE, SEND_CODE, RECV_CODE, SEQ_NUMB," +
					"FILE_NAME, SR_TP, SUCCESS_FLAG, ADD_INFO1, ADD_INFO2, ADD_INFO3, ADD_INFO4, ADD_INFO5) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			pst_hst = db_con.prepareStatement(Qry);
			pst_hst.setString(1, srHst.getSEND_DATE());
			pst_hst.setString(2, srHst.getSEND_TIME());
			pst_hst.setString(3, srHst.getBT_INFO_CODE());
			pst_hst.setString(4, srHst.getSEND_CODE());
			pst_hst.setString(5, srHst.getRECV_CODE());
			pst_hst.setString(6, srHst.getSEQ_NUMB());
			pst_hst.setString(7, srHst.getFILE_NAME());
			pst_hst.setString(8, srHst.getSR_TP());
			pst_hst.setString(9, srHst.getSUCCESS_FLAG());
			pst_hst.setString(10, srHst.getADD_INFO1());
			pst_hst.setString(11, srHst.getADD_INFO2());
			pst_hst.setString(12, srHst.getADD_INFO3());
			pst_hst.setLong(13, srHst.getADD_INFO4());
			pst_hst.setLong(14, srHst.getADD_INFO5());
			if(pst_hst.executeUpdate() != 1) log.error("[srHst2DB] INSERT WORK FAIL ~!!");
		} catch (SQLException e) {
			log.error("[srHst2DB] "+ e);
			result = false;
		} finally {
			if(pst_hst != null)	try{pst_hst.close();} catch (SQLException ignored){}
		}

		try{db_con.close();} catch (SQLException ignored){}
		if(result)	log.debug("[srHst2DB](SUCCESS)");
		else        log.debug("[srHst2DB](FAIL)");

		return result;
	}

	public boolean srHst2DB(DtoFileList dtoFileList, String srTp, String db_driver, String db_url, String db_user, String db_pass){
		boolean result = true;
		Connection db_con;
		PreparedStatement pst_hst = null;

		//log.debug("[srHst2DB] db_driver=["+db_driver+"], db_url=["+db_url+"], db_user=["+db_user+"], db_pass=["+db_pass+"]");
		db_con = Connect2DB(db_driver, db_url, db_user, db_pass);
		if(db_con == null)	return false;
		try {
			db_con.setAutoCommit(true);
			String Qry = "INSERT INTO " + TBL_SRHST + " (SEND_DATE, SEND_TIME, BT_INFO_CODE, SEND_CODE, RECV_CODE, SEQ_NUMB," +
					"FILE_NAME, SR_TP, SUCCESS_FLAG, ADD_INFO1, ADD_INFO2, ADD_INFO3, ADD_INFO4, ADD_INFO5) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			pst_hst = db_con.prepareStatement(Qry);
			pst_hst.setString(1, Util.getCurDtTm().substring(0, 8));
			pst_hst.setString(2, Util.getCurDtTm().substring(8));
			pst_hst.setString(3, dtoFileList.getInfoCd());
			pst_hst.setString(4, dtoFileList.getSendCd());
			pst_hst.setString(5, dtoFileList.getRecvCd());
			pst_hst.setString(6, dtoFileList.getSeqNo());
			pst_hst.setString(7, new File(dtoFileList.getFilePath()).getName());
			pst_hst.setString(8, srTp);
			pst_hst.setString(9, dtoFileList.isRetYn() ? "Y" : "N");
			pst_hst.setString(10, "");
			pst_hst.setString(11, "");
			pst_hst.setString(12, "");
			pst_hst.setLong(13, 0L);
			pst_hst.setLong(14, 0L);
			if(pst_hst.executeUpdate() != 1) log.error("[srHst2DB] INSERT WORK FAIL ~!!");
		} catch (SQLException e) {
			log.error("[srHst2DB] "+ e);
			result = false;
		} finally {
			if(pst_hst != null)	try{pst_hst.close();} catch (SQLException ignored){}
		}
		try{db_con.close();} catch (SQLException ignored){}
		if(result)	log.debug("[srHst2DB](SUCCESS)");
		else        log.debug("[srHst2DB](FAIL)");

		return result;
	}

	public boolean srHst2DB(List<DtoFileList> dtoFileLists, String srTp, String db_driver, String db_url, String db_user, String db_pass){
		boolean result = true;
		boolean commit_flag = true;
		Connection db_con;
		PreparedStatement pst_hst = null;

		//log.debug("[srHst2DB] db_driver=["+db_driver+"], db_url=["+db_url+"], db_user=["+db_user+"], db_pass=["+db_pass+"]");
		db_con = Connect2DB(db_driver, db_url, db_user, db_pass);
		if(db_con == null)	return false;
		try {
			db_con.setAutoCommit(false);
			String Qry = "INSERT INTO " + TBL_SRHST + " (SEND_DATE, SEND_TIME, BT_INFO_CODE, SEND_CODE, RECV_CODE, SEQ_NUMB," +
					"FILE_NAME, SR_TP, SUCCESS_FLAG, ADD_INFO1, ADD_INFO2, ADD_INFO3, ADD_INFO4, ADD_INFO5) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			pst_hst = db_con.prepareStatement(Qry);
			for(DtoFileList dtoFileList : dtoFileLists){
				pst_hst.setString(1, Util.getCurDtTm().substring(0, 8));
				pst_hst.setString(2, Util.getCurDtTm().substring(8));
				pst_hst.setString(3, dtoFileList.getInfoCd());
				pst_hst.setString(4, dtoFileList.getSendCd());
				pst_hst.setString(5, dtoFileList.getRecvCd());
				pst_hst.setString(6, dtoFileList.getSeqNo());
				pst_hst.setString(7, new File(dtoFileList.getFilePath()).getName());
				pst_hst.setString(8, srTp);
				pst_hst.setString(9, dtoFileList.isRetYn() ? "Y" : "N");
				pst_hst.setString(10, "");
				pst_hst.setString(11, "");
				pst_hst.setString(12, "");
				pst_hst.setLong(13, 0L);
				pst_hst.setLong(14, 0L);
				if(pst_hst.executeUpdate() != 1){
					log.error("[srHst2DB] INSERT WORK FAIL ~!!");
					commit_flag = false;
					break;
				}
			}//loop end.
			if(commit_flag)	db_con.commit();
			else {
				db_con.rollback();
				result = false;
			}
		} catch (SQLException e) {
			log.error("[srHst2DB] "+ e);
			try{db_con.rollback();} catch (SQLException ignored){}
			result = false;
		} finally {
			if(pst_hst != null)	try{pst_hst.close();} catch (SQLException ignored){}
		}
		try{db_con.close();} catch (SQLException ignored){}
		if(result)	log.debug("[srHst2DB](SUCCESS)");
		else        log.debug("[srHst2DB](FAIL)");

		return result;
	}

	public boolean corpCard2DB(String file_path, String info_cd, String db_driver, String db_url, String db_user, String db_pass) {
		boolean result = true;
		Connection db_con;
        log.debug("[CorpCard2DB] file_path=["+file_path+"], info_cd=["+info_cd+"], db_driver=["+db_driver+"], db_url=["+db_url+"], db_user=["+db_user+"], db_pass=["+db_pass+"]");

		db_con = Connect2DB(db_driver, db_url, db_user, db_pass);
		if(db_con == null)	return false;

        switch (info_cd) {
            case "C01":
                if (!Approval2DB(file_path, db_con)) result = false;
                break;
            case "C02":
                if (!Acquire2DB(file_path, db_con)) result = false;
                break;
            case "C03":
                if (!Bill2DB(file_path, db_con)) result = false;
                break;
            case "C04":
                if (!CardInfo2DB(file_path, db_con)) result = false;
                break;
            case "C05":
                if (!Settlement2DB(file_path, db_con)) result = false;
                break;
            case "C06":
                if (!CardLimit2DB(file_path, db_con)) result = false;
                break;
            default:
                log.error("Undefined info_cd : " + info_cd);
                result = false;
                break;
        }
        try{db_con.close();} catch (SQLException ignored){}

		if(result)	log.debug("[CorpCard2DB](SUCCESS) file_path=["+file_path+"], info_cd=["+info_cd+"]");
		else        log.debug("[CorpCard2DB](FAIL) file_path=["+file_path+"], info_cd=["+info_cd+"]");

		return result;
	}

	private boolean Approval2DB(String file_path, Connection db_con) {
		boolean result = true;
		boolean commit_flag = true;
		PreparedStatement pst_c01 = null;
		DtoDRecC01 data_rec = null;
		FileInputStream fis = null;
		byte[] dataBuf = null;

		try {
			db_con.setAutoCommit(false);
            String Qry = "INSERT INTO " + TBL_C01 + " (DATA_CODE, COMPANY_ID, SEND_DATE, SEQ_NO, READ_FLAG, Class, CardNo, ApprNo, " +
                    "TransDate, TransTime, CardType1, CardType2, CurrCode, ApprAmt, VAT, Tips, ApprTot, ApprExch, USDApprTot, InstType, InstMonth, " +
                    "Abroad, SlipNo, TerminalNo, Purch, Acqulssuer, MerchBizNo, MerchNo, MerchName, Master, MerchTel, MerchZipcode, MerchAddr1, " +
                    "MerchAddr2, MerchStatus, MCCName, MCCCode, PartApprCancYN, ServTypeYN, CollNo, TaxType, MerchCessDate, OrginTransDate, " +
                    "OrginApprNo, OrginTransTime, OrginMerchName, OrginMerchBizNo, ntsdate, CardIni, insert_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			pst_c01 = db_con.prepareStatement(Qry);
			
			int i_cnt = 0;
			fis = new FileInputStream(file_path);
			while(true) {
				i_cnt++;
				dataBuf = Util.readLineByte(fis);
				if(dataBuf.length==0)	break;
				if(!new String(dataBuf, 0, 1).equals("D"))	continue;
				
				data_rec = new DtoDRecC01(dataBuf);
				if(!data_rec.isNormal()) {
					log.error("[Approval2DB] dataBuf("+i_cnt+")=["+new String(dataBuf)+"]");
					log.error("[Approval2DB] "+data_rec.getAbnormalMsg());
					commit_flag = false;
					break;
				}
				log.trace("[Approval2DB] data_code=["+data_rec.getData_code()+"], company_id=["+data_rec.getCompany_id()+"], send_dt=["+data_rec.getSend_dt().trim()+
						"], seq_no=["+data_rec.getSeq_no()+"], ApprNo=["+data_rec.getApprNo()+"], MerchName=["+data_rec.getMerchName()+"]");

				pst_c01.setString(1, data_rec.getData_code());				
				pst_c01.setString(2, data_rec.getCompany_id());
				pst_c01.setString(3, data_rec.getSend_dt());
				pst_c01.setString(4, data_rec.getSeq_no());
				pst_c01.setString(5, data_rec.getRead_flag());
				pst_c01.setString(6, data_rec.getApClass());
				pst_c01.setString(7, data_rec.getCardNo());
				pst_c01.setString(8, data_rec.getApprNo());
				pst_c01.setString(9, data_rec.getTransDate());
				pst_c01.setString(10, data_rec.getTransTime());
				pst_c01.setString(11, data_rec.getCardType1());
				pst_c01.setString(12, data_rec.getCardType2());
				pst_c01.setString(13, data_rec.getCurrCode());
				pst_c01.setDouble(14, data_rec.getApprAmt());
				pst_c01.setDouble(15, data_rec.getVAT());
				pst_c01.setDouble(16, data_rec.getTips());
				pst_c01.setDouble(17, data_rec.getApprTot());
				pst_c01.setDouble(18, data_rec.getApprExch());
				pst_c01.setDouble(19, data_rec.getUSDApprTot());
				pst_c01.setString(20, data_rec.getInstType());
				pst_c01.setInt(21, data_rec.getInstMonth());
				pst_c01.setString(22, data_rec.getAbroad());
				pst_c01.setString(23, data_rec.getSlipNo());
				pst_c01.setString(24, data_rec.getTerminalNo());
				pst_c01.setString(25, data_rec.getPurch());
				pst_c01.setString(26, data_rec.getAcqulssuer());
				pst_c01.setString(27, data_rec.getMerchBizNo());
				pst_c01.setString(28, data_rec.getMerchNo());				
				pst_c01.setString(29, data_rec.getMerchName());
				pst_c01.setString(30, data_rec.getMaster());
				pst_c01.setString(31, data_rec.getMerchTel());
				pst_c01.setString(32, data_rec.getMerchZipCode());
				pst_c01.setString(33, data_rec.getMerchAddr1());
				pst_c01.setString(34, data_rec.getMerchAddr2());
				pst_c01.setString(35, data_rec.getMerchStatus());
				pst_c01.setString(36, data_rec.getMCCName());
				pst_c01.setString(37, data_rec.getMCCCode());				
				pst_c01.setString(38, data_rec.getPartApprCancYN());
				pst_c01.setString(39, data_rec.getServTypeYN());
				pst_c01.setString(40, data_rec.getCollNo());
				pst_c01.setString(41, data_rec.getTaxType());
				pst_c01.setString(42, data_rec.getMerchCessDate());
				pst_c01.setString(43, data_rec.getOrginTransDate());
				pst_c01.setString(44, data_rec.getOrginApprNo());
				pst_c01.setString(45, data_rec.getOrginTransTime());
				pst_c01.setString(46, data_rec.getOrginMerchName());
				pst_c01.setString(47, data_rec.getOrginMerchBizNo());
				pst_c01.setString(48, data_rec.getNtsdate());
				pst_c01.setString(49, data_rec.getCardIni());				
				pst_c01.setString(50, getCurTm());
	
				if(pst_c01.executeUpdate() != 1) {
					log.error("[Approval2DB] INSERT WORK FAIL ~!!");
					commit_flag = false;
					break;
				}
				if(i_cnt > 1000000)	break;
			}/*while end.*/
			
			if(commit_flag)	db_con.commit();
			else {
				db_con.rollback();
				result = false;
			}
		} catch (IOException | SQLException e) {
			log.error("[CorpCard2DB] "+ e);
			try{db_con.rollback();} catch (SQLException ignored){}
			result = false;
		} finally {
			if(pst_c01 != null)	try{pst_c01.close();} catch (SQLException ignored){}
			if(fis != null)	try{fis.close();} catch (IOException ignored){}
		}

		return result;
	}

	private boolean Acquire2DB(String file_path, Connection db_con) {
		boolean result = true;
		boolean commit_flag = true;
		PreparedStatement pst_c02 = null;
		DtoDRecC02 data_rec = null;
		FileInputStream fis = null;
		byte[] dataBuf = null;

		try {
			db_con.setAutoCommit(false);
			String Qry = "INSERT INTO " + TBL_C02 + " (DATA_CODE, COMPANY_ID, SEND_DATE, SEQ_NO, READ_FLAG, Class, CardNo, ApprNo, " +
					"CollNo, SlipNo, ApprDate, PurchDate, PurchTime, CurrCode, ApprTot, AcquTot, AcquExch, USDAcquTot, ConvFee, PurchTot, InstType, " +
					"SettDate, Abroad, PurchYN, MerchBizNo, MerchNo, MerchName, Master, MerchTel, MerchZipcode, MerchAddr1, MerchAddr2, MccName, " +
					"MccCode, PartAcquCancYN, NoneApprYN, ServTypeYN, OrginCollNo, DiscAmt, CurrAcquTot, AcquFee, ApprAmt, VAT, Tips, " +
					"OriginMerchName, OriginMerchBizNo, taxtype, MerchCessDate, ntsdate, CardIni, insert_time) " +
					"VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?) ";
			pst_c02 = db_con.prepareStatement(Qry);
			
			int i_cnt = 0;
			fis = new FileInputStream(file_path);
			while(true) {
				i_cnt++;
				dataBuf = Util.readLineByte(fis);
				if(dataBuf.length==0)	break;
				if(!new String(dataBuf, 0, 1).equals("D"))	continue;

				data_rec = new DtoDRecC02(dataBuf);
				if(!data_rec.isNormal()) {
					log.error("[Acquire2DB] dataBuf("+i_cnt+")=["+new String(dataBuf)+"]");
					log.error("[Acquire2DB] "+data_rec.getAbnormalMsg());
					commit_flag = false;
					break;					
				}
				log.trace("[Acquire2DB] data_code=["+data_rec.getDATA_CODE()+"], company_id=["+data_rec.getCOMPANY_ID()+"], send_dt=["+data_rec.getSEND_DT()+
						"], seq_no=["+data_rec.getSEQ_NO()+"], ApprNo=["+data_rec.getApprNo()+"], MerchName=["+data_rec.getMerchName()+"]");
				
				pst_c02.setString(1, data_rec.getDATA_CODE());
				pst_c02.setString(2, data_rec.getCOMPANY_ID());
				pst_c02.setString(3, data_rec.getSEND_DT());
				pst_c02.setString(4, data_rec.getSEQ_NO());
				pst_c02.setString(5, data_rec.getREAD_FLAG());
				pst_c02.setString(6, data_rec.getApClass());
				pst_c02.setString(7, data_rec.getCardNo());
				pst_c02.setString(8, data_rec.getApprNo());
				pst_c02.setString(9, data_rec.getCollNo());
				pst_c02.setString(10, data_rec.getSlipNo());
				pst_c02.setString(11, data_rec.getApprDate());
				pst_c02.setString(12, data_rec.getPurchDate());
				pst_c02.setString(13, data_rec.getPurchTime());
				pst_c02.setString(14, data_rec.getCurrCode());
				pst_c02.setDouble(15, data_rec.getApprTot());
				pst_c02.setDouble(16, data_rec.getAcquTot());
				pst_c02.setDouble(17, data_rec.getAcquExch());
				pst_c02.setDouble(18, data_rec.getUSDAcquTot());
				pst_c02.setDouble(19, data_rec.getConvFee());
				pst_c02.setDouble(20, data_rec.getPurchTot());
				pst_c02.setString(21, data_rec.getInstType());
				pst_c02.setString(22, data_rec.getSettDate());
				pst_c02.setString(23, data_rec.getAbroad());
				pst_c02.setString(24, data_rec.getPurchYN());
				pst_c02.setString(25, data_rec.getMerchBizNo());
				pst_c02.setString(26, data_rec.getMerchNo());
				pst_c02.setString(27, data_rec.getMerchName());
				pst_c02.setString(28, data_rec.getMaster());
				pst_c02.setString(29, data_rec.getMerchTel());
				pst_c02.setString(30, data_rec.getMerchZipcode());
				pst_c02.setString(31, data_rec.getMerchAddr1());
				pst_c02.setString(32, data_rec.getMerchAddr2());
				pst_c02.setString(33, data_rec.getMccName());
				pst_c02.setString(34, data_rec.getMccCode());
				pst_c02.setString(35, data_rec.getPartAcquCancYN());
				pst_c02.setString(36, data_rec.getNoneApprYN());
				pst_c02.setString(37, data_rec.getServTypeYN());
				pst_c02.setString(38, data_rec.getOrginCollNo());
				pst_c02.setDouble(39, data_rec.getDiscAmt());
				pst_c02.setDouble(40, data_rec.getCurrAcquTot());
				pst_c02.setDouble(41, data_rec.getAcquFee());
				pst_c02.setDouble(42, data_rec.getApprAmt());
				pst_c02.setDouble(43, data_rec.getVAT());
				pst_c02.setDouble(44, data_rec.getTips());
				pst_c02.setString(45, data_rec.getOriginMerchName());
				pst_c02.setString(46, data_rec.getOriginMerchBizNo());
				pst_c02.setString(47, data_rec.getTaxtype());
				pst_c02.setString(48, data_rec.getMerchCessDate());
				pst_c02.setString(49, data_rec.getNtsdate());
				pst_c02.setString(50, data_rec.getCardIni());
				pst_c02.setString(51, getCurTm());
				
				if(pst_c02.executeUpdate() != 1) {
					log.error("[Acquire2DB] INSERT WORK FAIL ~!!");
					commit_flag = false;
					break;
				}
				
				if(i_cnt > 1000000)	break;
			}/*while end.*/
			
			if(commit_flag)	db_con.commit();
			else {
				db_con.rollback();
				result = false;
			}
		} catch (IOException e) {
			log.error("[CorpCard2DB] "+e);
			try{db_con.rollback();} catch (SQLException ignored){}
			result = false;
		} catch(SQLException e) {
			log.error("[CorpCard2DB] "+e);
			try{db_con.rollback();} catch (SQLException ignored){}
			result = false;
		} finally {
			if(pst_c02 != null)	try{pst_c02.close();} catch (SQLException ignored){}
			if(fis != null)	try{fis.close();} catch (IOException ignored){}
		}
		
		return result;
	}
	
	private boolean Bill2DB(String file_path, Connection db_con)
	{
		boolean result = true;
		boolean commit_flag = true;
		PreparedStatement pst_c03 = null;
		DtoDRecC03 data_rec = null;
		FileInputStream fis = null;
		byte[] dataBuf = null;

		try
		{
			db_con.setAutoCommit(false);
			String Qry = "INSERT INTO " + TBL_C03 + " (DATA_CODE, COMPANY_ID, SEND_DATE, SEQ_NO, READ_FLAG, Class, BilType, CorpBizNo, " +
					"CorpNo, EstbNo, CardNo, ApprNo, SlipNo, CollNo, OrgnApprDate, BilWorkDate, BilAmt, BilFee, MembshipFee, AcquExch, USDAcquTot, ConvFee, " +
					"BilTot, InstType, InstMonth, RestInstMonth, SettDate, MerchBizNo, MerchName, ServTypeYN, DiscAmt, EstbName, UserNo, UserName, " +
					"OrgnApprTot, OrginTransTime, OriginMerchName, OriginMerchBizNo, taxtype, MerchCessDate, ntsdate, CardIni, insert_time) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			pst_c03 = db_con.prepareStatement(Qry);
			
			int i_cnt = 0;
			fis = new FileInputStream(file_path);
			while(true) {
				i_cnt++;
				dataBuf = Util.readLineByte(fis);
				if(dataBuf.length==0)	break;
				if(!new String(dataBuf, 0, 1).equals("D"))	continue;
				
				data_rec = new DtoDRecC03(dataBuf);
				if(!data_rec.isNormal()) {
					log.error("[Bill2DB] data_buf("+i_cnt+")=["+new String(dataBuf)+"]");
					log.error("[Bill2DB] "+data_rec.getAbnormalMsg());
					commit_flag = false;
					break;					
				}
				log.trace("[Bill2DB] data_code=["+data_rec.getDATA_CODE()+"], company_id=["+data_rec.getCOMPANY_ID()+"], send_dt=["+data_rec.getSEND_DT()+
						"], seq_no=["+data_rec.getSEQ_NO()+"], ApprNo=["+data_rec.getApprNo()+"], MerchName=["+data_rec.getMerchName()+"]");
				
				pst_c03.setString(1, data_rec.getDATA_CODE());
				pst_c03.setString(2, data_rec.getCOMPANY_ID());
				pst_c03.setString(3, data_rec.getSEND_DT());
				pst_c03.setString(4, data_rec.getSEQ_NO());
				pst_c03.setString(5, data_rec.getREAD_FLAG());
				pst_c03.setString(6, data_rec.getApClass());
				pst_c03.setString(7, data_rec.getBilType());
				pst_c03.setString(8, data_rec.getCorpBizNo());
				pst_c03.setString(9, data_rec.getCorpNo());
				pst_c03.setString(10, data_rec.getEstbNo());
				pst_c03.setString(11, data_rec.getCardNo());
				pst_c03.setString(12, data_rec.getApprNo());
				pst_c03.setString(13, data_rec.getSlipNo());
				pst_c03.setString(14, data_rec.getCollNo());
				pst_c03.setString(15, data_rec.getOrgnApprDate());
				pst_c03.setString(16, data_rec.getBilWorkDate());				
				pst_c03.setDouble(17, data_rec.getBilAmt());
				pst_c03.setDouble(18, data_rec.getBilFee());
				pst_c03.setDouble(19, data_rec.getMembshipFee());
				pst_c03.setDouble(20, data_rec.getAcquExch());
				pst_c03.setDouble(21, data_rec.getUSDAcquTot());
				pst_c03.setDouble(22, data_rec.getConvFee());
				pst_c03.setDouble(23, data_rec.getBilTot());
				pst_c03.setString(24, data_rec.getInstType());
				pst_c03.setString(25, data_rec.getInstMonth());
				pst_c03.setString(26, data_rec.getRestInstMonth());
				pst_c03.setString(27, data_rec.getSettDate());
				pst_c03.setString(28, data_rec.getMerchBizNo());
				pst_c03.setString(29, data_rec.getMerchName());
				pst_c03.setString(30, data_rec.getServTypeYN());
				pst_c03.setDouble(31, data_rec.getDiscAmt());
				pst_c03.setString(32, data_rec.getEstbName());
				pst_c03.setString(33, data_rec.getUserNo());
				pst_c03.setString(34, data_rec.getUserName());
				pst_c03.setDouble(35, data_rec.getOrgnApprTot());
				pst_c03.setString(36, data_rec.getOrginTransTime());
				pst_c03.setString(37, data_rec.getOriginMerchName());
				pst_c03.setString(38, data_rec.getOriginMerchBizNo());
				pst_c03.setString(39, data_rec.getTaxtype());
				pst_c03.setString(40, data_rec.getMerchCessDate());
				pst_c03.setString(41, data_rec.getNtsdate());
				pst_c03.setString(42, data_rec.getCardIni());
				pst_c03.setString(43, getCurTm());
				
				if(pst_c03.executeUpdate() != 1) {
					log.error("[Bill2DB] INSERT WORK FAIL ~!!");
					commit_flag = false;
					break;
				}
				
				if(i_cnt > 1000000)	break;
			}/*while end.*/
			
			if(commit_flag)	db_con.commit();
			else {
				db_con.rollback();
				result = false;
			}
		} catch (IOException e) {
			log.error("[CorpCard2DB] "+e);
			try{db_con.rollback();} catch (SQLException ignored){}
			result = false;
		} catch(SQLException e) {
			log.error("[CorpCard2DB] "+e);
			try{db_con.rollback();} catch (SQLException ignored){}
			result = false;
		} finally {
			if(pst_c03 != null)	try{pst_c03.close();} catch (SQLException ignored){}
			if(fis != null)	try{fis.close();} catch (IOException ignored){}
		}

		return result;
	}

	private boolean CardInfo2DB(String file_path, Connection db_con) {
		boolean result = true;
		boolean commit_flag = true;
		PreparedStatement pst_c04 = null;
		DtoDRecC04 data_rec = null;
		FileInputStream fis = null;
		byte[] dataBuf = null;
	
		try {
			db_con.setAutoCommit(false);
			String Qry = "INSERT INTO " + TBL_C04 + " (DATA_CODE, COMPANY_ID, SEND_DATE, SEQ_NO, READ_FLAG, Class, CorpName, CorpBizNo, " +
					"CorpNo, EstbNo, CustNo, CardBrand, LocalBrand, CardType1, CardType2, CardType3, CardType4, CoopCode, CoopName, CardNo, KorName, " +
					"EngName, RegisNo, Validity, PostCode, PostName, MembZipcode, MembAddr1, MembAddr2, SettType, SettDate, SettBankCode, SettAcctNo, " +
					"MembTel, NoticeType, IssueDate, TmntDate, PauseDate, BeforeCard, OrgnName, OrgnCode, EstbName, UserNo, UserName, CardIni, insert_time) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			pst_c04 = db_con.prepareStatement(Qry);

			int i_cnt = 0;
			fis = new FileInputStream(file_path);
			while(true) {
				i_cnt++;
				dataBuf = Util.readLineByte(fis);
				if(dataBuf.length==0)	break;
				if(!new String(dataBuf, 0, 1).equals("D"))	continue;
				
				data_rec = new DtoDRecC04(dataBuf);
				if(!data_rec.isNormal())
				{
					log.error("[CardInfo2DB] dataBuf("+i_cnt+")=["+new String(dataBuf)+"]");
					log.error("[CardInfo2DB] "+data_rec.getAbnormalMsg());
					commit_flag = false;
					break;					
				}
				log.trace("[CardInfo2DB] data_code=["+data_rec.getDATA_CODE()+"], company_id=["+data_rec.getCOMPANY_ID()+"], send_dt=["+data_rec.getSEND_DT()+"], seq_no=["+data_rec.getSEQ_NO()+"]");

				pst_c04.setString(1, data_rec.getDATA_CODE());
				pst_c04.setString(2, data_rec.getCOMPANY_ID());
				pst_c04.setString(3, data_rec.getSEND_DT());
				pst_c04.setString(4, data_rec.getSEQ_NO());
				pst_c04.setString(5, data_rec.getREAD_FLAG());
				pst_c04.setString(6, data_rec.getApClass());
				pst_c04.setString(7, data_rec.getCorpName());
				pst_c04.setString(8, data_rec.getCorpBizNo());
				pst_c04.setString(9, data_rec.getCorpNo());
				pst_c04.setString(10, data_rec.getEstbNo());
				pst_c04.setString(11, data_rec.getCustNo());
				pst_c04.setString(12, data_rec.getCardBrand());
				pst_c04.setString(13, data_rec.getLocalBrand());
				pst_c04.setString(14, data_rec.getCardType1());
				pst_c04.setString(15, data_rec.getCardType2());
				pst_c04.setString(16, data_rec.getCardType3());
				pst_c04.setString(17, data_rec.getCardType4());
				pst_c04.setString(18, data_rec.getCoopCode());
				pst_c04.setString(19, data_rec.getCoopName());
				pst_c04.setString(20, data_rec.getCardNo());
				pst_c04.setString(21, data_rec.getKorName());
				pst_c04.setString(22, data_rec.getEngName());
				pst_c04.setString(23, data_rec.getRegisNo());
				pst_c04.setString(24, data_rec.getValidity());
				pst_c04.setString(25, data_rec.getPostCode());
				pst_c04.setString(26, data_rec.getPostName());
				pst_c04.setString(27, data_rec.getMembZipcode());
				pst_c04.setString(28, data_rec.getMembAddr1());
				pst_c04.setString(29, data_rec.getMembAddr2());
				pst_c04.setString(30, data_rec.getSettType());
				pst_c04.setString(31, data_rec.getSettDate());
				pst_c04.setString(32, data_rec.getSettBankCode());
				pst_c04.setString(33, data_rec.getSettAcctNo());
				pst_c04.setString(34, data_rec.getMembTel());
				pst_c04.setString(35, data_rec.getNoticeType());
				pst_c04.setString(36, data_rec.getIssueDate());
				pst_c04.setString(37, data_rec.getTmntDate());
				pst_c04.setString(38, data_rec.getPauseDate());
				pst_c04.setString(39, data_rec.getBeforeCard());
				pst_c04.setString(40, data_rec.getOrgnName());
				pst_c04.setString(41, data_rec.getOrgnCode());
				pst_c04.setString(42, data_rec.getEstbName());
				pst_c04.setString(43, data_rec.getUserNo());
				pst_c04.setString(44, data_rec.getUserName());
				pst_c04.setString(45, data_rec.getCardIni());
				pst_c04.setString(46, getCurTm());

				if(pst_c04.executeUpdate() != 1) {
					log.error("[CardInfo2DB] INSERT WORK FAIL ~!!");
					commit_flag = false;
					break;
				}
				
				if(i_cnt > 1000000)	break;
			}/*while end.*/

			if(commit_flag)	db_con.commit();
			else {
				db_con.rollback();
				result = false;
			}
		} catch (IOException e) {
			log.error("[CardInfo2DB] "+e);
			try{db_con.rollback();} catch (SQLException ignored){}
			result = false;
		} catch(SQLException e) {
			log.error("[CorpCard2DB] "+e);
			try{db_con.rollback();} catch (SQLException ignored){}
			result = false;
		} finally {
			if(pst_c04 != null)	try{pst_c04.close();} catch (SQLException ignored){}
			if(fis != null)	try{fis.close();} catch (IOException ignored){}
		}
		
		return result;
	}
	
	private boolean Settlement2DB(String file_path, Connection db_con) {
		boolean result = true;
		boolean commit_flag = true;
		PreparedStatement pst_c05 = null;
		DtoDRecC05 data_rec = null;
		FileInputStream fis = null;
		byte[] dataBuf = null;

		try {
			db_con.setAutoCommit(false);
			String Qry = "INSERT INTO " + TBL_C05 + " (DATA_CODE, COMPANY_ID, SEND_DATE, SEQ_NO, READ_FLAG, Class, " +
					"SettCancYN, SettSeqNo, CorpBizNo, CorpNo, EstbNo, CardNo, CancSettDate, SettDate, SettTime, ReceName, SettBankCode, SettAcctNo, " +
					"SettTot, SettAmt, SettFee, SettInterest, DelayTot, SettSumr, YearFee, ShampPaymt, RefundAmt, BalanceAmt, CardIni, insert_time) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			pst_c05 = db_con.prepareStatement(Qry);
		
			int i_cnt = 0;
			fis = new FileInputStream(file_path);
			while(true) {
				i_cnt++;
				dataBuf = Util.readLineByte(fis);
				if(dataBuf.length==0)	break;
				if(!new String(dataBuf, 0, 1).equals("D"))	continue;
				
				data_rec = new DtoDRecC05(dataBuf);
				if(!data_rec.isNormal()) {
					log.error("[Settlement2DB] dataBuf("+i_cnt+")=["+new String(dataBuf)+"]");
					log.error("[Settlement2DB] "+data_rec.getAbnormalMsg());
					commit_flag = false;
					break;					
				}
				log.trace("[Settlement2DB] data_code=["+data_rec.getDATA_CODE()+"], company_id=["+data_rec.getCOMPANY_ID()+"], send_dt=["+data_rec.getSEND_DATE()+"], seq_no=["+data_rec.getSEQ_NO()+"]");
				
				pst_c05.setString(1, data_rec.getDATA_CODE());
				pst_c05.setString(2, data_rec.getCOMPANY_ID());
				pst_c05.setString(3, data_rec.getSEND_DATE());
				pst_c05.setString(4, data_rec.getSEQ_NO());
				pst_c05.setString(5, data_rec.getREAD_FLAG());
				pst_c05.setString(6, data_rec.getApClass());
				pst_c05.setString(7, data_rec.getSettCancYN());
				pst_c05.setString(8, data_rec.getSettSeqNo());
				pst_c05.setString(9, data_rec.getCorpBizNo());
				pst_c05.setString(10, data_rec.getCorpNo());
				pst_c05.setString(11, data_rec.getEstbNo());
				pst_c05.setString(12, data_rec.getCardNo());
				pst_c05.setString(13, data_rec.getCancSettDate());
				pst_c05.setString(14, data_rec.getSettDate());
				pst_c05.setString(15, data_rec.getSettTime());
				pst_c05.setString(16, data_rec.getReceName());
				pst_c05.setString(17, data_rec.getSettBankCode());
				pst_c05.setString(18, data_rec.getSettAcctNo());
				pst_c05.setDouble(19, data_rec.getSettTot());
				pst_c05.setDouble(20, data_rec.getSettAmt());
				pst_c05.setDouble(21, data_rec.getSettFee());
				pst_c05.setDouble(22, data_rec.getSettInterest());
				pst_c05.setDouble(23, data_rec.getDelayTot());
				pst_c05.setString(24, data_rec.getSettSumr());
				pst_c05.setDouble(25, data_rec.getYearFee());
				pst_c05.setDouble(26, data_rec.getShampPaymt());
				pst_c05.setDouble(27, data_rec.getRefundAmt());
				pst_c05.setDouble(28, data_rec.getBalanceAmt());
				pst_c05.setString(29, data_rec.getCardIni());
				pst_c05.setString(30, getCurTm());
				
				if(pst_c05.executeUpdate() != 1) {
					log.error("[Settlement2DB] INSERT WORK FAIL ~!!");
					commit_flag = false;
					break;
				}
				if(i_cnt > 1000000)	break;
			}/*while end.*/
			
			if(commit_flag)	db_con.commit();
			else {
				db_con.rollback();
				result = false;
			}
		} catch (IOException e) {
			log.error("[Settlement2DB] "+e);
			try{db_con.rollback();} catch (SQLException ignored){}
			result = false;
		} catch(SQLException e) {
			log.error("[CorpCard2DB] "+e);
			try{db_con.rollback();} catch (SQLException ignored){}
			result = false;
		} finally {
			if(pst_c05 != null)	try{pst_c05.close();} catch (SQLException ignored){}
			if(fis != null)	try{fis.close();} catch (IOException ignored){}
		}
		
		return result;
	}
	
	private boolean CardLimit2DB(String file_path, Connection db_con) {
		boolean result = true;
		boolean commit_flag = true;
		PreparedStatement pst_c06 = null;
		DtoDRecC06 data_rec = null;
		FileInputStream fis = null;
		byte[] dataBuf = null;

		try {
			db_con.setAutoCommit(false);
			String Qry = "INSERT INTO " + TBL_C06 + " (DATA_CODE, COMPANY_ID, SEND_DATE, SEQ_NO, READ_FLAG, Class, " +
					"CorpBizNo, CorpNo, EstablishNo, CardNo, CorpLmt, CorpRestLmt, CardLmt, CardRestLmt, CashLmt, CashRestLmt,  " +
					"AbroadLmt, AbroadRestLmt, ATMLmt, ATMRestLmt, PointMile, PointMileDate, MonthLmt, MonthRestLmt, CardIni, insert_time) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			pst_c06 = db_con.prepareStatement(Qry);
		
			int i_cnt = 0;
			fis = new FileInputStream(file_path);
			while(true) {
				i_cnt++;
				dataBuf = Util.readLineByte(fis);
				if(dataBuf.length==0)	break;
				if(!new String(dataBuf, 0, 1).equals("D"))	continue;

				data_rec = new DtoDRecC06(dataBuf);
				if(!data_rec.isNormal()) {
					log.error("[CardLimit2DB] dataBuf("+i_cnt+")=["+new String(dataBuf)+"]");
					log.error("[CardLimit2DB] "+data_rec.getAbnormalMsg());
					commit_flag = false;
					break;					
				}
				log.trace("[CardLimit2DB] data_code=["+data_rec.getDATA_CODE()+"], company_id=["+data_rec.getCOMPANY_ID()+"], send_dt=["+data_rec.getSEND_DATE()+"], seq_no=["+data_rec.getSEQ_NO()+"]");

				pst_c06.setString(1, data_rec.getDATA_CODE());
				pst_c06.setString(2, data_rec.getCOMPANY_ID());
				pst_c06.setString(3, data_rec.getSEND_DATE());
				pst_c06.setString(4, data_rec.getSEQ_NO());
				pst_c06.setString(5, data_rec.getREAD_FLAG());
				pst_c06.setString(6, data_rec.getApClass());
				pst_c06.setString(7, data_rec.getCorpBizNo());
				pst_c06.setString(8, data_rec.getCorpNo());
				pst_c06.setString(9, data_rec.getEstablishNo());
				pst_c06.setString(10, data_rec.getCardNo());				
				pst_c06.setDouble(11, data_rec.getCorpLmt());
				pst_c06.setDouble(12, data_rec.getCorpRestLmt());
				pst_c06.setDouble(13, data_rec.getCardLmt());
				pst_c06.setDouble(14, data_rec.getCardRestLmt());
				pst_c06.setDouble(15, data_rec.getCashLmt());
				pst_c06.setDouble(16, data_rec.getCashRestLmt());
				pst_c06.setDouble(17, data_rec.getAbroadLmt());
				pst_c06.setDouble(18, data_rec.getAbroadRestLmt());
				pst_c06.setDouble(19, data_rec.getATMLmt());
				pst_c06.setDouble(20, data_rec.getATMRestLmt());
				pst_c06.setDouble(21, data_rec.getPointMile());
				pst_c06.setString(22, data_rec.getPointMileDate());
				pst_c06.setDouble(23, data_rec.getMonthLmt());
				pst_c06.setDouble(24, data_rec.getMonthRestLmt());
				pst_c06.setString(25, data_rec.getCardIni());
				pst_c06.setString(26, getCurTm());
				
				if(pst_c06.executeUpdate() != 1) {
					log.error("[CardLimit2DB] INSERT WORK FAIL ~!!");
					commit_flag = false;
					break;
				}
				if(i_cnt > 1000000)	break;
			}/*while end.*/
			
			if(commit_flag)	db_con.commit();
			else {
				db_con.rollback();
				result = false;
			}		
		} catch (IOException e) {
			log.error("[CardLimit2DB] "+e);
			try{db_con.rollback();} catch (SQLException ignored){}
			result = false;
		} catch(SQLException e) {
			log.error("[CorpCard2DB] "+e);
			try{db_con.rollback();} catch (SQLException ignored){}
			result = false;
		} finally {
			if(pst_c06 != null)	try{pst_c06.close();} catch (SQLException ignored){}
			if(fis != null)	try{fis.close();} catch (IOException ignored){}
		}
		
		return result;
	}		
	
	private Connection Connect2DB(String db_driver, String db_url, String db_user, String db_pass) {
		try {
			Class.forName(db_driver);
			if(db_user==null || db_user.length()==0)	return DriverManager.getConnection(db_url);
			else return DriverManager.getConnection(db_url, db_user, db_pass);
		} catch (ClassNotFoundException e) {
			log.error("[Connect2DB] "+ e);
		} catch(SQLException e) {
			log.error("[Connect2DB] "+ e);
		}
		return null;
	}

	private String getCurTm() {
		Calendar cal		= Calendar.getInstance();
		DateFormat	format  = new SimpleDateFormat("yyyyMMddHHmmss");
		String date			= format.format(cal.getTime());
		return date;
	}


}
