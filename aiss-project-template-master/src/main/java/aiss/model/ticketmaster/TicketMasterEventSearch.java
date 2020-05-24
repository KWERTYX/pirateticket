package aiss.model.ticketmaster;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "_embedded",
    "_links",
    "page"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class TicketMasterEventSearch {

	@JsonProperty("_embedded")
    private Embedded embedded = null;
	
    @JsonProperty("_links")
    private TicketMasterLink links;
    
    @JsonProperty("page")
    private TicketMasterPage page;
    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Embedded getEmbedded() {
		return embedded;
	}

	public void setEmbedded(Embedded embedded) {
		this.embedded = embedded;
	}

	public TicketMasterLink getLinks() {
		return links;
	}

	public void setLinks(TicketMasterLink links) {
		this.links = links;
	}

	public TicketMasterPage getPage() {
		return page;
	}

	public void setPage(TicketMasterPage page) {
		this.page = page;
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
}
