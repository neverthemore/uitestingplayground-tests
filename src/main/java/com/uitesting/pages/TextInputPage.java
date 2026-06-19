package com.uitesting.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for http://www.uitestingplayground.com/textinput
 *
 * The page contains:
 *   - A text input (#newButtonName) where you type a new button label
 *   - A button (#updatingButton) whose text changes — but ONLY at the
 *     moment it is clicked, and only if the typed text was registered
 *     through proper 'input' AND 'change' browser events.
 *
 * Selenium's sendKeys() emulates real keyboard typing, so it fires the
 * native 'input' events as you type and a native 'change' event once the
 * field loses focus (which naturally happens when the button is clicked
 * next). This is why a plain sendKeys() + click() works correctly here.
 *
 * Important: the button's label does NOT update in real time while
 * typing — it only updates after the click.
 */
public class TextInputPage extends BasePage {

    private static final String URL = "http://www.uitestingplayground.com/textinput";

    private final By nameInput      = By.id("newButtonName");
    private final By updatingButton = By.id("updatingButton");

    public TextInputPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Text Input page")
    public TextInputPage open() {
        navigateTo(URL);
        return this;
    }

    @Step("Type new button name: {newName}")
    public TextInputPage typeButtonName(String newName) {
        type(nameInput, newName);
        return this;
    }

    @Step("Click the updating button (located by stable id)")
    public TextInputPage clickUpdatingButton() {
        click(updatingButton);
        return this;
    }

    @Step("Get current button text")
    public String getButtonText() {
        return getText(updatingButton);
    }

    @Step("Wait until button text equals: {expectedText}")
    public void waitForButtonText(String expectedText) {
        waitForTextToBe(updatingButton, expectedText);
    }
}
