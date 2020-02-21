package uk.ac.man.cs.eventlite.config.data;

import java.time.LocalDate;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import uk.ac.man.cs.eventlite.dao.EventService;
import uk.ac.man.cs.eventlite.dao.VenueService;
import uk.ac.man.cs.eventlite.entities.Event;
import uk.ac.man.cs.eventlite.entities.Venue;

@Component
@Profile({ "default", "test" })
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private final static Logger log = LoggerFactory.getLogger(InitialDataLoader.class);

	@Autowired
	private EventService eventService;

	@Autowired
	private VenueService venueService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (eventService.count() > 0) {
			log.info("Database already populated. Skipping data initialization.");
			return;
		}
//
//		// Build and save initial models here.
		Event AlgoLec = new Event();
		AlgoLec.setId(1);
		AlgoLec.setName("Algorithms Lecture ");
		AlgoLec.setDate(LocalDate.of(2020, 2, 24));
		AlgoLec.setTime(LocalTime.of(10,0,0));
		AlgoLec.setVenue(1);
        eventService.save(AlgoLec);
        
        Venue venue1 = new Venue();
        venue1.setId(1);
        venue1.setName("Chemistry Lecture Theatre C.051");
        venue1.setCapacity(200);
        venueService.save(venue1);
        
        Event Random = new Event();
        Random.setId(2);
		Random.setName("Random");
		Random.setDate(LocalDate.of(2020, 2, 12));
		Random.setTime(LocalTime.now());
		Random.setVenue(2);
		eventService.save(Random);
        
        Venue venue2 = new Venue();
        venue2.setName("Quiet Lab");
        venue2.setCapacity(20);
        venueService.save(venue2);
    }
}
