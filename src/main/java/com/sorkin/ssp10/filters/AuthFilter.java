package com.sorkin.ssp10.filters;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.HttpURLConnection;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    private static final String[] PUBLIC_PATHS = {"/register.jsp",
        "/login.jsp", "/errorPage.jsp", "SSP10/", "/AuthServlet"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //Проверка параметра сессии для определения регистрации
        HttpSession session = httpRequest.getSession();

        String url = httpRequest.getRequestURI();

        if (session.getAttribute("login") == null && !this.isPublic(url)) {
            httpResponse.setStatus(HttpURLConnection.HTTP_FORBIDDEN);
            httpRequest.setAttribute("error", "Для доступа к странице требуется авторизация");

            session.getServletContext().getRequestDispatcher("/errorPage.jsp")
                    .forward(httpRequest, httpResponse);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean isPublic(String url) {
        for (String public_path : PUBLIC_PATHS) {
            if (url.endsWith(public_path)) {
                return true;
            }
        }
        return false;
    }
}
