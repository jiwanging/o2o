package com.felix.o2o.service;

import com.felix.o2o.entity.PersonInfo;

public interface PersonInfoService {

	/**
	 * 通过id获取personinfo信息
	 * @param personInfoId
	 * @return
	 */
	PersonInfo getPersonInfoById(long userId);
}
