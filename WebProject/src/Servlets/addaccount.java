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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.management.relation.Role;
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
@WebServlet(urlPatterns = {"/addaccount"})
public class addaccount extends HttpServlet {

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
            User loggeduser = new User ();
            String email = request.getParameter("email");
            String department = request.getParameter("Department");
            String name = request.getParameter("name");
            String role = request.getParameter("Role");
            String subject="",rank;
            Random rand=new Random();
            int number=rand.nextInt(88888888);
            String pass=Integer.toString(number);            
            
            int id=0;
            
            if(role.equalsIgnoreCase("Student"))
            	rank="--";
            else
            	rank=request.getParameter("Rank");
            
            String query = "Select * from users where Email = ?" ;
            PreparedStatement prepStatement = Con.prepareStatement(query);
            prepStatement.setString(1,email);
			ResultSet RS = prepStatement.executeQuery();
            if(RS.next())
            {
                flag=true;
            }
            
            if(flag)
            	out.print("The User Already Exists");
            else 
            {
            	String mailtext="Welcome new User , your Generated Password :"+ pass;
                
                mail.sendMail(mailtext, email,"Registeration");
           
	            String SQL = "insert into users(Email,Password,Department,Name,Role,AcademicRank) "
	                    + "VALUES(?,?,?,?,?,?)";
	            PreparedStatement statement = Con.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
	            statement.setString(1, email);
				statement.setString(2, pass);
				statement.setString(3, department);
				statement.setString(4, name);
				statement.setString(5, role);
				statement.setString(6, rank);
				statement.executeUpdate();
				
				
				ResultSet rs=statement.getGeneratedKeys();
				rs.next();
				id=rs.getInt(1);
				loggeduser.setUser(name, id, pass, email, department, role,rank);
				
				
				if (role.equalsIgnoreCase("Staff Member")) {
	            	subject = request.getParameter("Subject");
	            	String SQL2 = "insert into staffmembers(UserID,Subject) "
	                        + "VALUES(?,?)";
	                PreparedStatement statement2 = Con.prepareStatement(SQL2);
	                statement2.setInt(1, id);
	                statement2.setString(2,subject);
	                statement2.executeUpdate();
	            }
				
				
				String Feedback="";
            	
            	HttpSession session = request.getSession(true);
            	session.setAttribute("Feedback", Feedback);           	
	        	session.setAttribute("SessionUser", loggeduser);
	        	if (role.equalsIgnoreCase("Student"))
	        		out.print("Student");
	        	else {
	        		session.setAttribute("loggedSubject", subject);
	        		out.print("Staff Member");
	        	}
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