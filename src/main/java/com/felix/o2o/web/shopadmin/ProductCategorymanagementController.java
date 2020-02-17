package com.felix.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.felix.o2o.dto.ProductCategoryExecution;
import com.felix.o2o.entity.ProductCategory;
import com.felix.o2o.entity.Shop;
import com.felix.o2o.enums.ProductCategoryStateEnum;
import com.felix.o2o.exceptions.ProductCategoryOperationException;
import com.felix.o2o.service.ProductCategoryService;

@Controller
@RequestMapping(value = "/shopadmin")
public class ProductCategorymanagementController {

	@Autowired
	private ProductCategoryService productCategoryService;
	
	/**
	 * ����������Ʒ���
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String ,Object> addProductCategorys(@RequestBody List<ProductCategory>
	productCategoryList,HttpServletRequest request){
		//ע��˴���7-2�еĴ��е㲻һ��
		Map<String ,Object> modelMap = new HashMap<String ,Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		for(ProductCategory pc: productCategoryList) {
			pc.setShopId(1L);//��ÿһ����Ʒ�����ӵ���id ע������д����
		}
		if(productCategoryList != null && productCategoryList.size() > 0 ) {
			try {
				ProductCategoryExecution pe = 
						productCategoryService.batchAddProductCategory(productCategoryList);
				if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("errMsg", "����������һ����Ʒ���");
			modelMap.put("success", false);
		}
		return modelMap;
	}
	
	/**
	 * ��ʾ������Ʒ���
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String ,Object> getProductCategoryManagement(HttpServletRequest request){
		//ע��˴���7-2�еĴ��е㲻һ��
		Map<String ,Object> modelMap = new HashMap<String ,Object>();
		long shopId = 1;
		List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(shopId);
		if(productCategoryList != null) {
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
		}
		return modelMap;
	}
	
	/**
	 * ɾ��������Ʒ���
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
	@ResponseBody
	private Map<String ,Object> removeProductCategory(Long productCategoryId, HttpServletRequest request){
		Map<String ,Object> modelMap = new HashMap<String ,Object>();
		if(productCategoryId != null && productCategoryId > 0) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");//��session��ȡ����ǰ�ĵ���id
				ProductCategoryExecution pe = 
						productCategoryService.deleteProductCategory(productCategoryId, 1L);//����д����ɾ������1�Ķ�Ӧ��Ʒ���
				if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("errMsg", "������ѡ��һ����Ʒ���");
			modelMap.put("success", false);
		}
		return modelMap;
	}
}
