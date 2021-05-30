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
 * Servlet implementation class GetOfficeHours
 */
@WebServlet("/GetOfficeHours")
public class GetOfficeHours extends HttpServlet {
	 /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session=request.getSession(); 
            User currentUser = (User) session.getAttribute("SessionUser");
            int id=Integer.parseInt(request.getParameter("val"));
            ArrayList<OfficeHour> officehours = new ArrayList <OfficeHour> ();
            
            officehours= OfficeHourGetter.HourGetter(id);
            
            
            if (officehours.size()!=0)
            {
	            String full_Hours = "";
	            for(int i=0;i<officehours.size();i++)
	            {
	            	if(i==officehours.size()-1)
	            		full_Hours+=officehours.get(i).toString();
	            	else
	            		full_Hours+=officehours.get(i).toString()+"|";
	            }
	            full_Hours+="%%";
	            for(int i=0;i<officehours.size();i++)
	            {
	            	if(i==officehours.size()-1)
	            		full_Hours+=officehours.get(i).getLocation();
	            	else
	            		full_Hours+=officehours.get(i).getLocation()+"|";
	            }
	            
	            out.print(full_Hours);
	        }
            else
            {
            	out.print("No Hours to Show Yet.");
            }
	       
            
            
            
            
            
            
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
		} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
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
		} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
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
