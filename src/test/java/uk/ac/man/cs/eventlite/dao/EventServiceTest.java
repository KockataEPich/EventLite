package uk.ac.man.cs.eventlite.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import uk.ac.man.cs.eventlite.EventLite;
import uk.ac.man.cs.eventlite.entities.Event;
import uk.ac.man.cs.eventlite.services.EventService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EventLite.class)
@DirtiesContext
@ActiveProfiles("test")
public class EventServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private EventService eventService;

    // This class is here as a starter for testing any custom methods within the
    // EventService.

    @Test
    public void testFindEventIfNameContains() {
        Iterable<Event> eventsFound = eventService.findByName("Test event");
        assertNotNull(eventsFound);

        for (Event e : eventsFound)
            assertFalse(e.getName().toUpperCase().contains("test event".toUpperCase()));

        Event newEvent = new Event();
        newEvent.setName("Test event");
        eventService.save(newEvent);

        eventsFound = eventService.findByName("car");
        assertNotNull(eventsFound);

        for (Event e : eventsFound)
            assertTrue(e.getName().toUpperCase().contains("test event".toUpperCase()));
    }

}
