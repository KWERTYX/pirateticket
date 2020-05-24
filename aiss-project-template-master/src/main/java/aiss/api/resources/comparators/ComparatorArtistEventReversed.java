package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Event;

public class ComparatorArtistEventReversed  implements Comparator<Event>{

	@Override
	public int compare(Event e1, Event e2) {
		return e2.getArtista().compareTo(e1.getArtista());
	}

}
