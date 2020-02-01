package com.felix.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.felix.o2o.BaseTest;
import com.felix.o2o.dto.ShopExecution;
import com.felix.o2o.entity.Area;
import com.felix.o2o.entity.PersonInfo;
import com.felix.o2o.entity.Shop;
import com.felix.o2o.entity.ShopCategory;
import com.felix.o2o.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest {

	@Autowired
	private ShopService shopService;
	
	
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException {
		
		//初始化店铺信息
		Shop shop = new Shop();
		shop.setShopId(1L);
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺1");
		shop.setShopDesc("test1");
		shop.setShopAddr("test1");
		shop.setPhone("test1");
		shop.setShopImg("test img");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");

		File shopImag = new File("E://thomas.jpg");
		InputStream shopImagInputStream = new FileInputStream(new File("E://thomas.jpg"));
		ShopExecution shopExecution =  shopService.addShop(shop, shopImagInputStream,shopImag.getName());
		
		assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
		
	}
	
	@Test
	@Ignore
	public void testModifyShop() throws FileNotFoundException {
		
		//初始化店铺信息
		Shop shop = shopService.getByShopId(10L);
		shop.setShopImg("E://missionbackground.jpg");
		shop.setShopName("小小咖啡店11");
		File shopImagFile = new File("E://missionbackground.jpg");
		InputStream shopImagInputStream = new FileInputStream(shopImagFile);
		ShopExecution shopExecution =  shopService.modifyShop(shop, shopImagInputStream,"missionbackground.jpg");
		System.out.println("==================="+shopExecution.getShop().getShopImg()+"=================");
		
	}
	
	@Test
	public void testgetShopList() throws FileNotFoundException {
		
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
       // int rowIndex = shopDao.queryShopCount(shopCondition);
		ShopExecution se = shopService.getShopList(shopCondition, 0, 5);
		System.out.println("-------------"+se.getShopList().size()+"-------------");
		System.out.println("-------------"+se.getCount()+"-------------");
	}
	
	
	
}
