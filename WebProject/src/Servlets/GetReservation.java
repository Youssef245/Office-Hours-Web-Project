package Servlets;
import Entities.*;
import Utilities.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * Servlet implementation class GetReservation
 */
@WebServlet("/GetReservation")
public class GetReservation extends HttpServlet {
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
            HttpSession session=request.getSession(); 
            User currentUser = (User) session.getAttribute("SessionUser");
            ArrayList<OfficeHour> officehours = new ArrayList <OfficeHour> ();
            
            int index = Integer.parseInt(request.getParameter("index"));
            officehours= (ArrayList<OfficeHour>) session.getAttribute("officehours");
            
            String ReservationInfo = "";
            for(int i=0;i<currentUser.getAppointments().size();i++)
            {
            	if(currentUser.getAppointment(i).getOfficeHour().getDay().equalsIgnoreCase(officehours.get(index).getDay())
            	 &&currentUser.getAppointment(i).getOfficeHour().getFromTime().equals(officehours.get(index).getFromTime()))
            	{
            		ReservationInfo="Student : "+currentUser.getAppointment(i).getOtherUser().getName()+" , On : "
            		+currentUser.getAppointment(i).getDate();
            		break;
            	}
            }
            
            
            out.print(ReservationInfo);
	       
            
            
            
            
            
            
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
