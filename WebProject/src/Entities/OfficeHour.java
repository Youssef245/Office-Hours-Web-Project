package Entities;

import java.sql.Date;
import java.sql.Time;


public class OfficeHour {

	private int ID;
	private String day;
	private Time fromTime;
	private Time toTime;
	private int UserID;
	private String Status;
	private String Location;
	
	public  void setOfficeHour(int iD, String day, Time fromTime, Time toTime, int userID, String status,String Location) {
		ID = iD;
		this.day = day;
		this.fromTime = fromTime;
		this.toTime = toTime;
		UserID = userID;
		Status = status;
		this.Location=Location;
	}
	
	
	
	public String getLocation() {
		return Location;
	}



	public void setLocation(String location) {
		Location = location;
	}



	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}




	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}

	public Time getFromTime() {
		return fromTime;
	}

	public void setFromTime(Time fromTime) {
		this.fromTime = fromTime;
	}

	public Time getToTime() {
		return toTime;
	}

	public void setToTime(Time toTime) {
		this.toTime = toTime;
	}

	public String toString () {
		
		String temp=day;
		int rest = 9-temp.length();
		if(temp.length()!=9)
			for(int i=0;i<rest;i++)
				temp+=" ";
		
		 return temp + " from " +fromTime.toString()+" To "+toTime.toString()+" "+Status;
		
	}
	public String toString2 () {
		
		return day + " from " +fromTime.toString()+" To "+toTime.toString();
		
	}
	
}
