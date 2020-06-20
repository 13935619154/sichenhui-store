package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.AddressService;
import cn.tedu.store.util.JsonResult;

@RequestMapping("addresses")
@RestController
public class AddressController extends BaseController {

	@Autowired
	private AddressService addressService;
	
	// http://localhost:8080/addresses/addnew?receiver=Alex&provinceName=Guangzhou&provinceCode=000000&cityName=Shenzhen&cityCode=000000&areaName=Nanshan&areaCode=000000&zip=000000&detail=MaNongXiaoQu&phone=13100131000&tel=010-11111111&tag=Home
	@RequestMapping("addnew")
	public JsonResult<Void> addnew(Address address, HttpSession session) {
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		addressService.addnew(uid, username, address);
		return new JsonResult<>(OK);
	}
	
	// http://localhost:8080/addresses
	@GetMapping("")
	public JsonResult<List<Address>> showList(HttpSession session) {
	    // 从Session中获取uid
		Integer uid = getUidFromSession(session);
	    // 调用addressService.showList()方法查询收货地址列表
		List<Address> result = addressService.showList(uid);
	    // 返回“成功”与“收货地址列表”
		return new JsonResult<>(OK, result);
	}
	
	// http://localhost:8080/addresses/15/set_default
	@RequestMapping("{aid}/set_default")
	public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		addressService.setDefault(aid, uid, username);
		return new JsonResult<>(OK);
	}
	
	// http://localhost:8080/addresses/15/delete
	@RequestMapping("{aid}/delete")
	public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session) {
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		addressService.delete(aid, uid, username);
		return new JsonResult<>(OK);
	}
	
}





