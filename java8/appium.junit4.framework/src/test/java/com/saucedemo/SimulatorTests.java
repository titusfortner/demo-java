package com.saucedemo;

import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.assertTrue;

public class SimulatorTests extends TestBase {
    @Test
    public void shouldOpenApp() {
        assertTrue(getDriver().findElement(By.id("test-LOGIN")).isDisplayed());
    }
}
