<%-- 
    Document   : obj1
    Created on : 22 Oct 2026, 2:09:30 pm
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Student Details!</h1>
        <form action="obj1.jsp" method=""get>
            Enter Your Name : 
            <input type="text" name="uname">
            <input type="submit" value="submit">
        </form>
        <%
            String name = request.getParameter("uname");
            if(name != null && !name.isEmpty()){
                out.print("<h3>Welcome , "+name+"!<h3>");
                response.setHeader("JSP-Demo","Student1");
            }
        %>
        
    </body>
</html>