package com.songmin.model;

public class UserRoleBean {
    private String userRoleId;
    private String userId;
    private String roleId;

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return this.roleId + "&" + this.userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (((UserRoleBean)obj).getRoleId().equals(roleId) && ((UserRoleBean)obj).getUserId().equals(userId)) {
            return true;
        } else {
            return false;
        }
    }
}
