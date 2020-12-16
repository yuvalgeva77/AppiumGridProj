import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TapTheDotTest {
    static AndroidDriver<AndroidElement> driver;
    static WebDriverWait wait;
    public static void main(String[] args) {
    }

    @BeforeEach
    public void setUp() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("accessKey","eyJhbGciOiJIUzI1NiJ9.eyJ4cC51Ijo0MDY4NjAyLCJ4cC5wIjozOTQ5MDQ1LCJ4cC5tIjoxNjA3NTA3MTQyNzMxLCJleHAiOjE5MjI4NjcxNDIsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.0CmfSM3ZeEOlm8wXW1CAzg_JzZcUBu5ujz1vfgD73t4");
        capabilities.setCapability("deviceQuery", "@os='android' and @category='TABLET' and @serialnumber='32e0d2a20377e920'");
        capabilities.setCapability("platformVersion", "8");
        capabilities.setCapability(MobileCapabilityType.APP,"cloud:com.example.tapthedot/.MainActivity");
        capabilities.setCapability(MobileCapabilityType.FULL_RESET,true);

        capabilities.setCapability("appPackage", "com.example.tapthedot");
        capabilities.setCapability("appActivity", ".MainActivity");



        try {

            driver = new AndroidDriver<>(new URL("https://qacloud.experitest.com/wd/hub"), capabilities);
//            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            wait = new WebDriverWait(driver, 10L);
        } catch (MalformedURLException var3) {
            System.out.println(var3.getCause());
            System.out.println(var3.getMessage());
            var3.printStackTrace();
        }

        System.out.println("Application Started");
        aproveConditions();
    }

    @Test
    public void Test1() {
        String pathToCsv = "C:\\Users\\YuvalGeva\\IdeaProjects\\FirstAutomationProj\\src\\test\\company.csv";
        BufferedReader csvReader = null;

        try {
            csvReader = new BufferedReader(new FileReader(pathToCsv));
        } catch (FileNotFoundException var8) {
            System.out.println("failed to open csv");
            var8.printStackTrace();
        }

        String log = "";

        while(true) {
            try {
                if ((log = csvReader.readLine()) == null) {
                    break;
                }
            } catch (IOException var9) {
                System.out.println("no line");
                var9.printStackTrace();
            }

            String[] logData = log.split(",");
            String name = logData[0];
            String verifyName = "";
            if (logData.length == 2) {
                verifyName = logData[1];
            }

            this.insertInfo(name, verifyName);
            if (name.equals(verifyName) && name!="") {
//                Assert.assertFalse("name==verifyName && name not empty pops Erorr message", this.checkErrorMessage());
                backToReister();
            } else {
                Assert.assertTrue(name + ", " + verifyName + "didnt pop Erorr message", this.checkErrorMessage());
                clearReister();
            }
        }

        try {
            csvReader.close();
        } catch (IOException var7) {
            System.out.println("cant close file");
            var7.printStackTrace();
        }

        System.out.println("test 1 finished");
    }

    @Test
    public void Test2() {

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    public void insertInfo(String username, String verifyName) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='nameTextBox']")))).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='verifyNameTextBox']")))).sendKeys(verifyName);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@text='LOGIN']")))).click();
    }

    public Boolean checkErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 100L);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("com.example.tapthedot:id/errorTextBox"))));
            return (driver.findElement(By.xpath("com.example.tapthedot:id/errorTextBox")).isDisplayed());
        } catch (Exception var2) {
            return false;
        }
    }
    public void clearReister() {
//        (wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("android:id/button3")))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='nameTextBox']")))).clear();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='verifyNameTextBox']")))).clear();
    }
    public void backToReister() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@text='LOG OUT']")))).click();


    }
    public void aproveConditions(){
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")))).click();

    }


}
