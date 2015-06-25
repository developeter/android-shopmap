package com.projectgroup.shopmap;

import java.util.ArrayList;
import com.parse.ParseObject;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityLista extends Activity {
	
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista);
		
		lv = (ListView)findViewById(R.id.list_selection);
		
		final double latitude = 45.9513;
        final double longitude= 12.6802;
        
        Location user = new Location("");
        user.setLatitude(latitude);
        user.setLongitude(longitude);
       
        ArrayList<ParseObject> result = ShopMapApplication.getShopsByLocation(user);
        
        ArrayAdapter<ParseObject> arrayAdapter = new ArrayAdapter<ParseObject>(
        		this,
        		android.R.layout.simple_list_item_1,
                result );

        lv.setAdapter(arrayAdapter);
        
      }
         
		
}




