package com.felix.o2o.dto;

import java.util.List;

import com.felix.o2o.entity.ProductCategory;
import com.felix.o2o.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {

	//结果状态
	private int state;
	
	//状态标识
	private String stateInfo;
	
	//店铺的数量
	private int count;
	
	//操作的商品类别（商品类别的时候用到）
	private ProductCategory productCategory;
	
	//shop列表（查询商品类别的时候使用）
	private List<ProductCategory> productcategoryList;
	
	public ProductCategoryExecution() {
		
	}
	
	/**
	 * 店铺操作失败时使用的构造器
	 * @param stateEnum
	 */
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	/**
	 * 店铺操作成功时使用的构造器
	 * @param stateEnum
	 * @param shop
	 */
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, ProductCategory productCategory) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategory = productCategory;
	}
	
	/**
	 * 店铺操作成功时使用的构造器
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
