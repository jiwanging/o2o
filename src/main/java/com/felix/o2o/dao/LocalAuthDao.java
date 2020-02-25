package com.felix.o2o.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.felix.o2o.entity.LocalAuth;



public interface LocalAuthDao {

	/**
	 * ͨ���˺ź������ѯ��Ӧ��Ϣ����¼��
	 * @param userName
	 * @param password
	 * @return
	 */
	LocalAuth queryLocalByUserNameAndPwd(@Param("userName") String userName,
			@Param("password") String password);

	/**
	 * ͨ���û�id��ѯ��Ӧlocalauth
	 * @param userId
	 * @return
	 */
	LocalAuth queryLocalByUserId(@Param("userId") long userId);

	/**
	 * ���ƽ̨�˺�
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(LocalAuth localAuth);

	/**
	 * ͨ��userid��username��password��������
	 * @param localAuth
	 * @return
	 */
	int updateLocalAuth(@Param("userId") Long userId,
			@Param("userName") String userName,
			@Param("password") String password,
			@Param("newPassword") String newPassword,
			@Param("lastEditTime") Date lastEditTime);
}



