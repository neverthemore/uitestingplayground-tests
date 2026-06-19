package com.uitesting.tests;

import com.uitesting.pages.FramesPage;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for: http://www.uitestingplayground.com/frames
 *
 * Scenario 2 (required): Frames
 *   - The page has a nested-frame structure: outer frame (level 1)
 *     containing an inner frame (level 2). Both contain identical
 *     buttons that can each be located with a different strategy
 *     (data-* attribute, text, name attribute, xpath-with-class).
 *   - This test switches into the outer frame, exercises all four
 *     locator strategies there, then drills into the nested inner
 *     frame and repeats one of them to prove the navigation works
 *     at both nesting levels.
 */
@Epic("UI Testing Playground")
@Feature("Frames — Nested iFrame Navigation & Locator Strategies")
public class FramesTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(FramesTest.class);

    @Test(description = "Switch into nested frames and locate buttons via 4 different strategies")
    @Story("Nested Frame Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Open /frames
            2. Switch to the outer frame (level 1)
            3. Assert frame label equals "Outer Frame (Level 1)"
            4. Click button by data-* attribute -> result shows "Button pressed: Edit"
            5. Click button by inner text       -> result shows "Button pressed: Submit"
            6. Click button by name attribute   -> result shows "Button pressed: Click me"
            7. Click button by xpath + class    -> result shows "Button pressed: Primary"
            8. Switch to the inner frame (level 2, nested inside the outer frame)
            9. Assert frame label equals "Inner Frame (Level 2)"
            10. Click button by data-* attribute inside the inner frame and verify its own result
            11. Switch back to the main page
            """)
    public void testNestedFrameNavigationAndLocatorStrategies() {
        FramesPage page = new FramesPage(driver);

        // 1. Open the frames page
        log.info("Opening Frames page");
        page.open();

        // 2. Switch into the outer frame (level 1)
        page.switchToOuterFrame();

        // 3. Verify outer frame label
        String outerLabel = page.getFrameLabelText();
        log.info("Outer frame label: '{}'", outerLabel);
        Assert.assertEquals(outerLabel, "Outer Frame (Level 1)",
                "Outer frame label text mismatch");

        // 4. data-* attribute strategy
        page.clickEditButton();
        page.waitForResultText("Button pressed: Edit");
        Assert.assertEquals(page.getResultText(), "Button pressed: Edit",
                "Result after clicking button located by data-* attribute");

        // 5. text strategy
        page.clickSubmitButton();
        page.waitForResultText("Button pressed: Submit");
        Assert.assertEquals(page.getResultText(), "Button pressed: Submit",
                "Result after clicking button located by inner text");

        // 6. name attribute strategy
        page.clickClickMeButton();
        page.waitForResultText("Button pressed: Click me");
        Assert.assertEquals(page.getResultText(), "Button pressed: Click me",
                "Result after clicking button located by name attribute");

        // 7. xpath with class strategy
        page.clickPrimaryButton();
        page.waitForResultText("Button pressed: Primary");
        Assert.assertEquals(page.getResultText(), "Button pressed: Primary",
                "Result after clicking button located by XPath + class");

        // 8. Switch into the nested inner frame (level 2)
        log.info("Switching into the nested inner frame");
        page.switchToInnerFrame();

        // 9. Verify inner frame label
        String innerLabel = page.getFrameLabelText();
        log.info("Inner frame label: '{}'", innerLabel);
        Assert.assertEquals(innerLabel, "Inner Frame (Level 2)",
                "Inner frame label text mismatch");

        // 10. Re-use the data-* attribute strategy inside the nested frame
        page.clickEditButton();
        page.waitForResultText("Button pressed: Edit");
        Assert.assertEquals(page.getResultText(), "Button pressed: Edit",
                "Result inside the nested inner frame after clicking its own Edit button");

        // 11. Return to the main page
        page.switchToMain();
    }
}
