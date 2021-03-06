package uk.ac.man.cs.eventlite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.man.cs.eventlite.entities.Event;
import uk.ac.man.cs.eventlite.services.EventService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/events", produces = {MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE})
public class EventsControllerApi {

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.GET)
    public Resources<Resource<Event>> getAllEvents() {
        return eventToResource(eventService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Resource<Event> getEvent(@PathVariable("id") long id) {
        Event event = eventService.findOne(id);
        return eventToResource(event);

    }

    private Resource<Event> eventToResource(Event event) {
        Link selfLink = linkTo(EventsControllerApi.class).slash(event.getId()).withSelfRel();
        Link eventLink = linkTo(EventsControllerApi.class).slash(event.getId()).slash("event").withRel("event");
        Link venueLink = linkTo(EventsControllerApi.class).slash(event.getId()).slash("venue").withRel("venue");
        return new Resource<>(event, eventLink, selfLink, venueLink);
    }

    private Resources<Resource<Event>> eventToResource(Iterable<Event> events) {
        Link selfLink = linkTo(methodOn(EventsControllerApi.class).getAllEvents()).withSelfRel();

        List<Resource<Event>> resources = new ArrayList<>();
        for (Event event : events) {
            resources.add(eventToResource(event));
        }

        return new Resources<>(resources, selfLink);
    }
}
