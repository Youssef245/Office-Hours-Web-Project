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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateProfile
 */
@WebServlet("/UpdateProfile")
public class UpdateProfile extends HttpServlet {
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
            HttpSession session=request.getSession(); 
            
            Con=DriverManager.getConnection(url, user, password);
            Stmt=Con.createStatement();
            User currentUser = (User) session.getAttribute("SessionUser");
            String sessionSubject = (String) session.getAttribute("loggedSubject");
            String email = request.getParameter("email");
            String department = request.getParameter("Department");
            String name = request.getParameter("name");
            String pass=request.getParameter("Password");
            
            String role=currentUser.getRole();
            int id=currentUser.getID();
            String rank=currentUser.getRank();
            String subject="";
            
            String SQL = "UPDATE  users SET Email=?,Password=?,Department=?,Name=?"
                    + "WHERE UserID=?;";
            PreparedStatement statement = Con.prepareStatement(SQL);
            statement.setString(1, email);
			statement.setString(2, pass);
			statement.setString(3, department);
			statement.setString(4, name);
			statement.setInt(5, id);
			statement.executeUpdate();
			currentUser.setUser(name, id, pass, email, department, role,rank);
			
			if (role.equalsIgnoreCase("Staff Member")) {
            	subject = request.getParameter("Subject");
            	sessionSubject=subject;
            	String SQL2 = "Update staffmembers SET Subject=?"
            			 + "WHERE UserID=?;";
                PreparedStatement statement2 = Con.prepareStatement(SQL2);
                statement2.setString(1,subject);
                statement2.setInt(2, id);
                statement2.executeUpdate();
                
            }
			
			String Feedback=(String) session.getAttribute("Feedback");
            Feedback="Success";
            session.setAttribute("Feedback", Feedback);
			request.getSession().setAttribute("loggedSubject", subject);
			        	
        	response.sendRedirect("Home.jsp");

                
            
            
            
            
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
