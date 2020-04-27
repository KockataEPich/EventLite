package uk.ac.man.cs.eventlite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.man.cs.eventlite.dao.EventRepository;
import uk.ac.man.cs.eventlite.dao.VenueRepository;
import uk.ac.man.cs.eventlite.entities.Event;
import uk.ac.man.cs.eventlite.entities.Venue;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private EventService eventService;

    @Override
    public long count() {
        return venueRepository.count();
    }

    @Override
    public Optional<Venue> findById(long id) {
        return venueRepository.findById(id);
    }

    @Override
    public Venue findOne(long id) {
        return findById(id).orElse(null);
    }

    @Override
    public Iterable<Venue> findAll() {
        return venueRepository.findByOrderByName();
    }

    @Override
    public Iterable<Venue> findByName(String name) {
        return venueRepository.findAllByName(name);
    }

    @Override
    public Iterable<Venue> findByNameContaining(String name) {
        return venueRepository.findAllByNameContainingIgnoreCaseOrderByName(name);
    }

    @Override
    public Venue save(Venue venue) {
        return venueRepository.save(venue);
    }

    @Override
    public List<Event> deleteEvents(Venue venue) {
        List<Event> eventList = eventService.findByVenue(venue);
        eventList.forEach(e -> eventService.delete(e));
        return eventList;
    }

    @Override
    public Optional<Venue> deleteById(long id) {
        Optional<Venue> venue = findById(id);
        venue.ifPresent(this::delete);
        return venue;
    }

    @Override
    public Venue delete(Venue venue) {
        venueRepository.delete(venue);
        return venue;
    }

    @Override
    public Boolean isDeletable(Venue venue) {
        List<Event> eventList = eventService.findByVenue(venue);
        return eventList.isEmpty();
    }
}
