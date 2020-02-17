package com.felix.o2o.enums;

public enum ProductStateEnum {

	CHECK(0,"�ϼ���"),
	OFFLINE(-1,"�Ƿ���Ʒ"),
	SUCCESS(1,"�����ɹ�"),
	PASS(2,"ͨ����֤"),
	INNER_ERROR(-1001,"�ڲ�ϵͳ����"),
	NUll_PRODUCTID(-1002,"ShoIdΪ��"),
	NULL_PRODUCT(-1003,"product��ϢΪ��"),
	EMPTY(-1004,"���༭����Ʒ��ϢΪ��");
	private int state;//״̬
	private String stateInfo;//״̬��Ϣ
	
	private ProductStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * ���ݴ����state������Ӧ��enumֵ
	 */
	public static ProductStateEnum stateOf(int state) {
		for (ProductStateEnum productStateEnum : values()) {
			if(productStateEnum.getState() == state) {
				return productStateEnum;
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
