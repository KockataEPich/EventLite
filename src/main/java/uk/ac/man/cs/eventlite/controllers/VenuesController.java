package uk.ac.man.cs.eventlite.controllers;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.man.cs.eventlite.entities.Event;

import uk.ac.man.cs.eventlite.entities.Venue;
import uk.ac.man.cs.eventlite.services.EventService;
import uk.ac.man.cs.eventlite.services.VenueService;

@Controller
@RequestMapping(value = "/venues", produces = {MediaType.TEXT_HTML_VALUE})
public class VenuesController {
    @Autowired
    private EventService eventService;

    @Autowired
    private VenueService venueService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllVenues(Model model) {

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
			BindingResult errors, Model model, RedirectAttributes redirectAttrs) throws InterruptedException {
        model.addAttribute("venues", venueService.findAll());

		if (errors.hasErrors()) {
			model.addAttribute("venue", venue);
			return "venues/new";
		}
		
		//save event passed over by the post request

		Point loc = Point.fromLngLat(venue.getLng(), venue.getLat());
		
		MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
				.accessToken("pk.eyJ1IjoiY2hyaXNuaWsiLCJhIjoiY2s5MDJ0ZGNvMHhmNTNsdGE1aGNzODhhZiJ9.cDsPoQeKp5JCO4VzzL9lxA")
				.query(venue.getAddress())
				.build();
		mapboxGeocoding.enqueueCall(new Callback<GeocodingResponse>() {
			
			@Override
			public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
				// TODO Auto-generated method stub
				List<CarmenFeature> results = response.body().features();			
				
				if (results.size() > 0) {
					 
				  // Log the first results Point.
				  Point firstResultPoint = results.get(0).center();
				  venue.setLng(firstResultPoint.longitude());
				  venue.setLat(firstResultPoint.latitude());

				} else {
		 
				  // No result for your request were found.
		 
				}
			}
			
			@Override
			public void onFailure(Call<GeocodingResponse> call, Throwable t) {
				// TODO Auto-generated method stub
				mapboxGeocoding.cancelCall();
			}
		});
		Thread.sleep(1000L);

		
		venueService.save(venue);
		redirectAttrs.addFlashAttribute("ok_message", "New Venue added.");
		return "redirect:/venues";
	}
	
	@RequestMapping(value= "/search", method= RequestMethod.GET)
	public String findVenueByName(@RequestParam (value= "search", required= false) String name, Model model) {
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
}

