package org.wah.cloned.im.tencent.consts.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.wah.cloned.im.tencent.consts.IMRole;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IMRoleHandler extends BaseTypeHandler<IMRole> implements TypeHandler<IMRole>{

    public void setNonNullParameter(PreparedStatement ps, int i, IMRole parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public IMRole getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return IMRole.getById(rs.getInt(columnName));
    }

    public IMRole getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return IMRole.getById(rs.getInt(columnIndex));
    }

    public IMRole getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return IMRole.getById(cs.getInt(columnIndex));
    }
}
