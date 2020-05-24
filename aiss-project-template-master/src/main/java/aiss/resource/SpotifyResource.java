package aiss.resource;

import aiss.model.spotify.NewPlaylist;
import aiss.model.spotify.Playlist;
import aiss.model.spotify.Playlists;
import aiss.model.spotify.Search;
import aiss.model.spotify.UserProfile;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SpotifyResource {

    private static final Logger log = Logger.getLogger(SpotifyResource.class.getName());

    private final String access_token;
    private final String baseURL = "https://api.spotify.com/v1";

    public SpotifyResource(String access_token) {
        this.access_token = access_token;
    }
    
    public void add_PlayLists(List<String> ids) throws UnsupportedEncodingException {
    	String userId = this.getUserId();
    	//vamos a recorrer individualmente cada playlist, sacarle las canciones, crear una nueva playlist, y añadirle las canciones
    	for(int i=0;i<ids.size();i++) {
    		Playlist playlist = this.getPlayList(ids.get(i));
    		
    		String tracksURI = "";
    		for(int j=0; j<playlist.getTracks().getTotal()||j==99; j++) {
    			tracksURI += (playlist.getTracks().getItems().get(j).getURI()+(j+1==playlist.getTracks().getTotal() ? "" : ","));
    		}
    		
    		List<String> emojies = Arrays.asList("😀,😁,😂,🤣,😃,😄,😅,😆,😎,😍".split(","));
    		
    		Playlist newPlaylist = this.createPlaylist(playlist.getName() + " [PirateTicket "+(emojies.get(getRandomNumberInRange(0, emojies.size()-1)))+"]", "Importada gracias a PirateTicket");
    		
    		String apiURL = baseURL+"/playlists/"+newPlaylist.getId()+"/tracks?uris="+URLEncoder.encode(tracksURI, StandardCharsets.UTF_8.toString());
    		ClientResource cr = new ClientResource(apiURL);
    		ChallengeResponse chr = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
    		
            chr.setRawValue(access_token);
            cr.setChallengeResponse(chr);
            
            try {
            	cr.post(MediaType.APPLICATION_ALL_JSON);
            } catch (ResourceException re) {
                log.warning("Error añadiendo PlayLists: " + cr.getResponse().getStatus());
            }
    	}
    }
    
    public void remove_PlayLists(List<String> ids) throws UnsupportedEncodingException {
    	String userId = this.getUserId();
    	//vamos a recorrer individualmente cada playlist para eliminarlas
    	for(int i=0;i<ids.size();i++) {
    		String apiURL = baseURL+"/playlists/"+ids.get(i)+"/followers";
    		ClientResource cr = new ClientResource(apiURL);
    		ChallengeResponse chr = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
    		
            chr.setRawValue(access_token);
            cr.setChallengeResponse(chr);
            
            try {
            	cr.delete(MediaType.APPLICATION_ALL_JSON);
            } catch (ResourceException re) {
                log.warning("Error añadiendo PlayLists: " + cr.getResponse().getStatus());
            }
    	}
    }
    
    private static int getRandomNumberInRange(int min, int max) {

    	if (min >= max) {
    		throw new IllegalArgumentException("max must be greater than min");
    	}

    	Random r = new Random();
    	return r.nextInt((max - min) + 1) + min;
    }
    
    private Playlist getPlayList(String id) throws UnsupportedEncodingException {
		String searchURL = baseURL + "/playlists/"+id+"?limit=50&fields="+URLEncoder.encode("collaborative,external_urls,href,id,images,name,owner,primary_color,public,snapshot_id,tracks(href,total,items.track.id),type,uri", StandardCharsets.UTF_8.toString());
		log.warning(searchURL);
		ClientResource cr = new ClientResource(searchURL);
		ChallengeResponse chr = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
        chr.setRawValue(access_token);
        cr.setChallengeResponse(chr);

        Playlist pl = null;
        try {
        	pl = cr.get(Playlist.class);
            return pl;

        } catch (ResourceException re) {
            log.warning("Asimbawe: " + re.getMessage());
            return null;
        }
    }
    
    public Search get_SearchResults(String searchInput) throws UnsupportedEncodingException {    	
        String searchURL = baseURL + "/search?q="+searchInput+"&type=playlist&limit=10&offset=0";
        
        ClientResource cr = new ClientResource(searchURL);

        ChallengeResponse chr = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
        chr.setRawValue(access_token);
        cr.setChallengeResponse(chr);

        Search search = null;
        try {
        	search = cr.get(Search.class);
            return search;

        } catch (ResourceException re) {
            log.warning("Error when retrieving Spotify playlists: " + cr.getResponse().getStatus());
            return null;
        }
    }

    public Playlists getPlaylists() {
        String playlistsGetURL = baseURL + "/me/playlists";
        ClientResource cr = new ClientResource(playlistsGetURL);

        ChallengeResponse chr = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
        chr.setRawValue(access_token);
        cr.setChallengeResponse(chr);

        Playlists playlists = null;
        try {
            playlists = cr.get(Playlists.class);
            return playlists;

        } catch (ResourceException re) {
            log.warning("Error when retrieving Spotify playlists: " + cr.getResponse().getStatus());
            log.warning(playlistsGetURL);
            return null;
        }
    }

    public Playlist createPlaylist(String name, String description) {
        String userId = this.getUserId();
        if (userId != null && !name.trim().isEmpty()) {
            String playlistPostURL = baseURL + "/users/" + userId + "/playlists";
            ClientResource cr = new ClientResource(playlistPostURL);

            ChallengeResponse chr = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
            chr.setRawValue(access_token);
            cr.setChallengeResponse(chr);

            NewPlaylist p = new NewPlaylist();
            p.setName(name);
            p.setDescription(description);

            log.info("Creating new playlist with name '" + name + "', description '" + description + "' and userId '" + userId + "'");

            try {
                return cr.post(p, Playlist.class);

            } catch (ResourceException re) {
                log.warning("Error when creating a Spotify playlist: " + cr.getResponse().getStatus());
                log.warning(playlistPostURL);
                return null;
            }
        } else {
            log.warning("Error when getting userID from Spotify");
            return null;
        }
    }

    protected String getUserId() {
        String userProfileURL = baseURL + "/me";
        ClientResource cr = new ClientResource(userProfileURL);

        ChallengeResponse chr = new ChallengeResponse(ChallengeScheme.HTTP_OAUTH_BEARER);
        chr.setRawValue(access_token);
        cr.setChallengeResponse(chr);

        log.info("Retrieving user profile");

        try {
            return cr.get(UserProfile.class).getId();

        } catch (ResourceException re) {
            log.warning("Error when retrieving the user profile: " + cr.getResponse().getStatus());
            log.warning(userProfileURL);
            return null;
        }
    }
}
