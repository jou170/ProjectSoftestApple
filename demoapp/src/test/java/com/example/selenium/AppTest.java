package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private App app;
    /**
     * Rigorous Test :-)
     */
    @BeforeClass
    @Description("Set up browser dan buka aplikasi melalui url")
    public void setUp(){
        app = new App();
        app.openURL("https://trytestingthis.netlify.app/");
    }

    @Test
    @Feature("Submit Form")
    public void testFormSubmission() {
        formFill("test", "test");
        verifySuccessMessage();
    }

    @Step("Isi form dengan Username:{uname} dan Password:{pwd}")
    private void formFill(String uname, String pwd){
        WebElement usernameField=app.getDriver().findElement(By.id("uname"));
        WebElement passwordField=app.getDriver().findElement(By.id("pwd"));

        usernameField.sendKeys(uname);
        passwordField.sendKeys(pwd);

        app.getDriver().findElement(By.cssSelector("input[type='submit']")).click();
    }

    @Step("Verifikasi pesan sukses")
    private void verifySuccessMessage(){
        WebElement successMessage=app.getDriver().findElement(By.cssSelector("body > div.main > h2"));
        Assert.assertTrue(successMessage.getText().contains("Login Successful"),"Failed");
    }

    @AfterClass
    @Description("Tutup browser")
    public void tearDown(){
        app.closeBrowser();
    }
}