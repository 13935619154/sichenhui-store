package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Address;

/**
 * 处理收货地址数据的持久层接口
 */
public interface AddressMapper {

	/**
	 * 插入收货地址数据
	 * 
	 * @param address 收货地址数据
	 * @return 受影响的行数
	 */
	Integer insert(Address address);

	/**
	 * 删除收货地址数据
	 * 
	 * @param aid 收货地址数据的id
	 * @return 受影响的行数
	 */
	Integer deleteByAid(Integer aid);

	/**
	 * 将指定的收货地址设置为“默认”
	 * 
	 * @param aid          收货地址数据的id
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 * @return 受影响的行数
	 */
	Integer updateDefaultByAid(
			@Param("aid") Integer aid, 
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);

	/**
	 * 将某用户的所有收货地址设置为“非默认”
	 * 
	 * @param uid 用户的id
	 * @return 受影响的行数
	 */
	Integer updateNonDefaultByUid(Integer uid);

	/**
	 * 统计某用户的收货地址的数量
	 * 
	 * @param uid 用户的id
	 * @return 用户的收货地址的数量
	 */
	Integer count(Integer uid);

	/**
	 * 查询某收货地址数据的详情
	 * 
	 * @param aid 收货地址数据的id
	 * @return 匹配的收货地址数据的详情，如果没有匹配的数据，则返回null
	 */
	Address findByAid(Integer aid);

	/**
	 * 查询某用户最后修改的收货地址
	 * 
	 * @param uid 用户的id
	 * @return 该用户最后修改的收货地址
	 */
	Address findLastModified(Integer uid);

	/**
	 * 查询某用户的所有收货地址
	 * 
	 * @param uid 用户的id
	 * @return 该用户的所有收货地址
	 */
	List<Address> findByUid(Integer uid);

}
