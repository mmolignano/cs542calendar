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

@PersistenceCapable
public class Ownership {

	@PrimaryKey
	@Persistent
	private String nickname;
	
	@Persistent
	private User account;
	
	@Persistent
	private Set<Key> ownedCalendars;
	
	@Persistent
	private Set<Key> pendingCalendars;
		
	@Persistent
	private Set<Key> pendingEvents;
	
	public Ownership() {
		this.nickname = "";
		this.account = new User("","");
		this.ownedCalendars = new HashSet<Key>();
		this.pendingCalendars = new HashSet<Key>();
		this.pendingEvents = new HashSet<Key>();
	}
	
	public Ownership(User account) {
		this.nickname = account.getNickname();
		this.account = account;
		this.ownedCalendars = new HashSet<Key>();
		this.pendingCalendars = new HashSet<Key>();
		this.pendingEvents = new HashSet<Key>();
	}
	
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public User getAccount() {
		return account;
	}
	
	public void setAccount(User account) {
		this.account = account;
	}
	
	public void addOwnedCalendar(Calendar calendar) {
		ownedCalendars.add(calendar.getId());
	}
		
	public void removeOwnedCalendar(Key calendarKey) {
		ownedCalendars.remove(calendarKey);
	}

	public void addOwnedCalendar(Key calendarKey) {
		ownedCalendars.add(calendarKey);
	}
	
	public Set<Key> getOwnedCalendarsKeySet() {
		return ownedCalendars;
	}
	
	public Collection<Calendar> getOwnedCalendars() {
		return DatabaseAccess.getCalendars(getOwnedCalendarsKeySet());
	}
	
	public void addPendingCalendar(Calendar calendar) {
		pendingCalendars.add(calendar.getId());
	}
	
	public void addPendingCalendar(Key calendarKey) {
		pendingCalendars.add(calendarKey);
	}
	
	public void removePendingCalendar(Key calendarKey) {
		pendingCalendars.remove(calendarKey);
	}
	
	public Set<Key> getPendingCalendarKeySet() {
		return pendingCalendars;
	}
	
	public Collection<Calendar> getPendingCalendars() {
		return DatabaseAccess.getCalendars(getPendingCalendarKeySet());
	}
	
	public void addPendingEvent(Event event) {
		pendingEvents.add(event.getId());
	}
	
	public void addPendingEvent(Key evKey) {
		pendingEvents.add(evKey);
	}
	
	public void removePendingEvent(Key eventKey) {
		pendingEvents.remove(eventKey);
	}
	
	public Set<Key> getPendingEventSet() {
		return pendingEvents;
	}
	
	public Collection<Event> getPendingEvents() {
		return DatabaseAccess.getEventsFromCalendar(pendingEvents);
	}
}
