package com.example.risktec;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = https://testpages.eviltester.com/styled/apps/7charval/simple7charvalidation.html
public class MainPage {

    @FindBy(xpath = "//input[@name='characters']")
    public WebElement inputCharacters;

    @FindBy(xpath = "//input[@name='validation_message']")
    public WebElement validationMessage;

    @FindBy(xpath = "//input[@type='button']")
    public WebElement buttonValidate;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
