package com.zk.po.handler;

import com.zk.po.enums.FileTopicType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileTopicTypeHandler extends BaseTypeHandler<FileTopicType> {

	

	@Override
	public FileTopicType getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		String type = rs.getString(columnName);
		if(type != null){
			return FileTopicType.getFileTopicTypeByType(type);
		}
		return null;
	}

	@Override
	public FileTopicType getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		String type = rs.getString(columnIndex);
		if(type != null){
			return FileTopicType.getFileTopicTypeByType(type);
		}
		return null;
	}

	@Override
	public FileTopicType getNullableResult(CallableStatement cs,
			int columnIndex) throws SQLException {
		String type = cs.getString(columnIndex);
		if(type != null){
			return FileTopicType.getFileTopicTypeByType(type);
		}
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			FileTopicType parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getType().toString());
	}



}
