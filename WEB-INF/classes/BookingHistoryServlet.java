import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/BookingHistoryServlet")
public class BookingHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/pack";
    private static final String USER = "root";
    private static final String PASS = "Navyatanu@3y";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user_id");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ArrayList<Booking> bookingList = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT * FROM booking_history WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setFromLocation(rs.getString("from_location"));
                booking.setToLocation(rs.getString("to_location"));
                booking.setCompany(rs.getString("company"));
                booking.setCost(rs.getInt("cost"));
                booking.setBookingDate(rs.getTimestamp("booking_date"));
                bookingList.add(booking);
            }
            rs.close();
            pstmt.close();

            // Construct JSON manually
            StringBuilder json = new StringBuilder();
            json.append("[");
            for (int i = 0; i < bookingList.size(); i++) {
                Booking booking = bookingList.get(i);
                json.append("{");
                json.append("\"bookingId\":").append(booking.getBookingId()).append(",");
                json.append("\"fromLocation\":\"").append(booking.getFromLocation()).append("\",");
                json.append("\"toLocation\":\"").append(booking.getToLocation()).append("\",");
                json.append("\"company\":\"").append(booking.getCompany()).append("\",");
                json.append("\"cost\":").append(booking.getCost()).append(",");
                json.append("\"bookingDate\":\"").append(booking.getBookingDate()).append("\"");
                json.append("}");
                if (i < bookingList.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
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

class Booking {
    private int bookingId;
    private String fromLocation;
    private String toLocation;
    private String company;
    private int cost;
    private java.sql.Timestamp bookingDate;

    // Getters and setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public java.sql.Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(java.sql.Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }
}
