package pulu.com.common;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractDAO {

	protected Log log = LogFactory.getLog(AbstractDAO.class);
	
	@Autowired // xml에 선언했던 의존관계 자동 주입
	private SqlSessionTemplate sqlSession;
	
	// console 로그 편의성을 위해 각각의 메서드 재정의
	protected void printQueryId(String queryId) {
		if(log.isDebugEnabled()) {
			log.debug("\t QueryId \t: " + queryId);
		}
	}
	
	// 글 + 상품 등록
	public Object insert(String queryId, Object params)  {
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}

	// 글 + 상품 수정
	public Object update(String queryId, Object params)  {
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}
		
	// 글 + 상품 삭제
	public Object delete(String queryId, Object params)  {
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}
	
	// 글 + 상품 목록
	public Object selectOne(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}
	
	// 글 + 상품 상세보기
	public Object selectOne(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}
	
	// 여러 글 + 상품 목록
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}
	
	@SuppressWarnings("rawtypes")
	public List selectList(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId, params);
	}
	
	// 검색
	
	// 검색
	
	// 검색
}
