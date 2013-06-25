package se.springworks.whenismybus.activity;

import se.springworks.android.utils.activity.BaseActivity;
import se.springworks.android.utils.inject.GrapeGuice;
import se.springworks.android.utils.inject.annotation.InjectLogger;
import se.springworks.android.utils.inject.annotation.InjectView;
import se.springworks.android.utils.logging.Logger;
import se.springworks.whenismybus.R;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

	@InjectLogger
	private Logger logger;
	
	@InjectView(id = R.id.from)
	private EditText from;
	
	@InjectView(id = R.id.to)
	private EditText to;
	
	@InjectView(id = R.id.result)
	private TextView result;
	
	@Override
	protected void createActivity(Bundle savedInstanceState) {
		GrapeGuice.getInjector(this).injectMembers(this);
//		setContentView(R.layout.activity_main);
		setContentView(R.layout.fragment_map);
//		GrapeGuice.getInjector(this).injectViews(this);
	}

}
