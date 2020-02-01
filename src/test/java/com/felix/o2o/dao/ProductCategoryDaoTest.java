package com.felix.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.felix.o2o.BaseTest;
import com.felix.o2o.entity.ProductCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest{

	@Autowired 
	private ProductCategoryDao productCategoryDao;
	
	/**
	 * 验证queryProductCategory方法
	 */
	@Test
	public void testQueryProductCategory() {
		long shopId = 1;
		List<ProductCategory> list = productCategoryDao.queryProductCategory(shopId);
		System.out.println("总个数为："+list.size()+"-------------");
		System.out.println(list.toString());
	}
	
	
	@Test
	@Ignore
	public void testBatchInsertProductCategory() {
		ProductCategory p1 = new ProductCategory();
		p1.setProductCategoryName("商品类别89");
		p1.setPriority(1);
		p1.setCreateTime(new Date());
		p1.setShopId(1L);
		
		ProductCategory p2 = new ProductCategory();
		p2.setProductCategoryName("商品类别00");
		p2.setPriority(1);
		p2.setCreateTime(new Date());
		p2.setShopId(1L);
		
		List<ProductCategory> list = new ArrayList<ProductCategory>();
		list.add(p1);
		list.add(p2);
		int num = productCategoryDao.batchInsertProductCategory(list);
		assertEquals(2, num);
	}
	
	@Test
	@Ignore
	public void testDeleteProductCategory() {
		ProductCategory p1 = new ProductCategory();
		p1.setProductCategoryName("商品类别89");
		p1.setPriority(1);
		p1.setCreateTime(new Date());
		p1.setShopId(1L);
		p1.setProductCategoryId(12L);
		int num = productCategoryDao.deleteProductCategory(p1.getProductCategoryId(), p1.getShopId());
		assertEquals(1, num);
		
		ProductCategory p2 = new ProductCategory();
		p2.setProductCategoryName("商品类别00");
		p2.setPriority(1);
		p2.setCreateTime(new Date());
		p2.setShopId(1L);
		p2.setProductCategoryId(13L);
		
		int num1 = productCategoryDao.deleteProductCategory(p2.getProductCategoryId(), p2.getShopId());
		assertEquals(1, num1);
		
	}
}
