package dao;

import connection._DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoleDAO extends _DBContext {

    public static int getRoleIdByRoleName(String roleName) {
        try {
            String sqlQuery = "select *\n"
                    + "from Role\n"
                    + "where RoleName = ?";
            PreparedStatement stm = new _DBContext().getConnection().prepareStatement(sqlQuery);
            stm.setString(1, roleName);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
