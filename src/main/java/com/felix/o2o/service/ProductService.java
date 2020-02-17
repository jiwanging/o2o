package com.felix.o2o.service;

import java.io.InputStream;
import java.util.List;

import com.felix.o2o.dto.ProductExecution;
import com.felix.o2o.dto.ShopExecution;
import com.felix.o2o.entity.Product;
import com.felix.o2o.entity.ProductImg;
import com.felix.o2o.entity.Shop;
import com.felix.o2o.exceptions.ProductOperationException;
import com.felix.o2o.exceptions.ShopOperationException;
import com.felix.o2o.util.ImageHolder;

public interface ProductService {

	/**
	 * 根据productCondition分页返回列表数据
	 * @param productCondition
	 * @param pageIndex //这里前端和后端是不一样的 后端以列数  前端以页数
	 * @param pageSize
	 * @return
	 */
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
	/**
	 * 增加店铺的方法
	 * @param shop
	 * @param shopImagInputStream
	 * @param targetAddr
	 * @return
	 */
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList);
	
	/**
	 * 根据店铺id查询出店铺信息的方法
	 * @param shopId
	 * @return
	 * @throws ShopOperationException
	 */
	public Product getByProductId(long shopId) throws ShopOperationException;
	
	/**
	 * 根据传入的shop数据 修改对应的商铺信息的方法
	 * @param shop
	 * @param shopImagInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	public ShopExecution modifyShop(Shop shop, InputStream shopImagInputStream,String fileName)throws ShopOperationException;
	
	/**
	 * 根据传入的product数据 修改对应的商品信息的方法
	 * @param product
	 * @param shopImagInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	public ProductExecution modifyProduct(Product product, ImageHolder imageHolder,List<ImageHolder> productList)throws ProductOperationException;

	/**
	 * 根据传入的id信息 查询相关的商品信息的方法
	 * @param productId
	 * @return product
	 * @throws ProductOperationException
	 */
	public Product getProductById(Long productId)throws ProductOperationException;
}
