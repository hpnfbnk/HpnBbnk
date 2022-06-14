package com.hyphen.fbnk.bbnk.dto;

import java.io.UnsupportedEncodingException;

public class DtoDRecC01 {
	private boolean isNormal;
	private int byte_length = 0;
	private String AbnormalMsg		= null;
	
	private String DATA_CODE		= null;	//001,	001
	private String COMPANY_ID		= null;	//020,	021
	private String SEND_DT			= null;	//010,	031
	private String SEQ_NO			= null;	//008,	039
	private String READ_FLAG		= null;	//001,	040
	
	private String ApClass			= null;	//001, 041
	private String CardNo			= null;	//016, 057
	private String ApprNo			= null;	//012, 069
	private String TransDate		= null;	//010, 079
	private String TransTime		= null;	//008, 087
	private String CardType1		= null;	//050, 137
	private String CardType2		= null;	//001, 138
	private String CurrCode			= null;	//003, 141
	private double ApprAmt			= 0D;	//017, 158
	private double VAT				= 0D;	//017, 175
	private double Tips				= 0D;	//017, 192
	private double ApprTot			= 0D;	//017, 209
	private double ApprExch			= 0D;	//017, 226
	private double USDApprTot		= 0D;	//017, 243
	private String InstType			= null;	//001, 244
	private int InstMonth			= 0;	//002, 246
	private String Abroad			= null;	//001, 247
	private String SlipNo			= null;	//012, 259
	private String TerminalNo		= null;	//012, 271
	private String Purch			= null;	//001, 272
	private String Acqulssuer		= null;	//050, 322
	private String MerchBizNo		= null;	//010, 332
	private String MerchNo			= null;	//012, 344
	private String MerchName		= null;	//050, 394
	private String Master			= null;	//050, 444
	private String MerchTel			= null;	//014, 458
	private String MerchZipCode		= null;	//006, 464
	private String MerchAddr1		= null;	//100, 564
	private String MerchAddr2		= null;	//100, 664
	private String MerchStatus		= null;	//001, 665
	private String MCCName			= null;	//050, 715
	private String MCCCode			= null;	//004, 719
	private String PartApprCancYN	= null;	//001, 720
	private String ServTypeYN		= null;	//001, 721
	private String CollNo			= null;	//032, 753
	private String TaxType			= null;	//020, 773
	private String MerchCessDate	= null;	//010, 783
	private String OrginTransDate	= null;	//010, 793
	private String OrginApprNo		= null;	//012, 805
	private String OrginTransTime	= null;	//008, 813
	private String OrginMerchName	= null;	//050, 863
	private String OrginMerchBizNo	= null;	//010, 873
	private String ntsdate			= null;	//008, 881
	private String CardIni			= null;	//002, 883
	
