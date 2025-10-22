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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.*;

@WebFilter(filterName = "LogFilter", urlPatterns = {"/*"})
public class LogFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(LogFilter.class.getName());

    private static final DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);      
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        long startTime = System.currentTimeMillis();

        // Логируем информацию о запросе
        logRequestInfo(httpRequest);

        try {
            // Продолжаем цепочку фильтров
            chain.doFilter(request, response);
        } finally {
            // Логируем информацию после обработки запроса
            long endTime = System.currentTimeMillis();
            logResponseInfo(httpRequest, startTime, endTime);
        }
    }

    private void logRequestInfo(HttpServletRequest request) {
        String timestamp = LocalDateTime.now().format(formatter);
        String method = request.getMethod();
        String servletName = request.getHttpServletMapping().getServletName();
        String requestURI = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        String sessionId = request.getSession(false) != null
                ? request.getSession().getId() : "no-session";

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("======= REQUEST LOG ========\n")
                .append("Timestamp: ").append(timestamp).append("\n")
                .append("Method: ").append(method).append("\n")
                .append("Servlet: ").append(servletName).append("\n")
                .append("URI: ").append(requestURI).append("\n")
                .append("Remote Address: ").append(remoteAddr).append("\n")
                .append("Session ID: ").append(sessionId).append("\n")
                .append("User-Agent: ").append(userAgent).append("\n")
                .append("=== END REQUEST ===\n");

        //ЗАМЕНИТЬ НА LOG4J
        //System.out.println(logMessage.toString());
        LOGGER.info(logMessage.toString());
    }

    private void logResponseInfo(HttpServletRequest request, long startTime, long endTime) {
        String timestamp = LocalDateTime.now().format(formatter);
        long duration = endTime - startTime;

        StringBuilder logMessage = new StringBuilder();
        logMessage.append("======== RESPONSE LOG ========\n")
                .append("Timestamp: ").append(timestamp).append("\n")
                .append("URI: ").append(request.getRequestURI()).append("\n")
                .append("Processing Time: ").append(duration).append("ms\n")
                .append("=== END RESPONSE ===\n");

        //ЗАМЕНИТЬ НА LOG4J
        //System.out.println(logMessage.toString());
        LOGGER.info(logMessage.toString());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
