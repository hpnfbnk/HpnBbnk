package com.hyphen.fbnk.bbnk.define;

public enum EncTp {
    SEED    ("SEED", "seed"),
    KECB    ("KECB", "kecb"),
    NONE    ("NONE", "none");

    private final String code;
    private final String detail;

    EncTp(String code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }
    public String getDetail() {
        return detail;
    }
}
