package com.hyphen.fbnk.bbnk.define;

public enum FNmTp {
    DEFAULT  ("DFLT", "default type"),
    KEDUFIN  ("KEDU", "k-edufile type");

    private final String code;
    private final String detail;

    FNmTp(String code, String detail) {
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
