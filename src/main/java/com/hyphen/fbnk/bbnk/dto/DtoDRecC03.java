package com.hyphen.fbnk.bbnk.dto;

import java.io.UnsupportedEncodingException;

public class DtoDRecC03 {
	private boolean isNormal;
	private int byte_length 		= 0;
	private String AbnormalMsg		= null;
	
	private String DATA_CODE		= null;	//001,	001
	private String COMPANY_ID		= null;	//020,	021
	private String SEND_DT			= null;	//010,	031
	private String SEQ_NO			= null;	//008,	039
	private String READ_FLAG		= null;	//001,	040

	private String ApClass			= null;	//001, 041
	private String BilType			= null;	//001, 042
	private String CorpBizNo		= null;	//010, 052
	private String CorpNo			= null;	//014, 066
	private String EstbNo			= null;	//010, 076
	private String CardNo			= null;	//016, 092
	private String ApprNo			= null;	//012, 104
	private String SlipNo			= null;	//012, 116
	private String CollNo			= null;	//032, 148
	private String OrgnApprDate		= null;	//010, 158
	private String BilWorkDate		= null;	//010, 168
	private double BilAmt			= 0D;	//017, 185
	private double BilFee			= 0D;	//017, 202
	private double MembshipFee		= 0D;	//017, 219
	private double AcquExch			= 0D;	//017, 236
	private double USDAcquTot		= 0D;	//017, 253
	private double ConvFee			= 0D;	//017, 270
	private double BilTot			= 0D;	//017, 287
	private String InstType			= null;	//001, 288
	private String InstMonth		= null;	//002, 290
	private String RestInstMonth	= null;	//002, 292
	private String SettDate			= null;	//010, 302
	private String MerchBizNo		= null;	//010, 312
	private String MerchName		= null;	//050, 362
	private String ServTypeYN		= null;	//001, 363
	private double DiscAmt			= 0D;	//017, 380
	private String EstbName			= null;	//050, 430
	private String UserNo			= null;	//030, 460
	private String UserName			= null;	//050, 510
	private double OrgnApprTot		= 0D;	//017, 527
	private String OrginTransTime	= null;	//008, 535
	private String OriginMerchName	= null;	//050, 585
	private String OriginMerchBizNo	= null;	//010, 595
	private String taxtype			= null;	//004, 599
	private String MerchCessDate	= null;	//008, 607
	private String ntsdate			= null;	//008, 615
	private String CardIni			= null;	//002, 617

