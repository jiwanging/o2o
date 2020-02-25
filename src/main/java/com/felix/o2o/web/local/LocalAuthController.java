package com.felix.o2o.web.local;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.felix.o2o.dto.LocalAuthExecution;
import com.felix.o2o.entity.LocalAuth;
import com.felix.o2o.entity.PersonInfo;
import com.felix.o2o.enums.LocalAuthStateEnum;
import com.felix.o2o.service.LocalAuthService;
import com.felix.o2o.util.CodeUtil;
import com.felix.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping(value="local",method={RequestMethod.GET,RequestMethod.POST})
public class LocalAuthController {
	@Autowired
	private LocalAuthService localAuthService;
	/**
	 * ���û���Ϣ��ƽ̨�˺Ű�
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/bindlocalauth",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> bindLocalAuth(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		//��֤��У��
		if(!CodeUtil.checkVerifyCode(request)){
			modelMap.put("success", false);
			modelMap.put("errmsg", "�����˴������֤��");
			return modelMap;
		}
		//��ȡ������˺�
		String userName=HttpServletRequestUtil.getString(request, "userName");
		//��ȡ���������
		String password=HttpServletRequestUtil.getString(request, "password");
		//��session�л�ȡ��ǰ�û���Ϣ���û�һ��ͨ��΢�ŵ�¼֮�󣬱��ܻ�ȡ���û�����Ϣ
		PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		//�ǿ��жϣ�Ҫ���˺������Լ���ǰ���û�session�ǿ�
		if (userName!=null&&password!=null&&user!=null&&user.getUserId()!=null){
			//����LocalAuth���󲢸�ֵ
			LocalAuth localAuth=new LocalAuth();
			localAuth.setUsername(userName);
			localAuth.setPassword(password);
			localAuth.setPersonInfo(user);
			//���˺�
			LocalAuthExecution le=localAuthService.bindLocalAuth(localAuth);
			if(le.getState()==LocalAuthStateEnum.SUCCESS.getState()){
				modelMap.put("success", true);
				
			}else{
				modelMap.put("success", false);
				modelMap.put("errmsg", le.getStateInfo());
			}
			
		}else{
			modelMap.put("success", false);
			modelMap.put("errmsg", "�û��������붼����Ϊ��");
		}
		return modelMap;
	}
	/**
	 * �޸�����
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/changelocalpwd",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> changeLocalPwd(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		//��֤��У��
		if(!CodeUtil.checkVerifyCode(request)){
			modelMap.put("success", false);
			modelMap.put("errmsg", "�����˴������֤��");
			return modelMap;
		}
		//��ȡ�˺�
		String userName=HttpServletRequestUtil.getString(request, "userName");
		//��ȡ����
		String password=HttpServletRequestUtil.getString(request, "password");
		//��ȡ������
		String newPassword=HttpServletRequestUtil.getString(request, "newPassword");
		//��session�л�ȡ��ǰ�û���Ϣ���û�һ��ͨ��΢�ŵ�¼֮�󣬱��ܻ�ȡ���û�����Ϣ
		PersonInfo user=(PersonInfo)request.getSession().getAttribute("user");
		//�ǿ��жϣ�Ҫ���˺��¾������Լ���ǰ���û�session�ǿգ����¾����벻��ͬ
		if(userName!=null&&password!=null&&newPassword!=null&&user!=null&&user.getUserId()!=null
				&&!password.equals(newPassword)){
			try {
				//�鿴ԭ���˺ţ�������������˺��Ƿ�һ�£���һ������Ϊ�ǷǷ�����
				LocalAuth localAuth=localAuthService.getLocalAuthByUserId(user.getUserId());
				if(localAuth==null||!localAuth.getUsername().equals(userName)){
					//��һ����ֱ���˳�
					modelMap.put("success", false);
					modelMap.put("errmsg", "������˺ŷǱ��ε�¼���˺�");
					return modelMap;
				}
				//�޸�ƽ̨�˺ŵ��û�����
				LocalAuthExecution le=localAuthService.modifyLocalAuth(user.getUserId(), 
						userName, password, newPassword);
				if(le.getState()==LocalAuthStateEnum.SUCCESS.getState()){
					modelMap.put("success", true);
					
				}else{
					modelMap.put("success", false);
					modelMap.put("errmsg", le.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errmsg", e.toString());
				return modelMap;
			}
			
		}else{
			modelMap.put("success", false);
			modelMap.put("errmsg", "����������");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/logincheck",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> logincheck(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		//��ȡ�Ƿ���Ҫ������֤��У��ı�ʶ��
		boolean needVerify=HttpServletRequestUtil.getBoolean(request, "needVerify");
		if(needVerify&&!CodeUtil.checkVerifyCode(request)){
			modelMap.put("success", false);
			modelMap.put("errmsg", "�����˴������֤��");
			return modelMap;
		}
		//��ȡ������˺�
		String userName=HttpServletRequestUtil.getString(request, "userName");
		//��ȡ���������
		String password=HttpServletRequestUtil.getString(request, "password");
		//�ǿ�У��
		if(userName!=null &&password!=null){
			//�����˺ź�����ȥ��ȡƽ̨�˺���Ϣ
			LocalAuth localAuth=localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
			if(localAuth!=null){
				//����ȡ���˺���Ϣ���¼�ɹ�
				modelMap.put("success", true);
				//ͬʱ��session�������û���Ϣ
				request.getSession().setAttribute("user", localAuth.getPersonInfo());
			}else{
				modelMap.put("success", false);
				modelMap.put("errmsg", "�û������������");
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errmsg", "�û��������������Ϊ��");
		}
		return modelMap;
	}
	/**
	 * ���û�����ǳ���ťʱע��session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="logout",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> logout(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<String,Object>();
		//���û�session��Ϊ��
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}
}

