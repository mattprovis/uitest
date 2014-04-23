uitest
======

This framework makes it easier to build web tests for an application in a style similar to traditional unit testing.

By treating the jsp files and their Spring controllers together as the "unit" under test, we can exercise their behaviour from the user's point of view, without needing complex stubbed services to stand-in for the backend.

**Easy to read**
* Everything you see in the test code is directly relevant to the behaviour being exercised
* No "magic numbers" or other special arguments need to be passed in to trigger desired behaviours

**Fast to write**
* Default behaviour is defined outside of the test method, so each test only need specify the things that are different

**High cohesion, loose coupling**
* Default stub behaviour is shared between all tests in a single test class, and set up right there in the test class: nothing is defined outside (unless you want it!)
* Setting up specific stub behaviour needed for just one test has no impact on other tests
* Impact from changes to production code is minimized: only those tests that actually execised the changed code need to be updated

**Fast to execute**
* Tests can run quickly because each one jumps in directly at the page being tested, with the desired state ready to go
* No logging in, or navigating through pages to get there
* It's easy to use HTMLUnit for its greater speed, but swap it for a real browser while debugging

**Familiar syntax**
* Expectations and behaviours are defined with EasyMock
* Selenium WebDriver for navigating in the browser and reading page content


```java

    @Test
    public void showHomePage() throws Exception {
        expect(newsService.getLatestNews()).andReturn("We're now testing with UI Test!");

        replayAll();
        driver.get(getBaseUrl() + "/");
        verifyAll();

        assertThat(driver.getTitle(), is("Home - UI Test Demo"));
        assertThat(driver.findElement(By.id("news")).getText(), is("We're now testing with UI Test!"));
    }
```

Where do I start?
-----------------
Download the source, and you'll find two maven projects.
Install uitest, and then have a look at HomeUITest.java in the uitest-demo project. The test is ready to run direct from your IDE, or via maven.
