package com.hyphen.fbnk.bbnk.dto;

import java.io.UnsupportedEncodingException;

public class DtoDRecC07 {
    private boolean isNormal;
    private int byte_length 		= 0;
    private String AbnormalMsg		= null;

    private String DATA_CODE		= null;	//001,	001
    private String COMPANY_ID		= null;	//020,	021
    private String SEND_DATE		= null;	//010,	031
    private String SEQ_NO			= null;	//008,	039
    private String READ_FLAG		= null;	//001,	040

    private String ApClass			= null;	//1
    private String HighPassSerial   = null; //16
    private String CardNo           = null; //16
    private String TgEntDateTime    = null; //19
    private String TgExtDateTime    = null; //19
    private String TgEntName        = null; //30
    private String TgExtName        = null; //30
    private String CorpNameOfExpr   = null; //40
    private Long OrgTgFee           = 0L;   //18
    private Long DiscTgFee          = 0L;   //18
    private String PostDate         = null; //10
    private String MerchNo          = null; //9
    private String MerBusiNo        = null; //10
    private String Reserve          = null; //74
    private String CrdSaInit        = null; //2

    //4_Get
    public DtoDRecC07(byte[] dataRec) {
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
            HighPassSerial  = new String(dataRec, 41, 16, "EUC-KR"); //16
            CardNo          = new String(dataRec, 57, 16, "EUC-KR"); //16
            TgEntDateTime   = new String(dataRec, 73, 19, "EUC-KR"); //19
            TgExtDateTime   = new String(dataRec, 92, 19, "EUC-KR"); //19
            TgEntName       = new String(dataRec, 111, 30, "EUC-KR"); //30
            TgExtName       = new String(dataRec, 141, 30, "EUC-KR"); //30
            CorpNameOfExpr  = new String(dataRec, 171, 40, "EUC-KR"); //40
            OrgTgFee        = bgetLong(new String(dataRec, 211, 18, "EUC-KR")); //18
            DiscTgFee       = bgetLong(new String(dataRec, 229, 18, "EUC-KR"));   //18
            PostDate        = new String(dataRec, 247, 10, "EUC-KR");   //10
            MerchNo         = new String(dataRec, 257, 9, "EUC-KR");    //9
            MerBusiNo       = new String(dataRec, 266, 10, "EUC-KR");   //10
            Reserve         = new String(dataRec, 276, 74, "EUC-KR");   //74
            CrdSaInit       = new String(dataRec, 350, 2, "EUC-KR");    //2
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
    public DtoDRecC07() {
        byte_length		= 0;
        isNormal		= true;
        AbnormalMsg		= "";

        DATA_CODE		= String.format("%-1s", " ");
        COMPANY_ID		= String.format("%-20s", " ");
        SEND_DATE		= String.format("%-10s", " ");
        SEQ_NO			= String.format("%-8s", " ");
        READ_FLAG		= String.format("%-1s", " ");
        ApClass			= String.format("%-1s", " ");
        HighPassSerial  = String.format("%-16s", " "); //16
        CardNo          = String.format("%-16s", " "); //16
        TgEntDateTime   = String.format("%-19s", " "); //19
        TgExtDateTime   = String.format("%-19s", " "); //19
        TgEntName       = String.format("%-30s", " "); //30
        TgExtName       = String.format("%-30s", " "); //30
        CorpNameOfExpr  = String.format("%-40s", " "); //40
        OrgTgFee        = 0L;
        DiscTgFee       = 0L;
        PostDate        = String.format("%-10s", " ");  //10
        MerchNo         = String.format("%-9s", " ");   //9
        MerBusiNo       = String.format("%-10s", " ");  //10
        Reserve         = String.format("%-74s", " ");  //74
        CrdSaInit       = String.format("%-2s", " ");   //2
    }

    public String toStrBuf() {
        StringBuffer str_buf = new StringBuffer();
        str_buf.append(DATA_CODE).append(COMPANY_ID).append(SEND_DATE).append(SEQ_NO).append(READ_FLAG);
        str_buf.append(ApClass).append(HighPassSerial).append(CardNo).append(TgEntDateTime).append(TgExtDateTime);
        str_buf.append(TgEntName).append(TgExtName).append(CorpNameOfExpr);
        str_buf.append(String.format("%018d", OrgTgFee)).append(String.format("%018d", DiscTgFee));
        str_buf.append(PostDate).append(MerchNo).append(MerBusiNo).append(Reserve).append(CrdSaInit);
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

    public Long bgetLong(String str_val) {
        if(str_val.trim().equals(""))	str_val = "0";
        return Long.parseLong(str_val.trim());
    }

    public boolean isNormal() {return isNormal;}
    public void setNormal(boolean normal) {isNormal = normal;}
    public String getAbnormalMsg() {return AbnormalMsg;}
    public void setAbnormalMsg(String abnormalMsg) {AbnormalMsg = abnormalMsg;}
    public String getDATA_CODE() {return DATA_CODE.trim();}
    public void setDATA_CODE(String DATA_CODE) {this.DATA_CODE = byte_format(DATA_CODE, 1);}
    public String getCOMPANY_ID() {return COMPANY_ID.trim();}
    public void setCOMPANY_ID(String COMPANY_ID) {this.COMPANY_ID = byte_format(COMPANY_ID, 20);}
    public String getSEND_DATE() {return SEND_DATE.trim();}
    public void setSEND_DATE(String SEND_DATE) {this.SEND_DATE = byte_format(SEND_DATE, 10);}
    public String getSEQ_NO() {return SEQ_NO.trim();}
    public void setSEQ_NO(String SEQ_NO) {this.SEQ_NO = byte_format(SEQ_NO, 8);}
    public String getREAD_FLAG() {return READ_FLAG.trim();}
    public void setREAD_FLAG(String READ_FLAG) {this.READ_FLAG = byte_format(READ_FLAG, 1);}
    public String getApClass() {return ApClass.trim();}
    public void setApClass(String apClass) {ApClass = byte_format(apClass, 1);}
    public String getHighPassSerial() {return HighPassSerial.trim();}
    public void setHighPassSerial(String highPassSerial) {HighPassSerial = byte_format(highPassSerial, 16);}
    public String getCardNo() {return CardNo.trim();}
    public void setCardNo(String cardNo) {CardNo = byte_format(cardNo, 16);}
    public String getTgEntDateTime() {return TgEntDateTime.trim();}
    public void setTgEntDateTime(String tgEntDateTime) {TgEntDateTime = byte_format(tgEntDateTime, 19);}
    public String getTgExtDateTime() {return TgExtDateTime.trim();}
    public void setTgExtDateTime(String tgExtDateTime) {TgExtDateTime = byte_format(tgExtDateTime, 19);}
    public String getTgEntName() {return TgEntName.trim();}
    public void setTgEntName(String tgEntName) {TgEntName = byte_format(tgEntName, 30);}
    public String getTgExtName() {return TgExtName.trim();}
    public void setTgExtName(String tgExtName) {TgExtName = byte_format(tgExtName, 30);}
    public String getCorpNameOfExpr() {return CorpNameOfExpr.trim();}
    public void setCorpNameOfExpr(String corpNameOfExpr) {CorpNameOfExpr = byte_format(corpNameOfExpr, 40);}
    public Long getOrgTgFee() {return OrgTgFee;}
    public void setOrgTgFee(Long orgTgFee) {OrgTgFee = orgTgFee;}
    public Long getDiscTgFee() {return DiscTgFee;}
    public void setDiscTgFee(Long discTgFee) {DiscTgFee = discTgFee;}
    public String getPostDate() {return PostDate.trim();}
    public void setPostDate(String postDate) {PostDate = byte_format(postDate, 10);}
    public String getMerchNo() {return MerchNo.trim();}
    public void setMerchNo(String merchNo) {MerchNo = byte_format(merchNo, 9);}
    public String getMerBusiNo() {return MerBusiNo.trim();}
    public void setMerBusiNo(String merBusiNo) {MerBusiNo = byte_format(merBusiNo, 10);}
    public String getReserve() {return Reserve.trim();}
    public void setReserve(String reserve) {Reserve = byte_format(reserve, 74);}
    public String getCrdSaInit() {return CrdSaInit.trim();}
    public void setCrdSaInit(String crdSaInit) {CrdSaInit = byte_format(crdSaInit, 2);}
}
