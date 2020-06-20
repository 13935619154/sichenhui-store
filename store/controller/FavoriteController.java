package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tedu.store.service.FavoriteService;
import cn.tedu.store.util.JsonResult;
import cn.tedu.store.util.PageData;

import org.springframework.web.bind.annotation.RestController;

@RequestMapping("favorites")
@RestController
public class FavoriteController extends BaseController {

	@Autowired
	private FavoriteService favoriteService;

	// http://localhost:8080/favorites/add?pid=10000001
	@RequestMapping("add")
	public JsonResult<Void> add(Integer pid, HttpSession session) {
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		favoriteService.add(pid, uid, username);
		return new JsonResult<>(OK);
	}

	// http://localhost:8080/favorites
	// http://localhost:8080/favorites?page=2
	@GetMapping({ "", "/" })
	public JsonResult<PageData> showListByPage(Integer page, HttpSession session) {
		Integer uid = getUidFromSession(session);
		PageData pageData = favoriteService.showListByPage(uid, page);
		return new JsonResult<>(OK, pageData);
	}

	// http://localhost:8080/favorites/10/delete
	@RequestMapping("{fid}/delete")
	public JsonResult<Void> delete(@PathVariable("fid") Integer fid, HttpSession session) {
		Integer uid = getUidFromSession(session);
		favoriteService.delete(fid, uid);
		return new JsonResult<>(OK);
	}

}
