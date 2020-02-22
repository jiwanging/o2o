package com.felix.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.felix.o2o.BaseTest;
import com.felix.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest{

	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Test
	public void testQueryShopCategoryNull() {
		List<ShopCategory> list = shopCategoryDao.queryShopCategory(null);
		assertEquals(1, list.size());
		
	}
	
	@Test
	@Ignore
	public void testQueryShopCategory() {
//		ShopCategory shopCategory = new ShopCategory();
//		shopCategory.setShopCategoryId(1L);
		
		List<ShopCategory> list = shopCategoryDao.queryShopCategory(new ShopCategory());
		assertEquals(2, list.size());
		
		ShopCategory shopCategory = new ShopCategory();
		ShopCategory parentShopCategory = new ShopCategory();
		parentShopCategory.setShopCategoryId(1L);
		shopCategory.setParent(parentShopCategory);
		list = shopCategoryDao.queryShopCategory(shopCategory);
		assertEquals(1, list.size());
		
	}
	
}
