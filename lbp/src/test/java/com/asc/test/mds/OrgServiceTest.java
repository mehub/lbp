package com.asc.test.mds;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.asc.mds.root.bean.FactoryOrg;
import com.asc.mds.root.bean.Region;
import com.asc.mds.root.xservice.IFactoryOrgXService;
import com.asc.mds.root.xservice.IRegionXService;
import com.asc.mds.service.IOrgService;
import com.asc.test.TestBase;

public class OrgServiceTest extends TestBase {
	@Autowired
	IRegionXService service;
	
	@Autowired
	IOrgService orgService;
	
	@Autowired
	IFactoryOrgXService factoryXService;
	
	
	@Test
	public void orgTest(){
		Region area= service.getByName("上海市");
		System.out.println(area.getCode());
		
		System.out.println(orgService.getOrg("000162899110"));
		FactoryOrg fc = factoryXService.getRelation("000001395171", "100000025893");
		System.out.println(fc);
	}  
	
}
