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

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/pack";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Navyatanu@3y";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Don't create a new session
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in.");
            return;
        }

        int userId = (int) session.getAttribute("user_id");

        Connection conn = null;
        PreparedStatement pstmtUser = null;
        PreparedStatement pstmtBooking = null;
        ResultSet rsUser = null;
        ResultSet rsBooking = null;
        PrintWriter out = response.getWriter();

        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            String sqlUser = "SELECT username, email FROM userdata WHERE id = ?";
            pstmtUser = conn.prepareStatement(sqlUser);
            pstmtUser.setInt(1, userId);
            rsUser = pstmtUser.executeQuery();

            if (rsUser.next()) {
                String username = rsUser.getString("username");
                String email = rsUser.getString("email");

                String sqlBooking = "SELECT bh.booking_id, bh.from_location, bh.to_location, c.company_name AS company, bh.cost, bh.booking_date "
                                 + "FROM booking_history bh "
                                 + "JOIN companies c ON bh.company_id = c.company_id "
                                 + "WHERE bh.user_id = ?";
                pstmtBooking = conn.prepareStatement(sqlBooking);
                pstmtBooking.setInt(1, userId);
                rsBooking = pstmtBooking.executeQuery();

                StringBuilder json = new StringBuilder();
                json.append("{");
                json.append("\"username\":\"").append(username).append("\",");
                json.append("\"email\":\"").append(email).append("\",");
                json.append("\"bookingHistory\":[");
                boolean first = true;
                while (rsBooking.next()) {
                    if (!first) json.append(",");
                    json.append("{");
                    json.append("\"bookingId\":").append(rsBooking.getInt("booking_id")).append(",");
                    json.append("\"fromLocation\":\"").append(rsBooking.getString("from_location")).append("\",");
                    json.append("\"toLocation\":\"").append(rsBooking.getString("to_location")).append("\",");
                    json.append("\"company\":\"").append(rsBooking.getString("company")).append("\",");
                    json.append("\"cost\":").append(rsBooking.getInt("cost")).append(",");
                    json.append("\"bookingDate\":\"").append(rsBooking.getString("booking_date")).append("\"");
                    json.append("}");
                    first = false;
                }
                json.append("]");
                json.append("}");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.write(json.toString());

                rsBooking.close();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();  // Print stack trace for debugging
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal server error occurred: " + e.getMessage());
        } finally {
            try {
                if (rsUser != null) rsUser.close();
                if (rsBooking != null) rsBooking.close();
                if (pstmtUser != null) pstmtUser.close();
                if (pstmtBooking != null) pstmtBooking.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
