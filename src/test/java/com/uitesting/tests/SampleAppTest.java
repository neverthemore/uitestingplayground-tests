package com.uitesting.tests;

import com.uitesting.pages.SampleAppPage;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("UI Testing Playground")
@Feature("Sample App — Login")
public class SampleAppTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(SampleAppTest.class);

    private static final String VALID_USERNAME   = "admin";
    private static final String VALID_PASSWORD   = "pwd";
    private static final String INVALID_PASSWORD = "wrong123";


    @Test(description = "Login with valid credentials shows welcome message")
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            Steps:
            1. Open /sampleapp
            2. Enter username "admin" and password "pwd"
            3. Click Log In
            Expected: status label contains "Welcome, admin!"
            """)
    public void testSuccessfulLogin() {
        SampleAppPage page = new SampleAppPage(driver);

        log.info("Opening Sample App page and logging in with valid credentials");
        page.open()
            .login(VALID_USERNAME, VALID_PASSWORD);

        String status = page.getLoginStatusText();
        log.info("Login status returned: '{}'", status);

        Assert.assertTrue(
                status.contains("Welcome"),
                "Status should contain 'Welcome', but was: " + status);

        Assert.assertTrue(
                status.contains(VALID_USERNAME),
                "Status should contain the username '" + VALID_USERNAME
                + "', but was: " + status);
    }


    @Test(description = "Login with invalid password shows error message")
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("""
            Steps:
            1. Open /sampleapp
            2. Enter username "admin" and wrong password "wrong123"
            3. Click Log In
            Expected: status label equals "Invalid username/password"
            """)
    public void testFailedLogin() {
        SampleAppPage page = new SampleAppPage(driver);

        log.info("Opening Sample App page and logging in with invalid password");
        page.open()
            .login(VALID_USERNAME, INVALID_PASSWORD);

        String status = page.getLoginStatusText();
        log.info("Login status returned: '{}'", status);

        Assert.assertEquals(
                status,
                "Invalid username/password",
                "Expected error message on invalid credentials");
    }
}
