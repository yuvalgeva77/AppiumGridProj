import com.experitest.appium.SeeTestClient;
import io.github.artsok.RepeatedIfExceptionsTest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TapTheDotTest extends MobileTest{

    public static void main(String[] args) {
    }

    @BeforeEach
    public void setUp() {
        test_name="Tap The Dot Android";
        try {
            driver =(driverFactory.getAndroidDriverApp("com.example.tapthedot",".MainActivity",true));
            wait = new WebDriverWait(driver, 10L);
            seeTestClient= new SeeTestClient(driver);
            seeTestClient.setProperty("android.install.grant.permissions", "true");
            driver.hideKeyboard();        }
        catch (Exception e) {
            System.out.println("--TEST "+test_name+" failed in setUp\n");
            printExeption(e);
        }
        System.out.println("----"+test_name+" test started----\n");
    }

    @RepeatedIfExceptionsTest(repeats = 2)
    public void TapTheDotLogin() {
        test_name="Tap The Dot Android Login";
        // do{
        try {
            aproveConditions();
            BufferedReader csvReader = null;
            csvReader = new BufferedReader(new FileReader(pathToCsv));
            String log = "";
            while(true) {
                if (( log = csvReader.readLine() ) == null) {
                    break;}
                String[] logData = log.split(",");
                String name = logData[0];
                String verifyName = "";
                if (logData.length == 2) {
                    verifyName = logData[1];
                }
                this.insertInfo(name, verifyName);
                if (name.equals(verifyName) && name != "") {
                    backToReister();
                } else {
                    Assert.assertTrue(name + ", " + verifyName + "didnt pop Erorr message", this.checkErrorMessage());
                    clearReister();
                }
            }
            csvReader.close();
            printSeccess();
        }
        catch (FileNotFoundException e) {
            System.out.println("TEST "+test_name+" failed: FileNotFoundException:failed to open csv");
            printExeption(e);
        }
        catch (IOException e) {
            System.out.println("TEST "+test_name+" failed: IOException -invalid file:cant close file or no line to read"+e.getStackTrace());
            printExeption(e);
        }
        catch (Exception e) {
            printExeption(e);
        }
        catch (AssertionError e) {
            System.out.println("AssertionError ");
            printAssertionError(e);
        }
        //}while(failures>=1&&failures<3);
    }

    @RepeatedIfExceptionsTest(repeats = 2)
    public void TapTheDotPlay() {
        test_name="Tap The Dot Android play 3 taps";
        //do{
        try {
            aproveConditions();
            insertInfo("Yuval", "Yuval");
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@text='PLAY!']")))).click();
            for (int i = 0; i < 3; i++) {
                try {
                    driver.findElement(By.xpath("//*[@text='TAP']")).click();
                } catch (Exception e) {
                    System.out.println("failed to catch the button on the:" + i + "'s try");
                }
            }

            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@text='FINISH']")))).click();
            printSeccess();
        }
        catch (Exception e) {
            printExeption(e);
        }
        catch (AssertionError e) {
            System.out.println("AssertionError ");
            printAssertionError(e);
        }
        //}while(failures>=1&&failures<3);
    }

    public void insertInfo(String username, String verifyName) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='nameTextBox']")))).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='verifyNameTextBox']")))).sendKeys(verifyName);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@text='LOGIN']")))).click();
    }

    public Boolean checkErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 100L);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='errorTextBox']\n"))));
            return ((driver.findElement(By.xpath("//*[@id='errorTextBox']\n"))).isDisplayed());
        } catch (Exception var2) {
            return false;
        }
    }
    public void clearReister() {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='nameTextBox']")))).clear();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='verifyNameTextBox']")))).clear();
    }
    public void backToReister() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@text='LOG OUT']")))).click();


    }
    public void aproveConditions() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")))).click();
        } catch (Exception e)
        {try {
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("//*[@text='Allow only while using the app']")))).click();
        } catch (Exception e2)
        {
        }
        }


    }
}
