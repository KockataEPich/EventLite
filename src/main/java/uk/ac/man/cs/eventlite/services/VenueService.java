package uk.ac.man.cs.eventlite.services;

import uk.ac.man.cs.eventlite.entities.Venue;

public interface VenueService {

	public long count();

	public Iterable<Venue> findAll();
	
	public Venue save(Venue venue);
}
