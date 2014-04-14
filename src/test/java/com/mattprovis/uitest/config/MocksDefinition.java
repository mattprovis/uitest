package com.mattprovis.uitest.config;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Defines the mocks to be added as beans to the server's Spring context.
 * They will also be recorded in the registry so that they can be reached with an @Mocked field in a UI Test.
 *
 * Default behaviour can be defined by overriding configureMocks().
 *
 * Create one or more instances of this class as an @Bean in your test @Configuration classes.
 *
 */
 public class MocksDefinition {

    @Autowired
    protected MocksRegistry mocksRegistry;

    private Class[] beanClasses;

    /**
     * Specifies that mock beans are to be created for each of the specified classes.
     *
     * @param beanClasses
     */
    public MocksDefinition(Class... beanClasses) {
        this.beanClasses = beanClasses;
    }

    /**
     * Override this method to set up the default behaviour for the mocks.
     *
     * This will be reapplied after they are reset between each test execution.
     * Note that these should all be andStubReturns() expectations.
     * You can fetch an instance of a mock (defined in the constructor of this or any other MocksDefinition instance) using get().
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
