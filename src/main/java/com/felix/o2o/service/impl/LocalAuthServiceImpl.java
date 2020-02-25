package com.felix.o2o.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felix.o2o.dao.LocalAuthDao;
import com.felix.o2o.dto.LocalAuthExecution;
import com.felix.o2o.entity.LocalAuth;
import com.felix.o2o.enums.LocalAuthStateEnum;
import com.felix.o2o.exceptions.LocalAuthOperationException;
import com.felix.o2o.service.LocalAuthService;
import com.felix.o2o.util.MD5;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {
	@Autowired
	private LocalAuthDao localAuthDao;

	@Override
	public LocalAuth getLocalAuthByUserNameAndPwd(String userName,
			String password) {
		return localAuthDao.queryLocalByUserNameAndPwd(userName, MD5.getMd5(password));
	}

	@Override
	public LocalAuth getLocalAuthByUserId(long userId) {
		return localAuthDao.queryLocalByUserId(userId);
	}
	@Override
	@Transactional
	public LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
			throws LocalAuthOperationException {
		//��ֵ�жϣ������localAuth�˺����룬�û���Ϣ�ر���userid����Ϊ�գ�����ֱ�ӷ��ش���
		if (localAuth == null || localAuth.getPassword() == null
				|| localAuth.getUsername() == null
				|| localAuth.getPersonInfo() == null
				||localAuth.getPersonInfo().getUserId()==null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		//��ѯ���û��Ƿ��Ѱ󶨹�ƽ̨�˺�
		LocalAuth tempAuth = localAuthDao.queryLocalByUserId(localAuth
				.getPersonInfo().getUserId());
		if (tempAuth != null) {
			//����󶨹���ֱ���˳����Ա�֤ƽ̨�˺ŵ�Ψһ��
			return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			//���֮ǰû�а󶨹�ƽ̨�˺ţ��򴴽�һ��ƽ̨�˺�����û���
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			//���������md5����
			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			//�жϴ����Ƿ�ɹ�
			if (effectedNum <= 0) {
				throw new LocalAuthOperationException("�ʺŰ�ʧ��");
			} else {
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS,
						localAuth);
			}
		} catch (Exception e) {
			throw new LocalAuthOperationException("insertLocalAuth error: "
					+ e.getMessage());
		}
	}

	@Override
	@Transactional
	public LocalAuthExecution modifyLocalAuth(Long userId, String userName,
			String password, String newPassword)throws LocalAuthOperationException {
		//�ǿ��жϣ��жϴ�����û�id���˺ţ��¾������Ƿ�Ϊ�գ��¾������Ƿ���ͬ
		//�������������򷵻ش�����Ϣ
		if (userId != null && userName != null && password != null
				&& newPassword != null && !password.equals(newPassword)) {
			try {
				//�������룬�������������md5����
				int effectedNum = localAuthDao.updateLocalAuth(userId,
						userName, MD5.getMd5(password),
						MD5.getMd5(newPassword), new Date());
				//�жϸ����Ƿ�ɹ�
				if (effectedNum <= 0) {
					throw new LocalAuthOperationException("��������ʧ��");
				}
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new LocalAuthOperationException("��������ʧ��:" + e.toString());
			}
		} else {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
	}

}

