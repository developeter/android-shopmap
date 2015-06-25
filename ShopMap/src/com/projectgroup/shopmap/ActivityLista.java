package com.projectgroup.shopmap;

import java.util.ArrayList;

import com.parse.ParseObject;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ActivityLista extends Activity {

	Location user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista);
		
		final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        final ListView shopM= (ListView)findViewById(R.id.list_selection);
        shopM.setAdapter(listAdapter);
        
        user.setLatitude(45.9513);
        user.setLongitude(12.6802);
        
        ArrayList<ParseObject> result = ShopMapApplication.getShopsByLocation(user);
        
        for(ParseObject object: result) {
                         String nome = object.getString("nome_attivita").toString();
                         String categoria_secondaria = object.getString("categoria_secondaria").toString();
                         String distanza = String.valueOf(object.getDouble("distanza"));
                         
                          listAdapter.add(nome);
                          listAdapter.add(categoria_secondaria);
                          listAdapter.add(distanza);
          			}
        }
         
		
}




