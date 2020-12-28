import io.appium.java_client.FindsByAndroidViewTag;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.swing.text.View;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;


public class AppStoreTest_ios {
    static IOSDriver<WebElement> driver;
    static WebDriverWait wait;
    String DEVICE_NAME = "device1";
    static long CURRENT_TIME;

    public static void main(String[] args) {

    }
    @BeforeAll
    public static void resetTimer(){
        CURRENT_TIME = System.currentTimeMillis();

    }
    @BeforeEach
    public void setUp()  {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("accessKey", "eyJhbGciOiJIUzI1NiJ9.eyJ4cC51Ijo0MDY4NjAyLCJ4cC5wIjozOTQ5MDQ1LCJ4cC5tIjoxNjA3NTA3MTQyNzMxLCJleHAiOjE5MjI4NjcxNDIsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.0CmfSM3ZeEOlm8wXW1CAzg_JzZcUBu5ujz1vfgD73t4");
        capabilities.setCapability("deviceQuery", "@os='ios'");
//        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);

//        capabilities.setCapability(MobileCapabilityType.APP, "cloud:com.apple.AppStore");
        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.apple.AppStore");
        try {
            driver = new IOSDriver<>(new URL("https://qacloud.experitest.com/wd/hub"), capabilities);
            wait = new WebDriverWait(driver, 120);
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
        System.out.println("Aplication Started");
    }


    @Test
    public void Test1() throws InterruptedException {
        String TEST_NAME = "AppStore Download";
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Games']"))).click();

        try {
            while (driver.findElements(By.xpath("//*[@text='See All' and @class='UIAButton' and ./parent::*[@text='Top Free Games']]")).size() == 0) {
                swipeDown();
            }
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='See All' and @class='UIAButton' and ./parent::*[@text='Top Free Games']]"))).click();
            List<WebElement> appList = driver.findElementsByXPath("//*[contains(@text, \"get\")]");
            WebElement button=wait.until(ExpectedConditions.elementToBeClickable(appList.get(0)));
            String appName=button.findElement(By.xpath("./..")).getText();
            button.click();
             wait.until(ExpectedConditions.elementToBeClickable(driver.findElementsByXPath("//*[@text='Install']").get(1))).click();
            TimeUnit.SECONDS.sleep(60);
            //*[@text='open' and ./parent::*[@text='2, Roblox, Adventure']]
            wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@text='open' and ./parent::*[@text='"+appName+"']]")));
            System.out.println("-----------TEST " + TEST_NAME + " passed ------------\n");
            writeFile("TEST " + TEST_NAME + " passed\n");
            //*[@text=

        } catch (Exception e) {
            System.out.println("-----------TEST " + TEST_NAME + " failed------------\n" + e.getStackTrace().toString() + "\n");
            writeFile("TEST " + TEST_NAME + " failed\n" + e.getStackTrace().toString() + "\n");
            throw e;

        }

    }

    @Test
    public void Test2() {
        String TEST_NAME = "AppStore top10";
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Games']"))).click();

        try {
            while (driver.findElements(By.xpath("//*[@text='See All' and @class='UIAButton' and ./parent::*[@text='Top Free Games']]")).size() == 0) {
                swipeDown();
            }
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='See All' and @class='UIAButton' and ./parent::*[@text='Top Free Games']]"))).click();

            List <String> appNames = new LinkedList<String>();
            while ( appNames.size() < 10) {
            List<WebElement> appList = driver.findElementsByXPath("//*[@text='AXStoreCollectionView' and  ./*[@text='Top Free iPad Apps']]/*[@knownSuperClass='UICollectionViewCell']");
                for (int i = 0; i < appList.size(); i++) {
                    String app=getNameOfApp((IOSElement) appList.get(i));
                    if (!appNames.contains(app))
                        appNames.add(app);
                }
                swipeDownonLeftScreeen();
            }
            assertTrue(appNames.size()>=10);
            for (int i = 0; i < 10; i++) {
                System.out.println(String.valueOf(i+1)+"."+appNames.get(i)+"\n");}

            System.out.println("-----------TEST " + TEST_NAME + " passed ------------\n");
            writeFile("TEST " + TEST_NAME + " passed");
        } catch(Exception e){
            System.out.println("-----------TEST " + TEST_NAME + " failed------------\n" + e.getStackTrace().toString() + "\n");
            writeFile("TEST " + TEST_NAME + " failed\n" + e.getStackTrace().toString() + "\n");
            throw e;

        }
    }
    public  void writeFile(String value){
        String PATH = "./";
        //crete RUN_CURRENT_TIME directory
        String directoryName = PATH.concat("RUN_"+CURRENT_TIME);
        String fileName = DEVICE_NAME+ ".txt";
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
            System.out.println("directory created at: "+directory.getAbsolutePath());
        }
        //crete device_number file id doesnt exist and write the data
        File file = new File(directoryName + "/" + fileName);
        try {
            file.createNewFile(); // if file already exists will do nothing
            //Here true is to append the content to file
            FileWriter fw = new FileWriter(file,true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(value);
            System.out.println("file data written at: "+file);
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
    public void swipeDown ( ) {
        Dimension dimension = driver.manage().window().getSize();
        int start_x = (int) ( dimension.width * 0.9 );
        int start_y = (int) ( dimension.height * 0.9 );
        int end_x = (int) ( dimension.width * 0.9 );
        int end_y = (int) ( dimension.height * 0.6 );
        TouchAction touch = new TouchAction(driver);
        touch.press(PointOption.point(start_x, start_y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(end_x, end_y)).release().perform();

    }
    public void swipeDownonLeftScreeen ( ) {
        Dimension dimension = driver.manage().window().getSize();
        int start_x = (int) ( dimension.width * 0.3 );
        int start_y = (int) ( dimension.height * 0.3 );
        int end_x = (int) ( dimension.width * 0.3 );
        int end_y = (int) ( dimension.height * 0.6 );
        TouchAction touch = new TouchAction(driver);
        touch.press(PointOption.point(start_x, start_y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(end_x, end_y)).release().perform();

    }
    public String getNameOfApp(IOSElement elem){
        String appName = elem.getText();
        String[] res = appName.split(",");
        String name = res[1];
        return name;
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
