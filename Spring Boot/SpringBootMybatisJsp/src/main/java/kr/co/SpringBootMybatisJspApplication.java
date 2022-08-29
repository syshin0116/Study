package kr.co;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@SpringBootApplication
public class SpringBootMybatisJspApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMybatisJspApplication.class, args);
	}
	
	 /*
	  * SqlSessionFactory 설정  
	  * 메인 SpringBootMybatisJspApplication.java에다가 Bean을 추가해도 되지만 
	  * 따로 MyBatisConfigApplication.java파일을 만들어서 넣어도 된다.
     */
   @Bean
   public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{

	    final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	    sessionFactory.setDataSource(dataSource);
	    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	    /*마이바티스 XML 파일 읽기*/
	    sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
	    /*마이바티스 설정파일 읽기*/
	    Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
	    sessionFactory.setConfigLocation(myBatisConfig);
	 
	    return sessionFactory.getObject();
   }
}
