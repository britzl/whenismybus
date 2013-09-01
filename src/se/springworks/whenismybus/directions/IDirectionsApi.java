package se.springworks.whenismybus.directions;


public interface IDirectionsApi {

//	http://maps.googleapis.com/maps/api/directions/json?origin=Mickelsbergsv�gen+354&destination=Sergels+torg&sensor=true&mode=transit&departure_time=1377897244

	public interface OnDirectionsCallback {
		public void onDirections(Directions directions);
		
		public void onError(Throwable t, String error);
	}
	
	void directions(String from, String to, TravelMode mode, int departureTimeSeconds, OnDirectionsCallback callback);
}
