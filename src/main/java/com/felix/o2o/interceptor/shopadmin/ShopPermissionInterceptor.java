package com.felix.o2o.interceptor.shopadmin;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.felix.o2o.entity.Shop;
 
/**
 * ��ҹ���ϵͳ������֤������
 * 
 * @author xiangze
 *
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
	/**
	 * ��Ҫ����ǰ���أ����û���������ǰ����дpreHandle����߼��������û�����Ȩ�޵�����
	 * 
	 * @author xiangze
	 *
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// ��session�л�ȡ��ǰѡ��ĵ���
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		@SuppressWarnings("unchecked")
		// ��session�л�ȡ��ǰ�û��ɲ����ĵ����б�
		List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
		// �ǿ��ж�
		if (currentShop != null && shopList != null) {
			// �����ɲ����ĵ����б�
			for (Shop shop : shopList) {
				// �����ǰ�����ڿɲ������б����򷵻�true�����н��������û�����
				if (shop.getShopId() == currentShop.getShopId()) {
					return true;
				}
			}
		}
		// ������������������֤�򷵻�false,��ֹ�û�������ִ��
		return false;
	}
}
