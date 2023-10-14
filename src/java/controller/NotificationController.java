package controller;

import com.google.gson.Gson;
import dao.NotificationDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Notification;
import model.User;

@WebServlet(name = "NotificationController", urlPatterns = {"/notification"})
public class NotificationController extends HttpServlet {

    public void createNotification(int fromUserId, int toUserId, String title, String content) {
        new NotificationDAO().createNotification(fromUserId, toUserId, title, content);
    }

    private void getNotifications(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = -1;
        if (req.getSession().getAttribute("user") != null) {
            userId = ((User) req.getSession().getAttribute("user")).getUserId();
        } else {
            return;
        }
        ArrayList<Notification> notifications = new NotificationDAO().getNotificationsToUser(userId);

        String json = new Gson().toJson(notifications);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    private void markAsRead(HttpServletRequest req) {
        int notificationId = Integer.parseInt(req.getParameter("notificationId"));

        new NotificationDAO().markAsRead(notificationId);
    }

    private void markAllAsRead(HttpServletRequest req) {
        int userId = ((User) req.getSession().getAttribute("user")).getUserId();

        new NotificationDAO().markAllAsRead(userId);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action") != null) {
            switch (req.getParameter("action")) {
                case "getNotifications":
                    this.getNotifications(req, resp);
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action") != null) {
            switch (req.getParameter("action")) {
                case "markAsRead":
                    this.markAsRead(req);
                    break;
                case "markAllAsRead":
                    this.markAllAsRead(req);
                    break;
            }
        }
    }

}
