package se.springworks.whenismybus.adapter;

import java.util.List;

import se.springworks.android.utils.adapter.SimpleListAdapter;
import se.springworks.android.utils.map.geocoding.GeoCodeResult;
import se.springworks.whenismybus.R;
import android.view.View;
import android.widget.TextView;

public class GeoCodeResultsAdapter extends SimpleListAdapter<GeoCodeResult> {

	public GeoCodeResultsAdapter(List<GeoCodeResult> results) {
		super(results, R.layout.item_address);
	}

	@Override
	protected void populateView(GeoCodeResult data, View view) {
		TextView address = (TextView)view.findViewById(R.id.address);
		address.setText(data.getFormattedAddress());
	}

}
