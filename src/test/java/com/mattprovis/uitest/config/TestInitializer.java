package com.mattprovis.uitest.config;

import com.mattprovis.uitest.demo.config.Initializer;


public class TestInitializer extends Initializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                MocksConfig.class,
                TestServiceConfig.class,
        };
    }
}
