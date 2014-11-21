package com.appxcraft.msquaresample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.appxcraft.msquarelib.*;
import com.appxcraft.msquarelib.common.Consts;
import com.appxcraft.msquarelib.entity.BeaconEntity;
import com.appxcraft.msquaresample.Common.Entity;
import com.appxcraft.msquaresample.Common.ListAdapter;
import com.appxcraft.msquaresample.Common.RuntimeRegisters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Iterator;

public class MainActivity extends Activity implements IBeaconConsumer,AdapterView.OnItemClickListener {

    private IBeaconManager iBeaconManager;
    ArrayList<String> beacons = new ArrayList<String>();

    ListAdapter adapter;
    ListView beaconList;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Initialize IBeaconManager
        iBeaconManager = IBeaconManager.getInstanceForApplication(getApplicationContext());
        iBeaconManager.bind(this);

        // set scanning parameter
        iBeaconManager.setForegroundBetweenScanPeriod(2000);
        iBeaconManager.setForegroundScanPeriod(1500);

        // set APP ID available from https://console.msquare.io admin page
        // Please register an account and create an App to get an ID and apply the ID in the property below
        iBeaconManager.setAppID("");


        adapter = new ListAdapter(this);

        beaconList = (ListView)findViewById(R.id.beaconList);

        beaconList.setAdapter(adapter);
        beaconList.setOnItemClickListener(this);
        RuntimeRegisters.syncContent = new ArrayList<Entity>();
    }


    // More configuration upon successfully connecting to IBeaconService
    @Override
    public void onIBeaconServiceConnect() {

        // Setup events when beacon in range detected
        iBeaconManager.setRangeNotifier(
            new RangeNotifier() {
                @Override
                public void didRangeBeaconsInRegion(Collection<IBeacon> iBeacons, Region region) {
                    if (iBeacons.size() > 0) {
                        Iterator<IBeacon> beaconIterator = iBeacons.iterator();

                        while (beaconIterator.hasNext()) {
                            IBeacon iBeacon = beaconIterator.next();
                            String beaconUUID= iBeacon.getProximityUuid().toLowerCase()+iBeacon.getMajor()+iBeacon.getMinor();

                            Entity checkBeacon = new Entity();
                            checkBeacon.setMyValue01(iBeacon.getProximityUuid().toLowerCase());
                            checkBeacon.setMyValue02(iBeacon.getMajor()+"");
                            checkBeacon.setMyValue03(iBeacon.getMinor() + "");


                                int index = RuntimeRegisters.syncContent.indexOf(checkBeacon);
                                if(index>=0) {
                                    Entity beacon = RuntimeRegisters.syncContent.get(index);
                                    beacon.setAccuracy(iBeacon.getAccuracy());

                                    if(!beacons.contains(beaconUUID)) {

                                        beacons.add(beaconUUID);
                                        adapter.addItem(beacon);
                                    }

                                }



                        }
                    }
                }
            });

        // Setup events when beacon state changed
        iBeaconManager.setMonitorNotifier(
                new MonitorNotifier() {
                    // Setup event when new beacon detected
                    @Override
                    public void didEnterRegion(Region region, IBeacon iBeacon) {

                    }
                    // Setup event when  beacon exits (not in range anymore)
                    @Override
                    public void didExitRegion(Region region) {
                        String beaconUUID= region.getProximityUuid().toLowerCase()+region.getMajor()+region.getMinor();
                        if(beacons.contains(beaconUUID))
                        {
                            Entity checkBeacon = new Entity();
                            checkBeacon.setMyValue01(region.getProximityUuid().toLowerCase());
                            checkBeacon.setMyValue02(region.getMajor()+"");
                            checkBeacon.setMyValue03(region.getMinor() + "");
                            int index = RuntimeRegisters.syncContent.indexOf(checkBeacon);
                            if(index>=0) {
                                Entity beacon = RuntimeRegisters.syncContent.get(index);
                                beacons.remove(beaconUUID);
                                adapter.removeContent(beacon);

                            }
                        }
                    }

                    // Setup event when  beacon state is being determined
                    @Override
                    public void didDetermineStateForRegion(int i, Region region, IBeacon iBeacon) {

                    }
                });

        // Setup events when data from msquare admin received
        iBeaconManager.setDataNotifier(new DataNotifier() {

            // Setup event when beacon data from msquare admin received
            @Override
            public void sync(ArrayList<BeaconEntity> objContent) {
                    //Loop through all beacon entities
                    for(BeaconEntity beaconEntity:objContent)
                    {
                        Entity entity = new Entity();

                        //retrieve values of the beacon
                        entity.setMyID(beaconEntity.values.get("beaconID"));
                        entity.setMyValue01(beaconEntity.values.get("uuid").toLowerCase());
                        entity.setMyValue02(beaconEntity.values.get("major"));
                        entity.setMyValue03(beaconEntity.values.get("minor"));
                        entity.setMyValue04(beaconEntity.values.get("name"));

                        entity.tags = new ArrayList<Entity>();

                        //loop through all tags in the beacon
                        for(int j = 0; j < beaconEntity.tags.size(); j++)
                        {
                            Entity tag = new Entity();
                            //retrieve one of the tag
                            Dictionary<String,String> tagValues = beaconEntity.tags.get(j);

                            //retrieve values of the tag
                            if(tagValues.get("title")!=null)
                                tag.setMyValue01(tagValues.get("title"));
                            if(tagValues.get("Title")!=null)
                                tag.setMyValue01(tagValues.get("Title"));
                            if(tagValues.get("Param")!=null)
                                tag.setMyValue02(tagValues.get("Param"));
                            if(tagValues.get("address")!=null)
                                tag.setMyValue02(tagValues.get("address"));
                            if(tagValues.get("Param2")!=null)
                                tag.setMyValue03(tagValues.get("Param2"));
                            if(tagValues.get("name")!=null)
                                tag.setMyValue03(tagValues.get("name"));
                            entity.tags.add(tag);
                        }
                        RuntimeRegisters.syncContent.add(entity);

                    }


            }
            // Setup event when beacon analytic data response from msquare admin received
            @Override
            public void addData(String status) {

            }
            // Setup event when error response from msquare admin received
            @Override
            public void error(String s) {

            }
        });

        iBeaconManager.initContent();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Entity beacon = (Entity) adapter.getItem(position);

        IBeacon ibeacon = new IBeacon(beacon.getMyValue01(),Integer.parseInt(beacon.getMyValue02()),Integer.parseInt(beacon.getMyValue03()),beacon.getAccuracy());
        //Add Analytics Data
        iBeaconManager.addData(ibeacon, Consts.REPORTINGDATATYPE_DRILLDOWN);

        Bundle bundle = new Bundle();
        Intent intent;


        intent = new Intent(this, DetailsActivity.class);
        bundle.putSerializable("Beacon", beacon);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroy()
    {
        //Stop Scanning
        if(iBeaconManager!=null)
            iBeaconManager.unBind(this);
        super.onDestroy();
    }
}
