package Servlets;

import Handlers.APIHandler;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

        //request.getRequestDispatcher("/web/index.jsp").forward(request, response);
        List<String> itemsList = apiHandler.getSearchResultsTitle(input, category);
        List<String> urlList = apiHandler.getSearchResultsURLs(input, category);

        request.setAttribute("itemsList", itemsList);
        request.setAttribute("urlList", urlList);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}

