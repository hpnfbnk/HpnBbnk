package com.hyphen.fbnk.bbnk.define;

public enum Define {
    CONNECTION_TIMEOUT  ("", 5*1000),
    READ_TIMEOUT        ("", 60*1000),
    MSG_LENGTH          ("", 4),
    HYPHEN              ("HYPHEN", 0);

    private final String code;
    private final int value;

    Define(String code, int value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {return code;}
    public int getValue() {return value;}
}
