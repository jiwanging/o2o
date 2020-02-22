package com.felix.o2o.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.felix.o2o.entity.HeadLine;

public interface HeadLineService {
	
	/**
	 * 
	 * @param headLineCondition
	 * @return
	 */
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) 
			throws IOException;

}
