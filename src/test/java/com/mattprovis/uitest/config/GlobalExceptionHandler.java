package com.mattprovis.uitest.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Throwable.class)
    public void handleAllException(Throwable ex) {
        System.out.println("-----------------------exception");
    }
}
