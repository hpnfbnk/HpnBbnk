package com.hyphen.fbnk.bbnk.define;

import java.util.Arrays;
import java.util.List;

public enum RtnCode {
    FINE                ("00"),
    ERR_FILE_CREATE     ("01"),
    ERR_FILE_WRITE      ("02"),
    ERR_RECV_CNT        ("03"),
    ERR_FILE_OPEN       ("04"),
    ERR_GET_FILENAME    ("05"),
    ERR_INSERT_DB       ("06"),
    ERR_FILE_READ       ("07"),
    ERR_REQDATE_SMALL   ("08"),
    ERR_RECV_SIZE       ("09"),
    ERR_DB_EXECUTE      ("82"),
    ERR_ESSENT_FIELD    ("83"),
    NO_DATA             ("84"),
    ERR_USER_CERT       ("85"),
    ERR_DATA_HEADER     ("86"),
    ERR_UNREGIST_SHOP   ("87"),
    ERR_ABNORMAL_MSG    ("88"),
    ERR_EB_OPEN         ("89"),
    ERR_PASSWD          ("91"),
    ERR_ETC             ("99"),
    DEFAULT             ("XX");

    private final String code;
    public String getCode() {return code;}
    RtnCode(String code) {this.code = code;}
    private static final List<RtnCode> RTN_CODE_LIST = Arrays.asList(RtnCode.values());
    public static RtnCode fromCode(String code){
        for (RtnCode rtnCode : RTN_CODE_LIST) {
            if (rtnCode.getCode().equals(code.trim())) {
                return rtnCode;
            }
        }
        //RtnCode empty = null;
        //throw new java.util.NoSuchElementException("No value present");
        return RtnCode.DEFAULT;
    }
}
