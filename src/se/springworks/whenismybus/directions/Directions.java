package se.springworks.whenismybus.directions;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Directions {

	
	@JsonProperty("routes")
	private ArrayList<Route> routes;
	
	
	@JsonProperty("status")
	private String status;
	
	
	public Iterable<Route> getRoutes() {
		return routes;
	}
	
	public String getStatus() {
		return status;
	}
}
