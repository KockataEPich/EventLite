package uk.ac.man.cs.eventlite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uk.ac.man.cs.eventlite.dao.EventService;
import uk.ac.man.cs.eventlite.dao.VenueService;
import uk.ac.man.cs.eventlite.entities.Event;

@Controller
@RequestMapping(value = "/events", produces = { MediaType.TEXT_HTML_VALUE })
public class EventsController {

	@Autowired
	private EventService eventService;

	@Autowired
	private VenueService venueService;

	@RequestMapping(method = RequestMethod.GET)
	public String getAllEvents(Model model) {

		model.addAttribute("events", eventService.findAll());
//		model.addAttribute("venues", venueService.findAll());

		return "events/index";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "eventDetails/index/{id}")
	public String getAllEventDetails(Model model, @PathVariable long id) {

        Event eventGiven = null;
        for(Event event : eventService.findAll()){
            if(event.getId() == id){
                eventGiven = event;
            }
        }
            
        if(eventGiven != null){
            // adding attributes
    		model.addAttribute("events", eventService.findAll());
		    model.addAttribute("eventName", eventGiven.getName());
	    	model.addAttribute("eventDescription", eventGiven.getDescription());
	    	model.addAttribute("eventTime", eventGiven.getTime());
	    	model.addAttribute("eventDate", eventGiven.getDate());
	    	model.addAttribute("eventVenue", eventGiven.getVenue())
;
        }
		return "events/eventDetails/index";
	}

}
