package com.felix.o2o.dto;

import java.util.List;

import com.felix.o2o.entity.LocalAuth;
import com.felix.o2o.enums.LocalAuthStateEnum;

public class LocalAuthExecution {

	//结果状态
	private int state;
	
	//状态标识
	private String stateInfo;
	
	//数量
	private int count;
	
	//微信用户信息
	private LocalAuth localAuth;
	
	//微信用户信息列表
	private List<LocalAuth> localAuthList;
	
	

	public LocalAuthExecution() {
		
	}
	
	/**
	 * 店铺操作失败时使用的构造器
	 * @param stateEnum
	 */
	public LocalAuthExecution(LocalAuthStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	/**
	 * 店铺操作成功时使用的构造器
	 * @param stateEnum
	 * @param shop
	 */
	public LocalAuthExecution(LocalAuthStateEnum stateEnum, LocalAuth localAuth) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.localAuth = localAuth;
	}
	
	/**
	 * 店铺操作成功时使用的构造器
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
