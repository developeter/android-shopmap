package com.projectgroup.shopmap;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity implements OnMapReadyCallback {

	private static final long LOCATION_REFRESH_DISTANCE = 1;
	private static final long LOCATION_REFRESH_TIME = 1000;
	private static final int GEOFENCE_RADIUS_IN_METERS = 200;

	private Location myLocation;
	// private MapFragment mapFragment;
	double lat, lon;
	OnMapReadyCallback callback;
	GoogleMapOptions options;
	Geofence fence;
	protected ArrayList<Geofence> mGeofenceList;
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
	public void onMapReady(GoogleMap map) {

		map.setMyLocationEnabled(true);

		// makerFactory Test
		double lonTest = 45.9589554;
		double latTest = 12.6667163;

		for (int i = 0; i < 50; i++) {

			MakerFactory(map, lonTest, latTest, 0);
			lonTest = lonTest + 0.0011100;
			latTest = latTest + 0.0011000;
		}

		/*
		 * LatLng newLatLng = new LatLng(myLocation.getLatitude(),
		 * myLocation.getLongitude()); CenterCamera(map, newLatLng);
		 */

	}

	// metodo generazione toast
	private void ToastMaker(String message) {
		assert (message != null);
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	// motion camera
	public void CenterCamera(GoogleMap map, LatLng latlng) {

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(latlng).zoom(17).bearing(90) // orientation camera to
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

	// without cluster
	private void MakerFactory(GoogleMap map, double lat, double lan,
			int idCategory) {

		String tempTitle = "";
		map.addMarker(new MarkerOptions().position(new LatLng(lat, lan)).title(
				tempTitle));
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
			ToastMaker("PREMUTO");
			return true;
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
