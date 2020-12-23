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

public class AppStoreTest_Android {
    static AndroidDriver<AndroidElement> driver;
    static WebDriverWait wait;
    String DEVICE_NAME = "device1";
    long CURRENT_TIME;

    public static void main(String[] args) {

    }

    @BeforeEach
    public void setUp() {
        CURRENT_TIME = System.currentTimeMillis();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("accessKey", "eyJhbGciOiJIUzI1NiJ9.eyJ4cC51Ijo0MDY4NjAyLCJ4cC5wIjozOTQ5MDQ1LCJ4cC5tIjoxNjA3NTA3MTQyNzMxLCJleHAiOjE5MjI4NjcxNDIsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.0CmfSM3ZeEOlm8wXW1CAzg_JzZcUBu5ujz1vfgD73t4");
        capabilities.setCapability("deviceQuery", "@os='android' ");
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.android.vending");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".AssetBrowserActivity");
        try {
            driver = new AndroidDriver<>(new URL("https://qacloud.experitest.com/wd/hub"), capabilities);
            wait = new WebDriverWait(driver, 10);
        } catch (MalformedURLException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Aplication Started");
    }


    @Test
    public void Test1() {
        String TEST_NAME = "AppStore Download";
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Top charts']"))).click();
            List<AndroidElement> appList = driver.findElementsByXPath("//*[contains(@contentDescription, \"App:\")]");
            wait.until(ExpectedConditions.elementToBeClickable(appList.get(0))).click();
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Install']"))).click();
            System.out.println("-----------TEST " + TEST_NAME + "passed ------------\n");
            writeFile("TEST " + TEST_NAME + " passed");
        } catch (Exception e) {
            System.out.println("-----------TEST " + TEST_NAME + " failed------------\n" + e + "\n");
            writeFile("TEST " + TEST_NAME + " failed\n" + e + "\n");

        }

    }

    @Test
    public void Test2() {
        String TEST_NAME = "AppStore top10";
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Top charts']"))).click();
            List <String> appNames = new LinkedList<String>();
            while (appNames == null || appNames.size() < 10) {
                List<AndroidElement> appList = driver.findElementsByXPath("//*[contains(@contentDescription, \"App:\")]");
                for (int i = 0; i < appList.size(); i++) {
                    String app=getNameOfApp(appList.get(i));
                    if (appNames==null||!appNames.contains(app))
                        appNames.add(app);
                }
                swipeDown();
            }
            assertTrue(appNames.size()>=10);
            for (int i = 0; i < 10; i++) {
                System.out.println(String.valueOf(i+1)+"."+appNames.get(i)+"\n");}

            System.out.println("-----------TEST " + TEST_NAME + "passed ------------\n");
            writeFile("TEST " + TEST_NAME + " passed");
        } catch( Exception e){
            System.out.println("-----------TEST " + TEST_NAME + " failed------------\n" + e.getStackTrace().toString() + "\n");
            writeFile("TEST " + TEST_NAME + " failed\n" + e.getStackTrace().toString() + "\n");
            throw e;

        }
    }
    public void writeFile ( String value){
        String PATH = "./";
        String directoryName = PATH.concat("RUN_" + CURRENT_TIME);
        String fileName = DEVICE_NAME + ".txt";

        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
            System.out.println("directory created at: " + PATH);

            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
        File file = new File(directoryName + "/" + fileName);
        try {
            file.createNewFile(); // if file already exists will do nothing
            FileOutputStream oFile = new FileOutputStream(file, false);

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(value);
            System.out.println("file data written at: " + file);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    public void swipeDown ( ) {
        Dimension dimension = driver.manage().window().getSize();
        int start_x = (int) ( dimension.width * 0.5 );
        int start_y = (int) ( dimension.height * 0.9 );
        int end_x = (int) ( dimension.width * 0.2 );
        int end_y = (int) ( dimension.height * 0.2 );
        TouchAction touch = new TouchAction(driver);
        touch.press(PointOption.point(start_x, start_y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(end_x, end_y)).release().perform();

    }
    public String getNameOfApp(AndroidElement elem){
        String appName = elem.getAttribute("contentDescription").toString();
        String[] res = appName.split("\n");
        String name = res[0].split("App: ")[1];
        return name;
    }
}

