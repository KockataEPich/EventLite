package uk.ac.man.cs.eventlite.services;

import java.util.Optional;

import uk.ac.man.cs.eventlite.entities.Venue;

public interface VenueService {

    long count();

    Optional<Venue> findById(long id);

    Venue findOne(long id);

    Iterable<Venue> findAll();

    Iterable<Venue> findByName(String name);

    Iterable<Venue> findByNameContaining(String name);

    Venue save(Venue venue);
}
