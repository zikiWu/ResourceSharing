package com.zk.service.impl;

import com.zk.exception.BussinessException;
import com.zk.mapper.UserMapper;
import com.zk.po.enums.TextLengthEnum;
import com.zk.po.model.User;
import com.zk.po.query.UserQuery;
import com.zk.po.utils.Constants;
import com.zk.po.utils.StringUtils;
import com.zk.po.vo.UserVo;
import com.zk.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper<User, UserQuery> userMapper;

	@Override
	public void register(User user) throws BussinessException {
		String userName = user.getUserName();
		String password = user.getPassword();
		String email = user.getEmail();
		if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(email) || StringUtils.isEmpty(email)
				|| userName.length() < Constants.LENGTH_1 || userName.length() > Constants.LENGTH_20
				|| password.length() < Constants.LENGTH_6 || password.length() > Constants.LENGTH_16
				|| !StringUtils.isUserName(userName) || !StringUtils.isPassword(password)|| !StringUtils.isEmail(email)
		){
			throw new BussinessException("输入参数不合法");
		}
		if(this.findUserByUserName(userName) != null){
			throw new BussinessException("用户名已存在");
		}
		if(this.findUserByEmail(email) != null){
			throw new BussinessException("邮箱已存在");
		}
		Date date = new Date();
		user.setRegisterTime(date);
		user.setLastLoginTime(date);
		user.setUserIcon(StringUtils.getRandomUserIcon());
		user.setUserBg(StringUtils.getRandomUserBg());
		//md5加密密码
		user.setPassword(StringUtils.encode(password));
		this.userMapper.insert(user);
		String dest = "user_icon/" + user.getUserid() + ".jpg";
		copyUserIcon(user.getUserIcon(), dest);
		user.setUserIcon(dest);
		updateUserWithoutValidate(user);
	}

	@Override
	public User findUserByUserName(String userName) {
		UserQuery userQuery = new UserQuery();
		userQuery.setUserName(userName);
		List<User> userList = userMapper.selectList(userQuery);
		if(userList.size() == 1){
			return userList.get(0);
		}
		return null;
	}

	@Override
	public User findUserByEmail(String email) {
		UserQuery userQuery = new UserQuery();
		userQuery.setEmail(email);
		List<User> userList = userMapper.selectList(userQuery);
		if(userList.size() == 1){
			return userList.get(0);
		}
		return null;
	}

	@Override
	public User findUserByUserid(Integer userid) {
		UserQuery userQuery = new UserQuery();
		userQuery.setUserid(userid);
		List<User> userList = userMapper.selectList(userQuery);
		if(userList.size() == 1){
			return userList.get(0);
		}
		return null;
	}

	@Override
	public User login(String account, String password) throws BussinessException {
		if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
			throw new BussinessException("输入参数不合法");
		}
		User user = null;
		if(account.contains("@")){
			user = this.findUserByEmail(account);
		}
		else {
			user = this.findUserByUserName(account);
		}
		if(user == null){
			throw new BussinessException("用户不存在");
		}
		if(!StringUtils.encode(password).equals(user.getPassword())){
			throw new BussinessException("密码错误");
		}
		user.setLastLoginTime(new Date());
		this.userMapper.update(user);
		return user;
	}

	@Override
	public User login(String account, String password, boolean isNeedEncoder) throws BussinessException {
		return null;
	}

	@Override
	public void sendCheckCode(String email) throws BussinessException {

	}

	@Override
	public void modifyPassword(String email, String password, String checkcode) throws BussinessException {

	}

	@Override
	public void addMark(int mark, int userid) {

	}

	@Override
	public void changeMark(int userid, int mark) {
		this.userMapper.changeUserMark(mark, userid);
	}

	@Override
	public User findUserInfo4UserHome(Integer userId) throws BussinessException {
		User user = this.findUserByUserid(userId);
		if(user == null){
			throw new BussinessException("用户不存在");
		}
		user.setPassword(null);
		user.setActivationCode(null);
		return user;
	}

	@Override
	public void updateUserInfo(User user) throws BussinessException {

		this.userMapper.update(user);
	}

	@Override
	public void updatePassword(Integer userId, String oldPassword, String newPassword) throws BussinessException {

	}

	@Override
	public void copyUserIcon(String source, String dest) {

	}

	@Override
	public void updateUserWithoutValidate(User user) {

	}

	@Override
	public String findHeadIcon(String account) throws BussinessException {
		if(StringUtils.isEmpty(account)){
			throw new BussinessException("用户名或者邮箱不能为空");
		}
		User user = null;
		if(account.contains("@")){
			user = this.findUserByEmail(account);
		}
		else {
			user = this.findUserByUserName(account);
		}
		if(user == null){
			throw new BussinessException("用户不存在");
		}
		return user.getUserIcon();
	}

	@Override
	public List<User> findAllUsers() {
		return this.userMapper.selectList(new UserQuery());
	}

	@Override
	public void deleteUser(Integer[] userIds) throws BussinessException {
		userMapper.delete(userIds);
	}

	@Override
	public List<UserVo> findUserVoList() {
		return null;
	}

	@Override
	public void updateUserRole(Integer userId, Integer[] roleIds) throws BussinessException {

	}

	@Override
	public void updateBatchUserRole(Integer[] userId, Integer[] roleIds) throws BussinessException {

	}

	@Override
	public void markChangeAdvice(Integer[] userIds, Integer mark, String des) throws BussinessException {

	}

//	@Autowired
//	private MailConfig mailConfig;
	
//	@Autowired
//	private SysUserRoleMapper<SysUserRole, SysUserRoleQuery> sysUserRoleMapper;
	

}
