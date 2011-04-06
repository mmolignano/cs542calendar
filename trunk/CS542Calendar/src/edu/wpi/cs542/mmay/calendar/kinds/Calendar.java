package edu.wpi.cs542.mmay.calendar.kinds;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * 
 * @author Andrew Yee
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Calendar {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key id;
	
	@Persistent
	String name;
	
	@Persistent
	String owner;
	
	@Persistent
	List<Event> events;
	
	public Calendar() {
		this.name = "";
		this.owner = "";
		events = new LinkedList<Event>();
	}
	
	public Calendar(String name, String owner) {
		this.name = name;
		this.owner = owner;
		events = new LinkedList<Event>();
	}

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	public void removeEvent(Event event) {
		events.remove(event);
	}
	
	public List<Event> getEvents() {
		return events;
	}
	
}
