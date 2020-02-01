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
	
	//֧���ϴ���Ʒ����ͼ���������
	private static final int IMAGEMAXCOUNT = 6;
	
	/**
	 * ������Ʒid��Ϣ�������ݿ��е���Ʒ��Ϣ
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getbyproductid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String ,Object>  getByProductId(HttpServletRequest request){
		
		 Map<String ,Object> modelMap = new HashMap<String ,Object>();
		 Long productId = HttpServletRequestUtil.getLong(request, "productId");
		 try {
			 if(productId > -1) {//�����ж�
				 Product product = productService.getByProductId(productId);
				 //����Ӧ���Ǹ���session���õ���ǰ�ĵ���id ��������Ϊ�˷��� ��д��
				 List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(1L);//��ȡ��Ʒ����
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
		//��֤��֤��
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "�����˴������֤��");
			return modelMap;
		}
		//1.���ܲ�ת����Ӧ�Ĳ��� ������Ʒ��Ϣ������ͼ��Ϣ������ͼƬ��Ϣ
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		ImageHolder thumbnailImgHolder = null;//������ͼ��
		List<ImageHolder> imgHolderList = new ArrayList<ImageHolder>();//������ͼ������
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		try {
			product = mapper.readValue(productStr, Product.class);//����ǰ̨���ݹ�������Ʒ��Ϣ
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile productThumbnailImg = null;//����ͼ
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {//�ж��Ƿ����ϴ����ļ���
			MultipartHttpServletRequest multipartHttpServletRequest = 
					(MultipartHttpServletRequest) request;
			try {
				//��������ͼ��
				productThumbnailImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");//��ȡ��Ʒ����ͼ������
				thumbnailImgHolder = new ImageHolder(productThumbnailImg.getInputStream(),productThumbnailImg.getOriginalFilename());
				//��������ͼ��
				for(int index = 0; index < IMAGEMAXCOUNT; index++) {
					CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + index);//��ȡ��Ʒ����ͼ������
					if(productImgFile.getInputStream() == null)
						break;
					ImageHolder imgHolder = new ImageHolder(productImgFile.getInputStream(),productImgFile.getOriginalFilename());
					imgHolderList.add(imgHolder);
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "�ϴ���ƷͼƬ����Ϊ��");
				return modelMap;
			}
			
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "�ϴ���ƷͼƬ����Ϊ��");
			return modelMap;
		}
		//2.��Ʒ��Ϣ���
		if(product != null) {
			//��session�л�ȡ��ǰ��Ʒ�����ĵ��̵�id��Ϣ ��������Ŀ����Ϊ�˼��ٶ�ǰ�����ݵ�����
			Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
			Shop shop = new Shop();
			shop.setShopId(currentShop.getShopId());
			product.setShop(shop);
			try {
				ProductExecution pe = productService.addProduct(product,thumbnailImgHolder,imgHolderList);
				if(pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("errMsg", "�����Ʒ�ɹ�");
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
			modelMap.put("errMsg", "��������Ʒ��Ϣ");
			return modelMap;
		}
	}
}
