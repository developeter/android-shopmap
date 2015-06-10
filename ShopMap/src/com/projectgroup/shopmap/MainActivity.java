package com.projectgroup.shopmap;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity{
	
	
	private int listsize = 0;
	private TextView testText;
	private Button GoToMaps;
	private Button gallery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground();*/
		testText = (TextView)findViewById(R.id.textTest);
		GoToMaps = (Button)findViewById(R.id.btnOpenMap);
		
		
		GoToMaps.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent add = new Intent(MainActivity.this, MapActivity.class);
				startActivity(add);				
			}
		});
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("attivita");
		query.whereEqualTo("categoria_principale", "negozio");
		query.findInBackground(new FindCallback<ParseObject>() {
		 
			
			
			public void done(List<ParseObject> listNegozi, ParseException e) {
		        if (e == null) {
		        	listsize = listNegozi.size();
		        	setText(listsize);
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		        }
		    }
		});
		
		
		gallery = (Button) findViewById(R.id.btn_gallery);
		gallery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent gal = new Intent(MainActivity.this, GalleryActivity.class);
				startActivity(gal);				
			}
		});
		
		
	}
	
	public void setText(int value){	
		testText.setText(""+value);
	}

}
