package com.cmc.dashboard.security;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author longl
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilterRequest implements Filter {


    private final Logger log = LoggerFactory.getLogger(CorsFilterRequest.class);

    public CorsFilterRequest() {
        log.info("1.--SimpleCORSFilter init");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
      log.info("1.---SimpleCORSFilter init ---- ");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Authorization, Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
//            String reqUrl = request.getRequestURI();
//            if(reqUrl.startsWith("/oauth/token")
//                    || reqUrl.startsWith("/api")) {
//                chain.doFilter(request, response);
//            } else {
//                try {
                    chain.doFilter(request, response);
//                } catch (Exception ex) {
//                    request.setAttribute("errorMessage", ex);
//                    request.getRequestDispatcher("/error.html")
//                            .forward(request, response);
//                }
            }
    }

    @Override
    public void init(FilterConfig filterConfig) {
      log.info("1.---SimpleCORSFilter init --- public void init(FilterConfig filterConfig)- ");
    }

    @Override
    public void destroy() {
      log.info("1.--SimpleCORSFilter init --- destroy()- ");
    }

}
