package com.felix.o2o.dao;

import com.felix.o2o.entity.WechatAuth;

public interface WechatAuthDao {

	 /**
	 * ͨ��openId��ѯ��Ӧ��ƽ̨��΢�ź�
	 * @param openId
	 * @return
     */
	 WechatAuth queryWechatInfoByOpenId(String openId);
	/**
	* ��Ӷ�Ӧ��ƽ̨��΢�ź�
	* @param wechatAuth
	* @return
	*/
	int insertWechatAuth(WechatAuth wechatAuth);
}
