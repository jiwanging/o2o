package com.felix.o2o.dto;

import java.util.List;

import com.felix.o2o.entity.WechatAuth;
import com.felix.o2o.enums.ShopStateEnum;
import com.felix.o2o.enums.WechatAuthStateEnum;

public class WechatAuthExecution {


	//结果状态
	private int state;
	
	//状态标识
	private String stateInfo;
	
	//数量
	private int count;
	
	//微信用户信息
	private WechatAuth wechatAuth;
	
	//微信用户信息列表
	private List<WechatAuth> WechatAuthList;
	
	

	public WechatAuthExecution() {
		
	}
	
	/**
	 * 店铺操作失败时使用的构造器
	 * @param stateEnum
	 */
	public WechatAuthExecution(WechatAuthStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	/**
	 * 店铺操作成功时使用的构造器
	 * @param stateEnum
	 * @param shop
	 */
	public WechatAuthExecution(WechatAuthStateEnum stateEnum, WechatAuth wechatAuth) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.wechatAuth = wechatAuth;
	}
	
	/**
	 * 店铺操作成功时使用的构造器
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
