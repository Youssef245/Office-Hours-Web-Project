package Utilities;
import Entities.*;
import Servlets.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class OnDayNotification {
	
	public static void sendOnDay (User currentUser) throws ClassNotFoundException, SQLException, AddressException, MessagingException 
	{	
		Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/officehours";
        String user="root";
        String password="root";
        Connection Con=null;
        Statement Stmt=null;
        
        Con=DriverManager.getConnection(url, user, password);
        Stmt=Con.createStatement();
		String now ;
        now=java.time.LocalDate.now().toString();
		for(int i=0;i<currentUser.getAppointments().size();i++)
		{
			
			String SQL="select * from appointments where AppointmentID = ?";
			PreparedStatement statement = Con.prepareStatement(SQL); 
			statement.setInt(1, currentUser.getAppointment(i).getAppointmentID());
			ResultSet RS=statement.executeQuery();
			RS.next();
			String condition=RS.getString("OnDayNotification");
			if(!condition.equalsIgnoreCase("Sent")) {
				System.out.println(currentUser.getAppointment(i).getDate());
				if(currentUser.getAppointment(i).getDate().toString().equalsIgnoreCase(now))
				{
					int id=currentUser.getAppointment(i).getOtherUser().getID();
					String Notify="Remember You Have an Appointment with "+currentUser.getAppointment(i).getOtherUser().getName()+" "
							+currentUser.getAppointment(i).getOfficeHour().toString2()+" Today.";
					
					
					String Notify2 = "Remember You Have an Appointment with "+currentUser.getName()+" "
		            		+currentUser.getAppointment(i).getOfficeHour().toString2()+" Today.";
					
					
					Notification.sendNotification(currentUser, Notify, Notify2, id);
					
		            
		            String Topic = "Reminder";
		            mail.sendMail(Notify, currentUser.getEmail(),Topic);
		            mail.sendMail(Notify2, currentUser.getAppointment(i).getOtherUser().getEmail(),Topic);
		            
		            
		            String SQL2 = "update appointments set OnDayNotification = 'Sent' where AppointmentID = ?;";
		            PreparedStatement statement2 = Con.prepareStatement(SQL2); 
					statement2.setInt(1, currentUser.getAppointment(i).getAppointmentID());
					statement2.executeUpdate();
				}
			}
		}
	}

}
