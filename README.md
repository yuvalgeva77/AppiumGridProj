Appium Grid Automation project for ios and android devices with junit tests.


Configuration file:
File path from repository root: src/test/configuration file.txt
Attributes:
{
serialNumber: run on selected device only -no specification:"",else:”device Serial Number”.
testToRun: run the all suite : "all",
specific test: "AppStoreDownload"/”AppStoreTop10"/"EriBankLogin”/"EriBankPayment"/"EspnMenu"/"EspnMenuButtons"/“TapTheDotLogin"/"TapTheDotPlay".
repeat: run the suite repeatedly for a given amount of minutes. For defulet: 1
}

Example:
{
cloudUrl:"https://qacloud.experitest.com",
username:"yuval_user",
accessKey:"eyJhbGciOiJIUzI1NiJ9.eyJ4cC51Ijo0MDY4NjAyLCJ4cC5wIjozOTQ5MDQ1LCJ4cC5tIjoxNjA3NTA3MTQyNzMxLCJleHAiOjE5MjI4NjcxNDIsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.0CmfSM3ZeEOlm8wXW1CAzg_JzZcUBu5ujz1vfgD73t4",
numOfDevices:4,
serialNumber:"",
testToRun:"all",
repeat:15
}

Login data file location: src/test/Login data.csv
Result File:
Will be created automatically after run.
Directory path from repository root: Result Files.
Inside it will be created the RUN directories with the device.txt and summery.txt and support data.zip reports:
For example: Result Files/RUN_1610541501087/samsung SM-N7505.txt

Classes:
- TestThreadPool: gets list of relevant devices-> for each device creates and runs a TestRunner with the relevant tests.
- TestRunner: extends Thread. Gets a device and list of test names and runs them on the device. according to device type- runs ios or android test/suite.
- DriverFactory: creted drivers.
- TestLogger: saves data after each test about the test result, and creates summery file at the end of all the TestRunner execution.
- Configuration: singleton. create Configuration object from Configuration
 File through json
- Device - create device from GET /api/v1/devices request through json

- MobileTest- basic class which all test extend. Contain test reset information and shared data like time stamp.
-Ios Test classes: AppStoreTest_ios, EriBankTest_ios, EspnTest_ios.
-Android Test classes: TapTheDotTest, AppStoreTest_Android, EriBankTest_Android, EspnTest_Android.
creates a driver-> runs the specific test-> print the result to the device.txt and to the TestLogger-> releases driver.






