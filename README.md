## Appium Grid Automation project for ios and android devices with junit tests. ##

**Classes:**  
- TestThreadPool: gets list of relevant devices-> for each device creates and runs a TestRunner with the relevant tests.  
- TestRunner: extends Thread. Gets a device and list of test names and runs them on the device. according to device type- runs ios or android test/suite.  
- DriverFactory: creted drivers.  
- TestLogger: saves data after each test about the test result, iterations and total nv profiles test runtime. Creates summery files at the end of all the TestRunner execution. 
- Configuration: singleton. create Configuration object from Configuration file through json
- Device: creates device from GET /api/v1/devices request through json  
- MobileTest- basic class which all test extend. Contain test reset information and shared data like time stamp. Each test has a failure mechanism and 10 mins timeout(including up to 5 mins of rebooting device if needed).  
-Ios Test classes: AppStoreTest_ios, EriBankTest_ios, EspnTest_ios.  
-Android Test classes: TapTheDotTest, AppStoreTest_Android, EriBankTest_Android, EspnTest_Android.  
creates a driver-> runs the specific test-> print the result to the device.txt and to the TestLogger-> releases driver.  

-----------------------------------------------
**Other files:**

- Configuration file:
File path from repository root: src/test/configuration file.txt  
Attributes:  
{  
serialNumber: run on selected device only -no specification:"",else:”device Serial Number”.  
testToRun: run the all suite : "all",  
specific test:"AppStoreDownload"/”AppStoreTop10"/"EriBankLogin”/"EriBankPayment"/"EspnMenu"/"EspnMenuButtons"/“TapTheDotLogin"/"TapTheDotPlay".  
repeat: run the suite repeatedly for a given amount of minutes. For defulet: 1  
NG profile: for all profiles: "Original","High_Latency"," Low_Bandwidth","High_PacketLoss": “all”/  
for no special profile: “default” /list of specific profiles  
}  

Example:  
{cloudUrl:"https://qacloud.experitest.com",  
username:"yuval",  
accessKey(user's accesskey to the cloud):"*dfdsd444***.*45645tgfghf**************.*gdd*-*********************",  
numOfDevices:1,  
serialNumber:"",  
testToRun:"AppStoreTop10",  
repeat:7,  
NG_profile:"all"  
}  

-----------------------------------------------

- Login data file: used in login tests. Location : src/test/Login data.csv  

- Result File:
Will be created automatically after run.    
Directory path from repository root: Result Files.  
Inside it will be created the RUN directories with the device test result data, support data.zip reports, NV Profile Summery Log.txt and summery log.txt and:
in path: Result Files/Run_ID/ NV Profile Name/ Devices folders/ Reports for this device and support data   
For example:  
Result Files/RUN_1611666833764/Original/Google Pixel 4/Google Pixel 4.txt -for device report  
Result Files/RUN_1611666833764/NV Profile Summery Log.txt for -total summery  

-----------------------------------------------

**Failure mechanism:**  
3 tries to each test
1. Test->
	if succeeds- stop.
	Else- test failure-> get support data and write exceptions-> 
2. restart test with new driver-> 
	if succeeds -stop. 
	Else- test failure-> get support data and write exceptions-> 
  reset device(up to 5 minutes)->
3. restart test with new driver-> 
if succeeds- stop.
Else- test failure-> get support data and write exceptions.










