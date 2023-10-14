package controller.auth;

import dao.UserDAO;
import dto.UserGoogleDTO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterGoogleController", urlPatterns = {"/googleRegister"})
public class _RegisterGoogleController extends HttpServlet {

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("auth/register.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        UserDAO userDao = new UserDAO();
//        UserGoogleDTO userGoogleDTO = (UserGoogleDTO) req.getSession().getAttribute("userGoogleDTO");
//
//        String username = req.getParameter("username");
//
//        if (userDao.isUserExist(UserDAO.LOGIN_USERNAME, username)) {
//            req.setAttribute("log", "That username has already been registered");
//            req.getRequestDispatcher("auth/register.jsp").forward(req, resp);
//        }
//
//        String role = req.getParameter("role");
//        int roleId = (role.equals("student")) ? 0 : 1;
//
//        req.getSession().setAttribute("user",
//                userDao.createUser(username, null, userGoogleDTO.getId(),
//                        userGoogleDTO.getEmail(), roleId,
//                        userGoogleDTO.getName(), userGoogleDTO.getPicture())
//        );
//     
//        req.getSession().removeAttribute("userGoogleDTO");
//        resp.sendRedirect(req.getContextPath() + "/");
//    }

}
