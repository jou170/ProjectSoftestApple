package com.example.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.List;

@Listeners({ AllureTestNg.class })
public class SearchTest {
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
        clickSearchIcon();
        fillSearchBar("phone");
        doSearch();
        Thread.sleep(10000);

        Assert.assertTrue(true);
    }

    @Test
    @Feature("Search")
    public void testSuggestedSearch() throws Exception {
        clickSearchIcon();
        fillSearchBar("watch");
        clickSuggestedSearch();
        Thread.sleep(10000);

        Assert.assertTrue(true);
    }

    @Test
    @Feature("Search")
    public void testMisspelledNormalSearch() throws Exception {
        clickSearchIcon();
        fillSearchBar("iphome");
        doSearch();
        Thread.sleep(10000);

        Assert.assertTrue(true);
    }

    @Test
    @Feature("Search")
    public void testWrongSearch() throws Exception {
        clickSearchIcon();
        fillSearchBar("awjdbh");
        doSearch();
        Thread.sleep(10000);

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

    @Step("Beralih ke iframe pertama")
    private void switchToIFrame(int index) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.findElements(By.tagName("iframe")).size() > 0);
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        System.out.println("Jumlah iframe di halaman: " + iframes.size());

        driver.switchTo().frame(iframes.get(index));
        System.out.println("Berada di dalam iframe ke-" + index);
    }

    @Step("Beralih ke default frame")
    private void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        System.out.println("Kembali ke konteks utama");
    }

    @Step("Mengisi field search bar")
    private void fillSearchBar(String value) {
        fillField(By.xpath("//input[@placeholder='Search apple.com']"), value, "First Name");
    }

    @Step("Klik ikon search")
    private void clickSearchIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Current URL: " + driver.getCurrentUrl());

        WebElement searchIcon = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='globalnav-menubutton-link-search']")));
        searchIcon.click();
        System.out.println("Clicked search icon");
    }

    @Step("Klik suggested search")
    private void clickSuggestedSearch() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Current URL: " + driver.getCurrentUrl());

        WebElement searchIcon = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-label='Watch Bands']")));
        searchIcon.click();
        System.out.println("Clicked suggested search");
    }

    @Step("Klik button search")
    private void doSearch() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Current URL: " + driver.getCurrentUrl());

        WebElement searchIcon = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Submit search']")));
        searchIcon.click();
        System.out.println("Clicked search icon");
    }

    private void fillField(By locator, String value, String fieldName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement fieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        fieldElement.sendKeys(value);
        System.out.println("Filled " + fieldName);
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
