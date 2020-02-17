package com.felix.o2o.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.felix.o2o.BaseTest;
import com.felix.o2o.entity.ProductImg;

public class ProductImgDaoTest extends BaseTest{

	@Autowired
	private ProductImgDao productImgDao;
	
	
	@Test
	@Ignore
	public void testAbatchInsertProductImg() throws Exception{
		
		//像tb_product_img表中插入两个详情图片记录
		ProductImg p1 = new ProductImg();
		p1.setImgAddr("图片1");
		p1.setImgDesc("测试图片1");
		p1.setPriority(1);
		p1.setCreateTime(new Date());
		p1.setProductId(2L);
		
		ProductImg p2 = new ProductImg();
		p2.setImgAddr("图片2");
		p2.setImgDesc("测试图片2");
		p2.setPriority(2);
		p2.setCreateTime(new Date());
		p2.setProductId(2L);
		
		List<ProductImg> list = new ArrayList<ProductImg>();
		list.add(p1);
		list.add(p2);
		
		int num = productImgDao.batchInsertProductImg(list);
		System.out.println("-----------------"+"执行完毕影响行数"+num+"-----------------");
		
	}
	
	@Test
	@Ignore
	public void testBQueryProductImgList() throws Exception{
		List<ProductImg> list = productImgDao.queryProductImgList(1L);
		System.out.println("-----------------"+"商品详情图片个数"+list.size()+"-----------------");
	}
	
	@Test
	public void testCDeleteByproductImgId() throws Exception{
		int num = productImgDao.deleteProductImgByProductId(6L);
		System.out.println("-----------------"+"执行完毕影响行数"+num+"-----------------");
	}
}
