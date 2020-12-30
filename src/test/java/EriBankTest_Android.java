
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



    public class EriBankTest_Android extends MobileTest{
        AndroidDriver DRIVER;
        WebDriverWait WAIT;
        String pathToCsv;

        public static void main(String[] args) {
        }

        @BeforeEach
        public void setUp() {
            TEST_NAME="EriBank Android";
             pathToCsv = "src/test/Login data.csv";

            try {
                DRIVER=(new DriverFactory()).getAndroidDriverApp("cloud:com.experitest.ExperiBank",".LoginActivity",true);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("-----couldnt load DRIVER!------");

            }
            WAIT = new WebDriverWait(DRIVER, 10);
            System.out.println("Aplication Started");
        }


        @Test
        public void Test1() {
            TEST_NAME="EriBank Login";
//            String pathToCsv = "C:\\Users\\YuvalGeva\\IdeaProjects\\FirstAutomationProj\\src\\test\\Login data.csv";
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

        @AfterEach
        public void tearDown() {
            DRIVER.quit();
        }


        public void insertInfo(String username, String password) {
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("usernameTextField"))).sendKeys(username);
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("passwordTextField"))).sendKeys(password);
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("loginButton"))).click();
        }

        public Boolean checkErrorMessage() {
            try {
                WAIT = new WebDriverWait(DRIVER, 100);
                WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("parentPanel")));
                return (DRIVER.findElementById("parentPanel").isDisplayed());
            }
            catch(Exception e){
                return false;
            }
        }
        public void backToReister(){
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("button3"))).click();
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("usernameTextField"))).clear();
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("passwordTextField"))).clear();
        }

        public double makePayment(double sum){
            double sumT = checkAmount();
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("makePaymentButton"))).click();
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("phoneTextField"))).sendKeys("0528446598");
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("nameTextField"))).sendKeys("Yuval");
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("amountTextField"))).sendKeys( Double.toString(sum));
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("countryButton"))).click();
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementByXPath("//*[@text='USA']"))).click();
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("sendPaymentButton"))).click();;
            WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementById("button1"))).click();;
            return sumT;

        }
        public double checkAmount(){
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String sumText=   WAIT.until(ExpectedConditions.visibilityOf(DRIVER.findElementByXPath("//*[contains(text(),'Your balance is:') ]"))).getText();
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

