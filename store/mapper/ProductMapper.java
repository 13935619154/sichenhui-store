package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Product;

/**
 * 处理商品数据的持久层接口
 */
public interface ProductMapper {

	/**
	 * 更新商品的库存值
	 * @param id 商品id
	 * @param num 新的库存值
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 * @return 受影响的行数
	 */
	Integer updateNumById(
		@Param("id") Integer id, 
		@Param("num") Integer num,
		@Param("modifiedUser") String modifiedUser, 
		@Param("modifiedTime") Date modifiedTime
	);

	/**
	 * 根据商品id查询商品详情
	 * 
	 * @param id 商品id
	 * @return 匹配的商品详情，如果没有匹配的数据，则返回null
	 */
	Product findById(Integer id);

	/**
	 * 查询新到好货的商品列表，查询结果中应该包括4个商品数据
	 * 
	 * @return 新到好货的商品列表
	 */
	List<Product> findNewArrival();

	/**
	 * 查询热销的商品列表，查询结果中应该包括4个商品数据
	 * 
	 * @return 热销的商品列表
	 */
	List<Product> findHotSale();

}
