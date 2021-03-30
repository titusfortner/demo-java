package com.saucedemo.tests;

import com.saucedemo.Endpoints;
import com.saucedemo.WebTestsBase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class VisualCrossPlatformTests extends WebTestsBase {
    /*
     * Configure our data driven parameters
     * */
    @Parameterized.Parameter
    public String browserName;
    @Parameterized.Parameter(2)
    public String browserVersion;
    @Parameterized.Parameter(1)
    public String platform;
    @Parameterized.Parameter(3)
    public String viewportSize;
    @Parameterized.Parameter(4)
    public String deviceName;
    String deviceNameValue;

    @Parameterized.Parameters(name = "{4}")
    public static Collection<Object[]> crossBrowserData() {
        return Arrays.asList(new Object[][]{
                {"Chrome", "Windows 10", "latest", "412x732", "Pixel XL"},
                {"Chrome", "Windows 10", "latest", "412x869", "Galaxy Note 10+"},
                {"Safari", "macOS 10.15", "latest", "375x812", "iPhone X"}
        });
    }

    @Before
    public void setUp() throws Exception {
        deviceNameValue = testName.getMethodName().split("\\[", -1)[1];

        MutableCapabilities browserOptions = new MutableCapabilities();
        browserOptions.setCapability(CapabilityType.BROWSER_NAME, browserName);
        browserOptions.setCapability(CapabilityType.BROWSER_VERSION, browserVersion);
        browserOptions.setCapability(CapabilityType.PLATFORM_NAME, platform);

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", sauceUsername);
        sauceOptions.setCapability("accesskey", sauceAccessKey);
        sauceOptions.setCapability("build", buildName);
        browserOptions.setCapability("sauce:options", sauceOptions);

        MutableCapabilities visualOptions = new MutableCapabilities();
        visualOptions.setCapability("apiKey", screenerApiKey);
        // Change project name to your application
        visualOptions.setCapability("projectName", "Flagstar");
        visualOptions.setCapability("viewportSize", viewportSize);
        browserOptions.setCapability("sauce:visual", visualOptions);

        URL url = Endpoints.getScreenerHub();
        driver = new RemoteWebDriver(url, browserOptions);
    }

    @Test()
    public void pagesRenderCorrectly() {
        //Open url using Selenium
        driver.get("https://www.flagstar.com");
        // Starting a new visual session called Responsive Flows
        getJSExecutor().executeScript("/*@visual.init*/", "Responsive Flows");

        //Deal with the cookie first using Selenium

        JavascriptExecutor js = driver;
        // Use this command to capture snapshots
        js.executeScript("/*@visual.snapshot*/",
                "Home Page" + ":" + deviceName);

        // another page
        driver.get("https://www.flagstar.com/branch-locator.html");
        WebElement search = driver.findElement(By.id("startAddress"));
        search.click();
        search.sendKeys("49201");
        driver.findElement(By.id("locator-submit")).click();
        js.executeScript("/*@visual.snapshot*/",
                "Branch Locator" + ":" + deviceName);

        Map<String, Object> response = (Map<String, Object>) getJSExecutor().executeScript("/*@visual.end*/");
        assertEquals(true, response.get("passed"));
    }
}
