package com.asc.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * <h3>此测试基类会自动代理事务，并且不论执行成功与否，都会回滚事务，这为我们测试带来了便利</h3>
 * 说起事务，自然要一个事务管理器，这里默认会去Spring上下文中找ID为transactionManager的bean，
 * 如果没有找到的话，将抛出异常，除非你的事务管理器的ID就是transactionManager，否则需要在你的测试类（非基类）
 * 中加上如下配置，当然如果是JDBC事务的话，也可能是transactionManager=jdbcTransactionManager
 * <code>@TransactionConfiguration(transactionManager="hibernateTransactionManager")</code>
 * @author wubo
 * @CreateDate 2010-8-3
 * @version 1.0.01
 */
@ContextConfiguration(locations={"classpath*:applicationContext-*.xml", "classpath*:applicationContext-dao.xml", "classpath*:applicationContext-service.xml"})
public abstract class TransactionalTestBase extends AbstractTransactionalJUnit4SpringContextTests {

}
