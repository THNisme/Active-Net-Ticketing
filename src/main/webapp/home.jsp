<%-- 
    Document   : home
    Created on : Oct 4, 2025, 5:25:12 PM
    Author     : NguyenDuc
--%>

<%@page import="Models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Trang chủ</title>
    </head>
    <body>
        
        <h2>Xin chào, <%= user.getUsername()%>!</h2>
    </body>
</html>