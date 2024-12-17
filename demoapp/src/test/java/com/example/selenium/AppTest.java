package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    public App app;
    /**
     * Rigorous Test :-)
     */

    @BeforeTest
    public void setUp()
    {
        app = new App();
        app.openURL("https://trytestingthis.netlify.app/");
    }

    @Test
    public void testFormSubmission()
    {
        formFill("test", "test");
        verifySuccessMessage();
        tearDown();
    }

    private  void formFill(String uname, String pwd)
    {
        WebElement usernameField = app.getDriver().findElement(By.id("uname"));
        WebElement passwordField = app.getDriver().findElement(By.id("pwd"));

        usernameField.sendKeys(uname);
        passwordField.sendKeys(pwd);

        app.getDriver().findElement(By.cssSelector("input[type='submit']")).click();
    }

    private void verifySuccessMessage()
    {
        WebElement successMessage = app.getDriver().findElement(By.cssSelector("h2"));

        Assert.assertTrue(successMessage.getText().contains("Login Successful"), "Failed");
    }

    @AfterTest
    public void tearDown()
    {
        app.closeBrowser();
    }
}
