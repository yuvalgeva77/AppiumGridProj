import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TestLogger {
    HashMap<String, device_log> deviceMap ;
    HashMap<Integer, String> iterationMap ;
    private static TestLogger Logger = null;
    public int Totalpassed=0,Totalfailed=0, numDevices=0,devrequested=0;

    public void setTotalpassed(int totalpassed) {
        Totalpassed = totalpassed;
    }

    public void setTotalfailed(int totalfailed) {
        Totalfailed = totalfailed;
    }

    public void setNumDevices(int numDevices) {
        this.numDevices = numDevices;
    }

    public void setDevrequested(int devrequested) {
        this.devrequested = devrequested;
    }

    class device_log {
        @Override
        public String toString() {
            String res=
                    device +
                            ", passed=" + passed +
                            ", failed=" + failed +"\n";
            if (failed>0)
                res=res+", errorList=" + errorList+"\n" ;
            return res;

        }

        public device_log(Device device, int passed, int failed, List<String> errorList) {
            this.device = device;
            this.passed = passed;
            this.failed = failed;
            this.errorList = errorList;
        }

        Device device;
        int passed;
        int failed;
        List<String> errorList;
    }
    // private constructor restricted to this class itself

    private TestLogger() {
        deviceMap= new HashMap<String, device_log>();
        iterationMap=new HashMap<Integer, String>();
    }
    public  String printIterationLog(){
        String report=
                "Summary View Report\n"+
                        "Number of devices that were requested to run:"+ devrequested+"\n"+
                        "Number of Devices that actually ran:"+ numDevices+"\n"+
                        "Total number of tests passed:"+ Totalpassed+"\n"+
                        "Total number of tests failed:"+ Totalfailed+"\n---------------------------\n";
        for (String iterString:iterationMap.values()) {
            report=report+iterString;
        }
        return report;
    }
    // static method to create instance of Singleton class
    public static TestLogger getTestLogger()
    {
        if (Logger == null)
            Logger = new TestLogger();

        return Logger;
    }
    public void addDevice(Device device){
        if(! deviceMap.containsKey(device.getName())){
            deviceMap.put(device.getName(), new device_log(device,0,0,new LinkedList<String>()));}
    }
    //The method put will replace the value of an existing key and will create it if doesn't exist.
    public void addDPassed(Device device) {
        if (!deviceMap.containsKey(device.getName())) {
            addDevice(device);}
        device_log oldLog=deviceMap.get(device.getName());
        device_log newLog=new device_log(device,oldLog.passed+1,oldLog.failed,oldLog.errorList);
        deviceMap.put(device.getName(),newLog );
        Totalpassed++;


    }
    public void finishIteration(Integer iter,Device device){
        if (!iterationMap.containsKey(iter)) {
            addIteration(iter);}
        String oldIterLog=iterationMap.get(iter);
        String newIterLogLog=oldIterLog+"\n"+ deviceMap.get(device.getName()).toString();
        iterationMap.put(iter,newIterLogLog );
        deviceMap.remove(device.getName()); //*****************


    }

    private void addIteration(Integer iter) {
        if(!iterationMap.containsKey(iter)){
            iterationMap.put(iter, "--------iteration:" + iter + "--------\n");
        }
    }

    public String printMap(HashMap<String, device_log> mp) {
        String res = new String();
        int i=1;
        for (device_log log : mp.values()) {
            res=res+i+". "+log.toString();
            i++;
        }
        return res;
    }

    @Override
    public String toString() {
        return printMap( deviceMap );
    }

    public void addDFail(Device device, String error) {
        if (!deviceMap.containsKey(device.getName())) ;
        addDevice(device);
        device_log oldLog=deviceMap.get(device.getName());
        List<String>oldErorr=oldLog.errorList;
        oldErorr.add(error);
        device_log newLog=new device_log(device,oldLog.passed,oldLog.failed+1,oldErorr);
        deviceMap.put(device.getName(),newLog );
        Totalfailed++;
    }
    public HashMap<String, device_log> getDeviceMap() {
        return deviceMap;
    }

    public void setDeviceMap(HashMap<String, device_log> deviceMap) {
        this.deviceMap = deviceMap;
    }



}
