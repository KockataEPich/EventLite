package uk.ac.man.cs.eventlite.services;

import java.util.List;
import java.util.Optional;

import uk.ac.man.cs.eventlite.entities.Event;
import uk.ac.man.cs.eventlite.entities.Venue;

public interface VenueService {

    long count();

    Optional<Venue> findById(long id);

    Venue findOne(long id);

    Iterable<Venue> findAll();

    Iterable<Venue> findByName(String name);

    Iterable<Venue> findByNameContaining(String name);

    Venue save(Venue venue);

    Venue delete(Venue venue);

    Optional<Venue> deleteById(long id);

    List<Event> deleteEvents(Venue venue);

    Boolean isDeletable(Venue venue);
}
