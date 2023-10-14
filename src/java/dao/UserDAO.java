package dao;

import connection._DBContext;
import java.sql.Connection;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Role;

public class UserDAO extends _DBContext {

    public static String LOGIN_USERNAME = "Username", LOGIN_EMAIL = "Email";

    public void createUser(String username, String email, int roleId) {
         String sql = "insert into [User] (Username, Email, RoleId, IsActive)\n"
                + "values (?, ?, ?, 0)";
        try ( Connection con = new _DBContext().getConnection();  PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setInt(3, roleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUserExist(String field, String usernameOrPassword) {
        try {
            String sqlQuery = "SELECT * FROM dbo.[User]\n"
                    + "WHERE " + field + " = ?\n"
                    + "AND [User].IsDeleted = 0";
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            stm.setString(1, usernameOrPassword);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public User getUser(String field, String usernameOrEmail, String password) {
        try {
            String sqlQuery = "select u.*, r.RoleName from  dbo.[User] u\n"
                    + "join [Role] r on r.RoleId = u.RoleId  WHERE " + field + "  = ? AND Password = ?\n"
                    + "AND u.IsDeleted = 0";
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            stm.setString(1, usernameOrEmail);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return new User(
                        Integer.parseInt(String.valueOf(rs.getString(1))),
                        String.valueOf(rs.getString(2)),
                        String.valueOf(rs.getString(3)),
                        String.valueOf(rs.getString(4)),
                        String.valueOf(rs.getString(5)),
                        new Role(rs.getInt(6), rs.getNString(12)),
                        String.valueOf(rs.getString(7)),
                        String.valueOf(rs.getString(8)),
                        rs.getTimestamp(9),
                        rs.getByte(10),
                        rs.getByte(11)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public User getUser(String field, String usernameOrEmail) {
        try {
            String sqlQuery = "SELECT u.*, r.RoleName from dbo.[User] u \n"
                    + "join [Role] r on r.RoleId = u.RoleId  WHERE " + field + " = ?\n"
                    + "AND u.IsDeleted = 0";
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            stm.setString(1, usernameOrEmail);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return new User(
                        Integer.parseInt(String.valueOf(rs.getString(1))),
                        String.valueOf(rs.getString(2)),
                        String.valueOf(rs.getString(3)),
                        String.valueOf(rs.getString(4)),
                        String.valueOf(rs.getString(5)),
                        new Role(rs.getInt(6), rs.getNString(12)),
                        String.valueOf(rs.getString(7)),
                        String.valueOf(rs.getString(8)),
                        rs.getTimestamp(9),
                        rs.getByte(10),
                        rs.getByte(11)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public User getUserById(int id) {
        try {
            String sqlQuery = "SELECT u.*, r.RoleName FROM dbo.[User] u\n"
                    + "join [Role] r on r.RoleId = u.RoleId WHERE UserId = ?";
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                return new User(
                        Integer.parseInt(String.valueOf(rs.getString(1))),
                        String.valueOf(rs.getString(2)),
                        String.valueOf(rs.getString(3)),
                        String.valueOf(rs.getString(4)),
                        String.valueOf(rs.getString(5)),
                        new Role(rs.getInt(6), rs.getNString(12)),
                        String.valueOf(rs.getString(7)),
                        String.valueOf(rs.getString(8)),
                        rs.getTimestamp(9),
                        rs.getByte(10),
                        rs.getByte(11)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<User> getUserList() {
        ArrayList<User> listUsers = new ArrayList<>();
        try {
            String strSQL = "SELECT u.*, r.RoleName FROM dbo.[User] u"
                    + " join [Role] r on r.RoleId = u.RoleId";

            PreparedStatement stm = connection.prepareStatement(strSQL);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                User u = new User(
                        Integer.parseInt(String.valueOf(rs.getString(1))),
                        String.valueOf(rs.getString(2)),
                        String.valueOf(rs.getString(3)),
                        String.valueOf(rs.getString(4)),
                        String.valueOf(rs.getString(5)),
                        new Role(rs.getInt(6), rs.getNString(12)),
                        String.valueOf(rs.getString(7)),
                        String.valueOf(rs.getString(8)),
                        rs.getTimestamp(9),
                        rs.getByte(10),
                        rs.getByte(11)
                );
                listUsers.add(u);
            }

        } catch (Exception e) {
            System.out.println("getListUser: " + e.getMessage());
        }
        return listUsers;
    }

    public User getUserForgotPass(String email) {
        try {
            String sqlQuery = "SELECT u.*, r.RoleName FROM dbo.[User] u\n"
                    + "join [Role] r on r.RoleId = u.RoleId WHERE Email = ?";
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return new User(
                        Integer.parseInt(String.valueOf(rs.getString(1))),
                        String.valueOf(rs.getString(2)),
                        String.valueOf(rs.getString(3)),
                        String.valueOf(rs.getString(4)),
                        String.valueOf(rs.getString(5)),
                        new Role(rs.getInt(6), rs.getNString(12)),
                        String.valueOf(rs.getString(7)),
                        String.valueOf(rs.getString(8)),
                        rs.getTimestamp(9),
                        rs.getByte(10),
                        rs.getByte(11)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateGoogleUser(String email, String oauthId,
            String fullName, String imgPath) {
        String sql = "update [User]\n"
                + "set OauthId = ?, FullName = ?, ImgPath = ?\n"
                + "where Email = ? and IsDeletd= 0";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, oauthId);
            ps.setString(2, fullName);
            ps.setString(3, imgPath);
            ps.setString(4, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public boolean updateUserProfile(String userName, String fullName, String password, String userId, String roleId, String Img) {
        int check = 0;
        String sql = (roleId == null)
                ? "UPDATE dbo.[User] SET UserName = ?, FullName = ?, Password = ? WHERE UserId = ?"
                : "UPDATE dbo.[User] SET UserName = ?, FullName = ?, Password = ?, RoleId = ?, ImgPath = ? WHERE UserId = ?";

        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setObject(1, userName);
            ps.setObject(2, fullName);
            ps.setObject(3, password);
            if (roleId == null) {
                ps.setObject(4, userId);
            } else {
                ps.setObject(4, roleId);
                ps.setObject(5, Img);
                ps.setObject(6, userId);
            }
            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean deleteUser(String userId) {
        int check = 0;
        String sql = "update [User] set isDeleted = 1 and IsActive = 0 where UserId = ?";

        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setObject(1, userId);
            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public void changePassword(String email, String password) {
        try {
            String sqlQuery = "UPDATE [dbo].[User]\n"
                    + "  SET Password = ?\n"
                    + "  WHERE Email = ?";
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            stm.setString(1, password);
            stm.setString(2, email);
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean activeAccountUser(String email) {
        String sql = "update [User] set IsActive = 1 where [User].Email = ?";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, email);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
