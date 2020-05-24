package aiss.model.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import aiss.model.Event;

public class MapEventRepository implements EventRepository{

	Map<String, Event> eventMap;
	private static MapEventRepository instance=null;
	private int index=0;			// Index to create event identifiers.
	
	
	public static MapEventRepository getInstance() {
		
		if (instance==null) {
			instance = new MapEventRepository();
			instance.init();
		}
		
		return instance;
	}
	
	public void init() {
		
		eventMap = new HashMap<String,Event>();
		
		// Create event
		Event conciertoEstopa=new Event();
		conciertoEstopa.setArtista("Estopa");
		conciertoEstopa.setFecha(1590157587000L);
		conciertoEstopa.setPrecio(35.0);
		conciertoEstopa.setLugar("Sevilla");
		addEvent(conciertoEstopa);
		
		Event conciertoEstopa2=new Event();
		conciertoEstopa2.setArtista("Estopa");
		conciertoEstopa2.setFecha(1598565600000L);
		conciertoEstopa2.setPrecio(35.0);
		conciertoEstopa2.setLugar("Granada");
		addEvent(conciertoEstopa2);
		
		Event conciertoEstopa3=new Event();
		conciertoEstopa3.setArtista("Estopa");
		conciertoEstopa3.setFecha(1599343200000L);
		conciertoEstopa3.setPrecio(35.0);
		conciertoEstopa3.setLugar("Barcelona");
		addEvent(conciertoEstopa3);
		
		Event conciertoACDC=new Event();
		conciertoACDC.setArtista("AC/DC");
		conciertoACDC.setFecha(1592231187000L);
		conciertoACDC.setPrecio(58.0);
		conciertoACDC.setLugar("Madrid");
		addEvent(conciertoACDC);
		
		Event conciertoACDC2=new Event();
		conciertoACDC2.setArtista("AC/DC");
		conciertoACDC2.setFecha(1599690600000L);
		conciertoACDC2.setPrecio(58.0);
		conciertoACDC2.setLugar("Bilbao");
		addEvent(conciertoACDC2);
		
		Event festivalDavidGuetta=new Event();
		festivalDavidGuetta.setArtista("David Guetta");
		festivalDavidGuetta.setFecha(1593108000000L);
		festivalDavidGuetta.setPrecio(40.0);
		festivalDavidGuetta.setLugar("Barcelona");
		addEvent(festivalDavidGuetta);
		
		Event festivalMartinGarrix=new Event();
		festivalMartinGarrix.setArtista("Martin Garrix");
		festivalMartinGarrix.setFecha(1593194400000L);
		festivalMartinGarrix.setPrecio(38.0);
		festivalMartinGarrix.setLugar("Paris");
		addEvent(festivalMartinGarrix);
		
		Event festivalDeadMau=new Event();
		festivalDeadMau.setArtista("Deadmau5");
		festivalDeadMau.setFecha(1593280800000L);
		festivalDeadMau.setPrecio(42.0);
		festivalDeadMau.setLugar("Lisboa");
		addEvent(festivalDeadMau);
		
	}
	
	@Override
	public void addEvent(Event e) {
		String id = "e" + index++;	
		e.setId(id);
		eventMap.put(id,e);
	}
	
	@Override
	public Collection<Event> getAllEvents() {
			return eventMap.values();
	}

	@Override
	public Event getEvent(String id) {
		return eventMap.get(id);
	}
	
	@Override
	public void updateEvent(Event e) {
		eventMap.put(e.getId(),e);
	}

	@Override
	public void deleteEvent(String id) {	
		eventMap.remove(id);
	}
	
}
