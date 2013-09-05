package se.springworks.whenismybus.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import se.springworks.android.utils.adapter.SimpleListAdapter;
import se.springworks.android.utils.map.directions.Leg;
import se.springworks.android.utils.map.directions.Route;
import se.springworks.whenismybus.R;
import android.view.View;
import android.widget.TextView;

public class RouteAdapter extends SimpleListAdapter<Route> {

	private SimpleDateFormat dateFormat;
	
	public RouteAdapter(List<Route> routes) {
		super(routes, R.layout.listitem_route);
		dateFormat = new SimpleDateFormat("kk:mm");
	}

	@Override
	protected void populateView(Route data, View view) {
		if(!data.hasLegs()) {
			return;
		}

		Leg firstLeg = data.getFirstLeg();
		Leg lastLeg = data.getLastLeg();
		
		TextView departureName = (TextView)view.findViewById(R.id.departure_name);
		TextView departureTime = (TextView)view.findViewById(R.id.departure_time);
		departureName.setText(firstLeg.getStartAddress());
		departureTime.setText(dateFormat.format(firstLeg.getDepartureTime().getDate()));

		TextView arrivalName = (TextView)view.findViewById(R.id.arrival_name);
		TextView arrivalTime = (TextView)view.findViewById(R.id.arrival_time);
		arrivalName.setText(lastLeg.getEndAddress());
		arrivalTime.setText(dateFormat.format(lastLeg.getArrivalTime().getDate()));

		final long timeInSeconds = data.getTotalTime() / 1000;
		TextView travelTime = (TextView)view.findViewById(R.id.travel_time);
		travelTime.setText(timeInSeconds / (60 * 60) + ":" + (timeInSeconds / 60));
		
	}

}
