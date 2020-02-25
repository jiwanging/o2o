package com.felix.o2o.service;

import com.felix.o2o.dto.WechatAuthExecution;
import com.felix.o2o.entity.WechatAuth;
import com.felix.o2o.exceptions.WechatAuthOperationException;

public interface WechatAuthService {

	/**
	 * ͨ��openId����ƽ̨��Ӧ��΢���˺�
	 * @param openId
	 * @return
	 */
	WechatAuth getWechatAuthByOpenId(String openId);
	
	/**
	 * ע�᱾ƽ̨��΢���˺�
	 * @param openId
	 * @return
	 */
	WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException;
}
