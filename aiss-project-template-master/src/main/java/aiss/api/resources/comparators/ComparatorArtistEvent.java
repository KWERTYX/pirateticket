package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Event;

public class ComparatorArtistEvent implements Comparator<Event>{

	@Override
	public int compare(Event e1, Event e2) {
		return e1.getArtista().compareTo(e2.getArtista());
	}

}
