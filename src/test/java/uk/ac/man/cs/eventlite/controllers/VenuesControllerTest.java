package uk.ac.man.cs.eventlite.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.ac.man.cs.eventlite.EventLite;
import uk.ac.man.cs.eventlite.config.Security;
import uk.ac.man.cs.eventlite.entities.Event;
import uk.ac.man.cs.eventlite.entities.Venue;
import uk.ac.man.cs.eventlite.services.EventService;
import uk.ac.man.cs.eventlite.services.VenueService;

import javax.servlet.Filter;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EventLite.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class VenuesControllerTest {

    private MockMvc mvc;

    @Autowired
    private Filter springSecurityFilterChain;

    @Mock
    private Event event;

    @Mock
    private Venue venue;

    @Mock
    private EventService eventService;

    @Mock
    private VenueService venueService;

    @InjectMocks
    private VenuesController venuesController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(venuesController).apply(springSecurity(springSecurityFilterChain))
                .build();
    }

    @Test
    public void postVenue() throws Exception {
        ArgumentCaptor<Venue> arg = ArgumentCaptor.forClass(Venue.class);

        mvc.perform(MockMvcRequestBuilders.post("/venues").with(user("Mustafa").roles(Security.ADMIN_ROLE))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).accept(MediaType.TEXT_HTML_VALUE).with(csrf())
                .param("name", "Factory of Cats")
                .param("capacity", "419")
                .param("address", "st bernard crossroads"))
                .andExpect(view().name("redirect:/venues"))
                .andExpect(status().isFound());
        verify(venueService).save(arg.capture());


    }

}
