package com.hyphen.fbnk.bbnk.dto;

import java.io.UnsupportedEncodingException;

public class DtoDRecC02 {
	private boolean isNormal;
	private int byte_length 		= 0;
	private String AbnormalMsg		= null;
	
	private String DATA_CODE		= null;	//001,	001
	private String COMPANY_ID		= null;	//020,	021
	private String SEND_DT			= null;	//010,	031
	private String SEQ_NO			= null;	//008,	039
	private String READ_FLAG		= null;	//001,	040

	private String ApClass			= null;	//001, 041
	private String CardNo			= null;	//016, 057
	private String ApprNo			= null;	//012, 069
	private String CollNo			= null;	//032, 101
	private String SlipNo			= null;	//012, 113
	private String ApprDate			= null;	//010, 123
	private String PurchDate		= null;	//010, 133
	private String PurchTime		= null;	//008, 141
	private String CurrCode			= null;	//003, 144
	private double ApprTot			= 0D;	//017, 161
	private double AcquTot			= 0D;	//017, 178
	private double AcquExch			= 0D;	//017, 195
	private double USDAcquTot		= 0D;	//017, 212
	private double ConvFee			= 0D;	//017, 229
	private double PurchTot			= 0D;	//017, 246
	private String InstType			= null;	//001, 247
	private String SettDate			= null;	//010, 257
	private String Abroad			= null;	//001, 258
	private String PurchYN			= null;	//001, 259
	private String MerchBizNo		= null;	//010, 269
	private String MerchNo			= null;	//012, 281
	private String MerchName		= null;	//050, 331
	private String Master			= null;	//050, 381
	private String MerchTel			= null;	//014, 395
	private String MerchZipcode		= null;	//006, 401
	private String MerchAddr1		= null;	//100, 501
	private String MerchAddr2		= null;	//100, 601
	private String MccName			= null;	//050, 651
	private String MccCode			= null;	//004, 655
	private String PartAcquCancYN	= null;	//001, 656
	private String NoneApprYN		= null;	//001, 657
	private String ServTypeYN		= null;	//001, 658
	private String OrginCollNo		= null;	//032, 690
	private double DiscAmt			= 0D;	//017, 707
	private double CurrAcquTot		= 0D;	//017, 724
	private double AcquFee			= 0D;	//017, 741
	private double ApprAmt			= 0D;	//017, 758
	private double VAT				= 0D;	//017, 775
	private double Tips				= 0D;	//017, 792
	private String OriginMerchName	= null;	//050, 842
	private String OriginMerchBizNo	= null;	//010, 852
	private String taxtype			= null;	//004, 856
	private String MerchCessDate	= null;	//008, 864
	private String ntsdate			= null;	//008, 872
	private String CardIni			= null;	//002, 874
	
