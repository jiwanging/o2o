package com.felix.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.felix.o2o.entity.Shop;

public interface ShopDao {

	/**
	 * 分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，
	 * 店铺类别，区域Id，owner
	 * @param shopCondition
	 * @param rowIndex 从第几行开始取
	 * @param pageSize 每页显示的条数
	 * @return
	 */
	//注意 param的作用是指定唯一的id 用于取参数 只有一个参数时可以不用
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	/**
	 * 返回queryShopList查询的总数
	 * @param shop
	 * @return
	 */
	
	int queryShopCount(@Param("shopCondition") Shop shop);
	
	/**
	 * 新增店铺
	 * @param shop
	 * @return 影响的行数
	 */
	int insertShop(Shop shop);
	
	/**
	 * 更新店铺信息
	 * @param shop
	 * @return 影响的行数
	 */
	int updataShop(Shop shop);
	
	/**
	 * 通过shop id查询店铺
	 * @param shop
	 * @return 影响的行数
	 */
	Shop queryByShopId(long shopId);
}
