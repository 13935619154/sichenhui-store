package cn.tedu.store.service;

import cn.tedu.store.util.PageData;

/**
 * 处理收藏数据的业务层接口
 */
public interface FavoriteService {

	/**
	 * 添加收藏
	 * 
	 * @param pid      商品id
	 * @param uid      用户id
	 * @param username 用户名
	 */
	void add(Integer pid, Integer uid, String username);

	/**
	 * 分页显示收藏列表
	 * 
	 * @param uid  用户id
	 * @param page 页码
	 * @return 收藏列表
	 */
	PageData showListByPage(Integer uid, Integer page);

	/**
	 * 删除某用户的收藏数据
	 * 
	 * @param fid 收藏数据的id
	 * @param uid 用户id
	 */
	void delete(Integer fid, Integer uid);

}
