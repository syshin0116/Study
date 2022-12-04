package pulu.com.test;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;
import org.junit.runner.RunWith;
// 선민: 1. Logger 라이브러리 import 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/config/action-servlet.xml") // 이것도 경로 맞는지
public class JdbcTests {

	// 선민: 2. 객체 생성
	protected static final Logger log = LoggerFactory.getLogger(JdbcTests.class); // 현재클래스(TestClass)

	@Test
	public void testConnection() throws Exception {
		Connection con = null;

		try {
			// 선민: 3. info() 메서드 호출
			Class clazz = Class.forName("oracle.jdbc.driver.OracleDriver");
			log.info("클래스 로딩 확인: " + clazz);
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "pulu", "pulu");
			log.info("커넥션 객체 생성 확인: " + con + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

}