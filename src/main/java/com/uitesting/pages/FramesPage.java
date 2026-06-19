package com.uitesting.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for http://www.uitestingplayground.com/frames
 *
 * The page contains an outer iframe (id="frame-outer") whose content is
 * injected via JavaScript (srcdoc). That outer frame in turn contains a
 * nested inner iframe (id="frame-inner"), injected the same way. Both
 * frames contain IDENTICAL button markup, on purpose, so the exact same
 * locators can be reused once you've switched into the right frame
 * context:
 *
 *   - button[data-action="edit"]   → "Edit"     (data-* attribute strategy)
 *   - button with text "Submit"    → "Submit"   (text strategy)
 *   - button[name="my-button"]     → "Click me" (name attribute strategy)
 *   - button.btn-class             → "Primary"  (xpath-with-class strategy)
 *
 * Clicking any button writes "Button pressed: <label>" into that frame's
 * own #result element.
 */
public class FramesPage extends BasePage {

    private static final String URL = "http://www.uitestingplayground.com/frames";

    private final By outerFrame  = By.id("frame-outer");
    private final By innerFrame  = By.id("frame-inner");

    private final By frameLabel  = By.className("frame-label");
    private final By resultBox   = By.id("result");

    private final By editButton    = By.cssSelector("button[data-action='edit']");
    private final By submitButton  = By.xpath("//button[normalize-space(text())='Submit']");
    private final By clickMeButton = By.cssSelector("button[name='my-button']");
    private final By primaryButton = By.xpath("//button[@class='btn-class']");

    public FramesPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Frames page")
    public FramesPage open() {
        navigateTo(URL);
        return this;
    }

    @Step("Switch into the outer frame (level 1)")
    public FramesPage switchToOuterFrame() {
        switchToFrame(outerFrame);
        return this;
    }

    @Step("Switch into the inner frame (level 2, nested inside the outer frame)")
    public FramesPage switchToInnerFrame() {
        switchToFrame(innerFrame);
        return this;
    }

    @Step("Switch back to the main page")
    public FramesPage switchToMain() {
        switchToDefaultContent();
        return this;
    }

    @Step("Get the current frame's label text")
    public String getFrameLabelText() {
        return getText(frameLabel);
    }

    @Step("Get the current frame's result text")
    public String getResultText() {
        return getText(resultBox);
    }

    @Step("Click the 'Edit' button (located by data-* attribute)")
    public FramesPage clickEditButton() {
        click(editButton);
        return this;
    }

    @Step("Click the 'Submit' button (located by inner text)")
    public FramesPage clickSubmitButton() {
        click(submitButton);
        return this;
    }

    @Step("Click the 'Click me' button (located by name attribute)")
    public FramesPage clickClickMeButton() {
        click(clickMeButton);
        return this;
    }

    @Step("Click the 'Primary' button (located by XPath with class)")
    public FramesPage clickPrimaryButton() {
        click(primaryButton);
        return this;
    }

    @Step("Wait until result text equals: {expectedText}")
    public void waitForResultText(String expectedText) {
        waitForTextToBe(resultBox, expectedText);
    }

    private void switchToFrame(By frameLocator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }
}
