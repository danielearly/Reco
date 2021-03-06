import Handlers.APIHandler;
import com.ECS.client.jax.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by danie_000 on 1/15/2016.
 */
@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet {

    APIHandler apiHandler = new APIHandler();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter writer = response.getWriter()) {

            writer.println("<!DOCTYPE html><html>");
            writer.println("<head>");
            writer.println("<meta charset=\"UTF-8\" />");
            writer.println("<title>MyServlet.java:doGet(): Servlet code!</title>");
            writer.println("</head>");
            writer.println("<body>");

            writer.println("<h1>These are some amazon products.</h1>");

            List<Items> itemsList = apiHandler.getSearchResults();
            for(Iterator<Items> i = itemsList.iterator(); i.hasNext();) {
                Items items = i.next();
                for(Iterator<Item> j = items.getItem().iterator(); j.hasNext();) {
                    Item item = j.next();
                    Image image = item.getMediumImage();
                    writer.println("<img src=\""+ image.getURL() +"\" alt =\"url didn't work\">");
                }
            }
            writer.println("</body>");
            writer.println("</html>");
        }
    }

}

