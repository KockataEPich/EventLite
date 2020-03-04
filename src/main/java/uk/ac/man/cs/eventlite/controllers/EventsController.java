package uk.ac.man.cs.eventlite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uk.ac.man.cs.eventlite.entities.Event;
import uk.ac.man.cs.eventlite.services.EventService;
import uk.ac.man.cs.eventlite.services.VenueService;

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
	
	@RequestMapping(value= "/search", method= RequestMethod.GET)
	public String findEventByName(@RequestParam (value= "search", required= false) String name, Model model) {
		model.addAttribute("search", eventService.findByName(name));
		model.addAttribute("search", eventService.findByNameContaining(name));
		return "events/index";
	}

	/*@RequestMapping (method = RequestMethod.DELETE,value = "/delete/{id}")
	public String deleteEvent(Model model, @PathVariable("id") long id){
		eventService.deleteById(id);
		model.addAttribute("events", eventService.findAll());
		return "redirect:/events/index";
	}*/

	@GetMapping("/delete/{id}")
	public String deleteEvent(@PathVariable("id") long id, Model model) {
		Event event = eventService.deleteById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		//eventService.delete(event);
		model.addAttribute("users", eventService.findAll());
		return "redirect:/events";
	}

}
