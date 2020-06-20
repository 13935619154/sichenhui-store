package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据的业务层接口
 */
public interface CartService {

	/**
	 * 将商品添加到购物车
	 * 
	 * @param pid      商品id
	 * @param amount   添加到购物车的数量
	 * @param uid      用户id
	 * @param username 用户名
	 */
	void addToCart(Integer pid, Integer amount, Integer uid, String username);

	/**
	 * 查询某用户的购物车列表
	 * 
	 * @param uid 用户id
	 * @return 该用户的购物车列表
	 */
	List<CartVO> showCartList(Integer uid);

	/**
	 * 查询某用户选中的购物车数据的列表
	 * 
	 * @param uid  用户id
	 * @param cids 要查询的购物车数据的id
	 * @return 该用户选中的购物车数据的列表
	 */
	List<CartVO> showCartListByCids(Integer uid, Integer[] cids);

	/**
	 * 删除购物车数据
	 * 
	 * @param cid 购物车数据id
	 * @param uid 用户id
	 */
	void delete(Integer cid, Integer uid);

	/**
	 * 批量删除购物车数据
	 * 
	 * @param uid  用户id
	 * @param cids 若干个购物车数据id
	 */
	void delete(Integer uid, Integer[] cids);

	/**
	 * 增加购物车中商品的数量
	 * 
	 * @param cid      购物车数据id
	 * @param uid      用户id
	 * @param username 用户名
	 * @return 增加后的数量
	 */
	Integer addNum(Integer cid, Integer uid, String username);

	/**
	 * 减少购物车中商品的数量
	 * 
	 * @param cid      购物车数据id
	 * @param uid      用户id
	 * @param username 用户名
	 * @return 减少后的数量
	 */
	Integer reduceNum(Integer cid, Integer uid, String username);

}
