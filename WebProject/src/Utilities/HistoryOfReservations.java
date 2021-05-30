package Utilities;
import Entities.*;
import Servlets.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HistoryOfReservations {
	
	public static void getHistory (User currentUser) throws SQLException, ClassNotFoundException
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
        
        
        String SQL = "select * from history where StudentID = ? or StaffMemberID = ?;";
		PreparedStatement statement = Con.prepareStatement(SQL);
		statement.setInt(1, currentUser.getID());
		statement.setInt(2, currentUser.getID());
		ResultSet RS=statement.executeQuery();
		while (RS.next())
		{
			Appointment newApp = new Appointment ();
			newApp.setAppointmentID(RS.getInt("HistoryID"));
			newApp.setDate(RS.getDate("Date"));
			
			
			String tempDate = RS.getDate("Date").toString();
		    LocalDate date = LocalDate.parse(tempDate);
		    DayOfWeek day = date.getDayOfWeek();
			
			OfficeHour newHour = new OfficeHour ();
			newHour.setOfficeHour(0, day.toString() , RS.getTime("From_Time"),
					RS.getTime("To_Time"),RS.getInt("StaffMemberID"), "X",RS.getString("Location"));
			newApp.setOfficeHour(newHour);
			
			String SQL1="select * from users where UserID = ?";
			PreparedStatement statement1 = Con.prepareStatement(SQL1);
			statement1.setInt(1,RS.getInt("StudentID"));
			ResultSet rs1=statement1.executeQuery();
			rs1.next();
			User newUser= new User ();
			newUser.setUser(rs1.getString("Name"), rs1.getInt("UserID"), rs1.getString("Password"),
					rs1.getString("Email"), rs1.getString("Department"), "Student" ,"--");
			newApp.setOtherUser(newUser);
			
			
			currentUser.getHistory().add(newApp);
		}
        	
        	
        
	}
	
	public static void checkApp() throws ClassNotFoundException, SQLException {
		
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
        int Appid=0;

		String SQL = "select * from appointments ";
    	PreparedStatement statement = Con.prepareStatement(SQL);
		ResultSet rs=statement.executeQuery();
		while(rs.next()) {
			 Date date1= rs.getDate("Date");
			 Date now  = new Date ();			 			
			 long diffInMillies =(now.getTime() - date1.getTime());
			 long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			 if(diff>0) {
				 
				 Appid = rs.getInt("AppointmentID");
				 int StudentID=rs.getInt("StudentID");
				 int StaffMemberID=rs.getInt("StaffMemberID");
				 int officeHourID=rs.getInt("OfficeHourID");
				 java.sql.Date date = rs.getDate("Date");
				 
				 
				 String SQL1 = "select * from officehours where OfficeHourID=?"; //To Get The Time Of The Office Hour Of The Meeting.
				 PreparedStatement statement1 = Con.prepareStatement(SQL1);
				 statement1.setInt(1, officeHourID);
				 ResultSet rs1=statement1.executeQuery();
				 rs1.next();
				 
				 String SQL2 = "insert into history (StudentID,StaffMemberID,From_Time,To_Time,Date,Location)"
			     +"values(?,?,?,?,?,?)";
			     PreparedStatement statement2 = Con.prepareStatement(SQL2);
			     statement2.setInt(1,StudentID);
			     statement2.setInt(2,StaffMemberID);
			     statement2.setTime(3,rs1.getTime("From_Time"));
			     statement2.setTime(4,rs1.getTime("To_Time"));
			     statement2.setDate(5,date);
			     statement2.setString(6, rs1.getString("Location"));
			     statement2.executeUpdate();
			     
			     String SQL3 = "delete from appointments where AppointmentID = ?";
			     PreparedStatement statement3 = Con.prepareStatement(SQL3);
			     statement3.setInt(1,Appid);
			     statement3.executeUpdate();
			     
			     String SQL4 ="update officehours set Status = 'Available' where OfficeHourID = ?;";
				 PreparedStatement statement4 = Con.prepareStatement(SQL4);
				 statement4.setInt(1, officeHourID);
				 statement4.executeUpdate();
				 
			 }
			 
			
	}
	
	}

}
