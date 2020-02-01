package com.felix.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.felix.o2o.BaseTest;
import com.felix.o2o.entity.Area;
import com.felix.o2o.entity.PersonInfo;
import com.felix.o2o.entity.Shop;
import com.felix.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{

	@Autowired
	private ShopDao shopDao;
	
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("���Եĵ���");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("�����");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}
	
	@Test
	@Ignore
	public void testUpdateShop() {
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
		shop.setShopName("���Եĵ���");
		shop.setShopDesc("��������");
		shop.setShopAddr("���Ե�ַ");
		shop.setPhone("18381902073");
		shop.setShopImg("test img");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("�����");
		int effectedNum = shopDao.updataShop(shop);
		assertEquals(1, effectedNum);
	}
	
	@Test
	@Ignore
	public void testQueryByShopId() {
		Shop shop = shopDao.queryByShopId(12L);
		System.out.println("==============="+shop.getShopAddr()+"====================");
	}
	
	/**
	 * ��֤queryShopList����
	 */
	@Test
	public void testQueryShopList() {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
       // int rowIndex = shopDao.queryShopCount(shopCondition);
		List<Shop> list = shopDao.queryShopList(shopCondition, 0, 5);
		System.out.println("-------------"+list.size()+"-------------");
	}
	
	/**
	 * ��֤queryShopCount����
	 */
	@Test
	@Ignore
	public void testQueryShopCount() {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
       // int rowIndex = shopDao.queryShopCount(shopCondition);
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("�ܸ���Ϊ��"+count+"-------------");
	}
	
	
}
