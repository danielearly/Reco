package Servlets;

import Handlers.APIHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by danie_000 on 1/15/2016.
 */
@WebServlet(name = "mainServlet")
public class mainServlet extends HttpServlet {

    APIHandler apiHandler = new APIHandler();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Preprocess request: we actually don't need to do any business stuff, so just display JSP.
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String input = request.getParameter("input");
        String category = request.getParameter("category");
        if (input != null && !input.contentEquals("")) {
            apiHandler.getSearchResults(input, category);

            request.setAttribute("titlesList", apiHandler.getTitles());
            request.setAttribute("urlList", apiHandler.getUrls());
            request.setAttribute("priceList", apiHandler.getPrices());
            request.setAttribute("genreList", apiHandler.getGenres());
            request.setAttribute("rankingList", apiHandler.getRankings());
            request.setAttribute("imageList", apiHandler.getImages());
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}

