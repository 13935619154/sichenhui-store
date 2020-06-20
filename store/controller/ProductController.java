package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Product;
import cn.tedu.store.service.ProductService;
import cn.tedu.store.util.JsonResult;

@RestController
@RequestMapping("products")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	// http://localhost:8080/products/10000001/details
	// http://localhost:8080/products/10088001/details
	@GetMapping("{id}/details")
	public JsonResult<Product> showDetail(@PathVariable("id") Integer id) {
		Product product = productService.showDetail(id);
		return new JsonResult<>(OK, product);
	}

	// http://localhost:8080/products/new_arrival
	@GetMapping("new_arrival")
	public JsonResult<List<Product>> showNewArrival() {
		List<Product> products = productService.showNewArrival();
		return new JsonResult<>(OK, products);
	}

	// http://localhost:8080/products/hot_sale
	@GetMapping("hot_sale")
	public JsonResult<List<Product>> showHotSale() {
		List<Product> products = productService.showHotSale();
		return new JsonResult<>(OK, products);
	}

}
