package Utilities;
import Entities.*;
import Servlets.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class OfficeHourGetter {

	public static ArrayList<OfficeHour> HourGetter (int id) throws ClassNotFoundException, SQLException
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
         
         ArrayList<OfficeHour> officehours = new ArrayList <OfficeHour> ();
         
         String SQL = "select * from officehours where UserID=?";
         PreparedStatement statement = Con.prepareStatement(SQL);  
         statement.setInt(1, id);
         ResultSet rs=statement.executeQuery();
         while (rs.next())
         {
         	OfficeHour newHour = new OfficeHour();
         	newHour.setDay(rs.getString("Day"));
         	newHour.setFromTime(rs.getTime("From_Time"));
         	newHour.setToTime(rs.getTime("To_Time"));
         	newHour.setID(rs.getInt("OfficeHourID"));
         	newHour.setStatus(rs.getString("Status"));
         	newHour.setLocation(rs.getString("Location"));
         	newHour.setUserID(id);
         	officehours.add(newHour);
         }
         
         return officehours;
	}
}
