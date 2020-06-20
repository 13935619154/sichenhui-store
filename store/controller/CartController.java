package cn.tedu.store.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.service.CartService;
import cn.tedu.store.util.JsonResult;
import cn.tedu.store.vo.CartVO;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController {

	@Autowired
	private CartService cartService;

	// http://localhost:8080/carts/add?pid=10000001&amount=2
	@RequestMapping("add")
	public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session) {
		System.err.println("CartController.addToCart()");
		System.err.println("\tpid=" + pid);
		System.err.println("\tamount=" + amount);
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		cartService.addToCart(pid, amount, uid, username);
		return new JsonResult<>(OK);
	}

	// http://localhost:8080/carts
	@GetMapping({ "", "/" })
	public JsonResult<List<CartVO>> showCartList(HttpSession session) {
		Integer uid = getUidFromSession(session);
		List<CartVO> carts = cartService.showCartList(uid);
		return new JsonResult<>(OK, carts);
	}

	// http://localhost:8080/carts/9/delete
	@RequestMapping("{cid}/delete")
	public JsonResult<Void> delete(@PathVariable("cid") Integer cid, HttpSession session) {
		Integer uid = getUidFromSession(session);
		cartService.delete(cid, uid);
		return new JsonResult<>(OK);
	}

	// http://localhost:8080/carts/7/num/add
	@RequestMapping("{cid}/num/add")
	public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		Integer newNum = cartService.addNum(cid, uid, username);
		return new JsonResult<>(OK, newNum);
	}

	// http://localhost:8080/carts/7/num/reduce
	@RequestMapping("{cid}/num/reduce")
	public JsonResult<Integer> reduceNum(@PathVariable("cid") Integer cid, HttpSession session) {
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		Integer newNum = cartService.reduceNum(cid, uid, username);
		return new JsonResult<>(OK, newNum);
	}

	// http://localhost:8080/carts/checked?cids=1&cids=2&cids=10&cids=15&cids=18
	@GetMapping("checked")
	public JsonResult<List<CartVO>> showCartListByCids(Integer[] cids, HttpSession session) {
		System.err.println("CartController.showCartListByCids()");
		System.err.println("\t参数cids=" + Arrays.toString(cids));
		Integer uid = getUidFromSession(session);
		List<CartVO> carts = cartService.showCartListByCids(uid, cids);
		return new JsonResult<>(OK, carts);
	}

}
