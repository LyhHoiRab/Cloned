package org.wah.cloned.im.tencent.consts.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.wah.cloned.im.tencent.consts.IMMessageType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IMMessageTypeHandler extends BaseTypeHandler<IMMessageType> implements TypeHandler<IMMessageType>{

    public void setNonNullParameter(PreparedStatement ps, int i, IMMessageType parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public IMMessageType getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return IMMessageType.getById(rs.getInt(columnName));
    }

    public IMMessageType getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return IMMessageType.getById(rs.getInt(columnIndex));
    }

    public IMMessageType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return IMMessageType.getById(cs.getInt(columnIndex));
    }
}
