package uk.ac.man.cs.eventlite.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import uk.ac.man.cs.eventlite.EventLite;
import uk.ac.man.cs.eventlite.entities.Venue;
import uk.ac.man.cs.eventlite.services.VenueService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EventLite.class)
@DirtiesContext
@ActiveProfiles("test")
public class VenueServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private VenueService venueService;

	// This class is here as a starter for testing any custom methods within the
	// VenueService.
	
	@Test
	public void testFindVenueIfNameContains() throws Exception {
		Iterable<Venue> venuesFound = venueService.findByNameContaining("Test venue");
		assertNotNull(venuesFound);
		
		for(Venue v : venuesFound)
			assertFalse(v.getName().toUpperCase().contains("test venue".toUpperCase()));
		
		Venue newVenue = new Venue();
		newVenue.setName("Test venue");
		venueService.save(newVenue);
		
		venuesFound = venueService.findByNameContaining("motorcycle");
		assertNotNull(venuesFound);
		
		for(Venue v : venuesFound)
			assertTrue(v.getName().toUpperCase().contains("test venue".toUpperCase()));
	}
}
