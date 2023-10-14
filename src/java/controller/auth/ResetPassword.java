package controller.auth;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ResetPassword", urlPatterns = {"/resetPassword"})
public class ResetPassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newPassword = (String) req.getParameter("newPassword");
        String confirmPassword = (String) req.getParameter("confirmPassword");
        String email = (String) req.getSession().getAttribute("email");
        UserDAO ud = new UserDAO();
        if (confirmPassword.equals(newPassword) || confirmPassword != null || newPassword != null) {
            if (ud.getUserForgotPass(email) != null) {
                ud.changePassword(email, newPassword);
            }
        } else {
            req.setAttribute("log", "Password does not match! Please try again");
            req.getRequestDispatcher("auth/resetPassword.jsp");
        }
        req.getSession().removeAttribute("email");
        resp.sendRedirect("/login");
    }

}
