package com.emusim;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.NoSuchElementException;

import java.io.InvalidObjectException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class EnhancedDebuggingTests {
    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    @Rule
    public SauceTestWatcher testWatcher = new SauceTestWatcher();
    private AppiumDriver driver;

    public AppiumDriver getDriver() {
        return driver;
    }

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.17.1");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone XS Max Simulator");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability("name", name.getMethodName());

        capabilities.setCapability("idleTimeout", "90");
        capabilities.setCapability("newCommandTimeout", "90");

        driver = new AppiumDriver(
                new URL("https://" + System.getenv("SAUCE_USERNAME") + ":" +
                        System.getenv("SAUCE_ACCESS_KEY") +
                        "@ondemand.saucelabs.com:443" + "/wd/hub"),
                capabilities);
        testWatcher.setDriver(driver);
    }


    @Test
    public void shouldLogin() throws InvalidObjectException {
        getDriver().navigate().to("https://www.saucedemo.com");
        getJsExecutor().executeScript("sauce:context=" + "-Opened the Home Page");

        getJsExecutor().executeScript("sauce:context=" + "-Starting login");
        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();
        getJsExecutor().executeScript("sauce:context=" + "-Exiting login");

        assertTrue(getDriver().findElement(By.id("inventory_filter_container")).isDisplayed());
    }

    /*
     * In this example, we get to see failing commands in Sauce
     * */
    @Test(expected = NoSuchElementException.class)
    public void shouldNotFindElement() throws InvalidObjectException {
        getDriver().navigate().to("https://www.saucedemo.com");
        getJsExecutor().executeScript("sauce:context=" + "-Opened the Home Page");

        getJsExecutor().executeScript("sauce:context=" + "-Starting login");
        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("BAZINGAH")).click();
        getJsExecutor().executeScript("sauce:context=" + "-Exiting login");
    }

    private JavascriptExecutor getJsExecutor() throws InvalidObjectException {
        if (getDriver() != null) {
            return (JavascriptExecutor) getDriver();
        }
        throw new InvalidObjectException("Driver was null");
    }
}
