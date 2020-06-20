package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.AddressService;
import cn.tedu.store.service.DistrictService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.AddressSizeLimitException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

/**
 * 处理收货地址数据的业务层实现类
 */
@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private DistrictService districtService;
	@Value("${project.address.max-count}")
	private Integer addressMaxCount;

	@Override
	public void addnew(Integer uid, String username, Address address) {
		// 基于参数uid调用私有的count()统计用户的收货地址数量
		Integer count = count(uid);
		// 判断统计结果是否达到上限
		if (count >= addressMaxCount) {
			// 是：已经达到上限，则不允许增加新的收货地址，抛出AddressSizeLimitException
			throw new AddressSizeLimitException("创建收货地址失败！您当前的收货地址数量已经达到上限(" + addressMaxCount + ")！");
		}

		// 创建当前时间对象now
		Date now = new Date();
		// 声明isDefault，表示即将创建的收货地址“是否默认”
		// 判断统计结果的值是否为0，如果是，则isDefault为1，否则，isDefault为0
		Integer isDefault = count == 0 ? 1 : 0;
		// 补全参数address中的属性：uid > 参数uid
		address.setUid(uid);
		// 补全参数address中的属性：省市区的名称 > ???
		String provinceCode = address.getProvinceCode();
		String provinceName = districtService.getNameByCode(provinceCode);
		address.setProvinceName(provinceName);
		String cityCode = address.getCityCode();
		String cityName = districtService.getNameByCode(cityCode);
		address.setCityName(cityName);
		String areaCode = address.getAreaCode();
		String areaName = districtService.getNameByCode(areaCode);
		address.setAreaName(areaName);
		// 补全参数address中的属性：is_default > isDefault
		address.setIsDefault(isDefault);
		// 补全参数address中的属性：4个日志 > 参数username, now
		address.setCreatedUser(username);
		address.setCreatedTime(now);
		address.setModifiedUser(username);
		address.setModifiedTime(now);
		// 基于参数address调用私有的insert()插入收货地址数据
		insert(address);
	}

	@Override
	public List<Address> showList(Integer uid) {
		// 基于参数uid调用私有的findByUid()查询收货地址列表
		List<Address> result = findByUid(uid);
		// 遍历查询结果
		for (Address address : result) {
			// 将遍历到的收货地址数据的部分属性设置为null
			// uid, provinceCode, cityCode, areaCode, isDefault, 4个日志属性
			address.setUid(null);
			address.setProvinceCode(null);
			address.setCityCode(null);
			address.setAreaCode(null);
			address.setIsDefault(null);
			address.setCreatedUser(null);
			address.setCreatedTime(null);
			address.setModifiedUser(null);
			address.setModifiedTime(null);
		}
		// 返回查询结果
		return result;
	}

	@Override
	@Transactional
	public void setDefault(Integer aid, Integer uid, String username) {
		// 基于参数aid调用私有的findByAid()查询收货地址数据
		Address result = findByAid(aid);
		// 判断查询结果是否为null
		if (result == null) {
			// 是：抛出AddressNotFoundException
			throw new AddressNotFoundException("设置默认收货地址失败！尝试访问的收货地址数据不存在！");
		}

		// 检查查询结果中的uid与参数uid(当前登录的用户的id)是否不匹配
		// -128 ~ 127
		// Integer i1 = 128;
		// Integer i2 = 128;
		// i1 == i2 结果为 false
		if (!result.getUid().equals(uid)) {
			// 是：抛出AccessDeniedException（导包时，导入自定义的异常类）
			throw new AccessDeniedException("设置默认收货地址失败！不允许访问他人的数据！");
		}

		// 基于参数uid调用私有的updateNonDefaultByUid()将当前用户的所有收货地址全部设置为“非默认”
		updateNonDefaultByUid(uid);

		// 创建当前时间对象now
		Date now = new Date();
		// 调用私有的updateDefaultByAid()将指定的收货地址设置为“默认”
		updateDefaultByAid(aid, username, now);
	}

	@Override
	@Transactional
	public void delete(Integer aid, Integer uid, String username) {
		// 基于参数aid调用私有的findByAid()查询收货地址数据
		Address result = findByAid(aid);
		// 判断查询结果是否为null
		if (result == null) {
			// 是：抛出AddressNotFoundException
			throw new AddressNotFoundException("删除收货地址失败！尝试访问的收货地址数据不存在！");
		}

		// 检查查询结果中的uid与参数uid是否不匹配
		if (!result.getUid().equals(uid)) {
			// 是：抛出AccessDeniedException（导包时，导入自定义的异常类）
			throw new AccessDeniedException("删除收货地址失败！不允许访问他人的数据！");
		}

		// 基于参数aid调用私有的deleteByAid()删除收货地址数据
		deleteByAid(aid);

		// 检查查询结果中的isDefault是否为0
		if (result.getIsDefault() == 0) {
			return;
		}

		// 基于参数uid调用私有的count()统计该用户的收货地址数量
		Integer count = count(uid);
		// 判断当前收货地址数量是否为0
		if (count == 0) {
			return;
		}

		// 基于参数uid调用私有的findLastModified()找出该用户最近修改的那一条收货地址
		Address lastModifiedAddress = findLastModified(uid);
		// 从查询结果中得到数据的aid
		Integer lastModifiedAid = lastModifiedAddress.getAid();
		// 创建当前时间对象now
		Date now = new Date();
		// 基于以上得到的各值调用私有的updateDefaultByAid()将以上收货地址设置为默认
		updateDefaultByAid(lastModifiedAid, username, now);
	}

	@Override
	public Address getByAid(Integer aid, Integer uid) {
		// 基于参数aid调用私有的findByAid()查询收货地址数据
		Address result = findByAid(aid);
		// 判断查询结果是否为null
		if (result == null) {
			// 是：抛出AddressNotFoundException
			throw new AddressNotFoundException("获取收货地址失败！尝试访问的收货地址数据不存在！");
		}

		// 检查查询结果中的uid与参数uid是否不匹配
		if (!result.getUid().equals(uid)) {
			// 是：抛出AccessDeniedException（导包时，导入自定义的异常类）
			throw new AccessDeniedException("获取收货地址失败！不允许访问他人的数据！");
		}

		// 将查询结果中不必要返回的属性设置为null：省市区的代码，isDefault，4个日志
		result.setUid(null);
		result.setProvinceCode(null);
		result.setCityCode(null);
		result.setAreaCode(null);
		result.setIsDefault(null);
		result.setCreatedUser(null);
		result.setCreatedTime(null);
		result.setModifiedUser(null);
		result.setModifiedTime(null);
		
		// 返回查询结果
		return result;
	}

	/**
	 * 插入收货地址数据
	 * 
	 * @param address 收货地址数据
	 */
	private void insert(Address address) {
		Integer rows = addressMapper.insert(address);
		if (rows != 1) {
			throw new InsertException("增加收货地址失败！插入收货地址数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 删除收货地址数据
	 * 
	 * @param aid 收货地址数据的id
	 */
	private void deleteByAid(Integer aid) {
		Integer rows = addressMapper.deleteByAid(aid);
		if (rows != 1) {
			throw new InsertException("删除收货地址失败！删除收货地址数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 将指定的收货地址设置为“默认”
	 * 
	 * @param aid          收货地址数据的id
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 */
	private void updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime) {
		Integer rows = addressMapper.updateDefaultByAid(aid, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException("设置默认收货地址失败！更新收货地址数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 将某用户的所有收货地址设置为“非默认”
	 * 
	 * @param uid 用户的id
	 */
	private void updateNonDefaultByUid(Integer uid) {
		Integer rows = addressMapper.updateNonDefaultByUid(uid);
		if (rows < 1) {
			throw new UpdateException("设置默认收货地址失败！更新收货地址数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 统计某用户的收货地址的数量
	 * 
	 * @param uid 用户的id
	 * @return 用户的收货地址的数量
	 */
	private Integer count(Integer uid) {
		return addressMapper.count(uid);
	}

	/**
	 * 查询某收货地址数据的详情
	 * 
	 * @param aid 收货地址数据的id
	 * @return 匹配的收货地址数据的详情，如果没有匹配的数据，则返回null
	 */
	private Address findByAid(Integer aid) {
		return addressMapper.findByAid(aid);
	}

	/**
	 * 查询某用户最后修改的收货地址
	 * 
	 * @param uid 用户的id
	 * @return 该用户最后修改的收货地址
	 */
	private Address findLastModified(Integer uid) {
		return addressMapper.findLastModified(uid);
	}

	/**
	 * 查询某用户的所有收货地址
	 * 
	 * @param uid 用户的id
	 * @return 该用户的所有收货地址
	 */
	private List<Address> findByUid(Integer uid) {
		return addressMapper.findByUid(uid);
	}

}
