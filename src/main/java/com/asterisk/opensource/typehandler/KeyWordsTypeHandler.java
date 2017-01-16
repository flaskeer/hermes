package com.asterisk.opensource.typehandler;

import com.google.common.base.Joiner;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author dongh38@ziroom
 * @Date 2017/1/16
 * @Time 11:05
 * @Description
 * @Since 1.0.0
 */
@MappedJdbcTypes(includeNullJdbcType = true,value = {JdbcType.VARCHAR})
public class KeyWordsTypeHandler extends BaseTypeHandler<List<?>> {


    private String listToString(List<?> parameter) {
        return Joiner.on("^^^").join(parameter);
    }

    private List<?> stringToList(String s) {
        return Arrays.asList(s.split("^^^"));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<?> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,listToString(parameter));
    }

    @Override
    public List<?> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return s == null ? null : stringToList(s);
    }

    @Override
    public List<?> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String s = rs.getString(columnIndex);
        return s == null ? null : stringToList(s);
    }

    @Override
    public List<?> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        return s == null ? null : stringToList(s);
    }
}
