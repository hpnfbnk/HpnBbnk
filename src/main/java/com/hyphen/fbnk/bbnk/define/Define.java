package com.hyphen.fbnk.bbnk.define;

public enum Define {
    CONNECTION_TIMEOUT  ("", 5*1000, (byte)0x00),
    READ_TIMEOUT        ("", 60*1000, (byte)0x00),
    MSG_LENGTH          ("", 4, (byte)0x00),
    HYPHEN              ("HYPHEN", 0, (byte)0x00),
    C_STX               ("", 0, (byte)0x02),
    C_ETX               ("", 0, (byte)0x03);

    private final String code;
    private final int value;
    private final byte byteValue;

    Define(String code, int value, byte byteValue) {
        this.code       = code;
        this.value      = value;
        this.byteValue  = byteValue;
    }

    public String getCode() {return code;}
    public int getValue() {return value;}
    public byte getByteValue() {return byteValue;}
}
