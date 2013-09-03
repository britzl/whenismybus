package se.springworks.whenismybus.adapter;

import se.springworks.android.utils.map.geocoding.GeoCodeResult;
import se.springworks.android.utils.map.geocoding.GeoCodeResults;
import se.springworks.whenismybus.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GeoCodeResultsAdapter extends BaseAdapter {

	private GeoCodeResults results;
	
	public GeoCodeResultsAdapter(GeoCodeResults results) {
		super();
		this.results = results;	
	}
	
	@Override
	public synchronized int getCount() {
		return results.size();
	}

	@Override
	public synchronized Object getItem(int position) {
		return results.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public synchronized int getItemViewType(int position) {
		return 0;
	}

	@Override
	public synchronized View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_address, null);
		}
		
		final GeoCodeResult result = results.get(position);
		TextView address = (TextView)convertView.findViewById(R.id.address);
		address.setText(result.getFormattedAddress());
		return convertView;
	}

	@Override
	public synchronized int getViewTypeCount() {
		return 1;
	}

	public synchronized void setResults(GeoCodeResults results) {
		this.results = results;
		notifyDataSetChanged();
	}

}
