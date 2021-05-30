package Entities;

import java.util.ArrayList;

public class User {
	private String name;
	private int ID;
	private String Password;
	private String email;
	private String Department;
	private String role;
	private String Rank;
	
	ArrayList <Appointment> appointments = new ArrayList <Appointment>();
	ArrayList <Message> messages = new ArrayList <Message>();
	ArrayList <String> notifications = new ArrayList <String>();
	ArrayList <Appointment> History = new ArrayList <Appointment>();
	
	
	
	
	public String getRank() {
		return Rank;
	}

	public void setRank(String rank) {
		Rank = rank;
	}

	public ArrayList<Appointment> getHistory() {
		return History;
	}

	public ArrayList<String> getNotifications() {
		return notifications;
	}

	public void setMessage (Message a)
	{
		messages.add(a);
	}
	
	public Message getMessage (int i)
	{
		return messages.get(i);
	}
	
	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setAppointment (Appointment a)
	{
		appointments.add(a);
	}
	
	public Appointment getAppointment (int i)
	{
		return appointments.get(i);
	}
	
	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}

	public void setUser(String name, int iD, String password, String email, String department, String role, String Rank) {
		this.name = name;
		ID = iD;
		Password = password;
		this.email = email;
		Department = department;
		this.role = role;
		this.Rank = Rank;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	
	
}
