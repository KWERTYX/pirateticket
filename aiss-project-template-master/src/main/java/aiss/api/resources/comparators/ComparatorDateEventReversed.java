package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Event;

public class ComparatorDateEventReversed implements Comparator<Event>{

	@Override
	public int compare(Event e1, Event e2) {
		return e2.getFecha().compareTo(e1.getFecha());
	}
	
}