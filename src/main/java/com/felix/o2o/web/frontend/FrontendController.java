package com.felix.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/frontend")
public class FrontendController {

	@RequestMapping(value = "/index", method = {RequestMethod.GET})
	private String index() {
		return "frontend/index";
	}
	
	@RequestMapping(value = "/shopdetail", method = {RequestMethod.GET})
	private String shopDetail() {
		return "frontend/shopdetail";//返回商品详情页的路由
	}
}
