<%-- 
    Document   : webapp.jsp
    Created on : 16 Oct 2026, 11:29:27 am
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
        <h1>Hello World!</h1>
        <%!
            //Declaration tag
            int factorial(int n){
                int fact = 1;
                
                for(int i = 1 ; i<=n; i++){
                    fact *=i;
                }
                return fact;
                
            }
        %>
        <%
            //Scriptlet tag
            int num = 5;
            int result = factorial(num);
        %>
        <h2>Factorial Calculation</h2>
        Number : <%=num%> <br>
        Factorial : <%=result%>
    </body>
</html>