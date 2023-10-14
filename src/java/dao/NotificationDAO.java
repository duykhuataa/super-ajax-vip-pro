package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import connection._DBContext;
import java.sql.SQLException;
import java.util.stream.Collectors;
import model.Notification;

public class NotificationDAO extends _DBContext {

    public void createNotification(int fromUserId, int toUserId, String title, String content) {
        String sqlQuery = "insert into Notification (FromUserId, ToUserId, Title, Content) \n"
                + "values (?, ?, ?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            stm.setInt(1, fromUserId);
            stm.setInt(2, toUserId);
            stm.setString(3, title);
            stm.setString(4, content);

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Notification> getNotificationsToUser(int userId) {
        ArrayList<Notification> notiList = new ArrayList<>();

        try {
            String sql = "select *\n"
                    + "from Notification\n"
                    + "where ToUserId = ?\n"
                    + "order by DateCreated desc ;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                notiList.add(new Notification(rs.getInt(1), rs.getInt(2),
                        rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getByte(6),
                        rs.getTimestamp(7), rs.getByte(8)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notiList;
    }

    public void markAsRead(int notificationId) {
        String sqlQuery = "update Notification\n"
                + "set IsRead = 1\n"
                + "where NotificationId = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            stm.setInt(1, notificationId);

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markAllAsRead(int userId) {
        String sqlQuery = "update Notification\n"
                + "set IsRead = 1\n"
                + "where ToUserId = ? and IsRead = 0\n";
        try {
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            stm.setInt(1, userId);

            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
