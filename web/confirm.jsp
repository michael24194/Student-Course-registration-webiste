<%-- 
    Document   : finish
    Created on : Nov 10, 2015, 9:24:37 PM
    Author     : Miki
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mini-university App</title>
        <style>
            table, th, td 
            {
                border:0.5px solid;
            }
            th, td 
            {
                padding: 5px;
            }
        </style>
    </head>
    <body>
        <h1>Registered subjects are : </h1> <br>
        <%
            int nSelected = Integer.parseInt(String.valueOf(request.getSession().getAttribute("nSelected")));
            ArrayList <String> subs = (ArrayList) request.getSession().getAttribute("selectedCourses");
        %>
        <form action="successs" name="form" id="form">
            <table>
                <%
                    for (int i = 0; i < subs.size(); i++) {
                %>
                &nbsp&nbsp&nbsp&nbsp
                <tr> 
                    <td>
                        <%out.print(subs.get(i));%>
                    </td>
                    <td>
                        <select name="sub<%=i%>">
                            <option value="Fall">Fall</option>
                            <option value="Spring">Spring</option>
                            <option value="Summer">Summer</option>
                        </select>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
            <br>
            <input type="submit" value="Confirm courses">
        </form>
        <br> <br>
        <form action="availableCourses.jsp">
            <input type="submit" value="Back">
        </form>
    </body>
</html>
