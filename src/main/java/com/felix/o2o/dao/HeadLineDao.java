package com.felix.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.felix.o2o.entity.HeadLine;

public interface HeadLineDao {

	/**
	 * ���ݵ�����Ϣɾ����������������Ʒ������Ϣ
	 * @param shopId ����Id
	 * @return
	 */
	List<HeadLine> queryHeadLine(@Param("headLineCondition")HeadLine headLineCondition);
}
