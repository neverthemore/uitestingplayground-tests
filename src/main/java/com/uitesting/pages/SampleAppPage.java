package com.uitesting.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SampleAppPage extends BasePage {

    private static final String URL = "http://www.uitestingplayground.com/sampleapp";

    // Locators
    private final By usernameField  = By.cssSelector("input[name='UserName']");
    private final By passwordField  = By.cssSelector("input[name='Password']");
    private final By loginButton    = By.id("login");
    private final By loginStatus    = By.id("loginstatus");

    public SampleAppPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Sample App page")
    public SampleAppPage open() {
        navigateTo(URL);
        return this;
    }

    @Step("Enter username: {username}")
    public SampleAppPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }

    @Step("Enter password")
    public SampleAppPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    @Step("Click Login / Logout button")
    public SampleAppPage clickLoginButton() {
        click(loginButton);
        return this;
    }

    @Step("Login with username: {username}")
    public SampleAppPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return this;
    }

    @Step("Get login status message")
    public String getLoginStatusText() {
        return getText(loginStatus);
    }
}
