package com.felix.o2o.dto;

import java.util.List;

import com.felix.o2o.entity.WechatAuth;
import com.felix.o2o.enums.ShopStateEnum;
import com.felix.o2o.enums.WechatAuthStateEnum;

public class WechatAuthExecution {


	//���״̬
	private int state;
	
	//״̬��ʶ
	private String stateInfo;
	
	//����
	private int count;
	
	//΢���û���Ϣ
	private WechatAuth wechatAuth;
	
	//΢���û���Ϣ�б�
	private List<WechatAuth> WechatAuthList;
	
	

	public WechatAuthExecution() {
		
	}
	
	/**
	 * ���̲���ʧ��ʱʹ�õĹ�����
	 * @param stateEnum
	 */
	public WechatAuthExecution(WechatAuthStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	/**
	 * ���̲����ɹ�ʱʹ�õĹ�����
	 * @param stateEnum
	 * @param shop
	 */
	public WechatAuthExecution(WechatAuthStateEnum stateEnum, WechatAuth wechatAuth) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.wechatAuth = wechatAuth;
	}
	
	/**
	 * ���̲����ɹ�ʱʹ�õĹ�����
	 * @param stateEnum
	 * @param shop
	 */
	public WechatAuthExecution(WechatAuthStateEnum stateEnum, List<WechatAuth> WechatAuthList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.WechatAuthList = WechatAuthList;
	}
	
	
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<WechatAuth> getWechatAuthList() {
		return WechatAuthList;
	}

	public void setWechatAuthList(List<WechatAuth> wechatAuthList) {
		WechatAuthList = wechatAuthList;
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

	public WechatAuth getWechatAuth() {
		return wechatAuth;
	}

	public void setWechatAuth(WechatAuth wechatAuth) {
		this.wechatAuth = wechatAuth;
	}

}
