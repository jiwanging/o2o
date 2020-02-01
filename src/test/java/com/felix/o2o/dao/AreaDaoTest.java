package com.felix.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.felix.o2o.BaseTest;
import com.felix.o2o.entity.Area;

public class AreaDaoTest extends BaseTest{
	

	@Autowired 
	private AreaDao areaDao;
	
	@Test
	public void testQueryArea() {
		List<Area> arealist = areaDao.queryArea();
		System.out.println("------------------------------------------------------------");
		assertEquals(2, arealist.size());
	}
}
