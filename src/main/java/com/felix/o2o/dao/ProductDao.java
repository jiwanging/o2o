package com.felix.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.felix.o2o.entity.Product;

public interface ProductDao {

	/**
	 * ��ҳ��ѯ��Ʒ�б�������������У���Ʒ����ģ��������Ʒ״̬��
	 * ����Id����Ʒ���
	 * @param productCondition
	 * @param rowIndex �ӵڼ��п�ʼȡ
	 * @param pageSize ÿҳ��ʾ������
	 * @return
	 */
	//ע�� param��������ָ��Ψһ��id ����ȡ���� ֻ��һ������ʱ���Բ���
	List<Product> queryProductList(@Param("productCondition") Product productCondition,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	
	/**
	 * ������Ʒ
	 * @param shop
	 * @return Ӱ�������
	 */
	int insertProduct(Product product);
	
	/**
	 * ͨ��product id��ѯ��Ʒ
	 * @param shop
	 * @return Ӱ�������
	 */
	Product queryByProductId(long productId);
	
	/**
	 * ��ѯ��Ʒ������
	 * @param productCondition
	 * @return
	 */
	int queryProductCount(@Param("productCondition") Product product);
	
	/**
	 * ͨ��product id��ѯ��Ʒ��Ϣ ����ͼƬ�������Ϣ 
	 * @param productId
	 * @return Ӱ�������
	 */
	Product queryProductById(long productId);
	
	/**
	 * ͨ����Ʒ��Ϣ ����ͼƬ�������Ϣ 
	 * @param product
	 * @return Ӱ�������
	 */
	int updateProduct(Product product);
}
