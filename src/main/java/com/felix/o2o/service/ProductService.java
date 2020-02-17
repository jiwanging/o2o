package com.felix.o2o.service;

import java.io.InputStream;
import java.util.List;

import com.felix.o2o.dto.ProductExecution;
import com.felix.o2o.dto.ShopExecution;
import com.felix.o2o.entity.Product;
import com.felix.o2o.entity.ProductImg;
import com.felix.o2o.entity.Shop;
import com.felix.o2o.exceptions.ProductOperationException;
import com.felix.o2o.exceptions.ShopOperationException;
import com.felix.o2o.util.ImageHolder;

public interface ProductService {

	/**
	 * ����productCondition��ҳ�����б�����
	 * @param productCondition
	 * @param pageIndex //����ǰ�˺ͺ���ǲ�һ���� ���������  ǰ����ҳ��
	 * @param pageSize
	 * @return
	 */
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
	/**
	 * ���ӵ��̵ķ���
	 * @param shop
	 * @param shopImagInputStream
	 * @param targetAddr
	 * @return
	 */
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList);
	
	/**
	 * ���ݵ���id��ѯ��������Ϣ�ķ���
	 * @param shopId
	 * @return
	 * @throws ShopOperationException
	 */
	public Product getByProductId(long shopId) throws ShopOperationException;
	
	/**
	 * ���ݴ����shop���� �޸Ķ�Ӧ��������Ϣ�ķ���
	 * @param shop
	 * @param shopImagInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	public ShopExecution modifyShop(Shop shop, InputStream shopImagInputStream,String fileName)throws ShopOperationException;
	
	/**
	 * ���ݴ����product���� �޸Ķ�Ӧ����Ʒ��Ϣ�ķ���
	 * @param product
	 * @param shopImagInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	public ProductExecution modifyProduct(Product product, ImageHolder imageHolder,List<ImageHolder> productList)throws ProductOperationException;

	/**
	 * ���ݴ����id��Ϣ ��ѯ��ص���Ʒ��Ϣ�ķ���
	 * @param productId
	 * @return product
	 * @throws ProductOperationException
	 */
	public Product getProductById(Long productId)throws ProductOperationException;
}
