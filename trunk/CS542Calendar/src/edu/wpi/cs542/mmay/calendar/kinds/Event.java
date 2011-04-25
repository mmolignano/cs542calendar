package edu.wpi.cs542.mmay.calendar.kinds;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.util.Date;

/**
 * Kind/Entity representing a calendar event
 * 
 * @author Andrew Yee
 * @author Mike Molignano
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Event {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private String eventName;	
	
	@Persistent
	private Date startDate;
	
	@Persistent
	private String location;
	
	@Persistent
	private String description;
	
	public Event() {
		eventName="Default Event";
		startDate = new Date();
		location="Default Location";
		description="Default Description";
	}
	
	public Event(String eventName, Date startDate, String location, String description) {
		this.eventName = eventName;
		this.startDate = startDate;
		this.location = location;
		this.description = description;
	}

	public Key getId() {
		return id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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
	
	public boolean equals(Object o) {
		if(this.getClass().equals(o.getClass())) {
			if (this.id.equals(((Event)o).getId())) {
				return true;
			}
		}
		return false;
	}
}