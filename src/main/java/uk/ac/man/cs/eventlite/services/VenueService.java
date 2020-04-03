package uk.ac.man.cs.eventlite.services;

import uk.ac.man.cs.eventlite.entities.Venue;

public interface VenueService {

	public long count();

    public Iterable<Venue> findAll();
	
	public Iterable<Venue> findByName(String name);
	
	public Iterable<Venue> findByNameContaining(String name);
	
	public Venue save(Venue venue);
}
