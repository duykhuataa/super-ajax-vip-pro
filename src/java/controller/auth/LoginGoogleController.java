package controller.auth;

import dao.UserDAO;
import java.io.IOException;

import dto.UserGoogleDTO;
import utils.Helper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginGoogleController", urlPatterns = {"/LoginGoogleController"})
public class LoginGoogleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        String accessToken = Helper.getToken(code);

        UserGoogleDTO userInfo = Helper.getUserInfo(accessToken);
        UserDAO userDao = new UserDAO();
        if (!userInfo.getEmail().endsWith("@fpt.edu.vn")) {
            request.getSession().setAttribute("log", "Only @fpt.edu.vn emails.");
            response.sendRedirect("./login");
            return;
        } else if (!userDao.isUserExist(UserDAO.LOGIN_EMAIL, userInfo.getEmail())) {
            request.getSession().setAttribute("log", "Account not registered. Contact admin");
            response.sendRedirect("./login");
            return;
        } else {
           if( userDao.getUserForgotPass(userInfo.getEmail()).getOauthId().equals("null")){
            userDao.updateGoogleUser(userInfo.getEmail(), userInfo.getId(),
                    userInfo.getName(), userInfo.getPicture());
            }
            request.getSession().setAttribute("user",
                    userDao.getUser(UserDAO.LOGIN_EMAIL, userInfo.getEmail()));
            response.sendRedirect(request.getContextPath());
            return;
        }

        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
