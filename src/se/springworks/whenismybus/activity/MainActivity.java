package se.springworks.whenismybus.activity;

import se.springworks.android.utils.activity.BaseActivity;
import se.springworks.android.utils.inject.GrapeGuice;
import se.springworks.android.utils.inject.annotation.InjectLogger;
import se.springworks.android.utils.inject.annotation.InjectView;
import se.springworks.android.utils.logging.Logger;
import se.springworks.whenismybus.R;
import se.springworks.whenismybus.directions.Directions;
import se.springworks.whenismybus.directions.IDirectionsApi;
import se.springworks.whenismybus.directions.IDirectionsApi.OnDirectionsCallback;
import se.springworks.whenismybus.directions.TravelMode;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.inject.Inject;

public class MainActivity extends BaseActivity {

	@InjectLogger
	private Logger logger;
	
	@InjectView(id = R.id.from)
	private EditText from;
	
	@InjectView(id = R.id.to)
	private EditText to;
	
	@InjectView(id = R.id.result)
	private TextView result;
	
	@Inject
	private IDirectionsApi directions;
	
	@Override
	protected void createActivity(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		GrapeGuice.getInjector(this).injectMembers(this).injectViews(this);
		
		from.setText("Mickelsbergsvägen 354");
		to.setText("Sergels Torg");
		directions.directions(from.getText().toString(), to.getText().toString(), TravelMode.TRANSIT, (int)(System.currentTimeMillis() / 1000), new OnDirectionsCallback() {
			
			@Override
			public void onError(Throwable t, String error) {
				logger.error(error, t);
			}
			
			@Override
			public void onDirections(Directions directions) {
				logger.debug("Directions received %s", directions.getStatus());
			}
		});
//		setContentView(R.layout.fragment_map);
//		GrapeGuice.getInjector(this).injectViews(this);
	}

}
