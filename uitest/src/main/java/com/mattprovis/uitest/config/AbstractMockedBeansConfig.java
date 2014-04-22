package com.mattprovis.uitest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public abstract class AbstractMockedBeansConfig {

    @Autowired
    protected MocksRegistry mocksRegistry;

    @PostConstruct
    public void createMockBeans() {
        mocksRegistry.createMockBeans(getMockedBeanClasses());
    }

    protected abstract Class<?>[] getMockedBeanClasses();
}
