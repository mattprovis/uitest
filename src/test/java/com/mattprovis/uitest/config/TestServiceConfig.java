package com.mattprovis.uitest.config;

import com.mattprovis.uitest.demo.entity.User;
import com.mattprovis.uitest.demo.service.NewsService;
import com.mattprovis.uitest.demo.service.ShoppingCartService;
import com.mattprovis.uitest.demo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.easymock.EasyMock.expect;

@Configuration
public class TestServiceConfig {

    @Bean
    public MocksConfiguration serviceMocksConfiguration() {
        return new MocksConfiguration(
                UserService.class,
                User.class,
                NewsService.class,
                ShoppingCartService.class
        ) {
            @Override
            protected void configureMocks() {
                UserService userService = get(UserService.class);
                User user = get(User.class);
                expect(userService.getCurrentUser()).andStubReturn(user);
                expect(user.getName()).andStubReturn("Default User");
                expect(user.getLastLoggedInTime()).andStubReturn("yesterday afternoon");
                expect(user.isAuthorised()).andStubReturn(true);

                ShoppingCartService shoppingCartService = get(ShoppingCartService.class);
                expect(shoppingCartService.getItemsCount()).andStubReturn(2);
            }
        };
    }
}
