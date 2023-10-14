package controller.auth;

import constant.Constant;
import java.io.IOException;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Role;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("loginGoogleURL", Constant.GOOGLE_URL_LOGIN);
        req.getRequestDispatcher("auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usernameOrEmail = (String) req.getParameter("usernameOrPassword");
        String password = (String) req.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(UserDAO.LOGIN_EMAIL, usernameOrEmail, password);
        if (user == null) {
            user = userDAO.getUser(UserDAO.LOGIN_USERNAME, usernameOrEmail, password);
            if (user == null) {
                req.getSession().setAttribute("log", "Username or password incorrect!");
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            } else {
                if (user.getIsActive() == 0) {
                    boolean check = new UserDAO().activeAccountUser(user.getEmail());
                }
            }
        } else {
            if (user.getIsActive() == 0) {
                boolean check = new UserDAO().activeAccountUser(user.getEmail());
            }
        }

        req.getSession().setAttribute("user", user);
        resp.sendRedirect(user.getRole().getRoleId() == Role.ROLE_ADMIN
                ? "./admin"
                : req.getContextPath()
        );
    }
}
