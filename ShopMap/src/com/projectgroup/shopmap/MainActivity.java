package com.projectgroup.shopmap;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private int listsize = 0;
	private TextView testText;
	private Button GoToMaps, GoToList;
	private Location lastLocation;
	private Location currentLocation;
	private final String USER_LOCATION = "user_location";
	private final String USER_DATA = "user_data";

	// private final Map<String, Marker> mapMarkers = new HashMap<String,
	// Marker>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*
		 * ParseObject testObject = new ParseObject("TestObject");
		 * testObject.put("foo", "bar");ù testObject.saveInBackground();
		 */

		GoToMaps = (Button) findViewById(R.id.btnOpenMap);
		GoToList = (Button) findViewById(R.id.btnOpenList);

		GoToMaps.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent inMapAct = new Intent(MainActivity.this,
						MapActivity.class);

				startActivity(inMapAct);
			}
		});

		GoToList.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent inListAct = new Intent(MainActivity.this,
						ActivityLista.class);

				startActivity(inListAct);
			}
		});

	}

	public void setText(int value) {

		testText.setText("" + value);
	}

}
