package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.entity.Product;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.service.CartService;
import cn.tedu.store.service.ProductService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.CartListEmptyException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.ProductNumLimitException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据的业务层实现类
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private ProductService productService;

	@Override
	public void addToCart(Integer pid, Integer amount, Integer uid, String username) {
		// 创建当前时间对象now
		Date now = new Date();
		// 基于参数uid和pid调用私有的findByUidAndPid()方法查询购物车数据
		Cart result = findByUidAndPid(uid, pid);
		// 判断查询结果是否为null {
		if (result == null) {
			// 是：表示该用户的购物车中没有该商品，需要插入购物车数据
			// 基于参数调用productService.showDetail()方法查询商品详情
			Product product = productService.showDetail(pid);
			// 创建Cart对象
			Cart cart = new Cart();
			// 向Cart对象中封装数据：uid > 参数uid
			cart.setUid(uid);
			// 向Cart对象中封装数据：pid > 参数pid
			cart.setPid(pid);
			// 向Cart对象中封装数据：num > 参数amount
			cart.setNum(amount);
			// 向Cart对象中封装数据：price > 从商品详情中获取
			cart.setPrice(product.getPrice());
			// 向Cart对象中封装数据：4个日志 > 参数username, now
			cart.setCreatedUser(username);
			cart.setCreatedTime(now);
			cart.setModifiedUser(username);
			cart.setModifiedTime(now);
			// 调用私有的insert()方法插入购物车数据
			insert(cart);
		} else {
			// 否：表示该用户的购物车中已有该商品，需要增加商品数量
			// 从查询结果中获取cid
			Integer cid = result.getCid();
			// 从查询结果中取出商品的原数量，与参数amount相加，得到新的数量
			Integer num = result.getNum() + amount;
			// 调用私有的updateNumByCid()方法更新购物车中商品的数量
			updateNumByCid(cid, num, username, now);
		}
	}

	@Override
	public List<CartVO> showCartList(Integer uid) {
		return findVOByUid(uid);
	}

	@Override
	public List<CartVO> showCartListByCids(Integer uid, Integer[] cids) {
		List<CartVO> result = findVOByUidAndCids(uid, cids);
		if (result.isEmpty()) {
			throw new CartListEmptyException("获取购物车数据列表失败！没有获取到有效的购物车列表数据！");
		}
		return result;
	}

	@Override
	public void delete(Integer cid, Integer uid) {
		// 基于cid查询购物车数据
		Cart result = findByCid(cid);
		// 判断查询是否为null
		if (result == null) {
			// 是：CartNotFoundException
			throw new CartNotFoundException("删除购物车数据失败！尝试访问的购物车数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不相同
		if (!result.getUid().equals(uid)) {
			// 是：AccessDeniedException
			throw new AccessDeniedException("删除购物车数据失败！不允许访问他人的数据！");
		}

		// 执行删除
		deleteByCid(cid);
	}

	@Override
	public void delete(Integer uid, Integer[] cids) {
		// 调用findVOByUidAndCids()查询购物车列表
		List<CartVO> result = findVOByUidAndCids(uid, cids);

		// 判断查询结果的列表长度与参数cids的长度是否不相同
		if (result.size() != cids.length) {
			// 根据查询结果的列表长度创建新的数组，并赋值给参数cids
			cids = new Integer[result.size()];
			// 把查询结果中的各cid值放到数组中
			for (int i = 0; i < cids.length; i++) {
				cids[i] = result.get(i).getCid();
			}
		}

		// 调用deleteByCids()执行删除
		deleteByCids(cids);
	}

	@Override
	public Integer addNum(Integer cid, Integer uid, String username) {
		// 基于cid查询购物车数据
		Cart result = findByCid(cid);
		// 判断查询是否为null
		if (result == null) {
			// 是：CartNotFoundException
			throw new CartNotFoundException("增加商品数量失败！尝试访问的购物车数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不相同
		if (!result.getUid().equals(uid)) {
			// 是：AccessDeniedException
			throw new AccessDeniedException("增加商品数量失败！不允许访问他人的数据！");
		}

		// 从查询结果中取出原数量，并增加1，得到新的数量
		Integer num = result.getNum() + 1;
		// 忽略：自行设计业务规则：是否有上限
		// 创建当前时间对象
		Date now = new Date();
		// 将新的数量更新到数据库中
		updateNumByCid(cid, num, username, now);

		// 返回新的数量
		return num;
	}

	@Override
	public Integer reduceNum(Integer cid, Integer uid, String username) {
		// 基于cid查询购物车数据
		Cart result = findByCid(cid);
		// 判断查询是否为null
		if (result == null) {
			// 是：CartNotFoundException
			throw new CartNotFoundException("减少商品数量失败！尝试访问的购物车数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不相同
		if (!result.getUid().equals(uid)) {
			// 是：AccessDeniedException
			throw new AccessDeniedException("减少商品数量失败！不允许访问他人的数据！");
		}

		// 从查询结果中取出原数量，并减少1，得到新的数量
		Integer num = result.getNum() - 1;
		// 判断减少后的数量是否达到下限值
		if (num < 1) {
			throw new ProductNumLimitException("减少商品数量失败！商品数量不可以少于1个！");
		}

		// 创建当前时间对象
		Date now = new Date();
		// 将新的数量更新到数据库中
		updateNumByCid(cid, num, username, now);

		// 返回新的数量
		return num;
	}

	/**
	 * 插入购物车数据
	 * 
	 * @param cart 购物车数据对象
	 */
	private void insert(Cart cart) {
		Integer rows = cartMapper.insert(cart);
		if (rows != 1) {
			throw new InsertException("插入购物车数据失败！插入购物车数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 删除购物车数据
	 * 
	 * @param cid 购物车数据的id
	 */
	private void deleteByCid(Integer cid) {
		Integer rows = cartMapper.deleteByCid(cid);
		if (rows != 1) {
			throw new DeleteException("删除购物车数据失败！删除购物车数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 批量删除购物车数据
	 * 
	 * @param cids 若干个购物车数据的id
	 */
	private void deleteByCids(Integer[] cids) {
		Integer rows = cartMapper.deleteByCids(cids);
		if (rows < 1) {
			throw new DeleteException("删除购物车数据失败！删除购物车数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 更新购物车中商品的数量
	 * 
	 * @param cid          购物车数据的id
	 * @param num          新的数量
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 */
	private void updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime) {
		Integer rows = cartMapper.updateNumByCid(cid, num, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException("更新商品数量失败！更新购物车数据时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 根据购物车数据id查询购物车详情
	 * 
	 * @param cid 购物车数据id
	 * @return 匹配的购物车详情，如果没有匹配的数据，则返回null
	 */
	private Cart findByCid(Integer cid) {
		return cartMapper.findByCid(cid);
	}

	/**
	 * 查询某用户的购物车中的某商品的详情
	 * 
	 * @param uid 用户id
	 * @param pid 商品id
	 * @return 如果该用户的购物车中有该商品，则返回匹配的购物车数据详情，如果没有该商品，则返回null
	 */
	private Cart findByUidAndPid(Integer uid, Integer pid) {
		return cartMapper.findByUidAndPid(uid, pid);
	}

	/**
	 * 查询某用户的购物车列表
	 * 
	 * @param uid 用户id
	 * @return 该用户的购物车列表
	 */
	private List<CartVO> findVOByUid(Integer uid) {
		return cartMapper.findVOByUid(uid);
	}

	/**
	 * 查询某用户勾选的购物车列表
	 * 
	 * @param uid  用户id
	 * @param cids 购物车数据的id
	 * @return 该用户的购物车列表
	 */
	private List<CartVO> findVOByUidAndCids(Integer uid, Integer[] cids) {
		return cartMapper.findVOByUidAndCids(uid, cids);
	}

}
