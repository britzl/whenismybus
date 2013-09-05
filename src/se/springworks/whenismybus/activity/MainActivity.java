package se.springworks.whenismybus.activity;

import se.springworks.android.utils.activity.BaseActivity;
import se.springworks.android.utils.eventbus.IEventBus;
import se.springworks.android.utils.inject.GrapeGuice;
import se.springworks.android.utils.inject.annotation.InjectLogger;
import se.springworks.android.utils.inject.annotation.InjectView;
import se.springworks.android.utils.logging.Logger;
import se.springworks.android.utils.map.directions.Directions;
import se.springworks.android.utils.map.directions.IDirectionsApi;
import se.springworks.android.utils.map.directions.IDirectionsApi.OnDirectionsCallback;
import se.springworks.android.utils.map.directions.Leg;
import se.springworks.android.utils.map.directions.Route;
import se.springworks.android.utils.map.directions.TravelMode;
import se.springworks.whenismybus.R;
import se.springworks.whenismybus.event.SearchEvent;
import se.springworks.whenismybus.event.ShowRoutesEvent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.inject.Inject;
import com.squareup.otto.Subscribe;

public class MainActivity extends BaseActivity {

	@InjectLogger
	private Logger logger;
	
	@Inject
	private IDirectionsApi directionsApi;
	
	@Inject
	private IEventBus bus;
	
	@InjectView(id = R.id.result)
	private TextView result;
	
	@Override
	protected void createActivity(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		GrapeGuice.getInjector(this).injectMembers(this).injectViews(this);
	}

	@Override
	protected void stopActivity() {
		super.stopActivity();
		bus.unregister(this);
	}

	@Override
	protected void startActivity() {
		super.startActivity();
		bus.register(this);
	}
	
	@Subscribe
	public void onSearch(SearchEvent e) {
		logger.debug("onSearch() from %s to %s", e.from, e.to);
		directionsApi.directions(e.from, e.to, TravelMode.TRANSIT, (int)(System.currentTimeMillis() / 1000), new OnDirectionsCallback() {
			
			@Override
			public void onError(Throwable t, String error) {
				logger.error(error, t);
				result.setText(error);
			}
			
			@Override
			public void onDirections(Directions directions) {
				logger.debug("Directions received %s", directions.getStatus());
				if(!directions.hasRoutes()) {
					result.setText("No route!");
					return;
				}
				
				Route route = directions.firstRoute();
				if(!route.hasLegs()) {
					result.setText("No route!");
					return;
				}
				Leg leg = route.getFirstLeg();
				result.setText(leg.getDepartureTime().getText());
				
				bus.post(new ShowRoutesEvent(directions.getRoutes()));
			}
		});
	}

}
