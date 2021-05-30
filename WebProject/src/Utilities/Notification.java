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

public class Notification {
	
	public static void sendNotification (User Reciever,String Content1,String Content2,int otherID) throws ClassNotFoundException, SQLException
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
                
        String SQL = "insert into notifications(ReceiverID,Content)"
        		+ "values (?,?)";
        PreparedStatement statement = Con.prepareStatement(SQL);  
        statement.setInt(1, Reciever.getID());
        statement.setString(2, Content1);
        statement.executeUpdate();
        PreparedStatement statement2 = Con.prepareStatement(SQL);  
        statement2.setInt(1, otherID);
        statement2.setString(2, Content2);
        statement2.executeUpdate();
        Reciever.getNotifications().add(Content1);
        
        
	}
	
	public static void sendSignleNotification (int Reciever,String Content1) throws ClassNotFoundException, SQLException
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
                
        String SQL = "insert into notifications(ReceiverID,Content)"
        		+ "values (?,?)";
        PreparedStatement statement = Con.prepareStatement(SQL);  
        statement.setInt(1, Reciever);
        statement.setString(2, Content1);
        statement.executeUpdate();
        
        
	}

}
