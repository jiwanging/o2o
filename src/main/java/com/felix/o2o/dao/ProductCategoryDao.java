package com.felix.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.felix.o2o.entity.ProductCategory;

public interface ProductCategoryDao {

	/**
	 * ���ݵ�����Ϣɾ����������������Ʒ������Ϣ
	 * @param shopId ����Id
	 * @return
	 */
	int deleteProductCategory(@Param("productcategoryId")long productcategoryId,
			@Param("shopId")long shopId);
	
	/**
	 * ���ݵ�����Ϣȡ����������������Ʒ������Ϣ
	 * @param shopId ����Id
	 * @return
	 */
	List<ProductCategory> queryProductCategory(long shopId);

	/**
	 * ��Ʒ������������
	 * @param productCategory
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategory);
}
