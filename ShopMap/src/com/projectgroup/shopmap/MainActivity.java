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
		List<ParseObject> objects = query.find();
		
		ParseObject.pinAllInBackground(objects);
		
		query.whereEqualTo("categoria_principale", "negozio");
		query.fromLocalDatastore();
		query.findInBackground(new FindCallback<ParseObject>() {
			
			public void done(List<ParseObject> listNegozi, ParseException e) {
		        if (e == null) {
		        	listsize = listNegozi.size();
		        	testText.setText(listsize);
		        } else {
		        	final String msg = "Error fetching data: " + e.getMessage();
		            testText.setText(msg);
		        }
		    }
		});
		
	}
	
	public void setText(int value){
			
		testText.setText(""+value);
	}

}
