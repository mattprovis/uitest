package com.mattprovis.uitest;

import org.springframework.stereotype.Component;

@Component
public class CapturedExceptionHolder {

    private Throwable capturedException;

    public synchronized void setCapturedException(Throwable e) {
        capturedException = e;
    }

    public synchronized Throwable getAndClear() {
        Throwable exception = capturedException;
        capturedException = null;
        return exception;
    }
}
