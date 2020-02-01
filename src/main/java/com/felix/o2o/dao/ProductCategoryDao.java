package com.felix.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.felix.o2o.entity.ProductCategory;

public interface ProductCategoryDao {

	/**
	 * 根据店铺信息删除店铺所包含的商品分类信息
	 * @param shopId 店铺Id
	 * @return
	 */
	int deleteProductCategory(@Param("productcategoryId")long productcategoryId,
			@Param("shopId")long shopId);
	
	/**
	 * 根据店铺信息取出店铺所包含的商品分类信息
	 * @param shopId 店铺Id
	 * @return
	 */
	List<ProductCategory> queryProductCategory(long shopId);

	/**
	 * 商品类别作批量添加
	 * @param productCategory
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategory);
}
