//import org.junit.Before;
//import org.junit.Test; // junit 4
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class temp extends MobileTest {

    @BeforeEach
    public void setUp() {
        test_name = "temp android";
        try {
            driver = ( driverFactory.getAndroidDriverApp("com.experitest.ExperiBank", ".LoginActivity", true) );
            wait = new WebDriverWait(driver, 10);
            device = new Device(driver);

        } catch (Exception e) {
            System.out.println("TEST " + test_name + " failed in setUp");
            printExeption(e);

        }

        System.out.println("----test Started----\n");
    }


    @Test
    public void Test1() {
        test_name = "temp android 1";

        try {
                 printSeccess();
        }
         catch (Exception e) {
            printExeption(e);
        }
    }

    @Test
    public void Test2() {
        test_name = "temp android 2";
        try {
            Assertions.assertTrue(false, "error:false in assert print");
            throw new Exception("Exception message");
        } catch (Exception e) {
            printExeption(e);
        }
    }

        @Test
        public void Test3() {
            test_name = "temp android 3";
            try {
                printSeccess();
            }
            catch (Exception e) {
                printExeption(e);
            }
        }


//
//        @AfterEach
//        public void tearDown() {
//            driver.quit();
//        }


}