package com.felix.o2o.enums;

public enum WechatAuthStateEnum {

	FAIL(-1,"openId��������"),
	SUCCESS(0,"�����ɹ�"),
	NULL(2,"openIdΪ��");
	private int state;//״̬
	private String stateInfo;//״̬��Ϣ
	
	private WechatAuthStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * ���ݴ����state������Ӧ��enumֵ
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
