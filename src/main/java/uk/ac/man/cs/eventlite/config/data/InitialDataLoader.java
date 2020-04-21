package uk.ac.man.cs.eventlite.config.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import uk.ac.man.cs.eventlite.entities.Event;
import uk.ac.man.cs.eventlite.entities.Venue;
import uk.ac.man.cs.eventlite.services.EventService;
import uk.ac.man.cs.eventlite.services.VenueService;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Profile({"default", "test"})
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger log = LoggerFactory.getLogger(InitialDataLoader.class);

    private final EventService eventService;

    private final VenueService venueService;

    public InitialDataLoader(EventService eventService, VenueService venueService) {
        this.eventService = eventService;
        this.venueService = venueService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (eventService.count() > 0) {
            log.info("Database already populated. Skipping data initialization.");
        } else {// Build and save initial models here.
            Venue venue1 = new Venue("Chemistry Lecture Theatre C.051", 200, "Uni Library");
            venue1.setLat(53.464481);
            venue1.setLng(-2.235746);
            venueService.save(venue1);

            Event AlgoLec = new Event(LocalDate.of(2021, 2, 24), LocalTime.of(10, 0), "Algorithms Lecture", venue1);
		    eventService.save(AlgoLec);
		     
	        Venue venue2 = new Venue("Kilburn G23", 40, "Uni Library");
	        venue2.setLat(53.464481);
	        venue2.setLng(-2.235746);
	        venueService.save(venue2);
	        
	        Event SoftEngg = new Event(LocalDate.of(2021, 2, 12), LocalTime.now(), "Software Engg", venue2);
			eventService.save(SoftEngg);
			
			Venue venue3 = new Venue("Tootil 1", 60, "Oxford st");
	        venueService.save(venue3);
	        
	        Event Microcontrollers = new Event(LocalDate.of(2018, 3, 15), LocalTime.of(13, 0), "Microcontrollers", venue2);
			eventService.save(Microcontrollers);
			
		}     
    }
}
