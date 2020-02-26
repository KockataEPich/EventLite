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

		else{// Build and save initial models here.
			Venue venue1 = new Venue("Chemistry Lecture Theatre C.051", 200);
//		    venue1.setId(1);
//	        venue1.setName("Chemistry Lecture Theatre C.051");
//	        venue1.setCapacity(200);
	        venueService.save(venue1);
	        
			Event AlgoLec = new Event(LocalDate.of(2020, 2, 24), LocalTime.of(10,0), "Algorithms Lecture", venue1);
//			AlgoLec.setId(1);
//			AlgoLec.setName("Algorithms Lecture ");
//			AlgoLec.setDate(LocalDate.of(2020, 2, 24));
//			AlgoLec.setTime(LocalTime.of(10,0,0));
//			AlgoLec.setVenue(venue1);
		    eventService.save(AlgoLec);
		     
		    
	        
	        Venue venue2 = new Venue("Kilburn G23", 40);
//	        venue2.setName("Quiet Lab");
//	        venue2.setCapacity(20);
	        venueService.save(venue2);
	        
	        Event SoftEngg = new Event(LocalDate.of(2020, 2, 12), LocalTime.now(), "Software Engg", venue2);
//	        Random.setId(2);
//			Random.setName("Random");
//			Random.setDate(LocalDate.of(2020, 2, 12));
//			Random.setTime(LocalTime.now());
//			Random.setVenue(venue2);
			eventService.save(SoftEngg);
	        
	        
		}     
    }
}
