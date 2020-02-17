package com.felix.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.felix.o2o.entity.Product;

public interface ProductDao {

	/**
	 * 分页查询商品列表，可输入的条件有：商品名（模糊），商品状态，
	 * 店铺Id，商品类别
	 * @param productCondition
	 * @param rowIndex 从第几行开始取
	 * @param pageSize 每页显示的条数
	 * @return
	 */
	//注意 param的作用是指定唯一的id 用于取参数 只有一个参数时可以不用
	List<Product> queryProductList(@Param("productCondition") Product productCondition,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	
	/**
	 * 新增商品
	 * @param shop
	 * @return 影响的行数
	 */
	int insertProduct(Product product);
	
	/**
	 * 通过product id查询商品
	 * @param shop
	 * @return 影响的行数
	 */
	Product queryByProductId(long productId);
	
	/**
	 * 查询商品的总数
	 * @param productCondition
	 * @return
	 */
	int queryProductCount(@Param("productCondition") Product product);
	
	/**
	 * 通过product id查询商品信息 包括图片等相关信息 
	 * @param productId
	 * @return 影响的行数
	 */
	Product queryProductById(long productId);
	
	/**
	 * 通过商品信息 包括图片等相关信息 
	 * @param product
	 * @return 影响的行数
	 */
	int updateProduct(Product product);
}
