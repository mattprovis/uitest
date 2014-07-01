package com.mattprovis.uitest.demo;

import com.mattprovis.uitest.config.ExceptionReportingConfig;
import com.mattprovis.uitest.config.UITestCoreConfig;
import com.mattprovis.uitest.demo.config.Initializer;


public class TestInitializer extends Initializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                UITestCoreConfig.class,
                TestServiceConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
                TestWebMvcConfig.class
        };
    }
}
