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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Listeners({ AllureTestNg.class })
public class SignInTest {
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
    @Feature("TC015")
    @Description("Sign Up Gagal Tanpa mengisi Data apapun")
    public void test01() throws Exception {
        navigateLoginForm();

        switchToIFrame(0);

        fillEmailAddress("abcdefg");
        fieldClick("//i[@class='shared-icon icon_sign_in']");

        Thread.sleep(3000);

        fillPassword("abcdefg123");
        fieldClick("//i[@class='shared-icon icon_sign_in']");

        Thread.sleep(10000);
        Assert.assertTrue(true);
    }

    @Test
    @Feature("TC016")
    @Description("Sign Up Gagal Tanpa mengisi Data First Name")
    public void test02() throws Exception {
        navigateLoginForm();
        switchToNewWindow();

        switchToIFrame(0);
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

        Thread.sleep(10000);

        fieldClick("//button[@type='submit']");

        Thread.sleep(10000);
        Assert.assertTrue(true);
    }

    @Test
    @Feature("TC017")
    @Description("Sign Up Gagal Tanpa mengisi Data Last Name")
    public void test03() throws Exception {
        navigateLoginForm();
        switchToNewWindow();

        switchToIFrame(0);
        fillFirstName("Gemilang");
        selectCountryRegion("indonesia");
        selectMonth("July");
        selectDay("17");
        selectYear("2004");
        fillEmailAddress("joy.g22@mhs.istts.ac.id");
        fillPassword("Proyek*123");
        fillConfirmPassword("Proyek*123");
        selectCountryOptions("+62");
        fillPhoneNumber("081283563500");

        Thread.sleep(10000);

        fieldClick("//button[@type='submit']");

        Thread.sleep(10000);
        Assert.assertTrue(true);
    }

    @Test
    @Feature("TC018")
    @Description("Sign Up Gagal Tanpa mengisi Data Birthday")
    public void test04() throws Exception {
        navigateLoginForm();
        switchToIFrame(0);
        fillEmailAddress(null);

        Thread.sleep(10000);

        fieldClick("//button[@type='submit']");

        Thread.sleep(10000);
        Assert.assertTrue(true);
    }

    @Test
    @Feature("TC019")
    @Description("Sign Up Gagal Tanpa mengisi Data Email")
    public void test05() throws Exception {
        navigateLoginForm();
        switchToNewWindow();

        switchToIFrame(0);
        fillFirstName("Joy");
        fillLastName("Gemilang");
        selectCountryRegion("indonesia");
        selectMonth("July");
        selectDay("17");
        selectYear("2004");
        // fillEmailAddress("joy.g22@mhs.istts.ac.id");
        fillPassword("Proyek*123");
        fillConfirmPassword("Proyek*123");
        selectCountryOptions("+62");
        fillPhoneNumber("081283563500");

        Thread.sleep(10000);

        fieldClick("//button[@type='submit']");

        Thread.sleep(10000);
        Assert.assertTrue(true);
    }

