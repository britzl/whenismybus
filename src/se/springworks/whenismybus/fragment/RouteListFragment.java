package se.springworks.whenismybus.fragment;

import java.util.ArrayList;

import se.springworks.android.utils.eventbus.IEventBus;
import se.springworks.android.utils.fragment.BaseFragment;
import se.springworks.android.utils.inject.annotation.InjectLogger;
import se.springworks.android.utils.inject.annotation.InjectView;
import se.springworks.android.utils.logging.Logger;
import se.springworks.android.utils.map.directions.Route;
import se.springworks.android.utils.map.geocoding.IGeoCodingApi;
import se.springworks.whenismybus.R;
import se.springworks.whenismybus.adapter.RouteAdapter;
import se.springworks.whenismybus.event.ShowRoutesEvent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.inject.Inject;
import com.squareup.otto.Subscribe;

public class RouteListFragment extends BaseFragment {

	@InjectLogger
	private Logger logger;
	
	@Inject
	private IEventBus bus;
	
	@InjectView(id = R.id.routeslist)
	private ListView routesList;

	@Inject
	private IGeoCodingApi geoCodingApi;
	
	private RouteAdapter adapter;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_routelist, container, false);
	}

	@Override
	protected void fragmentReadyToUse(Bundle savedInstanceState) {
		adapter = new RouteAdapter(new ArrayList<Route>());
		routesList.setAdapter(adapter);
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
	
	@Subscribe
	public void onShowRoutes(ShowRoutesEvent e) {
		adapter.updateListData(e.routes);
		
	}
	

}
