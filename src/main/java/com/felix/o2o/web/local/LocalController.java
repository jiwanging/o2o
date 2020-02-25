package com.felix.o2o.web.local;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="local",method={RequestMethod.GET})
public class LocalController {
	
	/**
	 * 进入登陆界面
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	@ResponseBody
	private String login(){
		return "/local/login";
	}
	
	/**
	 * 进入修改密码界面
	 * @return
	 */
	@RequestMapping(value="/changepsw",method=RequestMethod.GET)
	@ResponseBody
	private String changepsw(){
		return "/local/changepsw";
	}
	
	/**
	 * 进入绑定账号界面
	 * @return
	 */
	@RequestMapping(value="/accountbind",method=RequestMethod.GET)
	@ResponseBody
	private String accountbind(){
		return "/local/accountbind";
	}
	

}
