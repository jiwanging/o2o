package com.felix.o2o.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.felix.o2o.BaseTest;
import com.felix.o2o.entity.Product;
import com.felix.o2o.entity.Shop;
import com.felix.o2o.util.ImageHolder;


public class ProductServiceTest extends BaseTest{

	@Autowired
	private ProductService productService;
	
	@Test
	public void testInsertProduct() { 
		Product product = new Product();
		product.setProductName("冰淇淋");
		product.setProductDesc("很好吃的冰淇淋");
		product.setNormalPrice("11");
		product.setPromotionPrice("9.9");
		product.setPriority(2);
		Shop shop = new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		InputStream thumbnailInputStream = null;
		InputStream imgInputStream1 = null;
		InputStream imgInputStream2 = null;
		try {
			thumbnailInputStream = new FileInputStream(new File("E:\\thomas.jpg"));
			String fileName = "thomas.jpg";
			ImageHolder thumbnailImgHolder = new ImageHolder(thumbnailInputStream,fileName);
			
			List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
			imgInputStream1 =  new FileInputStream(new File("E:\\thomas.jpg"));
			String fileName1 = "thomas.jpg";
			ImageHolder ImgHolder1 = new ImageHolder(imgInputStream1,fileName1);
			productImgList.add(ImgHolder1);
			
			imgInputStream2 =  new FileInputStream(new File("E:\\电脑跑分.jpg"));
			String fileName2 = "电脑跑分.jpg";
			ImageHolder ImgHolder2 = new ImageHolder(imgInputStream2,fileName2);
			productImgList.add(ImgHolder2);
			
			productService.addProduct(product, thumbnailImgHolder, productImgList);
			
			System.out.println("=---------------执行成功------------------=");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
