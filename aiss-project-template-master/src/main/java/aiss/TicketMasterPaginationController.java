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

public class TicketMasterPaginationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final Logger log = Logger.getLogger(TicketMasterPaginationController.class.getName());
	 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketMasterPaginationController() {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
		String url = request.getParameter("url");
		RequestDispatcher rd = null;
		// Search for ticketmaster events
		log.log(Level.FINE, "Searching for ticketMaster events that contain " + url);
		url = Utils.TICKET_MASTER_API_URL.concat(url);
		TicketMasterResource ticketMasterResource = new TicketMasterResource();
		System.out.println("LA URL ES "+ url);
		TicketMasterEventSearch ticketMasterResults = ticketMasterResource.getEventsPage(url);

		if (ticketMasterResults!=null){
			rd = request.getRequestDispatcher("/ticketMasterList.jsp");
			request.setAttribute("events", ticketMasterResults.getEmbedded().getTicketMasterEvent());
			request.setAttribute("links", ticketMasterResults.getLinks());
			request.setAttribute("page", ticketMasterResults.getPage());
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
