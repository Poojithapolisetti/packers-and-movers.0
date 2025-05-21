import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CalculateCostServlet")
public class CalculateCostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fromLocation = request.getParameter("fromLocation");
        String toLocation = request.getParameter("toLocation");

        // Here you would typically call a method to get the costs from your database
        // For simplicity, let's just assume some dummy values
        String company1 = "Mover A";
        int cost1 = 1000;
        String company2 = "Mover B";
        int cost2 = 1200;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        out.println("<style>");
        out.println("body {font-family: Arial, sans-serif; margin: 20px;}");
        out.println(".container {max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ccc; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);}");
        out.println("h2 {color: #333;}");
        out.println(".company {margin-bottom: 15px; padding: 10px; border: 1px solid #eee; border-radius: 5px; background-color: #f9f9f9;}");
        out.println(".company p {margin: 5px 0;}");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class='container'>");
        out.println("<h2>Moving from " + fromLocation + " to " + toLocation + "</h2>");
        out.println("<div class='company'>");
        out.println("<p><strong>Company:</strong> " + company1 + "</p>");
        out.println("<p><strong>Cost:</strong> $" + cost1 + "</p>");
        out.println("</div>");
        out.println("<div class='company'>");
        out.println("<p><strong>Company:</strong> " + company2 + "</p>");
        out.println("<p><strong>Cost:</strong> $" + cost2 + "</p>");
        out.println("</div>");
        out.println("</div>");
        out.println("</body></html>");
    }
}
