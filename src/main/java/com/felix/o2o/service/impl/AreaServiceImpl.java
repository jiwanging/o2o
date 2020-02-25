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
import com.felix.o2o.dao.AreaDao;
import com.felix.o2o.entity.Area;
import com.felix.o2o.exceptions.AreaOperationException;
import com.felix.o2o.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    
    static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Override
    @Transactional
    public List<Area> getAreaList() {
        // ����redis��key
        String key = AREALISTKEY;
        // ������ն���
        List<Area> areaList = null;
        // ����jackson����ת��������
        ObjectMapper mapper = new ObjectMapper();
        // �ж�key�Ƿ����
        if (!jedisKeys.exists(key)) {
            // �������ڣ�������ݿ�����ȡ����Ӧ����
            areaList = areaDao.queryArea();
            // ����ص�ʵ���༯��ת����string,����redis�����Ӧ��key��
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(areaList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        } else {
            // �����ڣ���ֱ�Ӵ�redis����ȡ����Ӧ����
            String jsonString = jedisStrings.get(key);
            // ָ��Ҫ��stringת���ɵļ�������
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
            try {
                // �����key��Ӧ��value��ĵ�stringת���ɶ����ʵ���༯��
                areaList = mapper.readValue(jsonString, javaType);
            } catch (JsonParseException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            } catch (JsonMappingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }
        }
        return areaList;
    }

}
