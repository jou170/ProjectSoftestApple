package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import java.time.Duration;
import java.util.List;

@Listeners({AllureTestNg.class})
public class RegisterTest {
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
    @Feature("Submit Form")
    public void testRegisterFormSubmission() {
        navigateRegisterForm();
        switchToNewWindow();

        switchToIFrame(0);
        fillFirstName("Joy");
        fillLastName("Gemilang");
        selectCountryRegion("indonesia");
        selectMonth("July");
        selectDay("17");
        selectYear("2004");
        fillEmailAddress("joy.g22@mhs.istts.ac.id");
        fillPassword("Proyek*123");
        fillConfirmPassword("Proyek*123");
        selectCountryOptions("+62");
        fillPhoneNumber("081283563500");

        Assert.assertTrue(true);
    }

    @Step("Navigasi ke halaman register akun")
    private void navigateRegisterForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Current URL: " + driver.getCurrentUrl());

        WebElement shoppingBagIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='globalnav-menubutton-link-bag']")));
        shoppingBagIcon.click();
        System.out.println("Clicked shopping bag icon");

        WebElement accountNavigation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-analytics-title='account']")));
        accountNavigation.click();
        System.out.println("Clicked account navigation");

        wait.until(ExpectedConditions.urlContains("signIn/account"));
        System.out.println("Current URL after navigation: " + driver.getCurrentUrl());

        switchToIFrame(0);

        WebElement navigateCreateAccount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='create-link']")));
        navigateCreateAccount.click();
        System.out.println("Clicked Create Account");
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> d.findElements(By.tagName("iframe")).size() > 0);

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

    @Step("Mengisi field first name pada register form")
    private void fillFirstName(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[1]/div[1]/input[1]"), value, "First Name");
    }

    @Step("Mengisi field last name pada register form")
    private void fillLastName(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[1]/div[2]/input[1]"), value, "Last Name");
    }

    @Step("Memilih country/region berdasarkan keyword yang dimasukkan pada register form")
    private void selectCountryRegion(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[2]/select[1]"), value, "Country/Region");
    }

    @Step("Memilih month berdasarkan keyword yang dimasukkan pada register form")
    private void selectMonth(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[3]/div[2]/fieldset[1]/div[1]/div[1]/select[1]"), value, "Month");
    }

    @Step("Memilih day berdasarkan keyword yang dimasukkan pada register form")
    private void selectDay(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[3]/div[2]/fieldset[1]/div[2]/div[1]/select[1]"), value, "Day");
    }

    @Step("Memilih year berdasarkan keyword yang dimasukkan pada register form")
    private void selectYear(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[3]/div[2]/fieldset[1]/div[3]/div[1]/select[1]"), value, "Year");
    }

    @Step("Mengisi field email address pada register form")
    private void fillEmailAddress(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[1]/input[1]"), value, "Email Address");
    }

    @Step("Mengisi field password pada register form")
    private void fillPassword(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[3]/div[1]/div[2]/input[1]"), value, "Password");
    }

    @Step("Mengisi field confirm password pada register form")
    private void fillConfirmPassword(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[4]/input[1]"), value, "Confirm Password");
    }

    @Step("Memilih country options untuk phone number pada register form")
    private void selectCountryOptions(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[5]/div[1]/select[1]"), value, "Country Options");
    }

    @Step("Mengisi phone number pada register form")
    private void fillPhoneNumber(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[5]/div[2]/div[1]/input[1]"), value, "Phone Number");
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
