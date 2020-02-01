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
		//检查传入的参数是否合法
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//初始化必要参数信息
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//向数据库添加信息
			int effectedNum = shopDao.insertShop(shop);
			if(effectedNum <= 0) {
				throw new ShopOperationException("店铺创建失败！");//使用RuntimeException事务会进行回滚
			}else {
				//存储图片
				if(shopImagInputStream != null) {
					try {
						addshopImag(shop,shopImagInputStream,targetAddr);
						shop.getShopImg();
					}catch(Exception e) {
						throw new ShopOperationException("addShopImg error" + e.getMessage());
					}
					//更新店铺的图片地址
					effectedNum = shopDao.updataShop(shop);
					if(effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址失败！");//使用RuntimeException事务会进行回滚
					}
				}
			}
		}catch(Exception e) {
			throw new ShopOperationException("addShop error"+e.getMessage());
		}
		
		return new ShopExecution(ShopStateEnum.CHECK,shop);//返回成功的状态
	}

	private void addshopImag(Shop shop, InputStream shopImagInputStream,String fileName) {
		//获取shop图片的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImagInputStream,fileName,dest);
		shop.setShopImg(shopImgAddr);
	}

	/**
	 * 根据店铺id获取店铺信息
	 */
	@Override
	public Shop getByShopId(long shopId) throws ShopOperationException {
		return shopDao.queryByShopId(shopId);
	}
	
	
	/**
	 * 根据传入的信息在数据库更新商铺信息
	 */
	@Override
	public ShopExecution modifyShop(Shop shop, InputStream shopImagInputStream, String fileName)
			throws ShopOperationException {
		//非空判断
		if(shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			
			//1.判断是否需要处理图片
			if(shopImagInputStream != null && fileName != null && !"".equals(fileName)) {//有图片上传要先删除原有的图片
				Shop tempShop = shopDao.queryByShopId(shop.getShopId());
				if(tempShop.getShopImg()!=null) {
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());//先删除原有的图片文件
				}
				addshopImag(shop, shopImagInputStream, fileName);//生成新的图片
			}
			//2.更新店铺信息
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
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);//获取查询列表
		int count = shopDao.queryShopCount(shopCondition);//获取总数
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
