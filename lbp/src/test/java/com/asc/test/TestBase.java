package com.asc.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/** 普通JUnit测试基类，无事务
 * @author wubo
 * @CreateDate 2010-8-3
 * @version 1.0.01
 */
@ContextConfiguration(locations={"classpath*:applicationContext-*.xml"})
public abstract class TestBase  extends AbstractJUnit4SpringContextTests {

}
