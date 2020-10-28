package com.saucedemo;

import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class NativeAppTests extends TestBase {
    @Before
    public void setUpNativeCapabilities() throws MalformedURLException {
        capabilities.setCapability("platformName", "iOS");
        //cannot use .* for platform version
        capabilities.setCapability("platformVersion", "14.0.1");
        capabilities.setCapability("deviceName", "iPhone 11.*");
        capabilities.setCapability("app",
                SauceConstants.RealDevice.IOS_APP_FILE_NAME);

        driver = new IOSDriver(new URL(SauceConstants.HubUrl.WEST_FULL),
                capabilities);
    }

    @Test
    public void shouldOpenApp() {
        assertTrue(getDriver().findElement(By.id("test-LOGIN")).isDisplayed());
    }
}
