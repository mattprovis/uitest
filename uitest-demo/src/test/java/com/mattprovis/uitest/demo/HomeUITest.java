package com.mattprovis.uitest.demo;

import com.mattprovis.uitest.Mocked;
import com.mattprovis.uitest.StubExpectations;
import com.mattprovis.uitest.demo.controller.HomeController;
import com.mattprovis.uitest.demo.entity.User;
import com.mattprovis.uitest.demo.service.NewsService;
import org.junit.Test;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HomeUITest extends DemoUITestBase {

    @Mocked
    private NewsService newsService;

    @Mocked
    private User user;

    /*
     * We can set stub behaviour locally to a single test class, and then override it with .andReturn(...) in just the tests that care.
     */
    @StubExpectations
    public void stubExpectations() throws Exception {
        expect(newsService.getLatestNews()).andStubReturn("Default news item");
    }

    @Test
    public void showHomePage() throws Exception {
        expect(newsService.getLatestNews()).andReturn("We're now testing with UI Test!");

        replayAll();
        go("/");
        verifyAll();

        assertThat(driver.getTitle(), is("Home - UI Test Demo"));
        assertThat(element("#news").getText(), is("We're now testing with UI Test!"));
    }

    @Test
    public void shouldShowHeaderAndFooterWhenSignedIn() throws Exception {
        expect(user.isAuthorised()).andReturn(true);
        expect(user.getName()).andReturn("Test User");
        expect(user.getLastLoggedInTime()).andReturn("last week");

        replayAll();
        go("/");
        verifyAll();

        assertThat(element("#userGreeting").getText(), is("Hello Test User!"));
        assertThat(element("#lastLoggedInTime").getText(), is("Last logged in last week"));
    }

    @Test
    public void shouldShowSignInLinkInHeaderWhenWhenNotSignedIn() throws Exception {
        expect(user.isAuthorised()).andReturn(false);

        replayAll();
        go("/");
        verifyAll();

        assertThat(element("#userGreeting").getText(), is("Please sign in"));
        assertThat(elements("#lastLoggedInTime").size(), is(0));
    }

    @Test
    public void canNavigateToShoppingCartPage() throws Exception {
        replayAll();
        go("/");
        element("#shoppingCartItemsCount a").click();
        verifyAll();

        assertThat(driver.getTitle(), is("Shopping Cart - UI Test Demo"));
        assertThat(driver.getCurrentUrl(), is(getBaseUrl() + "/shoppingCart"));
    }

    @Test(expected = HomeController.ExampleException.class)
    public void shouldThrowAnExceptionBackOutToTheTest() throws Exception {
        replayAll();
        go("/exception");
        verifyAll();  // this should throw the exception
    }

    @Test(expected = HomeController.ExampleError.class)
    public void shouldThrowAnErrorBackOutToTheTest() throws Exception {
        replayAll();
        go("/error");
        verifyAll(); // this should throw the error
    }
}
