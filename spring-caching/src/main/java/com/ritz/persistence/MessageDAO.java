package com.ritz.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MessageDAO extends JdbcDaoSupport implements IMessageDAO {

	private static final String messageMetaSQL = "INSERT INTO MESSAGE_METADATA (TIMER, CREATED) VALUES (?, ?)";
	private static final String messageLogSQL = "INSERT INTO MESSAGE_AUDIT (CONTENT, CREATED) VALUES (?, ?)";

	@Override
	public void saveMessageLog(String message) {
		getJdbcTemplate().update(messageLogSQL, new Object[] { message, new Date() });
		String messageTimer = message.split(" ")[3];
		Long timer = Long.valueOf(messageTimer);
		
		if((timer % 2) != 0){
			System.out.println("Opps its an ODD Message - " + messageTimer);
			throw new RuntimeException("Dont Process ODD Messages...");
		}else{
			getJdbcTemplate().update(messageMetaSQL, new Object[] { messageTimer, new Date() });
		}
		
	}
	
	@Override
	@Cacheable(cacheNames={"results", "evicatbleResults"})
	@CacheEvict(cacheNames="evicatbleResults", condition="T(java.lang.System).currentTimeMillis() % 2 == 0")
	public List<String> getAllMessageTypes(){
		ArrayList<String> results = new ArrayList<String>();
		
		results.add("EvenMessage");
		simulateProcessingDelay();
		results.add("OddMessage");
		simulateProcessingDelay();
		results.add("PrimeMessage");
		simulateProcessingDelay();
		results.add("BadMessage");
		
		return results;
	}
	
	
	private static void simulateProcessingDelay(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
