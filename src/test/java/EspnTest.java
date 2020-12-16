import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EspnTest {
    static RemoteWebDriver driver;
    static WebDriverWait wait;

    public static void main(String[] args) {

    }

    @BeforeEach
    public void setUp() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "SM-G965F");
        capabilities.setCapability("udid", "23896d383d017ece");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "8");
//        capabilities.setCapability("appPackage", "com.experitest.ExperiBank");
//        capabilities.setCapability("appActivity", "LoginActivity");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("chromedriverExecutable", "C:\\Program Files\\nodejs\\chromedriver.exe");
        try {
            driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            driver.get("https://www.espn.com/");
            wait = new WebDriverWait(driver, 600);
        } catch (MalformedURLException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        aproveCondiotionsStart();
        System.out.println("Aplication Started");
    }

    @Test
    public void Test1() {

        for (int i=2;i<6;i++){
            waitT();
//            WebElement menu = driver.findElement(By.id( "global-nav-mobile-trigger"));
            List<WebElement> menu = driver.findElements(By.xpath( "//a[@id='global-nav-mobile-trigger']"));
//                    wait.until(ExpectedConditions.visibilityOf(menu));
            System.out.println(menu.get(0).isDisplayed());
            waitT();
            menu.get(0).click();
            String path="#global-nav-mobile > ul > li.active > ul > li:nth-child("+i+")" ;
            waitT();
            WebElement el4 =  wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByCssSelector(path)));
            String bTitle=el4.getText();
            el4.click();
            waitT();
            WebElement el5 =   wait.until(ExpectedConditions.visibilityOf((driver.findElementByCssSelector(  "#global-nav-secondary > div > ul > li.sports.sub > span > a > span.link-text"))));
            String title=el5.getText();
            assertTrue(bTitle.equals(title));
        }

    }


//    @Test
//    public void Test2() {
//
//    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
    public void aproveCondiotionsStart(){
        waitT();
        WebElement el1 =  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("onetrust-accept-btn-handler"))));
        el1.click();
    }
    public void waitT(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

