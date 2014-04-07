package com.mattprovis.uitest.test;

import com.mattprovis.uitest.AbstractUITestBase;
import com.mattprovis.uitest.Mocked;
import com.mattprovis.uitest.demo.entity.User;
import com.mattprovis.uitest.demo.service.NewsService;
import com.mattprovis.uitest.demo.service.ShoppingCartService;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import java.math.BigDecimal;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HomeUITest extends AbstractUITestBase {

    @Mocked
    private NewsService newsService;

    @Mocked
    private User user;

    /*
     * We can set stub behaviour locally to a single test class, and then override it with .andReturn(...) in just the tests that care.
     */
    @Before
    public void setUp() throws Exception {
        expect(newsService.getLatestNews()).andStubReturn("Default news item");
    }

    @Test
    public void showHomePage() throws Exception {
        expect(newsService.getLatestNews()).andReturn("We're now testing with UI Test!");

        replayAll();
        driver.get(getBaseUrl() + "/");
        verifyAll();

        assertThat(driver.getTitle(), is("Home - UI Test Demo"));
        assertThat(driver.findElement(By.id("news")).getText(), is("We're now testing with UI Test!"));
    }

    @Test
    public void shouldShowHeaderAndFooterWhenSignedIn() throws Exception {
        expect(user.isAuthorised()).andReturn(true);
        expect(user.getName()).andReturn("Test User");
        expect(user.getLastLoggedInTime()).andReturn("last week");

        replayAll();
        driver.get(getBaseUrl() + "/");
        verifyAll();

        assertThat(driver.findElement(By.id("userGreeting")).getText(), is("Hello Test User!"));
        assertThat(driver.findElement(By.id("lastLoggedInTime")).getText(), is("Last logged in last week"));
    }

    @Test
    public void shouldShowSignInLinkInHeaderWhenWhenNotSignedIn() throws Exception {
        expect(user.isAuthorised()).andReturn(false);

        replayAll();
        driver.get(getBaseUrl() + "/");
        verifyAll();

        assertThat(driver.findElement(By.id("userGreeting")).getText(), is("Please sign in"));
        assertThat(driver.findElements(By.id("lastLoggedInTime")).size(), is(0));
    }

    @Test
    public void canNavigateToShoppingCartPage() throws Exception {

        replayAll();
        driver.get(getBaseUrl() + "/");
        driver.findElement(By.cssSelector("#shoppingCartItemsCount a")).click();
        verifyAll();

        assertThat(driver.getTitle(), is("Shopping Cart - UI Test Demo"));
        assertThat(driver.getCurrentUrl(), is(getBaseUrl() + "/shoppingCart"));
    }
}
