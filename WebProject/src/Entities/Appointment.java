package Entities;

import java.sql.Date;

public class Appointment {
	private int appointmentID;
	private User otherUser = new User();
	private OfficeHour OfficeHour = new OfficeHour ();
	private Date date;
	
	
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getAppointmentID() {
		return appointmentID;
	}
	public void setAppointmentID(int appointmentID) {
		this.appointmentID = appointmentID;
	}
	public User getOtherUser() {
		return otherUser;
	}
	public void setOtherUser(User otherUser) {
		this.otherUser = otherUser;
	}
	public OfficeHour getOfficeHour() {
		return OfficeHour;
	}
	public void setOfficeHour(OfficeHour officeHour) {
		OfficeHour = officeHour;
	}
		
	
	

}
