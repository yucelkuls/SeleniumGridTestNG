package tests;


import org.testng.annotations.Test;
import pages.CLContactPage;
import pages.CLHomePage;
import utilities.ConfigReader;
import utilities.Driver;

import static org.testng.Assert.assertEquals;

public class C01_LoginTest {

    @Test
    public void loginTest01() throws InterruptedException {

        CLHomePage clHomePage = new CLHomePage();
        CLContactPage clContactPage = new CLContactPage();

        //Go to url
        Driver.getDriver().get(ConfigReader.getProperty("contact_list_url"));

        //Enter email
        clHomePage.email.sendKeys("clarusway@hotmail.com");

        //Enter password
        clHomePage.password.sendKeys("Clarusway.123");

        //Click submit
        Thread.sleep(200);
        clHomePage.submit.click();

        //Assert Contact List Header
        assertEquals(clContactPage.header.getText(), "Contact List");
        assert clContactPage.logout.isDisplayed();

        Driver.closeDriver();
    }

}