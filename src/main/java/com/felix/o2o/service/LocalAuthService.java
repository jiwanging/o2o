package com.felix.o2o.service;

import com.felix.o2o.dto.LocalAuthExecution;
import com.felix.o2o.entity.LocalAuth;
import com.felix.o2o.exceptions.LocalAuthOperationException;

public interface LocalAuthService {
	/**
	 * ͨ���˺������ȡƽ̨�˺���Ϣ
	 * @param userName
	 * @return
	 */
	LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

	/**
	 * ͨ��userid��ȡƽ̨�˺���Ϣ
	 * @param userId
	 * @return
	 */
	LocalAuth getLocalAuthByUserId(long userId);
	/**
	 * ��΢�ţ�����ƽ̨ר�����˺�
	 * @param localAuth
	 * @return
	 * @throws RuntimeException
	 */
	LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
			throws LocalAuthOperationException;

	/**
	 * �޸�ƽ̨�˺ŵĵ�¼����
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


