package com.hyphen.fbnk.bbnk.dto;

import java.io.UnsupportedEncodingException;

public class DtoDRecC06 {
	private boolean isNormal;
	private int byte_length 		= 0;
	private String AbnormalMsg		= null;
	
	private String DATA_CODE		= null;	//001,	001
	private String COMPANY_ID		= null;	//020,	021
	private String SEND_DATE		= null;	//010,	031
	private String SEQ_NO			= null;	//008,	039
	private String READ_FLAG		= null;	//001,	040
	private String ApClass			= null;	//001, 041
	private String CorpBizNo		= null;
	private String CorpNo			= null;
	private String EstablishNo		= null;
	private String CardNo			= null;
	private double CorpLmt			= 0D;
	private double CorpRestLmt		= 0D;
	private double CardLmt			= 0D;
	private double CardRestLmt		= 0D;
	private double CashLmt			= 0D;
	private double CashRestLmt		= 0D;
	private double AbroadLmt		= 0D;
	private double AbroadRestLmt	= 0D;
	private double ATMLmt			= 0D;
	private double ATMRestLmt		= 0D;
	private double PointMile		= 0D;
	private String PointMileDate	= null;
	private double MonthLmt			= 0D;
	private double MonthRestLmt		= 0D;
	private String CardIni			= null;
	
