package com.felix.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felix.o2o.cache.JedisUtil;
import com.felix.o2o.dao.HeadLineDao;
import com.felix.o2o.entity.HeadLine;
import com.felix.o2o.exceptions.HeadLineOperationException;
import com.felix.o2o.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService {

	/*ԭ��ʵ��
	@Autowired
	private HeadLineDao headLineDao;
	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
		return headLineDao.queryHeadLine(headLineCondition);
	}*/

    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    
    private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);

    @Override
    @Transactional
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
        // ����redis��keyǰ׺
        String key = HLLISTKEY;
        // ������ն���
        List<HeadLine> headLineList = null;
        // ����jackson����ת��������
        ObjectMapper mapper = new ObjectMapper();
        // ƴ�ӳ�redis��key
        if (headLineCondition != null && headLineCondition.getEnableStatus() != null) {
            key = key + "_" + headLineCondition.getEnableStatus();
        }
        // �ж�key�Ƿ����
        if (!jedisKeys.exists(key)) {
            // �������ڣ�������ݿ�����ȡ����Ӧ����
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            // ����ص�ʵ���༯��ת����string,����redis�����Ӧ��key��
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(headLineList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        } else {
            // �����ڣ���ֱ�Ӵ�redis����ȡ����Ӧ����
            String jsonString = jedisStrings.get(key);
            // ָ��Ҫ��stringת���ɵļ�������
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
            try {
                // �����key��Ӧ��value��ĵ�stringת���ɶ����ʵ���༯��
                headLineList = mapper.readValue(jsonString, javaType);
            } catch (JsonParseException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            } catch (JsonMappingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
        }
        return headLineList;
    }

}
