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

    // Use platform configurator to set your devices for Emusim
    // https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/
    //
    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("idleTimeout", "90");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("newCommandTimeout", "90");
        capabilities.setCapability("language", "en");
        capabilities.setCapability("platformName", "iOS");
        //cannot use .* for platform version
        //TODO different from real devices
        capabilities.setCapability("platformVersion", "13.4");
        //TODO different from real devices
        capabilities.setCapability("deviceName", "iPhone XS Max Simulator");
        capabilities.setCapability("appWaitActivity", "com.swaglabsmobileapp.MainActivity");
        capabilities.setCapability("name", name.getMethodName());
        capabilities.setCapability("username", System.getenv("SAUCE_USERNAME"));
        capabilities.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        //TODO different from real devices
        capabilities.setCapability("app",
                "storage:filename=iOS.Simulator.SauceLabs.Mobile.Sample.app.2.7.0.zip");

        driver = new IOSDriver(new URL(SauceConstants.HubUrl.WEST_FULL),
                capabilities);
    }
}
