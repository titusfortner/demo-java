package com.saucedemo;

import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {
    //We need to create a ThreadLocal object to be accessed by a specific thread
    protected static ThreadLocal<SauceSession> session = new ThreadLocal<>();
    protected static ThreadLocal<SauceOptions> options = new ThreadLocal<>();

    public SauceSession getSession() {
        return session.get();
    }

    public WebDriver getDriver() {
        return getSession().getDriver();
    }

    @BeforeMethod
    public void setup(Method method) {
        session.set(new SauceSession());
        getSession().start();
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        getSession().stop(result.isSuccess());
    }

    @AfterClass
    void terminate() {
        session.remove();
    }
}

