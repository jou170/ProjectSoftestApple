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
import java.util.List;

@Listeners({ AllureTestNg.class })
public class CheckoutTest {
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

    private void fillField(By locator, String value, String fieldName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement fieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        fieldElement.sendKeys(value);
        System.out.println("Filled " + fieldName);
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
    @Feature("TC029")
    @Description("Melakukan Checkout product yang di perjual belikan")
    public void testCheckout() throws Exception {
        fieldClick("//a[@aria-label='Store']//span[@class='globalnav-link-text-container']");
        fieldClick("//span[normalize-space()='40mm Plum Sport Loop']");
        fieldClick("//label[@for=':r2:']//img[@class='colornav-swatch']");
        fieldClick("//button[@id='add-to-cart']");
        fieldClick("//button[@id='shoppingCart.actions.checkoutOtherPayments']");
        fieldClick("//button[@id='signIn.guestLogin.guestLogin']");
        fillField(By.xpath("//input[@type='text']"), "02108", "checkout.fulfillment.pickupTab.pickup.storeLocator.searchInput");
        fieldClick("//span[normalize-space()='Apply']");
        fieldClick("//span[contains(text(),'Continue to Shipping Address')]");
        fillField(By.xpath("//input[@id='checkout.shipping.addressSelector.newAddress.address.firstName']"), "Test", "checkout.shipping.addressSelector.newAddress.address.firstName");
        fillField(By.xpath("//input[@id='checkout.shipping.addressSelector.newAddress.address.lastName']"), "Checkout", "checkout.shipping.addressSelector.newAddress.address.firstName");
        fillField(By.xpath("//input[@id='checkout.shipping.addressSelector.newAddress.address.street']"), "310 Washington St, Boston, MA 02108, United States", "checkout.shipping.addressSelector.newAddress.address.firstName");
        fillField(By.xpath("//input[@id='checkout.shipping.addressSelector.newAddress.address.street2']"), "-", "checkout.shipping.addressSelector.newAddress.address.firstName");
        fillField(By.xpath("//input[@id='checkout.shipping.addressContactEmail.address.emailAddress']"), "billie.n22@mhs.istts.ac.id", "checkout.shipping.addressSelector.newAddress.address.firstName");
        fillField(By.xpath("//input[@id='checkout.shipping.addressContactPhone.address.fullDaytimePhone']"), "(091) 203-9133", "checkout.shipping.addressSelector.newAddress.address.firstName");
        fieldClick("//button[@id='rs-checkout-continue-button-bottom']");
        fieldClick("//label[@id='checkout.billing.billingoptions.paypal_label']//span[@class='form-selector-left-col large-6 small-7']");
        fieldClick("//button[@id='rs-checkout-continue-button-bottom']");
        Thread.sleep(10000);
        takeScreenshot();
        Thread.sleep(3000);
    }

    @Test
    @Feature("TC030")
    @Description("Melakukan Checkout product yang di perjual belikan menggunakan Apple Pay")
    public void testCheckoutWithApplePay() throws Exception {
        fieldClick("//a[@aria-label='Store']//span[@class='globalnav-link-text-container']");
        fieldClick("//span[normalize-space()='40mm Plum Sport Loop']");
        fieldClick("//label[@for=':r2:']//img[@class='colornav-swatch']");
        fieldClick("//button[@id='pdp-options-applePay']");
        fillField(By.xpath("//input[@type='text']"), "02108", "checkout.fulfillment.pickupTab.pickup.storeLocator.searchInput");
        fieldClick("//span[normalize-space()='Apply']");
        fieldClick("//span[@class='rs-checkout-applepay-buttonlogo']");
        Thread.sleep(5000);
        takeScreenshot();
        Thread.sleep(10000);
    }
    
    @Test
    @Feature("TC031")
    @Description("Melakukan Checkout product yang di perjual belikan")
    public void testCheckoutWithInvalidZipCode() throws Exception {
        Thread.sleep(3000);
        fieldClick("//a[@aria-label='Store']//span[@class='globalnav-link-text-container']");
        Thread.sleep(3000);
        fieldClick("//span[normalize-space()='40mm Plum Sport Loop']");
        Thread.sleep(3000);
        fieldClick("//label[@for=':r2:']//img[@class='colornav-swatch']");
        // fieldClick("//input[@value='42mm']");
        fieldClick("//button[@id='add-to-cart']");
        Thread.sleep(10000);
        fieldClick("//button[@id='shoppingCart.actions.checkoutOtherPayments']");
        Thread.sleep(10000);
        fieldClick("//button[@id='signIn.guestLogin.guestLogin']");
        // Thread.sleep(10000);
        fillField(By.xpath("//input[@type='text']"), "johnDoe", "checkout.fulfillment.pickupTab.pickup.storeLocator.searchInput");
        // Thread.sleep(10000);
        fieldClick("//span[normalize-space()='Apply']");
        Thread.sleep(5000);
        takeScreenshot();
        Thread.sleep(10000);
    }

    @Test
    @Feature("TC032")
    @Description("Melakukan Checkout product yang di perjual belikan")
    public void testCheckoutWithValidZipCode() throws Exception {
        Thread.sleep(3000);
        fieldClick("//a[@aria-label='Store']//span[@class='globalnav-link-text-container']");
        Thread.sleep(3000);
        fieldClick("//span[normalize-space()='40mm Plum Sport Loop']");
        Thread.sleep(3000);
        fieldClick("//label[@for=':r2:']//img[@class='colornav-swatch']");
        // fieldClick("//input[@value='42mm']");
        fieldClick("//button[@id='add-to-cart']");
        Thread.sleep(10000);
        fieldClick("//button[@id='shoppingCart.actions.checkoutOtherPayments']");
        Thread.sleep(10000);
        fieldClick("//button[@id='signIn.guestLogin.guestLogin']");
        // Thread.sleep(10000);
        fillField(By.xpath("//input[@type='text']"), "02112", "checkout.fulfillment.pickupTab.pickup.storeLocator.searchInput");
        // Thread.sleep(10000);
        fieldClick("//span[normalize-space()='Apply']");
        Thread.sleep(5000);
        fieldClick("//span[contains(text(),'Continue to Shipping Address')]");
        Thread.sleep(5000);
        fillField(By.xpath("//input[@id='checkout.shipping.addressSelector.newAddress.address.firstName']"), "Test", "checkout.shipping.addressSelector.newAddress.address.firstName");
        fillField(By.xpath("//input[@id='checkout.shipping.addressSelector.newAddress.address.lastName']"), "Checkout", "checkout.shipping.addressSelector.newAddress.address.firstName");
        fillField(By.xpath("//input[@id='checkout.shipping.addressSelector.newAddress.address.street']"), "310 Washington St, Boston, MA 02108, United States", "checkout.shipping.addressSelector.newAddress.address.firstName");
        fillField(By.xpath("//input[@id='checkout.shipping.addressSelector.newAddress.address.street2']"), "-", "checkout.shipping.addressSelector.newAddress.address.firstName");
        fillField(By.xpath("//input[@id='checkout.shipping.addressContactEmail.address.emailAddress']"), "billie.n22@mhs.istts.ac.id", "checkout.shipping.addressSelector.newAddress.address.firstName");
        fillField(By.xpath("//input[@id='checkout.shipping.addressContactPhone.address.fullDaytimePhone']"), "(091) 203-9133", "checkout.shipping.addressSelector.newAddress.address.firstName");
        Thread.sleep(10000);
        fieldClick("//button[@id='rs-checkout-continue-button-bottom']");
        Thread.sleep(5000);
        fieldClick("//button[@id='checkout.shipping.addressVerification.recommendedAddresses.continueWithRecommended']");
        Thread.sleep(2000);
        fieldClick("//label[@id='checkout.billing.billingoptions.paypal_label']//span[@class='form-selector-left-col large-6 small-7']");
        Thread.sleep(5000);
        fieldClick("//button[@id='rs-checkout-continue-button-bottom']");
        Thread.sleep(5000);
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

    @Step("Beralih ke iframe pertama")
    private void switchToIFrame(int index) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.findElements(By.tagName("iframe")).size() > 0);
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        System.out.println("Jumlah iframe di halaman: " + iframes.size());

        driver.switchTo().frame(iframes.get(index));
        System.out.println("Berada di dalam iframe ke-" + index);
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

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
