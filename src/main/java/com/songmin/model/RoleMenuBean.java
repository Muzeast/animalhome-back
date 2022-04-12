package com.songmin.model;

public class RoleMenuBean {
    private String roleMenuId;
    private String roleId;
    private String menuId;

    public String getRoleMenuId() {
        return roleMenuId;
    }

    public void setRoleMenuId(String roleMenuId) {
        this.roleMenuId = roleMenuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return this.getRoleId() + "&" + this.getMenuId();
    }

    @Override
    public boolean equals(Object obj) {
        if (((RoleMenuBean)obj).getRoleId().equals(roleId) && ((RoleMenuBean)obj).getMenuId().equals(menuId)) {
            return true;
        } else {
            return false;
        }
    }
}
