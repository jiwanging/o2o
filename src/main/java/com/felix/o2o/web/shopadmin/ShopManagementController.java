package com.felix.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felix.o2o.dto.ShopExecution;
import com.felix.o2o.entity.Area;
import com.felix.o2o.entity.PersonInfo;
import com.felix.o2o.entity.ProductCategory;
import com.felix.o2o.entity.Shop;
import com.felix.o2o.enums.ShopStateEnum;
import com.felix.o2o.service.AreaService;
import com.felix.o2o.service.ShopCategoryService;
import com.felix.o2o.service.ShopService;
import com.felix.o2o.util.CodeUtil;
import com.felix.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping(value = "/shopadmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	/**
	 * �÷���ʹ��ֱ�ӷ��ʵ������������Ч �ض��򵽵����б�Ľ���
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getShopManagementInfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String ,Object> getShopManagementInfo(HttpServletRequest request){
		Map<String ,Object> modelMap = new HashMap<String ,Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId <= 0) {
			Object currentShopObj = request.getSession().
					getAttribute("currentShop");
			if(currentShopObj == null) {
				modelMap.put("redirect", true);
				modelMap.put("url", "/o2o/shop/shoplist");
			}else {
				Shop currentShop = (Shop) currentShopObj;
				modelMap.put("redirect", false);
				modelMap.put("shopId", currentShop.getShopId());
			}
		}else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
	}
	
	/**
	 * ��ȡ�����б���Ϣ
	 */
	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String ,Object> getShopList(HttpServletRequest request){
		Map<String ,Object> modelMap = new HashMap<String ,Object>();
		/*Ӳ���� 
		 * PersonInfo user = new PersonInfo(); 
		user.setUserId(1L);
		user.setName("test");*/
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");//��session�л�ȡ ������Ӳ����
		request.getSession().setAttribute("user",user);
		user = (PersonInfo) request.getSession().
				getAttribute("user");
		try {
			Shop shopConditon = new Shop();
			shopConditon.setOwner(user);
			int pageIndex = 0;
			int pageSize = 100;
			ShopExecution se = shopService.getShopList(shopConditon, pageIndex, pageSize);
			modelMap.put("shopList", se.getShopList());
			//�г����̳ɹ�֮�󣬽����̷���session����ΪȨ����֤���ݣ������˺�ֻ�ܲ������Լ��ĵ���
			request.getSession().setAttribute("shoplist", se.getShopList());
			modelMap.put("user", user);
			modelMap.put("success", true);
		}catch(Exception e) {
			 modelMap.put("errMsg", e.toString());
			 modelMap.put("success", false);
		}
 		return modelMap;
	}
	
	/**
	 * ���ݵ���ID��ȡ������Ϣ
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String ,Object>getShopById(HttpServletRequest request){
		 Map<String ,Object> modelMap = new HashMap<String ,Object>();
		 Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		 try {
			 if(shopId > -1) {
				 Shop shop = shopService.getByShopId(shopId);
				 List<Area> areaList = areaService.getAreaList();
				 modelMap.put("shop", shop);
				 modelMap.put("areaList", areaList);
				 modelMap.put("success", true);
			 }
		 }catch(Exception e){
			 modelMap.put("errMsg", e.toString());
			 modelMap.put("success", false);
		 }
		 return modelMap; 
	}
	
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> modifyShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//��֤��֤��
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "�����˴������֤��");
			return modelMap;
		}
		//1.���ܲ�ת����Ӧ�Ĳ��� ����������Ϣ��ͼƬ��Ϣ
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {//�ж��Ƿ����ϴ����ļ���
			MultipartHttpServletRequest multipartHttpServletRequest = 
					(MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
			
		}
		//2.���µ�����Ϣ 
		if(shop != null && shop.getShopId() != null) {
			ShopExecution se = null;
			try {
				if(shop.getShopImg() == null || 
						"".equals(shop.getShopImg())) {
					se = shopService.modifyShop(shop, null,null);//���е��̸���
				}else {
					se = shopService.addShop(shop, shopImg.getInputStream(),shopImg.getOriginalFilename());
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			if(se.getState() == ShopStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "���������ID");
			return modelMap;
		}
	}
	
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//��֤��֤��
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "�����˴������֤��");
			return modelMap;
		}
		//1.���ܲ�ת����Ӧ�Ĳ��� ����������Ϣ��ͼƬ��Ϣ
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {//�ж��Ƿ����ϴ����ļ���
			MultipartHttpServletRequest multipartHttpServletRequest = 
					(MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "�ϴ�ͼƬ����Ϊ��");
			return modelMap;
		}
		//2ע����� 
		if(shop != null && shopImg != null) {
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(owner);
			ShopExecution se = null;
			try {
				se = shopService.addShop(shop, shopImg.getInputStream(),shopImg.getOriginalFilename());
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}//���е���ע��
			if(se.getState() == ShopStateEnum.CHECK.getState()) {
				modelMap.put("success", true);
				//���û����Բ����ĵ����б�
				@SuppressWarnings("unchecked")
				List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
				if(shopList == null || shopList.size() == 0) {
					shopList = new ArrayList<Shop>();
				}
				shopList.add(se.getShop());
				request.getSession().setAttribute("shopList", shopList);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "�����������Ϣ");
			return modelMap;
		}
	}
	
//	private static void inputStreamToFile(InputStream ins,File file) {
//		FileOutputStream os = null;
//		try {
//			os = new FileOutputStream(file);
//			int bytesRead = 0;
//			byte[] buffer = new byte[1024];
//			while((bytesRead = ins.read(buffer)) != -1) {
//				os.write(buffer,0,bytesRead);
//			}
//		}catch(Exception e){
//			throw new RuntimeException("����inputStreamToFile�����쳣��" + e.getMessage());
//		}finally {
//			try {
//				if(os != null) {
//					os.close();
//				}
//				if(ins != null) {
//					ins.close();
//				}	
//			}catch(Exception e) {
//				throw new RuntimeException("inputStreamToFile�ر�io�����쳣��" + e.getMessage());
//			}
//			
//		}
//		
//	}
}
