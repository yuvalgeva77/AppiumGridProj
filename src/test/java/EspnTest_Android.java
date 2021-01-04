import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class EspnTest_Android extends MobileTest {



    public static void main(String[] args) {

    }

    @BeforeEach
    public void setUp() throws MalformedURLException {

        try {
            driver =(driverFactory.getAndroidDriverChrome("EspnTest Android",true));
            driver.get("https://www.espn.com/");
            wait = new WebDriverWait(driver, 600);
            device=new Device(driver);
            System.out.println("----test Started----\n");
        } catch (Exception e) {
            System.out.println("-----couldnt load DRIVER!------");
            e.printStackTrace();
        }
    }

    @Test
    public void Test1() {
        test_name="Espn Android";
        try{
            aproveCondiotionsStart();
            for (int i=2;i<6;i++){
                waitT();
                List<WebElement> menu = driver.findElements(By.xpath( "//*[@id='global-nav-mobile-trigger' and @nodeName='A']"));
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
                System.out.println("test 1 finished");
                writeRunFile("TEST "+test_name+" passed\n");
            }
        } catch (Exception e) {
            test_status="TEST "+test_name+" failed\n"+e.getStackTrace().toString();
//            System.out.println("TEST "+test_name+" failed\n"+e.getStackTrace());
            writeRunFile(test_status);
            assertTrue(test_status,false);

        }


    }



    public void aproveCondiotionsStart(){
        waitT();
        try {
            WebElement el1 =  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("onetrust-accept-btn-handler"))));
            el1.click();
        } catch (Exception e) {
            System.out.println("No approve condition notification");
        }

    }
    public void waitT(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
