package aiss.model.ticketmaster;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "events","venues"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class Embedded {

	@JsonProperty("events")
    private List<TicketMasterEvent> ticketMasterEvent = null;
	
	@JsonProperty("venues")
    private List<TicketMasterVenues> venues = null;

	public List<TicketMasterEvent> getTicketMasterEvent() {
		return ticketMasterEvent;
	}

	public void setTicketMasterEvent(List<TicketMasterEvent> ticketMasterEvent) {
		this.ticketMasterEvent = ticketMasterEvent;
	}

	public List<TicketMasterVenues> getVenues() {
		return venues;
	}

	public void setVenues(List<TicketMasterVenues> venues) {
		this.venues = venues;
	}
	
}