	//4_Get
	public DtoDRecC06(byte[] dataRec) {
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
			CorpBizNo		= new String(dataRec, 41, 10, "EUC-KR");
			CorpNo			= new String(dataRec, 51, 14, "EUC-KR");
			EstablishNo		= new String(dataRec, 65, 10, "EUC-KR");
			CardNo			= new String(dataRec, 75, 16, "EUC-KR");
			CorpLmt			= bgetDouble(new String(dataRec, 91, 17, "EUC-KR"));
			CorpRestLmt		= bgetDouble(new String(dataRec, 108, 17, "EUC-KR"));
			CardLmt			= bgetDouble(new String(dataRec, 125, 17, "EUC-KR"));
			CardRestLmt		= bgetDouble(new String(dataRec, 142, 17, "EUC-KR"));
			CashLmt			= bgetDouble(new String(dataRec, 159, 17, "EUC-KR"));
			CashRestLmt		= bgetDouble(new String(dataRec, 176, 17, "EUC-KR"));
			AbroadLmt		= bgetDouble(new String(dataRec, 193, 17, "EUC-KR"));
			AbroadRestLmt	= bgetDouble(new String(dataRec, 210, 17, "EUC-KR"));
			ATMLmt			= bgetDouble(new String(dataRec, 227, 17, "EUC-KR"));
			ATMRestLmt		= bgetDouble(new String(dataRec, 244, 17, "EUC-KR"));
			PointMile		= bgetDouble(new String(dataRec, 261, 17, "EUC-KR"));
			PointMileDate	= new String(dataRec, 278, 10, "EUC-KR");
			MonthLmt		= bgetDouble(new String(dataRec, 288, 17, "EUC-KR"));
			MonthRestLmt	= bgetDouble(new String(dataRec, 305, 17, "EUC-KR"));
			CardIni			= new String(dataRec, 322, 2, "EUC-KR");
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
	public DtoDRecC06() {
		byte_length		= 0;
		isNormal		= true;
		AbnormalMsg		= "";
		
		DATA_CODE		= String.format("%-1s", " ");
		COMPANY_ID		= String.format("%-20s", " ");
		SEND_DATE		= String.format("%-10s", " ");
		SEQ_NO			= String.format("%-8s", " ");
		READ_FLAG		= String.format("%-1s", " ");
		ApClass			= String.format("%-1s", " ");
		CorpBizNo		= String.format("%-10s", " ");
		CorpNo			= String.format("%-14s", " ");
		EstablishNo		= String.format("%-10s", " ");
		CardNo			= String.format("%-16s", " ");
		CorpLmt			= 0.000D;
		CorpRestLmt		= 0.000D;
		CardLmt			= 0.000D;
		CardRestLmt		= 0.000D;
		CashLmt			= 0.000D;
		CashRestLmt		= 0.000D;
		AbroadLmt		= 0.000D;
		AbroadRestLmt	= 0.000D;
		ATMLmt			= 0.000D;
		ATMRestLmt		= 0.000D;
		PointMile		= 0.000D;
		PointMileDate	= String.format("%-10s", " ");
		MonthLmt		= 0.000D;
		MonthRestLmt	= 0.000D;
		CardIni			= String.format("%-2s", " ");
	}
	
	public String toStrBuf() {
		StringBuffer str_buf = new StringBuffer();
		str_buf.append(DATA_CODE).append(COMPANY_ID).append(SEND_DATE).append(SEQ_NO).append(READ_FLAG);
		str_buf.append(ApClass).append(CorpBizNo).append(CorpNo).append(EstablishNo).append(CardNo).append(String.format("%017.3f", CorpLmt));
		str_buf.append(String.format("%017.3f", CorpRestLmt)).append(String.format("%017.3f", CardLmt)).append(String.format("%017.3f", CardRestLmt));
		str_buf.append(String.format("%017.3f", CashLmt)).append(String.format("%017.3f", CashRestLmt)).append(String.format("%017.3f", AbroadLmt));
		str_buf.append(String.format("%017.3f", AbroadRestLmt)).append(String.format("%017.3f", ATMLmt)).append(String.format("%017.3f", ATMRestLmt));
		str_buf.append(String.format("%017.3f", PointMile)).append(PointMileDate).append(String.format("%017.3f", MonthLmt));
		str_buf.append(String.format("%017.3f", MonthRestLmt)).append(CardIni);
		
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

	public String getEstablishNo()
	{
		return EstablishNo.trim();
	}

	public void setEstablishNo(String establishNo)
	{
		EstablishNo = byte_format(establishNo, 10);
	}

	public String getCardNo()
	{
		return CardNo.trim();
	}

	public void setCardNo(String cardNo)
	{
		CardNo = byte_format(cardNo, 16);
	}

	public double getCorpLmt()
	{
		return CorpLmt;
	}

	public void setCorpLmt(double corpLmt)
	{
		CorpLmt = corpLmt;
	}

	public double getCorpRestLmt()
	{
		return CorpRestLmt;
	}

	public void setCorpRestLmt(double corpRestLmt)
	{
		CorpRestLmt = corpRestLmt;
	}

	public double getCardLmt()
	{
		return CardLmt;
	}

	public void setCardLmt(double cardLmt)
	{
		CardLmt = cardLmt;
	}

	public double getCardRestLmt()
	{
		return CardRestLmt;
	}

	public void setCardRestLmt(double cardRestLmt)
	{
		CardRestLmt = cardRestLmt;
	}

	public double getCashLmt()
	{
		return CashLmt;
	}

	public void setCashLmt(double cashLmt)
	{
		CashLmt = cashLmt;
	}

	public double getCashRestLmt()
	{
		return CashRestLmt;
	}

	public void setCashRestLmt(double cashRestLmt)
	{
		CashRestLmt = cashRestLmt;
	}

	public double getAbroadLmt()
	{
		return AbroadLmt;
	}

	public void setAbroadLmt(double abroadLmt)
	{
		AbroadLmt = abroadLmt;
	}

	public double getAbroadRestLmt()
	{
		return AbroadRestLmt;
	}

	public void setAbroadRestLmt(double abroadRestLmt)
	{
		AbroadRestLmt = abroadRestLmt;
	}

	public double getATMLmt()
	{
		return ATMLmt;
	}

	public void setATMLmt(double aTMLmt)
	{
		ATMLmt = aTMLmt;
	}

	public double getATMRestLmt()
	{
		return ATMRestLmt;
	}

	public void setATMRestLmt(double aTMRestLmt)
	{
		ATMRestLmt = aTMRestLmt;
	}

	public double getPointMile()
	{
		return PointMile;
	}

	public void setPointMile(double pointMile)
	{
		PointMile = pointMile;
	}

	public String getPointMileDate()
	{
		return PointMileDate.trim();
	}

	public void setPointMileDate(String pointMileDate)
	{
		PointMileDate = byte_format(pointMileDate, 10);
	}

	public double getMonthLmt()
	{
		return MonthLmt;
	}

	public void setMonthLmt(double monthLmt)
	{
		MonthLmt = monthLmt;
	}

	public double getMonthRestLmt()
	{
		return MonthRestLmt;
	}

	public void setMonthRestLmt(double monthRestLmt)
	{
		MonthRestLmt = monthRestLmt;
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
