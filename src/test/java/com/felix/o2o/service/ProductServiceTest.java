package com.felix.o2o.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.felix.o2o.BaseTest;
import com.felix.o2o.dto.ProductExecution;
import com.felix.o2o.entity.Product;
import com.felix.o2o.entity.Shop;
import com.felix.o2o.enums.ProductStateEnum;
import com.felix.o2o.util.ImageHolder;


public class ProductServiceTest extends BaseTest{

	@Autowired
	private ProductService productService;
	
	@Test
	public void testModifyProduct() { 
		Product product = new Product();
		product.setProductId(11L);
		product.setProductName("����");
		product.setProductDesc("�ܺúȵĿ���2333");
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
			//����ͼ
			thumbnailInputStream = new FileInputStream(new File("E:\\thomas.jpg"));
			String fileName = "thomas.jpg";
			ImageHolder thumbnailImgHolder = new ImageHolder(thumbnailInputStream,fileName);
			
			//����ͼ
			List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
			imgInputStream1 =  new FileInputStream(new File("E:\\thomas.jpg"));
			String fileName1 = "thomas.jpg";
			ImageHolder ImgHolder1 = new ImageHolder(imgInputStream1,fileName1);
			productImgList.add(ImgHolder1);
			
			imgInputStream2 =  new FileInputStream(new File("E:\\�����ܷ�.jpg"));
			String fileName2 = "�����ܷ�.jpg";
			ImageHolder ImgHolder2 = new ImageHolder(imgInputStream2,fileName2);
			productImgList.add(ImgHolder2);
			
			ProductExecution pe = productService.modifyProduct(product, thumbnailImgHolder, productImgList);
			if(pe.getState() == 1) {
				System.out.println("=---------------ִ�гɹ�------------------=");
			}else {
				System.out.println("=---------------ִ�гɹ�------------------=");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	@Ignore
	public void testInsertProduct() { 
		Product product = new Product();
		product.setProductName("�����");
		product.setProductDesc("�ܺóԵı����");
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
			
			imgInputStream2 =  new FileInputStream(new File("E:\\�����ܷ�.jpg"));
			String fileName2 = "�����ܷ�.jpg";
			ImageHolder ImgHolder2 = new ImageHolder(imgInputStream2,fileName2);
			productImgList.add(ImgHolder2);
			
			productService.addProduct(product, thumbnailImgHolder, productImgList);
			
			System.out.println("=---------------ִ�гɹ�------------------=");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
