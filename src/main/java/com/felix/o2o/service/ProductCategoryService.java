package com.felix.o2o.service;

import java.util.List;

import com.felix.o2o.dto.ProductCategoryExecution;
import com.felix.o2o.entity.ProductCategory;
import com.felix.o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {

	/**
	 * 将此类别下的商品里的类别id置为空，再删除掉该商品类别
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException 抛出runtime异常 以回滚事务
	 */
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
	throws ProductCategoryOperationException;
	
	/**
	 * 
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException 抛出runtime异常 以回滚事务
	 */
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
	throws ProductCategoryOperationException;
	
	/**
	 * 根据传入的shopId搜索店铺商品分类信息
	 * @param shopId
	 * @return
	 */
	public List<ProductCategory> getProductCategoryList(long shopId);
}
