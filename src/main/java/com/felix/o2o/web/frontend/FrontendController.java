package com.felix.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/frontend")
public class FrontendController {

	/**
	 * �̳���ҳ·��
	 * @return
	 */
	@RequestMapping(value = "/index", method = {RequestMethod.GET})
	private String index() {
		return "frontend/index";
	}
	
	/**
	 * //���ص�������ҳ��·��
	 * @return
	 */
	@RequestMapping(value = "/shopdetail", method = {RequestMethod.GET})
	private String shopDetail() {
		return "frontend/shopdetail";
	}
	
	/**
	 * //���ص����б��·��
	 */
	@RequestMapping(value = "/shoplist", method = {RequestMethod.GET})
	private String shopList() {
		return "frontend/shoplist";
	}
	
	/**
	 * //���ص����б��·��
	 */
	@RequestMapping(value = "/productdetail", method = {RequestMethod.GET})
	private String productDetail() {
		return "frontend/productdetail";
	}
}
