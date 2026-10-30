<%-- 
    Document   : index.jsp
    Created on : 27 Oct 2026, 8:55:30 am
    Author     : santh
--%>

<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<%@ page language="java" 
         contentType="text/html; charset=UTF-8" 
         pageEncoding="UTF-8"
         import="java.util.Date, java.text.SimpleDateFormat, java.util.Random" 
         session="true" 
         buffer="16kb" 
         autoFlush="true" 
         errorPage="error.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP Page Directive Demo</title>
</head>
<body>
    <h2>JSP Page Directive Attributes Demo</h2>

    <p><strong>Current Date and Time:</strong> 
        <% 
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            out.print(formatter.format(now));
        %>
    </p>

    <p><strong>Session ID:</strong> <%= session.getId() %></p>

    <%
      if (true) {
            String test = null;
            test.length(); // Throws NullPointerException
        }
    %>
</body>
</html>

