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

@WebServlet("/CompanyListServlet")
public class CompanyListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/pack";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Navyatanu@3y";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load JDBC driver");
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fromLocation = request.getParameter("fromLocation");
        String toLocation = request.getParameter("toLocation");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Company List</title>");
        out.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css'>");
        out.println("<style>");
        out.println("body {");
        out.println("    position: relative;");
        out.println("    height: 100vh;");
        out.println("    display: flex;");
        out.println("    flex-direction: column;");
        out.println("    align-items: center;");
        out.println("    margin: 0;");
        out.println("    overflow: hidden;");
        out.println("}");
        out.println(".background-image {");
        out.println("    position: absolute;");
        out.println("    top: 0;");
        out.println("    left: 0;");
        out.println("    width: 100%;");
        out.println("    height: 100%;");
        out.println("    background-image: url('truck2.jpg');");
        out.println("    background-size: cover;");
        out.println("    background-position: center;");
        out.println("    z-index: -1;");
        out.println("}");
        out.println(".background-overlay {");
        out.println("    position: absolute;");
        out.println("    top: 0;");
        out.println("    left: 0;");
        out.println("    width: 100%;");
        out.println("    height: 100%;");
        out.println("    background: rgba(0, 0, 0, 0.5);");
        out.println("    z-index: -1;");
        out.println("}");
        out.println(".container {");
        out.println("    background: rgba(245, 230, 230, 0.7);");
        out.println("    padding: 30px;");
        out.println("    border-radius: 15px;");
        out.println("    width: 90%;");
        out.println("    max-width: 800px;");
        out.println("    margin-top: 20px;");
        out.println("    margin-bottom: 20px;");
        out.println("}");
        out.println("table {");
        out.println("    background: rgba(252, 248, 248, 0.7);");
        out.println("    border-radius: 10px;");
        out.println("    width: 100%;");
        out.println("}");
        out.println("thead {");
        out.println("    background: rgba(0, 0, 0, 0.8);");
        out.println("    color: white;");
        out.println("}");
        out.println("thead th {");
        out.println("    text-align: center;");
        out.println("}");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class='background-image'></div>");
        out.println("<div class='background-overlay'></div>");
        out.println("<div class='container'>");
        out.println("<h2 class='text-center'>Moving from " + fromLocation + " to " + toLocation + "</h2>");
        out.println("<table class='table table-striped table-bordered'>");
        out.println("<thead class='thead-dark'><tr><th>Company</th><th>Cost ($)</th><th>Phone Number</th><th>Rating</th><th>Action</th></tr></thead>");
        out.println("<tbody>");

        try (Connection connection = getConnection()) {
            String sql = "SELECT company_name, cost_per_km, phone_number, rating FROM companies WHERE from_location = ? AND to_location = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, fromLocation);
                statement.setString(2, toLocation);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String companyName = resultSet.getString("company_name");
                        int cost = resultSet.getInt("cost_per_km");
                        String phoneNumber = resultSet.getString("phone_number");
                        double rating = resultSet.getDouble("rating");

                        out.println("<tr><td>" + companyName + "</td><td>" + cost + "</td><td>" + phoneNumber + "</td><td>" + rating + "</td>");
                        out.println("<td><form action='ReceiptServlet' method='post'>");
                        out.println("<input type='hidden' name='company' value='" + companyName + "'>");
                        out.println("<input type='hidden' name='cost' value='" + cost + "'>");
                        out.println("<input type='hidden' name='fromLocation' value='" + fromLocation + "'>");
                        out.println("<input type='hidden' name='toLocation' value='" + toLocation + "'>");
                        out.println("<button type='submit' class='btn btn-primary'>Book</button>");
                        out.println("</form></td></tr>");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<tr><td colspan='5'>An error occurred while fetching company data.</td></tr>");
        }

        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</body></html>");
    }
}
