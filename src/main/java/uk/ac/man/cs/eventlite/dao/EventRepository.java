package uk.ac.man.cs.eventlite.dao;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import uk.ac.man.cs.eventlite.entities.Event;

public interface EventRepository extends CrudRepository<Event, Long>{

		public Iterable<Event> findByOrderByDateAscTimeAsc();
		
		public Iterable<Event> findByDateLessThan(LocalDate now);
		
		public Iterable<Event> findAllByDateLessThanAndNameContainingIgnoreCaseOrderByDateAscName(LocalDate date, String name);
		
		public Iterable<Event> findAllByDateGreaterThanEqualAndNameContainingIgnoreCaseOrderByDateAscName(LocalDate date, String name );
		
		public Iterable<Event> findByDateGreaterThanEqual(LocalDate now);
		
		public Iterable<Event> findAllByName(String name);

		public Iterable<Event> findAllByNameContainingIgnoreCaseOrderByDateAscName(String name);
}
