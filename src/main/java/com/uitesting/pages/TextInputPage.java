package com.uitesting.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
