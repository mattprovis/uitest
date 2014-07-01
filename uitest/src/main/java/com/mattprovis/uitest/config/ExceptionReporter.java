package com.mattprovis.uitest.config;

import com.mattprovis.uitest.CapturedExceptionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
/**
 * This class listens for Exceptions thrown by the controllers, and stores them in the CapturedExceptionHolder.
 * Unfortunately, @ExceptionHandler does not support Errors, so that had to be handled differently, in the ErrorReporter class.
 */
public class ExceptionReporter {

    @Autowired
    private CapturedExceptionHolder capturedExceptionHolder;

    @ExceptionHandler
    public synchronized void captureException(Exception e) {
        capturedExceptionHolder.setCapturedException(e);
    }
}
