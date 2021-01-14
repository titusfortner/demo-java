package com.saucedemo;

import com.saucelabs.saucebindings.SauceSession;
import junit.framework.TestCase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

public class CreateAuthUrl extends TestCase {
    private WebDriver driver;
    private boolean isTestPassed = false;
    private SauceSession session;
    private SessionId sessionId;

    //with sauce bindings
    public void setUp() {
        session = new SauceSession();
        driver = session.start();
        sessionId = ((RemoteWebDriver)driver).getSessionId();
    }
    public void tearDown() {
        session.stop(isTestPassed);
        String authUrl = "https://app.saucelabs.com/tests/" +
                sessionId + "?auth=" + getAuthToken(sessionId);
        //Now that you have the authUrl, you can perform
        //whatever operations you want with it
    }

    private String getAuthToken(SessionId sessionId) {
        // authToken = Use Hmac to generate the authToken using sessionId
        // I think you already have this logic
        String authToken = "";
        return System.getenv("SAUCE_USERNAME") + ":" + authToken;
    }

    public void testSauceStatus() {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        assertEquals(getTitle, "Swag Labs");
        isTestPassed = true;
    }
}
