package com.mattprovis.uitest.demo;

import com.mattprovis.uitest.config.AbstractMockedBeansConfig;
import com.mattprovis.uitest.config.MocksRegistry;
import com.mattprovis.uitest.demo.entity.User;
import com.mattprovis.uitest.demo.service.NewsService;
import com.mattprovis.uitest.demo.service.ShoppingCartService;
import com.mattprovis.uitest.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

public class TestServiceConfig extends AbstractMockedBeansConfig {

    @Override
    protected Class<?>[] getMockedBeanClasses() {
        return new Class<?>[] {
                UserService.class,
                User.class,
                NewsService.class,
                ShoppingCartService.class};
    }
}
