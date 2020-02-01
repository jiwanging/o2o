package com.felix.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felix.o2o.dto.ProductExecution;
import com.felix.o2o.entity.Area;
import com.felix.o2o.entity.Product;
import com.felix.o2o.entity.ProductCategory;
import com.felix.o2o.entity.Shop;
import com.felix.o2o.enums.ProductStateEnum;
import com.felix.o2o.service.ProductCategoryService;
import com.felix.o2o.service.ProductService;
import com.felix.o2o.util.CodeUtil;
import com.felix.o2o.util.HttpServletRequestUtil;
import com.felix.o2o.util.ImageHolder;

@Controller
@RequestMapping(value = "/shopadmin")
public class ProductManagementController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	//支持上传商品详情图的最大数量
	private static final int IMAGEMAXCOUNT = 6;
	
	/**
	 * 根据商品id信息返回数据库中的商品信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getbyproductid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String ,Object>  getByProductId(HttpServletRequest request){
		
		 Map<String ,Object> modelMap = new HashMap<String ,Object>();
		 Long productId = HttpServletRequestUtil.getLong(request, "productId");
		 try {
			 if(productId > -1) {//冗余判断
				 Product product = productService.getByProductId(productId);
				 //这里应当是根据session来得到当前的店铺id 但是这里为了方便 先写死
				 List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(1L);//获取商品分类
				 modelMap.put("product", product);
				 modelMap.put("productCategoryList", productCategoryList);
				 modelMap.put("success", true);
			 }
		 }catch(Exception e){
			 modelMap.put("errMsg", e.toString());
			 modelMap.put("success", false);
		 }
		 return modelMap; 
	}
	
	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProduct(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//验证验证码
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//1.接受并转换相应的参数 包括商品信息、缩略图信息、详情图片信息
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		ImageHolder thumbnailImgHolder = null;//存缩略图流
		List<ImageHolder> imgHolderList = new ArrayList<ImageHolder>();//存详情图的数据
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		try {
			product = mapper.readValue(productStr, Product.class);//读入前台传递过来的商品信息
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile productThumbnailImg = null;//缩略图
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {//判断是否有上传的文件流
			MultipartHttpServletRequest multipartHttpServletRequest = 
					(MultipartHttpServletRequest) request;
			try {
				//处理缩略图流
				productThumbnailImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");//获取产品缩略图输入流
				thumbnailImgHolder = new ImageHolder(productThumbnailImg.getInputStream(),productThumbnailImg.getOriginalFilename());
				//处理详情图流
				for(int index = 0; index < IMAGEMAXCOUNT; index++) {
					CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + index);//获取产品缩略图输入流
					if(productImgFile.getInputStream() == null)
						break;
					ImageHolder imgHolder = new ImageHolder(productImgFile.getInputStream(),productImgFile.getOriginalFilename());
					imgHolderList.add(imgHolder);
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传商品图片不能为空");
				return modelMap;
			}
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传商品图片不能为空");
			return modelMap;
		}
		//2.商品信息存库
		if(product != null) {
			//从session中获取当前商品所属的店铺的id信息 这样做的目的是为了减少对前端数据的依赖
			Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
			Shop shop = new Shop();
			shop.setShopId(currentShop.getShopId());
			product.setShop(shop);
			try {
				ProductExecution pe = productService.addProduct(product,thumbnailImgHolder,imgHolderList);
				if(pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("errMsg", "添加商品成功");
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
			return modelMap;
		}
	}
}
