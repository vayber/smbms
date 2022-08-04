package com.vayber.services.Role;

import com.vayber.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface roleServices {

    //获取角色列表    下拉框
    public List<Role> getRoleList();
}
