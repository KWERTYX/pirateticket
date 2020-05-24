package aiss.model.ticketmaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "first",
    "prev",
    "self",
    "next",
    "last"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class TicketMasterLink {

	@JsonProperty("first")
    private Href first;
	
    @JsonProperty("prev")
    private Href prev;
    
    @JsonProperty("self")
    private Href self;
    
    @JsonProperty("next")
    private Href next;
    
    @JsonProperty("last")
    private Href last;    
    
    public Href getFirst() {
		return first;
	}
    
	public void setFirst(Href first) {
		this.first = first;
	}

	public Href getPrev() {
		return prev;
	}
	
	public void setPrev(Href prev) {
		this.prev = prev;
	}

	public Href getSelf() {
		return self;
	}

	public void setSelf(Href self) {
		this.self = self;
	}

	public Href getNext() {
		return next;
	}


	public void setNext(Href next) {
		this.next = next;
	}


	public Href getLast() {
		return last;
	}

	public void setLast(Href last) {
		this.last = last;
	}

}
