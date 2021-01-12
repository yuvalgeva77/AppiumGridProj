
import io.appium.java_client.android.AndroidDriver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.*;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import org.junit.Test;
//import org.junit.Test;
import java.lang.*;



public class EriBankTest_Android extends MobileTest{

    public static void main(String[] args) {
    }

    @BeforeEach
    public void setUp() {
        test_name="EriBank Android";
        try {
            driver =driverFactory.getAndroidDriverApp("com.experitest.ExperiBank",".LoginActivity",true);
            wait = new WebDriverWait(driver, 10);
//            device=new Device(driver);
            //get driver from Test Runner

        } catch (Exception e) {
            System.out.println("TEST "+test_name+" failed in setUp");
            printExeption(e);

        }

        System.out.println("----test Started----\n");
    }


    @Test
    public void EriBankLogin() {
        test_name="EriBank android Login";
//             pathToCsv = "C:\\Users\\YuvalGeva\\IdeaProjects\\AppiumGridProj\\src\\test\\Login data.csv";
        try {
            BufferedReader csvReader = null;
            csvReader = new BufferedReader(new FileReader(pathToCsv));
            String log = "";
            while (true) {
                if (( ( log = csvReader.readLine() ) == null )) break;
                String[] logData = log.split(",");
                String name = logData[0];
                String password = "";
                if (logData.length == 2) {
                    password = logData[1];
                }
                insertInfo(name, password);
                if (name.equals("company") && password.equals("company")) {
                    assertFalse("company company pops Erorr message", checkErrorMessage());
                } else {
                    assertTrue(name + ", " + password + "didnt pop Erorr message", checkErrorMessage());
                    backToReister();
                }

            }
            csvReader.close();
            printSeccess();
        }
        catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException:failed to open csv");
            printExeption(e);
        }
        catch (IOException e) {
            System.out.println("IOException -invalid file");
            printExeption(e);
        }
        catch (Exception e) {
            printExeption(e);
        }
    }



    @Test
    public void EriBankPayment() {
         test_name="EriBank android Payment";
        try {
            insertInfo("company", "company");
            double payedAmount = 50;
            double firstAmount = makePayment(payedAmount);
            double finalAmount = checkAmount();
            assertTrue("error:checkAmount!=firstAmount-payedAmount", finalAmount == firstAmount - payedAmount);
            printSeccess();
        } catch (Exception e) {
            printExeption(e);
        }


    }
//
//        @AfterEach
//        public void tearDown() {
//            driver.quit();
//        }


    public void insertInfo(String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("usernameTextField"))).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("passwordTextField"))).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("loginButton"))).click();
    }

    public Boolean checkErrorMessage() {
        try {
            wait = new WebDriverWait(driver, 100);
            wait.until(ExpectedConditions.visibilityOf(driver.findElementById("parentPanel")));
            return ( driver.findElementById("parentPanel").isDisplayed());
        }
        catch(Exception e){
            return false;
        }
    }
    public void backToReister(){
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("button3"))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("usernameTextField"))).clear();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("passwordTextField"))).clear();
    }

    public double makePayment(double sum){
        double sumT = checkAmount();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("makePaymentButton"))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("phoneTextField"))).sendKeys("0528446598");
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("nameTextField"))).sendKeys("Yuval");
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("amountTextField"))).sendKeys( Double.toString(sum));
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("countryButton"))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@text='USA']"))).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("sendPaymentButton"))).click();;
        wait.until(ExpectedConditions.visibilityOf(driver.findElementById("button1"))).click();;
        return sumT;

    }
    public double checkAmount(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String sumText=   wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[contains(text(),'Your balance is:') ]"))).getText();
        String sumT = sumText.split("Your balance is: ")[1].replaceFirst(".$","");
        return Double.parseDouble(sumT);

    }

}

