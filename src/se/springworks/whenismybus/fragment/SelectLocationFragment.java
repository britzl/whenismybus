package se.springworks.whenismybus.fragment;

import java.util.ArrayList;

import se.springworks.android.utils.eventbus.IEventBus;
import se.springworks.android.utils.fragment.BaseDialogFragment;
import se.springworks.android.utils.inject.annotation.InjectLogger;
import se.springworks.android.utils.inject.annotation.InjectView;
import se.springworks.android.utils.logging.Logger;
import se.springworks.android.utils.map.geocoding.GeoCodeResult;
import se.springworks.android.utils.map.geocoding.GeoCodeResults;
import se.springworks.android.utils.map.geocoding.IGeoCodingApi;
import se.springworks.android.utils.map.geocoding.IGeoCodingApi.IGeoCodeCallback;
import se.springworks.whenismybus.R;
import se.springworks.whenismybus.adapter.GeoCodeResultsAdapter;
import se.springworks.whenismybus.event.LocationSelectedEvent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.google.inject.Inject;

public class SelectLocationFragment extends BaseDialogFragment {

	public static final String ARG_LOCATION = "ARG_LOCATION";
	
	@InjectLogger
	private Logger logger;

	@InjectView(id = R.id.location)
	private EditText location;

	@InjectView(id = R.id.location_results)
	private ListView results;

	@Inject
	private IGeoCodingApi geoCodingApi;
	
	private GeoCodeResultsAdapter resultsAdapter = new GeoCodeResultsAdapter(new ArrayList<GeoCodeResult>());
	
	
	@Inject
	private IEventBus bus;

	
	
	@Override
	protected void fragmentReadyToUse(Bundle savedInstanceState) {
		final String argLocation = getArguments().getString(ARG_LOCATION);
		if(argLocation != null) {
			location.setText(argLocation);
			location.setSelection(location.getText().length());
		}
		getDialog().setTitle(R.string.title_selectlocation);
		location.addTextChangedListener(new CustomTextWatcher(location, results));
		location.setOnKeyListener(new OnKeyListener() {			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_ENTER) {
					return true;
				}
				return false;
			}
		});

		results.setAdapter(resultsAdapter);
		results.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(results.getAdapter().isEmpty()) {
					return;
				}
				GeoCodeResult selectedResult = (GeoCodeResult)results.getAdapter().getItem(position);
				bus.post(new LocationSelectedEvent(selectedResult));
				dismissAllowingStateLoss();
			}
		});
	}




	@Override
	public View createView(LayoutInflater inflater, ViewGroup container) {
		return inflater.inflate(R.layout.fragment_selectlocation, container, false);
	}

	
	
	
	private class CustomTextWatcher implements TextWatcher, Runnable {

		private EditText text;
		private ListView list;
		private GeoCodeResultsAdapter adapter;
		
		private Handler handler = new Handler();
		
		public CustomTextWatcher(EditText text, ListView list) {
			this.text = text;
			this.list = list;
			adapter = new GeoCodeResultsAdapter(new ArrayList<GeoCodeResult>());
			list.setAdapter(adapter);
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			logger.debug("afterTextChanged()");
			// don't accept addresses shorter than 4 characters
			if(s.length() < 4) {
				list.setVisibility(View.GONE);
				return;
			}
			
			// don't start searching straight away
			// wait and see if the user writes some more
			handler.removeCallbacks(this);
			handler.postDelayed(this, 1750);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void run() {
			logger.debug("run()");
			geocode(text.getText().toString());
		}
		
		/**
		 * Performs a geocode lookup and updates the list adapter
		 * with the results
		 */
		private void geocode(String address) {
			logger.debug("geocode() %s", address);
			geoCodingApi.geocode(address, new IGeoCodeCallback() {
				
				@Override
				public void onSuccess(GeoCodeResults results) {
					logger.debug("onSuccess()");
					list.setVisibility(View.VISIBLE);
					adapter.updateListData(results.getResults());
				}
				
				@Override
				public void onError(Throwable t, String error) {
					logger.warn(error, t);
				}
			});			
		}
		
	}


}
