package com.uitesting.tests;

import com.uitesting.pages.DynamicIdPage;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for: http://www.uitestingplayground.com/dynamicid
 *
 * Scenario 4 (additional): Dynamic ID
 *   - The button has a randomly generated "id" that changes on every reload.
 *   - Tests must use a STABLE locator (class, text, etc.) instead of the id.
 *   - This test proves:
 *       (a) the id really does change between page loads,
 *       (b) a class-based locator can click the button reliably.
 */
@Epic("UI Testing Playground")
@Feature("Dynamic ID — Stable Locator Strategy")
public class DynamicIdTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(DynamicIdTest.class);

    @Test(description = "Button with dynamic ID is clickable via stable class-based locator")
    @Story("Dynamic ID Handling")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Open /dynamicid
            2. Assert button is visible and its text is "Button with Dynamic ID"
            3. Record current button id attribute
            4. Reload the page
            5. Assert the button id has changed (proves it is truly dynamic)
            6. Click the button using a class-based selector (not id)
            Expected: no exceptions thrown; button is clickable on every load
            """)
    public void testClickButtonWithDynamicId() {
        DynamicIdPage page = new DynamicIdPage(driver);

        // 1. Open page
        page.open();

        // 2. Assert button is visible and has the expected label
        Assert.assertTrue(
                page.isButtonVisible(),
                "The button with dynamic id should be visible");

        Assert.assertEquals(
                page.getButtonText(),
                page.getExpectedButtonText(),
                "Button label should always equal 'Button with Dynamic ID'");

        // 3. Capture id from the first load
        String idBeforeReload = page.getButtonId();
        log.info("Button id before reload: '{}'", idBeforeReload);
        Assert.assertNotNull(idBeforeReload, "Button id should not be null");
        Assert.assertFalse(idBeforeReload.isEmpty(), "Button id should not be empty");

        // 4. Reload the page
        page.reloadPage();

        // 5. Capture id after reload and assert it changed
        String idAfterReload = page.getButtonId();
        log.info("Button id after reload: '{}'", idAfterReload);
        Assert.assertNotEquals(
                idBeforeReload,
                idAfterReload,
                "Button id should be different after page reload "
                + "(before=" + idBeforeReload + ", after=" + idAfterReload + ")");

        // 6. Click using stable class-based locator — must not throw
        page.clickButton();
    }
}
