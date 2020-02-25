package com.felix.o2o.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.felix.o2o.dao.PersonInfoDao;
import com.felix.o2o.entity.PersonInfo;
import com.felix.o2o.service.PersonInfoService;

public class PersonInfoServiceImpl implements PersonInfoService {

	@Autowired
	private PersonInfoDao personInfoDao;
	
	@Override
	public PersonInfo getPersonInfoById(long userId) {

		return personInfoDao.queryPersonInfoById(userId);
	}

}
