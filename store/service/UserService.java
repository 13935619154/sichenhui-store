package cn.tedu.store.service;

import cn.tedu.store.entity.User;

/**
 * 处理用户相关业务的接口
 */
public interface UserService {

	/**
	 * 用户注册
	 * @param user 新注册的用户信息
	 */
	void reg(User user);
	
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return 成功登录的用户数据
	 */
	User login(String username, String password);
	
	/**
	 * 修改密码
	 * @param uid 用户id
	 * @param username 用户名
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 */
	void changePassword(Integer uid, String username, String oldPassword, String newPassword);
	
	/**
	 * 查询某用户资料
	 * @param uid 用户id
	 * @return 该用户的资料
	 */
	User showInfo(Integer uid);
	
	/**
	 * 修改个人资料
	 * @param uid 用户id
	 * @param username 用户名
	 * @param user 用户新的资料
	 */
	void changeInfo(Integer uid, String username, User user);
	
	/**
	 * 修改用户头像
	 * @param uid 用户id
	 * @param username 用户名
	 * @param avatar 新的头像路径
	 */
	void changeAvatar(Integer uid, String username, String avatar);
	
}







