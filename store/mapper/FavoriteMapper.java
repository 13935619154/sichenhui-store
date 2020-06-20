package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Favorite;
import cn.tedu.store.vo.FavoriteVO;

/**
 * 处理收藏数据的持久层接口
 */
public interface FavoriteMapper {

	/**
	 * 插入收藏数据
	 * 
	 * @param favorite 收藏数据对象
	 * @return 受影响的行数
	 */
	Integer insert(Favorite favorite);

	/**
	 * 删除收藏
	 * 
	 * @param fid 收藏数据的id
	 * @return 受影响的行数
	 */
	Integer deleteByFid(Integer fid);

	/**
	 * 统计某用户收藏商品的数量
	 * 
	 * @param uid 用户的id
	 * @return 该用户收藏商品的数量
	 */
	Integer countByUid(Integer uid);

	/**
	 * 根据收藏数据的id查询收藏数据的详情
	 * 
	 * @param fid 收藏数据的id
	 * @return 匹配的收藏数据的详情，如果没有匹配的数据，则返回null
	 */
	Favorite findByFid(Integer fid);

	/**
	 * 查询某用户的收藏的某商品的详情
	 * 
	 * @param uid 用户id
	 * @param pid 商品id
	 * @return 如果该用户的收藏中有该商品，则返回匹配的数据详情，如果没有该商品，则返回null
	 */
	Favorite findByUidAndPid(@Param("uid") Integer uid, @Param("pid") Integer pid);

	/**
	 * 分页查询某用户的收藏列表
	 * 
	 * @param uid    用户id
	 * @param offset 偏移量
	 * @param count  最多取出的数据量
	 * @return 用户的收藏列表
	 */
	List<Favorite> findByUid(@Param("uid") Integer uid, @Param("offset") Integer offset, @Param("count") Integer count);

	/**
	 * 分页查询某用户的收藏列表
	 * 
	 * @param uid    用户id
	 * @param offset 偏移量
	 * @param count  最多取出的数据量
	 * @return 用户的收藏列表
	 */
	List<FavoriteVO> findVOByUid(@Param("uid") Integer uid, @Param("offset") Integer offset,
			@Param("count") Integer count);

}
