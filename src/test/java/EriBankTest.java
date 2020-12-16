import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//import org.junit.Test;
//import org.junit.Test;


public class EriBankTest {
    static AndroidDriver<AndroidElement> driver;
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
        capabilities.setCapability("appPackage", "com.experitest.ExperiBank");
        capabilities.setCapability("appActivity", "LoginActivity");
        capabilities.setCapability("automationName", "UiAutomator1");
        try {
            driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            wait = new WebDriverWait(driver, 10);
        } catch (MalformedURLException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Aplication Started");
        System.out.println("sdgsfsdfsdf");
    }

    @Test
    public void Test1() {
        String pathToCsv = "C:\\Users\\YuvalGeva\\IdeaProjects\\FirstAutomationProj\\src\\test\\Login data.csv";
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(pathToCsv));
        } catch (FileNotFoundException e) {
            System.out.println("failed to open csv");
            e.printStackTrace();
        }
        String log = "";
        while (true) {
            try {
                if (((log = csvReader.readLine()) == null)) break;
            } catch (IOException e) {
                System.out.println("no line");
                e.printStackTrace();
            }
            String[] logData = log.split(",");
            String name = logData[0];
            String password = "";
            if(logData.length==2){
                password = logData[1];
            }


//            System.out.println(logData[0]+logData[1]+"\n");

            // do something with the data
            insertInfo(name,password);
            if(name.equals("company")&&password.equals("company")){
                assertFalse("company company pops Erorr message",checkErrorMessage());
            }
            else {
                assertTrue(name+", "+password+"didnt pop Erorr message",checkErrorMessage());
                backToReister();
            }
        }



        try {
            csvReader.close();
        } catch (IOException e) {
            System.out.println("cant close file");
            e.printStackTrace();
        }

        System.out.println("test 1 finished");
    }

    @Test
    public void Test2() {
        insertInfo("company","company");
        double payedAmount=50;
        double firstAmount=makePayment(payedAmount);
        double finalAmount=checkAmount();
        assertTrue("error:checkAmount!=firstAmount-payedAmount",finalAmount==firstAmount-payedAmount);



    }

    @AfterEach
    public void tearDown() {
        driver.quit();
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
}