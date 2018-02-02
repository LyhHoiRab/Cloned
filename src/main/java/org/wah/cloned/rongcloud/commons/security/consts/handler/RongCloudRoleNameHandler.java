package org.wah.cloned.rongcloud.commons.security.consts.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.wah.cloned.rongcloud.commons.security.consts.RoleName;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RongCloudRoleNameHandler extends BaseTypeHandler<RoleName> implements TypeHandler<RoleName>{

    public RongCloudRoleNameHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, RoleName parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getId());
    }

    public RoleName getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return RoleName.getById(rs.getInt(columnName));
    }

    public RoleName getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return RoleName.getById(rs.getInt(columnIndex));
    }

    public RoleName getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return RoleName.getById(cs.getInt(columnIndex));
    }
}
