import io.appium.java_client.FindsByAndroidViewTag;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.text.View;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AppStoreTest_Android {
    static AndroidDriver<AndroidElement> driver;
    static WebDriverWait wait;
    String DEVICE_NAME="device1";
    long CURRENT_TIME;

    public static void main(String[] args) {

    }

    @BeforeEach
    public void setUp() {
        CURRENT_TIME=System.currentTimeMillis();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("accessKey","eyJhbGciOiJIUzI1NiJ9.eyJ4cC51Ijo0MDY4NjAyLCJ4cC5wIjozOTQ5MDQ1LCJ4cC5tIjoxNjA3NTA3MTQyNzMxLCJleHAiOjE5MjI4NjcxNDIsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.0CmfSM3ZeEOlm8wXW1CAzg_JzZcUBu5ujz1vfgD73t4");
        capabilities.setCapability("deviceQuery", "@os='android' ");
//        capabilities.setCapability("platformVersion", "8");
        capabilities.setCapability(MobileCapabilityType.FULL_RESET,true);
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
        String TEST_NAME="AppStore Download";
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Top charts']"))).click();
            List<AndroidElement> appList= driver.findElementsByXPath("//*[contains(@contentDescription, \"App:\")]");
            wait.until(ExpectedConditions.elementToBeClickable(appList.get(0))).click();
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Install']"))).click();
            System.out.println("-----------TEST "+TEST_NAME+"passed ------------\n");
            writeFile("TEST "+TEST_NAME+" passed");
        } catch (Exception e) {
            System.out.println("-----------TEST "+TEST_NAME+" failed------------\n"+e+"\n");
            writeFile("TEST "+TEST_NAME+" failed\n"+e+"\n");

        }

    }

    @Test
    public void Test2() {
        String TEST_NAME="AppStore top10";
        try {
//            wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Galaxy Store']"))).click();
//            wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Close']"))).click();
//            wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Top']"))).click();
//            wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Top apps']"))).click();
//            List<AndroidElement> appList= driver.findElementsByXPath("//*//*[@id='layout_list_itemly_centerly_pname']");
             wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text='Top charts']"))).click();
            List<AndroidElement> appList= driver.findElementsByXPath("//*[contains(@contentDescription, \"App:\")]");
            for(int i=0;i<10;i++) {
                String appName= (appList.get(i)).getAttribute("contentDescription").toString();
                System.out.println(appName);
            }
            System.out.println("-----------TEST "+TEST_NAME+"passed ------------\n");
            writeFile("TEST "+TEST_NAME+" passed");
        } catch (Exception e) {
            System.out.println("-----------TEST "+TEST_NAME+" failed------------\n"+e.getStackTrace().toString()+"\n");
            writeFile("TEST "+TEST_NAME+" failed\n"+e.getStackTrace().toString()+"\n");
            throw e;

        }
    }



    public void insertInfo(String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("com.experitest.ExperiBank:id/usernameTextField"))).sendKeys(username);

        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("com.experitest.ExperiBank:id/passwordTextField"))).sendKeys(password);

        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("com.experitest.ExperiBank:id/loginButton"))).click();
    }

    public Boolean checkErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 100);
            wait.until(ExpectedConditions.visibilityOf(driver.findElementById("android:id/parentPanel")));
            return (driver.findElementById("android:id/parentPanel").isDisplayed());
        }
        catch(Exception e){
            return false;
        }
    }
    public void backToReister(){
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("android:id/button3"))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("com.experitest.ExperiBank:id/usernameTextField"))).clear();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("com.experitest.ExperiBank:id/passwordTextField"))).clear();
    }

    public double makePayment(double sum){
        double sumT = checkAmount();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("com.experitest.ExperiBank:id/makePaymentButton"))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("com.experitest.ExperiBank:id/phoneTextField"))).sendKeys("0528446598");
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("com.experitest.ExperiBank:id/nameTextField"))).sendKeys("Yuval");
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("com.experitest.ExperiBank:id/amountTextField"))).sendKeys( Double.toString(sum));
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("com.experitest.ExperiBank:id/countryButton"))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.ListView/android.widget.TextView[13]"))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("com.experitest.ExperiBank:id/sendPaymentButton"))).click();;
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("android:id/button1"))).click();;
        return sumT;

    }
    public double checkAmount(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String sumText=   wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View"))).getText();
        String sumT = sumText.split("Your balance is: ")[1].replaceFirst(".$","");
        return Double.parseDouble(sumT);

    }
    public void writeFile(String value){
        String PATH = "./";
        String directoryName = PATH.concat("RUN_"+CURRENT_TIME);
        String fileName = DEVICE_NAME+ ".txt";

        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
            System.out.println("directory created at: "+PATH);

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
            System.out.println("file data written at: "+file);

            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
