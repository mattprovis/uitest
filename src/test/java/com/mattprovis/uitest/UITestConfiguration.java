package com.mattprovis.uitest;

import com.mattprovis.uitest.demo.TestInitializer;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UITestConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WebServer webServer() {
        return new WebServer(8090, "localhost", TestInitializer.class);
    }

    /*
     * Share a single instance of the Firefox driver between tests so we don't waste time opening a new window each time.
     * If you have problems with tests interacting with each other, delete this bean and try creating a new instance for each test.
     */
    @Bean(destroyMethod = "quit")
    public FirefoxDriver firefoxDriver() {
        return new FirefoxDriver();
    }

}
