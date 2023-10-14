package controller.auth;

import java.io.IOException;

import dao.UserDAO;
import model.User;
import utils.Helper;
import utils.Mail;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ForgetPwdController", urlPatterns = {"/forgetPassword"})
public class ForgetPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("auth/forgetPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        UserDAO ud = new UserDAO();
        User user = ud.getUserForgotPass(email);
        if (user == null) {
            request.setAttribute("log", "The email is not exist");
            request.getRequestDispatcher("auth/forgetPassword.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("email", email);
            String otp = Helper.getRandomNumberString();
            request.getSession().setAttribute("otp", otp);
            System.out.println(otp + "*");
            Mail.send(email, "EMS - RESET PASSWORD", otp);
            request.getRequestDispatcher("auth/enterOTP.jsp").forward(request, response);
        }
    }

}
