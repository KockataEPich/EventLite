package uk.ac.man.cs.eventlite.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="events")
public class Event {
	@Id
	@GeneratedValue
	private long id;

	@NotNull(message = "The event date cannot be empty.")
	@Future
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime time;

	@NotEmpty(message = "The event name cannot be empty.")
	@Size(max = 256, message = "The event name must have 256 characters or less	.")
	private String name;
	
	@Size(max = 500, message = "The event description must have 500 characters or less	.")
	private String description;
	
	@NotNull(message = "The event venue cannot be empty.")
    @ManyToOne
    private Venue venue;
	

	public Event(LocalDate date, LocalTime time, String name, Venue v) {
		setName(name);
		setDate(date);
		setTime(time);
		setVenue(v);
	}
	
	public Event() {
		//Default Const
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}
	
	public String getDescription() {
		return description;
	}
//	
	public void setVenue(Venue venue) {
		this.venue = venue;
	}
}
