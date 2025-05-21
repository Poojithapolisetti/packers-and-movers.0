import java.io.PrintWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminBookingHistoryServlet")
public class AdminBookingHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/pack";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Navyatanu@3y";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> bookingList = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            String sql = "SELECT bh.booking_id, bh.from_location, bh.to_location, bh.company, bh.cost, bh.booking_date, u.username " +
                         "FROM booking_history bh " +
                         "JOIN userdata u ON bh.user_id = u.id " +
                         "WHERE bh.status != 'Cancelled'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                String fromLocation = rs.getString("from_location");
                String toLocation = rs.getString("to_location");
                String company = rs.getString("company");
                int cost = rs.getInt("cost");
                String bookingDate = rs.getString("booking_date");
                String username = rs.getString("username");
                
                String bookingJson = "{"
                        + "\"bookingId\":" + bookingId + ","
                        + "\"username\":\"" + username + "\","
                        + "\"fromLocation\":\"" + fromLocation + "\","
                        + "\"toLocation\":\"" + toLocation + "\","
                        + "\"company\":\"" + company + "\","
                        + "\"cost\":" + cost + ","
                        + "\"bookingDate\":\"" + bookingDate + "\""
                        + "}";
                
                bookingList.add(bookingJson);
            }
            
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("[");
            for (int i = 0; i < bookingList.size(); i++) {
                jsonBuilder.append(bookingList.get(i));
                if (i < bookingList.size() - 1) {
                    jsonBuilder.append(",");
                }
            }
            jsonBuilder.append("]");
            
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(jsonBuilder.toString());
            
        } catch (SQLException se) {
            se.printStackTrace();
            throw new ServletException(se);
            
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
