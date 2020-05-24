package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Event;

public class ComparatorDateEvent implements Comparator<Event>{

	@Override
	public int compare(Event e1, Event e2) {
		return e1.getFecha().compareTo(e2.getFecha());
	}
}
