package com.projectgroup.shopmap;

<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

>>>>>>> origin/Database
import android.app.Application;
import android.location.Location;
import android.util.Log;

import com.parse.Parse;

public class ShopMapApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);

		Parse.initialize(this, "6kKpkOx9HzE6dJ2cR5JSFgNCwyIhLtTcSayipPNu",
				"cVg9BkGbVivvdy8Fxo14ERX7rfpkxdYcgdDhID0h");
	}
	
	
	public ArrayList<String> getPhotosById(String Id) {
		
		final ArrayList<String> photos = new ArrayList<String>();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("attivita");
		query.fromLocalDatastore();
		query.getInBackground(Id, new GetCallback<ParseObject>() {
		  public void done(ParseObject object, ParseException e) {
		    if (e == null) {
		    	
		    	try {
					object.pin();
				} catch (ParseException e1) {
					Log.d("errore", "Impossibile salvare sul database: " + e1.getMessage());
				}
		    	
		    	String img1 = object.getString("image1");
		    	photos.add(img1);
		    	String img2 = object.getString("image2");
		    	photos.add(img2);
		    	String img3 = object.getString("image3");
		    	photos.add(img3);
		    	String img4 = object.getString("image4");
		    	photos.add(img4);
		    	
		    } else {
		    	Log.d("errore", "Errore nel download: " + e.getMessage());
		    }
		  }
		});
		
		return photos;
	}
	
	
	
	
	public ArrayList<ParseObject> getShopsByLocation(Location user){
		
		final ArrayList<ParseObject> result = new ArrayList<ParseObject>();
		
		final double lat = user.getLatitude();
		final double lon = user.getLongitude();
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
		        	for (ParseObject object:listNegozi) {
		        		object.remove("descrizione");
		        		object.remove("immagini");
		        		object.remove("createdAt");
		        		object.remove("updatedAt");
		        		object.remove("ACL");
		        		ParseGeoPoint pos = object.getParseGeoPoint("posizione");
		        		double dist = pos.distanceInKilometersTo(userposition);
		        		object.add("distanza", dist);
		        		result.add(object);
		        	};
		        	
		        } else {
		        	Log.d("errore", "Errore nel download: " + e.getMessage());
		        }
		    }
		});	
		
		return result;
	}
	
	
	
	
	public ParseObject getDetailById(String Id){
				
		ParseObject result = new ParseObject("attivita");
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("attivita");
		query.fromLocalDatastore();
		query.getInBackground(Id, new GetCallback<ParseObject>() {
		  public void done(ParseObject object, ParseException e) {
		    if (e == null) {
		    	
		    	try {
					object.pin();
				} catch (ParseException e1) {
					Log.d("errore", "Impossibile salvare sul database: " + e1.getMessage());
				}
		    	object.remove("immagini");
        		object.remove("createdAt");
        		object.remove("updatedAt");
        		object.remove("ACL");
		    	
		    } else {
		    	Log.d("errore", "Errore nel download: " + e.getMessage());
		    }
		  }
		});
		
		return result;
		
	}
	
	
}
