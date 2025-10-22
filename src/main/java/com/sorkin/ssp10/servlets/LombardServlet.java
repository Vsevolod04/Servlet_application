package com.sorkin.ssp10.servlets;

import com.sorkin.ssp10.myclasses.LombardItem;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.util.ArrayList;

@WebServlet(name = "LombardServlet", urlPatterns = {"/LombardServlet"})
public class LombardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("DELETE".equals(request.getParameter("method"))) {
            this.doDelete(request, response);
            return;
        }

        ArrayList<LombardItem> items = LombardItem.getAllItems();

        if (items == null) {
            response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
            request.setAttribute("error", "Ошибка доступа к БД!");
            this.getServletContext().getRequestDispatcher("/errorPage.jsp")
                    .forward(request, response);
        }

        request.setAttribute("items", items);
        this.getServletContext().getRequestDispatcher("/welcome.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if ("PUT".equals(request.getParameter("method"))) {
            this.doPut(request, response);
            return;
        }

        String client = request.getParameter("client");
        String item = request.getParameter("item");
        String cost = request.getParameter("cost");

        if (!LombardItem.addItem(client, item, cost)) {
            response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
            request.setAttribute("error", "Не удалось добавить:(");
            this.getServletContext().getRequestDispatcher("/errorPage.jsp")
                    .forward(request, response);
            return;
        }

        response.sendRedirect(this.getServletContext().getContextPath() + "/LombardServlet");

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int delId;
        try {
            delId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException ex) {
            response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
            request.setAttribute("error", "Ошибка в URL");
            this.getServletContext().getRequestDispatcher("/errorPage.jsp")
                    .forward(request, response);
            return;
        }

        if (!LombardItem.deleteItem(delId)) {
            response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
            request.setAttribute("error", "Не удалось удалить:(");
            this.getServletContext().getRequestDispatcher("/errorPage.jsp")
                    .forward(request, response);
            return;
        }

        response.sendRedirect(this.getServletContext().getContextPath() + "/LombardServlet");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String client = request.getParameter("client");
        String item = request.getParameter("item");
        String cost = request.getParameter("cost");

        if (!LombardItem.updateItem(id, client, item, cost)) {
            response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
            request.setAttribute("error", "Не удалось обновить:(");
            this.getServletContext().getRequestDispatcher("/errorPage.jsp")
                    .forward(request, response);
            return;
        }

        response.sendRedirect(this.getServletContext().getContextPath() + "/LombardServlet");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
