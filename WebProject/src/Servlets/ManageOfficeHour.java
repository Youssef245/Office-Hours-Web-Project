package Servlets;
import Entities.*;
import Utilities.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ManageOfficeHour
 */
@WebServlet("/ManageOfficeHour")
public class ManageOfficeHour extends HttpServlet {
	/**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
	 * @throws SQLException 
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws MessagingException 
     * @throws AddressException 
     */
	
	private boolean validateOfficeHour (String day,String from_Time , String To_Time,ArrayList<OfficeHour> OfficeHours,String crud,int index)
	{
		boolean flag=false;
		for (DayOfWeek d : DayOfWeek.values())
			if(d.name().equalsIgnoreCase(day)) {
				flag=true;
				break;}
		String f;
		String t;
		if(crud.equalsIgnoreCase("Add"))
		{
			for (int i=0 ; i<OfficeHours.size();i++)
			{
				f=OfficeHours.get(i).getFromTime().toString();
				t=OfficeHours.get(i).getToTime().toString();
				if(day.equalsIgnoreCase(OfficeHours.get(i).getDay()))
				{
					if(from_Time.compareToIgnoreCase(f)>=0&&from_Time.compareToIgnoreCase(t)<0) {
						flag=false;
						break;}
				}
			}
		}
		{
			for (int i=0 ; i<OfficeHours.size();i++)
			{
				f=OfficeHours.get(i).getFromTime().toString();
				t=OfficeHours.get(i).getToTime().toString();
				if(day.equalsIgnoreCase(OfficeHours.get(i).getDay())&&index!=i)
				{
					if(from_Time.compareToIgnoreCase(f)>=0&&from_Time.compareToIgnoreCase(t)<0) {
						flag=false;
						break;}
				}
			}
		}
		if(from_Time.compareToIgnoreCase(To_Time)>=0)
			flag=false;
		return flag;
		
	}
	
	
	private boolean Add (User currentUser,Connection Con,HttpServletRequest request, HttpServletResponse response,ArrayList<OfficeHour> OfficeHours) 
			throws SQLException
	{
		String weekDay = request.getParameter("weekday");
		String from = request.getParameter("From");
		String to = request.getParameter("To");
		String location = request.getParameter("Location");
		String from_fullTime = from+":00";
		java.sql.Time f_time = java.sql.Time.valueOf(from_fullTime);
		String to_fullTime=to+":00";
		java.sql.Time t_time = java.sql.Time.valueOf(to_fullTime);
		
		boolean flag = validateOfficeHour(weekDay, from_fullTime, to_fullTime, OfficeHours,"Add",0);
		if (flag)
		{
			String SQL = "insert into officehours (Day,From_Time,To_Time,UserID,Location)"
				+"values(?,?,?,?,?);";
			PreparedStatement statement = Con.prepareStatement(SQL);
			statement.setString(1, weekDay);
			statement.setTime(2, f_time);
			statement.setTime(3, t_time);
			statement.setInt(4, currentUser.getID());
			statement.setString(5, location);
			statement.executeUpdate();
		}
		
		return flag;
		
	}
	
	private boolean Update (Connection Con,HttpServletRequest request, HttpServletResponse response,ArrayList<OfficeHour> OfficeHours) 
			throws SQLException
	{
		
		String weekDay = request.getParameter("weekday");
		String from = request.getParameter("From");
		String location = request.getParameter("Location");
		String to = request.getParameter("To");
		String from_fullTime = from+":00";
		java.sql.Time f_time = java.sql.Time.valueOf(from_fullTime);
		String to_fullTime=to+":00";
		java.sql.Time t_time = java.sql.Time.valueOf(to_fullTime);
		int i = Integer.parseInt(request.getParameter("index"));
		
		boolean flag = validateOfficeHour(weekDay, from_fullTime, to_fullTime, OfficeHours,"Update",i);
		
		if(flag)
		{			
			
			String SQL = "update officehours set Day=?,From_Time=?,To_Time=?,Location=?"
					+"where OfficeHourID=?;";
			PreparedStatement statement = Con.prepareStatement(SQL);
			statement.setString(1, weekDay);
			statement.setTime(2, f_time);
			statement.setTime(3, t_time);
			statement.setString(4, location);
			statement.setInt(5, OfficeHours.get(i).getID());
			statement.executeUpdate();
			
			OfficeHours.get(i).setDay(weekDay);
			OfficeHours.get(i).setFromTime(f_time);
			OfficeHours.get(i).setToTime(t_time);
		}
		
		return flag;
	}
	
	private void Delete (User currentUser, Connection Con,HttpServletRequest request, HttpServletResponse response,ArrayList<OfficeHour> OfficeHours) 
			throws SQLException, AddressException, ClassNotFoundException, ParseException, MessagingException
	{
		
		int i = Integer.parseInt(request.getParameter("index"));
		
		String SQL2 = "select * from appointments where OfficeHourID=?;";
		PreparedStatement statement2 = Con.prepareStatement(SQL2);
		statement2.setInt(1, OfficeHours.get(i).getID());
		ResultSet RS=statement2.executeQuery();
		while (RS.next())
		{
			ManageAppointments.DeleteAppointment(currentUser, Con, request, RS.getInt("AppointmentID"), response);
		}
		
		
		String SQL = "delete from officehours where OfficeHourID=?;";
		PreparedStatement statement = Con.prepareStatement(SQL);
		statement.setInt(1, OfficeHours.get(i).getID());
		statement.executeUpdate();
		
		OfficeHours.remove(i);
	}
	
	
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, AddressException, MessagingException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/officehours";
            String user="root";
            String password="root";
            Connection Con=null;
            Statement Stmt=null;
            boolean flag=true;
            
            Con=DriverManager.getConnection(url, user, password);
            Stmt=Con.createStatement();
            
            HttpSession session=request.getSession();
            User currentUser = (User) session.getAttribute("SessionUser");
            ArrayList<OfficeHour> OfficeHours = (ArrayList<OfficeHour>) session.getAttribute("officehours");
            
            String CRUD = request.getParameter("crud");
            if(CRUD.equalsIgnoreCase("Add"))
            {
            	flag = Add(currentUser, Con, request, response,OfficeHours);
            	OfficeHours=OfficeHourGetter.HourGetter(currentUser.getID());
            }
            else if(CRUD.equalsIgnoreCase("Update"))
            {
            	flag=Update(Con, request, response, OfficeHours);
            }
            else
            {
            	Delete(currentUser,Con, request, response, OfficeHours);
            }
            
            session.setAttribute("officehours", OfficeHours);
            
            String Feedback=(String) session.getAttribute("Feedback");            
            
            if(!flag) {            	
            	Feedback="Fail";            	
                }
            else
            {
            	Feedback="Success";
            }
            
            session.setAttribute("Feedback", Feedback);
            
            response.sendRedirect("ManageOfficeHours.jsp");
            
            
            
            
    }   catch (ClassNotFoundException ex) {
            Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (ServletException | IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (ServletException | IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
