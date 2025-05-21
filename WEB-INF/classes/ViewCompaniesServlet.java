import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewCompaniesServlet")
public class ViewCompaniesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String DB_URL = "jdbc:mysql://localhost:3306/pack";
        String DB_USER = "root";
        String DB_PASSWORD = "Navyatanu@3y";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "SELECT DISTINCT company_name, phone_number, rating FROM companies";
            ResultSet rs = stmt.executeQuery(sql);

            StringBuilder json = new StringBuilder("[");
            while (rs.next()) {
                if (json.length() > 1) json.append(",");
                json.append("{");
                json.append("\"company_name\":\"").append(rs.getString("company_name")).append("\",");
                json.append("\"phone_number\":\"").append(rs.getString("phone_number")).append("\",");
                json.append("\"rating\":").append(rs.getBigDecimal("rating"));
                json.append("}");
            }
            json.append("]");

            out.print(json.toString());
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"An error occurred while fetching data.\"}");
        } finally {
            out.close();
        }
    }
}
