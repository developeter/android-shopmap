package com.projectgroup.shopmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MapActivity extends Activity implements OnMapReadyCallback {

	private static final long LOCATION_REFRESH_DISTANCE = 1;
	private static final String NAMEPLACE = "NOME";
	private static final long LOCATION_REFRESH_TIME = 5000;
	private static final int GEOFENCE_RADIUS_IN_METERS = 200;
	private static final String MY_LOC = "mylocation";
	private static final String SearchCategoryNegozi = "negozio";
	private static final String SearchCategoryAttrazioni = "puntointeresse";

	private Location myLocation;
	private MapFragment mapFragment;
	private GoogleMap Mymap;
	private HashMap<Marker, ParseObject> mHashMap;
	// private MapFragment mapFragment;
	double latitude, longitude;
	OnMapReadyCallback callback;
	GoogleMapOptions options;
	Geofence fence;
	protected ArrayList<Geofence> mGeofenceList;
	protected List<ParseObject> mParseObjectList;
	// private GoogleMap mMap;
	double myLatitude, myLongitude;

	// private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.googlemap);

		if (savedInstanceState != null) {
			myLocation = savedInstanceState.getParcelable(MY_LOC);
		}

		// MapView viewMap = (MapView) findViewById(R.id.map);

		mapFragment = (MapFragment) getFragmentManager().findFragmentById(
				R.id.map);
		mapFragment.getMapAsync(this);
		Mymap = mapFragment.getMap();

		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, true);
		myLocation = locationManager.getLastKnownLocation(bestProvider);

		locationManager.requestLocationUpdates(0, 0, criteria,
				mLocationListener, null);
		if (myLocation == null) {
			ToastMaker("position null");
			locationManager.requestLocationUpdates(bestProvider,
					LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE,
					mLocationListener);
		}
	}

	@Override
	public void onMapReady(final GoogleMap map) {

		// map.clear();

		double lat = myLocation.getLatitude();
		double lon = myLocation.getLongitude();

		map.setMyLocationEnabled(true);

		final ParseGeoPoint userposition = new ParseGeoPoint(lat, lon);
		final double maxDistance = 20;

		ParseQuery<ParseObject> query = ParseQuery.getQuery("attivita");

		// query.whereEqualTo("categoria_principale", categoria);
		query.whereWithinKilometers("posizione", userposition, maxDistance);

		query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> listNegozi, ParseException e) {
				if (e == null) {

					ParseObject.pinAllInBackground(listNegozi);
					listNegozi.iterator();
					for (ParseObject object : listNegozi) {
						object.remove("descrizione");
						object.remove("immagini");
						object.remove("createdAt");
						object.remove("updatedAt");
						object.remove("ACL");
						ParseGeoPoint pos = object
								.getParseGeoPoint("posizione");
						double dist = pos.distanceInKilometersTo(userposition);
						object.add("distanza", dist);

						// listNegozi.add(object);
					}
					;

					mParseObjectList = listNegozi;
					ParsePointsOnMap(map, mParseObjectList);
				} else {
					Log.d("errore", "Errore nel download: " + e.getMessage());
				}

			}

		});

		// newParseQuery(lat, lon, map, SearchCategoryNegozi);
		// newParseQuery(lat, lon, map, SearchCategoryAttrazioni);

		LatLng latlng = new LatLng(lat, lon);
		CenterCamera(map, latlng);

		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {

				ParseObject pObject = mHashMap.get(marker);
				String data = pObject.getString("nome_attivita");
				ToastMaker(data);
				Bundle bundle = new Bundle();
				Intent intent = new Intent(MapActivity.this,
						ActivityDettaglio.class);
				bundle.putString(NAMEPLACE, data);
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});

	}

	// metodo generazione toast
	public void ToastMaker(String message) {
		assert (message != null);
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	// motion camera
	public void CenterCamera(GoogleMap map, LatLng latlng) {

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(latlng).zoom(9).bearing(90) // orientation camera to
													// east
				.tilt(30) // camera to degrees
				.build(); // Creates CameraPosition from builder
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}

	// locationListener
	private final LocationListener mLocationListener = new LocationListener() {
		@Override
		public void onLocationChanged(final Location location) {
			// your code here
			ToastMaker("onLocationChanged");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			ToastMaker("onStatusChanged");
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			ToastMaker("GPS ATTIVATO");
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			ToastMaker("GPS DISATTIVATO");
		}
	};

	// convert parsobjects into makers and add these on the map

	private void ParsePointsOnMap(GoogleMap map, List<ParseObject> ParseList) {

		mHashMap = new HashMap<Marker, ParseObject>();
		for (ParseObject pObject : ParseList) {

			ParseGeoPoint pGeoP = pObject.getParseGeoPoint("posizione");
			double lat = pGeoP.getLatitude();
			double lan = pGeoP.getLongitude();
			// test per category
			// int idCategory = 0;
			String tTitle = pObject.getString("nome_attivita");
			String mCategory = pObject.getString("categoria_secondaria");

			LatLng newLatLng = new LatLng(lat, lan);
			Marker tempMaker = map.addMarker(new MarkerOptions()
					.position(newLatLng).title(tTitle).snippet(mCategory));
			tempMaker.showInfoWindow();
			buildGeofence(tTitle, map, newLatLng);
			mHashMap.put(tempMaker, pObject);
		}
	}

	// actionbar filter
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_map_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_filter:
			// openFilter();
			Mymap.clear();
			// ToastMaker("Update to PremiumAccount to use this feature");
			return true;
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void SendNotification(int mId, String notifyTitle, String notifyText) {

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(notifyTitle + " num " + mId)
				.setContentText(notifyText);

		Intent resultIntent = new Intent(this, MainActivity.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

		stackBuilder.addParentStack(MainActivity.class);

		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		mNotificationManager.notify(mId, mBuilder.build());
	}

	private void buildGeofence(String title, GoogleMap map, LatLng geofencePoint) {
		long NEVER_EXPIRE = -1;
		int GEOFENCE_TRANSITION_ENTER = 1;
		int radius = 250;
		String requestId = title;
		Geofence.Builder geofence = new Geofence.Builder();
		geofence.setCircularRegion(geofencePoint.latitude,
				geofencePoint.longitude, radius);
		geofence.setRequestId(requestId);
		geofence.setExpirationDuration(NEVER_EXPIRE);
		geofence.setTransitionTypes(GEOFENCE_TRANSITION_ENTER);
		geofence.setNotificationResponsiveness(0);
		geofence.build();
		CircleOptions circleOptions = new CircleOptions();
		circleOptions.center(geofencePoint);
		circleOptions.radius(radius);
		circleOptions.strokeColor(Color.BLACK);
		circleOptions.fillColor(0x30ff0000);
		circleOptions.strokeWidth(2);
		map.addCircle(circleOptions);

	}

	private void newParseQuery(double lat, double lon, final GoogleMap mMap,
			String categoria) {

		final ParseGeoPoint userposition = new ParseGeoPoint(lat, lon);
		final double maxDistance = 20;

		ParseQuery<ParseObject> query = ParseQuery.getQuery("attivita");

		// query.whereEqualTo("categoria_principale", categoria);
		query.whereWithinKilometers("posizione", userposition, maxDistance);

		query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> listNegozi, ParseException e) {
				if (e == null) {

					ParseObject.pinAllInBackground(listNegozi);
					listNegozi.iterator();
					for (ParseObject object : listNegozi) {
						object.remove("descrizione");
						object.remove("immagini");
						object.remove("createdAt");
						object.remove("updatedAt");
						object.remove("ACL");
						ParseGeoPoint pos = object
								.getParseGeoPoint("posizione");
						double dist = pos.distanceInKilometersTo(userposition);
						object.add("distanza", dist);

						// listNegozi.add(object);
					}
					;

					mParseObjectList = listNegozi;
					ParsePointsOnMap(mMap, mParseObjectList);
				} else {
					Log.d("errore", "Errore nel download: " + e.getMessage());
				}

			}

		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	// motion camera
	public void CenterCamera(GoogleMap map, LatLng latlng) {

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(latlng).zoom(10).bearing(90) // orientation camera to
														// east
				.tilt(30) // camera to degrees
				.build(); // Creates CameraPosition from builder
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}

	// locationListener
	private final LocationListener mLocationListener = new LocationListener() {
		@Override
		public void onLocationChanged(final Location location) {
			// your code here
			ToastMaker("onLocationChanged");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			ToastMaker("onStatusChanged");
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			ToastMaker("GPS ATTIVATO");
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			ToastMaker("GPS DISATTIVATO");
		}
	};

	/*
	 * // with geofence private void MakerFactory(GoogleMap map, double lat,
	 * double lan, int idCategory, String tTitle, String mCategory) {
	 * 
	 * LatLng newLatLng = new LatLng(lat, lan); Marker tempMaker =
	 * map.addMarker(new MarkerOptions()
	 * .position(newLatLng).title(tTitle).snippet(mCategory));
	 * tempMaker.showInfoWindow(); buildGeofence(tTitle, map, newLatLng); }
	 */

	// convert parsobjects into makers and add these on the map
	private void ParsePointsOnMap(GoogleMap map, List<ParseObject> ParseList) {

		mHashMap = new HashMap<Marker, ParseObject>();
		for (ParseObject pObject : ParseList) {

			ParseGeoPoint pGeoP = pObject.getParseGeoPoint("posizione");
			double lat = pGeoP.getLatitude();
			double lan = pGeoP.getLongitude();
			// test per category
			int idCategory = 0;
			String tTitle = pObject.getString("nome_attivita");
			String mCategory = pObject.getString("categoria_secondaria");

			LatLng newLatLng = new LatLng(lat, lan);
			Marker tempMaker = map.addMarker(new MarkerOptions()
					.position(newLatLng).title(tTitle).snippet(mCategory));
			tempMaker.showInfoWindow();
			buildGeofence(tTitle, map, newLatLng);
			mHashMap.put(tempMaker, pObject);
		}
	}

	// actionbar filter
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mapFragment.onResume();
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

		outState.putParcelable(MY_LOC, myLocation);
		super.onSaveInstanceState(outState);
		// outState.get(MY_LOC, myLocation);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mapFragment.onPause();
		Mymap = null;
		locationManager.super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
