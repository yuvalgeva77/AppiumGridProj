import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
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
public class EspnTest_ios extends MobileTest {
//    static RemoteWebDriver driver;
//    static WebDriverWait wait;
//    static long CURRENT_TIME;
//    String DEVICE_NAME = "device1";



    public static void main(String[] args) {

    }

    @BeforeEach
    public void setUp() {
        test_name="Espn ios";
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("accessKey","eyJhbGciOiJIUzI1NiJ9.eyJ4cC51Ijo0MDY4NjAyLCJ4cC5wIjozOTQ5MDQ1LCJ4cC5tIjoxNjA3NTA3MTQyNzMxLCJleHAiOjE5MjI4NjcxNDIsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.0CmfSM3ZeEOlm8wXW1CAzg_JzZcUBu5ujz1vfgD73t4");
//        capabilities.setCapability("deviceQuery", "@os='ios'");
//        capabilities.setCapability("testName", "EspnTest ios");
//        capabilities.setCapability(MobileCapabilityType.FULL_RESET,true);
//        capabilities.setBrowserName(MobileBrowserType.SAFARI);
        try {
            driver = driverFactory.getIOSDriverSAFARI(true);
            driver.get("https://www.espn.com/");
            wait = new WebDriverWait(driver, 600);
            device=new Device(driver);
        } catch (Exception e) {
            System.out.println("TEST "+test_name+" failed in setUp");
            printExeption(e);

        }
        System.out.println("Aplication Started");
    }
    //    @BeforeAll
//    public static void resetTimer(){
//        CURRENT_TIME = System.currentTimeMillis();
//
//    }
    @Test
    public void Test1() {
        test_name="Espn ios menu";
        try{
            aproveCondiotionsStart();
            for (int i=2;i<6;i++){
                waitT();
//            WebElement menu = driver.findElement(By.id( "global-nav-mobile-trigger"));
                List<WebElement> menu = driver.findElements(By.xpath( "//*[@id='global-nav-mobile-trigger' and @nodeName='A']"));
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
                printSeccess();
            }
        }
        catch (Exception e) {
            printExeption(e);

        }
    }


//    @Test
//    public void Test2() {
//
//    }

    //    @AfterEach
//    public void tearDown() {
//        driver.quit();
//    }
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
//    public void writeFile(String value){
//        String PATH = "/";
//        String directoryName = PATH.concat("RUN_"+CURRENT_TIME);
//        String fileName = DEVICE_NAME+ ".txt";
//        File directory = new File(directoryName);
//        if (! directory.exists()){
//            directory.mkdir();
//            System.out.println("directory created at: "+PATH);
//
//            // If you require it to make the entire directory path including parents,
//            // use directory.mkdirs(); here instead.
//        }
//        File file = new File(directoryName + "/" + fileName);
//        try {
//            file.createNewFile(); // if file already exists will do nothing
//            FileOutputStream oFile = new FileOutputStream(file, false);
//
//            FileWriter fw = new FileWriter(file.getAbsoluteFile());
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(value);
//            System.out.println("file data written at: "+file);
//
//            bw.close();
//
//        }
//        catch (IOException e){
//            e.printStackTrace();
//            System.exit(-1);
//        }
//    }
}
