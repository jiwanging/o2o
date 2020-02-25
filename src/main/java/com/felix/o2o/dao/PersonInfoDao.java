package com.felix.o2o.dao;

import com.felix.o2o.entity.PersonInfo;

public interface PersonInfoDao {

	/**
	 * ͨ���û�����ѯperson��Ϣ
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(long userId);
	
	/**
	 * ���Person��Ϣ
	 * @param personInfo
	 * @return
	 */
	int insertPersonInfo(PersonInfo personInfo);
}
