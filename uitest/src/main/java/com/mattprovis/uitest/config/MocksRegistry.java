package com.mattprovis.uitest.config;

import net.sf.cglib.proxy.Enhancer;
import org.easymock.EasyMockSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class MocksRegistry {

    @Autowired
    private EasyMockSupport easyMockSupport;

    @Autowired
    private ApplicationContext applicationContext;

    private Map<Class<?>, Object> registeredMocks = new HashMap<>();

    public <T> T get(Class<T> key) {
        return (T) registeredMocks.get(key);
    }

    /**
     * Defines the mocks to be added as beans to the server's Spring context.
     * They will also be recorded in the registry so that they can be reached with an @Mocked field in a UI Test.
     */
    public void createMockBeans(Class... beanClasses) {
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        SingletonBeanRegistry registry = (SingletonBeanRegistry) beanFactory;

        for (Class aClass : beanClasses) {
            Object mockBean = easyMockSupport.createMock(aClass);
            registry.registerSingleton("test" + aClass.getSimpleName(), mockBean);
            addSingletonMock(mockBean);
        }
    }

    /**
     * Records this mock instance in the registry for all its implementing types.
     * @param mockBean
     * @param <T>
     */
    public <T> void addSingletonMock(T mockBean) {
        for (Class mockedClass : getMockedClasses(mockBean)) {
            registeredMocks.put(mockedClass, mockBean);
        }
    }


    private static Class[] getMockedClasses(final Object object) {
        if (Enhancer.isEnhanced(object.getClass())) {
            return new Class[] {object.getClass().getSuperclass()};
        } else if (Proxy.isProxyClass(object.getClass())) {
            return object.getClass().getInterfaces();
        } else {
            return new Class[] {};
        }
    }

    public void resetMocks() {
        easyMockSupport.resetAll();
    }
}
