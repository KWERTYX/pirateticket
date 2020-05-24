package aiss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.com.google.gson.Gson;

import aiss.resource.SpotifyResource;
import aiss.model.spotify.Playlists;
import aiss.model.spotify.Search;

public class SpotifyServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(SpotifyServlet.class.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		String accessToken = (String) req.getSession().getAttribute("Spotify-token");
	
		Map params = req.getParameterMap();
		
		Map<String, String> response = new HashMap<String, String>();
		response.put("ok", "false");
		
		List<String> data = new ArrayList<String>();
		//recoge búsquedas
		if(params.containsKey("action") && accessToken != null && accessToken.length() > 0) {
			SpotifyResource spResource = new SpotifyResource(accessToken);
			response.put("ok", "true");
			
			if(params.containsKey("playlists")) {
				List<String> req_playlists = Arrays.asList(req.getParameter("playlists").split(","));
				System.out.println("LOCONTIENE");
				switch(req.getParameter("action")) {
				case "addPlaylists":
					System.out.println("aaaaaaaaaaaaaaaa");
					spResource.add_PlayLists(req_playlists);
					break;
				case "removePlaylists":
					spResource.remove_PlayLists(req_playlists);
					break;
				}
			}
			
			//devuelve la lista actualizada de playlists del usuario
			Playlists playlists = spResource.getPlaylists();
			for(Integer i=0;i<playlists.getTotal();i++) {
				data.add(playlists.getItems().get(i).getName());
			}
			response.put("data", (new Gson().toJson(data)));
		}
		req.setAttribute("json", (new Gson().toJson(response)));
		req.getRequestDispatcher("/json_results.jsp").forward(req, res);
	}
}
