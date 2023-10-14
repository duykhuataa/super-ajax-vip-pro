package dao;

import connection._DBContext;
import model.Student;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Role;
import model.Teacher;

public class StudentDAO extends _DBContext {

    public Student getStudentById(String id) {
        try {
            String sqlQuery = "SELECT  u.*, r.RoleName from  dbo.[User] u\n"
                    + "join [Role] r on r.RoleId = u.RoleId where u.UserId = ? ";
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return new Student(
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

    public ArrayList<Student> getStudentList() {
        ArrayList<Student> listStudent = new ArrayList<>();
        try {
            String strSQL = "SELECT u.*, r.RoleName from  dbo.[User] u\n"
                    + "join [Role] r on r.RoleId = u.RoleId where u.RoleId = 0";

            PreparedStatement stm = connection.prepareStatement(strSQL);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Student student = new Student(
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
                listStudent.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStudent;
    }
}
