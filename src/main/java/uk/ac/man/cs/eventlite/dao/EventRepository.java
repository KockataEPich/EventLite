package uk.ac.man.cs.eventlite.dao;

import org.springframework.data.repository.CrudRepository;
import uk.ac.man.cs.eventlite.entities.Event;

import java.time.LocalDate;

public interface EventRepository extends CrudRepository<Event, Long> {

    Iterable<Event> findByOrderByDateAscTimeAsc();

    Iterable<Event> findByDateLessThan(LocalDate now);

    Iterable<Event> findAllByDateLessThanAndNameContainingIgnoreCaseOrderByDateAscName(LocalDate date, String name);

    Iterable<Event> findAllByDateGreaterThanEqualAndNameContainingIgnoreCaseOrderByDateAscName(LocalDate date, String name);

    Iterable<Event> findByDateGreaterThanEqual(LocalDate now);

    Iterable<Event> findAllByName(String name);

    Iterable<Event> findAllByNameContainingIgnoreCaseOrderByDateAscName(String name);
}
