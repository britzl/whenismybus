package se.springworks.whenismybus.fragment;

import se.springworks.android.utils.eventbus.IEventBus;
import se.springworks.android.utils.fragment.BaseFragment;
import se.springworks.android.utils.inject.annotation.InjectLogger;
import se.springworks.android.utils.inject.annotation.InjectView;
import se.springworks.android.utils.logging.Logger;
import se.springworks.whenismybus.R;
import se.springworks.whenismybus.event.SearchEvent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.inject.Inject;

public class SearchBoxFragment extends BaseFragment {
	
	@InjectLogger
	private Logger logger;
	
	@InjectView(id = R.id.searchbutton)
	private Button searchButton;
	
	@InjectView(id = R.id.from)
	private EditText from;
	
	@InjectView(id = R.id.to)
	private EditText to;
	
	
	@Inject
	private IEventBus bus;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_search, container, false);
	}

	@Override
	protected void fragmentReadyToUse(Bundle savedInstanceState) {
		from.setText("Lövsångarvägen");
		to.setText("Karlsbergs station");
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				logger.debug("onClick()");
				bus.post(new SearchEvent(from.getText().toString(), to.getText().toString()));
			}
		});
	}

	@Override
	protected void startFragment() {
		bus.register(this);
	}

	@Override
	protected void stopFragment() {
		bus.unregister(this);
	}

	@Override
	protected void resumeFragment() {
	}

	@Override
	protected void pauseFragment() {
		// TODO Auto-generated method stub
		
	}

}