	//4_Get
	public DtoDRecC02(byte[] dataRec)
	{		
		try
		{
			byte_length = dataRec.length;
			isNormal = true;
			if(byte_length < 40)
			{
				isNormal = false;
				AbnormalMsg = "<Error~!!> string length is too short("+byte_length+"byte)";
				return;
			}

			setDATA_CODE(new String(dataRec, 0, 1, "EUC-KR"));
			setCOMPANY_ID(new String(dataRec, 1, 20, "EUC-KR"));
			setSEND_DT(new String(dataRec, 21, 10, "EUC-KR"));
			setSEQ_NO(new String(dataRec, 31, 8, "EUC-KR"));
			setREAD_FLAG(new String(dataRec, 39, 1, "EUC-KR"));
			setApClass(new String(dataRec, 40, 1, "EUC-KR"));
			setCardNo(new String(dataRec, 41, 16, "EUC-KR"));
			setApprNo(new String(dataRec, 57, 12, "EUC-KR"));
			setCollNo(new String(dataRec, 69, 32, "EUC-KR"));
			setSlipNo(new String(dataRec, 101, 12, "EUC-KR"));
			setApprDate(new String(dataRec, 113, 10, "EUC-KR"));
			setPurchDate(new String(dataRec, 123, 10, "EUC-KR"));
			setPurchTime(new String(dataRec, 133, 8, "EUC-KR"));
			setCurrCode(new String(dataRec, 141, 3, "EUC-KR"));
			setApprTot(bgetDouble(new String(dataRec, 144, 17, "EUC-KR")));
			setAcquTot(bgetDouble(new String(dataRec, 161, 17, "EUC-KR")));
			setAcquExch(bgetDouble(new String(dataRec, 178, 17, "EUC-KR")));
			setUSDAcquTot(bgetDouble(new String(dataRec, 195, 17, "EUC-KR")));
			setConvFee(bgetDouble(new String(dataRec, 212, 17, "EUC-KR")));
			setPurchTot(bgetDouble(new String(dataRec, 229, 17, "EUC-KR")));
			setInstType(new String(dataRec, 246, 1, "EUC-KR"));
			setSettDate(new String(dataRec, 247, 10, "EUC-KR"));
			setAbroad(new String(dataRec, 257, 1, "EUC-KR"));
			setPurchYN(new String(dataRec, 258, 1, "EUC-KR"));
			setMerchBizNo(new String(dataRec, 259, 10, "EUC-KR"));
			setMerchNo(new String(dataRec, 269, 12, "EUC-KR"));
			setMerchName(new String(dataRec, 281, 50, "EUC-KR"));
			setMaster(new String(dataRec, 331, 50, "EUC-KR"));
			setMerchTel(new String(dataRec, 381, 14, "EUC-KR"));
			setMerchZipcode(new String(dataRec, 395, 6, "EUC-KR"));
			setMerchAddr1(new String(dataRec, 401, 100, "EUC-KR"));
			setMerchAddr2(new String(dataRec, 501, 100, "EUC-KR"));
			setMccName(new String(dataRec, 601, 50, "EUC-KR"));
			setMccCode(new String(dataRec, 651, 4, "EUC-KR"));
			setPartAcquCancYN(new String(dataRec, 655, 1, "EUC-KR"));
			setNoneApprYN(new String(dataRec, 656, 1, "EUC-KR"));
			setServTypeYN(new String(dataRec, 657, 1, "EUC-KR"));
			setOrginCollNo(new String(dataRec, 658, 32, "EUC-KR"));
			setDiscAmt(bgetDouble(new String(dataRec, 690, 17, "EUC-KR")));
			setCurrAcquTot(bgetDouble(new String(dataRec, 707, 17, "EUC-KR")));
			setAcquFee(bgetDouble(new String(dataRec, 724, 17, "EUC-KR")));
			setApprAmt(bgetDouble(new String(dataRec, 741, 17, "EUC-KR")));
			setVAT(bgetDouble(new String(dataRec, 758, 17, "EUC-KR")));
			setTips(bgetDouble(new String(dataRec, 775, 17, "EUC-KR")));
			setOriginMerchName(new String(dataRec, 792, 50, "EUC-KR"));
			setOriginMerchBizNo(new String(dataRec, 842, 10, "EUC-KR"));
			setTaxtype(new String(dataRec, 852, 4, "EUC-KR"));
			setMerchCessDate(new String(dataRec, 856, 8, "EUC-KR"));
			setNtsdate(new String(dataRec, 864, 8, "EUC-KR"));
			setCardIni(new String(dataRec, 872, 2, "EUC-KR"));
		}
		catch(NumberFormatException e)
		{
			setNormal(false);
			setAbnormalMsg(e.toString());
		}
		catch(IndexOutOfBoundsException e)
		{
			isNormal = false;
			AbnormalMsg = e.toString();
		}
		catch(UnsupportedEncodingException e)
		{
			isNormal = false;
			AbnormalMsg = e.toString();
		}
	}
	
