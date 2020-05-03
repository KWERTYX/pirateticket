package aiss;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aiss.model.resource.SpotifyResource;
import aiss.model.spotify.Playlists;
import aiss.model.spotify.Search;

public class SpotifyServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(SpotifyServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		String accessToken = (String) req.getSession().getAttribute("Spotify-token");
	
		Map params = req.getParameterMap();
		
		//recoge búsquedas
		if(params.containsKey("spotify_search")) {
			if(accessToken != null && accessToken.length() > 0) {
				SpotifyResource spResource = new SpotifyResource(accessToken);
				Search search_results = spResource.get_SearchResults(req.getParameter("spotify_search"));
				req.setAttribute("search_results", search_results);
			} else {
				req.getRequestDispatcher("/AuthController/Spotify").forward(req, res);
			}
		}
		
		//recoge las playlist del usuario
		if(accessToken != null && accessToken.length() > 0) {
			SpotifyResource spResource = new SpotifyResource(accessToken);
			Playlists playlists = spResource.getPlaylists();
			req.setAttribute("user_playlists", playlists);

		}

		req.getRequestDispatcher("/spotify_results.jsp").forward(req, res);
		
	}
}
