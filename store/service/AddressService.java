package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;

/**
 * 处理收货地址数据的业务层接口
 */
public interface AddressService {

	/**
	 * 增加收货地址
	 * 
	 * @param uid      用户id
	 * @param username 用户名
	 * @param address  收货地址数据
	 */
	void addnew(Integer uid, String username, Address address);

	/**
	 * 显示某用户的收货地址列表
	 * 
	 * @param uid 用户id
	 * @return 该用户的收货地址列表
	 */
	List<Address> showList(Integer uid);

	/**
	 * 设置默认收货地址
	 * 
	 * @param aid      收货地址数据的id
	 * @param uid      当前登录的用户id
	 * @param username 当前登录的用户名
	 */
	void setDefault(Integer aid, Integer uid, String username);

	/**
	 * 删除收货地址
	 * 
	 * @param aid      收货地址数据的id
	 * @param uid      当前登录的用户id
	 * @param username 当前登录的用户名
	 */
	void delete(Integer aid, Integer uid, String username);

	/**
	 * 根据收货地址id获取收货地址详情
	 * 
	 * @param aid 收货地址数据的id
	 * @param uid 当前登录的用户id
	 * @return 匹配的收货地址详情
	 */
	Address getByAid(Integer aid, Integer uid);

}
