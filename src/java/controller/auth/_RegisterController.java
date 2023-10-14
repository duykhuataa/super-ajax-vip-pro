package controller.auth;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class _RegisterController extends HttpServlet {

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("auth/register.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        UserDAO userDao = new UserDAO();
//
//        String username = req.getParameter("username");
//        String email = req.getParameter("email");
//
//        if (userDao.isUserExist(UserDAO.LOGIN_USERNAME, username)) {
//            req.setAttribute("log", "That username has already been registered");
//            req.getRequestDispatcher("auth/register.jsp").forward(req, resp);
//        }
//
//        if (userDao.isUserExist(UserDAO.LOGIN_EMAIL, email)) {
//            req.setAttribute("log", "That email address has already been registered");
//            req.getRequestDispatcher("auth/register.jsp").forward(req, resp);
//        }
//
//        String password = req.getParameter("password");
//
//        String role = req.getParameter("role");
//        int roleId = (role.equals("student")) ? 0 : 1;
//
//        req.getSession().setAttribute("user", 
//                userDao.createUser(username, password, 
//                        null, email, roleId, username, null)
//        );
//        resp.sendRedirect("./");
//    }
}
