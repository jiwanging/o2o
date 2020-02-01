package com.felix.o2o.service;

import java.io.InputStream;

import com.felix.o2o.dto.ShopExecution;
import com.felix.o2o.entity.Shop;
import com.felix.o2o.exceptions.ShopOperationException;

public interface ShopService {

	/**
	 * g����shopCondition��ҳ�����б�����
	 * @param shopCondition
	 * @param pageIndex //����ǰ�˺ͺ���ǲ�һ���� ���������  ǰ����ҳ��
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
	/**
	 * ���ӵ��̵ķ���
	 * @param shop
	 * @param shopImagInputStream
	 * @param targetAddr
	 * @return
	 */
	ShopExecution addShop(Shop shop, InputStream shopImagInputStream, String targetAddr);
	
	/**
	 * ���ݵ���id��ѯ��������Ϣ�ķ���
	 * @param shopId
	 * @return
	 * @throws ShopOperationException
	 */
	Shop getByShopId(long shopId) throws ShopOperationException;
	
	/**
	 * ���ݴ����shop���� �޸Ķ�Ӧ��������Ϣ�ķ���
	 * @param shop
	 * @param shopImagInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution modifyShop(Shop shop, InputStream shopImagInputStream,String fileName)throws ShopOperationException;
}
