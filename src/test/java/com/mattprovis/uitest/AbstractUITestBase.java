package com.mattprovis.uitest;

import com.mattprovis.uitest.config.MocksRegistry;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UITestConfiguration.class})
public abstract class AbstractUITestBase {

    @Autowired
    protected FirefoxDriver driver;

    @Autowired
    private WebServer webServer;

    private EasyMockSupport easyMockSupport;
    private MocksRegistry mocksRegistry;

    /*
     * Handle the @Mocked field annotations.
     * The fields will be populated with mocks from the server's context, as defined by the TestInitializer.
     *
     * NOTE: This is different to @Autowired, @Resource, @Inject, because the test is running in a different Spring context than the server.
     *
     */
    @PostConstruct
    public void populateMockedFieldsFromServerContext() {
        easyMockSupport = getBean(EasyMockSupport.class);
        mocksRegistry = getBean(MocksRegistry.class);

        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Mocked.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(this) == null) {
                        Class<?> type = field.getType();
                        Object mockedInstanceFromServerContext = mocksRegistry.get(type);
                        field.set(this, mockedInstanceFromServerContext);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
