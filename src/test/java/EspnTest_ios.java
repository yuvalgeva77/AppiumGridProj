import com.experitest.appium.SeeTestClient;
import io.github.artsok.RepeatedIfExceptionsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertTrue;

public class EspnTest_ios extends MobileTest {

    public static void main(String[] args) {
    }

    @BeforeEach
    public void setUp() {
        test_name="Espn ios";
        try {
            driver = driverFactory.getIOSDriverSAFARI(true);
            driver.get("https://www.espn.com/");
            wait = new WebDriverWait(driver, 600);
            seeTestClient= new SeeTestClient(driver);
        } catch (Exception e) {
            System.out.println("--TEST "+test_name+" failed in setUp\n");
            printExeption(e);
        }
        System.out.println("----"+test_name+" test started----\n");
    }


    @RepeatedIfExceptionsTest(repeats = 2)
    public void EspnMenu() {
        test_name="Espn ios menu";
      //  do{
            try{
                aproveCondiotionsStart();
                for (int i=2;i<6;i++){
                    waitT();
                    List<WebElement> menu = driver.findElements(By.xpath( "//*[@id='global-nav-mobile-trigger' and @nodeName='A']"));
                    System.out.println(menu.get(0).isDisplayed());
                    waitT();
                    menu.get(0).click();
                    String path="#global-nav-mobile > ul > li.active > ul > li:nth-child("+i+")" ;
                    waitT();
                    WebElement el4 =  wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByCssSelector(path)));
                    String bTitle=el4.getText();
                    el4.click();
                    waitT();
                    WebElement el5 =   wait.until(ExpectedConditions.visibilityOf((driver.findElementByCssSelector(  "#global-nav-secondary > div > ul > li.sports.sub > span > a > span.link-text"))));
                    String title=el5.getText();
                    assertTrue(bTitle.equals(title));
                }
                printSeccess();
            }
            catch (Exception e) {
                printExeption(e);
            }
            catch (AssertionError e) {
                System.out.println("AssertionError ");
                printAssertionError(e);
            }
       // }while(failures>=1&&failures<3);
    }

    @RepeatedIfExceptionsTest(repeats = 2)
    public void EspnMenuButtons() {
        test_name="Espn2 ios menu buttons";
       // do{
            try{
                aproveCondiotionsStart();
                for (int i=2;i<6;i++){
                    waitT();
                    List<WebElement> menu = driver.findElements(By.xpath( "//*[@id='global-nav-mobile-trigger' and @nodeName='A']"));
                    System.out.println(menu.get(0).isDisplayed());
                    waitT();
                    menu.get(0).click();
                    String path="#global-nav-mobile > ul > li.active > ul > li:nth-child("+i+")" ;
                    waitT();
                    WebElement el4 =  wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByCssSelector(path)));
                    String bTitle=el4.getText();
                    el4.click();
                    waitT();
                    WebElement el5 =   wait.until(ExpectedConditions.visibilityOf((driver.findElementByCssSelector(  "#global-nav-secondary > div > ul > li.sports.sub > span > a > span.link-text"))));
                    String title=el5.getText();
                    assertTrue(bTitle.equals(title));
                }
                printSeccess();
            }
            catch (Exception e) {
                printExeption(e);
            }
            catch (AssertionError e) {
                System.out.println("AssertionError ");
                printAssertionError(e);
            }
       // }while(failures>=1&&failures<3);
    }


    public void aproveCondiotionsStart(){
        waitT();
        try {
            WebElement el1 =  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("onetrust-accept-btn-handler"))));
            el1.click();
        } catch (Exception e) {
            System.out.println("No approve condition notification");
        }
    }

    public void waitT(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
