package com.igor.CouponSystemSpringProj.rest;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igor.CouponSystemSpringProj.CouponSystem;
import com.igor.CouponSystemSpringProj.enums.ClientType;
import com.igor.CouponSystemSpringProj.rest.ClientSession;
import com.igor.CouponSystemSpringProj.service.Facade;

@RestController
//@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private Map<String, ClientSession> tokensMap;
	
	@Autowired
	private CouponSystem couponSystem;
	
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String password, @RequestParam String type) {
		if (!type.equals("ADMIN") && !type.equals("COMPANY") && !type.equals("CUSTOMER")) {
			return new ResponseEntity<>("Wrong type", HttpStatus.UNAUTHORIZED);
		}
		ClientSession clientSession = new ClientSession();
		Facade facade = null;
		String token = UUID.randomUUID().toString();
		long LastAccsessed = System.currentTimeMillis();
		try {
			facade = couponSystem.login(userName, password, ClientType.valueOf(type));
			clientSession.setFacade(facade);
			clientSession.setLastAccessed(LastAccsessed);
			tokensMap.put(token, clientSession);
			return new ResponseEntity<>(token, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

}
