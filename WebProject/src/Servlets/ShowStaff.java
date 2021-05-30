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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShowStaff
 */
@WebServlet("/ShowStaff")
public class ShowStaff extends HttpServlet {
	/**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/officehours";
            String user="root";
            String password="root";
            Connection Con=null;
            Statement Stmt=null;
            boolean flag=false;
            HttpSession session=request.getSession(); 
            String redirect=request.getParameter("redirect");
            
            Con=DriverManager.getConnection(url, user, password);
            Stmt=Con.createStatement();
            
            ArrayList<String>Subjects = new ArrayList<String>();
            ArrayList<ArrayList<User>> StaffMembers = new ArrayList<ArrayList<User>> ();             
            ResultSet RS = Stmt.executeQuery("SELECT DISTINCT Subject FROM staffmembers;");
            while(RS.next())
            {
            		ArrayList<User> dummy = new ArrayList<User>();
            		String currSubject="";
            		currSubject=RS.getString("Subject");
            		Subjects.add(currSubject);
            		            		
            		String SQL = "select * from users"
            					+" join staffmembers on users.UserID = staffmembers.UserID"
            					+" where Subject=?;";
        			PreparedStatement statement = Con.prepareStatement(SQL);
        			statement.setString(1,currSubject);
        			ResultSet RS2=statement.executeQuery();
        			
        			while(RS2.next()) {
            			User newUser = new User ();
            			newUser.setUser(RS2.getString("Name"), RS2.getInt("UserID"), RS2.getString("Password"),
            					RS2.getString("Email"), RS2.getString("Department"), "Staff Member",RS2.getString("AcademicRank"));
            			dummy.add(newUser);
        			}
            		StaffMembers.add(dummy);          		
            }
            
            session.setAttribute("SubjectsList", Subjects);
            session.setAttribute("NamesList", StaffMembers);
            
            if(redirect.equalsIgnoreCase("true"))
            	response.sendRedirect("ShowStaffMembers.jsp");
            else
            	response.sendRedirect("SubjectStaff.jsp");
            
            
            
            
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
        processRequest(request, response);
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
        processRequest(request, response);
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
