package com.projectgroup.shopmap;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GalleryActivity extends Activity{

	ParseFile obj1; String url1;
	ParseFile obj2; String url2;
	ParseFile obj3; String url3;
	ParseFile obj4; String url4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		Bundle bundle = getIntent().getExtras();

		if(bundle != null){
			String nome_string = (String) bundle.get("NOME");
			getPhotosById(nome_string);
		}

	}

	private void getPhotosById(String nome) {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("attivita");
		query.fromLocalDatastore();
		query.whereEqualTo("nome_attivita", nome);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {

					try {
						object.pin();
					} catch (ParseException e1) {
						Log.d("errore", "Impossibile salvare sul database: " + e1.getMessage());
					}

					obj1 = object.getParseFile("image1");
					obj2 = object.getParseFile("image2");
					obj3 = object.getParseFile("image3");
					obj4 = object.getParseFile("image4");

					ImageView img1 = (ImageView)findViewById(R.id.img1);
					ImageView img2 = (ImageView)findViewById(R.id.img2);
					ImageView img3 = (ImageView)findViewById(R.id.img3);
					ImageView img4 = (ImageView)findViewById(R.id.img4);

					if(obj1!=null){ url1 = obj1.getUrl(); }
					else {img1.setVisibility(View.GONE);}
					Picasso.with(getBaseContext()).load(url1).into(img1);

					if(obj2!=null){ url2 = obj2.getUrl(); }
					else {img2.setVisibility(View.GONE);}
					Picasso.with(getBaseContext()).load(url2).into(img2);

					if(obj3!=null){ url3 = obj3.getUrl(); }
					else {img3.setVisibility(View.GONE);}
					Picasso.with(getBaseContext()).load(url3).into(img3);

					if(obj4!=null){ url4 = obj4.getUrl(); }
					else {img4.setVisibility(View.GONE);}
					Picasso.with(getBaseContext()).load(url4).into(img4);
					
					if(url1==null && url2==null && url3==null && url4==null){
						setError();
					}

				} else {
					Log.d("errore", "Errore nel download: " + e.getMessage());
				}
			}
		});
	}

	protected void setError() {
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		final TextView error = new TextView(this);
		error.setText("Nessuna immagine presente.");
		error.setGravity(Gravity.CENTER);
		error.setTextAppearance(this, android.R.style.TextAppearance_Large);
		ll.addView(error);
		
	}

}
