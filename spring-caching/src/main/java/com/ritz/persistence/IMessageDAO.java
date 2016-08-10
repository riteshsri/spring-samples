package com.ritz.persistence;

import java.util.List;

public interface IMessageDAO {
	
	void saveMessageLog(String message);

	List<String> getAllMessageTypes();

}
