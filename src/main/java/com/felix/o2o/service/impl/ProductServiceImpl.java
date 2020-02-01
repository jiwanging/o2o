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
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);//获取查询列表
		int count = productDao.queryProductCount(productCondition);//获取总数
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
	 * 根据商品id返回数据库中存在的商品信息
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

		//处理逻辑
		//1.检查传入的参数是否合法
		//2.处理缩略图 并获得缩略图的地址
		//3.保存商品，将商品存库，并得到返回的商品id
		//4.根据商品id批量的添加详情图
		//检查传入的参数是否合法
		if(product == null || (product.getShop() == null && product.getShop().getShopId() != null)) {
			return new ProductExecution(ProductStateEnum.NULL_PRODUCT);
		}
		try {
			//初始化必要的商品信息
			product.setEnableStatus(1);//默认为上架状态
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//开始处理缩略图
			//存储缩略图片
			if(thumbnail.getInputStream() != null && thumbnail.getFileName() != null) {//判断缩略图路径与名称是否为空
				try {
					addProductImag(product,thumbnail);
					//向数据库添加信息
					int effectedNum = productDao.insertProduct(product);
					if(effectedNum <= 0) {
						throw new ProductOperationException("商品添加失败！");//使用RuntimeException事务会进行回滚
					}
					//开始处理详情图 并存入数据库
					addProductImagList(product,productImgList);
				}catch(Exception e) {
					throw new ProductOperationException("addShopImg error" + e.getMessage());
				}
			}
		}catch(Exception e) {
			throw new ShopOperationException("addShop error"+e.getMessage());
		}
		
		return new ProductExecution(ProductStateEnum.CHECK,product);//返回成功的状态
	}
	
	/**
	 * 具体处理详情图的方法
	 * @param product
	 * @param productImgList
	 */
	private void addProductImagList(Product product, List<ImageHolder> productImgList) {

		try {
			long productId = product.getProductId();
			String filePath = product.getImgAddr();//文件路径
			List<ProductImg> imgList = new ArrayList<ProductImg>();
			//遍历处理
			for (int index = 0; index < productImgList.size(); index++) {
				ProductImg productImg = new ProductImg();
				productImg.setCreateTime(new Date());
				productImg.setProductId(productId);
				productImg.setPriority(1);
				productImg.setImgDesc("商品详情图");
				String productImgAddr = ImageUtil.generateThumbnail(productImgList.get(index).getInputStream(),
						productImgList.get(index).getFileName(),filePath);//保存每张图片，并返回每张详情图对应的地址
				productImg.setImgAddr(productImgAddr);
				imgList.add(productImg);
			}
			if(imgList != null && imgList.size() > 0) {
				productImgDao.batchInsertProductImg(imgList);//批量添加数据至数据库
			}
		}catch(Exception e) {
			throw new ProductOperationException("添加详情图片失败！");
		}
		
	}

	private void addProductImag(Product product, ImageHolder thumbnail) {
		//获取shop图片的相对值路径
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String productImgAddr = ImageUtil.generateThumbnail(thumbnail.getInputStream(),thumbnail.getFileName(),dest);
		product.setImgAddr(productImgAddr);
	}

}
