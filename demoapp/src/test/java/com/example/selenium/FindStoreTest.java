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
import java.util.List;

@Listeners({ AllureTestNg.class })
public class FindStoreTest {
    private WebDriver driver;
    private App app;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite: setting properti dari sistem");
    }

    @BeforeTest
    @Description("Set up browser dan buka aplikasi melalui url")
    public void setUp() {
        System.out.println("Before Test: siapkan apa saja yang dibutuhkan saat sebelum test");
        app = new App();
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class: membuka browser");
        driver = app.getDriver();
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method: navigasi ke website yang akan di test");
        driver.get("https://www.apple.com/");
    }

    @Test
    @Feature("Search")
    public void testNormalSearch() throws Exception {
        hoverOnStoreAndValidateDropdown();
        clickFindAStore();
        fillSearchBarAndEnter("jakarta");
        Thread.sleep(10000); // Adjust if needed for real page behavior

        Assert.assertTrue(true);
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] screenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Step("Beralih ke tab/jendela baru")
    private void switchToNewWindow() {
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        System.out.println("Switched to new window");
        System.out.println("Current URL: " + driver.getCurrentUrl());
    }

    @Step("Hover pada elemen Store dan validasi dropdown")
    private void hoverOnStoreAndValidateDropdown() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement storeNav = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[@aria-label='Store']//span[@class='globalnav-link-text-container']")));

        // Ensure the element is visible before performing the hover
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", storeNav);

        Actions actions = new Actions(driver);
        actions.moveToElement(storeNav).perform();

        // Validate the dropdown appears
        WebElement dropdown = wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//div[@data-analytics-region='quick links - store']")));
        Assert.assertNotNull(dropdown, "Dropdown tidak ditemukan setelah hover");
        System.out.println("Dropdown ditemukan: " + dropdown.getText());
    }

    @Step("Klik Find A Store")
    private void clickFindAStore() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchIcon = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@class='globalnav-submenu-link'][normalize-space()='Find a Store']")));
        searchIcon.click();
        System.out.println("Clicked find a store");
    }

    @Step("Mengisi field search bar dan tekan Enter")
    private void fillSearchBarAndEnter(String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@placeholder='Search by location, ZIP, or store name']")));

        // Clear the input to avoid leftover values from previous search attempts
        searchInput.clear();
        searchInput.sendKeys(value);

        // Ensure the input value is properly set before pressing Enter
        Assert.assertEquals(searchInput.getAttribute("value"), value, "Search bar value not set correctly!");

        // Simulate pressing Enter on the search input field
        searchInput.sendKeys(Keys.RETURN);
        System.out.println("Tombol Enter ditekan pada input search");
    }

    @Step("Klik suggested search")
    private void clickSuggestedSearch() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement suggestedSearch = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-label='Watch Bands']")));
        suggestedSearch.click();
        System.out.println("Clicked suggested search");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method: langkah untuk clear atau reload halaman jika diperlukan");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After Class: tutup browser");
        app.closeBrowser();
    }

    @AfterTest
    @Description("Tutup browser")
    public void afterTest() {
        app.closeBrowser();
        System.out.println("After Test: bersihkan semua resource yang tidak dipakai");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite: test suite lengkap");
    }
}
