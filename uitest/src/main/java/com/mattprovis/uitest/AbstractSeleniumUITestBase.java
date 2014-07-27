package com.mattprovis.uitest;

import com.google.common.base.Function;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class AbstractSeleniumUITestBase extends AbstractUITestBase {

    protected WebDriver driver;

    protected void go(String path) {
        driver.get(getBaseUrl() + path);
    }

    protected WebElement element(By by) {
        return driver.findElement(by);
    }

    protected WebElement element(String cssSelector) {
        return getOneElement(elements(cssSelector));
    }

    private WebElement getOneElement(List<WebElement> matchingElements) {
        if (matchingElements.size() != 1) {
            throw new IllegalStateException("Expected exactly 1 matching element, found " + matchingElements.size());
        }

        return matchingElements.get(0);
    }

    protected List<WebElement> elements(String cssSelector) {
        return driver.findElements(By.cssSelector(cssSelector));
    }

    protected String attributeOf(String cssSelector, String attributeName) {
        return element(cssSelector).getAttribute(attributeName);
    }

    protected String textOf(String cssSelector) {
        return element(cssSelector).getText();
    }

    protected String textOf(WebElement parent, String cssSelector) {
        WebElement element = getOneElement(parent.findElements(By.cssSelector(cssSelector)));
        return element.getText();
    }

    protected WebElement waitForElement(final By by) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(2, SECONDS)
                .pollingEvery(200, MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(by);
            }
        });
    }

    protected <T> void patientlyAssertThat(final AssertionActualValueRetrivalFunction<T> fetcher, final Matcher<? super T> matcher) {
        final FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(2, SECONDS)
                .pollingEvery(200, MILLISECONDS)
                .ignoring(AssertionError.class)
                .ignoring(NullPointerException.class)
                .ignoring(IllegalStateException.class); // Occurs when using element() to fetch an element that does not exist (yet?)

        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                T actual = fetcher.getActual();
                assertThat(actual, matcher);
                return true;
            }

            @Override
            public String toString() {
                return "matcher: <" + matcher.toString() + ">";
            }
        });
    }

    protected static interface AssertionActualValueRetrivalFunction<T> {
        T getActual();
    }

    @Autowired
    protected void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
