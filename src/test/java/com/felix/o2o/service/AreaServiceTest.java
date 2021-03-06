package com.felix.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.felix.o2o.BaseTest;
import com.felix.o2o.entity.Area;

public class AreaServiceTest extends BaseTest{

	@Autowired
	private AreaService areaServiceImpl;//如何找到具体实现类的呢
	
	@Autowired
    private CacheService cacheService;
	
	@Test
	public void testareaService() {
		List<Area> list = areaServiceImpl.getAreaList();
		assertEquals("西苑", list.get(0).getAreaName());
	}
	
	/**
	 * 测试redis的方法
	 */
	@Test
    public void testGetAreaList() {
        List<Area> areaList = areaServiceImpl.getAreaList();
        assertEquals("西苑", areaList.get(0).getAreaName());
        cacheService.removeFromCache(areaServiceImpl.AREALISTKEY);
        areaList = areaServiceImpl.getAreaList();
    }
}
