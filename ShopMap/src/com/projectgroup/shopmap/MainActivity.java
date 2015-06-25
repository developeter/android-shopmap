package com.projectgroup.shopmap;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Activity {

	private int listsize = 0;
	private TextView testText;
	private Button GoToMaps;
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
		testText = (TextView) findViewById(R.id.textTest);
		GoToMaps = (Button) findViewById(R.id.btnOpenMap);

		GoToMaps.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				/*
				 * Location myLoc = (currentLocation == null) ? lastLocation :
				 * currentLocation; if (myLoc == null) {
				 * Toast.makeText(MainActivity.this,
				 * "Prova ancora se la posizione non appare",
				 * Toast.LENGTH_LONG).show(); return; }
				 */
				Intent inMapAct = new Intent(MainActivity.this,
						MapActivity.class);
				// inMapAct.putExtra(USER_LOCATION, myLoc);
				startActivity(inMapAct);
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
					Log.d("erroretest", "Error: " + e.getMessage());
				}
			}
		});

	}

	public void setText(int value) {

		testText.setText("" + value);
	}

}
