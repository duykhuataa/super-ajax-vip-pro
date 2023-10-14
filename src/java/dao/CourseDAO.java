package dao;

import connection._DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Course;
import model.Class;
import model.Role;
import model.User;
import utils.Helper;

public class CourseDAO extends _DBContext {

    public ArrayList<Course> getCourseList() {
        ArrayList<Course> courseList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM dbo.Course";
            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                courseList.add(new Course(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getByte(5)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public ArrayList<Course> searchCourseList(String search) {
        ArrayList<Course> courseList = new ArrayList<>();
        try {
            String sql = "select *\n"
                    + "from Course \n"
                    + "where Course.CourseCode like ? or Course.CourseName like ? ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, "%" + search + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                courseList.add(new Course(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getByte(5)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public int createCourse(String courseCode, String courseName) {
        String sql = "insert into Course(CourseCode, CourseName)\n"
                + "  values(?, ?)";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, courseCode);
            ps.setString(2, courseName);
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Course getCourse(int courseId) {
        try {
            String sql = "select *\n"
                    + "from Course\n"
                    + "where CourseId = ?;";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return new Course(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4), rs.getByte(5)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCourse(String courseName) {
        int check = 0;
        try {
            String sql = "  UPDATE [dbo].Course\n"
                    + "  SET CourseName = ?\n"
                    + "  WHERE CourseId = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            stm.setString(1, courseName);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public boolean deleteCourse(String courseId) {
        int check = 0;
        try {
            String sql = " UPDATE [dbo].Course\n"
                    + " SET isDeleted = 1"
                    + " WHERE CourseId = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, courseId);
            check = stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public ArrayList<Object> getAllObjectByPage(int index, int numOfElementAtPage, String keyValue, String object, String userId) {
        ArrayList<Object> list = new ArrayList<>();
        boolean flag = (keyValue == null);
        String sql = "SELECT * FROM [" + object + "] o "
                + ((object.equals("User")) ? " JOIN Role r ON r.RoleId = o.RoleId" : "")
                + ((object.equals("Class")) ? " JOIN Course c on c.CourseId = o.CourseId" : "")
                + ((userId != null && flag) ? " join [ClassStudent] cs on cs.ClassId = o.ClassId\n" + "where UserId = ? " : "")
                + ((!flag) ? " WHERE " + Helper.getKeyWordSearch(object) : "")
                + " ORDER BY o." + object + "Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try ( PreparedStatement ps = connection.prepareStatement(sql)) {

            if (!flag) {
                if (object.equals("User") || object.equals("Class")) {
                    ps.setString(1, "%" + keyValue + "%");
                    ps.setString(2, "%" + keyValue + "%");
                    ps.setString(3, "%" + keyValue + "%");
                    ps.setInt(4, (index - 1) * numOfElementAtPage);
                    ps.setInt(5, numOfElementAtPage);
                } else {
                    ps.setString(1, "%" + keyValue + "%");
                    ps.setString(2, "%" + keyValue + "%");
                    ps.setInt(3, (index - 1) * numOfElementAtPage);
                    ps.setInt(4, numOfElementAtPage);
                }
            } else {
                if (userId != null) {
                    ps.setObject(1, userId);
                    ps.setInt(2, (index - 1) * numOfElementAtPage);
                    ps.setInt(3, numOfElementAtPage);
                } else {
                    ps.setInt(1, (index - 1) * numOfElementAtPage);
                    ps.setInt(2, numOfElementAtPage);
                }
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (object.equals("Class")) {
                    list.add(new Class(rs.getInt("ClassId"), rs.getString("ClassName"),
                            rs.getInt("CourseId"), rs.getString("ClassEnrollKey"),
                            rs.getTimestamp("DateCreated"), rs.getString("ClassCode"),
                            rs.getByte("IsDeleted")));
                } else if (object.equals("User")) {
                    list.add(new User(rs.getInt("UserId"), rs.getString("Username"),
                            rs.getString("Password"), rs.getString("OauthId"),
                            rs.getString("Email"), new Role(rs.getInt("RoleId"), rs.getString("RoleName")),
                            rs.getString("FullName"), rs.getString("ImgPath"),
                            rs.getTimestamp("DateCreated"), rs.getByte("IsDeleted"),
                            rs.getByte("IsActive")));
                } else {

                    list.add(new Course(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getByte(5)));
                }
            }

            rs.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Course getCourseByClassId(int classId) {

        String sql = "SELECT [CourseId]\n"
                + "  FROM [dbo].[Class]\n"
                + "  WHERE [ClassId] = ?";
        try ( Connection con = connection;  PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setInt(1, classId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int courseId = rs.getInt(1);
            sql = "SELECT * \n"
                    + "FROM [dbo].[Course]\n"
                    + "WHERE [CourseId] = ?";
            PreparedStatement pstate = connection.prepareStatement(sql);
            pstate.setInt(1, courseId);
            pstate.executeUpdate();
            while (rs.next()) {
                return new Course(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getByte(5)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCourseIdByClassId(int classId) {
    String sql = "SELECT CourseId FROM Class WHERE ClassId = ?";
    try (Connection con = connection;
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, classId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int courseId = rs.getInt("CourseId");
            return courseId;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Return an error indicator if no result is found or an error occurs
}


    
}

