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
	
	//֧���ϴ���Ʒ����ͼ���������
	private static final int IMAGEMAXCOUNT = 1;
	
	/**
	 * ���ݽ����ύ����Ϣ�޸�ָ������Ʒ��Ϣ
	 * @param request
	 * @return
	 */
	   /*
	    * ��������޸�product��controller����
	    * */
	    @RequestMapping(value ="/modifyproduct" ,method = RequestMethod.POST)
	    @ResponseBody
	    private  Map<String,Object> modifyProduct(HttpServletRequest request){
	        Map<String, Object> modelMap = new HashMap<String, Object>();
	        // ����Ʒ�༭ʱ����û������¼ܲ�����ʱ�����
	        // ��Ϊǰ���������֤���жϣ�������������֤���ж�
	        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
	        // ��֤�����
	        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
	            modelMap.put("success", false);
	            modelMap.put("errMsg", "��֤�����");
	            return modelMap;
	        }
	        Product product = null;
	        // ����ǰ�˴��ݹ�����product
	        String productStr = null;
	        // ��ƷͼƬ����ͼ�������������Ƶķ�װ�ࣩ
	        ImageHolder thumbnail = null;
	 
	        // ��HttpServletRequestת��ΪMultipartHttpServletRequest�����Ժܷ���صõ��ļ������ļ�����
	        MultipartHttpServletRequest multipartHttpServletRequest = null;
	        // ������Ʒ����ͼ
	        CommonsMultipartFile thumbnailFile = null;
	        // ������Ʒ����ͼƬ
	        List<ImageHolder> productDetailImgList = new ArrayList<ImageHolder>();
	 
	        // ����һ��ͨ�õĶಿ�ֽ�����
	        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
	 
	        // Step2: ʹ��FastJson�ṩ��api,ʵ����Product �������service��ĵ�һ������
	        ObjectMapper mapper = new ObjectMapper();
	        // ��ȡǰ�˴��ݹ�����product,Լ����ʹ��productStr
	        try {
	            productStr = HttpServletRequestUtil.getString(request, "productStr");
	            product = mapper.readValue(productStr, Product.class);
	        } catch (Exception e) {
	            modelMap.put("success", false);
	            modelMap.put("errMsg", e.toString());
	            return modelMap;
	        }
	        // Step3: ��Ʒ����ͼ �� ��Ʒ����ͼ �������service��ĵڶ��������͵���������
	        try {
	            // �ж� request �Ƿ����ļ��ϴ�,���ಿ������
	            if (commonsMultipartResolver.isMultipart(request)) {
	                // ��requestת���ɶಿ��request
	                multipartHttpServletRequest = (MultipartHttpServletRequest) request;
	 
	                // �õ�����ͼ��CommonsMultipartFile ,��ǰ��Լ����ʹ��thumbnail ����
	                // ��������ImageHolder����
	                thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
	                // ת��ΪImageHolder��ʹ��service��Ĳ�������Ҫ��
	                thumbnail = new ImageHolder(thumbnailFile.getInputStream(),thumbnailFile.getOriginalFilename());
	 
	                // �õ� ��Ʒ������б���ǰ��Լ��ʹ��productImg + i ���� ,������ImageHolder����
	                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
	                    CommonsMultipartFile productDetailImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
	                    if (productDetailImgFile != null) {
	                        ImageHolder productDetailImg = new ImageHolder(productDetailImgFile.getInputStream(),
	                        		productDetailImgFile.getOriginalFilename());
	                        productDetailImgList.add(productDetailImg);
	                    } else {
	                        // ����������л�ȡ�ĵ�fileΪ�գ���ֹѭ��
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
	 
	        // Step4 ����Service��
	        if (product != null ) {
	            try {
	                // ��session�л�ȡshop��Ϣ��������ǰ�˵Ĵ��ݸ��Ӱ�ȫ
	                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
	                product.setShop(currentShop);
	                // ����addProduct
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
	            modelMap.put("errMsg", "��������Ʒ��Ϣ");
	        }
	        return modelMap;
	    }
	
	/**
	 * ������Ʒid��Ϣ�������ݿ��е���Ʒ��Ϣ
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String ,Object>  getProductById(@RequestParam Long productId){
		
		 Map<String ,Object> modelMap = new HashMap<String ,Object>();
		 try {
			 if(productId > -1) {//�����ж�
				 Product product = productService.getProductById(productId);
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
			//��session�л�ȡ��ǰ��Ʒ�����ĵ��̵�id��Ϣ ��������Ŀ����Ϊ�˼��ٶ�ǰ�����ݵ����� ������ʱֱ��д��Ϊ1
//			Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
			Shop shop = new Shop();
			shop.setShopId(1L);
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
