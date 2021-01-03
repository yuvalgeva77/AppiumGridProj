import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Driver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class  MobileTest {
    AppiumDriver driver;
    String test_status;

    WebDriverWait wait;
    Device device;
    protected String test_name;
    protected long CURRENT_TIME;
    protected String pathToCsv = "src/test/Login data.csv";

    @BeforeAll
    public void resetTimer(){
        System.out.println("--------Test suite started-----");
        CURRENT_TIME = System.currentTimeMillis();

    }
    public void writeRunFile(String value){
        String fileName = "Result Files/RUN_"+CURRENT_TIME+"/"+device.getName()+ ".txt";
        try {
            Path pathToFile = Paths.get(fileName);
            Files.createDirectories(pathToFile.getParent());
            File file = new File(String.valueOf(pathToFile));
            file.createNewFile(); // if file already exists will do nothing
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(value);
            System.out.println("file data written at: "+file);
            bw.close();
        }
        catch (IOException e){
            System.out.println("couldnt write to file");
            e.printStackTrace();
            System.exit(-1);
        }
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
        System.out.println(test_name+" finished\n");

    }

    public void resetConfigurations(){
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(pathToCsv));
        } catch (FileNotFoundException e) {
            System.out.println("failed to open csv");
            e.printStackTrace();
        }
    }



};

