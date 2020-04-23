package uk.ac.man.cs.eventlite.services;

import org.springframework.stereotype.Service;
import uk.ac.man.cs.eventlite.dao.EventRepository;
import uk.ac.man.cs.eventlite.entities.Event;
import uk.ac.man.cs.eventlite.entities.Venue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    //private final static Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    final EventRepository eventRepository;

    //private final static String DATA = "data/events.json";

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Iterable<Event> findByName(String name) {
        return eventRepository.findAllByName(name);
    }

    // Find the events that are upcoming
    @Override
    public Iterable<Event> findUpcoming() {
        return eventRepository.findByDateGreaterThanEqual(LocalDate.now());
    }

    // Find the past events
    @Override
    public Iterable<Event> findPast() {
        return eventRepository.findByDateLessThan(LocalDate.now());
    }

    public Iterable<Event> findNamePast(String name) {
        return eventRepository.findAllByDateLessThanAndNameContainingIgnoreCaseOrderByDateAscName(LocalDate.now(), name);
    }

    public Iterable<Event> findNameUpcoming(String name) {
        return eventRepository.findAllByDateGreaterThanEqualAndNameContainingIgnoreCaseOrderByDateAscName(LocalDate.now(), name);
    }

    @Override
    public Optional<Event> findById(long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event findOne(long id) {
        return findById(id).orElse(null);
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

    @Override
    public List<Event> findByVenue(Venue venue) {
        Iterable<Event> eventIterable = findAll();
        List<Event> allEvents = new ArrayList<>();
        eventIterable.forEach(allEvents::add);
        return allEvents.stream()
                .filter(e -> e.getVenue()
                        .equals(venue))
                .collect(Collectors.toList());
    }
}
