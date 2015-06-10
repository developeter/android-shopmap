package com.projectgroup.shopmap;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity{
	
	private TextView testText;
	private Button GoToMaps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		testText = (TextView)findViewById(R.id.textTest);
		GoToMaps = (Button)findViewById(R.id.btnOpenMap);
		
		GoToMaps.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent add = new Intent(MainActivity.this, MapActivity.class);
				startActivity(add);		
			}
		});
		
	}

}
