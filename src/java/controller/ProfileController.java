package controller;

import dao.ClassDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Role;
import model.User;

@WebServlet(name = "UpdateProfile", urlPatterns = {"/profile"})
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int userId = Integer.parseInt(request.getParameter("id"));
        if (new UserDAO().getUserById(userId) == null) {
            response.sendRedirect("./error");
        } else {
            if (user.getRole().getRoleId() != Role.ROLE_ADMIN) {
                if (new UserDAO().getUserById(userId).getRole().getRoleId() == Role.ROLE_ADMIN) {
                    //message
                    response.sendRedirect("./error");
                    return;
                } else {
                    request.setAttribute("classList", new ClassDAO().getClassListByUserId(userId, new UserDAO().getUserById(userId).getRole().getRoleId()));
                    request.setAttribute("user", new UserDAO().getUserById(Integer.parseInt(request.getParameter("id"))));
                    request.getRequestDispatcher("profile/viewProfile.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("classList", new ClassDAO().getClassListByUserId(userId, new UserDAO().getUserById(userId).getRole().getRoleId()));
                request.setAttribute("user", new UserDAO().getUserById(Integer.parseInt(request.getParameter("id"))));
                request.getRequestDispatcher("profile/viewProfile.jsp").forward(request, response);
                return;
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO uDao = new UserDAO();
        boolean check = uDao.updateUserProfile(request.getParameter("name"),
                request.getParameter("fullname"), request.getParameter("pass"),
                request.getParameter("id"), request.getParameter("role"), request.getParameter("imgur"));
        System.out.println(request.getParameter("role"));
        System.out.println(check);
        User user = (User) request.getSession().getAttribute("user");
        request.getSession().setAttribute("messProfile", "Profile has been update successfully!");
        response.sendRedirect((user.getRole().getRoleId() == Role.ROLE_ADMIN) ? "./admin?section=users" : "./");
    }
}
