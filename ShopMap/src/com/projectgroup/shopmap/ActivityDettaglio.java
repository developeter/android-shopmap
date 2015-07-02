package com.projectgroup.shopmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ActivityDettaglio extends Activity {

	TextView cat_princ;
	TextView cat_sec;
	TextView nome_etichetta;
	TextView descr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dettaglio);
		Bundle bundle = getIntent().getExtras();

		cat_princ = (TextView) findViewById(R.id.cat_princ);
		cat_sec = (TextView) findViewById(R.id.cat_sec);
		nome_etichetta = (TextView) findViewById(R.id.nome);
		descr = (TextView) findViewById(R.id.descrizione);

		final String nome_string = (String) bundle.get("NOME");

		if (nome_string != null) {
			getDetailById(nome_string);
		} else {
			Toast.makeText(this, "Bundel vuoto", Toast.LENGTH_SHORT).show();
		}

		Button ButtonGallery = (Button) findViewById(R.id.btn_gallery);
		ButtonGallery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Bundle newbundle = new Bundle();
				newbundle.putString("NOME", nome_string);
				Intent intent = new Intent(ActivityDettaglio.this, GalleryActivity.class);
				intent.putExtras(newbundle);
				startActivity(intent);
			}
		});
	}

	private void getDetailById(final String nome) {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("attivita");
		query.whereEqualTo("nome_attivita", nome);
		query.fromLocalDatastore();
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {

					try {
						object.pin();
					} catch (ParseException e1) {
						Log.d("errore", "Impossibile salvare sul database: "
								+ e1.getMessage());
					}

					cat_princ.setText(object.getString("categoria_principale"));
					cat_sec.setText(object.getString("categoria_secondaria"));
					nome_etichetta.setText(nome);
					descr.setText(object.getString("descrizione"));

				} else {
					Log.d("errore", "Errore nel download: " + e.getMessage());
				}
			}
		});
	}
}
