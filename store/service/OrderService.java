package cn.tedu.store.service;

import cn.tedu.store.entity.Order;

/**
 * 处理订单相关数据的业务接口
 * 
 */
public interface OrderService {

	/**
	 * 创建订单
	 * 
	 * @param aid      收货地址id
	 * @param cids     即将购买的商品在购物车中的数据id
	 * @param uid      用户id
	 * @param username 用户名
	 * @return 成功创建的订单对象
	 */
	Order create(Integer aid, Integer[] cids, Integer uid, String username);

}
