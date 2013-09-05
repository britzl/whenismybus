package se.springworks.whenismybus.activity;

import com.google.inject.Inject;
import com.squareup.otto.Subscribe;

import android.os.Bundle;
import se.springworks.android.utils.activity.BaseActivity;
import se.springworks.android.utils.eventbus.IEventBus;
import se.springworks.android.utils.inject.GrapeGuice;
import se.springworks.android.utils.inject.annotation.InjectLogger;
import se.springworks.android.utils.logging.Logger;
import se.springworks.whenismybus.R;
import se.springworks.whenismybus.event.LocationSelectedEvent;

public class SelectLocationActivity extends BaseActivity {

	@InjectLogger
	private Logger logger;
	
	@Inject
	private IEventBus bus;

	@Override
	protected void createActivity(Bundle savedInstanceState) {
		setContentView(R.layout.activity_selectlocation);
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
	public void onLocationSelected(LocationSelectedEvent e) {
	}
}
