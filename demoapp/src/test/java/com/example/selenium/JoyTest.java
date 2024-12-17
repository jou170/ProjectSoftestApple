package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.security.Key;
import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class JoyTest
{
    public App app;

    @BeforeTest
    public void setUp()
    {
        app = new App();
        app.openURL("https://www.apple.com/");
    }

    @Test
    public void testFormSubmission()
    {
        navigateRegisterForm();
        tearDown();
    }

    private void navigateRegisterForm() {
        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(20)); // Explicit wait 10 detik
    
        // Tunggu shopping bag icon untuk muncul dan klik
        WebElement shoppingBagIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("globalnav-menubutton-link-bag")));
        shoppingBagIcon.click();
    
        // Tunggu elemen Account navigation muncul dan klik
        WebElement accountNavigation = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-analytics-title='account']"))
        );
        accountNavigation.click();
        
        // Beralih ke tab atau jendela baru
        for (String windowHandle : app.getDriver().getWindowHandles()) {
            app.getDriver().switchTo().window(windowHandle);
        }

        JavascriptExecutor js = (JavascriptExecutor) app.getDriver();
        js.executeScript("arguments[0].click();", accountNavigation);

        System.out.println("Current URL: " + app.getDriver().getCurrentUrl());
        // Tunggu tombol 'Create Account' muncul dan klik
        WebElement navigateCreateAccount = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='create-link']"))
        );

        // Pindah ke tab baru
        for (String handle : app.getDriver().getWindowHandles()) {
            app.getDriver().switchTo().window(handle);
        }

        // Tambahkan aksi setelah beralih ke tab baru
        System.out.println("Current URL: " + app.getDriver().getCurrentUrl());
    }

    @AfterTest
    public void tearDown()
    {
        app.closeBrowser();
    }
}
