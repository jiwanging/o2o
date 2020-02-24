package com.felix.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/frontend")
public class FrontendController {

	/**
	 * 商城首页路由
	 * @return
	 */
	@RequestMapping(value = "/index", method = {RequestMethod.GET})
	private String index() {
		return "frontend/index";
	}
	
	/**
	 * //返回店铺详情页的路由
	 * @return
	 */
	@RequestMapping(value = "/shopdetail", method = {RequestMethod.GET})
	private String shopDetail() {
		return "frontend/shopdetail";
	}
	
	/**
	 * //返回店铺列表的路由
	 */
	@RequestMapping(value = "/shoplist", method = {RequestMethod.GET})
	private String shopList() {
		return "frontend/shoplist";
	}
	
	/**
	 * //返回店铺列表的路由
	 */
	@RequestMapping(value = "/productdetail", method = {RequestMethod.GET})
	private String productDetail() {
		return "frontend/productdetail";
	}
}
