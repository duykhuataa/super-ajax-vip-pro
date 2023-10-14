package controller.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "EnterOTPController", urlPatterns = {"/enterOTP"})
public class EnterOTPController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String value = request.getParameter("otp");
        String otp = (String) request.getSession().getAttribute("otp");
        if (value.equals(otp)) {
            //otp is true -> move to reset password
            request.getSession().getAttribute("email");
            request.getRequestDispatcher("auth/resetPassword.jsp").forward(request, response);
        } else {
            //user has to try again
            request.setAttribute("log", "Wrong OTP! Please try again!");
            request.getRequestDispatcher("auth/enterOTP.jsp").forward(request, response);
        }
    }
}
