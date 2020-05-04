package uk.ac.man.cs.eventlite.services;

import uk.ac.man.cs.eventlite.entities.Event;
import uk.ac.man.cs.eventlite.entities.Venue;

import java.util.List;
import java.util.Optional;

public interface EventService {

    long count();

    Iterable<Event> findAll();

    // Find the events that are upcoming
    Iterable<Event> findUpcoming();

    // Find the past events
    Iterable<Event> findPast();

    Iterable<Event> findByName(String name);

    Iterable<Event> findNamePast(String name);

    Iterable<Event> findNameUpcoming(String name);

    Optional<Event> findById(long id);

    Event findOne(long id);

    Event save(Event event);

    Event delete(Event event);

    Optional<Event> deleteById(long id);

    List<Event> findByVenue(Venue venue);
}
