package com.ssafy.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.ws.model.repo.BookRepo;
import com.ssafy.ws.model.repo.UserRepo;
import com.ssafy.ws.model.service.BookService;
import com.ssafy.ws.model.service.UserService;

public class WS_01_BeanCreationTest extends AbstractTest {

	private static Logger logger = LoggerFactory.getLogger(WS_01_BeanCreationTest.class);

	@Autowired
	BookRepo brepo;
	@Autowired
	BookService bserv;
	@Autowired
	UserRepo urepo;
	@Autowired
	UserService userv;
	@Autowired
	DataSource ds;

	@Test
	public void testBeanCreation() {
		assertNotNull(brepo);
		assertNotNull(bserv);
		assertNotNull(urepo);
		assertNotNull(userv);
	}

	@Test
	public void testSingleton() {
		try {
			assertEquals(brepo, bserv.getBookRepo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDataSource() {
		logger.debug("datasource 확인: {}", ds);
		assertNotNull(ds);
	}

}
