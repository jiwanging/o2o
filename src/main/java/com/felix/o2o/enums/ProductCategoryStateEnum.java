package com.felix.o2o.enums;

public enum ProductCategoryStateEnum {

	SUCCESS(1,"创建成功"),
	INNER_ERROR(-1001,"操作失败"),
	EMPTY_LIST(-1002,"添加数少于1");
	
	private int state;//状态
	private String stateInfo;//状态信息
	
	private ProductCategoryStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 依据传入的state返回相应的enum值
	 */
	public static ProductCategoryStateEnum stateOf(int index) {
		for (ProductCategoryStateEnum state : values()) {
			if(state.getState() == index) {
				return state;
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
