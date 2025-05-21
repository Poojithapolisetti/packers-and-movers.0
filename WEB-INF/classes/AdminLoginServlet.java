import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("adminUsername"); // Corrected parameter name
        String password = request.getParameter("adminPassword"); // Corrected parameter name

        if ("admin".equals(username) && "admin123".equals(password)) {
            // Admin login successful
            HttpSession session = request.getSession();
            session.setAttribute("adminLoggedIn", true);
            response.sendRedirect("admin-dashboard.html");
        } else {
            // Invalid credentials
            response.setContentType("text/html");
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h2>Invalid admin credentials</h2>");
            response.getWriter().println("<p><a href='admin-login.html'>Go back to login</a></p>");
            response.getWriter().println("</body></html>");
        }
    }
}
