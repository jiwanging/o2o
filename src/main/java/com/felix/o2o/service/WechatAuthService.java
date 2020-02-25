package com.felix.o2o.service;

import com.felix.o2o.dto.WechatAuthExecution;
import com.felix.o2o.entity.WechatAuth;
import com.felix.o2o.exceptions.WechatAuthOperationException;

public interface WechatAuthService {

	/**
	 * 通过openId查找平台对应的微信账号
	 * @param openId
	 * @return
	 */
	WechatAuth getWechatAuthByOpenId(String openId);
	
	/**
	 * 注册本平台的微信账号
	 * @param openId
	 * @return
	 */
	WechatAuthExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException;
}
