import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Driver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class  MobileTest {

    protected String TEST_NAME;
    protected long CURRENT_TIME;
    //    protected AppiumDriver DRIVER;
    protected  String DEVICE_NAME;
    protected String pathToCsv = "src/test/Login data.csv";

    @BeforeAll
    public void resetTimer(){
        System.out.println("in before all");
        CURRENT_TIME = System.currentTimeMillis();

    }
    public void writeRunFile(String value){
//        String PATH = "/";
//        String directoryName = PATH.concat("RUN_"+CURRENT_TIME);
        String fileName = "Result Files/RUN_"+CURRENT_TIME+"/"+DEVICE_NAME+ ".txt";
//        File directory = new File(directoryName);
//        if (! directory.exists()){
//            directory.mkdir();
//            System.out.println("directory created at: "+PATH);
//        }
    ;
//        File file = new File(directoryName + "/" + fileName);
        try {
            Path pathToFile = Paths.get(fileName);
            Files.createDirectories(pathToFile.getParent());
            File file= Files.createFile(pathToFile).toFile();
//            File file=  file.createNewFile(); // if file already exists will do nothing
            FileOutputStream oFile = new FileOutputStream(file, true);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
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

