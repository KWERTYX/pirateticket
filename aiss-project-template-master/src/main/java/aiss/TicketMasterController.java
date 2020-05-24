package aiss;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aiss.resource.TicketMasterResource;
import aiss.model.ticketmaster.TicketMasterEventSearch;
import aiss.utility.Utils;

public class TicketMasterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final Logger log = Logger.getLogger(TicketMasterController.class.getName());
	 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketMasterController() {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
		String query = request.getParameter("query");
		String url = request.getParameter("url");

		RequestDispatcher rd = null;
		
		// Search for ticketmaster events
		log.log(Level.FINE, "Searching for ticketMaster events that contain " + query);
		TicketMasterResource ticketMasterResource = new TicketMasterResource();
		TicketMasterEventSearch ticketMasterResults = null;
		if (query != null) {
			ticketMasterResults = ticketMasterResource.getEvents(query);			
		} else {
			ticketMasterResults = ticketMasterResource.getEventsPage(url);
		}

		if (ticketMasterResults!=null){
			rd = request.getRequestDispatcher("/ticketMasterList.jsp");
			System.out.println("nombre lugar: "+ ticketMasterResults.getEmbedded().getTicketMasterEvent().get(0).get_embedded().getVenues().get(0).getName());
			request.setAttribute("events", ticketMasterResults.getEmbedded().getTicketMasterEvent());
			request.setAttribute("links", ticketMasterResults.getLinks());
			request.setAttribute("page", ticketMasterResults.getPage());
			request.setAttribute("busqueda", query);
			request.setAttribute("tm_api_url", Utils.TICKET_MASTER_API_URL);
		} else {
			log.log(Level.SEVERE, "TicketMaster object: " + ticketMasterResults);
			rd = request.getRequestDispatcher("/error.jsp");
		}
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
