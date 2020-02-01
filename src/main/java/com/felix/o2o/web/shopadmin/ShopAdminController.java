package com.felix.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin" ,method = {RequestMethod.GET})
public class ShopAdminController {

	@RequestMapping(value = "/shopoperation" ,method = {RequestMethod.GET})
	public String shopOperation() {
		return "shop/shopoperation";//返回路径
	}
	
	/**
	 * 返回商品列表界面
	 * @return
	 */
	@RequestMapping(value = "/shoplist" ,method = {RequestMethod.GET})
	public String shopList() {
		return "shop/shoplist";//返回路径
	}
	
	/**
	 * 返回商店管理界面
	 * @return
	 */
	@RequestMapping(value = "/shopmanagement" ,method = {RequestMethod.GET})
	public String shopmanagement() {
		return "shop/shopmanagement";//返回路径
	}
	
	/**
	 * 进入商品分类管理界面
	 * @return
	 */
	@RequestMapping(value = "/productcategorymanagement" ,method = {RequestMethod.GET})
	public String productcategorymanagement() {
		return "shop/productcategorymanagement";//返回路径
	}
	
	/**
	 * 进入商品编辑界面
	 * @return
	 */
	@RequestMapping(value = "/productedit" ,method = {RequestMethod.GET})
	public String productedit() {
		return "shop/productoperation";//返回路径
	}
	
}
