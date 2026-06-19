package com.uitesting.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for http://www.uitestingplayground.com/click
 *
 * The page registers a capturing click listener on document.body that
 * inspects event.screenX. A real, physically-driven mouse click (the kind
 * Selenium's native click() command produces) carries real screen
 * coordinates, so screenX > 0 and the button turns green. A purely
 * synthetic/programmatic click (e.g. one dispatched via JavaScript's
 * element.click()) has screenX == 0 by default and is therefore ignored
 * by the page — the button stays in its default state.
 *
 * This means the correct, working approach here is a plain Selenium
 * WebElement.click() (NOT a JavaScript-executed click).
 *
 * After a successful click the button's class changes from
 *   "btn btn-primary" → "btn btn-success"
 */
public class ClickPage extends BasePage {

    private static final String URL = "http://www.uitestingplayground.com/click";

    // The button that ignores purely synthetic (non-physical) clicks
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
