import com.experitest.appium.SeeTestClient;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.github.artsok.RepeatedIfExceptionsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertTrue;


public class AppStoreTest_ios extends MobileTest{

    public static void main(String[] args) {

    }
    @BeforeEach
    public void setUp()  {
        test_name="App Store ios";
        try {
            driver = driverFactory.getIOSDriverApp("com.apple.AppStore",test_name);
            driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
            wait = new WebDriverWait(driver, 120);
            seeTestClient= new SeeTestClient(driver);
            driver.hideKeyboard();
        } catch (Exception e) {
            System.out.println("--TEST " + test_name + " failed in setUp\n");
            printExeption(e);
        }
        System.out.println("----"+test_name+" test started----\n");
    }


    @RepeatedIfExceptionsTest(repeats = 2)
    public void AppStoreDownload()  {
        test_name = "AppStore ios Download";
    //    do {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Games']"))).click();
                while (driver.findElements(By.xpath("//*[@text='See All' and @class='UIAButton' and ./parent::*[@text='Top Free Games']]")).size() == 0 || !checkVisable((WebElement) driver.findElements(By.xpath("//*[@text='See All' and @class='UIAButton' and ./parent::*[@text='Top Free Games']]")).get(0))) {
                    swipeDown();
                }
                wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='See All' and @class='UIAButton' and ./parent::*[@text='Top Free Games']]"))).click();
                List<WebElement> appList = driver.findElementsByXPath("//*[contains(@text, \"get\")]");
                while (appList.size() == 0 || !checkVisable(appList.get(0))) {
                    swipeDownonLeftScreeen();
                    appList = driver.findElementsByXPath("//*[contains(@text, \"get\")]");
                }
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(appList.get(0)));
                String appName = button.findElement(By.xpath("./..")).getText();
                button.click();
                wait.until(ExpectedConditions.elementToBeClickable((WebElement) driver.findElementsByXPath("//*[@text='Install']").get(1))).click();
                TimeUnit.SECONDS.sleep(60);
                while (driver.findElementsByXPath("//*[@text='downloading']").size() > 0 || driver.findElementsByXPath("//*[@text='loading']").size() > 0) {
                    TimeUnit.SECONDS.sleep(60);
                }
                wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@text='open' and ./parent::*[@text='" + appName + "']]")));
                printSeccess();
            }
            catch (Exception e) {
                printExeption(e);
            }
      //  } while(failures>=1&&failures<3);
    }

    @RepeatedIfExceptionsTest(repeats = 2)
    public void AppStoreTop10() throws Exception {
        test_name = "AppStore ios top10";
      //  do {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Games']"))).click();
                while (driver.findElements(By.xpath("//*[@text='See All' and @class='UIAButton' and ./parent::*[@text='Top Free Games']]")).size() == 0 || !checkVisable((WebElement) driver.findElements(By.xpath("//*[@text='See All' and @class='UIAButton' and ./parent::*[@text='Top Free Games']]")).get(0))) {
                    swipeDown();
                }
                wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='See All' and @class='UIAButton' and ./parent::*[@text='Top Free Games']]"))).click();
                List<String> appNames = new LinkedList<String>();
                while (appNames.size() < 10) {
                    List<WebElement> appList = driver.findElementsByXPath("//*[@text='AXStoreCollectionView' and  ./*[@text='Top Free iPad Apps']]/*[@knownSuperClass='UICollectionViewCell']");
                    if (appList.size() == 0) {
                        throw new Exception("appList size == 0");
                    }
                    for (int i = 0; i < appList.size(); i++) {
                        String app = getNameOfApp((IOSElement) appList.get(i));
                        if (!appNames.contains(app))
                            appNames.add(app);
                    }
                    swipeDownonLeftScreeen();
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
        int start_y = (int) ( dimension.height * 0.9 );
        int end_x = (int) ( dimension.width * 0.3 );
        int end_y = (int) ( dimension.height * 0.6 );
        TouchAction touch = new TouchAction(driver);
        touch.press(PointOption.point(start_x, start_y)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(PointOption.point(end_x, end_y)).release().perform();

    }
    public boolean checkVisable (WebElement button ) {
        int minHight= wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@class='UIATabBar' or @text='Tab Bar']"))).getRect().getY();
        int buttonHight=button.getRect().getY()+button.getRect().getHeight();
        if(minHight>buttonHight)
            return true;
        return false;

    }
    public String getNameOfApp(IOSElement elem){
        String appName = elem.getText();
        String[] res = appName.split(",");
        String name = res[1];
        return name;
    }

}
