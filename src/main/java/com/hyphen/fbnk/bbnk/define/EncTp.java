package com.hyphen.fbnk.bbnk.define;

import java.util.Arrays;
import java.util.List;

public enum EncTp {
    SEED        ("SEED", "seed"),
    KECB        ("KECB", "kecb"),
    NONE        ("NONE", "none"),
    RSA_HD_S    ("S", "rsa header S"),
    RSA_HD_D    ("D", "rsa header D");

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
