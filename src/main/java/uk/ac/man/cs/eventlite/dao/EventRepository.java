package uk.ac.man.cs.eventlite.dao;

import org.springframework.data.repository.CrudRepository;

import uk.ac.man.cs.eventlite.entities.Event;

public interface EventRepository extends CrudRepository<Event, Long>{

		public Iterable<Event> findByOrderByDateAscTimeAsc();
		
		public Iterable<Event> findAllByName(String name);

		public Iterable<Event> findAllByNameContainingIgnoreCase(String name);
}
