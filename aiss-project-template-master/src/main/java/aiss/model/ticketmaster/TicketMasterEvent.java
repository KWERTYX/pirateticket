package aiss.model.ticketmaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "type", "id", "url", "locale", "images", "embedded", "dates", "description", "aditionalInfo", "info" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketMasterEvent {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("locale")
	private String locale;
	
	@JsonProperty("images")
	private List<TicketMasterImage> images;
	
	@JsonProperty("_embedded")
	private Embedded _embedded;
	
	@JsonProperty("dates")
	private EventDates dates;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("aditionalInfo")
	private String aditionalInfo;
	
	@JsonProperty("info")
	private String info;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAditionalInfo() {
		return aditionalInfo;
	}

	public void setAditionalInfo(String aditionalInfo) {
		this.name = aditionalInfo;
	}
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public List<TicketMasterImage> getImages() {
		return images;
	}

	public void setImages(List<TicketMasterImage> images) {
		this.images = images;
	}

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public Embedded get_embedded() {
		return _embedded;
	}

	public void set_embedded(Embedded _embedded) {
		this._embedded = _embedded;
	}

	public EventDates getDates() {
		return dates;
	}

	public void setDates(EventDates dates) {
		this.dates = dates;
	}
	
	
}
