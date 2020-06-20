package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.District;
import cn.tedu.store.service.DistrictService;
import cn.tedu.store.util.JsonResult;

@RequestMapping("districts")
@RestController
public class DistrictController extends BaseController {

	@Autowired
	private DistrictService districtService;
	
	// http://localhost:8080/districts?parent=86
	@GetMapping("")
	public JsonResult<List<District>> showList(String parent) {
		List<District> result = districtService.showList(parent);
		return new JsonResult<>(OK, result);
	}
	
}








