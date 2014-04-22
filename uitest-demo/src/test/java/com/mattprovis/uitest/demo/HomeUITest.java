package com.mattprovis.uitest.demo;

import com.mattprovis.uitest.Mocked;
import com.mattprovis.uitest.StubExpectations;
import com.mattprovis.uitest.demo.entity.User;
import com.mattprovis.uitest.demo.service.NewsService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.easymock.EasyMock.expect;

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
        driver.get(getBaseUrl() + "/");
        verifyAll();

        Assert.assertThat(driver.getTitle(), CoreMatchers.is("Home - UI Test Demo"));
        Assert.assertThat(driver.findElement(By.id("news")).getText(), CoreMatchers.is("We're now testing with UI Test!"));
    }

    @Test
    public void shouldShowHeaderAndFooterWhenSignedIn() throws Exception {
        expect(user.isAuthorised()).andReturn(true);
        expect(user.getName()).andReturn("Test User");
        expect(user.getLastLoggedInTime()).andReturn("last week");

        replayAll();
        driver.get(getBaseUrl() + "/");
        verifyAll();

        Assert.assertThat(driver.findElement(By.id("userGreeting")).getText(), CoreMatchers.is("Hello Test User!"));
        Assert.assertThat(driver.findElement(By.id("lastLoggedInTime")).getText(), CoreMatchers.is("Last logged in last week"));
    }

    @Test
    public void shouldShowSignInLinkInHeaderWhenWhenNotSignedIn() throws Exception {
        expect(user.isAuthorised()).andReturn(false);

        replayAll();
        driver.get(getBaseUrl() + "/");
        verifyAll();

        Assert.assertThat(driver.findElement(By.id("userGreeting")).getText(), CoreMatchers.is("Please sign in"));
        Assert.assertThat(driver.findElements(By.id("lastLoggedInTime")).size(), CoreMatchers.is(0));
    }

    @Test
    public void canNavigateToShoppingCartPage() throws Exception {

        replayAll();
        driver.get(getBaseUrl() + "/");
        driver.findElement(By.cssSelector("#shoppingCartItemsCount a")).click();
        verifyAll();

        Assert.assertThat(driver.getTitle(), CoreMatchers.is("Shopping Cart - UI Test Demo"));
        Assert.assertThat(driver.getCurrentUrl(), CoreMatchers.is(getBaseUrl() + "/shoppingCart"));
    }
}
