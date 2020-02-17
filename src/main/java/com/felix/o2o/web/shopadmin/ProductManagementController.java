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
import org.springframework.web.bind.annotation.RequestParam;
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
import com.felix.o2o.exceptions.ProductOperationException;
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
	private static final int IMAGEMAXCOUNT = 1;
	
	/**
	 * 根据界面提交的信息修改指定的商品信息
	 * @param request
	 * @return
	 */
	   /*
	    * 我们完成修改product的controller操作
	    * */
	    @RequestMapping(value ="/modifyproduct" ,method = RequestMethod.POST)
	    @ResponseBody
	    private  Map<String,Object> modifyProduct(HttpServletRequest request){
	        Map<String, Object> modelMap = new HashMap<String, Object>();
	        // 是商品编辑时候调用还是上下架操作的时候调用
	        // 若为前者则进行验证码判断，后者则跳过验证码判断
	        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
	        // 验证码检验
	        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
	            modelMap.put("success", false);
	            modelMap.put("errMsg", "验证码错误！");
	            return modelMap;
	        }
	        Product product = null;
	        // 接收前端传递过来的product
	        String productStr = null;
	        // 商品图片缩略图（输入流和名称的封装类）
	        ImageHolder thumbnail = null;
	 
	        // 将HttpServletRequest转型为MultipartHttpServletRequest，可以很方便地得到文件名和文件内容
	        MultipartHttpServletRequest multipartHttpServletRequest = null;
	        // 接收商品缩略图
	        CommonsMultipartFile thumbnailFile = null;
	        // 接收商品详情图片
	        List<ImageHolder> productDetailImgList = new ArrayList<ImageHolder>();
	 
	        // 创建一个通用的多部分解析器
	        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
	 
	        // Step2: 使用FastJson提供的api,实例化Product 构造调用service层的第一个参数
	        ObjectMapper mapper = new ObjectMapper();
	        // 获取前端传递过来的product,约定好使用productStr
	        try {
	            productStr = HttpServletRequestUtil.getString(request, "productStr");
	            product = mapper.readValue(productStr, Product.class);
	        } catch (Exception e) {
	            modelMap.put("success", false);
	            modelMap.put("errMsg", e.toString());
	            return modelMap;
	        }
	        // Step3: 商品缩略图 和 商品详情图 构造调用service层的第二个参数和第三个参数
	        try {
	            // 判断 request 是否有文件上传,即多部分请求
	            if (commonsMultipartResolver.isMultipart(request)) {
	                // 将request转换成多部分request
	                multipartHttpServletRequest = (MultipartHttpServletRequest) request;
	 
	                // 得到缩略图的CommonsMultipartFile ,和前端约定好使用thumbnail 传递
	                // ，并构建ImageHolder对象
	                thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
	                // 转化为ImageHolder，使用service层的参数类型要求
	                thumbnail = new ImageHolder(thumbnailFile.getInputStream(),thumbnailFile.getOriginalFilename());
	 
	                // 得到 商品详情的列表，和前端约定使用productImg + i 传递 ,并构建ImageHolder对象
	                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
	                    CommonsMultipartFile productDetailImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
	                    if (productDetailImgFile != null) {
	                        ImageHolder productDetailImg = new ImageHolder(productDetailImgFile.getInputStream(),
	                        		productDetailImgFile.getOriginalFilename());
	                        productDetailImgList.add(productDetailImg);
	                    } else {
	                        // 如果从请求中获取的到file为空，终止循环
	                        break;
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            modelMap.put("success", false);
	            modelMap.put("errMsg", e.toString());
	            return modelMap;
	        }
	 
	        // Step4 调用Service层
	        if (product != null ) {
	            try {
	                // 从session中获取shop信息，不依赖前端的传递更加安全
	                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
	                product.setShop(currentShop);
	                // 调用addProduct
	                ProductExecution pe = productService.modifyProduct(product, thumbnail, productDetailImgList);
	                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
	                    modelMap.put("success", true);
	                } else {
	                    modelMap.put("success", false);
	                    modelMap.put("errMsg", pe.getStateInfo());
	                }
	            } catch (ProductOperationException e) {
	                modelMap.put("success", false);
	                modelMap.put("errMsg", e.toString());
	                return modelMap;
	            }
	        } else {
	            modelMap.put("success", false);
	            modelMap.put("errMsg", "请输入商品信息");
	        }
	        return modelMap;
	    }
	
	/**
	 * 根据商品id信息返回数据库中的商品信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String ,Object>  getProductById(@RequestParam Long productId){
		
		 Map<String ,Object> modelMap = new HashMap<String ,Object>();
		 try {
			 if(productId > -1) {//冗余判断
				 Product product = productService.getProductById(productId);
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
			//从session中获取当前商品所属的店铺的id信息 这样做的目的是为了减少对前端数据的依赖 这里暂时直接写死为1
//			Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
			Shop shop = new Shop();
			shop.setShopId(1L);
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
