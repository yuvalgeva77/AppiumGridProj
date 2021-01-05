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
public class EriBankTest_ios extends MobileTest {
//    static IOSDriver<WebElement> DRIVER;
//    static WebDriverWait WAIT;
//    String DEVICE_NAME = "device1";
//    static long CURRENT_TIME;
//    String pathToCsv;


    public static void main(String[] args) {

    }
//    @BeforeAll
//    public static void resetTimer(){
//        CURRENT_TIME = System.currentTimeMillis();
//
//    }
    @BeforeEach
    public void setUp()  {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("testName", "Eri Bank ios Test");
        capabilities.setCapability("accessKey", "eyJhbGciOiJIUzI1NiJ9.eyJ4cC51Ijo0MDY4NjAyLCJ4cC5wIjozOTQ5MDQ1LCJ4cC5tIjoxNjA3NTA3MTQyNzMxLCJleHAiOjE5MjI4NjcxNDIsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.0CmfSM3ZeEOlm8wXW1CAzg_JzZcUBu5ujz1vfgD73t4");
        capabilities.setCapability("deviceQuery", "@os='ios'");
        capabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");

        try {
            driver = new IOSDriver<>(new URL("https://qacloud.experitest.com/wd/hub"), capabilities);
            driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
            wait = new WebDriverWait(driver, 120);
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
//         pathToCsv = "src/test/company.csv";
         pathToCsv = "src/test/Login data.csv";
        System.out.println("Aplication Started");
    }
    @Test
    public void Test1() {
        test_name="EriBank Login";
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
                writeFile("TEST "+test_name+" failed\n"+e.getStackTrace());

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
                try {
                    assertFalse("company company pops Erorr message",checkErrorMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    writeFile("TEST "+test_name+" failed\n"+e.getStackTrace());
                    throw e;
                }

            }
            else {
                try {
                    assertTrue(name+", "+password+"didnt pop Erorr message",checkErrorMessage());
                    backToReister();
                } catch (Exception e) {
                    e.printStackTrace();
                    writeFile("TEST "+test_name+" failed\n"+e.getStackTrace());
                    throw e;
                }
            }
        }

        try {
            csvReader.close();
        } catch (IOException e) {
            System.out.println("cant close file");
            e.printStackTrace();
            writeFile("TEST "+test_name+" failed\n"+e.getStackTrace());

        }

        System.out.println("test 1 finished");
        writeFile("TEST "+test_name+" passed");
    }

    @Test
    public void Test2() {
        String TEST_NAME="EriBank Payment";
        insertInfo("company","company");
        double payedAmount=50;
        double firstAmount=makePayment(payedAmount);
        double finalAmount=checkAmount();
        assertTrue("error:checkAmount!=firstAmount-payedAmount",finalAmount==firstAmount-payedAmount);
        writeFile("TEST "+TEST_NAME+" passed");



    }

    public  void writeFile(String value){
        String PATH = "./";
        //crete RUN_CURRENT_TIME directory
        String directoryName = PATH.concat("RUN_"+CURRENT_TIME);
        String fileName = device.getName()+ ".txt";
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
    @AfterEach
//    public void tearDown() {
//        driver.quit();
//    }

    public void insertInfo(String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@placeholder='Username']"))).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@placeholder='Password']"))).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@text='loginButton']"))).click();
    }

    public Boolean checkErrorMessage() {
        try {
            wait = new WebDriverWait(driver, 100);
            wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@text='Invalid username or password!']")));
            return (driver.findElementByXPath("//*[@class='UIAView' and ./*[@class='UIAStaticText']]").isDisplayed());
        }
        catch(Exception e){
            return false;
        }
    }
    public void backToReister(){
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@text='Dismiss']"))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@placeholder='Username']"))).clear();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@placeholder='Password']"))).clear();
    }

    public double makePayment(double sum){
        double sumT = checkAmount();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@text='makePaymentButton']"))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@placeholder='Phone']"))).sendKeys("0528446598");
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@placeholder='Name']"))).sendKeys("Yuval");
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@placeholder='Amount']"))).sendKeys( Double.toString(sum));
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@text='countryButton']"))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@text='USA']"))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@text='sendPaymentButton']"))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@text='Yes']"))).click();;
        return sumT;

    }
    public double checkAmount(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String sumText=   wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[contains(@id,'Your balance is:') and @value='1'  ]"))).getText();
        String sumT = sumText.split("Your balance is: ")[1].replaceFirst(".$","");
        return Double.parseDouble(sumT);

    }

}
