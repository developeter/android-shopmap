package com.projectgroup.shopmap;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	
	private int listsize = 0;
	private TextView testText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground();*/
		testText = (TextView)findViewById(R.id.textTest);
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("attivita");
		query.whereEqualTo("categoria_principale", "negozio");
		query.findInBackground(new FindCallback<ParseObject>() {
		 
			public void done(List<ParseObject> listNegozi, ParseException e) {
		        if (e == null) {
		        	listsize = listNegozi.size();
		        	setText(listsize);
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		        }
		    }

		});
		
		
		}
	
	public void setText(int value){
			
		testText.setText(""+value);
	}

}
