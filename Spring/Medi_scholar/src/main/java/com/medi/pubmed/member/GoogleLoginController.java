package com.medi.pubmed.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@CrossOrigin("*")
@Controller
public class GoogleLoginController {
	@Value("262441477345-7b93nc3lfquuov7f25o1tmqgl0bnc3me.apps.googleusercontent.com")
	private String googleClientId;
	@Value("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")
	private String googleClientPw;
	
	
	private static final Logger logger = LoggerFactory.getLogger(GoogleLoginController.class);
	
	@Autowired
	private MediMemberService medimembersvc;
	
	
	@RequestMapping(value="/api/v1/oauth2/google", method = RequestMethod.POST)
	public String loginUrlGoogle(){
		String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
                + "&redirect_uri=http://localhost:8080/api/v2/oauth2/google&response_type=code&scope=email%20profile%20openid&access_type=offline";
		
		return reqUrl;
		
	}
	
	 @RequestMapping(value="/api/v2/oauth2/google", method = RequestMethod.GET)
	    public void loginGoogle(@RequestParam(value = "code") String authCode,ModelMap modelMap ,HttpServletRequest req,HttpServletResponse resp , @RequestParam HashMap<String, Object> param) throws IOException{
	        RestTemplate restTemplate = new RestTemplate();
	        GoogleRequest googleOAuthRequestParam = GoogleRequest
	                .builder()
	                .clientId(googleClientId)
	                .clientSecret(googleClientPw)
	                .code(authCode)
	                .redirectUri("http://localhost:8080/api/v2/oauth2/google")
	                .grantType("authorization_code").build();
	        ResponseEntity<GoogleResponse> resultEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
	                googleOAuthRequestParam, GoogleResponse.class);
	        String jwtToken=resultEntity.getBody().getId_token();
	        Map<String, String> map=new HashMap<>();
	        map.put("id_token",jwtToken);
	        ResponseEntity<GoogleInfResponse> resultEntity2 = restTemplate.postForEntity("https://oauth2.googleapis.com/tokeninfo",
	                map, GoogleInfResponse.class);
	        String email=resultEntity2.getBody().getEmail();
	        int emailExists = medimembersvc.checkEmailExist(email);
	        	       
			if (emailExists == 1) {
				param.put("user_email", email);
				HashMap<String, Object> user = medimembersvc.googleUserInfo(param);
				//user에 userId라는 이름으로 user_email
				// usernm 담겨있음
				//HttpSession session = req.getSession();
				//session.setAttribute("user_email", email);
				//user.put("user_email", email);
				
				System.out.println("email :"+user.get("userid"));
				System.out.println("name :"+user.get("usernm"));
				
				// Call the method to check Google login in another controller
				 if (user != null) {
						HttpSession session = req.getSession();
						
						session.setAttribute("userId", user.get("userid"));
						// user_email 을 userid라는 이름으로 저장한거임 , sql문에 userId인데 값이 userid로 찍히는 것은 카멜식 떄문이다 user_email _ 때문에 _사라지고 userId I 대문자가 i로 바뀐다.
						session.setAttribute("userNm", user.get("usernm"));
						
				 	}
				 		resp.sendRedirect("/journal/journallist");
	        		} else {
	        			// 이메일이 존재하지 않으면 세션에 로그인 정보를 저장하지 않고 로그인 화면으로 리다이렉트
	        			resp.sendRedirect("/member/login");
	        		}
	 }
}


