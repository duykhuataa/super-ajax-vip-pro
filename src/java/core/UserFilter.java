package core;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Role;
import model.User;

@WebFilter(filterName = "UserFilter", urlPatterns = {"/profile", "/process", "/class", "/exam","/notification","/examAttemp"})
public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if(request.getRequestURI().equals("/exam")) {
                if(user.getRole().getRoleId() == Role.ROLE_TEACHER){
                    chain.doFilter(req, res);
                }else {
                    response.sendRedirect("./error");
                    return;
                }
            } 
            if(request.getRequestURI().equals("/examAttemp")) {
                if(user.getRole().getRoleId() == Role.ROLE_STUDENT){
                    chain.doFilter(req, res);
                }else {
                    response.sendRedirect("./error");
                    return;
                }
            } 
            chain.doFilter(req, res);
        } else {
            response.sendRedirect("./error");
            return;
        }
    }

}
