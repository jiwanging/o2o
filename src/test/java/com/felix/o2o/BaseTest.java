package com.felix.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ��������spring��junit���ϣ�junit����ʱ����springIOC����
 * @author Jiwanging
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//����junit spring�����ļ���λ�� ע�������·��
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml","classpath:spring/spring-redis.xml"})
public class BaseTest {

	
}

//��������source�ύ