	//4_Put
	public DtoDRecC02()
	{
		byte_length		= 0;
		setNormal(true);
		setAbnormalMsg("");

		setDATA_CODE(String.format("%-1s", " "));
		setCOMPANY_ID(String.format("%-20s", " "));
		setSEND_DT(String.format("%-10s", " "));
		setSEQ_NO(String.format("%-8s", " "));
		setREAD_FLAG(String.format("%-1s", " "));
		setApClass(String.format("%-1s", " "));
		setCardNo(String.format("%-16s", " "));
		setApprNo(String.format("%-12s", " "));		
		setCollNo(String.format("%-32s", " "));
		setSlipNo(String.format("%-12s", " "));
		setApprDate(String.format("%-10s", " "));
		setPurchDate(String.format("%-10s", " "));
		setPurchTime(String.format("%-8s", " "));
		setCurrCode(String.format("%-3s", " "));
		setApprTot(0.000D);
		setAcquTot(0.000D);
		setAcquExch(0.000D);
		setUSDAcquTot(0.000D);
		setConvFee(0.000D);
		setPurchTot(0.000D);
		setInstType(String.format("%-1s", " "));
		setSettDate(String.format("%-10s", " "));
		setAbroad(String.format("%-1s", " "));
		setPurchYN(String.format("%-1s", " "));
		setMerchBizNo(String.format("%-10s", " "));
		setMerchNo(String.format("%-12s", " "));
		setMerchName(String.format("%-50s", " "));
		setMaster(String.format("%-50s", " "));
		setMerchTel(String.format("%-14s", " "));
		setMerchZipcode(String.format("%-6s", " "));
		setMerchAddr1(String.format("%-100s", " "));
		setMerchAddr2(String.format("%-100s", " "));
		setMccName(String.format("%-50s", " "));
		setMccCode(String.format("%-4s", " "));
		setPartAcquCancYN(String.format("%-1s", " "));
		setNoneApprYN(String.format("%-1s", " "));
		setServTypeYN(String.format("%-1s", " "));
		setOrginCollNo(String.format("%-32s", " "));
		setDiscAmt(0.000D);
		setCurrAcquTot(0.000D);
		setAcquFee(0.000D);
		setApprAmt(0.000D);
		setVAT(0.000D);
		setTips(0.000D);
		setOriginMerchName(String.format("%-50s", " "));
		setOriginMerchBizNo(String.format("%-10s", " "));
		setTaxtype(String.format("%-4s", " "));
		setMerchCessDate(String.format("%-8s", " "));
		setNtsdate(String.format("%-8s", " "));
		setCardIni(String.format("%-2s", " "));
	}
	
	public String toStrBuf()
	{
		StringBuffer str_buf = new StringBuffer();
		str_buf.append(DATA_CODE).append(COMPANY_ID).append(SEND_DT).append(SEQ_NO).append(READ_FLAG);
		str_buf.append(ApClass).append(CardNo).append(ApprNo).append(CollNo).append(SlipNo).append(ApprDate).append(PurchDate).append(PurchTime).append(CurrCode);
		str_buf.append(String.format("%017.3f", ApprTot)).append(String.format("%017.3f", AcquTot)).append(String.format("%017.3f", AcquExch)).append(String.format("%017.3f", USDAcquTot)).append(String.format("%017.3f", ConvFee)).append(String.format("%017.3f", PurchTot));
		str_buf.append(InstType).append(SettDate).append(Abroad).append(PurchYN).append(MerchBizNo).append(MerchNo).append(MerchName).append(Master).append(MerchTel).append(MerchZipcode);
		str_buf.append(MerchAddr1).append(MerchAddr2).append(MccName).append(MccCode).append(PartAcquCancYN).append(NoneApprYN).append(ServTypeYN).append(OrginCollNo);
		str_buf.append(String.format("%017.3f", DiscAmt)).append(String.format("%017.3f", CurrAcquTot)).append(String.format("%017.3f", AcquFee)).append(String.format("%017.3f", ApprAmt)).append(String.format("%017.3f", VAT)).append(String.format("%017.3f", Tips));
		str_buf.append(OriginMerchName).append(OriginMerchBizNo).append(taxtype).append(MerchCessDate).append(ntsdate).append(CardIni);
		
		return str_buf.toString();
	}
	
