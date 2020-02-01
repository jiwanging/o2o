package com.felix.o2o.dao;

import java.util.List;

import com.felix.o2o.entity.ProductImg;

public interface ProductImgDao {

	List<ProductImg> queryProductImgList(long productId);
	
	/**
	 * ���������Ʒ������ͼƬ
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	
	/**
	 * ������Ʒidɾ����ƷͼƬ
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
}
