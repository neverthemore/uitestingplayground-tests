package com.uitesting.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ClickPage extends BasePage {

    private static final String URL = "http://www.uitestingplayground.com/click";

    private final By badButton = By.id("badButton");

    public ClickPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Click page")
    public ClickPage open() {
        navigateTo(URL);
        return this;
    }

    @Step("Get class attribute of the button")
    public String getButtonClass() {
        return getAttribute(badButton, "class");
    }

    @Step("Click button using a real (native) Selenium click")
    public ClickPage clickButton() {
        scrollIntoView(badButton);
        click(badButton);
        return this;
    }

    @Step("Wait until button has class: {cssClass}")
    public void waitForButtonClass(String cssClass) {
        waitForAttributeContains(badButton, "class", cssClass);
    }
}
