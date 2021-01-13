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

public class TapTheDotTest extends MobileTest{
    //    static AndroidDriver<AndroidElement> driver;
//    static WebDriverWait wait;
    public static void main(String[] args) {
    }

    @BeforeEach
    public void setUp() {
        test_name="Tap The Dot Android";
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("accessKey","eyJhbGciOiJIUzI1NiJ9.eyJ4cC51Ijo0MDY4NjAyLCJ4cC5wIjozOTQ5MDQ1LCJ4cC5tIjoxNjA3NTA3MTQyNzMxLCJleHAiOjE5MjI4NjcxNDIsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.0CmfSM3ZeEOlm8wXW1CAzg_JzZcUBu5ujz1vfgD73t4");
//        capabilities.setCapability("deviceQuery", "@os='android' and @category='TABLET' and @serialnumber='32e0d2a20377e920'");
//        capabilities.setCapability("platformVersion", "8");
//        capabilities.setCapability(MobileCapabilityType.APP,"cloud:com.example.tapthedot/.MainActivity");
//        capabilities.setCapability(MobileCapabilityType.FULL_RESET,true);
//        capabilities.setCapability("appPackage", "com.example.tapthedot");
//        capabilities.setCapability("appActivity", ".MainActivity");
        try {
            driver =(driverFactory.getAndroidDriverApp("com.example.tapthedot",".MainActivity",true));

//            driver = new AndroidDriver<>(new URL("https://qacloud.experitest.com/wd/hub"), capabilities);
//            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            wait = new WebDriverWait(driver, 10L);

        }   catch (Exception e) {
            System.out.println("TEST "+test_name+" failed in setUp");
            printExeption(e);
        }
        catch (AssertionError e) {
            System.out.println("AssertionError ");
            printAssertionError(e);
        }

        System.out.println("Application Started");
    }

    @Test
    public void TapTheDotLogin() {
        test_name="Tap The Dot Android Login";
        try {
//        String pathToCsv = "C:\\Users\\YuvalGeva\\IdeaProjects\\FirstAutomationProj\\src\\test\\Login data.csv";
            BufferedReader csvReader = null;
            csvReader = new BufferedReader(new FileReader(pathToCsv));
            String log = "";
            while(true) {
                if (( log = csvReader.readLine() ) == null) {
                    break;}
                String[] logData = log.split(",");
                String name = logData[0];
                String verifyName = "";
                if (logData.length == 2) {
                    verifyName = logData[1];
                }
                this.insertInfo(name, verifyName);
                if (name.equals(verifyName) && name != "") {
//                Assert.assertFalse("name==verifyName && name not empty pops Erorr message", this.checkErrorMessage());
                    backToReister();
                } else {
                    Assert.assertTrue(name + ", " + verifyName + "didnt pop Erorr message", this.checkErrorMessage());
                    clearReister();
                }
            }

            csvReader.close();
            printSeccess();
        }
        catch (FileNotFoundException e) {
            System.out.println("TEST "+test_name+" failed: FileNotFoundException:failed to open csv");
            printExeption(e);
        }
        catch (IOException e) {
            System.out.println("TEST "+test_name+" failed: IOException -invalid file:cant close file or no line to read"+e.getStackTrace());
            printExeption(e);

        }
        catch (Exception e) {
            printExeption(e);

        }
        catch (AssertionError e) {
            System.out.println("AssertionError ");
            printAssertionError(e);
        }
    }

    @Test
    public void TapTheDotPlay() {
        test_name="Tap The Dot Android play 3 taps";
        try {
            insertInfo("Yuval", "Yuval");
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@text='PLAY!']")))).click();
            for (int i = 0; i < 3; i++) {
                try {
                    driver.findElement(By.xpath("//*[@text='TAP']")).click();
                } catch (Exception e) {
                    System.out.println("failed to catch the button on the:" + i + "'s try");
                }
            }

            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@text='FINISH']")))).click();
            printSeccess();
        }
        catch (Exception e) {
            printExeption(e);
        }
        catch (AssertionError e) {
            System.out.println("AssertionError ");
            printAssertionError(e);
        }
    }
//
//    @AfterEach
//    public void tearDown() {
//        driver.quit();
//    }

    public void insertInfo(String username, String verifyName) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='nameTextBox']")))).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='verifyNameTextBox']")))).sendKeys(verifyName);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@text='LOGIN']")))).click();
    }

    public Boolean checkErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 100L);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='errorTextBox']\n"))));
            return ((driver.findElement(By.xpath("//*[@id='errorTextBox']\n"))).isDisplayed());
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
