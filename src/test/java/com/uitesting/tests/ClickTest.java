package com.uitesting.tests;

import com.uitesting.pages.ClickPage;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("UI Testing Playground")
@Feature("Click — Native vs Synthetic Click Events")
public class ClickTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(ClickTest.class);

    @Test(description = "A real Selenium click changes the button state to success")
    @Story("Button Click Handling")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Open /click
            2. Assert button has class "btn-primary" (default state)
            3. Click the button using Selenium's native click() command
            4. Wait for & assert button class changes to "btn-success"
            """)
    public void testNativeClickChangesButtonState() {
        ClickPage page = new ClickPage(driver);

        log.info("Opening Click page");
        page.open();

        String initialClass = page.getButtonClass();
        log.info("Initial button class: '{}'", initialClass);
        Assert.assertTrue(
                initialClass.contains("btn-primary"),
                "Button should initially have class 'btn-primary', but was: " + initialClass);

        log.info("Performing a native Selenium click");
        page.clickButton();

        page.waitForButtonClass("btn-success");
        String updatedClass = page.getButtonClass();
        log.info("Updated button class: '{}'", updatedClass);

        Assert.assertTrue(
                updatedClass.contains("btn-success"),
                "Button class should change to 'btn-success' after a native click, but was: " + updatedClass);
    }
}