	//4_Get
	public DtoDRecC03(byte[] dataRec) {
		try {
			byte_length = dataRec.length;
			setNormal(true);
			if(byte_length < 40) {
				setNormal(false);
				setAbnormalMsg("<Error~!!> string length is too short("+byte_length+"byte)");
				return;
			}

			DATA_CODE			= new String(dataRec, 0, 1, "EUC-KR");
			COMPANY_ID			= new String(dataRec, 1, 20, "EUC-KR");
			SEND_DT				= new String(dataRec, 21, 10, "EUC-KR");
			SEQ_NO				= new String(dataRec, 31, 8, "EUC-KR");
			READ_FLAG			= new String(dataRec, 39, 1, "EUC-KR");
			ApClass				= new String(dataRec, 40, 1, "EUC-KR");
			BilType				= new String(dataRec, 41, 1, "EUC-KR");
			CorpBizNo			= new String(dataRec, 42, 10, "EUC-KR");
			CorpNo				= new String(dataRec, 52, 14, "EUC-KR");
			EstbNo				= new String(dataRec, 66, 10, "EUC-KR");
			CardNo				= new String(dataRec, 76, 16, "EUC-KR");
			ApprNo				= new String(dataRec, 92, 12, "EUC-KR");
			SlipNo				= new String(dataRec, 104, 12, "EUC-KR");
			CollNo				= new String(dataRec, 116, 32, "EUC-KR");
			OrgnApprDate		= new String(dataRec, 148, 10, "EUC-KR");
			BilWorkDate			= new String(dataRec, 158, 10, "EUC-KR");
			BilAmt				= bgetDouble(new String(dataRec, 168, 17, "EUC-KR"));
			BilFee				= bgetDouble(new String(dataRec, 185, 17, "EUC-KR"));
			MembshipFee			= bgetDouble(new String(dataRec, 202, 17, "EUC-KR"));
			AcquExch			= bgetDouble(new String(dataRec, 219, 17, "EUC-KR"));
			USDAcquTot			= bgetDouble(new String(dataRec, 236, 17, "EUC-KR"));
			ConvFee				= bgetDouble(new String(dataRec, 253, 17, "EUC-KR"));
			BilTot				= bgetDouble(new String(dataRec, 270, 17, "EUC-KR"));
			InstType			= new String(dataRec, 287, 1, "EUC-KR");
			InstMonth			= new String(dataRec, 288, 2, "EUC-KR");
			RestInstMonth		= new String(dataRec, 290, 2, "EUC-KR");
			SettDate			= new String(dataRec, 292, 10, "EUC-KR");
			MerchBizNo			= new String(dataRec, 302, 10, "EUC-KR");
			MerchName			= new String(dataRec, 312, 50, "EUC-KR");
			ServTypeYN			= new String(dataRec, 362, 1, "EUC-KR");
			DiscAmt				= bgetDouble(new String(dataRec, 363, 17, "EUC-KR"));
			EstbName			= new String(dataRec, 380, 50, "EUC-KR");
			UserNo				= new String(dataRec, 430, 30, "EUC-KR");
			UserName			= new String(dataRec, 460, 50, "EUC-KR");
			OrgnApprTot			= bgetDouble(new String(dataRec, 510, 17, "EUC-KR"));
			OrginTransTime		= new String(dataRec, 527, 8, "EUC-KR");
			OriginMerchName		= new String(dataRec, 535, 50, "EUC-KR");
			OriginMerchBizNo	= new String(dataRec, 585, 10, "EUC-KR");
			taxtype				= new String(dataRec, 595, 4, "EUC-KR");
			MerchCessDate		= new String(dataRec, 599, 8, "EUC-KR");
			ntsdate				= new String(dataRec, 607, 8, "EUC-KR");
			CardIni				= new String(dataRec, 615, 2, "EUC-KR");
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
	
	public DtoDRecC03() {
		byte_length			= 0;
		isNormal			= true;
		AbnormalMsg			= "";
	
		DATA_CODE			= String.format("%-1s", " ");
		COMPANY_ID			= String.format("%-20s", " ");
		SEND_DT				= String.format("%-10s", " ");
		SEQ_NO				= String.format("%-8s", " ");
		READ_FLAG			= String.format("%-1s", " ");
		ApClass				= String.format("%-1s", " ");
		BilType				= String.format("%-1s", " ");
		CorpBizNo			= String.format("%-10s", " ");
		CorpNo				= String.format("%-14s", " ");
		EstbNo				= String.format("%-10s", " ");
		CardNo				= String.format("%-16s", " ");
		ApprNo				= String.format("%-12s", " ");
		SlipNo				= String.format("%-12s", " ");
		CollNo				= String.format("%-32s", " ");
		OrgnApprDate		= String.format("%-10s", " ");
		BilWorkDate			= String.format("%-10s", " ");
		BilAmt				= 0.000D;
		BilFee				= 0.000D;
		MembshipFee			= 0.000D;
		AcquExch			= 0.000D;
		USDAcquTot			= 0.000D;
		ConvFee				= 0.000D;
		BilTot				= 0.000D;
		InstType			= String.format("%-1s", " ");
		InstMonth			= String.format("%-2s", " ");
		RestInstMonth		= String.format("%-2s", " ");
		SettDate			= String.format("%-10s", " ");
		MerchBizNo			= String.format("%-10s", " ");
		MerchName			= String.format("%-50s", " ");
		ServTypeYN			= String.format("%-1s", " ");
		DiscAmt				= 0.000D;
		EstbName			= String.format("%-50s", " ");
		UserNo				= String.format("%-30s", " ");
		UserName			= String.format("%-50s", " ");
		OrgnApprTot			= 0.000D;
		OrginTransTime		= String.format("%-8s", " ");
		OriginMerchName		= String.format("%-50s", " ");
		OriginMerchBizNo	= String.format("%-10s", " ");
		taxtype				= String.format("%-4s", " ");
		MerchCessDate		= String.format("%-8s", " ");
		ntsdate				= String.format("%-8s", " ");
		CardIni				= String.format("%-2s", " ");
	}

	public String toStrBuf() {
		StringBuffer str_buf = new StringBuffer();
		str_buf.append(DATA_CODE).append(COMPANY_ID).append(SEND_DT).append(SEQ_NO).append(READ_FLAG);
		str_buf.append(ApClass).append(BilType).append(CorpBizNo).append(CorpNo).append(EstbNo).append(CardNo).append(ApprNo).append(SlipNo);
		str_buf.append(CollNo).append(OrgnApprDate).append(BilWorkDate).append(String.format("%017.3f", BilAmt)).append(String.format("%017.3f", BilFee)).append(String.format("%017.3f", MembshipFee));
		str_buf.append(String.format("%017.3f", AcquExch)).append(String.format("%017.3f", USDAcquTot)).append(String.format("%017.3f", ConvFee)).append(String.format("%017.3f", BilTot));
		str_buf.append(InstType).append(InstMonth).append(RestInstMonth).append(SettDate).append(MerchBizNo).append(MerchName).append(ServTypeYN).append(String.format("%017.3f", DiscAmt));
		str_buf.append(EstbName).append(UserNo).append(UserName).append(String.format("%017.3f", OrgnApprTot)).append(OriginMerchName).append(OriginMerchBizNo);
		str_buf.append(taxtype).append(MerchCessDate).append(ntsdate).append(CardIni);
		
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

	public Double bgetDouble(String str_val)
	{
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

	public String getSEND_DT()
	{
		return SEND_DT.trim();
	}

	public void setSEND_DT(String sEND_DT)
	{
		SEND_DT = byte_format(sEND_DT, 10);
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

	public String getBilType()
	{
		return BilType.trim();
	}

	public void setBilType(String bilType)
	{
		BilType = byte_format(bilType, 1);
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

	public String getApprNo()
	{
		return ApprNo.trim();
	}

	public void setApprNo(String apprNo)
	{
		ApprNo = byte_format(apprNo, 12);
	}

	public String getSlipNo()
	{
		return SlipNo.trim();
	}

	public void setSlipNo(String slipNo)
	{
		SlipNo = byte_format(slipNo, 12);
	}

	public String getCollNo()
	{
		return CollNo.trim();
	}

	public void setCollNo(String collNo)
	{
		CollNo = byte_format(collNo, 32);
	}

	public String getOrgnApprDate()
	{
		return OrgnApprDate.trim();
	}

	public void setOrgnApprDate(String orgnApprDate)
	{
		OrgnApprDate = byte_format(orgnApprDate, 10);
	}

	public String getBilWorkDate()
	{
		return BilWorkDate.trim();
	}

	public void setBilWorkDate(String bilWorkDate)
	{
		BilWorkDate = byte_format(bilWorkDate, 10);
	}

	public double getBilAmt()
	{
		return BilAmt;
	}

	public void setBilAmt(double bilAmt)
	{
		BilAmt = bilAmt;
	}

	public double getBilFee()
	{
		return BilFee;
	}

	public void setBilFee(double bilFee)
	{
		BilFee = bilFee;
	}

	public double getMembshipFee()
	{
		return MembshipFee;
	}

	public void setMembshipFee(double membshipFee)
	{
		MembshipFee = membshipFee;
	}

	public double getAcquExch()
	{
		return AcquExch;
	}

	public void setAcquExch(double acquExch)
	{
		AcquExch = acquExch;
	}

	public double getUSDAcquTot()
	{
		return USDAcquTot;
	}

	public void setUSDAcquTot(double uSDAcquTot)
	{
		USDAcquTot = uSDAcquTot;
	}

	public double getConvFee()
	{
		return ConvFee;
	}

	public void setConvFee(double convFee)
	{
		ConvFee = convFee;
	}

	public double getBilTot()
	{
		return BilTot;
	}

	public void setBilTot(double bilTot)
	{
		BilTot = bilTot;
	}

	public String getInstType()
	{
		return InstType.trim();
	}

	public void setInstType(String instType)
	{
		InstType = byte_format(instType, 1);
	}

	public String getInstMonth()
	{
		return InstMonth.trim();
	}

	public void setInstMonth(String instMonth)
	{
		InstMonth = byte_format(instMonth, 2);
	}

	public String getRestInstMonth()
	{
		return RestInstMonth.trim();
	}

	public void setRestInstMonth(String restInstMonth)
	{
		RestInstMonth = byte_format(restInstMonth, 2);
	}

	public String getSettDate()
	{
		return SettDate.trim();
	}

	public void setSettDate(String settDate)
	{
		SettDate = byte_format(settDate, 10);
	}

	public String getMerchBizNo()
	{
		return MerchBizNo.trim();
	}

	public void setMerchBizNo(String merchBizNo)
	{
		MerchBizNo = byte_format(merchBizNo, 10);
	}

	public String getMerchName()
	{
		return MerchName.trim();
	}

	public void setMerchName(String merchName)
	{
		MerchName = byte_format(merchName, 50);
	}

	public String getServTypeYN()
	{
		return ServTypeYN.trim();
	}

	public void setServTypeYN(String servTypeYN)
	{
		ServTypeYN = byte_format(servTypeYN, 1);
	}

	public double getDiscAmt()
	{
		return DiscAmt;
	}

	public void setDiscAmt(double discAmt)
	{
		DiscAmt = discAmt;
	}

	public String getEstbName()
	{
		return EstbName.trim();
	}

	public void setEstbName(String estbName)
	{
		EstbName = byte_format(estbName, 50);
	}

	public String getUserNo()
	{
		return UserNo.trim();
	}

	public void setUserNo(String userNo)
	{
		UserNo = byte_format(userNo, 30);
	}

	public String getUserName()
	{
		return UserName.trim();
	}

	public void setUserName(String userName)
	{
		UserName = byte_format(userName, 50);
	}

	public double getOrgnApprTot()
	{
		return OrgnApprTot;
	}

	public void setOrgnApprTot(double orgnApprTot)
	{
		OrgnApprTot = orgnApprTot;
	}

	public String getOrginTransTime()
	{
		return OrginTransTime.trim();
	}

	public void setOrginTransTime(String orginTransTime)
	{
		OrginTransTime = byte_format(orginTransTime, 8);
	}

	public String getOriginMerchName()
	{
		return OriginMerchName.trim();
	}

	public void setOriginMerchName(String originMerchName)
	{
		OriginMerchName = byte_format(originMerchName, 50);
	}

	public String getOriginMerchBizNo()
	{
		return OriginMerchBizNo.trim();
	}

	public void setOriginMerchBizNo(String originMerchBizNo)
	{
		OriginMerchBizNo = byte_format(originMerchBizNo, 10);
	}

	public String getTaxtype()
	{
		return taxtype.trim();
	}

	public void setTaxtype(String taxtype)
	{
		this.taxtype = byte_format(taxtype, 4);
	}

	public String getMerchCessDate()
	{
		return MerchCessDate.trim();
	}

	public void setMerchCessDate(String merchCessDate)
	{
		MerchCessDate = byte_format(merchCessDate, 8);
	}

	public String getNtsdate()
	{
		return ntsdate.trim();
	}

	public void setNtsdate(String ntsdate)
	{
		this.ntsdate = byte_format(ntsdate, 8);
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
