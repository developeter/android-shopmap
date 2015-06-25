package com.projectgroup.shopmap;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ProvaImmagine extends Activity {
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.testimage);  
            
            Bundle bundle = getIntent().getExtras();
            String url = bundle.getString("url"); 
            ImageView imgView =(ImageView)findViewById(R.id.img);
            Picasso.with(getBaseContext()).load(url).into(imgView);
            
    }
   }