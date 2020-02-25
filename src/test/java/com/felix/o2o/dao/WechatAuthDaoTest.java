package com.felix.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.felix.o2o.BaseTest;
import com.felix.o2o.entity.PersonInfo;
import com.felix.o2o.entity.WechatAuth;

public class WechatAuthDaoTest extends BaseTest {

	@Autowired 
	private WechatAuthDao wechatAuthDao;
	
	@Test
	public void testQueryWechatInfoByOpenId() {
		WechatAuth wechatAuth = wechatAuthDao.queryWechatInfoByOpenId("12345");
		System.out.println("-----------------------"+wechatAuth.getPersonInfo().getName()+"-------------------------------------");
		assertEquals("²âÊÔ", wechatAuth.getPersonInfo().getName());
	}
	
	@Test
	@Ignore
	public void testInsertWechatAuth() {
		WechatAuth wechatAuth = new WechatAuth();
		wechatAuth.setCreateTime(new Date());
		wechatAuth.setOpenId("12345");
		wechatAuth.setUserId(1L);
		PersonInfo personInfo = new PersonInfo();
		personInfo.setCreateTime(new Date());
		personInfo.setEmail("119000@qq.com");
		personInfo.setEnableStatus(1);
		personInfo.setGender("ÄÐ");;
		personInfo.setName("felix");;
		personInfo.setUserId(2L);
		personInfo.setUserType(1);
		wechatAuth.setPersonInfo(personInfo);
		
		int num = wechatAuthDao.insertWechatAuth(wechatAuth);
		System.out.println("------------------------------------------------------------");
		assertEquals(1, num);
	}
}
