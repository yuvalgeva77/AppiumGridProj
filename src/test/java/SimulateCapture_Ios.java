import com.experitest.appium.SeeTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimulateCapture_Ios extends MobileTest {

    @BeforeEach
    public void setUp() {
        test_name = "SimulateCapture Ios";
        try {
            driver = driverFactory.getAndroidDriverApp("com.experitest.uicatalog",".MainActivity",true,true,test_name);
//                wait = new WebDriverWait(driver, 10);
            seeTestClient = new SeeTestClient(driver);
            seeTestClient.setProperty("Android.instrumentation.camera", "true");
            seeTestClient.setProperty("android.instrumentation.camera2", "true");
//                seeTestClient.install("cloud:com.experitest.uicatalog", true, false);
//                seeTestClient.launch("com.experitest.uicatalog/.MainActivity", true, true);

//            driver.hideKeyboard();
        } catch (Exception e) {
            System.out.println("TEST " + test_name + " failed in setUp\n");
            printExeption(e);
        }
        System.out.println("----"+test_name+" test started----\n");
    }

    //    @RepeatedIfExceptionsTest(repeats = 2)
    @Test
    public void SimulateCapture() {
        test_name = "SimulateCapture";
        //do {
        try {
            System.out.println("failures: "+ failures+"\n");
            seeTestClient.swipeWhileNotFound("Down", 200, 2000,
                    "NATIVE", "text=Camera", 0, 1000, 5, true);
            driver.findElementByXPath("//*[@text='Camera api1']").click();
            seeTestClient.simulateCapture("src/test/java/image.jpg");
            driver.findElementByXPath("//*[@text='Capture']").click();
            printSeccess();

        } catch (AssertionError e) {
            System.out.println("AssertionError ");
            printAssertionError(e);
        } catch (Exception e) {
            printExeption(e);
        }
    }

}



