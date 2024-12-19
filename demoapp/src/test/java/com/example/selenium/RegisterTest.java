package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;

import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
public class RegisterTest
{
    private WebDriver driver;
    public App app;

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("Before Suite: setting properti dari sistem");
    }

    @BeforeTest
    @Description("Set up browser dan buka aplikasi melalui url")
    public void setUp()
    {
        System.out.println("Before Test: siapkan apa saja yang dibutuhkan saat sebelum test");
        app = new App();
    }

    @BeforeClass
    public void beforeClass(){
        System.out.println("Before Class: membuka browser");
        driver = app.getDriver();
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("Before Method: navigasi ke website yang akan di test");
        driver.get("https://www.apple.com/");
    }

    @Test
    @Feature("Submit Form")
    public void testRegisterFormSubmission()
    {
        navigateRegisterForm();
        switchToNewWindow();
        
        switchToIFrame(0); 
        // semua elemen form ada di dalam iframe, jadi harus pindah ke iframe dulu
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
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(10)); // Explicit wait 10 detik
        System.out.println("Current URL: " + app.getDriver().getCurrentUrl());
        
        // Tunggu shopping bag icon untuk muncul dan klik
        WebElement shoppingBagIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='globalnav-menubutton-link-bag']")));
        shoppingBagIcon.click();
        System.out.println("Clicked shopping bag icon");

        // Tunggu elemen Account navigation muncul dan klik
        WebElement accountNavigation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-analytics-title='account']")));
        accountNavigation.click();
        System.out.println("Clicked account navigation");

        // Tunggu hingga halaman account selesai dimuat
        wait.until(ExpectedConditions.urlContains("signIn/account"));
        System.out.println("Current URL after navigation: " + app.getDriver().getCurrentUrl());

        switchToIFrame(0);

       // Tunggu elemen 'Create Account' muncul dan klik
        WebElement navigateCreateAccount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='create-link']"))); // elemen ini di dalam iframe
        navigateCreateAccount.click();
        System.out.println("Clicked Create Account");
        
        // Cetak URL sebelum switch window
        System.out.println("Current URL: " + app.getDriver().getCurrentUrl());
    }

    @Step("Beralih ke tab/jendela baru")
    private void switchToNewWindow(){
        //  Beralih ke tab atau jendela baru
        String originalWindow = app.getDriver().getWindowHandle();
        for (String windowHandle : app.getDriver().getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                app.getDriver().switchTo().window(windowHandle);
                break;
            }
        }
        System.out.println("Switched to new window");
        System.out.println("Current URL: " + app.getDriver().getCurrentUrl());
    }

    @Step("Beralih ke iframe pertama")
    private void switchToIFrame(int index)
    {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(10)); // Explicit wait 10 detik
        wait.until(driver -> driver.findElements(By.tagName("iframe")).size() > 0); // tunggu sampai setidaknya ada 1 iframe
        
        List<WebElement> iframes = app.getDriver().findElements(By.tagName("iframe"));
        System.out.println("Jumlah iframe di halaman: " + iframes.size());
        
        app.getDriver().switchTo().frame(iframes.get(index)); // Pindah ke iframe sesuai index nya
        System.out.println("Berada di dalam iframe ke-" + index);
    }

    @Step("Beralih ke default frame")
    private void switchToDefaultContent()
    {
        app.getDriver().switchTo().defaultContent();
        System.out.println("Kembali ke konteks utama");
    }

    @Step("Mengisi field first name pada register form")
    private void fillFirstName(String value)
    {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(10)); // Explicit wait 10 detik
        WebElement fieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[1]/div[1]/input[1]")));
        fieldElement.sendKeys(value);
        System.out.println("Filled First Name");
    }

    @Step("Mengisi field last name pada register form")
    private void fillLastName(String value)
    {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(10)); // Explicit wait 10 detik
        WebElement fieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[1]/div[2]/input[1]")));
        fieldElement.sendKeys(value);
        System.out.println("Filled Last Name");
    }

    @Step("Memilih country/region berdasarkan keyword yang dimasukkan pada register form")
    private void selectCountryRegion(String value)
    {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(10)); // Explicit wait 10 detik
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[2]/select[1]")));
        selectElement.sendKeys(value);
        System.out.println("Filled Country/Region");
    }

    @Step("Memilih month berdasarkan keyword yang dimasukkan pada register form")
    private void selectMonth(String value)
    {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(10)); // Explicit wait 10 detik
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[3]/div[2]/fieldset[1]/div[1]/div[1]/select[1]")));
        selectElement.sendKeys(value);
        System.out.println("Filled Month");
    }

    @Step("Memilih day berdasarkan keyword yang dimasukkan pada register form")
    private void selectDay(String value)
    {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(10)); // Explicit wait 10 detik
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[3]/div[2]/fieldset[1]/div[2]/div[1]/select[1]")));
        selectElement.sendKeys(value);
        System.out.println("Filled Day");
    }

    @Step("Memilih year berdasarkan keyword yang dimasukkan pada register form")
    private void selectYear(String value)
    {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(10)); // Explicit wait 10 detik
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[3]/div[2]/fieldset[1]/div[3]/div[1]/select[1]")));
        selectElement.sendKeys(value);
        System.out.println("Filled Year");
    }

    @Step("Mengisi field email address pada register form")
    private void fillEmailAddress(String value)
    {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(10)); // Explicit wait 10 detik
        WebElement fieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[1]/input[1]")));
        fieldElement.sendKeys(value);
        System.out.println("Filled Email Address");
    }

    @Step("Mengisi field password pada register form")
    private void fillPassword(String value)
    {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(10)); // Explicit wait 10 detik
        WebElement fieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[3]/div[1]/div[2]/input[1]")));
        fieldElement.sendKeys(value);
        System.out.println("Filled Password");
    }

    @Step("Mengisi field confirm password pada register form")
    private void fillConfirmPassword(String value)
    {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(10)); // Explicit wait 10 detik
        WebElement fieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[4]/input[1]")));
        fieldElement.sendKeys(value);
        System.out.println("Filled Confirm Password");
    }

    @Step("Memilih country options untuk phone number pada register form")
    private void selectCountryOptions(String value)
    {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(10)); // Explicit wait 10 detik
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[5]/div[1]/select[1]")));
        selectElement.sendKeys(value);
        System.out.println("Filled Country Options");
    }
    
    @Step("Mengisi phone number pada register form")
    private void fillPhoneNumber(String value)
    {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(10)); // Explicit wait 10 detik
        WebElement fieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[5]/div[2]/div[1]/input[1]")));
        fieldElement.sendKeys(value);
        System.out.println("Filled Phone Number");
    }
    
    @AfterMethod
    public void afterMethod(){
        System.out.println("After Method: langkah untuk clear atau reload halaman jika diperlukan");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("After Class: tutup browser");
        app.closeBrowser();
    }

    @Description("Tutup browser")
    @AfterTest
    public void afterTest()
    {
        app.closeBrowser();
        System.out.println("After Test: bersihkan semua resource yang tidak dipakai");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("After Suite: test suite lengkap");
    }
}
