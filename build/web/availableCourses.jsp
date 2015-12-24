<%-- 
    Document   : availableCourses
    Created on : Nov 9, 2015, 3:20:19 AM
    Author     : Miki
--%>
<%@page import="java.util.Properties"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.mysql.jdbc.DatabaseMetaData"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mysql.jdbc.Statement"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mini-university App</title>
    </head>
    
        <%
            String studentName = request.getSession().getAttribute("studentName").toString();
        %>
        <h1>
            Hello  <%=studentName%> <br> <br>
        </h1>
        <h2>
            List of available courses : <br> <br>
        </h2>
        <%
            String studentID = request.getSession().getAttribute("sID").toString();
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException cnfe) {
                System.err.print("Error loaing driver" + cnfe);
            }
            Connection Con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3309/MiniUniversity", "root", "admin");
            Statement stmt = (Statement) Con.createStatement();
            ResultSet rs = stmt.executeQuery("select Course.Name from Course where CrsCode not in (select CrsCode from Registered where Registered.SSN = " + studentID + ")");
            int i=0;
            %>
            <form action="confirmm" id="form" name="form"">
            <%
            while (rs.next()) {
                i++;
                String subject = rs.getString("Name");
            %>
            <input type="checkbox" name="sub<%=i%>" value="<%=subject%>"> <%=subject%>
            <br>
            <%
            }
            %>
            <%
                session.setAttribute("subjectsNumber", i);
            %>
            <input type="submit" value="Add subjects">
            </form>
            

    

