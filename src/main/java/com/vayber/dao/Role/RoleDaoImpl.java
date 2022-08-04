package com.vayber.dao.Role;

import com.vayber.dao.BaseDao;
import com.vayber.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao{

    public List<Role> getRoleList(Connection conn) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Role> roleList = new ArrayList<Role>();

        if (conn != null){
            String sql = "select * from smbms_role";
            Object[] params = {};

            resultSet = BaseDao.execute(conn, preparedStatement, resultSet, sql, params);

            while(resultSet.next()){
                Role role = new Role();

                role.setId(resultSet.getInt("id"));
                role.setRoleCode(resultSet.getString("roleCode"));
                role.setRoleName(resultSet.getString("roleName"));

                roleList.add(role);

            }

            BaseDao.closeResource(null,preparedStatement,resultSet);
        }

        return roleList;
    }
}
