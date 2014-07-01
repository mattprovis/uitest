package com.mattprovis.uitest.demo;

import com.mattprovis.uitest.config.ErrorReporter;
import com.mattprovis.uitest.config.ExceptionReporter;
import com.mattprovis.uitest.demo.config.WebMvcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.mattprovis.uitest.demo.controller"})
public class TestWebMvcConfig extends WebMvcConfig {

    @Autowired
    private ErrorReporter errorReporter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(errorReporter);
    }

    @Bean
    public ExceptionReporter exceptionReporter() {
        return new ExceptionReporter();
    }

    @Bean
    public ErrorReporter errorReporter() {
        return new ErrorReporter();
    }

}
