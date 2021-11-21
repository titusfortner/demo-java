package com.saucedemo.selenium.se4newfeatures;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
public class ShadowRootOldTest extends SauceBaseTest {

    public SauceOptions createSauceOptions() {
        return SauceOptions.chrome().setBrowserVersion("95").build();
    }

    @Test
    public void getShadowDOMContentChrome95() {
        driver.get("https://watir.com/examples/shadow_dom.html");
        driver.findElement(By.id("details-button")).click();
        driver.findElement(By.id("proceed-link")).click();

        WebElement shadowHost = driver.findElement(By.cssSelector("#shadow_host"));
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;

        WebElement shadowRoot = (WebElement) jsDriver.executeScript("return arguments[0].shadowRoot", shadowHost);
        WebElement shadowContent = shadowRoot.findElement(By.cssSelector("#shadow_content"));
    }
}
