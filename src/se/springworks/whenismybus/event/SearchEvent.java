package se.springworks.whenismybus.event;

public class SearchEvent {

	public String from;
	public String to;
	
	public SearchEvent(String from, String to) {
		this.from = from;
		this.to = to;
	}
}
