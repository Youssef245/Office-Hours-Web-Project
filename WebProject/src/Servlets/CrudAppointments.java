package Servlets;
import Entities.*;
import Utilities.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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
 * Servlet implementation class CrudAppointments
 */
@WebServlet("/CrudAppointments")
public class CrudAppointments extends HttpServlet {
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
            throws ServletException, IOException, AddressException, MessagingException, ParseException {
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
            
            HttpSession session=request.getSession();
            User currentUser = (User) session.getAttribute("SessionUser");
            
            String CRUD = request.getParameter("crud");
            
            if(CRUD.equalsIgnoreCase("Create"))
            {
            	int id=Integer.parseInt(request.getParameter("val"));
            	int Index=Integer.parseInt(request.getParameter("num"));
            	ManageAppointments.CreateAppointment(currentUser, Con, request, id, Index, response);
            	response.sendRedirect("ShowStaffMembers.jsp");
            	
            }
            else if (CRUD.equalsIgnoreCase("Delete"))
            {
            	 int AppID = Integer.parseInt(request.getParameter("val"));
            	 ManageAppointments.DeleteAppointment(currentUser, Con, request, AppID, response);
            	 response.sendRedirect("Appointments.jsp");
            }
            String Feedback=(String) session.getAttribute("Feedback");
            Feedback="Success";
            session.setAttribute("Feedback", Feedback);
	                
            
            
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
