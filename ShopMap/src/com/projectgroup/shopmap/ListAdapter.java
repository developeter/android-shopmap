package com.projectgroup.shopmap;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

public class ListAdapter extends ArrayAdapter<ParseObject>{

	private final Context context;
	private final ArrayList<ParseObject> list;
	
	public ListAdapter(Context context, ArrayList<ParseObject> list){
		super(context, R.layout.cella, list);
		this.context=context;
		this.list=list;
	}
	
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		View rowView=inflater.inflate(R.layout.cella, null, true);
		
		TextView nome = (TextView)rowView.findViewById(R.id.nome);
		TextView cat = (TextView)rowView.findViewById(R.id.cat);
		TextView dist = (TextView)rowView.findViewById(R.id.dist);
	
		String[] data = new String[3];
		data = extractFromList(position);
		
		nome.setText(data[1]);
		cat.setText(data[2]);
		dist.setText(data[3]);
		
		return rowView;
	}
	
	private String[] extractFromList(int position){
		
		ParseObject object = list.get(position);
		String[] data = new String[3];
		data[1] = object.getString("nome_attivita");
		data[2] = object.getString("categoria_secondaria");
		data[3] = object.getString("distanza");
		
		return data;
	}
}
