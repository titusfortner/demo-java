package com.saucedemo;

import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class SimulatorTests extends TestBase {

    @Before
    public void setUpSimulatorCaps() throws MalformedURLException {
        capabilities.setCapability("platformName", "iOS");
        //cannot use .* for platform version
        //TODO different from real devices
        capabilities.setCapability("platformVersion", "13.0");
        //TODO different from real devices
        capabilities.setCapability("deviceName", "iPhone XS Max Simulator");
        capabilities.setCapability("appWaitActivity",
                "com.swaglabsmobileapp.MainActivity");
        capabilities.setCapability("name", name.getMethodName());
        capabilities.setCapability("username", System.getenv("SAUCE_USERNAME"));
        capabilities.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        //TODO different from real devices
        capabilities.setCapability("app",
                "storage:filename=iOS.Simulator.SauceLabs.Mobile.Sample.app.2.7.0.zip");

        driver = new IOSDriver(new URL(SauceConstants.HubUrl.WEST_FULL),
                capabilities);
    }

    @Test
    public void shouldOpenApp() {
        assertTrue(getDriver().findElement(By.id("test-LOGIN")).isDisplayed());
    }
}
