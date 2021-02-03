//import com.experitest.appium.SeeTestClient;
//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;
//import java.io.File;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//
//public class EspressoTest_Android extends MobileTest {
//    String pathToTestApp = "apk/apk/androidTest"; // path to XcuiTests or espresso tests
//    String pathToApp = "src/test/apk/apk/debug";
//
//    @BeforeEach
//    public void setUp() {
//        test_name="EspressoTest";
//        try {
////            driver = driverFactory.getIOSDriverSAFARI(true);
////            driver.get("https://www.espn.com/");
////            wait = new WebDriverWait(driver, 600);
////            seeTestClient= new SeeTestClient(driver);
////            driver.hideKeyboard();
//        } catch (Exception e) {
//            System.out.println("--TEST "+test_name+" failed in setUp\n");
//            printExeption(e);
//        }
//        System.out.println("----"+test_name+" test started----\n");
//    }
//
//
//    @Test
//    public void EspressoTest() throws UnirestException {
//        test_name = "EspressoTest";
//        File app = new File(pathToApp);
//        File testApp = new File(pathToTestApp);
//        String url = MobileTest.testConfiguration.getCloudUrl() + "/api/v1/test-run/execute-test-run";
//        try {
//            Unirest.setTimeouts(0, 0); //set infinity timeout for post request
//            HttpResponse<String> response = Unirest.post(url).header("Authorization", "Bearer " + MobileTest.testConfiguration.getAccessKey()).
//                    queryString("executionType", "espresso")
//                    .queryString("runningType", "coverage")
//                    .queryString("deviceQueries", "@os='android'") // can be repeated if multiple queries
//                    .field("app", app)
//                    .field("testApp", testApp)
//                    .asString();
//            System.out.println(response.getBody());
//            printSeccess();
//
//        }    catch (Exception e) {
//            printExeption(e);
//        }
//    }
//}
//
import com.experitest.appium.SeeTestClient;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public class EspressoTest_Android extends MobileTest {

    @BeforeEach
    public void setUp() {
        test_name = "EspressoTest Android";
        try {
//            driver = driverFactory.getAndroidDriverApp("com.experitest.uicatalog",".MainActivity",true,true);
////                wait = new WebDriverWait(driver, 10);
//            seeTestClient = new SeeTestClient(driver);
//            seeTestClient.setProperty("android.instrument.fingerprint", "true");
//            seeTestClient.setProperty(" android.instrument.fingerprint.secure", "true");
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
    public void EspressoTest() {
        test_name = "EspressoTest";
        String pathToTestApp = "src/test/apk/apk/androidTest/debug/app-debug-androidTest.apk"; // path to XcuiTests or espresso tests
        String pathToApp = "src/test/apk/apk/androidTest/debug/app-debug-androidTest.apk";
        File app = new File(pathToApp);
        File testApp = new File(pathToTestApp);
        String url = MobileTest.testConfiguration.getCloudUrl() + "/api/v1/test-run/execute-test-run";
        try {
            Unirest.setTimeouts(0, 0); //set infinity timeout for post request
            HttpResponse<String> response = Unirest.post(url).header("Authorization", "Bearer " + MobileTest.testConfiguration.getAccessKey()).
                    queryString("executionType", "espresso")
                    .queryString("runningType", "coverage")
                    .queryString("deviceQueries", "@os='android'") // can be repeated if multiple queries
                    .field("app", app)
                    .field("testApp", testApp)
                    .asString();
            System.out.println(response.getBody());
            printSeccess();

        }    catch (Exception e) {
            printExeption(e);
        }
    }
}


