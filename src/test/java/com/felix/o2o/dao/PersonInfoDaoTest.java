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

public class PersonInfoDaoTest extends BaseTest{

	@Autowired 
	private PersonInfoDao personInfoDao;
	
	@Test
	public void testQueryPeroInfoById() {
		PersonInfo person = personInfoDao.queryPersonInfoById(2L);
		System.out.println("------------------------------------------------------------");
		assertEquals("felix", person.getName());
	}
	
	@Test
	@Ignore
	public void testInsertPeroInfo() {
		PersonInfo personInfo = new PersonInfo();
		personInfo.setCreateTime(new Date());
		personInfo.setEmail("119000@qq.com");
		personInfo.setEnableStatus(1);
		personInfo.setGender("ÄÐ");;
		personInfo.setName("felix");;
		personInfo.setUserId(1L);
		personInfo.setUserType(1);
		int num = personInfoDao.insertPersonInfo(personInfo);
		System.out.println("-----Ó°ÏìÁË----------------"+num+"--------------------");
		assertEquals(1, num);
	}
	
}
