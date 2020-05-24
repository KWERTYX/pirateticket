package aiss.model.spotify;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "track"
})
public class TrackHolder {
    @JsonProperty("track")
    private Track track;

    @JsonProperty("track")
    public String getId() {
        return track.getId();
    }
    
    public String getURI() {
    	return "spotify:track:"+track.getId();
    }

}
