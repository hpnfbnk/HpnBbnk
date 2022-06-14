package com.hyphen.fbnk.bbnk.dto;

import java.io.UnsupportedEncodingException;

public class DtoDRecC04 {
	private boolean isNormal;
	private int byte_length 					= 0;
	private String AbnormalMsg	= null;

	private String DATA_CODE	= null;	//001,	001
	private String COMPANY_ID	= null;	//020,	021
	private String SEND_DATE	= null;	//010,	031
	private String SEQ_NO		= null;	//008,	039
	private String READ_FLAG	= null;	//001,	040

	private String ApClass		= null;	//001, 041
	private String CorpName		= null;
	private String CorpBizNo	= null;
	private String CorpNo		= null;
	private String EstbNo		= null;
	private String CustNo		= null;
	private String CardBrand	= null;
	private String LocalBrand	= null;
	private String CardType1	= null;
	private String CardType2	= null;
	private String CardType3	= null;
	private String CardType4	= null;
	private String CoopCode		= null;
	private String CoopName		= null;
	private String CardNo		= null;
	private String KorName		= null;
	private String EngName		= null;
	private String RegisNo		= null;
	private String Validity		= null;
	private String PostCode		= null;
	private String PostName		= null;
	private String MembZipcode	= null;
	private String MembAddr1	= null;
	private String MembAddr2	= null;
	private String SettType		= null;
	private String SettDate		= null;
	private String SettBankCode	= null;
	private String SettAcctNo	= null;
	private String MembTel		= null;
	private String NoticeType	= null;
	private String IssueDate	= null;
	private String TmntDate		= null;
	private String PauseDate	= null;
	private String BeforeCard	= null;
	private String OrgnName		= null;
	private String OrgnCode		= null;
	private String EstbName		= null;
	private String UserNo		= null;
	private String UserName		= null;
	private String CardIni		= null;

