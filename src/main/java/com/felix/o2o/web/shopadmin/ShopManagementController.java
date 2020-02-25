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
	 * 该方法使得直接访问店铺详情界面无效 重定向到店铺列表的界面
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
	 * 获取店铺列表信息
	 */
	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String ,Object> getShopList(HttpServletRequest request){
		Map<String ,Object> modelMap = new HashMap<String ,Object>();
		/*硬编码 
		 * PersonInfo user = new PersonInfo(); 
		user.setUserId(1L);
		user.setName("test");*/
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");//从session中获取 而不是硬编码
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
			//列出店铺成功之后，将店铺放入session中作为权限验证依据，即该账号只能操作它自己的店铺
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
	 * 根据店铺ID获取店铺信息
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
		//验证验证码
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//1.接受并转换相应的参数 包括店铺信息和图片信息
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
		if(commonsMultipartResolver.isMultipart(request)) {//判断是否有上传的文件流
			MultipartHttpServletRequest multipartHttpServletRequest = 
					(MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
			
		}
		//2.更新店铺信息 
		if(shop != null && shop.getShopId() != null) {
			ShopExecution se = null;
			try {
				if(shop.getShopImg() == null || 
						"".equals(shop.getShopImg())) {
					se = shopService.modifyShop(shop, null,null);//进行店铺更新
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
			modelMap.put("errMsg", "请输入店铺ID");
			return modelMap;
		}
	}
	
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//验证验证码
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//1.接受并转换相应的参数 包括店铺信息和图片信息
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
		if(commonsMultipartResolver.isMultipart(request)) {//判断是否有上传的文件流
			MultipartHttpServletRequest multipartHttpServletRequest = 
					(MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		//2注册店铺 
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
			}//进行店铺注册
			if(se.getState() == ShopStateEnum.CHECK.getState()) {
				modelMap.put("success", true);
				//该用户可以操作的店铺列表
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
			modelMap.put("errMsg", "请输入店铺信息");
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
//			throw new RuntimeException("调用inputStreamToFile产生异常：" + e.getMessage());
//		}finally {
//			try {
//				if(os != null) {
//					os.close();
//				}
//				if(ins != null) {
//					ins.close();
//				}	
//			}catch(Exception e) {
//				throw new RuntimeException("inputStreamToFile关闭io产生异常：" + e.getMessage());
//			}
//			
//		}
//		
//	}
}
