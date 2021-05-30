package Utilities;
import Entities.*;
import Servlets.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageAppointments {
	
	private static int parseDayOfWeek(String day, Locale locale)
            throws ParseException {
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", locale);
        Date date = dayFormat.parse(day);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }
	
	public static void CreateAppointment (User currentUser,Connection Con,HttpServletRequest request,
			int id, int ReserveNum, HttpServletResponse response) 
				throws ClassNotFoundException, SQLException, ParseException, AddressException, MessagingException
	{
		ArrayList<OfficeHour> officehours = new ArrayList <OfficeHour> ();
        
        officehours=OfficeHourGetter.HourGetter(id);
        
       	String SQL3="update officehours set Status = 'Reserved' where OfficeHourID = ?;";
       	PreparedStatement statement3 = Con.prepareStatement(SQL3);  
        statement3.setInt(1, officehours.get(ReserveNum).getID());
        statement3.executeUpdate();
            
        int dayOfOH = parseDayOfWeek(officehours.get(ReserveNum).getDay(), Locale.US);
        LocalDate now = LocalDate.now();
        int DayNow = parseDayOfWeek(now.getDayOfWeek().toString(), Locale.US);           
        int diff=0;
        
        if(dayOfOH>=DayNow)
        	diff=dayOfOH-DayNow;
        else
        	diff=7-(DayNow-dayOfOH);
        
        LocalDate New =now.plusDays(diff);
        java.sql.Date sqlDate = java.sql.Date.valueOf(New);
        
       
        String SQL4="insert into appointments (StudentID,StaffMemberID,OfficeHourID,Date)"
           		+"values(?,?,?,?);";
        PreparedStatement statement4 = Con.prepareStatement(SQL4);  
        statement4.setInt(1, currentUser.getID());
        statement4.setInt(2, id);
        statement4.setInt(3, officehours.get(ReserveNum).getID());
        statement4.setDate(4, sqlDate);
        statement4.executeUpdate();
            
        currentUser.getAppointments().clear();
        ReadAppointment(currentUser);
        
        int index = currentUser.getAppointments().size()-1  ;
        
        String Notify = "You Now Have an Appointment with "+currentUser.getAppointment(index).getOtherUser().getName()+" On "
        		+currentUser.getAppointment(index).getDate()+" "
        		+currentUser.getAppointment(index).getOfficeHour().toString2()+" at "+
        		currentUser.getAppointment(index).getOfficeHour().getLocation()+".";
        
        String Notify2 = "You Now Have an Appointment with "+currentUser.getName()+" On "
        		+currentUser.getAppointment(index).getDate()+" "
        		+currentUser.getAppointment(index).getOfficeHour().toString2()+" at "+
        		currentUser.getAppointment(index).getOfficeHour().getLocation()+".";
        
        Notification.sendNotification(currentUser, Notify, Notify2, id);
        
        String Topic = "Appointment Made";
        mail.sendMail(Notify, currentUser.getEmail(),Topic);
        mail.sendMail(Notify2, currentUser.getAppointment(index).getOtherUser().getEmail(),Topic);
	}
	
	public static void ReadAppointment (User currentUser) throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/officehours";
        String user="root";
        String password="root";
        Connection Con=null;
        Statement Stmt=null;
        boolean flag=false;
        
        Con=DriverManager.getConnection(url, user, password);
        Stmt=Con.createStatement();
        String role="",Subject="";
        int id=0;
        
        
        String SQL = "select * from appointments where StudentID = ? or StaffMemberID = ?;";
		PreparedStatement statement = Con.prepareStatement(SQL);
		statement.setInt(1, currentUser.getID());
		statement.setInt(2, currentUser.getID());
		ResultSet RS=statement.executeQuery();
		while (RS.next())
		{
			Appointment newApp = new Appointment ();
			newApp.setAppointmentID(RS.getInt("AppointmentID"));
			newApp.setDate(RS.getDate("Date"));
			
			
			String SQL1="select * from users where UserID = ?";
			PreparedStatement statement1 = Con.prepareStatement(SQL1);
			if(currentUser.getRole().equalsIgnoreCase("Student"))
				statement1.setInt(1, RS.getInt("StaffMemberID"));
			else
				statement1.setInt(1,RS.getInt("StudentID"));
			ResultSet rs1=statement1.executeQuery();
			rs1.next();
			User newUser= new User ();
			newUser.setUser(rs1.getString("Name"), rs1.getInt("UserID"), rs1.getString("Password"),
					rs1.getString("Email"), rs1.getString("Department"), rs1.getString("Role"),rs1.getString("AcademicRank"));
			newApp.setOtherUser(newUser);
			
			
			String SQL2 = "select * from officehours where OfficeHourID = ?";
        	PreparedStatement statement2 = Con.prepareStatement(SQL2);
			statement2.setInt(1, RS.getInt("OfficeHourID"));
			ResultSet rs2=statement2.executeQuery();
			rs2.next();
			OfficeHour newHour = new OfficeHour ();
			newHour.setOfficeHour(rs2.getInt("OfficeHourID"), rs2.getString("Day"), rs2.getTime("From_Time"),
					rs2.getTime("To_Time"),rs2.getInt("UserID"), rs2.getString("Status"),rs2.getString("Location"));
			newApp.setOfficeHour(newHour);
			
			currentUser.setAppointment(newApp);
		}
        	
        	
        
	}
	
	public static void DeleteAppointment (User currentUser,Connection Con,HttpServletRequest request,
			int AppID, HttpServletResponse response) 
				throws ClassNotFoundException, SQLException, ParseException, AddressException, MessagingException
	{
		 int index=0;
         
         for(int i=0;i<currentUser.getAppointments().size();i++)
         {
         	if(currentUser.getAppointment(i).getAppointmentID()==AppID)
         		index=i;     		
         }
         
         int officeHourID = currentUser.getAppointment(index).getOfficeHour().getID();
         int otherID=currentUser.getAppointment(index).getOtherUser().getID();
         
         String SQL = "delete from appointments where AppointmentID=? ";
         PreparedStatement statement = Con.prepareStatement(SQL);
			statement.setInt(1, AppID);
			statement.executeUpdate();
			
			String SQL2 ="update officehours set Status = 'Available' where OfficeHourID = ?;";
			PreparedStatement statement2 = Con.prepareStatement(SQL2);
			statement2.setInt(1, officeHourID);
			statement2.executeUpdate();
         
         String Notify = "Your Appointment with "+currentUser.getAppointment(index).getOtherUser().getName()+" On "
         		 +currentUser.getAppointment(index).getDate()+" "
        		 +currentUser.getAppointment(index).getOfficeHour().toString2()+" Has Been Cancelled.";
         
         String Notify2 = "Your Appointment with "+currentUser.getName()+" On "
         		+currentUser.getAppointment(index).getOfficeHour().toString2()+" Has Been Cancelled.";
         
         Notification.sendNotification(currentUser, Notify, Notify2, otherID);
         
         currentUser.getNotifications().add(Notify);
         
         String Topic = "Appointment Cancellation";
         mail.sendMail(Notify, currentUser.getEmail(),Topic);
         mail.sendMail(Notify2, currentUser.getAppointment(index).getOtherUser().getEmail(),Topic);
         
         currentUser.getAppointments().remove(index);
         
	}
	

}
