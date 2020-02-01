package com.felix.o2o.dto;

import java.util.List;

import com.felix.o2o.entity.Shop;
import com.felix.o2o.enums.ShopStateEnum;

public class ShopExecution {

	//���״̬
	private int state;
	
	//״̬��ʶ
	private String stateInfo;
	
	//���̵�����
	private int count;
	
	//�����ĵ��̣���ɾ�ĵ��̵�ʱ���õ���
	private Shop shop;
	
	//shop�б���ѯ�����б��ʱ��ʹ�ã�
	private List<Shop> shopList;
	
	public ShopExecution() {
		
	}
	
	/**
	 * ���̲���ʧ��ʱʹ�õĹ�����
	 * @param stateEnum
	 */
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	/**
	 * ���̲����ɹ�ʱʹ�õĹ�����
	 * @param stateEnum
	 * @param shop
	 */
	public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
	}
	
	/**
	 * ���̲����ɹ�ʱʹ�õĹ�����
	 * @param stateEnum
	 * @param shop
	 */
	public ShopExecution(ShopStateEnum stateEnum, List<Shop> shop) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shop;
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

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	
	
	
	
}
