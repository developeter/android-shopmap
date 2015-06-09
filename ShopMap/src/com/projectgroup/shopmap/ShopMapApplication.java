package com.projectgroup.shopmap;

import com.parse.Parse;

import android.app.Application;

public class ShopMapApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);
		
		Parse.initialize(this, "6kKpkOx9HzE6dJ2cR5JSFgNCwyIhLtTcSayipPNu", "cVg9BkGbVivvdy8Fxo14ERX7rfpkxdYcgdDhID0h");
	}

}
