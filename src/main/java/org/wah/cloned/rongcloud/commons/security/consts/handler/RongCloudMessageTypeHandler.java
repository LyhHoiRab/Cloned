package org.wah.cloned.rongcloud.commons.security.consts.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.wah.cloned.rongcloud.commons.security.consts.MessageType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RongCloudMessageTypeHandler extends BaseTypeHandler<MessageType> implements TypeHandler<MessageType>{

    public RongCloudMessageTypeHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, MessageType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getId());
    }

    public MessageType getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return MessageType.getById(rs.getInt(columnName));
    }

    public MessageType getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return MessageType.getById(rs.getInt(columnIndex));
    }

    public MessageType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return MessageType.getById(cs.getInt(columnIndex));
    }
}
