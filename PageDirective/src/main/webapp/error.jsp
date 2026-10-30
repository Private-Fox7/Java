<%-- 
    Document   : error.jsp
    Created on : 27 Oct 2026, 8:57:26 am
    Author     : santh
--%>

<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<!DOCTYPE html>
<%@ page language="java" 
         contentType="text/html; charset=UTF-8" 
         pageEncoding="UTF-8" 
         isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
</head>
<body>
    <h3 style="color: red;">Oops! Something went wrong.</h3>
    <p><strong>Error Details:</strong> <%= exception.getMessage() %></p>
</body>
</html>