	public String byte_format(String str, int len)
	{
		String rt_val = null;
		try
		{
			if(str.getBytes("EUC-KR").length > len)	rt_val = new String(str.getBytes("EUC-KR"), 0, len, "EUC-KR");
			else 
			{
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

	public String getCollNo()
	{
		return CollNo.trim();
	}

	public void setCollNo(String collNo)
	{
		CollNo = byte_format(collNo, 32);
	}

	public String getSlipNo()
	{
		return SlipNo.trim();
	}

	public void setSlipNo(String slipNo)
	{
		SlipNo = byte_format(slipNo, 12);
	}

	public String getApprDate()
	{
		return ApprDate.trim();
	}

	public void setApprDate(String apprDate)
	{
		ApprDate = byte_format(apprDate, 10);
	}

	public String getPurchDate()
	{
		return PurchDate.trim();
	}

	public void setPurchDate(String purchDate)
	{
		PurchDate = byte_format(purchDate, 10);
	}

	public String getPurchTime()
	{
		return PurchTime.trim();
	}

	public void setPurchTime(String purchTime)
	{
		PurchTime = byte_format(purchTime, 8);
	}

	public String getCurrCode()
	{
		return CurrCode.trim();
	}

	public void setCurrCode(String currCode)
	{
		CurrCode = byte_format(currCode, 3);
	}

	public double getApprTot()
	{
		return ApprTot;
	}

	public void setApprTot(double apprTot)
	{
		ApprTot = apprTot;
	}

	public double getAcquTot()
	{
		return AcquTot;
	}

	public void setAcquTot(double acquTot)
	{
		AcquTot = acquTot;
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

	public double getPurchTot()
	{
		return PurchTot;
	}

	public void setPurchTot(double purchTot)
	{
		PurchTot = purchTot;
	}

	public String getInstType()
	{
		return InstType.trim();
	}

	public void setInstType(String instType)
	{
		InstType = byte_format(instType, 1);
	}

	public String getSettDate()
	{
		return SettDate.trim();
	}

	public void setSettDate(String settDate)
	{
		SettDate = byte_format(settDate, 10);
	}

	public String getAbroad()
	{
		return Abroad.trim();
	}

	public void setAbroad(String abroad)
	{
		Abroad = byte_format(abroad, 1);
	}

	public String getPurchYN()
	{
		return PurchYN.trim();
	}

	public void setPurchYN(String purchYN)
	{
		PurchYN = byte_format(purchYN, 1);
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

	public String getMerchZipcode()
	{
		return MerchZipcode.trim();
	}

	public void setMerchZipcode(String merchZipcode)
	{
		MerchZipcode = byte_format(merchZipcode, 6);
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

	public String getMccName()
	{
		return MccName.trim();
	}

	public void setMccName(String mccName)
	{
		MccName = byte_format(mccName, 50);
	}

	public String getMccCode()
	{
		return MccCode.trim();
	}

	public void setMccCode(String mccCode)
	{
		MccCode = byte_format(mccCode, 4);
	}

	public String getPartAcquCancYN()
	{
		return PartAcquCancYN.trim();
	}

	public void setPartAcquCancYN(String partAcquCancYN)
	{
		PartAcquCancYN = byte_format(partAcquCancYN, 1);
	}

	public String getNoneApprYN()
	{
		return NoneApprYN.trim();
	}

	public void setNoneApprYN(String noneApprYN)
	{
		NoneApprYN = byte_format(noneApprYN, 1);
	}

	public String getServTypeYN()
	{
		return ServTypeYN.trim();
	}

	public void setServTypeYN(String servTypeYN)
	{
		ServTypeYN = byte_format(servTypeYN, 1);
	}

	public String getOrginCollNo()
	{
		return OrginCollNo.trim();
	}

	public void setOrginCollNo(String orginCollNo)
	{
		OrginCollNo = byte_format(orginCollNo, 32);
	}

	public double getDiscAmt()
	{
		return DiscAmt;
	}

	public void setDiscAmt(double discAmt)
	{
		DiscAmt = discAmt;
	}

	public double getCurrAcquTot()
	{
		return CurrAcquTot;
	}

	public void setCurrAcquTot(double currAcquTot)
	{
		CurrAcquTot = currAcquTot;
	}

	public double getAcquFee()
	{
		return AcquFee;
	}

	public void setAcquFee(double acquFee)
	{
		AcquFee = acquFee;
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
}
