package com.sorkin.ssp10.servlets;

import com.sorkin.ssp10.myclasses.User;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.HttpURLConnection;
import java.security.MessageDigest;

@WebServlet(name = "AuthServlet", urlPatterns = {"/AuthServlet"})
public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        //Проверка на то, что вход уже выполнен
        if (session.getAttribute("login") != null) {
            response.sendRedirect(this.getServletContext().getContextPath()
                    + "/LombardServlet");
//            getServletContext().getRequestDispatcher("/LombardServlet")
//                    .forward(request, response);
            return;
        }

        String login, password;
        login = request.getParameter("login");

        try {
            password = hashWithSHA256(request.getParameter("password"));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
            request.setAttribute("error", "Внутренняя ошибка");
            getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
            return;
        }

        User user = User.getUser(login, password);

        if (user == null) {
            response.setStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
            request.setAttribute("error", "Неверные учётные данные!!!");
            getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
        } else {

            //Сессия
            session.setAttribute("login", user.getLogin());
            session.setAttribute("role", user.getRole());
            session.setAttribute("id", user.getId());

            response.sendRedirect(this.getServletContext().getContextPath()
                    + "/LombardServlet");
            //getServletContext().getRequestDispatcher("/LombardServlet").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String login, password, role;
        login = request.getParameter("login").trim();
        role = request.getParameter("role");

        try {
            password = hashWithSHA256(request.getParameter("password").trim());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
            request.setAttribute("error", "Внутренняя ошибка");
            getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
            return;
        }

        Boolean exists = User.exists(login);
        if (exists == null) {
            response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
            request.setAttribute("error", "Ошибка доступа к БД!!!");
            getServletContext().getRequestDispatcher("/errorPage.jsp").forward(request, response);
            return;
        } else if (exists == true) {
            request.setAttribute("result", "<p class=\"text-danger\">Пользователь уже существует!!!ы</p>");
            getServletContext().getRequestDispatcher("/register.jsp")
                    .forward(request, response);
        }

        User user = User.createUser(login, password, role);

        if (user == null) {
            response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
            request.setAttribute("error", "Ошибка доступа к БД!!!");
            getServletContext().getRequestDispatcher("/errorPage.jsp")
                    .forward(request, response);
        } else {
            request.setAttribute("result", "<p class=\"text-success\">Пользователь зарегистрирован</p>");
            getServletContext().getRequestDispatcher("/register.jsp")
                    .forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public String hashWithSHA256(String data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
