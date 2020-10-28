package com.saucedemo;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.MutableCapabilities;

import java.net.MalformedURLException;

public class TestBase {
    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    public AppiumDriver<MobileElement> driver;
    public MutableCapabilities capabilities;

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
    public void setUpSharedCaps() throws MalformedURLException {
        capabilities = new MutableCapabilities();
        capabilities.setCapability("idleTimeout", "90");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("newCommandTimeout", "90");
        capabilities.setCapability("language", "en");
    }
}
