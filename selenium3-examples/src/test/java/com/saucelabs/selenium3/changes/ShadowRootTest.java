package com.saucelabs.selenium3.changes;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;
public class ShadowRootTest extends SauceBaseTest {
    @Test
    public void shadowDOM() {
        driver.get("http://watir.com/examples/shadow_dom.html");
        driver.findElement(By.id("details-button")).click();
        driver.findElement(By.id("proceed-link")).click();

        WebElement shadow_host = driver.findElement(By.id("shadow_host"));
        Object shadowRoot = ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot", shadow_host);

        String id = (String) ((Map<String, Object>) shadowRoot).get("shadow-6066-11e4-a52e-4f735466cecf");
        RemoteWebElement remoteWebElement = new RemoteWebElement();
        remoteWebElement.setParent(driver);
        remoteWebElement.setId(id);
        String shadowElementText = remoteWebElement.findElement(By.id("shadow_dom_first_element")).getText();

        Assertions.assertEquals("some text", shadowElementText);
    }
}
