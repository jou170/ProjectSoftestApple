package com.example.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;

import java.io.ByteArrayInputStream;
import java.time.Duration;

@Listeners({ AllureTestNg.class })
public class BagTest {
    private WebDriver driver;
    private App app;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite: Setting system properties.");
    }

    @BeforeTest
    @Description("Set up browser and open the application URL.")
    public void setUp() {
        System.out.println("Before Test: Preparing required resources before tests.");
        app = new App();
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class: Launching the browser.");
        driver = app.getDriver();
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method: Navigating to the test website.");
        driver.get("https://www.apple.com");
    }

    @Test
    @Feature("TC020")
    @Description("Add to Bag Berhasil")
    public void test1() throws Exception {
        Thread.sleep(3000);
        fieldClick("//a[@aria-label='Store']//span[@class='globalnav-link-text-container']");
        Thread.sleep(3000);
        fieldClick("//span[normalize-space()='40mm Plum Sport Loop']");
        Thread.sleep(3000);
        fieldClick("//label[@for=':r2:']//img[@class='colornav-swatch']");
        // fieldClick("//input[@value='42mm']");
        fieldClick("//button[@id='add-to-cart']");
        Thread.sleep(3000);
        takeScreenshot();
        Thread.sleep(10000);
    }

    @Test
    @Feature("TC021")
    @Description("Update Bag")
    public void test2() throws Exception {
        Thread.sleep(3000);
        fieldClick("//a[@id='globalnav-menubutton-link-bag']");
        fieldClick("//a[@class='globalnav-flyout-item ac-gn-bagview-button ac-gn-bagview-button-pill']");
        selectOptionByValue("rs-quantity-dropdown", "4");
        Thread.sleep(3000);
        takeScreenshot();
        Thread.sleep(10000);
    }

    @Test
    @Feature("TC022")
    @Description("Membuang salah satu item di bag")
    public void test3() throws Exception {
        Thread.sleep(3000);
        fieldClick("//a[@id='globalnav-menubutton-link-bag']");
        fieldClick("//a[@class='globalnav-flyout-item ac-gn-bagview-button ac-gn-bagview-button-pill']");
        fieldClick("//span[contains(text(),'Remove')]");
        Thread.sleep(3000);
        takeScreenshot();
        Thread.sleep(10000);
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot() {
        try {
            Allure.addAttachment("Hasil test",
                new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    @Step("Switch to new tab/window.")
    private void switchToNewWindow() {
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        System.out.println("Switched to a new window.");
    }

    @Step("Fill the search bar with the value and press Enter.")
    private void fillSearchBarAndEnter(String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Search by location, ZIP, or store name']")));

        // Clear input field properly using clear() method
        searchInput.clear();

        // Adding a small wait to ensure that the input field is properly cleared
        try {
            Thread.sleep(500); // Optional: Add a wait to ensure that the input field is cleared
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Send the search value directly to the input field
        searchInput.sendKeys(value);

        searchInput.sendKeys(Keys.RETURN);
        System.out.println("Search submitted with value: " + value);
    }

    @Step("Fill the search bar with the value and press Enter.")
    private void fillSearchBar(String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Search by location, ZIP, or store name']")));

        // Clear input field properly using clear() method
        searchInput.clear();

        // Adding a small wait to ensure that the input field is properly cleared
        try {
            Thread.sleep(500); // Optional: Add a wait to ensure that the input field is cleared
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Send the search value directly to the input field
        searchInput.sendKeys(value);

        System.out.println("Search submitted with value: " + value);
    }

    @Step("Memilih option combobox")
    private void selectOptionByValue(String selectElementId, String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Tunggu hingga elemen <select> terlihat
        WebElement selectElement = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.className(selectElementId)));

        // Gunakan kelas Select untuk memilih opsi berdasarkan value
        Select select = new Select(selectElement);
        select.selectByValue(value);
    }

    @Step("Click field")
    private void fieldClick(String path) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        field.click();
    }

    @Step("Click 'Find a Store'.")
    private void clickFindAStore() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@class='globalnav-submenu-link'][normalize-space()='Find a Store']")));
        searchIcon.click();
        System.out.println("Clicked 'Find a Store'.");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method: Performing cleanup or resetting state.");
        try {
            // Take an action if needed
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After Class: Closing the browser.");
        app.closeBrowser();
    }

    @AfterTest
    @Description("Clean up resources.")
    public void afterTest() {
        System.out.println("After Test: Cleaning up unused resources.");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite: Test suite execution completed.");
    }
}
