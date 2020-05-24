package aiss.model.ticketmaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "start"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class EventDates {
	
	@JsonProperty("start")
	private Start start;
	
	public Start getStart() {
		return start;
	}

	public void setStart(Start start) {
		this.start = start;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
	    "localDate", "localTime"
	})
	@JsonIgnoreProperties(ignoreUnknown=true)
	public class Start {
		
		@JsonProperty("localDate")
		private String localDate;
		
		@JsonProperty("localTime")
		private String localTime;

		public String getLocalDate() {
			return localDate;
		}

		public void setLocalDate(String localDate) {
			this.localDate = localDate;
		}

		public String getLocalTime() {
			return localTime;
		}

		public void setLocalTime(String localTime) {
			this.localTime = localTime;
		}
		
	}

}
