package com.felix.o2o.service;

public interface CacheService {
    /**
     * ����keyǰ׺ɾ��ƥ���ģʽ�µ�����key-value �紫��:shopcategory,��shopcategory_allfirstlevel��
     * ��shopcategory��ͷ��key_value���ᱻ���
     * 
     * @param keyPrefix
     * @return
     */
    void removeFromCache(String keyPrefix);
}
