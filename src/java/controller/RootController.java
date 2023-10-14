package controller;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import dao.ClassDAO;
import dao.CourseDAO;
import model.Class;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import utils.Helper;

@WebServlet(name = "RootController", urlPatterns = {""})
public class RootController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            req.getRequestDispatcher("landingPage.jsp").forward(req, resp);
            return;
        }else {
            resp.sendRedirect("class");
            return;
        }
    }

}
