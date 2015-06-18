package com.projectgroup.shopmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity{
	
	private Button GoToMaps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
