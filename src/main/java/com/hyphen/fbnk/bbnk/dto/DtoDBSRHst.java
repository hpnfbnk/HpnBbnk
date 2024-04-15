package com.hyphen.fbnk.bbnk.dto;

import com.hyphen.fbnk.bbnk.Util;

public class DtoDBSRHst {
    private String SEND_DATE;   //송수신일자 char(8)
    private String SEND_TIME;   //송수신시간 char(8)
    private String BT_INFO_CODE;  //파일종류 char(3)
    private String SEND_CODE;   //송신자코드 char(4)
    private String RECV_CODE;   //수신지코드 char(4)
    private String SEQ_NUMB;    //순번 char(3)
    private String FILE_NAME;   //파일명 varchar(40)
    private String SR_TP;     //송수신구분(사용자입장 S:송신, R:수신) char(1)
    private String SUCCESS_FLAG = "N";  //송수신성공여부(Y/N) char(1)
    private String ADD_INFO1 = "";   //추가정보1 varchar(20)
    private String ADD_INFO2 = "";   //추가정보2 varchar(20)
    private String ADD_INFO3 = "";   //추가정보3 varchar(40)
    private Long ADD_INFO4 = 0L;     //추가정보4 decimal(16,0)
    private Long ADD_INFO5 = 0L;     //추가정보5 decimal(20,0)


    public DtoDBSRHst(String BT_INFO_CD, String SEND_CODE, String RECV_CODE, String SEQ_NUMB, String FILE_NAME, String SR_TP, boolean retYn) {
        this.SEND_DATE = Util.getCurDtTm().substring(0, 8);
        this.SEND_TIME = Util.getCurDtTm().substring(8);
        this.BT_INFO_CODE = BT_INFO_CD;
        this.SEND_CODE = SEND_CODE;
        this.RECV_CODE = RECV_CODE;
        this.SEQ_NUMB = SEQ_NUMB;
        this.FILE_NAME = FILE_NAME;
        this.SR_TP = SR_TP;
        if(retYn) this.SUCCESS_FLAG = "Y";
    }

    public String getSEND_DATE() {
        return SEND_DATE;
    }

    public void setSEND_DATE(String SEND_DATE) {
        this.SEND_DATE = SEND_DATE;
    }

    public String getSEND_TIME() {
        return SEND_TIME;
    }

    public void setSEND_TIME(String SEND_TIME) {
        this.SEND_TIME = SEND_TIME;
    }

    public String getBT_INFO_CODE() {
        return BT_INFO_CODE;
    }

    public void setBT_INFO_CD(String BT_INFO_CODE) {
        this.BT_INFO_CODE = BT_INFO_CODE;
    }

    public String getSEND_CODE() {
        return SEND_CODE;
    }

    public void setSEND_CODE(String SEND_CODE) {
        this.SEND_CODE = SEND_CODE;
    }

    public String getRECV_CODE() {
        return RECV_CODE;
    }

    public void setRECV_CODE(String RECV_CODE) {
        this.RECV_CODE = RECV_CODE;
    }

    public String getSEQ_NUMB() {
        return SEQ_NUMB;
    }

    public void setSEQ_NUMB(String SEQ_NUMB) {
        this.SEQ_NUMB = SEQ_NUMB;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public String getSR_TP() {
        return SR_TP;
    }

    public void setSR_TYPE(String SR_TP) {
        this.SR_TP = SR_TP;
    }

    public String getSUCCESS_FLAG() {
        return SUCCESS_FLAG;
    }

    public void setSUCCESS_FLAG(String SUCCESS_FLAG) {
        this.SUCCESS_FLAG = SUCCESS_FLAG;
    }

    public String getADD_INFO1() {
        return ADD_INFO1;
    }

    public void setADD_INFO1(String ADD_INFO1) {
        this.ADD_INFO1 = ADD_INFO1;
    }

    public String getADD_INFO2() {
        return ADD_INFO2;
    }

    public void setADD_INFO2(String ADD_INFO2) {
        this.ADD_INFO2 = ADD_INFO2;
    }

    public String getADD_INFO3() {
        return ADD_INFO3;
    }

    public void setADD_INFO3(String ADD_INFO3) {
        this.ADD_INFO3 = ADD_INFO3;
    }

    public Long getADD_INFO4() {
        return ADD_INFO4;
    }

    public void setADD_INFO4(Long ADD_INFO4) {
        this.ADD_INFO4 = ADD_INFO4;
    }

    public Long getADD_INFO5() {
        return ADD_INFO5;
    }

    public void setADD_INFO5(Long ADD_INFO5) {
        this.ADD_INFO5 = ADD_INFO5;
    }

    @Override
    public String toString() {
        return "DtoDBSRHst{" +
                "SEND_DATE='" + SEND_DATE + '\'' +
                ", SEND_TIME='" + SEND_TIME + '\'' +
                ", BT_INFO_CODE='" + BT_INFO_CODE + '\'' +
                ", SEND_CODE='" + SEND_CODE + '\'' +
                ", RECV_CODE='" + RECV_CODE + '\'' +
                ", SEQ_NUMB='" + SEQ_NUMB + '\'' +
                ", FILE_NAME='" + FILE_NAME + '\'' +
                ", SR_TP='" + SR_TP + '\'' +
                ", SUCCESS_FLAG='" + SUCCESS_FLAG + '\'' +
                ", ADD_INFO1='" + ADD_INFO1 + '\'' +
                ", ADD_INFO2='" + ADD_INFO2 + '\'' +
                ", ADD_INFO3='" + ADD_INFO3 + '\'' +
                ", ADD_INFO4=" + ADD_INFO4 +
                ", ADD_INFO5=" + ADD_INFO5 +
                '}';
    }
}
