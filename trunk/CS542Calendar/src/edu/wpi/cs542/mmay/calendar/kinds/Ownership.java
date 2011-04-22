package edu.wpi.cs542.mmay.calendar.kinds;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Ownership {

	@PrimaryKey
	@Persistent
	User account;
	
	@Persistent
	List<Calendar> ownedCalendars;
	
	@Persistent
	List<Calendar> pendingCalendars;
	
	@Persistent
	List<Event> pendingEvents;
	
	public Ownership() {
		this.account = new User("","");
		this.ownedCalendars = new LinkedList<Calendar>();
		this.pendingCalendars = new LinkedList<Calendar>();
		this.pendingEvents = new LinkedList<Event>();
	}
	
	public Ownership(User account) {
		this.account = account;
		this.ownedCalendars = new LinkedList<Calendar>();
		this.pendingCalendars = new LinkedList<Calendar>();
		this.pendingEvents = new LinkedList<Event>();
	}
	
	
	public User getAccount() {
		return account;
	}
	
	public void setAccount(User account) {
		this.account = account;
	}
	
	public void addOwnedCalendar(Calendar calendar) {
		ownedCalendars.add(calendar);
	}
	
	public void removeOwnedCalendar(Key calendarKey) {
		for (Calendar calendar : ownedCalendars) {
			if (calendar.getId().equals(calendarKey)) {
				ownedCalendars.remove(calendar);
				break;
			}
		}
	}
	
	public List<Calendar> getOwnedCalendars() {
		return ownedCalendars;
	}
	
	public void addPendingCalendar(Calendar calendar) {
		pendingCalendars.add(calendar);
	}
	
	public void removePendingCalendar(Key calendarKey) {
		for (Calendar calendar : pendingCalendars) {
			if (calendar.getId().equals(calendarKey)) {
				pendingCalendars.remove(calendar);
				break;
			}
		}
	}
	
	public List<Calendar> getPendingCalendars() {
		return pendingCalendars;
	}
	
	public void addPendingEvent(Event event) {
		pendingEvents.add(event);
	}
	
	public void removePendingEvent(Key eventKey) {
		for (Event event : pendingEvents) {
			if (event.getId().equals(eventKey)) {
				pendingEvents.remove(event);
				break;
			}
		}
	}
	
	public List<Event> getPendingEvents() {
		return pendingEvents;
	}
}
