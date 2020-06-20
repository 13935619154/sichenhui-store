package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.District;

/**
 * 处理省市区数据的业务层接口
 */
public interface DistrictService {

	/**
	 * 根据省市区的代号查询名称
	 * 
	 * @param code 省市区的代号
	 * @return 匹配的省市区的名称，如果没有匹配的数据，则返回null
	 */
	String getNameByCode(String code);

	/**
	 * 查询全国所有省/某省所有市/某市所有区的数据列表
	 * 
	 * @param parent 父级单位的代号，如果需要查询全国所有省，使用"86"作为父级代号
	 * @return 全国所有省/某省所有市/某市所有区的数据列表
	 */
	List<District> showList(String parent);

}
