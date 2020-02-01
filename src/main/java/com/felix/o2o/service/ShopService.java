package com.felix.o2o.service;

import java.io.InputStream;

import com.felix.o2o.dto.ShopExecution;
import com.felix.o2o.entity.Shop;
import com.felix.o2o.exceptions.ShopOperationException;

public interface ShopService {

	/**
	 * g根据shopCondition分页返回列表数据
	 * @param shopCondition
	 * @param pageIndex //这里前端和后端是不一样的 后端以列数  前端以页数
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
	/**
	 * 增加店铺的方法
	 * @param shop
	 * @param shopImagInputStream
	 * @param targetAddr
	 * @return
	 */
	ShopExecution addShop(Shop shop, InputStream shopImagInputStream, String targetAddr);
	
	/**
	 * 根据店铺id查询出店铺信息的方法
	 * @param shopId
	 * @return
	 * @throws ShopOperationException
	 */
	Shop getByShopId(long shopId) throws ShopOperationException;
	
	/**
	 * 根据传入的shop数据 修改对应的商铺信息的方法
	 * @param shop
	 * @param shopImagInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution modifyShop(Shop shop, InputStream shopImagInputStream,String fileName)throws ShopOperationException;
}
