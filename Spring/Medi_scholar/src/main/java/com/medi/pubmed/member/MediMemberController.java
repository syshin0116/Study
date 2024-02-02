package com.medi.pubmed.member;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/member/*")
@Controller
public class MediMemberController {

	private static final Logger logger = LoggerFactory.getLogger(MediMemberController.class);

	@Autowired
	private MediMemberService medimembersvc;
	
	@GetMapping("login")
	public String login(HttpServletRequest req, ModelMap modelMap) {
		return "member/login";
	}

	
	@PostMapping("login")
	public String loginChk(HttpServletRequest req, ModelMap modelMap, @RequestParam HashMap<String, Object> param) {

		// userinfo 받아와서 hashmap에 저장하기
		HashMap<String, Object> user = medimembersvc.getUserInfo(param);

		logger.info("param: " + param);
		logger.info("user info: " + String.valueOf(user));
		if(user != null) {
			HttpSession session = req.getSession();
			// 세션값 저장
			session.setAttribute("userId", user.get("userId"));
			session.setAttribute("userNm", user.get("userNm"));
			// 세션 잘 받았나 로그확인
			logger.info("session Id : " + user.get("userId"));
			logger.info("session Name : " + user.get("userNm"));

		}
		return "redirect:/community/list";

	}

	// 로그아웃
	@GetMapping("logout")
	public String Logout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return ("redirect:/member/login");
	}
	
	
	
	@GetMapping("signup")
	public String signUp(HttpServletRequest req, ModelMap modelMap, @RequestParam HashMap<String, Object> param) {
		List<HashMap<String, Object>> primaryList = medimembersvc.getpriList(param); // 학술지분류
		List<HashMap<String, Object>> nation = medimembersvc.getnation(param); // nation
		List<HashMap<String, Object>> job = medimembersvc.getjob(param); // job

		modelMap.addAttribute("pri", primaryList); // 변수명 변경 (primaryList)
		modelMap.addAttribute("nat", nation);
		modelMap.addAttribute("job", job);
		return "member/signUp";
	}

	@ResponseBody
	@PostMapping("idcheck")
	public ResponseEntity<Object> checkUserId(@RequestBody HashMap<Object, String> requestData) {
			String userId = requestData.get("userid");

			// 여기에 중복 체크 로직을 수행하고, 중복 여부를 반환하는 코드
			int isDuplicate = medimembersvc.isUserIdDuplicate(userId);
			System.out.println("is duplicate : " + isDuplicate);

			// 중복 여부를 JSON으로 반환
		return ResponseEntity.ok().body("{\"result\": " + isDuplicate + "}");
	}

	@ResponseBody
	@PostMapping("signupchk")
	public HashMap<String, Object> signUpCheck(HttpServletRequest req, ModelMap modelMap,
			@RequestBody HashMap<String, Object> param) {
		// 값들어오는지 확인
		System.out.println("con-userId: " + param.get("userid"));
		System.out.println("con-userPass: " + param.get("userpw"));
		System.out.println("con-userNm: " + param.get("usernm"));
		System.out.println("con-nation: " + param.get("nation"));
		System.out.println("con-job: " + param.get("job"));
		System.out.println("con-primary: " + param.get("major"));
		System.out.println("con-second: " + param.get("interest"));

		// 사용자 정보 db에 넣기
		medimembersvc.insertUserInfo(param);

		// signUp.js에서 idcheck,pass체크,email체크 하고 다 성공하면 => success로 넘어가게
		// JSON 형식으로 응답할 데이터를 담을 HashMap을 생성합니다.
		HashMap<String, Object> response = new HashMap<String, Object>();
		// 회원가입이 성공적으로 이루어졌음을 나타내는 "status" 키와 그 값을 HashMap에 추가합니다.
		response.put("status", "success");
		// 위에서 생성한 HashMap을 반환하면, Spring MVC는 이를 JSON 형태로 변환하여 클라이언트에게 응답합니다.
		return response;

		// 이 코드는 클라이언트에서 전송된 회원가입 관련 정보를 받아서 데이터베이스에 저장하고,
		// 성공 여부를 JSON 형태로 응답하는 간단한 회원가입 처리를 담당하는 컨트롤러 메서드입니다.
	}
	
	@PostMapping("interest")
	@ResponseBody
	public List<HashMap<String, Object>> interestChk(@RequestBody HashMap<String, Object> param) {
		String primary = (String) param.get("primary");
		System.out.println("primary : " + primary);
		List<HashMap<String, Object>> iList = medimembersvc.getsignList(primary);

		return iList;
	}
}