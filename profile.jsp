<%@ page import="java.sql.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page language="java" %>

<%
    // Initialize variables
    String username = null;
    String email = null;

    // Get session and validate
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
        return; // Stop further execution
    } else {
        username = (String) session.getAttribute("username");
    }

    // Debug output
    out.println("Username from session: " + username);

    // Database connection details
    String dbURL = "jdbc:mysql://localhost:3306/pack";
    String dbUser = "root";
    String dbPass = "Navyatanu@3y";

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        // Establish database connection
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
        
        // Prepare and execute SQL query
        String sql = "SELECT email FROM userdata WHERE username=?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        rs = stmt.executeQuery();
        
        // Retrieve email
        if (rs.next()) {
            email = rs.getString("email");
            out.println("Retrieved email: " + email); // Debug output
        } else {
            email = "Email not found";
        }
    } catch (Exception e) {
        e.printStackTrace();
        email = "Error retrieving email"; // Set a default value in case of error
    } finally {
        // Close resources
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2 class="mt-5">User Profile</h2>
        <div class="card mt-3">
            <div class="card-body">
                <h5 class="card-title"><%= username %></h5>
                <p class="card-text"><strong>Email:</strong> <%= email %></p>
                <a href="dashboard.jsp" class="btn btn-primary">Back to Dashboard</a>
            </div>
        </div>
    </div>
</body>
</html>
