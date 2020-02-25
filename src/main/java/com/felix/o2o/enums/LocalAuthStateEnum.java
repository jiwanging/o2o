package com.felix.o2o.enums;

public enum LocalAuthStateEnum {

	FAIL(-1,"������Ϣ��������"),
	SUCCESS(0,"�����ɹ�"),
	NULL_AUTH_INFO(2,"�����û���ϢΪ��"),
	ONLY_ONE_ACCOUNT(1,"���˺��Ѵ���");
	private int state;//״̬
	private String stateInfo;//״̬��Ϣ
	
	private LocalAuthStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * ���ݴ����state������Ӧ��enumֵ
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
