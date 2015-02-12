package com.db.jsf;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserBeanTest {

	UserBean ub = new UserBean();
	
	@Before
	public void setUp() throws Exception {
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testVerifyUser() {
		
			assertNotNull(ub.verifyUser() == null);
		
		
	}

}
