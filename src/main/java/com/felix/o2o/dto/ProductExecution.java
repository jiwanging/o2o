package com.felix.o2o.dto;

import java.util.List;

import com.felix.o2o.entity.Product;
import com.felix.o2o.enums.ProductStateEnum;

public class ProductExecution {

	//���״̬
	private int state;
	
	//״̬��ʶ
	private String stateInfo;
	
	//��Ʒ������
	private int count;
	
	//��������Ʒ����ɾ����Ʒ��ʱ���õ���
	private Product product;
	
	//�����б���ѯ�����б��ʱ��ʹ�ã�
	private List<Product> productList;
	
	public ProductExecution() {
		
	}
	
	/**
	 * ���̲���ʧ��ʱʹ�õĹ�����
	 * @param stateEnum
	 */
	public ProductExecution(ProductStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	/**
	 * ���̲����ɹ�ʱʹ�õĹ�����
	 * @param stateEnum
	 * @param Product
	 */
	public ProductExecution(ProductStateEnum stateEnum, Product product) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.product = product;
	}
	
	/**
	 * ���̲����ɹ�ʱʹ�õĹ�����
	 * @param stateEnum
	 * @param Product
	 */
	public ProductExecution(ProductStateEnum stateEnum, List<Product> productList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productList = productList;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
}
