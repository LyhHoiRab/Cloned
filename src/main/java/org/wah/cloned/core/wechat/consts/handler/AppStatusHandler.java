package org.wah.cloned.core.wechat.consts.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.wah.cloned.core.wechat.consts.AppStatus;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppStatusHandler extends BaseTypeHandler<AppStatus> implements TypeHandler<AppStatus>{

    public void setNonNullParameter(PreparedStatement ps, int i, AppStatus parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    public AppStatus getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return AppStatus.getById(rs.getInt(columnName));
    }

    public AppStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return AppStatus.getById(rs.getInt(columnIndex));
    }

    public AppStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return AppStatus.getById(cs.getInt(columnIndex));
    }
}
