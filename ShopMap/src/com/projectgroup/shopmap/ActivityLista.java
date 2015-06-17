package com.projectgroup.shopmap;

import java.util.ArrayList;
import java.util.Iterator;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ActivityLista extends Activity {

	ListAdapter listAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista);
		
		ArrayList<String> shopList = new ArrayList<String>();
		final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        final ListView shopM= (ListView)findViewById(R.id.list_selection);
        shopM.setAdapter(listAdapter);
        
        ArrayList<ParseObject> result = ShopMapApplication.getShopsByLocation(location user);
        
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




