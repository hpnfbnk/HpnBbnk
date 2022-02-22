package com.hyphen.fbnk.bbnk.define;

public enum RtnCode {
    FINE                ("00"),
    ERR_FILE_CREATE     ("01"),
    ERR_FILE_WRITE      ("02"),
    NO_DATA             ("84"),
    ERR_ABNORMAL_MSG    ("88"),
    ERR_ETC             ("99");

    private final String code;

    RtnCode(String code) {
        this.code = code;
    }

    public String getCode() {return code;}
}
