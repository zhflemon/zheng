package com.zheng.upms.rpc.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zheng.upms.dao.model.UpmsPermission;
import com.zheng.upms.dao.model.UpmsPermissionExample;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.rpc.api.UpmsPermissionService;
import com.zheng.upms.rpc.api.UpmsUserService;

/**
 * 单元测试 Created by shuzheng on 2017/2/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:META-INF/spring/applicationContext-jdbc.xml",
		"classpath:META-INF/spring/applicationContext-listener.xml" })
public class UpmsServiceTest {

	@Autowired
	private UpmsUserService upmsUserService;

	@Autowired
	private UpmsPermissionService upmsPermissionService;

	@Autowired
	DataSourceTransactionManager txManager;

	@Test
	public void index() {
		UpmsUser upmsUser = new UpmsUser();
		upmsUser.setAvatar("");
		upmsUser.setCtime(System.currentTimeMillis());
		upmsUser.setEmail("");
		upmsUser.setLocked((byte) 0);
		upmsUser.setPassword("xxx");
		upmsUser.setPhone("");
		upmsUser.setRealname("zsz");
		upmsUser.setSex((byte) 1);
		upmsUser.setSalt("");
		upmsUser.setUsername("zsz");
		upmsUserService.insertSelective(upmsUser);
		System.out.println(upmsUser.getUserId());
	}

	@Test
	public void selectForPage() {
		DefaultTransactionDefinition transDef = new DefaultTransactionDefinition();
		transDef.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(transDef);
		try {
			// 根据条件，按页码+每页条数分页
			UpmsPermissionExample upmsPermissionExample = new UpmsPermissionExample();
			upmsPermissionExample.createCriteria().andSystemIdEqualTo(1);
			List<UpmsPermission> upmsPermissions = upmsPermissionService
					.selectByExampleForStartPage(upmsPermissionExample, 2, 20);
			System.out.println(upmsPermissions.size());
			// 根据条件，按offset+limit分页
			upmsPermissionExample = new UpmsPermissionExample();
			upmsPermissionExample.createCriteria().andSystemIdEqualTo(2);
			upmsPermissions = upmsPermissionService.selectByExampleForOffsetPage(upmsPermissionExample, 3, 5);
			System.out.println(upmsPermissions.size());
			txManager.commit(status);
		} catch (Exception e) {
			txManager.rollback(status);
			e.printStackTrace();
		}
	}

}
