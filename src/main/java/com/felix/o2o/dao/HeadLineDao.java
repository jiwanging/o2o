package com.felix.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.felix.o2o.entity.HeadLine;

public interface HeadLineDao {

	/**
	 * 根据店铺信息删除店铺所包含的商品分类信息
	 * @param shopId 店铺Id
	 * @return
	 */
	List<HeadLine> queryHeadLine(@Param("headLineCondition")HeadLine headLineCondition);
}
