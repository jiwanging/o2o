package com.felix.o2o.enums;

public enum WechatAuthStateEnum {

	FAIL(-1,"openId输入有误"),
	SUCCESS(0,"操作成功"),
	NULL(2,"openId为空");
	private int state;//状态
	private String stateInfo;//状态信息
	
	private WechatAuthStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 依据传入的state返回相应的enum值
	 */
	public static WechatAuthStateEnum stateOf(int state) {
		for (WechatAuthStateEnum wechatAuthStateEnum : values()) {
			if(wechatAuthStateEnum.getState() == state) {
				return wechatAuthStateEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

}
