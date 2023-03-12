<%-- 
    Document   : welcome
    Created on : Mar 5, 2023, 9:53:34 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table border="1px"> 
            <tr>
                <td></td>
                <c:forEach items="${requestScope.dates}" var="d">
                    <td>${d}<br/><fmt:formatDate value="${d}" pattern="EEEE"/>
            </td>
        </c:forEach>

    </tr>
    <c:forEach items="${requestScope.slots}" var="slot"> 
        <tr>
            <td>${slot.name}</td>
            <c:forEach items="${requestScope.dates}" var="d">
                <td>
                    <c:forEach items="${requestScope.s.groups}" var="g">
                        <c:forEach items="${g.sessions}" var="ses">
                            <c:if test="${ses.date eq d and ses.slot.id eq slot.id}">
                                ${g.name}(${g.course.name}) <br/>
                                ${ses.lecturer.name}-${ses.room.name} <br/>
                                <c:if test="${ses.status}">
                                    <img src="../img/Ok-icon.png" alt=""/>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </td>
            </c:forEach>
        </tr>
    </c:forEach>

</table>
</body>
</html>
