package aiss.model.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import aiss.api.resources.EventResource;
import aiss.model.Event;

public class EventRepositoryTest {

	//private MapEventRepository repository;
	
	private EventResource repository;
	
	@Before
	public void setUp() throws Exception {
		repository = EventResource.getInstance();
	}
	
	@Test
	public void testGetEvent() {
		Collection<Event> events = repository.getAll(null, null, null);
		Event e = repository.get(events.stream().findFirst().get().getId());
		assertNotNull("El evento es null", e);
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetEventNotFound() {
		repository.get("1");
	}
	
	@Test
	public void testAddEvent() {
		 UriInfo uriInfo = Mockito.mock(UriInfo.class);
		    Mockito.when(uriInfo.getAbsolutePath())
		        .thenReturn(URI.create("http://localhost:8090/test"));
		    Response response= repository.addEvent(uriInfo, new Event("Artista 1", 1600209000000L, "Lugar 1"));
		    assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
	}
	
	@Test(expected = BadRequestException.class)
	public void testAddEventBadRequest() {
		 UriInfo uriInfo = Mockito.mock(UriInfo.class);
		    Mockito.when(uriInfo.getAbsolutePath())
		        .thenReturn(URI.create("http://localhost:8090/test"));
		    repository.addEvent(uriInfo, new Event());
	}
	
	@Test
	public void testAddEventConflict() {
		 UriInfo uriInfo = Mockito.mock(UriInfo.class);
		    Mockito.when(uriInfo.getAbsolutePath())
		        .thenReturn(URI.create("http://localhost:8090/test"));
		    Response response= repository.addEvent(uriInfo, new Event("Estopa",1590157587000L, "Sevilla"));
		    assertEquals(Status.CONFLICT.getStatusCode(), response.getStatus());
	}
	
	@Test(expected = NotFoundException.class)
	public void testUpdateEventNotFound() {
		Event e = new Event("1","Artista 1", 1600209000000L, "Lugar 1", 20.0);
		repository.updateEvent(e);
	}
	
	@Test
	public void testUpdateEvent() {
		Collection<Event> events = repository.getAll(null, null, null);
		Event e = repository.get(events.stream().findFirst().get().getId());
		e.setPrecio(42.0);
		repository.updateEvent(e);
	}
	
	@Test(expected = NotFoundException.class)
	public void testDeleteEventNotFound() {
		Event e = new Event("1","Artista 1", 1600209000000L, "Lugar 1", 20.0);
		repository.removeEvent(e.getId());
	}
	
	@Test
	public void testDeleteEvent() {
		Collection<Event> events = repository.getAll(null, null, null);
		assertNotNull("El evento es null", events);
		Event e = repository.get(events.stream().findFirst().get().getId());
		repository.removeEvent(e.getId());
		Collection<Event> newEvents = repository.getAll(null, null, null);
		assertNotNull("El evento es null", events);
		assertTrue("El evento no ha sido eliminado correctamente", events.size()-1 == newEvents.size());
	}
	
	@Test
	public void testGetAll() {
		Collection<Event> events = repository.getAll(null, null, null);
		assertNotNull("El evento es null", events);
		assertTrue("la lista de eventos está vacía", events.size() > 0);
	}
	
	@Test
	public void testGetAllArtistNaturalOrder() {
		Collection<Event> eventsOrdered = repository.getAll("artista", null, null);
		assertNotNull("El evento es null", eventsOrdered);
		Collection<Event> events = repository.getAll(null, null, null);
		assertNotNull("El evento es null", events);
		assertTrue("La ordenación no ha funcionado", eventsOrdered.stream().findFirst().get().getId().equals(events.stream().sorted((e1,e2) -> e1.getArtista().compareTo(e2.getArtista())).findFirst().get().getId()));
	}
	
	@Test
	public void testGetAllArtistReversedOrder() {
		Collection<Event> eventsOrdered = repository.getAll("-artista", null, null);
		assertNotNull("El evento es null", eventsOrdered);
		Collection<Event> events = repository.getAll(null, null, null);
		assertNotNull("El evento es null", events);
		assertTrue("La ordenación no ha funcionado", eventsOrdered.stream().findFirst().get().getId().equals(events.stream().sorted((e1,e2) -> e2.getArtista().compareTo(e1.getArtista())).findFirst().get().getId()));
	}
	
	@Test
	public void testGetAllDateNaturalOrder() {
		Collection<Event> eventsOrdered = repository.getAll("fecha", null, null);
		assertNotNull("El evento es null", eventsOrdered);
		Collection<Event> events = repository.getAll(null, null, null);
		assertNotNull("El evento es null", events);
		assertTrue("La ordenación no ha funcionado", eventsOrdered.stream().findFirst().get().getId().equals(events.stream().sorted((e1,e2) -> e1.getFecha().compareTo(e2.getFecha())).findFirst().get().getId()));
	}
	
	@Test
	public void testGetAllDateReversedOrder() {
		Collection<Event> eventsOrdered = repository.getAll("-fecha", null, null);
		assertNotNull("El evento es null", eventsOrdered);
		Collection<Event> events = repository.getAll(null, null, null);
		assertNotNull("El evento es null", events);
		assertTrue("La ordenación no ha funcionado", eventsOrdered.stream().findFirst().get().getId().equals(events.stream().sorted((e1,e2) -> e2.getFecha().compareTo(e1.getFecha())).findFirst().get().getId()));
	}
	
	@Test(expected = BadRequestException.class)
	public void testGetAllBadOrder() {
		repository.getAll("id", null, null);
	}
	
	@Test
	public void testGetAllArtistFilter() {
		Collection<Event> eventsFiltered = repository.getAll(null, "estopa", null);
		assertNotNull("El evento es null", eventsFiltered);
		Collection<Event> events = repository.getAll(null, null, null);
		assertNotNull("El evento es null", events);
		assertTrue("El filtrado por artista ha fallado", eventsFiltered.size() == events.stream()
				.filter(e -> e.getArtista().equalsIgnoreCase("estopa"))
				.collect(Collectors.toList()).size());
	}
	
	@Test
	public void testGetAllPlaceFilter() {
		Collection<Event> eventsFiltered = repository.getAll(null, null, "sevilla");
		assertNotNull("El evento es null", eventsFiltered);
		Collection<Event> events = repository.getAll(null, null, null);
		assertNotNull("El evento es null", events);
		assertTrue("El filtrado por artista ha fallado", eventsFiltered.size() == events.stream()
				.filter(e -> e.getLugar().equalsIgnoreCase("sevilla"))
				.collect(Collectors.toList()).size());
	}
	
	/*public void addEvent(Event s);*/
	
	
}
