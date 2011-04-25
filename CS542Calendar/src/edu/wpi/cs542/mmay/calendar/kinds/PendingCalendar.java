package edu.wpi.cs542.mmay.calendar.kinds;

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

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class PendingCalendar {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	//private String nickname;
	//User account;
	
	@Persistent
	//private List<Calendar> pendingCalendars;
	private Set<Key> pendingCalendars;
	
	public PendingCalendar() {
		//this.account = new User("","");
		//this.nickname = "";
		//this.pendingCalendars = new LinkedList<Calendar>();
		this.pendingCalendars = new HashSet<Key>();
	}
	
	public PendingCalendar(User account) {
		//this.account = account;
		//this.nickname = account.getNickname();
		//this.pendingCalendars = new LinkedList<Calendar>();
		this.pendingCalendars = new HashSet<Key>();
	}
	
	/*public User getAccount() {
		return this.account;
	}
	
	public void setAccount(User account) {
		this.account = account;
	}*/
	
	/*public String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}*/
	
	public Key getId() {
		return id;
	}
	
	public void setId(Key key) {
		this.id = key;
	}
	
	/*public void addPendingCalendar(Calendar calendar) {
		this.pendingCalendars.add(calendar);
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
	}*/
	
	public void addPendingCalendar(Key calendarKey) {
		this.pendingCalendars.add(calendarKey);
	}
	
	public void removePendingCalendar(Key calendarKey) {
		this.pendingCalendars.remove(calendarKey);
	}
	
	public Set<Key> getPendingCalendars() {
		return this.pendingCalendars;
	}
	
}
