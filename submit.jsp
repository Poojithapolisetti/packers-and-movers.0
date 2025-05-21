<%@ page import="java.sql.*" %>
<%
String username = request.getParameter("username");
String email = request.getParameter("email");
String password = request.getParameter("password");
String phone = request.getParameter("phone");
String address = request.getParameter("address");

// JDBC driver name and database URL
String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
String DB_URL = "jdbc:mysql://localhost/pack";

// Database credentials
String USER = "root";
String PASS = "Navyatanu@3y";
Connection conn = null;
PreparedStatement pstmt = null;

try {
    // Register JDBC driver
    Class.forName(JDBC_DRIVER);
    // Open a connection
    conn = DriverManager.getConnection(DB_URL, USER, PASS);

    // Create SQL query
    String sql = "INSERT INTO userdata (username, password, email, phone, address) VALUES (?, ?, ?, ?, ?)";
    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, username);
    pstmt.setString(2, password);
    pstmt.setString(3, email);
    pstmt.setString(4, phone);
    pstmt.setString(5, address);

    // Execute SQL query
    pstmt.executeUpdate();

    // Redirect to success page
    response.sendRedirect("dashboard.html");

} catch (SQLException se) {
    // Handle errors for JDBC
    se.printStackTrace();
    out.println("Error: " + se.getMessage());
} catch (Exception e) {
    // Handle errors for Class.forName
    e.printStackTrace();
    out.println("Error: " + e.getMessage());
} finally {
    // Finally block used to close resources
    try {
        if (pstmt != null) pstmt.close();
    } catch (SQLException se) {
        se.printStackTrace();
    }
    try {
        if (conn != null) conn.close();
    } catch (SQLException se) {
        se.printStackTrace();
    }
}
%>
