package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.OrderService;
import cn.tedu.store.util.JsonResult;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	// http://localhost:8080/orders/create?aid=24&cids=9&cids=18&cids=22
	@RequestMapping("create")
	public JsonResult<Order> create(Integer aid, Integer[] cids, HttpSession session) {
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		Order order = orderService.create(aid, cids, uid, username);
		return new JsonResult<>(OK, order);
	}

}
