package uk.ac.man.cs.eventlite.services;

import uk.ac.man.cs.eventlite.entities.Event;

import java.util.Optional;

public interface EventService {

    public long count();

    public Iterable<Event> findAll();
	
	public Iterable<Event> findByName(String name);
	
	public Iterable<Event> findByNameContaining(String name);
	
	public Optional<Event> findById(long id);
	
	public Event findOne(long id);
	
    public Event save(Event event);

    public Event delete(Event event);

    public Optional<Event> deleteById(long id);
}
