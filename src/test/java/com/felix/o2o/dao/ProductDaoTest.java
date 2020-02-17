package com.felix.o2o.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.felix.o2o.BaseTest;
import com.felix.o2o.entity.Product;
import com.felix.o2o.entity.ProductCategory;
import com.felix.o2o.entity.Shop;

public class ProductDaoTest extends BaseTest{

	@Autowired
	private ProductDao productDao;
	
	@Test
	@Ignore
	public void testUpdateProduct() {
		Product product = new Product();
		product.setProductId(2L);
		product.setProductName("咖啡");
		product.setProductDesc("很好喝的咖啡1111");
		product.setImgAddr("测试地址1");
		product.setNormalPrice("10.00");
		product.setPromotionPrice("10.00");
		product.setPriority(2);
		product.setCreateTime(new Date());
		product.setLastEditTime(new Date());
		product.setEnableStatus(1);
		
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(1L);
		product.setProductCategory(productCategory);
		
		Shop shop = new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		
		int num = productDao.updateProduct(product);
		System.out.println("影响的行数是："+num+"---------");
	}
	
	@Test
	@Ignore
	public void testqueryProductbyId() {
		Product product = new Product();
		product = productDao.queryProductById(11L);//查询id为1的商品信息
		
		System.out.println("-----------------"+"商品个数"+product.toString()+"-----------------");
	}
	
	@Test
	@Ignore
	public void queryProductList() {
		Product product = new Product();
		product.setProductName("咖啡");
		product.setProductDesc("很好喝的咖啡");
		product.setImgAddr("测试地址1");
		product.setNormalPrice("10.00");
		product.setPromotionPrice("10.00");
		product.setPriority(2);
		product.setCreateTime(new Date());
		product.setLastEditTime(new Date());
		product.setEnableStatus(1);
		
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(1L);
		product.setProductCategory(productCategory);
		
		Shop shop = new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		List<Product> list = productDao.queryProductList(product, 0, 10);
		System.out.println("-----------------"+"商品个数"+list.size()+"-----------------");
	}
	
	
	@Test
	@Ignore
	public void testInsertShop() {
		Product product = new Product();
		product.setProductId(2L);
		product.setProductName("咖啡");
		product.setProductDesc("很好喝的咖啡");
		product.setImgAddr("测试地址1");
		product.setNormalPrice("10.00");
		product.setPromotionPrice("10.00");
		product.setPriority(2);
		product.setCreateTime(new Date());
		product.setLastEditTime(new Date());
		product.setEnableStatus(1);
		
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(1L);
		product.setProductCategory(productCategory);
		
		Shop shop = new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		
		int num = productDao.insertProduct(product);
		System.out.println("影响的行数是："+num+"---------");
	}
}
