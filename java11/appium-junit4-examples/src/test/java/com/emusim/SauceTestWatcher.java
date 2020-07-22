package com.emusim;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class SauceTestWatcher extends TestWatcher {

    private WebDriver driver;

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    protected void succeeded(Description description) {
        if(driver == null){return;}

        ((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
        driver.quit();
    }

    protected void failed(Throwable e, Description description) {
        if(driver == null)
            return;

        ((JavascriptExecutor) driver).executeScript("sauce:context=" + e.getMessage());
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=failed");
        driver.quit();
    }
}
