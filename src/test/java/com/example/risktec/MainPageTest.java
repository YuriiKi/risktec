package com.example.risktec;

import org.testng.annotations.*;

import static org.testng.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Configure Chrome options to fix issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://testpages.eviltester.com/styled/apps/7charval/simple7charvalidation.html");

        mainPage = new MainPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test(dataProvider = "inputTestDataProvider")
    public void inputTest(String inputText, String expectedResult) {
        mainPage.inputCharacters.sendKeys(inputText);
        mainPage.buttonValidate.click();
        String actualResult = mainPage.validationMessage.getAttribute("value");

        assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "inputTestDataProvider")
    public Object[][] getDataFromDataprovider() {
        /*
        I found that the validation does not work correctly with many ASCII symbols.
        For example, including characters :[\}^_` in the text returns "Valid Value",
        while the presence of the character A returns "Invalid Value".
        Therefore, this test checks the interaction with all ASCII symbols.
        In a real situation, this is unnecessary.
        It is possible to limit the checks according to the equivalence classes.
         */

        Object[][] objects = new Object[256][2];
        for (int i = 0; i <= 255; i++) {
            objects[i][0] = "123456" + (char) i;
            objects[i][1] = isValidValue((char) +i) ? "Valid Value" : "Invalid Value";
        }
        return objects;
    }

    private boolean isValidValue(char ch) {
        return (ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch == '*');
    }


    @Test(dataProvider = "inputSizeTestDataProvider")
    public void inputSizeTest(String inputText, String expectedResult) {
        mainPage.inputCharacters.sendKeys(inputText);
        mainPage.buttonValidate.click();
        String actualResult = mainPage.validationMessage.getAttribute("value");

        assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "inputSizeTestDataProvider")
    public Object[][] inputSizeTest() {
        return new Object[][]{
                {"", "Invalid Value"},
                {"123456", "Invalid Value"},
                {"1234567", "Valid Value"},
                {"12345678", "Invalid Value"}
        };
    }
}
