package com.felix.o2o.entity;

import java.util.Date;

public class Area {

	//为什么使用引用类型 而不是基础类型 因为基础类型会使用默认值 某些情况下是不适用的
	private Integer areaId;// ID
	private String areaName;// 名称
	private Integer priority;// 权重
	private Date createTime;// 创建时间
	private Date lastEditTime;// 最后修改时间

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

}
