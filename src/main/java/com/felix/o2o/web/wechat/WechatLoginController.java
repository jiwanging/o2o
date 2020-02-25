package com.felix.o2o.web.wechat;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.felix.o2o.dto.UserAccessToken;
import com.felix.o2o.dto.WechatAuthExecution;
import com.felix.o2o.dto.WechatUser;
import com.felix.o2o.entity.PersonInfo;
import com.felix.o2o.entity.WechatAuth;
import com.felix.o2o.enums.WechatAuthStateEnum;
import com.felix.o2o.service.PersonInfoService;
import com.felix.o2o.service.WechatAuthService;
import com.felix.o2o.util.wechat.WechatUtil;

@Controller
@RequestMapping("wechatlogin")
/**
 * ��ȡ��ע���ں�֮���΢���û���Ϣ�Ľӿڣ������΢������������
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=����appId&redirect_uri=http://o2o.yitiaojieinfo.com/o2o/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * �����ｫ���ȡ��code,֮���ٿ���ͨ��code��ȡ��access_token ������ȡ���û���Ϣ
 * 
 * @author xiangze
 *
 */
public class WechatLoginController {

    private static Logger log = LoggerFactory.getLogger(WechatLoginController.class);

    private static final String FRONTEND = "1";
    
    private static final String ShOPEND = "2";
    
    @Autowired
    private WechatAuthService wechatAuthService;
    
    @Autowired
    private PersonInfoService personInfoService;
    
    
    @RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("weixin login get...");
        // ��ȡ΢�Ź��ںŴ��������code,ͨ��code�ɻ�ȡaccess_token,������ȡ�û���Ϣ
        String code = request.getParameter("code");
        // ���state���������������Զ������Ϣ�����������ã�����Ҳ���Բ���
        String roleType = request.getParameter("state");
        log.debug("weixin login code:" + code);
        WechatUser user = null;
        String openId = null;
        WechatAuth auth = null;
        if (null != code) {
            UserAccessToken token;
            try {
                // ͨ��code��ȡaccess_token
                token = WechatUtil.getUserAccessToken(code);
                log.debug("weixin login token:" + token.toString());
                // ͨ��token��ȡaccessToken
                String accessToken = token.getAccessToken();
                // ͨ��token��ȡopenId
                openId = token.getOpenId();
                // ͨ��access_token��openId��ȡ�û��ǳƵ���Ϣ
                user = WechatUtil.getUserInfo(accessToken, openId);
                log.debug("weixin login user:" + user.toString());
                request.getSession().setAttribute("openId", openId);
                auth = wechatAuthService.getWechatAuthByOpenId(openId);
            } catch (IOException e) {
                log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
                e.printStackTrace();
            }
        }
        // ======todo begin======
        // ǰ�����ǻ�ȡ��openId�󣬿���ͨ����ȥ���ݿ��жϸ�΢���ʺ��Ƿ���������վ���ж�Ӧ���ʺ��ˣ�
        // û�еĻ���������Զ������ϣ�ֱ��ʵ��΢����������վ���޷�Խӡ�
        // ======todo end======
        if (auth == null) {
			PersonInfo personInfo = WechatUtil.getPersonInfoFromRequest(user);
			auth = new WechatAuth();
			auth.setOpenId(openId);
			if (FRONTEND.equals(roleType)) {
				personInfo.setUserType(1);
			} else {
				personInfo.setUserType(2);
			}
			auth.setPersonInfo(personInfo);
			WechatAuthExecution we = wechatAuthService.register(auth);
			if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
				return null;
			} else {
				personInfo = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
				request.getSession().setAttribute("user", personInfo);
			}
		} else {
			request.getSession().setAttribute("user", auth.getPersonInfo());
		}
        
        if (FRONTEND.equals(roleType)) {
        	return "frontend/index";
		} else {
			return "shopadmin/shoplist";
		}
    }
}
