package com.mattprovis.uitest.demo;

import com.mattprovis.uitest.AbstractUITestConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import java.io.File;

@Configuration
public class UITestConfiguration extends AbstractUITestConfiguration {

    @Override
    protected Class<? extends WebApplicationInitializer> getTestInitializerClass() {
        return TestInitializer.class;
    }

}
