package com.felix.o2o.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felix.o2o.dao.PersonInfoDao;
import com.felix.o2o.dao.WechatAuthDao;
import com.felix.o2o.dto.WechatAuthExecution;
import com.felix.o2o.entity.PersonInfo;
import com.felix.o2o.entity.WechatAuth;
import com.felix.o2o.enums.WechatAuthStateEnum;
import com.felix.o2o.exceptions.WechatAuthOperationException;
import com.felix.o2o.service.WechatAuthService;

@Service
public class WechatAuthServiceImpl implements WechatAuthService {

	private static Logger log = LoggerFactory.getLogger(WechatAuthServiceImpl.class);
	
	@Autowired
	private PersonInfoDao personInfoDao;
	
	@Autowired
	private WechatAuthDao wechatAuthDao;
	
	@Override
	public WechatAuth getWechatAuthByOpenId(String openId) {
		
		return wechatAuthDao.queryWechatInfoByOpenId(openId);
	}

	@Override
	@Transactional
	public WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException {
		WechatAuthExecution we = new WechatAuthExecution();
		//��ֵ�ж�
		if(wechatAuth != null && wechatAuth.getOpenId() == null) {
			return new WechatAuthExecution(WechatAuthStateEnum.NULL,wechatAuth);
		}
		try {
			wechatAuth.setCreateTime(new Date());
			if(wechatAuth.getPersonInfo() != null && wechatAuth.getPersonInfo().getUserId() != null) {
				try {
					wechatAuth.getPersonInfo().setCreateTime(new Date());
					wechatAuth.getPersonInfo().setEnableStatus(1);
					PersonInfo personInfo = wechatAuth.getPersonInfo();
					int effectedNum = personInfoDao.insertPersonInfo(personInfo);
					if(effectedNum <= 0){
						throw new WechatAuthOperationException("����û���Ϣʧ��"); //�������쳣 �Է�������Ļع�
					}
				}catch(Exception e) {
					log.error("insertPersonInfo erro" + e.toString());
					throw new WechatAuthOperationException("insertPersonInfo error" + e.getMessage()); //�������쳣 �Է�������Ļع�
				}
			}
			int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
			if(effectedNum <= 0){
				throw new WechatAuthOperationException("�˺Ŵ���ʧ��"); //�������쳣 �Է�������Ļع�
			}else {
				return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS,wechatAuth);
			}
		}catch(Exception e) {
			log.error("insertwechatAuth erro" + e.toString());
			throw new WechatAuthOperationException("insertwechatAuth error" + e.getMessage()); //�������쳣 �Է�������Ļع�
		}
		
	}

}
