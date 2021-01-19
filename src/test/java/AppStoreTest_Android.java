import com.experitest.appium.SeeTestClient;
import io.appium.java_client.FindsByAndroidViewTag;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.github.artsok.RepeatedIfExceptionsTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
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

public class AppStoreTest_Android extends MobileTest{

    public static void main(String[] args) {

    }
    @BeforeEach
    public void setUp()  {
        test_name = "AppStore android ";
        try {
            driver=driverFactory.getAndroidDriverApp("com.android.vending",".AssetBrowserActivity",false);
            wait = new WebDriverWait(driver, 120);
            driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.MINUTES);
            seeTestClient= new SeeTestClient(driver);
        } catch (Exception e) {
            System.out.println("TEST "+test_name+" failed in setUp\n");
            printExeption(e);
        }
        System.out.println("----"+test_name+" test started----\n");
    }


    @RepeatedIfExceptionsTest(repeats = 2)
    public void AppStoreDownload()  {
        test_name = "AppStore android Download";
        //do {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Top charts']"))).click();
//                List<AndroidElement> appList = driver.findElementsByXPath("//*[contains(@contentDescription, \"App:\")]");
                List<AndroidElement> appList = driver.findElementsByXPath("//*[contains(@contentDescription, \"App:\") and not(contains(@contentDescription,'Update')) and not(contains(@contentDescription,'Installed'))]");
                while (appList.size()==0){
                    swipeDown();
                }
                wait.until(ExpectedConditions.elementToBeClickable(appList.get(0))).click();
                wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Install']"))).click();
                accept();
                TimeUnit.SECONDS.sleep(30);
//            wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@id='message']")));
                wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@text='Uninstall']")));
                printSeccess();
                //*[@text=

            } catch (Exception e) {
                printExeption(e);
            }
     //   } while(failures>=1&&failures<3);
    }

    @RepeatedIfExceptionsTest(repeats = 2)
    public void AppStoreTop10() {
        test_name = "AppStore android top10";
       // do {
            try {
                // wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Top charts']"))).click();
                wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Top charts']"))).click();
                List<String> appNames = new LinkedList<String>();
                while (appNames.size() < 10) {
                    List<AndroidElement> appList = driver.findElementsByXPath("//*[contains(@contentDescription, \"App:\")]");
                    for (int i = 0; i < appList.size(); i++) {
                        String app = getNameOfApp(appList.get(i));
                        if (!appNames.contains(app))
                            appNames.add(app);
                    }
                    swipeDown();
                }
                assertTrue(appNames.size() >= 10);
                for (int i = 0; i < 10; i++) {
                    System.out.println(String.valueOf(i + 1) + "." + appNames.get(i) + "\n");
                }

                printSeccess();
            } catch (Exception e) {
                printExeption(e);

            }
      //  } while(failures>=1&&failures<3);
    }

    public void swipeDown () {
        Dimension dimension = driver.manage().window().getSize();
        int start_x = (int) ( dimension.width * 0.3 );
        int start_y = (int) ( dimension.height * 0.6 );
        int end_x = (int) ( dimension.width * 0.3 );
        int end_y = (int) ( dimension.height * 0.3 );
        TouchAction touch = new TouchAction(driver);
        touch.press(PointOption.point(start_x, start_y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(end_x, end_y)).release().perform();

    }
    public String getNameOfApp(AndroidElement elem){
        String appName = elem.getAttribute("contentDescription").toString();
        String[] res = appName.split("\n");
        String name = res[0].split("App: ")[1];
        return name;
    }
    public void accept() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 100L);
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='ACCEPT']"))).click();
        } catch (Exception e) {
        }
    }

}

