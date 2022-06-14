package com.hyphen.fbnk.bbnk.dto;

import java.io.UnsupportedEncodingException;

public class DtoDRecC05 {
	private boolean isNormal;
	private int byte_length 	= 0;
	private String AbnormalMsg	= null;
	
	private String DATA_CODE	= null;	//001,	001
	private String COMPANY_ID	= null;	//020,	021
	private String SEND_DATE	= null;	//010,	031
	private String SEQ_NO		= null;	//008,	039
	private String READ_FLAG	= null;	//001,	040
	private String ApClass		= null;	//001, 041
	private String SettCancYN	= null;
	private String SettSeqNo	= null;
	private String CorpBizNo	= null;
	private String CorpNo		= null;
	private String EstbNo		= null;
	private String CardNo		= null;
	private String CancSettDate	= null;
	private String SettDate		= null;
	private String SettTime		= null;
	private String ReceName		= null;
	private String SettBankCode	= null;
	private String SettAcctNo	= null;
	private double SettTot		= 0D;
	private double SettAmt		= 0D;
	private double SettFee		= 0D;
	private double SettInterest	= 0D;
	private double DelayTot		= 0D;
	private String SettSumr		= null;
	private double YearFee		= 0D;
	private double ShampPaymt	= 0D;
	private double RefundAmt	= 0D;
	private double BalanceAmt	= 0D;
	private String CardIni		= null;
	
	//4_Get
	public DtoDRecC05(byte[] dataRec) {
		try {
			byte_length = dataRec.length;
			isNormal = true;
			if(byte_length < 40) {
				isNormal = false;
				AbnormalMsg = "<Error~!!> string length is too short("+byte_length+"byte)";
				return;
			}

			DATA_CODE		= new String(dataRec, 0, 1, "EUC-KR");
			COMPANY_ID		= new String(dataRec, 1, 20, "EUC-KR");
			SEND_DATE		= new String(dataRec, 21, 10, "EUC-KR");
			SEQ_NO			= new String(dataRec, 31, 8, "EUC-KR");
			READ_FLAG		= new String(dataRec, 39, 1, "EUC-KR");
			ApClass			= new String(dataRec, 40, 1, "EUC-KR");
			SettCancYN		= new String(dataRec, 41, 1, "EUC-KR");
			SettSeqNo		= new String(dataRec, 42, 10, "EUC-KR");
			CorpBizNo		= new String(dataRec, 52, 10, "EUC-KR");
			CorpNo			= new String(dataRec, 62, 14, "EUC-KR");
			EstbNo			= new String(dataRec, 76, 10, "EUC-KR");
			CardNo			= new String(dataRec, 86, 16, "EUC-KR");
			CancSettDate	= new String(dataRec, 102, 10, "EUC-KR");
			SettDate		= new String(dataRec, 112, 10, "EUC-KR");
			SettTime		= new String(dataRec, 122, 8, "EUC-KR");
			ReceName		= new String(dataRec, 130, 50, "EUC-KR");
			SettBankCode	= new String(dataRec, 180, 10, "EUC-KR");
			SettAcctNo		= new String(dataRec, 190, 20, "EUC-KR");
			SettTot			= bgetDouble(new String(dataRec, 210, 17, "EUC-KR"));
			SettAmt			= bgetDouble(new String(dataRec, 227, 17, "EUC-KR"));
			SettFee			= bgetDouble(new String(dataRec, 244, 17, "EUC-KR"));
			SettInterest	= bgetDouble(new String(dataRec, 261, 17, "EUC-KR"));
			DelayTot		= bgetDouble(new String(dataRec, 278, 17, "EUC-KR"));
			SettSumr		= new String(dataRec, 295, 50, "EUC-KR");
			YearFee			= bgetDouble(new String(dataRec, 345, 17, "EUC-KR"));
			ShampPaymt		= bgetDouble(new String(dataRec, 362, 17, "EUC-KR"));
			RefundAmt		= bgetDouble(new String(dataRec, 379, 17, "EUC-KR"));
			BalanceAmt		= bgetDouble(new String(dataRec, 396, 17, "EUC-KR"));
			CardIni			= new String(dataRec, 413, 2, "EUC-KR");
		} catch(NumberFormatException e) {
			isNormal = false;
			AbnormalMsg = e.toString();
		} catch(IndexOutOfBoundsException e) {
			isNormal = false;
			AbnormalMsg = e.toString();
		} catch(UnsupportedEncodingException e) {
			isNormal = false;
			AbnormalMsg = e.toString();
		}
	}

