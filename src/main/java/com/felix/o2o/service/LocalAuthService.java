package com.felix.o2o.service;

import com.felix.o2o.dto.LocalAuthExecution;
import com.felix.o2o.entity.LocalAuth;
import com.felix.o2o.exceptions.LocalAuthOperationException;

public interface LocalAuthService {
	/**
	 * 通过账号密码获取平台账号信息
	 * @param userName
	 * @return
	 */
	LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

	/**
	 * 通过userid获取平台账号信息
	 * @param userId
	 * @return
	 */
	LocalAuth getLocalAuthByUserId(long userId);
	/**
	 * 绑定微信，生成平台专属的账号
	 * @param localAuth
	 * @return
	 * @throws RuntimeException
	 */
	LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
			throws LocalAuthOperationException;

	/**
	 * 修改平台账号的登录密码
	 * @param localAuthId
	 * @param username
	 * @param password
	 * @param newPassword
	 * @param lastEditTime
	 * @return
	 */
	LocalAuthExecution modifyLocalAuth(Long userId, String username,
			String password, String newPassword)
					throws LocalAuthOperationException;
}


