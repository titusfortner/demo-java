package com.saucedemo.selenium.se4newfeatures;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
public class ShadowRootNewTest extends SauceBaseTest {

    @Test
    public void getShadowDOMSearchContext() {
        driver.get("https://watir.com/examples/shadow_dom.html");
        tempWatirSiteWorkaround();

        WebElement shadowHost = driver.findElement(By.cssSelector("#shadow_host"));
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;

        SearchContext shadowRoot = (SearchContext) jsDriver.executeScript("return arguments[0].shadowRoot", shadowHost);
        WebElement shadowContent = shadowRoot.findElement(By.cssSelector("#shadow_content"));

        Assertions.assertEquals("some text", shadowContent.getText());
    }

    @Test
    public void getShadowDOMMethod() {
        driver.get("https://watir.com/examples/shadow_dom.html");
        tempWatirSiteWorkaround();

        WebElement shadowHost = driver.findElement(By.cssSelector("#shadow_host"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        WebElement shadowContent = shadowRoot.findElement(By.cssSelector("#shadow_content"));

        Assertions.assertEquals("some text", shadowContent.getText());
    }

    public void tempWatirSiteWorkaround() {
        driver.findElement(By.id("details-button")).click();
        driver.findElement(By.id("proceed-link")).click();
    }
}
