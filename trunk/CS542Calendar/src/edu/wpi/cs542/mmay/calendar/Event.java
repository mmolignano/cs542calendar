package edu.wpi.cs542.mmay.calendar;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.Date;

import com.google.appengine.api.datastore.Key;

/**
 * Entity representing a calendar event
 * 
 * @author Andrew Yee
 * @author Mike Molignano
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Event {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Key id; //PRIMARY KEY ('id') 'id' varchar(255) NOT NULL
	
	@Persistent
	String eventName;	
	
	@Persistent
	Date eventDate;
	
	@Persistent
	String location;
	
	@Persistent
	String description;

	public Event() {
		eventName="Default Event";
		eventDate = new Date();
		location="Default Location";
		description="Default Description";
	}
	
	public Event(String eventName, Date eventDate, String location, String description) {
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.location = location;
		this.description = description;
	}
	
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	
}
