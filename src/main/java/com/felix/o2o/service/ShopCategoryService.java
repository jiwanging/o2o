package com.felix.o2o.service;

import java.util.List;

import com.felix.o2o.entity.ShopCategory;

public interface ShopCategoryService {

	 public static final String SCLISTKEY="shopcategorylist";
	/**
	 * ���ݲ�ѯ����  ��ȡshopcategory�б�
	 * @param shopCategoryCondition
	 * @return
	 */
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);//
}
