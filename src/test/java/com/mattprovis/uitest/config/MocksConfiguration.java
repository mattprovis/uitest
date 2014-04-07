package com.mattprovis.uitest.config;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class MocksConfiguration {

    @Autowired
    protected MocksRegistry mocksRegistry;

    private Class[] beanClasses;

    public MocksConfiguration(Class... beanClasses) {
        this.beanClasses = beanClasses;
    }

    /**
     * Override this method to set up the initial state for the mocks, to be reapplied after they are reset between each test execution.
     * These should all be andStubReturns() expectations.
     */
    protected void configureMocks() {
    }

    protected <T> T get(Class<T> key) {
        return (T) mocksRegistry.get(key);
    }

    @PostConstruct
    public void register() {
        mocksRegistry.createMockBeans(beanClasses);
    }
}
