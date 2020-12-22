import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//import org.junit.Test;
//import org.junit.Test;
import java.lang.*;



public class EriBankTest {
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
        capabilities.setCapability("deviceQuery", "@os='android' and @category='TABLET' and @serialnumber='32e0d2a20377e920'");
        capabilities.setCapability("platformVersion", "8");
        capabilities.setCapability(MobileCapabilityType.APP,"cloud:com.experitest.ExperiBank/.LoginActivity");
        capabilities.setCapability(MobileCapabilityType.FULL_RESET,true);
        capabilities.setCapability("appPackage", "com.experitest.ExperiBank");
        capabilities.setCapability("appActivity", ".LoginActivity");




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
        String TEST_NAME="EriBank Login";
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
                writeFile("TEST "+TEST_NAME+" failed\n"+e.getStackTrace());

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
                    writeFile("TEST "+TEST_NAME+" failed\n"+e.getStackTrace());
                    throw e;
                }

            }
            else {
                try {
                    assertTrue(name+", "+password+"didnt pop Erorr message",checkErrorMessage());
                    backToReister();
                } catch (Exception e) {
                    e.printStackTrace();
                    writeFile("TEST "+TEST_NAME+" failed\n"+e.getStackTrace());
                    throw e;
                }
                }
            }




        try {
            csvReader.close();
        } catch (IOException e) {
            System.out.println("cant close file");
            e.printStackTrace();
            writeFile("TEST "+TEST_NAME+" failed\n"+e.getStackTrace());

        }

        System.out.println("test 1 finished");
        writeFile("TEST "+TEST_NAME+" passed");
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
    @Test
    public void Test3() {

        writeFile("TEST TEST_NAME sdfdsfsfdsfsd");}

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
    public void writeFile(String value){
        String PATH = "/";
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