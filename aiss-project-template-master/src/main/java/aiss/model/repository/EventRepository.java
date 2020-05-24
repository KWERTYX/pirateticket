package aiss.model.repository;

import java.util.Collection;

import aiss.model.Event;

public interface EventRepository {
	// 
	public void addEvent(Event s);
	public Collection<Event> getAllEvents();
	public Event getEvent(String id);
	public void updateEvent(Event s);
	public void deleteEvent(String id);

}
