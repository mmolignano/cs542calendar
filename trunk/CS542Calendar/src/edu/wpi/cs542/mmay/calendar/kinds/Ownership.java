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
	
	public Ownership() {
		this.account = new User("","");
		this.ownedCalendars = new LinkedList<Calendar>(); 
	}
	
	public Ownership(User account) {
		this.account = account;
		this.ownedCalendars = new LinkedList<Calendar>();
	}
	
	
	public User getAccount() {
		return account;
	}
	
	public void setAccount(User account) {
		this.account = account;
	}
	
	public void addCalendar(Calendar calendar) {
		ownedCalendars.add(calendar);
	}
	
	public void removeCalendar(Key calendarKey) {
		for (Calendar calendar : ownedCalendars) {
			if (calendar.getId().equals(calendarKey)) {
				ownedCalendars.remove(calendar);
				break;
			}
		}
	}
}
