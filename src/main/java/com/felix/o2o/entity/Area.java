package com.felix.o2o.entity;

import java.util.Date;

public class Area {

	//Ϊʲôʹ���������� �����ǻ������� ��Ϊ�������ͻ�ʹ��Ĭ��ֵ ĳЩ������ǲ����õ�
	private Integer areaId;// ID
	private String areaName;// ����
	private Integer priority;// Ȩ��
	private Date createTime;// ����ʱ��
	private Date lastEditTime;// ����޸�ʱ��

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
