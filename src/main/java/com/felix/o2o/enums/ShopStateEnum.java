package com.felix.o2o.enums;

public enum ShopStateEnum {

	CHECK(0,"�����"),
	OFFLINE(-1,"�Ƿ�����"),
	SUCCESS(1,"�����ɹ�"),
	PASS(2,"ͨ����֤"),
	INNER_ERROR(-1001,"�ڲ�ϵͳ����"),
	NUll_SHOPID(-1002,"ShoIdΪ��"),
	NULL_SHOP(-1003,"shop��ϢΪ��");
	
	private int state;//״̬
	private String stateInfo;//״̬��Ϣ
	
	private ShopStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * ���ݴ����state������Ӧ��enumֵ
	 */
	public static ShopStateEnum stateOf(int state) {
		for (ShopStateEnum shopStateEnum : values()) {
			if(shopStateEnum.getState() == state) {
				return shopStateEnum;
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
