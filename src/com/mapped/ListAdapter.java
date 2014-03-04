package com.mapped;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class ListAdapter extends ArrayAdapter<String> {

	private final LayoutInflater inflater;
	private final Resources res;
	private final int itemLayout;
	private ArrayList<String> name = new ArrayList<String>();
	private ArrayList<String> latitude = new ArrayList<String>();
	private ArrayList<String> longitude = new ArrayList<String>();
	private Intent navigation;
	private Context c;
	public ListAdapter(Context context, int itemLayout,
			ArrayList<String> names, ArrayList<String> latitudes,
			ArrayList<String> longitudes) {
		super(context, itemLayout, R.id.rowContent, names);
		name = names;
		c = context;
		latitude = latitudes;
		longitude = longitudes;
		inflater = LayoutInflater.from(context);
		res = context.getResources();
		this.itemLayout = itemLayout;
		navigation = new Intent(context,ARActivity.class);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(itemLayout, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.text.setText(name.get(position));
		final int pos = position;
		holder.text.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				navigation.putExtra("latitude", latitude.get(pos));
				navigation.putExtra("longitude", longitude.get(pos));
				c.startActivity(navigation);
			}
		});

		return convertView;
	}

	static class ViewHolder {
		final TextView text;

		ViewHolder(View view) {
			text = (TextView) view.findViewById(R.id.rowContent);
		}
	}
}
