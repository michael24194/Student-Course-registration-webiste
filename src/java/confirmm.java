/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.SystemColor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Miki
 */
@WebServlet(urlPatterns = {"/confirmm"})
public class confirmm extends HttpServlet {

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
            int numberOfSubjects = Integer.parseInt(String.valueOf(request.getSession().getAttribute("subjectsNumber")));
            ArrayList subs = new ArrayList();
            for (int i = 0; i <= numberOfSubjects; i++) {
                String y = String.valueOf(i);
                String s = request.getParameter("sub" + y);
                if (s != null) {
                    subs.add(s);
                }
            }
            checkExistance.session.setAttribute("nSelected", subs.size());
            checkExistance.session.setAttribute("selectedCourses", subs);
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Mini-university App</title>");
            if (subs.size() == 0)
            {
                out.println("No subjects are selected!! Redirecting to available courses in 3 seconds...");
                out.println("<script>");
                out.println("function leave() {");
                out.println("window.location = 'availableCourses.jsp'");
                out.println("}");
                out.println("setTimeout('leave()', 2500);");
                out.println("</script>");
                out.println("</head>");
            }
            else
            {
                out.println("</head>");
                out.println("<body>");
                out.println("</body>");
                out.println("</html>");
                response.sendRedirect("confirm.jsp");
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
