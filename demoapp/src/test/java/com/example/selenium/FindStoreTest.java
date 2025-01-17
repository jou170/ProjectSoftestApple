package com.example.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;

import java.time.Duration;

@Listeners({ AllureTestNg.class })
public class FindStoreTest {
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
        driver.get("https://www.apple.com/retail/");
    }

    @Test
    @Feature("Search")
    @Description("Test normal search functionality.")
    public void testNormalSearch() {
        fillSearchBarAndEnter("jakarta");
        System.out.println("Search test completed successfully.");
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot() {
        try {
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

    @Step("Hover over 'Store' and validate dropdown using JavaScript.")
    private void hoverOnStoreAndValidateDropdown() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement storeNav = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@aria-label='Store']//span[@class='globalnav-link-text-container']")));

        // Using JavascriptExecutor to simulate hover if Actions does not work
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseover', {bubbles: true}));", storeNav);

        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@data-analytics-region='quick links - store']")));
        Assert.assertNotNull(dropdown, "Dropdown is not visible after hovering.");
        System.out.println("Dropdown found: " + dropdown.getText());
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

        // Verify the input value is set as expected
        Assert.assertEquals(searchInput.getAttribute("value"), value, "Search bar value does not match input!");

        // Ensure the input field is focused
        searchInput.click(); // Focus the input before sending Enter

        // Submit the form using Enter key
        searchInput.sendKeys(Keys.RETURN);
        System.out.println("Search submitted with value: " + value);
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
            takeScreenshot(); // Take a screenshot if needed
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
