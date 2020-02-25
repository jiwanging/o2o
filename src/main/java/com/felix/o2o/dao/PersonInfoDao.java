package com.felix.o2o.dao;

import com.felix.o2o.entity.PersonInfo;

public interface PersonInfoDao {

	/**
	 * 通过用户名查询person信息
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(long userId);
	
	/**
	 * 添加Person信息
	 * @param personInfo
	 * @return
	 */
	int insertPersonInfo(PersonInfo personInfo);
}
