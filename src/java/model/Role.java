package model;

import dao.RoleDAO;

public class Role {
    
    public static int ROLE_STUDENT = RoleDAO.getRoleIdByRoleName("Student");
    public static int ROLE_TEACHER = RoleDAO.getRoleIdByRoleName("Teacher");
    public static int ROLE_ADMIN = RoleDAO.getRoleIdByRoleName("Admin");
    
    private int roleId;
    private String roleName;

    public Role() {
    }
   
    public Role(int roleId) {
        this.roleId = roleId;
    }
    
    public Role(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
}
