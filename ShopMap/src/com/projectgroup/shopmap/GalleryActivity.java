package com.projectgroup.shopmap;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class GalleryActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		Bundle bundle = getIntent().getExtras();
		
		if(bundle != null){
			String nome_string = (String) bundle.get("NOME");
			getPhotosById(nome_string);
		}
		
		/*
		img1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("url", url);
				Intent img = new Intent(GalleryActivity.this, ProvaImmagine.class);
				img.putExtras(bundle);
				startActivity(img);
			}
		});
		
		img2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("url", url);
				Intent img = new Intent(GalleryActivity.this, ProvaImmagine.class);
				img.putExtras(bundle);
				startActivity(img);
			}
		});
		*/
		
	}
	
	private void getPhotosById(String nome) {
		
        ParseQuery<ParseObject> query = ParseQuery.getQuery("attivita");
        query.fromLocalDatastore();
        query.whereEqualTo("nome_attivita", nome);
        query.include("immagini");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
          public void done(ParseObject object, ParseException e) {
            if (e == null) {
               
                try {
                                object.pin();
                        } catch (ParseException e1) {
                                Log.d("errore", "Impossibile salvare sul database: " + e1.getMessage());
                        }
                
                String url1 = object.getString("image1");
                String url2 = object.getString("image2");
                String url3 = object.getString("image3");
                String url4 = object.getString("image4");

        		ImageView img1 = (ImageView)findViewById(R.id.img1);
        		ImageView img2 = (ImageView)findViewById(R.id.img2);
        		ImageView img3 = (ImageView)findViewById(R.id.img3);
        		ImageView img4 = (ImageView)findViewById(R.id.img4);
                
                Picasso.with(getBaseContext()).load(url1).into(img1);
        		Picasso.with(getBaseContext()).load(url2).into(img2);
        		Picasso.with(getBaseContext()).load(url3).into(img3);
        		Picasso.with(getBaseContext()).load(url4).into(img4);
               
            } else {
                Log.d("errore", "Errore nel download: " + e.getMessage());
            }
          }
        });
}

}
