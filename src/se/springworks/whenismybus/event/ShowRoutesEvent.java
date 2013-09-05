package se.springworks.whenismybus.event;

import java.util.List;

import se.springworks.android.utils.map.directions.Route;

public class ShowRoutesEvent {

	public List<Route> routes;
	
	public ShowRoutesEvent(List<Route> routes) {
		this.routes = routes;
	}
	
}
