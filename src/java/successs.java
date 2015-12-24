/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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

/**
 *
 * @author Miki
 */
@WebServlet(urlPatterns = {"/successs"})
public class successs extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String studentID = request.getSession().getAttribute("sID").toString();
            // 101
            //out.println(studentID);
            int nSelected = Integer.parseInt(String.valueOf(request.getSession().getAttribute("nSelected")));
            ArrayList <String> selected = (ArrayList) request.getSession().getAttribute("selectedCourses");
            Connection Con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3309/MiniUniversity", "root", "admin");
            for (int i=0;i<nSelected;i++)
            {
                Statement stmt = (Statement) Con.createStatement();
                // programming --> need to get course code by a query
                //out.println(selected.get(i));
                // course code 
                ResultSet rs = stmt.executeQuery("select CrsCode from Course where Name = '"+selected.get(i)+"'");
                String crsCode = "";
                while (rs.next()) 
                {
                    crsCode = rs.getString("crsCode");
                }
                //out.println(crsCode);
                // spring
                String semester = request.getParameter("sub"+String.valueOf(i)); 
                //out.println(semester);
                // Query el ktaba (stuentID,crsCode,semester,2015)
                Statement stmt2 = (Statement)Con.createStatement();
                stmt2.executeUpdate("Insert Into Registered Values ('"+String.valueOf(studentID)+"','"+crsCode+"','"+semester+"',2015);");
            }
            Statement stmt1 = (Statement) Con.createStatement();
            ResultSet rs2 = stmt1.executeQuery("select Course.Name from Course where CrsCode in (select CrsCode from Registered where Registered.SSN = " + studentID + ")");
            // Query a5od feha kol el registered courses
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Mini-university App</title>");   
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Registered courses are : </h1>");
            out.println("<br> <br>");
            while (rs2.next())
            {
                out.println(rs2.getString("Name"));
                out.println("<br>");
            }
            //out.println("<h1>Servlet successs at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            // mawwet el session
            checkExistance.session.invalidate();
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
            Logger.getLogger(successs.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(successs.class.getName()).log(Level.SEVERE, null, ex);
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
