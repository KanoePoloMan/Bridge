package s21.domain.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends GenericFilterBean {
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String method = request.getMethod();
        String contentType = request.getContentType();
        String servletPath = request.getServletPath();

        if(method.equalsIgnoreCase("POST") && contentType.equalsIgnoreCase("application/x-www-form-urlencoded")
            && servletPath.equals("/authentication")) {

            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if(username != null) {
                if(authenticationService.authorization(username, password)) {
                    chain.doFilter(request, response);
                    return;
                } 
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid login or pasword");
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication with null user");
            }
        } else {
            chain.doFilter(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

}
