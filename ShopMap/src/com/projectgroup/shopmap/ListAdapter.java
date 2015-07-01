package com.projectgroup.shopmap;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<String[]> {

	private final Activity context;

	public ListAdapter(Activity context, ArrayList<String[]> contenuto) {
		super(context, R.layout.cella, contenuto);
		this.context = context;
	}

	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.cella, null, true);

		TextView nome = (TextView) rowView.findViewById(R.id.nome);
		TextView cat = (TextView) rowView.findViewById(R.id.cat);
		TextView dist = (TextView) rowView.findViewById(R.id.dist);

		String[] attivita = this.getItem(position);
		nome.setText(attivita[0]);
		cat.setText(attivita[1]);
		dist.setText(attivita[2]);

		return rowView;
	}
}
