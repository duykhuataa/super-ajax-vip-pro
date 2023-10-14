package dao;

import connection._DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Role;
import model.Teacher;

public class TeacherDAO extends _DBContext {

    public ArrayList<Teacher> getTeacherList() {
        ArrayList<Teacher> teacherList = new ArrayList<>();
        try {
            String strSQL = "SELECT  u.*, r.RoleName from  dbo.[User] u\n"
                    + "join [Role] r on r.RoleId = u.RoleId where u.RoleId = 1\n"
                    + "AND u.IsDeleted = 0";

            PreparedStatement stm = connection.prepareStatement(strSQL);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Teacher teacher = new Teacher(
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
                teacherList.add(teacher);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacherList;
    }
}
