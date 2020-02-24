package com.felix.o2o.util.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felix.o2o.dto.UserAccessToken;
import com.felix.o2o.dto.WechatUser;

/**
 * ΢�Ź�����
 * 
 * @author xiangze
 *
 */
public class WechatUtil {

    private static Logger log = LoggerFactory.getLogger(WechatUtil.class);

    /**
     * ��ȡUserAccessTokenʵ����
     * 
     * @param code
     * @return
     * @throws IOException
     */
    public static UserAccessToken getUserAccessToken(String code) throws IOException {
        // ���Ժ���Ϣ���appId
        String appId = "wxe538aac41dd024dd";
        log.debug("appId:" + appId);
        // ���Ժ���Ϣ���appsecret
        String appsecret = "3258b0863de0d8371d874301dadf454f";
        log.debug("secret:" + appsecret);
        // ���ݴ����code,ƴ�ӳ�����΢�Ŷ���õĽӿڵ�URL
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appsecret
                + "&code=" + code + "&grant_type=authorization_code";
        // ����ӦURL���������ȡtoken json�ַ���
        String tokenStr = httpsRequest(url, "GET", null);
        log.debug("userAccessToken:" + tokenStr);
        UserAccessToken token = new UserAccessToken();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // ��json�ַ���ת������Ӧ����
            token = objectMapper.readValue(tokenStr, UserAccessToken.class);
        } catch (JsonParseException e) {
            log.error("��ȡ�û�accessTokenʧ��: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonMappingException e) {
            log.error("��ȡ�û�accessTokenʧ��: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("��ȡ�û�accessTokenʧ��: " + e.getMessage());
            e.printStackTrace();
        }
        if (token == null) {
            log.error("��ȡ�û�accessTokenʧ�ܡ�");
            return null;
        }
        return token;
    }

    /**
     * ��ȡWechatUserʵ����
     * 
     * @param accessToken
     * @param openId
     * @return
     */
    public static WechatUser getUserInfo(String accessToken, String openId) {
        // ���ݴ����accessToken�Լ�openIdƴ�ӳ�����΢�Ŷ���Ķ˿ڲ���ȡ�û���Ϣ��URL
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId
                + "&lang=zh_CN";
        // ���ʸ�URL��ȡ�û���Ϣjson �ַ���
        String userStr = httpsRequest(url, "GET", null);
        log.debug("user info :" + userStr);
        WechatUser user = new WechatUser();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // ��json�ַ���ת������Ӧ����
            user = objectMapper.readValue(userStr, WechatUser.class);
        } catch (JsonParseException e) {
            log.error("��ȡ�û���Ϣʧ��: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonMappingException e) {
            log.error("��ȡ�û���Ϣʧ��: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("��ȡ�û���Ϣʧ��: " + e.getMessage());
            e.printStackTrace();
        }
        if (user == null) {
            log.error("��ȡ�û���Ϣʧ�ܡ�");
            return null;
        }
        return user;
    }

    /**
     * ����https���󲢻�ȡ���
     * 
     * @param requestUrl
     *            �����ַ
     * @param requestMethod
     *            ����ʽ��GET��POST��
     * @param outputStr
     *            �ύ������
     * @return json�ַ���
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            // ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // ������SSLContext�����еõ�SSLSocketFactory����
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // ��������ʽ��GET/POST��
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // ����������Ҫ�ύʱ
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // ע������ʽ����ֹ��������
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // �����ص�������ת�����ַ���
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // �ͷ���Դ
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            log.debug("https buffer:" + buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return buffer.toString();
    }
}