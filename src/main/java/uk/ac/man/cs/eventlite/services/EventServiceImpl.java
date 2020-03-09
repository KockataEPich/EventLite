package uk.ac.man.cs.eventlite.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.ac.man.cs.eventlite.dao.EventRepository;
import uk.ac.man.cs.eventlite.entities.Event;

import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final static Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    final EventRepository eventRepository;

    private final static String DATA = "data/events.json";

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    
    @Override
	public Iterable<Event> findByName(String name) {
		return eventRepository.findAllByName(name);
	}
	
	@Override
	public Iterable<Event> findByNameContaining(String name) {
		return eventRepository.findAllByNameContainingIgnoreCase(name);
	}

    @Override
    public long count() {
        return eventRepository.count();
    }

    @Override
    public Iterable<Event> findAll() {
        return eventRepository.findByOrderByDateAscTimeAsc();
    }

    @Override
    public Event save(Event event) {
        // TODO Auto-generated method stub
        return eventRepository.save(event);
    }

    @Override
    public Event delete(Event event) {
        eventRepository.delete(event);
        return event;
    }

    @Override
    public Optional<Event> deleteById(long id) {
        Optional<Event> requestedEvent = Optional.ofNullable(eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id)));
        requestedEvent.ifPresent(c -> eventRepository.deleteById(id));
        return requestedEvent;
    }

}
