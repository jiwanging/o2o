package com.felix.o2o.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.felix.o2o.BaseTest;
import com.felix.o2o.dto.WechatAuthExecution;
import com.felix.o2o.entity.PersonInfo;
import com.felix.o2o.entity.WechatAuth;
import com.felix.o2o.exceptions.WechatAuthOperationException;

public class WechatAuthServiceTest extends BaseTest {

	@Autowired
	private WechatAuthService wechatAuthService;
	
	
	@Test
	public void testregister() throws WechatAuthOperationException {
		WechatAuth wechatAuth = new WechatAuth();
		wechatAuth.setCreateTime(new Date());
		wechatAuth.setOpenId("12345678");
		wechatAuth.setUserId(1L);
		PersonInfo personInfo = new PersonInfo();
		personInfo.setCreateTime(new Date());
		personInfo.setEmail("129000@qq.com");
		personInfo.setEnableStatus(1);
		personInfo.setGender("Ů");;
		personInfo.setName("felix_woman");;
		personInfo.setUserId(2L);
		personInfo.setUserType(1);
		wechatAuth.setPersonInfo(personInfo);
		
		WechatAuthExecution wechatAuthExecution = wechatAuthService.register(wechatAuth);
		System.out.println("---------------"+wechatAuthExecution.getState()+""
				+ "---------------------------------------------");
	}
	
}

