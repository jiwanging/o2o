package com.felix.o2o.dto;

import java.util.List;

import com.felix.o2o.entity.LocalAuth;
import com.felix.o2o.enums.LocalAuthStateEnum;

public class LocalAuthExecution {

	//���״̬
	private int state;
	
	//״̬��ʶ
	private String stateInfo;
	
	//����
	private int count;
	
	//΢���û���Ϣ
	private LocalAuth localAuth;
	
	//΢���û���Ϣ�б�
	private List<LocalAuth> localAuthList;
	
	

	public LocalAuthExecution() {
		
	}
	
	/**
	 * ���̲���ʧ��ʱʹ�õĹ�����
	 * @param stateEnum
	 */
	public LocalAuthExecution(LocalAuthStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	/**
	 * ���̲����ɹ�ʱʹ�õĹ�����
	 * @param stateEnum
	 * @param shop
	 */
	public LocalAuthExecution(LocalAuthStateEnum stateEnum, LocalAuth localAuth) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.localAuth = localAuth;
	}
	
	/**
	 * ���̲����ɹ�ʱʹ�õĹ�����
	 * @param stateEnum
	 * @param shop
	 */
	public LocalAuthExecution(LocalAuthStateEnum stateEnum, List<LocalAuth> localAuthList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.localAuthList = localAuthList;
	}
	
	
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<LocalAuth> getlocalAuthList() {
		return localAuthList;
	}

	public void setlocalAuthList(List<LocalAuth> localAuthList) {
		this.localAuthList = localAuthList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public LocalAuth getlocalAuth() {
		return localAuth;
	}

	public void setlocalAuth(LocalAuth localAuth) {
		this.localAuth = localAuth;
	}



}
