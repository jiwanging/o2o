package com.felix.o2o.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felix.o2o.dao.ShopDao;
import com.felix.o2o.dto.ShopExecution;
import com.felix.o2o.entity.Shop;
import com.felix.o2o.enums.ShopStateEnum;
import com.felix.o2o.exceptions.ShopOperationException;
import com.felix.o2o.service.ShopService;
import com.felix.o2o.util.ImageUtil;
import com.felix.o2o.util.PageCalculator;
import com.felix.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;
	
	@Transactional
	public ShopExecution addShop(Shop shop, InputStream shopImagInputStream, String targetAddr) {
		//��鴫��Ĳ����Ƿ�Ϸ�
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//��ʼ����Ҫ������Ϣ
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//�����ݿ������Ϣ
			int effectedNum = shopDao.insertShop(shop);
			if(effectedNum <= 0) {
				throw new ShopOperationException("���̴���ʧ�ܣ�");//ʹ��RuntimeException�������лع�
			}else {
				//�洢ͼƬ
				if(shopImagInputStream != null) {
					try {
						addshopImag(shop,shopImagInputStream,targetAddr);
						shop.getShopImg();
					}catch(Exception e) {
						throw new ShopOperationException("addShopImg error" + e.getMessage());
					}
					//���µ��̵�ͼƬ��ַ
					effectedNum = shopDao.updataShop(shop);
					if(effectedNum <= 0) {
						throw new ShopOperationException("����ͼƬ��ַʧ�ܣ�");//ʹ��RuntimeException�������лع�
					}
				}
			}
		}catch(Exception e) {
			throw new ShopOperationException("addShop error"+e.getMessage());
		}
		
		return new ShopExecution(ShopStateEnum.CHECK,shop);//���سɹ���״̬
	}

	private void addshopImag(Shop shop, InputStream shopImagInputStream,String fileName) {
		//��ȡshopͼƬ�����ֵ·��
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImagInputStream,fileName,dest);
		shop.setShopImg(shopImgAddr);
	}

	/**
	 * ���ݵ���id��ȡ������Ϣ
	 */
	@Override
	public Shop getByShopId(long shopId) throws ShopOperationException {
		return shopDao.queryByShopId(shopId);
	}
	
	
	/**
	 * ���ݴ������Ϣ�����ݿ����������Ϣ
	 */
	@Override
	public ShopExecution modifyShop(Shop shop, InputStream shopImagInputStream, String fileName)
			throws ShopOperationException {
		//�ǿ��ж�
		if(shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			
			//1.�ж��Ƿ���Ҫ����ͼƬ
			if(shopImagInputStream != null && fileName != null && !"".equals(fileName)) {//��ͼƬ�ϴ�Ҫ��ɾ��ԭ�е�ͼƬ
				Shop tempShop = shopDao.queryByShopId(shop.getShopId());
				if(tempShop.getShopImg()!=null) {
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());//��ɾ��ԭ�е�ͼƬ�ļ�
				}
				addshopImag(shop, shopImagInputStream, fileName);//�����µ�ͼƬ
			}
			//2.���µ�����Ϣ
			shop.setLastEditTime(new Date());
			int effectNum = shopDao.updataShop(shop);
			if(effectNum <= 0) {
				return new ShopExecution(ShopStateEnum.INNER_ERROR); 
			}else {
				shop = shopDao.queryByShopId(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS,shop);
			}
			
		}catch(Exception e){
			throw new ShopOperationException("modifyShop error" + e.getMessage());
		}
	}

	//
	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);//��ȡ��ѯ�б�
		int count = shopDao.queryShopCount(shopCondition);//��ȡ����
		ShopExecution se = new ShopExecution();
		if(shopList != null) {
			se.setShopList(shopList);
			se.setCount(count);
		}else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

}
