package uk.ac.man.cs.eventlite.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "venues")
public class Venue {

	@Id
	@GeneratedValue
	private long id;

	@NotEmpty(message = "The Venue name cannot be empty.")
	@Size(max = 256, message = "The venue name must have 256 characters or less	.")
	private String name;

	@OneToMany
	private Set<Event> events;

	@Positive(message = "Venue's capacity must be positive")
	private int capacity;

	@NotEmpty(message = "The Venue address cannot be empty.")
	@Size(max = 300, message = "The venue address must have 300 characters or less	.")
	private String address;

	private double lng;
	
	private double lat;

	public Venue(String name, int capacity, String address) {
		setName(name);
		setCapacity(capacity);
		setAddress(address);
	}

	public Venue() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public double getLng() {
		return this.lng;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public double getLat() {
		return this.lat;
	}
}
