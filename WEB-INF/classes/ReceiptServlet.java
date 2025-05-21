import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ReceiptServlet")
public class ReceiptServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/pack";
    private static final String USER = "root";
    private static final String PASS = "Navyatanu@3y";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String company = request.getParameter("company");
        String cost = request.getParameter("cost");
        String fromLocation = request.getParameter("fromLocation");
        String toLocation = request.getParameter("toLocation");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Retrieve the user ID based on the username
            String sqlGetUserId = "SELECT id FROM userdata WHERE username = ?";
            pstmt = conn.prepareStatement(sqlGetUserId);
            pstmt.setString(1, username);
            int userId = 0;
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
            }
            rs.close();
            pstmt.close();

            // Insert booking information into the database
            String sqlInsertBooking = "INSERT INTO booking_history (user_id, from_location, to_location, company, cost) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sqlInsertBooking);
            pstmt.setInt(1, userId);
            pstmt.setString(2, fromLocation);
            pstmt.setString(3, toLocation);
            pstmt.setString(4, company);
            pstmt.setInt(5, Integer.parseInt(cost));
            pstmt.executeUpdate();

            // Set attributes for receipt.jsp
            request.setAttribute("company", company);
            request.setAttribute("cost", cost);
            request.setAttribute("fromLocation", fromLocation);
            request.setAttribute("toLocation", toLocation);

            // Forward to receipt.jsp
            request.getRequestDispatcher("receipt.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
