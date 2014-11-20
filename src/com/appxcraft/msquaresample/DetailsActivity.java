package com.appxcraft.msquaresample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.appxcraft.msquaresample.Common.Entity;
import com.appxcraft.msquaresample.Common.ListAdapter;

/**
 * Created by darrenso on 6/11/14.
 */
public class DetailsActivity extends Activity {
    Entity beacon ;

    ListAdapter adapter;
    ListView tagList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        adapter = new ListAdapter(this);

        tagList = (ListView)findViewById(R.id.tagList);

        tagList.setAdapter(adapter);


        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            beacon = (Entity) extras.getSerializable("Beacon");
            if (beacon == null)
                return;
        }else
        {
            Log.v("DetailsActivity", "null input");
            return;
        }

        for(Entity tag:beacon.tags)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Value1:");
            sb.append(tag.getMyValue01());
            sb.append("\n");
            sb.append("Value2:");
            sb.append(tag.getMyValue02());
            sb.append("\n");
            sb.append("Value3:");
            sb.append(tag.getMyValue03());
            sb.append("\n");
            Entity details = new Entity();
            details.setMyValue04(sb.toString());
            adapter.addItem(details);
        }
    }
}