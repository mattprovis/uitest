package com.mattprovis.uitest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionReportingConfig {

    @Bean
    public ExceptionReporter exceptionReporter() {
        return new ExceptionReporter();
    }

    @Bean
    public ErrorReporter errorReporter() {
        return new ErrorReporter();
    }

}
