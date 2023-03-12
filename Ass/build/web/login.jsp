<%-- 
    Document   : Login
    Created on : Mar 5, 2023, 9:47:00 PM
    Author     : ADMIN
--%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1> LOG IN </h1>
        <form action = "login">
            Username: <input type = "text" name ="txtUsername" value =""> <br>
            Password: <input type = "password" name ="txtPassword" value =""> <br>
            <input type ="submit" name ="btAction" value ="Submit"> <br>     
        </form>
        
        <c:if test = "${requestScope.incorrectUsernamePassword == true}">
            <p style = "color: red" > Incorrect Username or Password. Please try again. </p>
        </c:if>
    </body>
</html>
