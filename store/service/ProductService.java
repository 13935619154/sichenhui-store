package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Product;

/**
 * 处理商品数据的业务层接口
 */
public interface ProductService {

	/**
	 * 减少商品库存
	 * 
	 * @param id       商品的id
	 * @param amount   减少的数量
	 * @param username 用户名
	 */
	void reduceNum(Integer id, Integer amount, String username);

	/**
	 * 根据商品id查询商品详情
	 * 
	 * @param id 商品id
	 * @return 匹配的商品详情
	 */
	Product showDetail(Integer id);

	/**
	 * 查询新到好货的商品列表，查询结果中应该包括4个商品数据
	 * 
	 * @return 新到好货的商品列表
	 */
	List<Product> showNewArrival();

	/**
	 * 查询热销的商品列表，查询结果中应该包括4个商品数据
	 * 
	 * @return 热销的商品列表
	 */
	List<Product> showHotSale();

}
