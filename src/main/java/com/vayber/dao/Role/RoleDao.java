package com.vayber.dao.Role;

import com.vayber.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RoleDao {
    //获取角色列表    下拉框选项
    public List<Role> getRoleList(Connection conn) throws SQLException;
}