	//4_Get
	public DtoDRecC04(byte[] dataRec) {
		try {
			byte_length = dataRec.length;
			setNormal(true);
			if(byte_length < 40) {
				setNormal(false);
				setAbnormalMsg("<Error~!!> string length is too short("+byte_length+"byte)");
				return;
			}

			DATA_CODE		= new String(dataRec, 0, 1, "EUC-KR");
			COMPANY_ID		= new String(dataRec, 1, 20, "EUC-KR");
			SEND_DATE		= new String(dataRec, 21, 10, "EUC-KR");
			SEQ_NO			= new String(dataRec, 31, 8, "EUC-KR");
			READ_FLAG		= new String(dataRec, 39, 1, "EUC-KR");
			ApClass			= new String(dataRec, 40, 1, "EUC-KR");
			CorpName		= new String(dataRec, 41, 50, "EUC-KR");
			CorpBizNo		= new String(dataRec, 91, 10, "EUC-KR");
			CorpNo			= new String(dataRec, 101, 14, "EUC-KR");
			EstbNo			= new String(dataRec, 115, 10, "EUC-KR");
			CustNo			= new String(dataRec, 125, 15, "EUC-KR");
			CardBrand		= new String(dataRec, 140, 1, "EUC-KR");
			LocalBrand		= new String(dataRec, 141, 1, "EUC-KR");
			CardType1		= new String(dataRec, 142, 1, "EUC-KR");
			CardType2		= new String(dataRec, 143, 1, "EUC-KR");
			CardType3		= new String(dataRec, 144, 1, "EUC-KR");
			CardType4		= new String(dataRec, 145, 1, "EUC-KR");
			CoopCode		= new String(dataRec, 146, 6, "EUC-KR");
			CoopName		= new String(dataRec, 152, 50, "EUC-KR");
			CardNo			= new String(dataRec, 202, 16, "EUC-KR");
			KorName			= new String(dataRec, 218, 50, "EUC-KR");
			EngName			= new String(dataRec, 268, 50, "EUC-KR");
			RegisNo			= new String(dataRec, 318, 13, "EUC-KR");
			Validity		= new String(dataRec, 331, 7, "EUC-KR");
			PostCode		= new String(dataRec, 338, 20, "EUC-KR");
			PostName		= new String(dataRec, 358, 50, "EUC-KR");
			MembZipcode		= new String(dataRec, 408, 6, "EUC-KR");
			MembAddr1		= new String(dataRec, 414, 100, "EUC-KR");
			MembAddr2		= new String(dataRec, 514, 100, "EUC-KR");
			SettType		= new String(dataRec, 614, 1, "EUC-KR");
			SettDate		= new String(dataRec, 615, 2, "EUC-KR");
			SettBankCode	= new String(dataRec, 617, 10, "EUC-KR");
			SettAcctNo		= new String(dataRec, 627, 20, "EUC-KR");
			MembTel			= new String(dataRec, 647, 14, "EUC-KR");
			NoticeType		= new String(dataRec, 661, 1, "EUC-KR");
			IssueDate		= new String(dataRec, 662, 10, "EUC-KR");
			TmntDate		= new String(dataRec, 672, 10, "EUC-KR");
			PauseDate		= new String(dataRec, 682, 10, "EUC-KR");
			BeforeCard		= new String(dataRec, 692, 16, "EUC-KR");
			OrgnName		= new String(dataRec, 708, 50, "EUC-KR");
			OrgnCode		= new String(dataRec, 758, 20, "EUC-KR");
			EstbName		= new String(dataRec, 778, 50, "EUC-KR");
			UserNo			= new String(dataRec, 828, 30, "EUC-KR");
			UserName		= new String(dataRec, 858, 50, "EUC-KR");
			CardIni			= new String(dataRec, 908, 2, "EUC-KR");
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
	public DtoDRecC04() {
		byte_length		= 0;
		isNormal		= true;
		AbnormalMsg		= "";

		DATA_CODE		= String.format("%-1s", " ");
		COMPANY_ID		= String.format("%-20s", " ");
		SEND_DATE		= String.format("%-10s", " ");
		SEQ_NO			= String.format("%-8s", " ");
		READ_FLAG		= String.format("%-1s", " ");
		ApClass			= String.format("%-1s", " ");
		
		CorpName		= String.format("%-50s", " ");
		CorpBizNo		= String.format("%-10s", " ");
		CorpNo			= String.format("%-14s", " ");
		EstbNo			= String.format("%-10s", " ");
		CustNo			= String.format("%-15s", " ");
		CardBrand		= String.format("%-1s", " ");
		LocalBrand		= String.format("%-1s", " ");
		CardType1		= String.format("%-1s", " ");
		CardType2		= String.format("%-1s", " ");
		CardType3		= String.format("%-1s", " ");
		CardType4		= String.format("%-1s", " ");
		CoopCode		= String.format("%-6s", " ");
		CoopName		= String.format("%-50s", " ");
		CardNo			= String.format("%-16s", " ");
		KorName			= String.format("%-50s", " ");
		EngName			= String.format("%-50s", " ");
		RegisNo			= String.format("%-13s", " ");
		Validity		= String.format("%-7s", " ");
		PostCode		= String.format("%-20s", " ");
		PostName		= String.format("%-50s", " ");
		MembZipcode		= String.format("%-6s", " ");
		MembAddr1		= String.format("%-100s", " ");
		MembAddr2		= String.format("%-100s", " ");
		SettType		= String.format("%-1s", " ");
		SettDate		= String.format("%-2s", " ");
		SettBankCode	= String.format("%-10s", " ");
		SettAcctNo		= String.format("%-20s", " ");
		MembTel			= String.format("%-14s", " ");
		NoticeType		= String.format("%-1s", " ");
		IssueDate		= String.format("%-10s", " ");
		TmntDate		= String.format("%-10s", " ");
		PauseDate		= String.format("%-10s", " ");
		BeforeCard		= String.format("%-16s", " ");
		OrgnName		= String.format("%-50s", " ");
		OrgnCode		= String.format("%-20s", " ");
		EstbName		= String.format("%-50s", " ");
		UserNo			= String.format("%-30s", " ");
		UserName		= String.format("%-50s", " ");
		CardIni			= String.format("%-2s", " ");
	}

	public String toStrBuf() {
		StringBuffer str_buf = new StringBuffer();
		str_buf.append(DATA_CODE).append(COMPANY_ID).append(SEND_DATE).append(SEQ_NO).append(READ_FLAG);
		str_buf.append(ApClass).append(CorpName).append(CorpBizNo).append(CorpNo).append(EstbNo).append(CustNo).append(CardBrand).append(LocalBrand);
		str_buf.append(CardType1).append(CardType2).append(CardType3).append(CardType4).append(CoopCode).append(CoopName).append(CardNo).append(KorName);
		str_buf.append(EngName).append(RegisNo).append(Validity).append(PostCode).append(PostName).append(MembZipcode).append(MembAddr1).append(MembAddr2);
		str_buf.append(SettType).append(SettDate).append(SettBankCode).append(SettAcctNo).append(MembTel).append(NoticeType).append(IssueDate).append(TmntDate);
		str_buf.append(PauseDate).append(BeforeCard).append(OrgnName).append(OrgnCode).append(EstbName).append(UserNo).append(UserName).append(CardIni);
		
		return str_buf.toString();
	}	
		
	public String byte_format(String str, int len) {
		String rt_val = null;
		try {
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

	public String getSEND_DT()
	{
		return SEND_DATE.trim();
	}

	public void setSEND_DT(String sEND_DT)
	{
		SEND_DATE = byte_format(sEND_DT, 10);
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

	public String getCorpName()
	{
		return CorpName.trim();
	}

	public void setCorpName(String corpName)
	{
		CorpName = byte_format(corpName, 50);
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

	public String getCustNo()
	{
		return CustNo.trim();
	}

	public void setCustNo(String custNo)
	{
		CustNo = byte_format(custNo, 15);
	}

	public String getCardBrand()
	{
		return CardBrand.trim();
	}

	public void setCardBrand(String cardBrand)
	{
		CardBrand = byte_format(cardBrand, 1);
	}

	public String getLocalBrand()
	{
		return LocalBrand.trim();
	}

	public void setLocalBrand(String localBrand)
	{
		LocalBrand = byte_format(localBrand, 1);
	}

	public String getCardType1()
	{
		return CardType1.trim();
	}

	public void setCardType1(String cardType1)
	{
		CardType1 = byte_format(cardType1, 1);
	}

	public String getCardType2()
	{
		return CardType2.trim();
	}

	public void setCardType2(String cardType2)
	{
		CardType2 = byte_format(cardType2, 1);
	}

	public String getCardType3()
	{
		return CardType3.trim();
	}

	public void setCardType3(String cardType3)
	{
		CardType3 = byte_format(cardType3, 1);
	}

	public String getCardType4()
	{
		return CardType4.trim();
	}

	public void setCardType4(String cardType4)
	{
		CardType4 = byte_format(cardType4, 1);
	}

	public String getCoopCode()
	{
		return CoopCode.trim();
	}

	public void setCoopCode(String coopCode)
	{
		CoopCode = byte_format(coopCode, 6);
	}

	public String getCoopName()
	{
		return CoopName.trim();
	}

	public void setCoopName(String coopName)
	{
		CoopName = byte_format(coopName, 50);
	}

	public String getCardNo()
	{
		return CardNo.trim();
	}

	public void setCardNo(String cardNo)
	{
		CardNo = byte_format(cardNo, 16);
	}

	public String getKorName()
	{
		return KorName.trim();
	}

	public void setKorName(String korName)
	{
		KorName = byte_format(korName, 50);
	}

	public String getEngName()
	{
		return EngName.trim();
	}

	public void setEngName(String engName)
	{
		EngName = byte_format(engName, 50);
	}

	public String getRegisNo()
	{
		return RegisNo.trim();
	}

	public void setRegisNo(String regisNo)
	{
		RegisNo = byte_format(regisNo, 13);
	}

	public String getValidity()
	{
		return Validity.trim();
	}

	public void setValidity(String validity)
	{
		Validity = byte_format(validity, 7);
	}

	public String getPostCode()
	{
		return PostCode.trim();
	}

	public void setPostCode(String postCode)
	{
		PostCode = byte_format(postCode, 20);
	}

	public String getPostName()
	{
		return PostName.trim();
	}

	public void setPostName(String postName)
	{
		PostName = byte_format(postName, 50);
	}

	public String getMembZipcode()
	{
		return MembZipcode.trim();
	}

	public void setMembZipcode(String membZipcode)
	{
		MembZipcode = byte_format(membZipcode, 6);
	}

	public String getMembAddr1()
	{
		return MembAddr1.trim();
	}

	public void setMembAddr1(String membAddr1)
	{
		MembAddr1 = byte_format(membAddr1, 100);
	}

	public String getMembAddr2()
	{
		return MembAddr2.trim();
	}

	public void setMembAddr2(String membAddr2)
	{
		MembAddr2 = byte_format(membAddr2, 100);
	}

	public String getSettType()
	{
		return SettType.trim();
	}

	public void setSettType(String settType)
	{
		SettType = byte_format(settType, 1);
	}

	public String getSettDate()
	{
		return SettDate.trim();
	}

	public void setSettDate(String settDate)
	{
		SettDate = byte_format(settDate, 2);
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

	public String getMembTel()
	{
		return MembTel.trim();
	}

	public void setMembTel(String membTel)
	{
		MembTel = byte_format(membTel, 14);
	}

	public String getNoticeType()
	{
		return NoticeType.trim();
	}

	public void setNoticeType(String noticeType)
	{
		NoticeType = byte_format(noticeType, 1);
	}

	public String getIssueDate()
	{
		return IssueDate.trim();
	}

	public void setIssueDate(String issueDate)
	{
		IssueDate = byte_format(issueDate, 10);
	}

	public String getTmntDate()
	{
		return TmntDate.trim();
	}

	public void setTmntDate(String tmntDate)
	{
		TmntDate = byte_format(tmntDate, 10);
	}

	public String getPauseDate()
	{
		return PauseDate.trim();
	}

	public void setPauseDate(String pauseDate)
	{
		PauseDate = byte_format(pauseDate, 10);
	}

	public String getBeforeCard()
	{
		return BeforeCard.trim();
	}

	public void setBeforeCard(String beforeCard)
	{
		BeforeCard = byte_format(beforeCard, 16);
	}

	public String getOrgnName()
	{
		return OrgnName.trim();
	}

	public void setOrgnName(String orgnName)
	{
		OrgnName = byte_format(orgnName, 50);
	}

	public String getOrgnCode()
	{
		return OrgnCode.trim();
	}

	public void setOrgnCode(String orgnCode)
	{
		OrgnCode = byte_format(orgnCode, 20);
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

	public String getCardIni()
	{
		return CardIni.trim();
	}

	public void setCardIni(String cardIni)
	{
		CardIni = byte_format(cardIni, 2);
	}
	
}