	//4_Put
	public DtoDRecC05() {
		byte_length		= 0;
		isNormal		= true;
		AbnormalMsg		= "";
		
		DATA_CODE		= String.format("%-1s", " ");
		COMPANY_ID		= String.format("%-20s", " ");
		SEND_DATE		= String.format("%-10s", " ");
		SEQ_NO			= String.format("%-8s", " ");
		READ_FLAG		= String.format("%-1s", " ");
		ApClass			= String.format("%-1s", " ");
		SettCancYN		= String.format("%-1s", " ");
		SettSeqNo		= String.format("%-10s", " ");
		CorpBizNo		= String.format("%-10s", " ");
		CorpNo			= String.format("%-14s", " ");
		EstbNo			= String.format("%-10s", " ");
		CardNo			= String.format("%-16s", " ");
		CancSettDate	= String.format("%-10s", " ");
		SettDate		= String.format("%-10s", " ");
		SettTime		= String.format("%-8s", " ");
		ReceName		= String.format("%-50s", " ");
		SettBankCode	= String.format("%-10s", " ");
		SettAcctNo		= String.format("%-20s", " ");
		SettTot			= 0.000D;
		SettAmt			= 0.000D;
		SettFee			= 0.000D;
		SettInterest	= 0.000D;
		DelayTot		= 0.000D;
		SettSumr		= String.format("%-50s", " ");
		YearFee			= 0.000D;
		ShampPaymt		= 0.000D;
		RefundAmt		= 0.000D;
		BalanceAmt		= 0.000D;
		CardIni			= String.format("%-2s", " ");
	}
	
	public String toStrBuf() {
		StringBuffer str_buf = new StringBuffer();
		str_buf.append(DATA_CODE).append(COMPANY_ID).append(SEND_DATE).append(SEQ_NO).append(READ_FLAG);
		str_buf.append(ApClass).append(SettCancYN).append(SettSeqNo).append(CorpBizNo).append(CorpNo).append(EstbNo).append(CardNo);
		str_buf.append(CancSettDate).append(SettDate).append(SettTime).append(ReceName).append(SettBankCode).append(SettAcctNo);
		str_buf.append(String.format("%017.3f", SettTot)).append(String.format("%017.3f", SettAmt)).append(String.format("%017.3f", SettFee));
		str_buf.append(String.format("%017.3f", SettInterest)).append(String.format("%017.3f", DelayTot)).append(SettSumr);
		str_buf.append(String.format("%017.3f", YearFee)).append(String.format("%017.3f", ShampPaymt)).append(String.format("%017.3f", RefundAmt));
		str_buf.append(String.format("%017.3f", BalanceAmt)).append(CardIni);
	
		return str_buf.toString();
	}	
	
	public String byte_format(String str, int len) {
		String rt_val = null;
		try {
			if(str.getBytes("EUC-KR").length > len)	rt_val = new String(str.getBytes("EUC-KR"), 0, len, "EUC-KR");
			else {
				int pad_len = len - str.getBytes("EUC-KR").length;
				StringBuffer pad_sb = new StringBuffer();
				for(int i=0 ; i<pad_len ; i++)	pad_sb.append(" ");
				rt_val = new String(str.getBytes("EUC-KR"), "EUC-KR") + pad_sb.toString();
			}
		} 
		catch (UnsupportedEncodingException ignored){}
		
		return rt_val;
	}

	public Double bgetDouble(String str_val) {
		if(str_val.trim().equals(""))	str_val = "0";
		return Double.parseDouble(str_val.trim());
	}	
	
	public boolean isNormal()
	{
		return isNormal;
	}

	public void setNormal(boolean isNormal)
	{
		this.isNormal = isNormal;
	}

	public String getAbnormalMsg()
	{
		return AbnormalMsg;
	}

	public void setAbnormalMsg(String abnormalMsg)
	{
		AbnormalMsg = abnormalMsg;
	}

	public String getDATA_CODE()
	{
		return DATA_CODE.trim();
	}

	public void setDATA_CODE(String dATA_CODE)
	{
		DATA_CODE = byte_format(dATA_CODE, 1);
	}

	public String getCOMPANY_ID()
	{
		return COMPANY_ID.trim();
	}

	public void setCOMPANY_ID(String cOMPANY_ID)
	{
		COMPANY_ID = byte_format(cOMPANY_ID, 20);
	}

	public String getSEND_DATE()
	{
		return SEND_DATE.trim();
	}

	public void setSEND_DATE(String sEND_DATE)
	{
		SEND_DATE = byte_format(sEND_DATE, 10);
	}

	public String getSEQ_NO()
	{
		return SEQ_NO.trim();
	}

