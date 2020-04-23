package uk.ac.man.cs.eventlite.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uk.ac.man.cs.eventlite.entities.Event;
import uk.ac.man.cs.eventlite.entities.Venue;
import uk.ac.man.cs.eventlite.services.EventService;
import uk.ac.man.cs.eventlite.services.VenueService;

import java.util.Optional;

@Controller
@RequestMapping(value = "/venues", produces = {MediaType.TEXT_HTML_VALUE})
public class VenuesController {
    @Autowired
    private EventService eventService;

    @Autowired
    private VenueService venueService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllVenues(Model model) {

        model.addAttribute("venues", venueService.findAll());

        return "venues/index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newVenue(Model model) {
        if (!model.containsAttribute("venue")) {
            model.addAttribute("venue", new Venue());
        }

        return "venues/new";
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createVenue(@RequestBody @Valid @ModelAttribute("venue") Venue venue,
                              BindingResult errors, Model model, RedirectAttributes redirectAttrs) {

        if (errors.hasErrors()) {
            model.addAttribute("venue", venue);
            return "venues/new";
        }

        //save event passed over by the post request
        venueService.save(venue);
        redirectAttrs.addFlashAttribute("ok_message", "New Venue added.");
        return "redirect:/venues";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String findVenueByName(@RequestParam(value = "search", required = false) String name, Model model) {
        model.addAttribute("search", venueService.findByName(name));
        model.addAttribute("search", venueService.findByNameContaining(name));
        return "venues/index";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String renderUpdateVenue(@PathVariable("id") long id, Model model) {
        model.addAttribute("venue", venueService.findById(id));
        Iterable<Event> allEvents = eventService.findAll();
        model.addAttribute("allEvents", allEvents);
        return "/venues/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updateVenue(@PathVariable("id") long id, @RequestBody @Valid @ModelAttribute Venue venue,
                              BindingResult errors, Model model) {
        venueService.save(venue);
        return "redirect:/venues";
    }


    @GetMapping("/delete/{id}")
    public String deleteVenue(@PathVariable("id") long id, Model model) {
        Optional<Venue> venue = venueService.findById(id);
        venue.ifPresent(v -> {
            if(venueService.isDeletable(v)){
                venueService.deleteById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid venue ID:" + id));
            }
        });
        model.addAttribute("users", venueService.findAll());
        return "redirect:/venues";
    }
}

