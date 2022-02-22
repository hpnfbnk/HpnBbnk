package com.hyphen.fbnk.bbnk.logging;

public class NoLoggingImpl implements Log{
    public NoLoggingImpl(String clazz) {
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void error(String s, Throwable e) {

    }

    @Override
    public void error(String s) {

    }

    @Override
    public void debug(String s) {

    }

    @Override
    public void trace(String s) {

    }

    @Override
    public void warn(String s) {

    }
}
