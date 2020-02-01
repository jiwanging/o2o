package com.felix.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin" ,method = {RequestMethod.GET})
public class ShopAdminController {

	@RequestMapping(value = "/shopoperation" ,method = {RequestMethod.GET})
	public String shopOperation() {
		return "shop/shopoperation";//����·��
	}
	
	/**
	 * ������Ʒ�б����
	 * @return
	 */
	@RequestMapping(value = "/shoplist" ,method = {RequestMethod.GET})
	public String shopList() {
		return "shop/shoplist";//����·��
	}
	
	/**
	 * �����̵�������
	 * @return
	 */
	@RequestMapping(value = "/shopmanagement" ,method = {RequestMethod.GET})
	public String shopmanagement() {
		return "shop/shopmanagement";//����·��
	}
	
	/**
	 * ������Ʒ����������
	 * @return
	 */
	@RequestMapping(value = "/productcategorymanagement" ,method = {RequestMethod.GET})
	public String productcategorymanagement() {
		return "shop/productcategorymanagement";//����·��
	}
	
	/**
	 * ������Ʒ�༭����
	 * @return
	 */
	@RequestMapping(value = "/productedit" ,method = {RequestMethod.GET})
	public String productedit() {
		return "shop/productoperation";//����·��
	}
	
}
