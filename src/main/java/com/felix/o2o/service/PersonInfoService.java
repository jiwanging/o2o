package com.felix.o2o.service;

import com.felix.o2o.entity.PersonInfo;

public interface PersonInfoService {

	/**
	 * ͨ��id��ȡpersoninfo��Ϣ
	 * @param personInfoId
	 * @return
	 */
	PersonInfo getPersonInfoById(long userId);
}
