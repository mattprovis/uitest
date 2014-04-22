package com.mattprovis.uitest;

import com.mattprovis.uitest.config.MocksRegistry;
import org.apache.log4j.Logger;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public abstract class AbstractUITestBase {

    private Logger logger = Logger.getLogger(AbstractUITestBase.class);

    @Autowired
    protected WebDriver driver;

    @Autowired
    private WebServer webServer;

    private EasyMockSupport easyMockSupport;
    private MocksRegistry mocksRegistry;

    @PostConstruct
    public void postConstruct() {
        populateServerSideBeans();
        populateMockedFieldsFromServerContext();
    }

    private void populateServerSideBeans() {
        easyMockSupport = getBean(EasyMockSupport.class);
        mocksRegistry = getBean(MocksRegistry.class);
    }

    /*
     * Handle the @Mocked field annotations.
     * The fields will be populated with mocks from the server's context, as defined by the TestInitializer.
     *
     * NOTE: This is different to @Autowired, @Resource, @Inject, because the test is running in a different Spring context than the server.
     *
     */
    private void populateMockedFieldsFromServerContext() {
        for (Field field : getAllFields()) {
            if (field.isAnnotationPresent(Mocked.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(this) == null) {
                        Class<?> type = field.getType();
                        Object mockedInstanceFromServerContext = mocksRegistry.get(type);
                        field.set(this, mockedInstanceFromServerContext);
                    } else {
                        logger.warn("Found @Mocked annotated field \"" + field.getDeclaringClass().getName() + "." + field.getName() + "\", but it was already assigned a value. Expected null. Will not inject mock.");
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private List<Field> getAllFields() {
        List<Field> fields = new ArrayList<>();

        // Walk up the hierarchy, adding fields from each class because we might have an eligible field that is declared private.
        Class<?> clazz = this.getClass();
        while(clazz != AbstractUITestBase.class) {
            fields.addAll(asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    @Before
    public void resetMocksBeforeEachTest() {
        resetAll();
    }

    protected void replayAll() {
        easyMockSupport.replayAll();
    }

    protected void verifyAll() {
        easyMockSupport.verifyAll();
    }

    protected void resetAll() {
        mocksRegistry.resetMocks();

        executeStubExpectationsAnnotatedMethods();
    }

    private void executeStubExpectationsAnnotatedMethods() {
        for (Method method : getAllAccessibleMethods()) {
            if (method.isAnnotationPresent(StubExpectations.class)) {
                try {
                    method.setAccessible(true);
                    method.invoke(this);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private Set<Method> getAllAccessibleMethods() {
        Method[] locallyDeclaredMethods = this.getClass().getDeclaredMethods();
        Method[] publicAndInheritedMethods = this.getClass().getMethods();

        Set<Method> allMethods = new HashSet<>();
        allMethods.addAll(asList(locallyDeclaredMethods));
        allMethods.addAll(asList(publicAndInheritedMethods));
        return allMethods;
    }

    protected <T> T getBean(Class<T> requiredType) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(webServer.getWebAppContext().getServletContext());
        return webApplicationContext.getBean(requiredType);
    }

    protected String getHostname() {
        return webServer.getHostname();
    }

    protected int getPort() {
        return webServer.getPort();
    }

    protected String getBaseUrl() {
        return "http://" + getHostname() + ":" + getPort();
    }
}
