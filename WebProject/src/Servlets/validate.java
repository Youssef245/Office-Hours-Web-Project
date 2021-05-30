package Servlets;
import Entities.*;
import Utilities.*;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;	
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PC
 */
@WebServlet(urlPatterns = {"/validate"})
public class validate extends HttpServlet {

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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, AddressException, MessagingException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/officehours";
            String user="root";
            String password="root";
            Connection Con=null;
            Statement Stmt=null;
            boolean flag=false;
            
            Con=DriverManager.getConnection(url, user, password);
            Stmt=Con.createStatement();
            User loggedUser = new User ();
            String role="",Subject="";
            int id=0;
            String email = request.getParameter("email");
            String pass = request.getParameter("password");
            ResultSet RS = Stmt.executeQuery("Select * from users");
            while(RS.next())
            {
                if(email.equalsIgnoreCase(RS.getString("Email")))
                {
                    if(pass.equalsIgnoreCase(RS.getString("Password")))
                    {
                    	role=RS.getString("Role");
                    	loggedUser.setRole(role);
	                    loggedUser.setName(RS.getString("Name"));
	                    loggedUser.setEmail(email);
	                    loggedUser.setPassword(pass);
	                    id=RS.getInt("UserID");
	                    loggedUser.setID(id);
	                    loggedUser.setDepartment(RS.getString("Department"));
	                    loggedUser.setRank(RS.getString("AcademicRank"));
	                    flag=true;
	                    break;
	                }
	             }
	         }
            
            if(flag)
            {
	            String SQL4= "select * from messages where ReceiverID=?";
				PreparedStatement statement4 = Con.prepareStatement(SQL4);
				statement4.setInt(1, id);
				ResultSet rs4=statement4.executeQuery();
				while(rs4.next())
				{
					Message newMessage = new Message ();
					newMessage.setID(rs4.getInt("MessageID"));
					newMessage.setContent(rs4.getString("Content"));
					
					String SQL5="select * from users where UserID = ?";
					PreparedStatement statement5 = Con.prepareStatement(SQL5);
					statement5.setInt(1, rs4.getInt("SenderID"));
					ResultSet rs5=statement5.executeQuery();
					rs5.next();
					User newUser= new User ();
					newUser.setUser(rs5.getString("Name"), rs5.getInt("UserID"), rs5.getString("Password"),
							rs5.getString("Email"), rs5.getString("Department"), rs5.getString("Role"),rs5.getString("AcademicRank"));
					
					newMessage.setSender(newUser);
					
					loggedUser.setMessage(newMessage);
				}			
				
				
				String SQL3= "select * from notifications where ReceiverID=?";
				PreparedStatement statement3 = Con.prepareStatement(SQL3);
				statement3.setInt(1, id);
				ResultSet rs3=statement3.executeQuery();
				while(rs3.next())
				{
					loggedUser.getNotifications().add(rs3.getString("Content"));
				}			
				
				
				
	            if (role.equalsIgnoreCase("Staff Member")) {
	            	String SQL1 = "Select * from staffmembers where UserID=?";
	    			PreparedStatement statement1 = Con.prepareStatement(SQL1);
	    			statement1.setInt(1, id);
	    			ResultSet rs=statement1.executeQuery();
	    			rs.next();
	    			Subject=rs.getString("Subject");
	            }
	            
	            HistoryOfReservations.checkApp();
	            ManageAppointments.ReadAppointment(loggedUser);
	                    	
            
            	OnDayNotification.sendOnDay(loggedUser);
            	
            	String Feedback="";
            	
            	HttpSession session = request.getSession(true);
            	session.setAttribute("SessionUser", loggedUser);
            	session.setAttribute("Feedback", Feedback);
            	
            	if (role.equalsIgnoreCase("Student"))
            		out.print("Student");
            	else {
            		session.setAttribute("loggedSubject", Subject);
            		
            		ArrayList <User> results = new ArrayList<User> ();
            		session.setAttribute("SearchResults", results);
            		
            		HistoryOfReservations.getHistory(loggedUser);
            		
            		ArrayList <OfficeHour> officehours = new ArrayList<OfficeHour> ();
            		officehours=OfficeHourGetter.HourGetter(loggedUser.getID());
            		session.setAttribute("officehours", officehours);
            		
            		
            		
            		out.print("Staff Member");
            	}
                
            }
            else
            {
            	String errorMessage = "Wrong ID or Password , Please Enter Right Information.";
            	out.print(errorMessage);
            }
            
            
            
            
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