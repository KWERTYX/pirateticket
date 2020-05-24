package aiss.resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.resource.ClientResource;

import aiss.model.ticketmaster.TicketMasterEvent;
import aiss.model.ticketmaster.TicketMasterEventSearch;
import aiss.utility.Utils;

public class TicketMasterResource {
	
	private static final String TICKET_MASTER_API_KEY = "CUIy3RTKDmO99PZBAOHKN1ipd70g2INy";  //apikey ticketMaster
	private static final Logger log = Logger.getLogger(TicketMasterResource.class.getName());
	
	public TicketMasterEventSearch getEvents(String query) throws UnsupportedEncodingException {

		String queryFormatted = URLEncoder.encode(query, "UTF-8");
		String uri = Utils.TICKET_MASTER_API_URL+"/discovery/v2/events?apikey=" + TICKET_MASTER_API_KEY + "&keyword=" +queryFormatted;
		
		log.log(Level.FINE, "TicketMaster uri" + uri);
		
		ClientResource cr = new ClientResource(uri);
		TicketMasterEventSearch ticketMasterEvents= cr.get(TicketMasterEventSearch.class);
		
	    return ticketMasterEvents;
	}
	
	public TicketMasterEventSearch getEventsPage(String url) throws UnsupportedEncodingException {
		//String uri = URLEncoder.encode(url, "UTF-8");
		url = url + "&apikey=" + TICKET_MASTER_API_KEY;
		log.log(Level.FINE, "TicketMaster uri" + url);
		
		ClientResource cr = new ClientResource(url);
		TicketMasterEventSearch ticketMasterEvents= cr.get(TicketMasterEventSearch.class);
		
	    return ticketMasterEvents;
	}
	
	public TicketMasterEventSearch getEventsById(String id) throws UnsupportedEncodingException {

		String uri = Utils.TICKET_MASTER_API_URL+"/discovery/v2/events?apikey=" + TICKET_MASTER_API_KEY + "&id=" +id;
		
		log.log(Level.FINE, "TicketMaster uri" + uri);
		
		ClientResource cr = new ClientResource(uri);
		TicketMasterEventSearch ticketMasterEvents= cr.get(TicketMasterEventSearch.class);
		
	    return ticketMasterEvents;
	}

}
