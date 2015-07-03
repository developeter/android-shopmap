package com.projectgroup.shopmap;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	DrawerLayout mDrawerLayout;
	NavigationView navigation;
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
		
		 final ActionBar actionBar = getSupportActionBar();
		    actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
		    actionBar.setDisplayHomeAsUpEnabled(true);
		    
		

		GoToMaps = (Button) findViewById(R.id.btnOpenMap);
		GoToList = (Button) findViewById(R.id.btnOpenList);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation);
		
		GoToMaps.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent inMapAct = new Intent(MainActivity.this,MapActivity.class);
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
		
		navigationSlide();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            mDrawerLayout.openDrawer(GravityCompat.START);
	            return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	private void navigationSlide() {
		navigation = (NavigationView) findViewById(R.id.navigation_d);
	    navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem menuItem) {
				int id = menuItem.getItemId();
	            switch (id) {
	                case R.id.navItem1:
	                	Intent inMapAct = new Intent(MainActivity.this,MapActivity.class);
	    				startActivity(inMapAct);
	                    break;
	                case R.id.navItem2:
	                	Intent inListAct = new Intent(MainActivity.this,ActivityLista.class);

	    				startActivity(inListAct);
	                    break;
	            }
	            mDrawerLayout.closeDrawers();
				return false;
			}
	    });
	}
	
	public void setText(int value) {

		testText.setText("" + value);
	}

}
