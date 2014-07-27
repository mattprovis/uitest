package com.mattprovis.uitest.demo;

import com.mattprovis.uitest.AbstractSeleniumUITestBase;
import com.mattprovis.uitest.Mocked;
import com.mattprovis.uitest.StubExpectations;
import com.mattprovis.uitest.demo.entity.User;
import com.mattprovis.uitest.demo.service.ShoppingCartService;
import com.mattprovis.uitest.demo.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.easymock.EasyMock.expect;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UITestConfiguration.class})
public abstract class DemoUITestBase extends AbstractSeleniumUITestBase {

    @Mocked
    private UserService userService;

    @Mocked
    private User user;

    @Mocked
    private ShoppingCartService shoppingCartService;

    @StubExpectations
    public void commonUserStubExpectations() {
        expect(userService.getCurrentUser()).andStubReturn(user);
        expect(user.getName()).andStubReturn("Default User");
        expect(user.getLastLoggedInTime()).andStubReturn("yesterday afternoon");
        expect(user.isAuthorised()).andStubReturn(true);
    }

    @StubExpectations
    public void commonShoppingCartStubExpectations() {
        expect(shoppingCartService.getItemsCount()).andStubReturn(2);
    }
}
