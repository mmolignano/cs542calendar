package edu.wpi.cs542.mmay.calendar.kinds;

import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

@PersistenceCapable
public class PendingCalendar {

	@PrimaryKey
	@Persistent
	private String nickname;
	//User account;
	
	@Persistent
	private List<Calendar> pendingCalendars;
	
	public PendingCalendar() {
		//this.account = new User("","");
		this.nickname = "";
		this.pendingCalendars = new LinkedList<Calendar>();
	}
	
	public PendingCalendar(User account) {
		//this.account = account;
		this.nickname = account.getNickname();
		this.pendingCalendars = new LinkedList<Calendar>();
	}
	
	/*public User getAccount() {
		return this.account;
	}
	
	public void setAccount(User account) {
		this.account = account;
	}*/
	
	public String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void addPendingCalendar(Calendar calendar) {
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
	}
	
}
