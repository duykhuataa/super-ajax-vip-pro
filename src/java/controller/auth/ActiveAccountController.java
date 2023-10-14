package controller.auth;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Helper;


@WebServlet(name = "ActiveAccountController", urlPatterns = {"/active"})
public class ActiveAccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String password = Helper.generatePassword();
        new UserDAO().changePassword(request.getParameter("email"), password);
        request.setAttribute("username", request.getParameter("username"));
        request.setAttribute("pass", password);
        request.getRequestDispatcher("common/verify.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
