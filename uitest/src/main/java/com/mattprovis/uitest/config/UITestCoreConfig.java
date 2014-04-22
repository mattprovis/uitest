package com.mattprovis.uitest.config;

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
}
