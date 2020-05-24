package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.api.resources.comparators.ComparatorArtistEvent;
import aiss.api.resources.comparators.ComparatorArtistEventReversed;
import aiss.api.resources.comparators.ComparatorDateEvent;
import aiss.api.resources.comparators.ComparatorDateEventReversed;
import aiss.model.Event;
import aiss.model.repository.EventRepository;
import aiss.model.repository.MapEventRepository;

@Path("/events")
public class EventResource {
	
	private static EventResource _instance = null;
	EventRepository repository;

	private EventResource() {
		repository=MapEventRepository.getInstance();
	}

	public static EventResource getInstance() {
		if (_instance == null)
			_instance = new EventResource();
		return _instance;
	}

	@GET
	@Produces("application/json")
	public Collection<Event> getAll(@QueryParam("order") String order, @QueryParam("artista") String artist, @QueryParam("lugar") String lugar) {
		List<Event> result = new ArrayList<Event>();

		result = repository.getAllEvents().stream().filter(e -> {
			if (artist == null) {
				return true;
			} else {
				return e.getArtista().equalsIgnoreCase(artist);
			}
		}).filter(e ->{
			if (lugar == null) {
				return true;
			} else {
				return e.getLugar().equalsIgnoreCase(lugar);
			}
		}).collect(Collectors.toList());
		
		/*for (Event event : repository.getAllEvents()) {
			if ((artist == null && lugar == null) || (event.getArtista().equals(artist))) { // artist filter
					result.add(event);
			}
		}*/

		if (order != null) { // Order results
			if (order.equals("artista")) {
				Collections.sort(result, new ComparatorArtistEvent());
			} else if (order.equals("-artista")) {
				Collections.sort(result, new ComparatorArtistEventReversed());
			} else if(order.contentEquals("fecha")) {
				Collections.sort(result, new ComparatorDateEvent());
			}else if(order.contentEquals("-fecha")) {
				Collections.sort(result, new ComparatorDateEventReversed());
			} else {
				throw new BadRequestException("The order parameter must be 'artista/-artista' or 'fecha/-fecha'.");
			}
		}

		return result;
	}

	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Event get(@PathParam("id") String id) {
		Event event = repository.getEvent(id);

		if (event == null) {
			throw new NotFoundException("The event wit id=" + id + " was not found");
		}

		return event;
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addEvent(@Context UriInfo uriInfo, Event event) {
		if (event.getArtista() == null || event.getArtista().isEmpty() || event.getLugar() == null || event.getLugar().isEmpty() || event.getFecha() == null)
			throw new BadRequestException("The artist, place and date event must not be null");
		
		Boolean exist = repository.getAllEvents().stream()
			.filter(e -> e.getArtista().equalsIgnoreCase(event.getArtista()))
			.filter(e -> e.getLugar().equalsIgnoreCase(event.getLugar()))
			.filter(e -> e.getFecha().equals(event.getFecha()))
			.findAny()
			.isPresent();
		
		if (exist) {
			return Response.status(Status.CONFLICT).build();
		}

		repository.addEvent(event);

		// Builds the response. Returns the event the has just been added.
		if (uriInfo.getAbsolutePathBuilder() != null) {
			UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
			URI uri = ub.build(event.getId());
			ResponseBuilder resp = Response.created(uri);
			resp.entity(event);			
			return resp.build();
		} else {
			return Response.status(Status.CREATED).entity(event).build();
		}
	}

	@PUT
	@Consumes("application/json")
	public Response updateEvent(Event event) {
		Event oldEvent= repository.getEvent(event.getId());
		if (oldEvent == null) {
			throw new NotFoundException("The event with id=" + event.getId() + " was not found");
		}

		// Update artista
		if (event.getArtista() != null) {
			oldEvent.setArtista(event.getArtista());			
		}

		// Update fecha
		if (event.getFecha() != null) {
			oldEvent.setFecha(event.getFecha());			
		}
		
		// Update lugar
		if (event.getLugar() != null) {
			oldEvent.setLugar(event.getLugar());			
		}
		
		// Update precio
		if (event.getPrecio() != null) {
			oldEvent.setPrecio(event.getPrecio());			
		}

		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id}")
	public Response removeEvent(@PathParam("id") String id) {
		Event eventremoved = repository.getEvent(id);
		if (eventremoved == null)
			throw new NotFoundException("The event with id=" + id + " was not found");
		else
			repository.deleteEvent(id);

		return Response.noContent().build();
	}
}