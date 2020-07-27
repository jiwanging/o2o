package com.felix.o2o.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.felix.o2o.entity.User;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

	@Autowired 
	private UserDao userDao;

	@Test
	@Ignore
	public void testQueryUser() {
		User user = userDao.queryUserByUserName("admin");
		System.out.println("-----------------"+user.getPassword()+"-------------------------------------------");
		assertEquals("123456", user.getPassword());
	}
	
	@Test
	@Ignore
	public void testQueryUserByVague() {
		User user = userDao.queryUserByVague("a");
		System.out.println("-----------------"+user.getUserName()+"-------------------------------------------");
		assertEquals("123456", user.getPassword());
	}
	
	@Test
	@Ignore
	public void testInsertUser() {
		User user = new User();
		user.setUserName("test_user33");
		user.setPassword("9080789");
		user.setEmail("1152062134@qq.com");
		user.setMobile("18381307935");
		user.setRole(1);
		user.setState(false);
		int num = userDao.insertUser(user);
		System.out.println("-----------------"+"添加用户成功"+"-------------------------------------------");
		assertEquals(1, num);
	}
	
	@Test
	@Ignore
	public void testDeleteUser() {
		int num = userDao.deleteUserById(4L);
		System.out.println("-----------------"+"删除用户数据成功"+"-------------------------------------------");
		assertEquals(1, num);
	}
	
	@Test
	@Ignore
	public void testUpdataUser() {
		User user = new User();
		user.setUserName("test_user1");
		user.setId(2L);
		user.setEmail("123456@psdc.com");
		user.setMobile("12345678");
		int num = userDao.updateUser(user);
		System.out.println("-----------------"+"更新用户数据成功"+"-------------------------------------------");
		assertEquals(1, num);
	}
	
	@Test
	public void testGetUserlist() {
		String query = "";
		int pagesize = 2;
		int pagenum = 1;
		List<User> list = userDao.getUserList(query,(pagenum-1)*pagesize,pagesize);
		System.out.println("-----------------"+"查询用户信息成功"+"-------------------------------------------");
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getUserName());
		}
		assertEquals(2, list.size());
	}
	
	@Test
	@Ignore
	public void testQueryUserById() {
		long id = 9L;
		User user = userDao.queryUserById(9L);
		System.out.println("-----------------"+"查询用户信息成功"+"-------------------------------------------");
		assertEquals("email", user.getEmail());
	}
}
