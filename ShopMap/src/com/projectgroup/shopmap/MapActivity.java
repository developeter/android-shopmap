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
	private static final long LOCATION_REFRESH_TIME = 5000;
	private static final int GEOFENCE_RADIUS_IN_METERS = 200;
	private String SearchCategory = "negozi";

	private Location myLocation;

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

		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, true);

		myLocation = locationManager.getLastKnownLocation(bestProvider);

		if (myLocation == null) {
			ToastMaker("position null");
			locationManager.requestLocationUpdates(bestProvider,
					LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE,
					mLocationListener);
		}
	}

	@Override
	public void onMapReady(final GoogleMap map) {
		// Location testLocation = new Location("");
		// testLocation.setLatitude(latTest);
		// testLocation.setLongitude(lonTest);

		// mParseObjectList =
		// ShopMapApplication.getShopsByLocation(testLocation);
		// map.clear();

		double lat = myLocation.getLatitude();
		double lon = myLocation.getLongitude();

		map.setMyLocationEnabled(true);

		final ParseGeoPoint userposition = new ParseGeoPoint(lat, lon);
		final double maxDistance = 20;

		ParseQuery<ParseObject> query = ParseQuery.getQuery("attivita");

		query.whereEqualTo("categoria_principale", "negozio");
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

		// makerFactory Test

		// testMarkerMap
		/*
		 * for (int i = 0; i < 50; i++) {
		 * 
		 * MakerFactory(map, lonTest, latTest, 0); lonTest = lonTest +
		 * 0.0011100; latTest = latTest + 0.0011000; }
		 */

		/*
		 * LatLng newLatLng = new LatLng(myLocation.getLatitude(),
		 * myLocation.getLongitude()); CenterCamera(map, newLatLng);
		 */

		LatLng latlng = new LatLng(lat, lon);
		CenterCamera(map, latlng);

		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {

				ParseObject pObject = mHashMap.get(marker);
				String data = pObject.getString("0");
				Bundle bundle = new Bundle();
				Intent intent = new Intent(MapActivity.this,
						ActivityDettaglio.class);
				bundle.putString("NOME", data);
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
			ToastMaker("Update to PremiumAccount to use this feature");
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

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	// Cluster e co

	/*
	 * private void setUpClusterer(GoogleMap map) { // Declare a variable for
	 * the cluster manager. ClusterManager<MyItem> mClusterManager;
	 * 
	 * // Position the map. getMap(tempMap).moveCamera(
	 * CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));
	 * 
	 * // Initialize the manager with the context and the map. // (Activity
	 * extends context, so we can pass 'this' in the constructor.)//
	 * mClusterManager = new ClusterManager<MyItem>(this, getMap(tempMap));
	 * 
	 * // Point the map's listeners at the listeners implemented by the cluster
	 * // manager. getMap(tempMap).setOnCameraChangeListener(mClusterManager);
	 * map.setOnMarkerClickListener(mClusterManager);
	 * 
	 * // Add cluster items (markers) to the cluster manager.
	 * addItems(mClusterManager); }
	 * 
	 * private void addItems(ClusterManager<MyItem> mClusterManager) {
	 * 
	 * // Set some lat/lng coordinates to start with. double lat = 51.5145160;
	 * double lng = -0.1270060;
	 * 
	 * // Add ten cluster items in close proximity, for purposes of this //
	 * example. for (int i = 0; i < 10; i++) { double offset = i / 60d; lat =
	 * lat + offset; lng = lng + offset; MyItem offsetItem = new MyItem(lat,
	 * lng); mClusterManager.addItem(offsetItem); }
	 * 
	 * }
	 */
	// myLatitude = myLocation.getLongitude();
	// myLongitude = myLocation.getLatitude();
	// map.moveCamera(CameraUpdateFactory.newLatLngZoom(new
	// LatLng(myLatitude,
	// myLongitude), 16));

	/*
	 * @Override public void onLocationChanged(Location location) { // TODO
	 * Auto-generated method stub
	 */
	/*
	 * @Override public void onStatusChanged(String provider, int status, Bundle
	 * extras) { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void onProviderEnabled(String provider) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void onProviderDisabled(String provider) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void onMapReady(GoogleMap arg0) { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 */

	// __________________________________________________________________________

	/*
	 * SupportMapFragment fm = (SupportMapFragment)
	 * getSupportFragmentManager().findFragmentById(R.id.map);
	 * 
	 * 
	 * googleMap = fm.getMap();
	 * 
	 * 
	 * googleMap.setMyLocationEnabled(true);
	 */

	// Criteria criteria = new Criteria();

	/*
	 * Location location = LocationManager.getLastKnownLocation(LocationManager
	 * .getBestProvider(criteria, false)); if (location != null) {
	 * googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom( new
	 * LatLng(location.getLatitude(), location.getLongitude()), 13));
	 * 
	 * CameraPosition cameraPosition = new CameraPosition.Builder() .target(new
	 * LatLng(location.getLatitude(), location.getLongitude())) //centro mappa
	 * .zoom(17) .bearing(90) //orientazione .tilt(40) //tilt .build(); //
	 * posizione camera googleMap.animateCamera(CameraUpdateFactory
	 * .newCameraPosition(cameraPosition));
	 * 
	 * }
	 */
}
