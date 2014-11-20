Msquare iBeacon Library 
=======================

Android library providing APIs to interact with iBeacons and Msquare Web Content


## What does this library do?

It allows Android devices to use iBeacons much like iOS devices do.  An app can request to get notifications when one
or more iBeacons appear or disappear.  An app can also request to get a ranging update from one or more iBeacons
at a frequency of 1Hz. Also it allows user to sync beacon content from the web and update analytics data to the web.

## How to Use this Library

The [project website](http://msquare.io/) is the best place to go if you want
to learn how to use this library.  It includes binary downloads, a quick start guide, sample code, and even a reference application.


## Build Instructions

Known working with IntelliJ IDEA 13.1.4

Configuration/Installation	1. Copy msqurelib.jar to your libs directory.	2. Add following permissions and features to your AndroidManifest.xml: <uses-sdk android:minSdkVersion="18"/>  <uses-permission android:name="android.permission.BLUETOOTH"/> <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/> <uses-permission android:name="android.permission.INTERNET" /> <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> <uses-permission android:name="android.permission.READ_PHONE_STATE"/>  <uses-feature android:name="android.hardware.bluetooth_le" android:required="true" /> 	3. Add following services to your AndroidManifest.xml application section:  <service android:enabled="true"                 android:exported="true"                 android:isolatedProcess="false"                 android:label="iBeacon"                 android:name="com.appxcraft.msquarelib.service.IBeaconService"> </service> <service android:name="com.appxcraft.msquarelib.web.ExecuteRequest" /> ## Usage
1. Add implements IBeaconConsumer in Activity/Service classe.g. public class mainactivity extends Activity implements BeaconConsumer  2. Declare an instance of IBeaconManager inside the Activyt/Service classprivate IBeaconManager iBeaconManager; 3. Instantiate the IBeaconManager: iBeaconManager = IBeaconManager.getInstanceForApplication(getApplicationContext());        iBeaconManager.bind(this); 4. Update the scan intervals (Optional) and set the AppID from console.msquare.io :        iBeaconManager.setForegroundBetweenScanPeriod(2000);        iBeaconManager.setForegroundScanPeriod(1500);        iBeaconManager.setAppID("88bb0d1f-7f0d-4a80-977f-b2ef9227aefd"); 5. Define actions for notifiers when scanning service connected and call initContent to sync beacon data:    @Override    public void onIBeaconServiceConnect() {        iBeaconManager.setRangeNotifier(            new RangeNotifier() {                @Override                public void didRangeBeaconsInRegion(Collection<IBeacon> iBeacons, Region region) {                    }            });          iBeaconManager.setMonitorNotifier(                new MonitorNotifier() {                    @Override                    public void didEnterRegion(Region region, IBeacon iBeacon) {                     }                     @Override                    public void didExitRegion(Region region) {                    }                     @Override                    public void didDetermineStateForRegion(int i, Region region, IBeacon iBeacon) {                     }                });         iBeaconManager.setDataNotifier(new DataNotifier() {            @Override            public void sync(JSONObject objContent) {                            }             @Override            public void addData(String status) {             }             @Override            public void error(String s) {             }        });         iBeaconManager.initContent();    } 6. Unbind IBeacon once activity/service stopped or when deemed necessary:    @Override    public void onDestroy()    {        if(iBeaconManager!=null)            iBeaconManager.unBind(this);        super.onDestroy();    }	 7. Adding Analytics Data:IBeacon ibeacon = new IBeacon(UUID,Major,Minor,Distance);iBeaconManager.addData(ibeacon, Consts.REPORTINGDATATYPE_DRILLDOWN);

