package com.felix.o2o.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felix.o2o.dao.ProductDao;
import com.felix.o2o.dao.ProductImgDao;
import com.felix.o2o.dto.ProductExecution;
import com.felix.o2o.dto.ShopExecution;
import com.felix.o2o.entity.Product;
import com.felix.o2o.entity.ProductImg;
import com.felix.o2o.entity.Shop;
import com.felix.o2o.enums.ProductStateEnum;
import com.felix.o2o.exceptions.ProductOperationException;
import com.felix.o2o.exceptions.ShopOperationException;
import com.felix.o2o.service.ProductService;
import com.felix.o2o.util.ImageHolder;
import com.felix.o2o.util.ImageUtil;
import com.felix.o2o.util.PageCalculator;
import com.felix.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductImgDao productImgDao;
	
	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {

		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);//��ȡ��ѯ�б�
		int count = productDao.queryProductCount(productCondition);//��ȡ����
		ProductExecution pe = new ProductExecution();
		if(productList != null) {
			pe.setProductList(productList);
			pe.setCount(count);
		}else {
			pe.setState(ProductStateEnum.INNER_ERROR.getState());
		}
		return pe;
	}

	/**
	 * ������Ʒid�������ݿ��д��ڵ���Ʒ��Ϣ
	 */
	@Override
	public Product getByProductId(long productId) throws ShopOperationException {
		return productDao.queryByProductId(productId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, InputStream shopImagInputStream, String fileName)
			throws ShopOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) {

		//�����߼�
		//1.��鴫��Ĳ����Ƿ�Ϸ�
		//2.��������ͼ ���������ͼ�ĵ�ַ
		//3.������Ʒ������Ʒ��⣬���õ����ص���Ʒid
		//4.������Ʒid�������������ͼ
		//��鴫��Ĳ����Ƿ�Ϸ�
		if(product == null || (product.getShop() == null && product.getShop().getShopId() != null)) {
			return new ProductExecution(ProductStateEnum.NULL_PRODUCT);
		}
		try {
			//��ʼ����Ҫ����Ʒ��Ϣ
			product.setEnableStatus(1);//Ĭ��Ϊ�ϼ�״̬
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//��ʼ��������ͼ
			//�洢����ͼƬ
			if(thumbnail.getInputStream() != null && thumbnail.getFileName() != null) {//�ж�����ͼ·���������Ƿ�Ϊ��
				try {
					addProductImag(product,thumbnail);
					//�����ݿ������Ϣ
					int effectedNum = productDao.insertProduct(product);
					if(effectedNum <= 0) {
						throw new ProductOperationException("��Ʒ���ʧ�ܣ�");//ʹ��RuntimeException�������лع�
					}
					//��ʼ��������ͼ ���������ݿ�
					addProductImagList(product,productImgList);
				}catch(Exception e) {
					throw new ProductOperationException("addShopImg error" + e.getMessage());
				}
			}
		}catch(Exception e) {
			throw new ShopOperationException("addShop error"+e.getMessage());
		}
		
		return new ProductExecution(ProductStateEnum.CHECK,product);//���سɹ���״̬
	}
	
	/**
	 * ���崦������ͼ�ķ���
	 * @param product
	 * @param productImgList
	 */
	private void addProductImagList(Product product, List<ImageHolder> productImgList) {

		try {
			long productId = product.getProductId();
			String filePath = product.getImgAddr();//�ļ�·��
			List<ProductImg> imgList = new ArrayList<ProductImg>();
			//��������
			for (int index = 0; index < productImgList.size(); index++) {
				ProductImg productImg = new ProductImg();
				productImg.setCreateTime(new Date());
				productImg.setProductId(productId);
				productImg.setPriority(1);
				productImg.setImgDesc("��Ʒ����ͼ");
				String productImgAddr = ImageUtil.generateThumbnail(productImgList.get(index).getInputStream(),
						productImgList.get(index).getFileName(),filePath);//����ÿ��ͼƬ��������ÿ������ͼ��Ӧ�ĵ�ַ
				productImg.setImgAddr(productImgAddr);
				imgList.add(productImg);
			}
			if(imgList != null && imgList.size() > 0) {
				productImgDao.batchInsertProductImg(imgList);//����������������ݿ�
			}
		}catch(Exception e) {
			throw new ProductOperationException("�������ͼƬʧ�ܣ�");
		}
		
	}

	private void addProductImag(Product product, ImageHolder thumbnail) {
		//��ȡshopͼƬ�����ֵ·��
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String productImgAddr = ImageUtil.generateThumbnail(thumbnail.getInputStream(),thumbnail.getFileName(),dest);
		product.setImgAddr(productImgAddr);
	}

}
