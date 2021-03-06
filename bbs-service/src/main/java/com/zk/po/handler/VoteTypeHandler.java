package com.zk.po.handler;

import com.zk.po.enums.VoteType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VoteTypeHandler extends BaseTypeHandler<VoteType> {

	

	@Override
	public VoteType getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Integer type = rs.getInt(columnName);
		if(type != null){
			return VoteType.getVoteTypeByValue(type);
		}
		return null;
	}

	@Override
	public VoteType getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		Integer type = rs.getInt(columnIndex);
		if(type != null){
			return VoteType.getVoteTypeByValue(type);
		}
		return null;
	}

	@Override
	public VoteType getNullableResult(CallableStatement cs,
			int columnIndex) throws SQLException {
		Integer type = cs.getInt(columnIndex);
		if(type != null){
			return VoteType.getVoteTypeByValue(type);
		}
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			VoteType parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getType());
	}



}
