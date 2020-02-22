package com.felix.o2o.service;

import java.util.List;

import com.felix.o2o.entity.ShopCategory;

public interface ShopCategoryService {

	/**
	 * 根据查询条件  获取shopcategory列表
	 * @param shopCategoryCondition
	 * @return
	 */
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);//
}
