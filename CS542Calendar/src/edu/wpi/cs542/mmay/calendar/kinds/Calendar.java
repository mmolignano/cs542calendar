package edu.wpi.cs542.mmay.calendar.kinds;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import edu.wpi.cs542.mmay.calendar.DatabaseAccess;

/**
 * 
 * @author Andrew Yee, Mike Molignano
 *
 */
@PersistenceCapable
public class Calendar {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private String name;
	
	@Persistent
	private List<User> owners;
	
	@Persistent
	private String description;
	
	@Persistent
	//private List<Event> events;
	private Set<Key> events;
	
	public Calendar() {
		this.name = "";
		this.owners = new LinkedList<User>();
		//events = new LinkedList<Event>();
		events = new HashSet<Key>();
	}
	
	public Calendar(String name, User user) {
		this.name = name;
		this.owners = new LinkedList<User>();
		this.owners.add(user);
		//events = new LinkedList<Event>();
		events = new HashSet<Key>();
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

	public List<User> getOwners() {
		return owners;
	}

	public void addOwner(User user) {
		this.owners.add(user);
	}
	
	public void removeOwner(User user) {
		this.owners.remove(user);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addEvent(Event event) {
		//events.add(event);
		events.add(event.getId());
	}
	
	public void addEvent(Key eventKey) {
		//events.add(event);
		events.add(eventKey);
	}
	
	public void removeEvent(Event event) {
		events.remove(event.getId());
	}
	
	public void removeEvent(Key eventKey) {
		events.remove(eventKey);
	}
	
	public Collection<Event> getEvents() {
		return DatabaseAccess.getEventsFromCalendar(events);
	}
	
	public Set<Key> getEventSet() {
		return events;
	}
	
}
