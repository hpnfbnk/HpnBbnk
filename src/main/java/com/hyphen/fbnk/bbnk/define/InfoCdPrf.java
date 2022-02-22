package com.hyphen.fbnk.bbnk.define;

public enum InfoCdPrf {
    NML_PRF ("Y00", "Normal Proof"),
    NML_INS ("Y06", "Normal Post Inspection"),
    INT_PRF ("IY0", "Intergrated Proof"),
    INT_INS ("IY6", "Intergrated Post Inspection"),
    BAT_PRF ("AY0", "BatchAgent Proof"),
    BAT_INS ("AY6", "BatchAgent Post Inspection");

    private final String code;
    private final String detail;

    InfoCdPrf(String code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }
    public String getDetail() {
        return detail;
    }

    public static boolean isValue(String code){
        boolean result = false;
        InfoCdPrf[] arr = InfoCdPrf.values();
        for(InfoCdPrf infcd : arr)  if(infcd.getCode().equals(code))    result = true;

        return result;
    }
}
