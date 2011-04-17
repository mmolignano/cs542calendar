package edu.wpi.cs542.mmay.calendar.kinds;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

/**
 * 
 * @author Andrew Yee, Mike Molignano
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
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
	private List<Event> events;
	
	public Calendar() {
		this.name = "";
		this.owners = new LinkedList<User>();
		events = new LinkedList<Event>();
	}
	
	public Calendar(String name, User user) {
		this.name = name;
		this.owners = new LinkedList<User>();
		this.owners.add(user);
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
		events.add(event);
	}
	
	public void removeEvent(Event event) {
		events.remove(event);
	}
	
	public List<Event> getEvents() {
		return events;
	}
	
}
