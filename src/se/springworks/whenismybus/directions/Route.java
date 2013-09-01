package se.springworks.whenismybus.directions;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Route {

	
	public class Bounds {
		@JsonProperty("northeast")
		private LatLong northEastE6;

		@JsonProperty("southwest")
		private LatLong southWestE6;
	}
	
	@JsonProperty("bounds")
	private Bounds bounds;
	
	@JsonProperty("copyrights")
	private String copyrights;
	
	@JsonProperty("legs")
	private ArrayList<Leg> legs;
	
	@JsonProperty("overview_polyline")
	private PolyLine overviewPolyline;
	
	@JsonProperty("summary")
	private String summary;
	
	@JsonProperty("warnings")
	private ArrayList<String> warnings;
	
	/**
	 * Get the viewport bounding box of this route
	 * @return
	 */
	public Bounds getBounds() {
		return bounds;
	}
	
	/**
	 * Get the copyrights text to be displayed for this route. You must handle and
	 * display this information yourself
	 * @return
	 */
	public String getCopyrights() {
		return copyrights;
	}
	
	/**
	 * Get an array which contains information about a leg of the route, between two
	 * locations within the given route. A separate leg will be present for each waypoint
	 * or destination specified. (A route with no waypoints will contain exactly one leg
	 * within the legs array.) Each leg consists of a series of steps
	 * @return
	 */
	public Iterable<Leg> getLegs() {
		return legs;
	}
	
	/**
	 * Get an object holding an array of encoded points that represent an
	 * approximate (smoothed) path of the resulting directions
	 * @return
	 */
	public PolyLine getOverviewPolyline() {
		return overviewPolyline;
	}
	
	/**
	 * Get a short textual description for the route, suitable for naming and disambiguating
	 * the route from alternatives
	 * @return
	 */
	public String getSummary() {
		return summary;
	}
	
	/**
	 * Get an array of warnings to be displayed when showing these directions. You must
	 * handle and display these warnings yourself.
	 * @return
	 */
	public Iterable<String> getWarnings() {
		return warnings;
	}
	
	
}
