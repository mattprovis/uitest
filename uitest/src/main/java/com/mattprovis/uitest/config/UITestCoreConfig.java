package com.mattprovis.uitest.config;

import com.mattprovis.uitest.CapturedExceptionHolder;
import org.easymock.EasyMockSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UITestCoreConfig {

    @Bean
    public EasyMockSupport easyMockSupport() {
        return new EasyMockSupport();
    }

    @Bean
    public MocksRegistry mocksRegistry() {
        return new MocksRegistry();
    }

    @Bean
    public CapturedExceptionHolder capturedExceptionHolder() {
        return new CapturedExceptionHolder();
    }
}
