package com.felix.o2o.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * �û���Ȩtoken
 * 
 * @author xiangze
 *
 */
public class UserAccessToken {

    // ��ȡ����ƾ֤
    @JsonProperty("access_token")
    private String accessToken;
    // ƾ֤��Чʱ�䣬��λ����
    @JsonProperty("expires_in")
    private String expiresIn;
    // ��ʾ�������ƣ�������ȡ��һ�εķ������ƣ�����û̫���ô�
    @JsonProperty("refresh_token")
    private String refreshToken;
    // ���û��ڴ˹��ں��µ���ݱ�ʶ�����ڴ�΢�źž���Ψһ��
    @JsonProperty("openid")
    private String openId;
    // ��ʾȨ�޷�Χ�������ʡ��
    @JsonProperty("scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "accessToken:" + this.getAccessToken() + ",openId:" + this.getOpenId();
    }

}
