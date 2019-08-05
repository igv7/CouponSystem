package com.igor.dao;

import com.igor.enums.ClientType;

public interface ClientDAO {
	
	ClientDAO login(String name, String password, ClientType type) throws Exception;

}
