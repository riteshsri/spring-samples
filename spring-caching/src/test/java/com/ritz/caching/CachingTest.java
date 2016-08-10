package com.ritz.caching;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ritz.persistence.IMessageDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-context.xml")
public class CachingTest {

	@Autowired
	IMessageDAO messageDAO;
	
	@Test
	public void testProduction() {
		long starTime = System.currentTimeMillis();
		messageDAO.getAllMessageTypes();
		long runningTime = System.currentTimeMillis() - starTime;
		System.out.println("First Running Time - " + runningTime);
		
		starTime = System.currentTimeMillis();
		messageDAO.getAllMessageTypes();
		runningTime = System.currentTimeMillis() - starTime;
		System.out.println("Second Running Time - " + runningTime);
	}
	

}
