package org.wah.cloned.im.tencent.consts.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.wah.cloned.im.tencent.consts.IMType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IMTypeHandler extends BaseTypeHandler<IMType> implements TypeHandler<IMType>{

    public void setNonNullParameter(PreparedStatement ps, int i, IMType parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public IMType getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return IMType.getById(rs.getInt(columnName));
    }

    public IMType getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return IMType.getById(rs.getInt(columnIndex));
    }

    public IMType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return IMType.getById(cs.getInt(columnIndex));
    }
}
