package com.saucedemo;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.MutableCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class TestBase {
    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    private AppiumDriver<MobileElement> driver;

    public AppiumDriver<MobileElement> getDriver() {
        return driver;
    }
    @After
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
        }
    }
    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("idleTimeout", "90");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("newCommandTimeout", "90");
        capabilities.setCapability("language", "en");
        capabilities.setCapability("platformName", "iOS");
        //cannot use .* for platform version
        capabilities.setCapability("platformVersion", "14.0.1");
        capabilities.setCapability("deviceName", "iPhone 11.*");
        capabilities.setCapability("name", name.getMethodName());
        capabilities.setCapability("username", System.getenv("SAUCE_USERNAME"));
        capabilities.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        capabilities.setCapability("app",
                SauceConstants.RealDevice.IOS_APP_FILE_NAME);

        driver = new IOSDriver(new URL(SauceConstants.HubUrl.WEST_FULL),
                capabilities);
    }
}
