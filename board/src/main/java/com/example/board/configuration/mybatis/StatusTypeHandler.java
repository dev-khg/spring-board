package com.example.board.configuration.mybatis;

import com.example.board.server.enums.Status;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class StatusTypeHandler extends BaseTypeHandler<Status> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Status parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public Status getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getStatusEnum(rs.getString(columnName));
    }

    @Override
    public Status getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getStatusEnum(rs.getString(columnIndex));
    }

    @Override
    public Status getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getStatusEnum(cs.getString(columnIndex));
    }

    private Status getStatusEnum(String str){
        //System.out.println(str);
        return Arrays.stream(Status.values()).filter(
                arg -> arg.getValue().equals(str)
        ).findAny().orElseThrow();
    }
}
