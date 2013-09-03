package se.springworks.whenismybus.fragment;

import se.springworks.android.utils.eventbus.IEventBus;
import se.springworks.android.utils.fragment.BaseFragment;
import se.springworks.android.utils.inject.annotation.InjectLogger;
import se.springworks.android.utils.inject.annotation.InjectView;
import se.springworks.android.utils.logging.Logger;
import se.springworks.android.utils.map.geocoding.GeoCodeResult;
import se.springworks.android.utils.map.geocoding.GeoCodeResults;
import se.springworks.android.utils.map.geocoding.IGeoCodingApi;
import se.springworks.android.utils.map.geocoding.IGeoCodingApi.IGeoCodeCallback;
import se.springworks.android.utils.threading.ThreadingUtils;
import se.springworks.whenismybus.R;
import se.springworks.whenismybus.adapter.GeoCodeResultsAdapter;
import se.springworks.whenismybus.event.SearchEvent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.inject.Inject;

public class SearchBoxFragment extends BaseFragment {
	
	@InjectLogger
	private Logger logger;
	
	@InjectView(id = R.id.searchbutton)
	private Button searchButton;
	
	@InjectView(id = R.id.from)
	private EditText from;
	
	@InjectView(id = R.id.from_results)
	private ListView fromResults;
	
	@InjectView(id = R.id.to)
	private EditText to;

	@InjectView(id = R.id.to_results)
	private ListView toResults;
	
	private GeoCodeResultsAdapter fromAdapter;
	
	private GeoCodeResultsAdapter toAdapter;
	
	@Inject
	private IEventBus bus;

	@Inject
	private IGeoCodingApi geoCodingApi;
	
	
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_search, container, false);
	}

	@Override
	protected void fragmentReadyToUse(Bundle savedInstanceState) {
		toResults.setAdapter(toAdapter);
		fromResults.setAdapter(fromAdapter);

		from.setText("Lövsångarvägen");
		from.addTextChangedListener(new CustomTextWatcher(from, fromResults));
		to.setText("Karlbergs station");
		to.addTextChangedListener(new CustomTextWatcher(to, toResults));

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
		super.startFragment();
		bus.register(this);
	}

	@Override
	protected void stopFragment() {
		super.stopFragment();
		bus.unregister(this);
	}

	

	private class CustomTextWatcher implements TextWatcher, OnItemClickListener, Runnable {

		private EditText text;
		private ListView list;
		private GeoCodeResultsAdapter adapter;
		
		private GeoCodeResult selectedResult;
		
		private Handler handler = new Handler();
		
		public CustomTextWatcher(EditText text, ListView list) {
			this.text = text;
			this.list = list;
			adapter = new GeoCodeResultsAdapter(new GeoCodeResults());
			list.setAdapter(adapter);
			list.setOnItemClickListener(this);
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// don't accept addresses shorter than 4 characters
			if(s.length() < 4) {
				list.setVisibility(View.GONE);
				return;
			}

			// same text as selected result?
			if(selectedResult != null && selectedResult.getFormattedAddress().equals(s.toString())) {
				list.setVisibility(View.GONE);
				return;
			}
			
			handler.removeCallbacks(this);
			handler.postDelayed(this, 2500);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if(adapter.isEmpty()) {
				selectedResult = null;
				return;
			}
			selectedResult = (GeoCodeResult)adapter.getItem(position);
			text.setText(selectedResult.getFormattedAddress());
			list.setVisibility(View.GONE);
		}

		@Override
		public void run() {
			geoCodingApi.geocode(text.getText().toString(), new IGeoCodeCallback() {
				
				@Override
				public void onSuccess(GeoCodeResults results) {
					list.setVisibility(View.VISIBLE);
					adapter.setResults(results);
				}
				
				@Override
				public void onError(Throwable t, String error) {
					logger.warn(error, t);
				}
			});
		}
		
	}

}
