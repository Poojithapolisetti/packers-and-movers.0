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
import java.text.SimpleDateFormat;

@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/pack";
    private static final String USER = "root";
    private static final String PASS = "Navyatanu@3y";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        if (userId == null) {
            response.sendRedirect("login1.html");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmtBookings = null;
        ResultSet rs = null;
        ResultSet rsBookings = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Query user profile and transaction count
            String sql = "SELECT u.username, u.email, COUNT(b.booking_id) AS transaction_count " +
                         "FROM userdata u LEFT JOIN booking_history b ON u.id = b.user_id " +
                         "WHERE u.id = ? AND (b.status != 'Cancelled' OR b.status IS NULL)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{");

            if (rs.next()) {
                jsonBuilder.append("\"username\":\"").append(rs.getString("username")).append("\",");
                jsonBuilder.append("\"email\":\"").append(rs.getString("email")).append("\",");
                jsonBuilder.append("\"transactionCount\":").append(rs.getInt("transaction_count")).append(",");
            }

            // Query booking history
            String sqlBookings = "SELECT booking_id, from_location, to_location, company, cost, booking_date FROM booking_history WHERE user_id = ? AND status != 'Cancelled'";
            pstmtBookings = conn.prepareStatement(sqlBookings);
            pstmtBookings.setInt(1, userId);
            rsBookings = pstmtBookings.executeQuery();

            jsonBuilder.append("\"bookingHistory\":[");
            boolean first = true;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            while (rsBookings.next()) {
                if (!first) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append("{");
                jsonBuilder.append("\"bookingId\":").append(rsBookings.getInt("booking_id")).append(",");
                jsonBuilder.append("\"fromLocation\":\"").append(rsBookings.getString("from_location")).append("\",");
                jsonBuilder.append("\"toLocation\":\"").append(rsBookings.getString("to_location")).append("\",");
                jsonBuilder.append("\"company\":\"").append(rsBookings.getString("company")).append("\",");
                jsonBuilder.append("\"cost\":").append(rsBookings.getInt("cost")).append(",");
                
                java.sql.Date sqlDate = rsBookings.getDate("booking_date");
                String formattedDate = (sqlDate != null) ? dateFormat.format(sqlDate) : "Unknown";
                jsonBuilder.append("\"bookingDate\":\"").append(formattedDate).append("\"");
                jsonBuilder.append("}");
                first = false;
            }
            jsonBuilder.append("]");

            jsonBuilder.append("}");

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(jsonBuilder.toString());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (rsBookings != null) rsBookings.close();
                if (pstmtBookings != null) pstmtBookings.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}