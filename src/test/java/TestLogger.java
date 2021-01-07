import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TestLogger {
    HashMap<String, device_log> deviceMap ;


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

    public TestLogger() {
        deviceMap= new HashMap<String, device_log>();
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
    }
    public HashMap<String, device_log> getDeviceMap() {
        return deviceMap;
    }

    public void setDeviceMap(HashMap<String, device_log> deviceMap) {
        this.deviceMap = deviceMap;
    }


}
