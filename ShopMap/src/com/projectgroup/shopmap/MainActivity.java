package com.projectgroup.shopmap;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener {

	private int listsize = 0;

	private TextView testText;
	private Button GoToMaps, GoToList;
	private Location lastLocation;
	private Location currentLocation;
	private final String USER_LOCATION = "user_location";
	private final String USER_DATA = "user_data";
	private Location mLastLocation;
	private GoogleApiClient mGoogleApiClient;
	int x = 0;

	// test
	TextView mLongitudeText;
	TextView mLatitudeText;

	// private final Map<String, Marker> mapMarkers = new HashMap<String,
	// Marker>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GoToMaps = (Button) findViewById(R.id.btnOpenMap);
		GoToList = (Button) findViewById(R.id.btnOpenList);

		mLongitudeText = (TextView) findViewById(R.id.txtLongitudeText);
		mLatitudeText = (TextView) findViewById(R.id.txtLatitudeText);
		buildGoogleApiClient();

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

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
	}

	@Override
	public void onConnected(Bundle connectionHint) {

		mLastLocation = LocationServices.FusedLocationApi
				.getLastLocation(mGoogleApiClient);
		if (mLastLocation != null) {

			mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));

			mLongitudeText
					.setText(String.valueOf(mLastLocation.getLongitude()));
		}

	}

	public void setText(int value) {

		testText.setText("" + value);
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}

}
