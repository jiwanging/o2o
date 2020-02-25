package com.felix.o2o.util;


import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    // ��Ҫ���ܵ��ֶ�����
    private String[] encryptPropNames = { "jdbc.username", "jdbc.password" };

    /**
     * �Թؼ������Խ���ת��
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)) {
            // ���Ѽ��ܵ��ֶν��н��ܹ���
            String decryptValue = DESUtil.getDecryptString(propertyValue);
            return decryptValue;
        } else {
            return propertyValue;
        }
    }

    /**
     * �������Ƿ��Ѽ���
     * 
     * @param propertyName
     * @return
     */
    private boolean isEncryptProp(String propertyName) {
        // ��������Ҫ���ܵ�field������м���
        for (String encryptpropertyName : encryptPropNames) {
            if (encryptpropertyName.equals(propertyName))
                return true;
        }
        return false;
    }
}
