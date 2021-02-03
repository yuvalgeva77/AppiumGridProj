import com.experitest.appium.SeeTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MockAuthentication_Android extends MobileTest {

    @BeforeEach
    public void setUp() {
        test_name = "MockAuthentication Android";
        try {
            driver = driverFactory.getAndroidDriverApp("com.experitest.uicatalog",".MainActivity",true,true);
//                wait = new WebDriverWait(driver, 10);
            seeTestClient = new SeeTestClient(driver);
            seeTestClient.setProperty("android.instrument.fingerprint", "true");
            seeTestClient.setProperty(" android.instrument.fingerprint.secure", "true");
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
    public void MockAuthentication() {
        test_name = "MockAuthentication";
        //do {
        try {
            System.out.println("failures: "+ failures+"\n");
            seeTestClient.setAuthenticationReply("AUTHENTICATION_SUCCEEDED", 0);
            seeTestClient.swipeWhileNotFound("Down", 200, 2000,
                    "NATIVE", "text='Fingerprint Authentication'", 0, 1000, 5, true);
//                seeTestClient.click("NATIVE", "xpath=//*[@text='PURCHASE']", 0, 1);
            printSeccess();

        } catch (AssertionError e) {
            System.out.println("AssertionError ");
            printAssertionError(e);
        } catch (Exception e) {
            printExeption(e);
        }
    }

}



