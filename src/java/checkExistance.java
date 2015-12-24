/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Miki
 */
@WebServlet(urlPatterns = {"/checkExistance"})
public class checkExistance extends HttpServlet {
    public static HttpSession session;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException cnfe)
        {
            System.err.print("Error loaing driver" + cnfe);
        }
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            session = request.getSession(true);
            if (session.isNew() == false )
            {
                session.invalidate();
                session = request.getSession(true);
            }
            String studentID = request.getParameter("sID");
            String studentPass = request.getParameter("sPass");
            /* Please note that I installed mySQL at port number 3309 */
            Connection Con = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3309/MiniUniversity", "root", "admin");
            Statement stmt = (Statement)Con.createStatement();
            Statement stmt2 = (Statement)Con.createStatement();
            ResultSet rs = stmt.executeQuery("select Name from Student where SSN = '" + studentID + "'");
            ResultSet rs2 = stmt2.executeQuery("select Password from Student where SSN = '" + studentID + "'");
            boolean idCheck = false,passCheck = false;
            String res="";
            String res2="";
            while (rs.next())
            {
                idCheck = true;
                res = rs.getString("Name");
            }
            while (rs2.next())
            {
                res2 = rs2.getString("Password");
            }
            if (res2.equals(studentPass))
                passCheck = true;
            if (!idCheck || !passCheck)
            {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>ERROR!</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h3>");
                out.println("Student ID doesn't exist or wrong password...Press return button to go back to "
                        + "login form");
                out.println("</h3");
                out.println("<br><br>");
                out.println("<form action='index.html'>");
                out.println("<br>");
                out.println("<input type='submit' value='Return'>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
                out.close();
            }
            else 
            {
                session.setAttribute("studentName", res);
                session.setAttribute("sID", studentID);
                response.sendRedirect("availableCourses.jsp");
                out.close();
            }
            out.close();
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
        } catch (SQLException ex) {
            Logger.getLogger(checkExistance.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(checkExistance.class.getName()).log(Level.SEVERE, null, ex);
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
