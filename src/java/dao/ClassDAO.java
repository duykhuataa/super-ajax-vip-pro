package dao;

import connection._DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Class;
import model.Course;
import model.Role;
import model.User;
import utils.Helper;

public class ClassDAO extends _DBContext {

    public void createClass(String className, int courseId, int teacherId) {
        String sql = "insert into Class (ClassName, CourseId, ClassEnrollKey) \n"
                + "values (?, ?, ?)";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, className);
            ps.setInt(2, courseId);
            ps.setString(3, Helper.getRandomString(8));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int classId = rs.getInt(1);

            sql = "insert into ClassTeacher (UserId, ClassId) \n"
                    + "values (?, ?)";
            PreparedStatement pstate = con.prepareStatement(sql);
            pstate.setInt(1, teacherId);
            pstate.setInt(2, classId);
            pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Class> getClassList() {
        ArrayList<Class> classList = new ArrayList<>();
        String sql = "select * from [Class]";
        try ( Connection cnn = connection;  PreparedStatement ps = cnn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                classList.add(new Class(
                        rs.getInt(1),
                        rs.getNString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getTimestamp(5),
                        rs.getNString(7),
                        rs.getByte(6)
                ));
            }
            return classList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateClass(int classId, String className, int teacherId) {
        int check = 0;
        try {
            String strQuery = "UPDATE dbo.Class\n"
                    + "  SET ClassName = ?\n"
                    + "  WHERE ClassId = ?";
            PreparedStatement stm = connection.prepareStatement(strQuery);
            stm.setInt(1, classId);
            stm.setString(2, className);
            stm.executeUpdate();
            strQuery = "UPDATE ClassTeacher\n"
                    + "  SET UserId = ?\n"
                    + "  WHERE ClassId = ?";
            PreparedStatement pstate = connection.prepareStatement(strQuery);
            pstate.setInt(1, classId);
            pstate.setInt(2, teacherId);
            pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean deleteClass(String classId) {
        int check = 0;
        try {
            String strQuery = " DELETE dbo.Class\n"
                    + " SET isDeleted = 1"
                    + " WHERE ClassId = ?";
            PreparedStatement stm = connection.prepareStatement(strQuery);
            stm.setString(1, classId);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public ArrayList<Class> getClassListByUserId(int userId, int roleId) {
        ArrayList<Class> classList = new ArrayList<>();
        String sql = "select *\n"
                + "from Class\n"
                + (roleId == Role.ROLE_TEACHER
                        ? "inner join dbo.ClassTeacher CT on Class.ClassId = CT.ClassId\n"
                        : "inner join dbo.ClassStudent CS on Class.ClassId = CS.ClassId\n")
                + "where UserId = ?;";
        try ( PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setObject(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                classList.add(new Class(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getTimestamp(5),
                        rs.getNString(7),
                        rs.getByte(6)
                ));
            }
            return classList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Course getClassCourse(int courseId) {
        String sql = "select *\n"
                + "from Class\n"
                + "inner join dbo.Course C on Class.CourseId = C.CourseId\n"
                + "where C.CourseId = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Course(
                        rs.getInt(3),
                        rs.getNString(9),
                        rs.getNString(10),
                        rs.getNString(11)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addStudentIntoClass(int classId, ArrayList<Integer> studentIdList) {
        String sql = "insert into [ClassStudent] (classId, studentId) values \n"
                + "(?,?)";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql);) {
            for (int studentId : studentIdList) {
                ps.setInt(1, classId);
                ps.setInt(2, studentId);
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getTeacherByClassId(int classId) {
        String sql = "select *\n"
                + "from Class\n"
                + "inner join dbo.ClassTeacher CT on Class.ClassId = CT.ClassId\n"
                + "inner join dbo.[User] U on U.UserId = CT.UserId\n"
                + "where Class.ClassId = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, classId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        Integer.parseInt(String.valueOf(rs.getString(10))),
                        String.valueOf(rs.getString(11)),
                        String.valueOf(rs.getString(12)),
                        String.valueOf(rs.getString(13)),
                        String.valueOf(rs.getString(14)),
                        new Role(rs.getInt(15)),
                        String.valueOf(rs.getString(16)),
                        String.valueOf(rs.getString(17)),
                        rs.getTimestamp(18),
                        rs.getByte(19),
                        rs.getByte(20)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("null");
        return null;
    }

    public int getTotalOfPage(int numOfElementAtPage, String keyValue, String object, String userId) {
        System.out.println("Userid" + userId);
        String sql = (keyValue == null) ? "select Count(o." + object + "Id) from [" + object + "] o"
                + ((userId != null) ? " join [ClassStudent] cs on cs.ClassId = o.ClassId\n" + "where UserId = ?" : "")
                : "select Count(o." + object + "Id) from [" + object + "] o "
                + ((object.equals("Class")) ? " JOIN Course c on c.CourseId = o.CourseId" : "")
                + " where " + Helper.getKeyWordSearch(object);
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql);) {
            if (keyValue != null) {
                if (object.equals("User") || object.equals("Class")) {
                    ps.setString(1, "%" + keyValue + "%");
                    ps.setString(2, "%" + keyValue + "%");
                    ps.setString(3, "%" + keyValue + "%");
                } else {
                    ps.setString(1, "%" + keyValue + "%");
                    ps.setString(2, "%" + keyValue + "%");
                }
            } else {
                if (object.equals("User") || object.equals("Class")) {
                    if (userId != null) {
                        ps.setString(1, userId);
                    }
                }
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                double totalFood = rs.getDouble(1);
                double totalPage = 0;
                if (numOfElementAtPage > totalFood) {
                    return 1;
                } else {
                    totalPage = totalFood / numOfElementAtPage;
                    if (totalPage - (int) totalPage != 0) {
                        totalPage = (int) totalPage + 1;
                    }
                    return (int) totalPage;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return 0;
    }

    public void addStudentToClass(int classId, int StudentId) {
        try {
            String sql = "insert into [ClassStudent] (classId, UserId) values (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, classId);
            ps.setInt(2, StudentId);
            ps.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public Class getClassByEnroll(String Enroll) {
        try {
            String sql = "select * from [Class] where ClassEnrollKey = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, Enroll);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Class clas = new Class(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4),
                        rs.getTimestamp(5), rs.getNString(7), rs.getByte(6));
                return clas;
            }
        } catch (SQLException e) {

        }
        return null;
    }

    public ArrayList<User> getStudentListByClassId(int classId) {
        ArrayList<User> studentList = new ArrayList<>();
        String sql = "SELECT [U].*\n"
                + "FROM [dbo].[User] AS [U]\n"
                + "INNER JOIN [dbo].[ClassStudent] AS [CS] ON [U].[UserId] = [CS].[UserId]\n"
                + "WHERE [CS].[ClassId] = ?\n"
                + "  AND [U].[RoleId] = 0;";
        try ( Connection cnn = connection;  PreparedStatement ps = cnn.prepareStatement(sql)) {
            ps.setInt(1, classId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                studentList.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), new Role(rs.getInt(6)), rs.getString(7), rs.getString(8),
                        rs.getTimestamp(9), rs.getByte(10), rs.getByte(11)));
            }
            return studentList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
