package aiss.model.spotify;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "playlists"
    //"artist",
    //"albums",
    //"tracks",
})
public class Search {

    @JsonProperty("playlists")
    private Playlists playlists;
    //@JsonProperty("artist")
    //private List<Artist> artists;
    //@JsonProperty("albums")
    //private List<Album> albums;
    //@JsonProperty("tracks")
    //private Tracks tracks;
    

    @JsonProperty("playlists")
    public Playlists getPlaylists() {
        return this.playlists;
    }

    @JsonProperty("playlists")
    public void setPlaylists(Playlists playlists) {
        this.playlists = playlists;
    }

}
