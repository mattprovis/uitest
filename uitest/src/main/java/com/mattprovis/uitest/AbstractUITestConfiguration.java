package com.mattprovis.uitest;

import org.junit.rules.TestRule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;

import java.io.File;

public abstract class AbstractUITestConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WebServer webServer() {
        return new WebServer(8090, "localhost", getTestInitializerClass(), getResourceBaseDirectory(), getContextPath());
    }

    /**
     * A class implementing WebApplicationInitializer (typically by extending your Initializer from the production code).
     * It MUST return MocksConfig.class from getRootConfigClasses(), along with at least one @Configuration class of your own that declares a bean of type MocksDefinition to set up the necessary mocks.
     */
    protected abstract Class<? extends WebApplicationInitializer> getTestInitializerClass();

    @Bean(destroyMethod = "quit")
    public WebDriver webDriver() {
        return new HtmlUnitDriver();
    }

    /**
     * Override this if you use some other base directory. This is called once on startup of the server, and the result is passed to the WebAppContext.setResourceBase(...)
     */
    protected File getResourceBaseDirectory() {
        return new File("src/main/webapp");
    }

    /**
     * Override this if you use some other context path. This is called once on startup of the server, and the result is passed to the WebAppContext.setContextPath(...)
     */
    protected String getContextPath() {
        return "/";
    }
}
