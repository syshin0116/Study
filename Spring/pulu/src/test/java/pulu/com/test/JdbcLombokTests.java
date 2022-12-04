package pulu.com.test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/config/action-servlet.xml") // 이것도 경로 맞는지
@Log4j
public class JdbcLombokTests {


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