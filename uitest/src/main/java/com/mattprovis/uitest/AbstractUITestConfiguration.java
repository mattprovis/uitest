package com.mattprovis.uitest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;

import javax.annotation.PostConstruct;
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

    /*
     * Share a single instance of the Firefox driver between tests so we don't waste time opening a new window each time.
     * If you have problems with tests interacting with each other, delete this bean and try creating a new instance for each test.
     */
    @Bean(destroyMethod = "quit")
    public WebDriver webDriver() {
        return new FirefoxDriver();
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
