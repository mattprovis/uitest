package com.mattprovis.uitest.demo;

import com.mattprovis.uitest.AbstractUITestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

@Configuration
public class UITestConfiguration extends AbstractUITestConfiguration {

    @Override
    protected Class<? extends WebApplicationInitializer> getTestInitializerClass() {
        return TestInitializer.class;
    }

}
