package com.projectgroup.shopmap;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ActivityLista extends Activity {

	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista);

		// ListAdapter adapter = new ListAdapter (this, null);
		lv = (ListView) findViewById(R.id.list_selection);

		final double latitude = 45.9513;
		final double longitude = 12.6802;

		Location user = new Location("");
		user.setLatitude(latitude);
		user.setLongitude(longitude);

		final ArrayList<String[]> contenuto = new ArrayList<String[]>();

		final double lat = user.getLatitude();
		final double lon = user.getLongitude();
		final ParseGeoPoint userposition = new ParseGeoPoint(lat, lon);
		final double maxDistance = 20;
		final ListAdapter adapter = new ListAdapter(this, contenuto);

		lv.setAdapter(adapter);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("attivita");

		query.whereEqualTo("categoria_principale", "negozio");
		query.whereWithinKilometers("posizione", userposition, maxDistance);

		query.findInBackground(new FindCallback<ParseObject>() {

			public void done(List<ParseObject> listNegozi, ParseException e) {
				if (e == null) {

					ParseObject.pinAllInBackground(listNegozi);
					listNegozi.iterator();
					for (ParseObject object : listNegozi) {
						final String[] attivita = new String[3];
						attivita[0] = object.getString("nome_attivita");
						attivita[1] = object.getString("categoria_secondaria");
						ParseGeoPoint pos = object
								.getParseGeoPoint("posizione");
						float dist = (float) pos
								.distanceInKilometersTo(userposition);
						String str = String.format("%.0f",
								(dist * 1000) - 0.005);
						attivita[2] = str + "m";
						contenuto.add(attivita);
					}
					;
					adapter.notifyDataSetChanged();

				} else {
					Log.d("errore", "Errore nel download: " + e.getMessage());
				}
			}
		});

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				String data = ((TextView) view.findViewById(R.id.nome))
						.getText().toString();
				bundle.putString("NOME", data);
				Intent aggiungi = new Intent(ActivityLista.this,
						ActivityDettaglio.class);
				aggiungi.putExtras(bundle);
				startActivity(aggiungi);

			}
		});

	}

}
