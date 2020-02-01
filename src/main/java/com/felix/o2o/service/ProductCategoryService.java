package com.felix.o2o.service;

import java.util.List;

import com.felix.o2o.dto.ProductCategoryExecution;
import com.felix.o2o.entity.ProductCategory;
import com.felix.o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {

	/**
	 * ��������µ���Ʒ������id��Ϊ�գ���ɾ��������Ʒ���
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException �׳�runtime�쳣 �Իع�����
	 */
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
	throws ProductCategoryOperationException;
	
	/**
	 * 
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException �׳�runtime�쳣 �Իع�����
	 */
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
	throws ProductCategoryOperationException;
	
	/**
	 * ���ݴ����shopId����������Ʒ������Ϣ
	 * @param shopId
	 * @return
	 */
	public List<ProductCategory> getProductCategoryList(long shopId);
}
