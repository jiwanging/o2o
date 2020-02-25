package com.felix.o2o.enums;

public enum LocalAuthStateEnum {

	FAIL(-1,"本地信息输入有误"),
	SUCCESS(0,"操作成功"),
	NULL_AUTH_INFO(2,"本地用户信息为空"),
	ONLY_ONE_ACCOUNT(1,"该账号已存在");
	private int state;//状态
	private String stateInfo;//状态信息
	
	private LocalAuthStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 依据传入的state返回相应的enum值
	 */
	public static LocalAuthStateEnum stateOf(int state) {
		for (LocalAuthStateEnum LocalAuthStateEnum : values()) {
			if(LocalAuthStateEnum.getState() == state) {
				return LocalAuthStateEnum;
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
