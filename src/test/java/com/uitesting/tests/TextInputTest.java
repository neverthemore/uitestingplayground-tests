package com.uitesting.tests;

import com.uitesting.pages.TextInputPage;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


@Epic("UI Testing Playground")
@Feature("Text Input — Button Label Updated on Click")
public class TextInputTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(TextInputTest.class);

    private static final String NEW_BUTTON_NAME = "QA Automation Button";
    private static final String DEFAULT_BUTTON_TEXT =
            "Button That Should Change it's Name Based on Input Value";

    @Test(description = "Typing + clicking updates the button label; typing alone does not")
    @Story("Button Label Updated via Real Keyboard Input")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Open /textinput
            2. Assert the button shows its default label
            3. Type "QA Automation Button" in the Name Input field (sendKeys)
            4. Assert the button label has NOT changed yet (no click happened)
            5. Click the button (located by stable id)
            6. Assert the button label now equals "QA Automation Button"
            """)
    public void testButtonLabelUpdatesOnlyAfterClick() {
        TextInputPage page = new TextInputPage(driver);

        page.open();

        String initialLabel = page.getButtonText();
        log.info("Initial button label: '{}'", initialLabel);
        Assert.assertEquals(
                initialLabel,
                DEFAULT_BUTTON_TEXT,
                "Button should show its default label before any interaction");

        log.info("Typing new button name: '{}'", NEW_BUTTON_NAME);
        page.typeButtonName(NEW_BUTTON_NAME);

        String labelBeforeClick = page.getButtonText();
        log.info("Button label right after typing (before click): '{}'", labelBeforeClick);
        Assert.assertEquals(
                labelBeforeClick,
                DEFAULT_BUTTON_TEXT,
                "Button label should NOT change just from typing, only after a click");

        page.clickUpdatingButton();

        page.waitForButtonText(NEW_BUTTON_NAME);
        String finalLabel = page.getButtonText();
        log.info("Final button label after click: '{}'", finalLabel);

        Assert.assertEquals(
                finalLabel,
                NEW_BUTTON_NAME,
                "Button label should equal '" + NEW_BUTTON_NAME
                + "' after clicking, but was: " + finalLabel);
    }
}
