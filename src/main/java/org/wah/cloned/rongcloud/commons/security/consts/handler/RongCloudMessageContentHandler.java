package org.wah.cloned.rongcloud.commons.security.consts.handler;

import com.google.gson.reflect.TypeToken;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.wah.doraemon.utils.ObjectUtils;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class RongCloudMessageContentHandler extends BaseTypeHandler<Map<String, Object>> implements TypeHandler<Map<String, Object>>{

    private static final Type type = new TypeToken<Map<String, Object>>(){}.getType();

    public RongCloudMessageContentHandler(){

    }

    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType) throws SQLException{
        ps.setString(i, ObjectUtils.serialize(parameter));
    }

    public Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return ObjectUtils.deserialize(rs.getString(columnName), type);
    }

    public Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return ObjectUtils.deserialize(rs.getString(columnIndex), type);
    }

    public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return ObjectUtils.deserialize(cs.getString(columnIndex), type);
    }
}
