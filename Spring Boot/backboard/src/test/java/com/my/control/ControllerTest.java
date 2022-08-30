package com.my.control;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//@WebMvcTest(DemoController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ControllerTest {
	
	// MockMvc : 웹 애플리케이션을 서버에 배포하지 않고도 스프링 MVC의 동작을 재현할 수 있는 클래스
	@Autowired
	private MockMvc mockMvc; // 모의 객체 : "흉내"내는 "가짜" 모듈
	
	
	@Test
	void testList() throws Exception {
		
		MockHttpServletRequestBuilder  mockRequestBuilder = 
				MockMvcRequestBuilders.get("/board/list/2").accept(org.springframework.http.MediaType.APPLICATION_JSON);
		
//		MockHttpServletRequestBuilder  mockRequestBuilder = 
//				MockMvcRequestBuilders.get("/board/list").accept(org.springframework.http.MediaType.APPLICATION_JSON);
		
//		MockHttpServletRequestBuilder  mockRequestBuilder = 
//				MockMvcRequestBuilders.get("/board/list/2").accept(org.springframework.http.MediaType.APPLICATION_JSON);
//		
		//status가 1이라고 예상한다
		int expectedStatus = 1;
		
		//모의객체를 이용해서 요청/응답한다. 응답결과: ResultActions
		ResultActions resultActions = mockMvc.perform(mockRequestBuilder);
		
		//응답결과가 응답상태코드 200임을 예상한다
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());

		//응답결과는 {"status": 1}이다
		//jsonPath를 이용해서 status 프포퍼티값이 1과 같은가 예상한다
		org.hamcrest.Matcher<Integer> matcher;
		ResultMatcher resultMatcher;
		matcher = org.hamcrest.CoreMatchers.is(expectedStatus);
		resultMatcher = jsonPath("status", matcher);
		resultActions.andExpect(resultMatcher);
	}
	
	@Test
	void testSearch() throws Exception {
		
		
		MockHttpServletRequestBuilder  mockRequestBuilder = 
				MockMvcRequestBuilders.get("/board/search/답").accept(org.springframework.http.MediaType.APPLICATION_JSON);
		
		int expectedStatus = 1;
		
		//모의객체를 이용해서 요청/응답한다. 응답결과: ResultActions
		ResultActions resultActions = mockMvc.perform(mockRequestBuilder);
		
		//응답결과가 응답상태코드 200임을 예상한다
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());

		//응답결과는 {"status": 1}이다
		//jsonPath를 이용해서 status 프포퍼티값이 1과 같은가 예상한다
		org.hamcrest.Matcher<Integer> matcher;
		ResultMatcher resultMatcher;
		matcher = org.hamcrest.CoreMatchers.is(expectedStatus);
		resultMatcher = jsonPath("status", matcher);
		resultActions.andExpect(resultMatcher);
	}
	
	@Test
	void testView() throws Exception {
		
		MockHttpServletRequestBuilder  mockRequestBuilder = 
				MockMvcRequestBuilders.get("/board/view/3").accept(org.springframework.http.MediaType.APPLICATION_JSON);
		
		int expectedStatus = 1;
		
		//모의객체를 이용해서 요청/응답한다. 응답결과: ResultActions
		ResultActions resultActions = mockMvc.perform(mockRequestBuilder);
		
		//응답결과가 응답상태코드 200임을 예상한다
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());

		//응답결과는 {"status": 1}이다
		//jsonPath를 이용해서 status 프포퍼티값이 1과 같은가 예상한다
		org.hamcrest.Matcher<Integer> matcher;
		ResultMatcher resultMatcher;
		matcher = org.hamcrest.CoreMatchers.is(expectedStatus);
		resultMatcher = jsonPath("status", matcher);
		resultActions.andExpect(resultMatcher);
	}
	
	
	@Test
	void testWrite() throws Exception {
	//searchBoard 테스트
		MockHttpServletRequestBuilder  mockRequestBuilder = 
				MockMvcRequestBuilders.post("/board/write").accept(org.springframework.http.MediaType.APPLICATION_JSON);
		
		//모의객체를 이용해서 요청/응답한다. 응답결과: ResultActions
		ResultActions resultActions = mockMvc.perform(mockRequestBuilder);
		
		
		//응답결과가 응답상태코드 500임을 예상한다
		//resultActions.andExpect(MockMvcResultMatchers.status().isInternalServerError());
		
		//응답결과가 응답상태코드 200임을 예상한다
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
				

		//write는 ResponseEntity로 리턴
		//jsonPath를 이용해서 비교할 프로퍼티값이 없으므로 추가적인 확인 작업 필요X

	}
}
