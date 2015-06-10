package com.projectgroup.shopmap;

import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Application;

public class ShopMapApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);
		
		Parse.initialize(this, "6kKpkOx9HzE6dJ2cR5JSFgNCwyIhLtTcSayipPNu", "cVg9BkGbVivvdy8Fxo14ERX7rfpkxdYcgdDhID0h");
	}
	
	public List<ParseObject> getParseObjects(){
		
		double lat = 13.1618;	//latitudine da aggiornare con currentposition
		double lon = 46.3329;	//longitudine idem
		
		ParseGeoPoint userposition = new ParseGeoPoint(lat, lon);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("attivita");
		double maxDistance = 20;
		
		query.whereEqualTo("categoria_principale", "negozio");
		query.fromLocalDatastore();
		query.whereWithinKilometers("posizione", userposition, maxDistance);	//filtro in base alla posizione
		query.findInBackground(new FindCallback<ParseObject>() {
			
			public void done(List<ParseObject> listNegozi, ParseException e) {
		        if (e == null) {
		        	
		        	ParseObject.pinAllInBackground(listNegozi);
		        	listNegozi.iterator();
		        	for (ParseObject object:listNegozi) {
		        		inflateItemsList(object);
		        	};
		        	
		        } else {
		        	final String msg = "Errore nel download: " + e.getMessage();
		            testText.setText(msg);
		        }
		    }

			private void inflateItemsList(ParseObject object) {
				
				String categoria_principale = object.getString("categoria_principale");
				String categoria_secondaria = object.getString("categoria_secondaria");
				String nome_attivita = object.getString("nome_attivita");
				String descrizione = object.getString("descrizione");
				String citta = object.getString("citta");
				ParseGeoPoint position = object.getParseGeoPoint("posizione");
				
				
			}
		});
		return null;
	}

}
