package com.projectgroup.shopmap;

import android.app.Application;

import com.parse.Parse;

public class ShopMapApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);

		Parse.initialize(this, "6kKpkOx9HzE6dJ2cR5JSFgNCwyIhLtTcSayipPNu",
				"cVg9BkGbVivvdy8Fxo14ERX7rfpkxdYcgdDhID0h");
	}

}
