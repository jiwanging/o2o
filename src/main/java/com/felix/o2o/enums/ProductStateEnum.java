package com.felix.o2o.enums;

public enum ProductStateEnum {

	CHECK(0,"上架中"),
	OFFLINE(-1,"非法商品"),
	SUCCESS(1,"操作成功"),
	PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NUll_PRODUCTID(-1002,"ShoId为空"),
	NULL_PRODUCT(-1003,"product信息为空"),
	EMPTY(-1004,"待编辑的商品信息为空");
	private int state;//状态
	private String stateInfo;//状态信息
	
	private ProductStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 依据传入的state返回相应的enum值
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
