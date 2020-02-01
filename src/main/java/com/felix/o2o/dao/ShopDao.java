package com.felix.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.felix.o2o.entity.Shop;

public interface ShopDao {

	/**
	 * ��ҳ��ѯ���̣�������������У���������ģ����������״̬��
	 * �����������Id��owner
	 * @param shopCondition
	 * @param rowIndex �ӵڼ��п�ʼȡ
	 * @param pageSize ÿҳ��ʾ������
	 * @return
	 */
	//ע�� param��������ָ��Ψһ��id ����ȡ���� ֻ��һ������ʱ���Բ���
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	/**
	 * ����queryShopList��ѯ������
	 * @param shop
	 * @return
	 */
	
	int queryShopCount(@Param("shopCondition") Shop shop);
	
	/**
	 * ��������
	 * @param shop
	 * @return Ӱ�������
	 */
	int insertShop(Shop shop);
	
	/**
	 * ���µ�����Ϣ
	 * @param shop
	 * @return Ӱ�������
	 */
	int updataShop(Shop shop);
	
	/**
	 * ͨ��shop id��ѯ����
	 * @param shop
	 * @return Ӱ�������
	 */
	Shop queryByShopId(long shopId);
}
