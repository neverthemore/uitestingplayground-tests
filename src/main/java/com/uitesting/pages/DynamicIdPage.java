package com.uitesting.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DynamicIdPage extends BasePage {

    private static final String URL = "http://www.uitestingplayground.com/dynamicid";

    private static final String EXPECTED_BUTTON_TEXT = "Button with Dynamic ID";

    private final By button = By.cssSelector("button.btn-primary");

    public DynamicIdPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Dynamic ID page")
    public DynamicIdPage open() {
        navigateTo(URL);
        return this;
    }

    @Step("Get button text")
    public String getButtonText() {
        return getText(button);
    }

    @Step("Get button id attribute (dynamic value)")
    public String getButtonId() {
        return getAttribute(button, "id");
    }

    @Step("Check button is visible")
    public boolean isButtonVisible() {
        return isVisible(button);
    }

    @Step("Click button using stable class-based locator")
    public DynamicIdPage clickButton() {
        click(button);
        return this;
    }

    @Step("Reload the page")
    public DynamicIdPage reloadPage() {
        driver.navigate().refresh();
        return this;
    }

    public String getExpectedButtonText() {
        return EXPECTED_BUTTON_TEXT;
    }
}
