package com.vayber.services.Role;

import com.vayber.dao.BaseDao;
import com.vayber.dao.Role.RoleDao;
import com.vayber.dao.Role.RoleDaoImpl;
import com.vayber.pojo.Role;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class roleServicesImpl implements roleServices{
    //引入dao
    private RoleDao roleDao;

    public roleServicesImpl(){
         roleDao = new RoleDaoImpl();
    }


    public List<Role> getRoleList() {

        Connection connection = null;
        List<Role> roleList = null;

        try {
            connection = BaseDao.getConnection();
             roleList = roleDao.getRoleList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection,null,null);
        }

        return roleList;
    }


    @Test
    public void test(){
        roleServicesImpl roleService = new roleServicesImpl();
        List<Role> roleList = roleService.getRoleList();
        for (Role role: roleList) {
            System.out.println(role.getRoleName());
        }
    }

}
