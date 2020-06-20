package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据的持久层接口
 */
public interface CartMapper {

	/**
	 * 插入购物车数据
	 * 
	 * @param cart 购物车数据对象
	 * @return 受影响的行数
	 */
	Integer insert(Cart cart);

	/**
	 * 删除购物车数据
	 * 
	 * @param cid 购物车数据的id
	 * @return 受影响的行数
	 */
	Integer deleteByCid(Integer cid);

	/**
	 * 批量删除购物车数据
	 * 
	 * @param cids 若干个购物车数据的id
	 * @return 受影响的行数
	 */
	Integer deleteByCids(Integer[] cids);

	/**
	 * 更新购物车中商品的数量
	 * 
	 * @param cid          购物车数据的id
	 * @param num          新的数量
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 * @return 受影响的行数
	 */
	Integer updateNumByCid(@Param("cid") Integer cid, @Param("num") Integer num,
			@Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

	/**
	 * 根据购物车数据id查询购物车详情
	 * 
	 * @param cid 购物车数据id
	 * @return 匹配的购物车详情，如果没有匹配的数据，则返回null
	 */
	Cart findByCid(Integer cid);

	/**
	 * 查询某用户的购物车中的某商品的详情
	 * 
	 * @param uid 用户id
	 * @param pid 商品id
	 * @return 如果该用户的购物车中有该商品，则返回匹配的购物车数据详情，如果没有该商品，则返回null
	 */
	Cart findByUidAndPid(@Param("uid") Integer uid, @Param("pid") Integer pid);

	/**
	 * 查询某用户的购物车列表
	 * 
	 * @param uid 用户id
	 * @return 该用户的购物车列表
	 */
	List<CartVO> findVOByUid(Integer uid);

	/**
	 * 查询某用户勾选的购物车列表
	 * 
	 * @param uid  用户id
	 * @param cids 购物车数据的id
	 * @return 该用户的购物车列表
	 */
	List<CartVO> findVOByUidAndCids(@Param("uid") Integer uid, @Param("cids") Integer[] cids);

}
