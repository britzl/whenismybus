package se.springworks.whenismybus.fragment;

import se.springworks.android.utils.eventbus.IEventBus;
import se.springworks.android.utils.fragment.BaseFragment;
import se.springworks.android.utils.fragment.FragmentUtil;
import se.springworks.android.utils.inject.annotation.InjectLogger;
import se.springworks.android.utils.inject.annotation.InjectView;
import se.springworks.android.utils.logging.Logger;
import se.springworks.android.utils.persistence.IKeyValueStorage;
import se.springworks.whenismybus.R;
import se.springworks.whenismybus.event.LocationSelectedEvent;
import se.springworks.whenismybus.event.SearchEvent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.squareup.otto.Subscribe;

public class SearchBoxFragment extends BaseFragment {
	
	@InjectLogger
	private Logger logger;
	
	@InjectView(id = R.id.searchbutton)
	private Button searchButton;
	
	@InjectView(id = R.id.from)
	private TextView from;
	
	@InjectView(id = R.id.to)
	private TextView to;
	
	@InjectView(id = R.id.swapfromto)
	private ImageView swapfromto;
	
	private TextView selected;
	
	@Inject
	private IEventBus bus;
	
	@Inject
	private IKeyValueStorage storage;
	
	
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_search, container, false);
	}

	@Override
	protected void fragmentReadyToUse(Bundle savedInstanceState) {
		final Bundle args = new Bundle();
		from.setText("Lövsångarvägen");
		from.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selected = from;
				args.putString(SelectLocationFragment.ARG_LOCATION, from.getText().toString());
				FragmentUtil.showSingle(getActivity(), new SelectLocationFragment(), args);
			}
		});
		
		to.setText("Karlbergs station");
		to.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selected = to;
				args.putString(SelectLocationFragment.ARG_LOCATION, to.getText().toString());
				FragmentUtil.showSingle(getActivity(), new SelectLocationFragment(), args);
			}
		});

		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				logger.debug("onClick() search");
				bus.post(new SearchEvent(from.getText().toString(), to.getText().toString()));
			}
		});
		
		swapfromto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CharSequence f = from.getText();
				CharSequence t = to.getText();
				from.setText(t);
				to.setText(f);
				bus.post(new SearchEvent(from.getText().toString(), to.getText().toString()));
			}
		});
	}
	
	@Subscribe
	public void onLocationSelected(LocationSelectedEvent e) {
		if(selected != null) {
			selected.setText(e.location.getFormattedAddress());
		}
	}

	@Override
	protected void startFragment() {
		super.startFragment();
		bus.register(this);
	}

	@Override
	protected void stopFragment() {
		super.stopFragment();
		bus.unregister(this);
	}

	

}
