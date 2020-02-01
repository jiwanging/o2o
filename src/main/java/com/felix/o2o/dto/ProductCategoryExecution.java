package com.felix.o2o.dto;

import java.util.List;

import com.felix.o2o.entity.ProductCategory;
import com.felix.o2o.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {

	//���״̬
	private int state;
	
	//״̬��ʶ
	private String stateInfo;
	
	//���̵�����
	private int count;
	
	//��������Ʒ�����Ʒ����ʱ���õ���
	private ProductCategory productCategory;
	
	//shop�б���ѯ��Ʒ����ʱ��ʹ�ã�
	private List<ProductCategory> productcategoryList;
	
	public ProductCategoryExecution() {
		
	}
	
	/**
	 * ���̲���ʧ��ʱʹ�õĹ�����
	 * @param stateEnum
	 */
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	/**
	 * ���̲����ɹ�ʱʹ�õĹ�����
	 * @param stateEnum
	 * @param shop
	 */
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, ProductCategory productCategory) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategory = productCategory;
	}
	
	/**
	 * ���̲����ɹ�ʱʹ�õĹ�����
	 * @param stateEnum
	 * @param shop
	 */
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> productcategoryList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productcategoryList = productcategoryList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<ProductCategory> getProductcategoryList() {
		return productcategoryList;
	}

	public void setProductcategoryList(List<ProductCategory> productcategoryList) {
		this.productcategoryList = productcategoryList;
	}
	
	
}