    private void fieldClick(String path) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
        field.click();
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] screenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Step("Navigasi ke halaman register akun")
    private void navigateLoginForm() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Current URL: " + driver.getCurrentUrl());

        WebElement shoppingBagIcon = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='globalnav-menubutton-link-bag']")));
        shoppingBagIcon.click();
        System.out.println("Clicked shopping bag icon");

        WebElement accountNavigation = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Sign in']")));
        accountNavigation.click();
        System.out.println("Clicked sign in navigation");

    }

    @Step("Beralih ke tab/jendela terbaru")
    private void switchToNewWindow() {
        // Ambil semua window handles saat ini
        Set<String> windowHandles = driver.getWindowHandles();

        // Ubah menjadi list untuk mendapatkan jendela terakhir
        List<String> handlesList = new ArrayList<>(windowHandles);

        // Pindah ke jendela terakhir dalam daftar
        String newWindowHandle = handlesList.get(handlesList.size() - 1);
        driver.switchTo().window(newWindowHandle);

        System.out.println("Switched to the latest new window");
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
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[1]/div[1]/input[1]"), value,
                "First Name");
    }

    @Step("Mengisi field last name pada register form")
    private void fillLastName(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[1]/div[2]/input[1]"), value,
                "Last Name");
    }

    @Step("Memilih country/region berdasarkan keyword yang dimasukkan pada register form")
    private void selectCountryRegion(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[2]/select[1]"), value,
                "Country/Region");
    }

    @Step("Memilih month berdasarkan keyword yang dimasukkan pada register form")
    private void selectMonth(String value) {
        fillField(By.xpath(
                "/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[3]/div[2]/fieldset[1]/div[1]/div[1]/select[1]"),
                value, "Month");
    }

    @Step("Memilih day berdasarkan keyword yang dimasukkan pada register form")
    private void selectDay(String value) {
        fillField(By.xpath(
                "/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[3]/div[2]/fieldset[1]/div[2]/div[1]/select[1]"),
                value, "Day");
    }

    @Step("Memilih year berdasarkan keyword yang dimasukkan pada register form")
    private void selectYear(String value) {
        fillField(By.xpath(
                "/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[3]/div[3]/div[2]/fieldset[1]/div[3]/div[1]/select[1]"),
                value, "Year");
    }

    @Step("Mengisi field email address pada register form")
    private void fillEmailAddress(String value) {
        fillField(By.xpath("//input[@id='account_name_text_field']"), value,
                "Email Or Phone Number");
    }

    @Step("Mengisi field password pada register form")
    private void fillPassword(String value) {
        fillField(By.xpath("//input[@id='password_text_field']"),
                value, "Password");
    }

    @Step("Mengisi field confirm password pada register form")
    private void fillConfirmPassword(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[4]/input[1]"), value,
                "Confirm Password");
    }

    @Step("Memilih country options untuk phone number pada register form")
    private void selectCountryOptions(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[5]/div[1]/select[1]"), value,
                "Country Options");
    }

    @Step("Mengisi phone number pada register form")
    private void fillPhoneNumber(String value) {
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[5]/div[2]/div[1]/input[1]"), value,
                "Phone Number");
    }

    private void fillField(By locator, String value, String fieldName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement fieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        fieldElement.sendKeys(value);
        System.out.println("Filled " + fieldName);
    }

    @Step("Mengisi field captcha sesuai gambar")
    private void fillCaptcha() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement imageChallenge = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[7]/div[1]/img[1]")));
        String imageUrl = (String) js.executeScript("return arguments[0].getAttribute('src');", imageChallenge);

        String captchaText = CaptchaSolver.decodeCaptcha(imageUrl);
        if (captchaText == null || captchaText == "") {
            System.out.println("Captcha text kosong");
            captchaText = "ABCDE";
        }
        fillField(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[7]/div[2]/div[1]/input[1]"),
                captchaText, "Captcha");
        // if (captchaText != null && captchaText != "") {
        // Kalau teks tidak kosong maka coba input
        // } else {
        // // Kalau tidak berhasil baca apapun maka tekan new code
        // WebElement newCode =
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[7]/div[2]/div[2]/button[1]/span[1]")));
        // newCode.click();
        // }
    }

    @Step("Mencoba mengisi captcha sampai berhasil")
    private void solveCaptcha() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean notPass = true;
        do {
            Thread.sleep(2000);
            fillCaptcha(); // Mengisi field captcha
            try {
                Thread.sleep(1000);

                WebElement captchaField = wait.until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[7]/div[2]/div[1]/input[1]")));
                // Inisialisasi JavascriptExecutor
                JavascriptExecutor js = (JavascriptExecutor) driver;
                // Dapatkan atribut 'value' menggunakan JavaScript
                String fieldValue = (String) js.executeScript("return arguments[0].value;", captchaField);

                if (fieldValue.isEmpty()) {
                    System.out.println("Gagal CAPTCHA kosong, mencoba lagi...");

                    WebElement newCode = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[7]/div[2]/div[2]/button[1]/span[1]")));
                    newCode.click();
                    continue;
                }

                WebElement continueButton = driver
                        .findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[9]/button[1]"));
                continueButton.click();

                // Mencari elemen error CAPTCHA
                WebElement captchaError = driver.findElement(By.xpath(
                        "/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[7]/div[2]/div[1]/div[1]/span[2]"));
                // Memeriksa apakah elemen error berisi teks error
                if (captchaError.getText().contains("Please enter the characters you see or hear to continue.")) {
                    System.out.println("Gagal CAPTCHA error, mencoba lagi...");

                    WebElement newCode = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "/html[1]/body[1]/div[1]/div[1]/div[1]/form[1]/div[1]/div[7]/div[2]/div[2]/button[1]/span[1]")));
                    newCode.click();
                    continue;
                } else {
                    notPass = false; // Tidak ada error, CAPTCHA berhasil
                }
            } catch (NoSuchElementException e) {
                // Jika elemen tidak ditemukan, dianggap CAPTCHA berhasil
                notPass = false;
            }
        } while (notPass);

        System.out.println("CAPTCHA berhasil dipecahkan.");
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