	//4_Get
	public DtoDRecC01(byte[] dataRec) {
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
			SEND_DT			= new String(dataRec, 21, 10, "EUC-KR");
			SEQ_NO			= new String(dataRec, 31, 8, "EUC-KR");
			READ_FLAG		= new String(dataRec, 39, 1, "EUC-KR");
			ApClass			= new String(dataRec, 40, 1, "EUC-KR");
			CardNo			= new String(dataRec, 41, 16, "EUC-KR");
			ApprNo			= new String(dataRec, 57, 12, "EUC-KR");
			TransDate		= new String(dataRec, 69, 10, "EUC-KR");
			TransTime		= new String(dataRec, 79, 8, "EUC-KR");
			CardType1		= new String(dataRec, 87, 50, "EUC-KR");
			CardType2		= new String(dataRec, 137, 1, "EUC-KR");
			CurrCode		= new String(dataRec, 138, 3, "EUC-KR");
			ApprAmt			= bgetDouble(new String(dataRec, 141, 17, "EUC-KR"));
			VAT				= bgetDouble(new String(dataRec, 158, 17, "EUC-KR"));
			Tips			= bgetDouble(new String(dataRec, 175, 17, "EUC-KR"));
			ApprTot			= bgetDouble(new String(dataRec, 192, 17, "EUC-KR"));
			ApprExch		= bgetDouble(new String(dataRec, 209, 17, "EUC-KR"));
			USDApprTot		= bgetDouble(new String(dataRec, 226, 17, "EUC-KR"));
			InstType		= new String(dataRec, 243, 1, "EUC-KR");
			InstMonth		= Integer.parseInt("0"+new String(dataRec, 244, 2, "EUC-KR").trim());
			Abroad			= new String(dataRec, 246, 1, "EUC-KR");
			SlipNo			= new String(dataRec, 247, 12, "EUC-KR");
			TerminalNo		= new String(dataRec, 259, 12, "EUC-KR");
			Purch			= new String(dataRec, 271, 1, "EUC-KR");
			Acqulssuer		= new String(dataRec, 272, 50, "EUC-KR");
			MerchBizNo		= new String(dataRec, 322, 10, "EUC-KR");
			MerchNo			= new String(dataRec, 332, 12, "EUC-KR");
			MerchName		= new String(dataRec, 344, 50, "EUC-KR");
			Master			= new String(dataRec, 394, 50, "EUC-KR");
			MerchTel		= new String(dataRec, 444, 14, "EUC-KR");
			MerchZipCode	= new String(dataRec, 458, 6, "EUC-KR");
			MerchAddr1		= new String(dataRec, 464, 100, "EUC-KR");
			MerchAddr2		= new String(dataRec, 564, 100, "EUC-KR");
			MerchStatus		= new String(dataRec, 664, 1, "EUC-KR");
			MCCName			= new String(dataRec, 665, 50, "EUC-KR");
			MCCCode			= new String(dataRec, 715, 4, "EUC-KR");
			PartApprCancYN	= new String(dataRec, 719, 1, "EUC-KR");
			ServTypeYN		= new String(dataRec, 720, 1, "EUC-KR");
			CollNo			= new String(dataRec, 721, 32, "EUC-KR");
			TaxType			= new String(dataRec, 753, 20, "EUC-KR");
			MerchCessDate	= new String(dataRec, 773, 10, "EUC-KR");
			OrginTransDate	= new String(dataRec, 783, 10, "EUC-KR");
			OrginApprNo		= new String(dataRec, 793, 12, "EUC-KR");
			OrginTransTime	= new String(dataRec, 805, 8, "EUC-KR");
			OrginMerchName	= new String(dataRec, 813, 50, "EUC-KR");
			OrginMerchBizNo	= new String(dataRec, 863, 10, "EUC-KR");
			ntsdate			= new String(dataRec, 873, 8, "EUC-KR");
			CardIni			= new String(dataRec, 881, 2, "EUC-KR");
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
	public DtoDRecC01() {
		byte_length		= 0;
		isNormal		= true;
		AbnormalMsg		= "";
		
		DATA_CODE		= String.format("%-1s", " ");
		COMPANY_ID		= String.format("%-20s", " ");
		SEND_DT			= String.format("%-10s", " ");
		SEQ_NO			= String.format("%-8s", " ");
		READ_FLAG		= String.format("%-1s", " ");
		ApClass			= String.format("%-1s", " ");
		CardNo			= String.format("%-16s", " ");
		ApprNo			= String.format("%-12s", " ");
		TransDate		= String.format("%-10s", " ");
		TransTime		= String.format("%-8s", " ");
		CardType1		= String.format("%-50s", " ");
		CardType2		= String.format("%-1s", " ");
		CurrCode		= String.format("%-3s", " ");
		ApprAmt			= 0.000D;
		VAT				= 0.000D;
		Tips			= 0.000D;
		ApprTot			= 0.000D;
		ApprExch		= 0.000D;
		USDApprTot		= 0.000D;
		InstType		= String.format("%-1s", " ");
		InstMonth		= 0;
		Abroad			= String.format("%-1s", " ");
		SlipNo			= String.format("%-12s", " ");
		TerminalNo		= String.format("%-12s", " ");
		Purch			= String.format("%-1s", " ");
		Acqulssuer		= String.format("%-50s", " ");
		MerchBizNo		= String.format("%-10s", " ");
		MerchNo			= String.format("%-12s", " ");
		MerchName		= String.format("%-50s", " ");
		Master			= String.format("%-50s", " ");
		MerchTel		= String.format("%-14s", " ");
		MerchZipCode	= String.format("%-6s", " ");
		MerchAddr1		= String.format("%-100s", " ");
		MerchAddr2		= String.format("%-100s", " ");
		MerchStatus		= String.format("%-1s", " ");
		MCCName			= String.format("%-50s", " ");
		MCCCode			= String.format("%-4s", " ");
		PartApprCancYN	= String.format("%-1s", " ");
		ServTypeYN		= String.format("%-1s", " ");
		CollNo			= String.format("%-32s", " ");
		TaxType			= String.format("%-20s", " ");
		MerchCessDate	= String.format("%-10s", " ");
		OrginTransDate	= String.format("%-10s", " ");
		OrginApprNo		= String.format("%-12s", " ");
		OrginTransTime	= String.format("%-8s", " ");
		OrginMerchName	= String.format("%-50s", " ");
		OrginMerchBizNo	= String.format("%-10s", " ");
		ntsdate			= String.format("%-8s", " ");
		CardIni			= String.format("%-2s", " ");
	}
	
	public String toStrBuf() {
		StringBuffer str_buf = new StringBuffer();
		str_buf.append(DATA_CODE).append(COMPANY_ID).append(SEND_DT).append(SEQ_NO).append(READ_FLAG);
		str_buf.append(ApClass).append(CardNo).append(ApprNo).append(TransDate).append(TransTime).append(CardType1).append(CardType2).append(CurrCode);
		str_buf.append(String.format("%017.3f", ApprAmt)).append(String.format("%017.3f", VAT)).append(String.format("%017.3f", Tips)).append(String.format("%017.3f", ApprTot)).append(String.format("%017.3f", ApprExch)).append(String.format("%017.3f", USDApprTot));
		str_buf.append(InstType).append(String.format("%02d", InstMonth)).append(Abroad).append(SlipNo).append(TerminalNo).append(Purch).append(Acqulssuer);
		str_buf.append(MerchBizNo).append(MerchNo).append(MerchName).append(Master).append(MerchTel).append(MerchZipCode).append(MerchAddr1).append(MerchAddr2);
		str_buf.append(MerchStatus).append(MCCName).append(MCCCode).append(PartApprCancYN).append(ServTypeYN).append(CollNo).append(TaxType).append(MerchCessDate);
		str_buf.append(OrginTransDate).append(OrginApprNo).append(OrginTransTime).append(OrginMerchName).append(OrginMerchBizNo).append(ntsdate).append(CardIni);
		
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
	
	public String getData_code()
	{
		return DATA_CODE.trim();
	}

	public void setData_code(String data_code)
	{
		this.DATA_CODE = byte_format(data_code, 1);
	}

	public String getCompany_id()
	{
		return COMPANY_ID.trim();
	}

	public void setCompany_id(String company_id)
	{
		this.COMPANY_ID = byte_format(company_id, 20);
	}

	public String getSend_dt()
	{
		return SEND_DT.trim();
	}

	public void setSend_dt(String send_dt)
	{
		this.SEND_DT = byte_format(send_dt, 10);
	}

	public String getSeq_no()
	{
		return SEQ_NO.trim();
	}

	public void setSeq_no(String seq_no)
	{
		this.SEQ_NO = byte_format(seq_no, 8);
	}

	public String getRead_flag()
	{
		return READ_FLAG.trim();
	}

	public void setRead_flag(String read_flag)
	{
		this.READ_FLAG = byte_format(read_flag, 1);
	}

	public int getByte_length()
	{
		return byte_length;
	}

	public void setByte_length(int byte_length)
	{
		this.byte_length = byte_length;
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

	public String getApClass()
	{
		return ApClass.trim();
	}

	public void setApClass(String apClass)
	{
		ApClass = byte_format(apClass, 1);
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

	public String getTransDate()
	{
		return TransDate.trim();
	}

	public void setTransDate(String transDate)
	{
		TransDate = byte_format(transDate, 10);
	}

	public String getTransTime()
	{
		return TransTime.trim();
	}

	public void setTransTime(String transTime)
	{
		TransTime = byte_format(transTime, 8);
	}

	public String getCardType1()
	{
		return CardType1.trim();
	}

	public void setCardType1(String cardType1)
	{
		CardType1 = byte_format(cardType1, 50);
	}

	public String getCardType2()
	{
		return CardType2.trim();
	}

	public void setCardType2(String cardType2)
	{
		CardType2 = byte_format(cardType2, 1);
	}

	public String getCurrCode()
	{
		return CurrCode.trim();
	}

	public void setCurrCode(String currCode)
	{
		CurrCode = byte_format(currCode, 3);
	}

	public double getApprAmt()
	{
		return ApprAmt;
	}

	public void setApprAmt(double apprAmt)
	{
		ApprAmt = apprAmt;		
	}

	public double getVAT()
	{
		return VAT;
	}

	public void setVAT(double vAT)
	{
		VAT = vAT;
	}

	public double getTips()
	{
		return Tips;
	}

	public void setTips(double tips)
	{
		Tips = tips;
	}

	public double getApprTot()
	{
		return ApprTot;
	}

	public void setApprTot(double apprTot)
	{
		ApprTot = apprTot;
	}

	public double getApprExch()
	{
		return ApprExch;
	}

	public void setApprExch(double apprExch)
	{
		ApprExch = apprExch;
	}

	public double getUSDApprTot()
	{
		return USDApprTot;
	}

	public void setUSDApprTot(double uSDApprTot)
	{
		USDApprTot = uSDApprTot;
	}

	public String getInstType()
	{
		return InstType.trim();
	}

	public void setInstType(String instType)
	{
		InstType = byte_format(instType, 1);
	}

	public int getInstMonth()
	{
		return InstMonth;
	}

	public void setInstMonth(int instMonth)
	{
		InstMonth = instMonth;
	}

	public String getAbroad()
	{
		return Abroad.trim();
	}

	public void setAbroad(String abroad)
	{
		Abroad = byte_format(abroad, 1);
	}

	public String getSlipNo()
	{
		return SlipNo.trim();
	}

	public void setSlipNo(String slipNo)
	{
		SlipNo = byte_format(slipNo, 12);
	}

	public String getTerminalNo()
	{
		return TerminalNo.trim();
	}

	public void setTerminalNo(String terminalNo)
	{
		TerminalNo = byte_format(terminalNo, 12);
	}

	public String getPurch()
	{
		return Purch.trim();
	}

	public void setPurch(String purch)
	{
		Purch = byte_format(purch, 1);
	}

	public String getAcqulssuer()
	{
		return Acqulssuer.trim();
	}

	public void setAcqulssuer(String acqulssuer)
	{
		Acqulssuer = byte_format(acqulssuer, 50);
	}

	public String getMerchBizNo()
	{
		return MerchBizNo.trim();
	}

	public void setMerchBizNo(String merchBizNo)
	{
		MerchBizNo = byte_format(merchBizNo, 10);
	}

	public String getMerchNo()
	{
		return MerchNo.trim();
	}

	public void setMerchNo(String merchNo)
	{
		MerchNo = byte_format(merchNo, 12);
	}

	public String getMerchName()
	{
		return MerchName.trim();
	}

	public void setMerchName(String merchName)
	{
		MerchName = byte_format(merchName, 50);
	}

	public String getMaster()
	{
		return Master.trim();
	}

	public void setMaster(String master)
	{
		Master = byte_format(master, 50);
	}

	public String getMerchTel()
	{
		return MerchTel.trim();
	}

	public void setMerchTel(String merchTel)
	{
		MerchTel = byte_format(merchTel, 14);
	}

	public String getMerchZipCode()
	{
		return MerchZipCode.trim();
	}

	public void setMerchZipCode(String merchZipCode)
	{
		MerchZipCode = byte_format(merchZipCode, 6);
	}

	public String getMerchAddr1()
	{
		return MerchAddr1.trim();
	}

	public void setMerchAddr1(String merchAddr1)
	{
		MerchAddr1 = byte_format(merchAddr1, 100);
	}

	public String getMerchAddr2()
	{
		return MerchAddr2.trim();
	}

	public void setMerchAddr2(String merchAddr2)
	{
		MerchAddr2 = byte_format(merchAddr2, 100);
	}

	public String getMerchStatus()
	{
		return MerchStatus.trim();
	}

	public void setMerchStatus(String merchStatus)
	{
		MerchStatus = byte_format(merchStatus, 1);
	}

	public String getMCCName()
	{
		return MCCName.trim();
	}

	public void setMCCName(String mCCName)
	{
		MCCName = byte_format(mCCName, 50);
	}

	public String getMCCCode()
	{
		return MCCCode.trim();
	}

	public void setMCCCode(String mCCCode)
	{
		MCCCode = byte_format(mCCCode, 4);
	}

	public String getPartApprCancYN()
	{
		return PartApprCancYN.trim();
	}

	public void setPartApprCancYN(String partApprCancYN)
	{
		PartApprCancYN = byte_format(partApprCancYN, 1);
	}

	public String getServTypeYN()
	{
		return ServTypeYN.trim();
	}

	public void setServTypeYN(String servTypeYN)
	{
		ServTypeYN = byte_format(servTypeYN, 1);
	}

	public String getCollNo()
	{
		return CollNo.trim();
	}

	public void setCollNo(String collNo)
	{
		CollNo = byte_format(collNo, 32);
	}

	public String getTaxType()
	{
		return TaxType.trim();
	}

	public void setTaxType(String taxType)
	{
		TaxType = byte_format(taxType, 20);
	}

	public String getMerchCessDate()
	{
		return MerchCessDate.trim();
	}

	public void setMerchCessDate(String merchCessDate)
	{
		MerchCessDate = byte_format(merchCessDate, 10);
	}

	public String getOrginTransDate()
	{
		return OrginTransDate.trim();
	}

	public void setOrginTransDate(String orginTransDate)
	{
		OrginTransDate = byte_format(orginTransDate, 10);
	}

	public String getOrginApprNo()
	{
		return OrginApprNo.trim();
	}

	public void setOrginApprNo(String orginApprNo)
	{
		OrginApprNo = byte_format(orginApprNo, 12);
	}

	public String getOrginTransTime()
	{
		return OrginTransTime.trim();
	}

	public void setOrginTransTime(String orginTransTime)
	{
		OrginTransTime = byte_format(orginTransTime, 8);
	}

	public String getOrginMerchName()
	{
		return OrginMerchName.trim();
	}

	public void setOrginMerchName(String orginMerchName)
	{
		OrginMerchName = byte_format(orginMerchName, 50);
	}

	public String getOrginMerchBizNo()
	{
		return OrginMerchBizNo.trim();
	}

	public void setOrginMerchBizNo(String orginMerchBizNo)
	{
		OrginMerchBizNo = byte_format(orginMerchBizNo, 10);
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
