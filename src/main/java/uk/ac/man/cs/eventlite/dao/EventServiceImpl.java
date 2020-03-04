package uk.ac.man.cs.eventlite.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import uk.ac.man.cs.eventlite.entities.Event;

@Service
public class EventServiceImpl implements EventService {

	private final static Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
	@Autowired
	EventRepository eventRepository;
	 
	private final static String DATA = "data/events.json";

	@Override
	public long count() {
		return eventRepository.count();
	}
	@Override
	public Iterable<Event> findAll() {
		return eventRepository.findByOrderByDateAscTimeAsc();
	}

	@Override
	public Event save(Event event) {
		// TODO Auto-generated method stub
		return eventRepository.save(event);
	}
	

	

}