package com.hyphen.fbnk.bbnk.logging;

public interface Log {
    boolean isDebugEnabled();
    boolean isTraceEnabled();
    void error(String s, Throwable e);
    void error(String s);
    void debug(String s);
    void trace(String s);
    void warn(String s);
}
