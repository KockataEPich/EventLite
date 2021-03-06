package uk.ac.man.cs.eventlite.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uk.ac.man.cs.eventlite.entities.Event;
import uk.ac.man.cs.eventlite.entities.Venue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import uk.ac.man.cs.eventlite.services.EventService;
import uk.ac.man.cs.eventlite.services.VenueService;

@Controller
@RequestMapping(value = "/events", produces = {MediaType.TEXT_HTML_VALUE})
public class EventsController {

    @Autowired
    private EventService eventService;

    @Autowired
    private VenueService venueService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllEvents(Model model) {


        model.addAttribute("upcoming_events", eventService.findUpcoming());
        model.addAttribute("past_events", eventService.findPast());
        model.addAttribute("events", eventService.findAll());

        return "events/index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newEvent(Model model) {
        if (!model.containsAttribute("event")) {
            model.addAttribute("event", new Event());
        }

        //get all the venues so we can display them
        Iterable<Venue> allVenues = venueService.findAll();

        model.addAttribute("allVenues", allVenues);
        return "events/new";
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createEvent(@RequestBody @Valid @ModelAttribute("event") Event event,
                              BindingResult errors, Model model, RedirectAttributes redirectAttrs) {

        if (errors.hasErrors()) {
            model.addAttribute("event", event);
            //get all the venues so we can display them after error in form
            Iterable<Venue> allVenues = venueService.findAll();

            model.addAttribute("allVenues", allVenues);

            return "events/new";
        }

        //save event passed over by the post request
        eventService.save(event);
        redirectAttrs.addFlashAttribute("ok_message", "New event added.");
        return "redirect:/events";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String findEventByName(@RequestParam(value = "search", required = false) String name, Model model) {

        model.addAttribute("search_upcoming", eventService.findNameUpcoming(name));
        model.addAttribute("search_past", eventService.findNamePast(name));

        return "events/index";
    }

    @RequestMapping(value = "/expanded/{id}", method = RequestMethod.GET)
    public String expandEvent(@PathVariable("id") long id, Model model) {

        model.addAttribute("event", eventService.findOne(id));
        return "/events/expanded";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable("id") long id, Model model) {
        Event event = eventService.deleteById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("users", eventService.findAll());
        return "redirect:/events";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String renderUpdateEvent(@PathVariable("id") long id, Model model) {
        model.addAttribute("event", eventService.findById(id));
        Iterable<Venue> allVenues = venueService.findAll();
        model.addAttribute("allVenues", allVenues);
        return "/events/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updateEvent(@PathVariable("id") long id, @RequestBody @Valid @ModelAttribute Event event,
                              BindingResult errors, Model model) {
        eventService.save(event);
        return "redirect:/events";
    }
}



