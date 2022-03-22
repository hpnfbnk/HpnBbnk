package com.hyphen.fbnk.bbnk.define;

import java.util.Arrays;
import java.util.List;

public enum MsgCode {
    MSG_TP_SND_REQ  ("S"),
    MSG_TP_RCV_REQ  ("R"),
    MSG_TP_SND_LST  ("L"),
    MSG_TP_RCV_LST  ("M"),
    MSG_TP_BIN      ("B"),
    MSG_TP_ZIP      ("Z"),
    MSG_TP_REQ_ALL  ("A"),
    MSG_TP_REQ_YET  ("E"),
    MSG_OK          ("OK"),
    MSG_ENCODE      ("EUC-KR"),
    MSG_OPEN_REQ    ("0800100"),
    MSG_OPEN_REP    ("0810100"),
    MSG_TRANS_REQ   ("0700100"),
    MSG_TRANS_REP   ("0720100"),
    MSG_DATA_REQ    ("0710100"),
    MSG_MISS_REQ    ("0400100"),
    MSG_MISS_REP    ("0420100"),
    MSG_MISS_DATA   ("0410100"),
    MSG_PARTEND_REQ ("0800800"),
    MSG_PARTEND_REP ("0810800"),
    MSG_CLOSE_REQ   ("0800900"),
    MSG_CLOSE_REP   ("0810900"),
    DEFAULT         ("");

    private final String code;
    public String getCode() {return code;}

    MsgCode(String code) {
        this.code = code;
    }

    private static final List<MsgCode> MSG_CODE_LIST = Arrays.asList(MsgCode.values());
    public static MsgCode fromCode(String code){
        for (MsgCode msgCode : MSG_CODE_LIST) {
            if (msgCode.getCode().equals(code.trim())) {
                return msgCode;
            }
        }
        MsgCode empty = null;
        throw new java.util.NoSuchElementException("No value present");
    }
}
