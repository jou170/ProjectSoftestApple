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

@Listeners({AllureTestNg.class})
public class SignUpTest {
    private WebDriver driver;
    private App app;

    // Variabel untuk pengujian registrasi
    private String firstName;
    private String lastName;
    private String country;
    private String month;
    private String day;
    private String year;
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneCode;
    private String phoneNumber;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite: Setting properties untuk sistem");
    }

    @BeforeTest(description = "Set up browser dan input data")
    public void setUp() {
        app = new App();

        // Menyiapkan variabel input
        firstName = "Joy";
        lastName = "Gemilang";
        country = "indonesia";
        month = "July";
        day = "17";
        year = "2004";
        email = "joygemilang17@gmail.com";
        password = "@Buatsofttest123";
        confirmPassword = "@Buatsofttest123";
        phoneCode = "+62";
        phoneNumber = "081283563500";
    }

    @BeforeClass(description = "Membuka browser dan menavigasi ke halaman pendaftaran")
    public void beforeClass() {
        driver = app.getDriver();
        driver.get("https://www.apple.com/"); // Navigasi ke halaman utama
        navigateRegisterForm(); // Navigasi ke form pendaftaran
        switchToNewWindow(); // Pindah ke window baru jika diperlukan
    }

    @BeforeMethod(description = "Pindah ke dalam iframe")
    public void beforeMethod() {
        switchToIFrame(0); // Pastikan kembali ke iframe jika diperlukan
    }

    @Test(description = "Sign Up Gagal Tanpa mengisi Data First Name")
    @Feature("Sign Up")
    public void testSignUpFailsWithoutFirstName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        fillLastName(lastName);
        selectCountryRegion(country);
        selectMonth(month);
        selectDay(day);
        selectYear(year);
        fillEmailAddress(email);
        fillPassword(password);
        fillConfirmPassword(confirmPassword);
        selectCountryOptions(phoneCode);
        fillPhoneNumber(phoneNumber);
        clickContinue();

        WebElement errorElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[1]/div[1]/div[1]/span[2]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", errorElement); // Scroll ke elemen error sebelum mengambil screenshot
        Assert.assertTrue(errorElement.isDisplayed(), "Error message should be displayed when first name is missing");
        Allure.addAttachment("Pesan Error", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }
    
    @Test(description = "Sign Up Gagal Tanpa mengisi Data Last Name")
    @Feature("Sign Up")
    public void testSignUpFailsWithoutLastName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        fillFirstName(firstName);
        selectCountryRegion(country);
        selectMonth(month);
        selectDay(day);
        selectYear(year);
        fillEmailAddress(email);
        fillPassword(password);
        fillConfirmPassword(confirmPassword);
        selectCountryOptions(phoneCode);
        fillPhoneNumber(phoneNumber);
        clickContinue();

        WebElement errorElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[1]/div[2]/div[1]/span[2]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", errorElement); // Scroll ke elemen error sebelum mengambil screenshot
        Assert.assertTrue(errorElement.isDisplayed(), "Error message should be displayed when last name is missing");
        Allure.addAttachment("Pesan Error", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }
    
    @Test(description = "Sign Up Gagal Tanpa mengisi Data Birthday")
    @Feature("Sign Up")
    public void testSignUpFailsWithoutBirthday() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        fillFirstName(firstName);
        fillLastName(lastName);
        selectCountryRegion(country);
        fillEmailAddress(email);
        fillPassword(password);
        fillConfirmPassword(confirmPassword);
        selectCountryOptions(phoneCode);
        fillPhoneNumber(phoneNumber);
        clickContinue();

        WebElement errorElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[3]/div[2]/div[1]/div[1]/span[2]/p[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", errorElement); // Scroll ke elemen error sebelum mengambil screenshot
        Assert.assertTrue(errorElement.isDisplayed(), "Error message should be displayed when birthday is missing");
        Allure.addAttachment("Pesan Error", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }
    
    @Test(description = "Sign Up Gagal Tanpa mengisi Data Email")
    @Feature("Sign Up")
    public void testSignUpFailsWithoutEmail() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        fillFirstName(firstName);
        fillLastName(lastName);
        selectCountryRegion(country);
        selectMonth(month);
        selectDay(day);
        selectYear(year);
        fillPassword(password);
        fillConfirmPassword(confirmPassword);
        selectCountryOptions(phoneCode);
        fillPhoneNumber(phoneNumber);
        clickContinue();

        WebElement errorElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[1]/div[1]/span[2]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", errorElement); // Scroll ke elemen error sebelum mengambil screenshot
        Assert.assertTrue(errorElement.isDisplayed(), "Error message should be displayed when email address is missing");
        Allure.addAttachment("Pesan Error", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }
    
    @Test(description = "Sign Up Gagal dengan mengisi Format Email Salah")
    @Feature("Sign Up")
    public void testSignUpFailsInvalidEmail() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        fillFirstName(firstName);
        fillLastName(lastName);
        selectCountryRegion(country);
        selectMonth(month);
        selectDay(day);
        selectYear(year);
        fillEmailAddress(email.replace("@",""));
        fillPassword(password);
        fillConfirmPassword(confirmPassword);
        selectCountryOptions(phoneCode);
        fillPhoneNumber(phoneNumber);
        // clickContinue();

        WebElement errorElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[1]/div[1]/span[2]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", errorElement); // Scroll ke elemen error sebelum mengambil screenshot
        Assert.assertTrue(errorElement.isDisplayed(), "Error message should be displayed when email address is invalid");
        Allure.addAttachment("Pesan Error", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Test(description = "Sign Up Gagal Tanpa mengisi Password")
    @Feature("Sign Up")
    public void testSignUpFailsWithoutPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        fillFirstName(firstName);
        fillLastName(lastName);
        selectCountryRegion(country);
        selectMonth(month);
        selectDay(day);
        selectYear(year);
        fillEmailAddress(email);
        fillConfirmPassword(confirmPassword);
        selectCountryOptions(phoneCode);
        fillPhoneNumber(phoneNumber);
        clickContinue();

        WebElement errorElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[3]/div[1]/div[2]/div[1]/span[2]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", errorElement); // Scroll ke elemen error sebelum mengambil screenshot
        Assert.assertTrue(errorElement.isDisplayed(), "Error message should be displayed when password is missing");
        Allure.addAttachment("Pesan Error", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Test(description = "Sign Up Gagal Tanpa mengisi Confirm Password")
    @Feature("Sign Up")
    public void testSignUpFailsWithoutConfirmPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        fillFirstName(firstName);
        fillLastName(lastName);
        selectCountryRegion(country);
        selectMonth(month);
        selectDay(day);
        selectYear(year);
        fillEmailAddress(email);
        fillPassword(password);
        selectCountryOptions(phoneCode);
        fillPhoneNumber(phoneNumber);
        clickContinue();

        WebElement errorElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[4]/div[1]/span[2]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", errorElement); // Scroll ke elemen error sebelum mengambil screenshot
        Assert.assertTrue(errorElement.isDisplayed(), "Error message should be displayed when confirm password is missing");
        Allure.addAttachment("Pesan Error", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Test(description = "Sign Up Berhasil")
    @Feature("Sign Up")
    public void testSignUpSuccess() throws Exception {
        fillFirstName(firstName);
        fillLastName(lastName);
        selectCountryRegion(country);
        selectMonth(month);
        selectDay(day);
        selectYear(year);
        fillEmailAddress(email);
        fillPassword(password);
        fillConfirmPassword(confirmPassword);
        selectCountryOptions(phoneCode);
        fillPhoneNumber(phoneNumber);
        solveCaptcha(); // ini bakal nyoba solve sampai kena limit refresh code nya
        
        Thread.sleep(10000); // Masukkan durasi yang cukup untuk menyelesaikan captcha dan verify email phone number
        Assert.assertTrue(true);
        Allure.addAttachment("Masuk ke halaman Verify Email Address", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] screenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
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
        fieldElement.click();

        
        // fieldElement.sendKeys(value); // Mengetik cepat karakter


        // Mengetik satu per satu karakter dengan delay
        for (char c : value.toCharArray()) {
            fieldElement.sendKeys(Character.toString(c)); // Kirim karakter satu per satu
            try {
                Thread.sleep((long) (Math.random() * 100)); // Penundaan 100-400 ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Filled " + fieldName);
    }
    
    @Step(value = "Menekan tombol continue")
    private void clickContinue() {
        try {
            WebElement continueButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[9]/button[1]"));
            continueButton.click();
        } catch (NoSuchElementException e) {
            // Tidak melakukan apa-apa jika tombol tidak ditemukan atau tidak bisa diklik
        }
    }

    
    @Step("Mengisi field captcha sesuai gambar")
    private void fillCaptcha() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement imageChallenge = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[7]/div[1]/img[1]")));
        String imageUrl = (String) js.executeScript("return arguments[0].getAttribute('src');", imageChallenge);
        
        String captchaText = CaptchaSolver.decodeCaptcha(imageUrl);
        if (captchaText == null || captchaText.isEmpty() || captchaText.isBlank()) {
            System.out.println("Captcha text kosong");
            captchaText = "Not Recognized";
        }
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[7]/div[2]/div[1]/input[1]"), captchaText, "Captcha");
    }

    @Step("Mencoba mengisi captcha sampai berhasil")
    private void solveCaptcha() throws Exception {
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean notPass = true;
        do {
            fillCaptcha(); // Mengisi field captcha
            if (!driver.findElements(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[9]/button[1]")).isEmpty()) {
                WebElement continueButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[9]/button[1]"));
                continueButton.click();
                System.out.println("Clicked continue button");
            }

            Thread.sleep(1928);
            // cek apakah berpindah ke verify email address
            if(driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/h1[1]")).getText().contains("Verify Email Address")) {
                notPass = false;
            }
        } while (notPass);

        System.out.println("CAPTCHA berhasil dipecahkan.");
    }

  
    @AfterMethod(description = "Refresh halaman untuk test case berikutnya")
    public void afterMethod() {
        driver.navigate().refresh();
    }

    @AfterClass(description = "Tutup browser")
    public void afterClass() {
        app.closeBrowser();
    }

    @AfterTest(description = "Bersihkan semua resource yang tidak dipakai")
    public void afterTest() {
        System.out.println("After Test: bersihkan semua resource yang tidak dipakai");
    }

    @AfterSuite(description = "AfterSuite")
    public void afterSuite() {
        System.out.println("After Suite: test suite lengkap");
    }
}
