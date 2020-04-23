package uk.ac.man.cs.eventlite.entities;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "venues")
public class Venue {

	@Id
	@GeneratedValue
	private long id;

	private String name;

	@OneToMany
	private Set<Event> events;
	
	private int capacity;

	private String address;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Venue)) return false;
		Venue venue = (Venue) o;
		return id == venue.id &&
				capacity == venue.capacity &&
				Objects.equals(name, venue.name) &&
				Objects.equals(events, venue.events) &&
				Objects.equals(address, venue.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, events, capacity, address);
	}
}
