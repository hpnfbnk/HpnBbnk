package com.hyphen.fbnk.bbnk.logging;

import java.lang.reflect.Constructor;

public final class LogFactory {
    public static final String MARKER = "HPNBBNK";
    private static Constructor<? extends Log> logConstructor;

    static {
        tryImplementation(LogFactory::useSlf4jLogging);
        tryImplementation(LogFactory::useNoLogging);
    }

    private LogFactory(){
        //disable construction
    }

    public static Log getLog(Class<?> aClass){
        return getLog(aClass.getName());
    }
    public static Log getLog(String logger){
        try {
            return logConstructor.newInstance(logger);
        } catch (Throwable t) {
            throw new RuntimeException("Error creating logger for logger "+logger+". Cause: "+t, t);
        }
    }

    public static synchronized void useSlf4jLogging(){
        setImplementation(com.hyphen.fbnk.bbnk.logging.Slf4jImpl.class);
    }

    public static synchronized void useNoLogging(){
        setImplementation(com.hyphen.fbnk.bbnk.logging.NoLoggingImpl.class);
    }

    private static void tryImplementation(Runnable runnable){
        //System.out.println("[tryImplementation] "+runnable);
        if(logConstructor == null){
            try {
                runnable.run();
            } catch (Throwable t){
                //ignore
            }
        }
    }

    private static void setImplementation(Class<? extends Log> impClass){
        //System.out.println("[setImplementation] "+impClass);
        try {
            Constructor<? extends Log> candidate = impClass.getConstructor(String.class);
            Log log = candidate.newInstance(LogFactory.class.getName());

            if(log.isDebugEnabled()){
                log.debug("Logging initialized using '"+impClass+"' adapter...");
                log.debug("isDebugEnabled="+log.isDebugEnabled()+", isTraceEnabled="+log.isTraceEnabled());
            }
            logConstructor = candidate;
        } catch (Throwable t) {
            throw new RuntimeException("Error setting Log implementation. Cause: "+t, t);
        }
    }

}
