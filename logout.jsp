<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page language="java" %>
<%
    // Avoid declaring the session variable twice
    HttpSession currentSession = request.getSession(false);
    if (currentSession != null) {
        currentSession.invalidate();
    }
    response.sendRedirect("login1.html");
%>
