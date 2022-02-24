package com.hyphen.fbnk.bbnk.define;

import java.util.Arrays;
import java.util.List;

public enum RtnCode {
    FINE                ("00"),
    ERR_FILE_CREATE     ("01"),
    ERR_FILE_WRITE      ("02"),
    NO_DATA             ("84"),
    ERR_ABNORMAL_MSG    ("88"),
    ERR_ETC             ("99"),
    DEFAULT             ("");

    private final String code;
    public String getCode() {return code;}

    RtnCode(String code) {
        this.code = code;
    }

    private static final List<RtnCode> RTN_CODE_LIST = Arrays.asList(RtnCode.values());
    public static RtnCode fromCode(String code){return RTN_CODE_LIST.stream().filter(rtnCode -> rtnCode.getCode().equals(code.trim())).findFirst().get();}
}
