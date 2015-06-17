package com.projectgroup.shopmap;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GalleryActivity extends Activity{

	final String url = "http://i.imgur.com/DvpvklR.png";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		
		ImageView img1 = (ImageView)findViewById(R.id.img1);
		ImageView img2 = (ImageView)findViewById(R.id.img2);
		ImageView img3 = (ImageView)findViewById(R.id.img3);
		ImageView img4 = (ImageView)findViewById(R.id.img4);
		
		Picasso.with(getBaseContext()).load(url).into(img1);
		Picasso.with(getBaseContext()).load(url).into(img2);
		Picasso.with(getBaseContext()).load(url).into(img3);
		Picasso.with(getBaseContext()).load(url).into(img4);
		
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
		
		
	}

}
