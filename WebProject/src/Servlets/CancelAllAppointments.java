package Servlets;
import Entities.*;
import Utilities.*;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class CancelAllAppointments
 */
@WebServlet("/CancelAllAppointments")
public class CancelAllAppointments extends HttpServlet {
	 /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
	 * @throws MessagingException 
	 * @throws AddressException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, AddressException, MessagingException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
        	
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/officehours";
            String user="root";
            String password="root";
            Connection Con=null;
            Statement Stmt=null;
            
            Con=DriverManager.getConnection(url, user, password);
            Stmt=Con.createStatement();
            String role="",Subject="";
            HttpSession session=request.getSession(); 
            User currentUser = (User) session.getAttribute("SessionUser");
                                    
            int AppID = Integer.parseInt(request.getParameter("val"));
            int index=0;
            
            for(int i=0;i<currentUser.getAppointments().size();i++)
            {
            	if(currentUser.getAppointment(i).getAppointmentID()==AppID)
            		index=i;     		
            }
            
            String SQL = "delete from appointments where Date=? ";
            PreparedStatement statement = Con.prepareStatement(SQL);
			statement.setDate(1,currentUser.getAppointment(index).getDate() );
			statement.executeUpdate();
            			
			
			String curr_Date = currentUser.getAppointment(index).getDate().toString();
			int size=currentUser.getAppointments().size();
			
			ArrayList <Integer> removeIndices = new ArrayList <Integer> () ;
			
            for(int i=0 ; i<size ; i++){
            	String comp_Date = currentUser.getAppointment(i).getDate().toString();
            	System.out.println(" h1 "+curr_Date+" h2 "+comp_Date);
            	if(curr_Date.equalsIgnoreCase(comp_Date)) 
            	{ 
            		String SQL2 ="update officehours set Status = 'Available' where OfficeHourID = ?;";
        			PreparedStatement statement2 = Con.prepareStatement(SQL2);
        			statement2.setInt(1,currentUser.getAppointment(i).getOfficeHour().getID());
        			statement2.executeUpdate();
         			
            		int otherID=currentUser.getAppointment(i).getOtherUser().getID();
            		String Notify = "Your Appointment with "+currentUser.getAppointment(i).getOtherUser().getName()+" On "
            		+currentUser.getAppointment(i).getOfficeHour().toString2()+" Has Been Cancelled.";           
            		String Notify2 = "Your Appointment with "+currentUser.getName()+" On "
            		+currentUser.getAppointment(i).getOfficeHour().toString2()+" Has Been Cancelled.";          
            		Notification.sendNotification(currentUser, Notify, Notify2, otherID);
            		currentUser.getNotifications().add(Notify);
            
		            String Topic = "Appointment Cancellation";
		            mail.sendMail(Notify, currentUser.getEmail(),Topic);
		            mail.sendMail(Notify2, currentUser.getAppointment(i).getOtherUser().getEmail(),Topic);
		            
		            removeIndices.add(i);
		            
            	}
            }
            
            for (int i=0;i<removeIndices.size();i++)
            	currentUser.getAppointments().remove(i);
            
            String Feedback=(String) session.getAttribute("Feedback");
            Feedback="Success";
            session.setAttribute("Feedback", Feedback);
            
            
            response.sendRedirect("Appointments.jsp");
                     
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
			try {
				processRequest(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ServletException | IOException | MessagingException e) {
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
				try {
					processRequest(request, response);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ServletException | IOException | MessagingException e) {
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