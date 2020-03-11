package com.zk.mapper;

import com.zk.po.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper<T, Q>  extends BaseMapper<T, Q>{
	public Integer changeUserMark(@Param(value = "changeMark") Integer changeMark, @Param(value = "userId") Integer userId);
	
	public void delete(@Param("ids") Integer[] ids);
	
	public List<UserVo> selectUserVoList();
	
}