	public void setSEQ_NO(String sEQ_NO)
	{
		SEQ_NO = byte_format(sEQ_NO, 8);
	}

	public String getREAD_FLAG()
	{
		return READ_FLAG.trim();
	}

	public void setREAD_FLAG(String rEAD_FLAG)
	{
		READ_FLAG = byte_format(rEAD_FLAG, 1);
	}

	public String getApClass()
	{
		return ApClass.trim();
	}

	public void setApClass(String apClass)
	{
		ApClass = byte_format(apClass, 1);
	}

	public String getSettCancYN()
	{
		return SettCancYN.trim();
	}

	public void setSettCancYN(String settCancYN)
	{
		SettCancYN = byte_format(settCancYN, 1);
	}

	public String getSettSeqNo()
	{
		return SettSeqNo.trim();
	}

	public void setSettSeqNo(String settSeqNo)
	{
		SettSeqNo = byte_format(settSeqNo, 10);
	}

	public String getCorpBizNo()
	{
		return CorpBizNo.trim();
	}

	public void setCorpBizNo(String corpBizNo)
	{
		CorpBizNo = byte_format(corpBizNo, 10);
	}

	public String getCorpNo()
	{
		return CorpNo.trim();
	}

	public void setCorpNo(String corpNo)
	{
		CorpNo = byte_format(corpNo, 14);
	}

	public String getEstbNo()
	{
		return EstbNo.trim();
	}

	public void setEstbNo(String estbNo)
	{
		EstbNo = byte_format(estbNo, 10);
	}

	public String getCardNo()
	{
		return CardNo.trim();
	}

	public void setCardNo(String cardNo)
	{
		CardNo = byte_format(cardNo, 16);
	}

	public String getCancSettDate()
	{
		return CancSettDate.trim();
	}

	public void setCancSettDate(String cancSettDate)
	{
		CancSettDate = byte_format(cancSettDate, 10);
	}

	public String getSettDate()
	{
		return SettDate.trim();
	}

	public void setSettDate(String settDate)
	{
		SettDate = byte_format(settDate, 10);
	}

	public String getSettTime()
	{
		return SettTime.trim();
	}

	public void setSettTime(String settTime)
	{
		SettTime = byte_format(settTime, 8);
	}

	public String getReceName()
	{
		return ReceName.trim();
	}

	public void setReceName(String receName)
	{
		ReceName = byte_format(receName, 50);
	}

	public String getSettBankCode()
	{
		return SettBankCode.trim();
	}

	public void setSettBankCode(String settBankCode)
	{
		SettBankCode = byte_format(settBankCode, 10);
	}

	public String getSettAcctNo()
	{
		return SettAcctNo.trim();
	}

	public void setSettAcctNo(String settAcctNo)
	{
		SettAcctNo = byte_format(settAcctNo, 20);
	}

	public double getSettTot()
	{
		return SettTot;
	}

	public void setSettTot(double settTot)
	{
		SettTot = settTot;
	}

	public double getSettAmt()
	{
		return SettAmt;
	}

	public void setSettAmt(double settAmt)
	{
		SettAmt = settAmt;
	}

	public double getSettFee()
	{
		return SettFee;
	}

	public void setSettFee(double settFee)
	{
		SettFee = settFee;
	}

	public double getSettInterest()
	{
		return SettInterest;
	}

	public void setSettInterest(double settInterest)
	{
		SettInterest = settInterest;
	}

	public double getDelayTot()
	{
		return DelayTot;
	}

	public void setDelayTot(double delayTot)
	{
		DelayTot = delayTot;
	}

	public String getSettSumr()
	{
		return SettSumr.trim();
	}

	public void setSettSumr(String settSumr)
	{
		SettSumr = byte_format(settSumr, 50);
	}

	public double getYearFee()
	{
		return YearFee;
	}

	public void setYearFee(double yearFee)
	{
		YearFee = yearFee;
	}

	public double getShampPaymt()
	{
		return ShampPaymt;
	}

	public void setShampPaymt(double shampPaymt)
	{
		ShampPaymt = shampPaymt;
	}

	public double getRefundAmt()
	{
		return RefundAmt;
	}

	public void setRefundAmt(double refundAmt)
	{
		RefundAmt = refundAmt;
	}

	public double getBalanceAmt()
	{
		return BalanceAmt;
	}

	public void setBalanceAmt(double balanceAmt)
	{
		BalanceAmt = balanceAmt;
	}

	public String getCardIni()
	{
		return CardIni.trim();
	}

	public void setCardIni(String cardIni)
	{
		CardIni = byte_format(cardIni, 2);
	}
}
