package com.zheng.upms.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zheng.upms.dao.model.UpmsSystemExample;
import com.zheng.upms.rpc.api.UpmsSystemService;

/**
 * 单元测试 Created by shuzheng on 2017/2/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:applicationContext-dubbo-consumer.xml" })
public class UpmsServiceTest {

	@Autowired
	private UpmsSystemService upmsSystemService;

	@Autowired
	DataSourceTransactionManager txManager;

	@Test
	public void index() {
		DefaultTransactionDefinition transDef = new DefaultTransactionDefinition();
		transDef.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(transDef);
		try {
			int count = upmsSystemService.countByExample(new UpmsSystemExample());
			System.out.println(count);
			txManager.commit(status);
		} catch (Exception e) {
			txManager.rollback(status);
			e.printStackTrace();
		}
	}

}
