package pulu.com.member;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j;
import pulu.com.common.CommandMap;

@Controller
@Log4j
// 선민: 회원가입 컨트롤러
public class MemberController {
	
	@Resource(name = "memberService")
	private MemberService memberService;
	
	/* ---------------------- (1) 회원가입 ---------------------- */

	// 가입완료 창으로 이동
	@RequestMapping(value = "/joinOK")
	public String joinOK(HttpServletRequest request) {

		return "joinOK";
	}

	// 회원가입 폼으로 이동
	@RequestMapping(value = "/joinForm", method = RequestMethod.GET)
	public String joinForm(HttpSession session) throws Exception {
		String loginStatus = (String)session.getAttribute("loginStatus");
		log.info("로그인 상태: " + loginStatus);
		
		if(loginStatus != null) { // 로그인된 상태라면
			return "redirect:/main.pulu";
		} else {
			log.info("메인페이지에서 join.jsp로 GET 이동");
			return "join";
		}
		
		
	}

	// 회원가입 (DB에 회원 정보를 insert)
	@RequestMapping(value = "/joinForm", method = RequestMethod.POST)
	public ModelAndView insertMember(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/joinOK.pulu");
		
		log.info(commandMap.getMap().get("ID")); //
		memberService.insertMember(commandMap.getMap());
		log.info("ModelAndView 데이터를 가지고 joinOK.jsp로 리다이렉트");
		return mv;
	}
	
	/* ---------------------- (2) 로그인 ---------------------- */
	
	// 로그인 폼으로 이동
	@RequestMapping(value = "/loginForm")
	public String login(ModelAndView mv, HttpSession session) {
		
		return "loginForm";
	}
	
	// 로그인
	@SuppressWarnings("unused")
	@RequestMapping(value = "/login")

	public String login(ModelAndView mv, CommandMap commandMap, HttpSession session)
	throws Exception
	{
		//해당 아이디로 검색하여 회원 정보 가져오기
		Map<String, Object> loginCheck = memberService.selectId(commandMap.getMap());
		if(loginCheck == null) //가입 되어 있지 않으면
		{
			//입력한 아이디가 없다는 메시지 출력
			mv.addObject("message", "해당 아이디가 존재하지 않습니다.");
			log.info("아이디 없음");
			return "loginForm";
		}
		//해당 회원정보가 있으면
		else
		{
			log.info(loginCheck.get("ID") + "의 정보를 DB로부터 가져왔습니다.");
			
			//비밀번호가 일치하면
		    if(loginCheck.get("PASSWORD").toString().equals(commandMap.get("PASSWORD").toString()))
		    {
		    	//세션 영역 저장(아이디, 회원 이름, 회원번호)
		    	session.setAttribute("loginStatus", "ON");
		    	session.setAttribute("loginId", loginCheck.get("ID"));
		    	session.setAttribute("loginName", loginCheck.get("NAME"));
		    	session.setAttribute("loginNum", loginCheck.get("NUM"));
		    	session.setAttribute("loginGrade", loginCheck.get("GRADE"));
		    	session.setAttribute("loginPw", loginCheck.get("PASSWORD"));
//		    	int weight = Integer.parseInt(loginCheck.get("MEMBER_WEIGHT").toString());
//				int height = Integer.parseInt(loginCheck.get("MEMBER_HEIGHT").toString());
//				
//				//회원 칼로리 계산 식
//				double MemberKcal = 655 + (9.56 * weight) + (5 * height) - (4.68 * 20);
//				
//				//세션에 회원 칼로리 저장
//				session.setAttribute("MyKcal", (int)MemberKcal/3);
//				System.out.println((int)MemberKcal/3);
//
//		    	//사이드 장바구니 처리
//				List<Map<String,Object>> sessionList = new ArrayList<Map<String,Object>>();
//		        sessionList = goodsService.BascketMemberSelect( Integer.parseInt(session.getAttribute("MEMBER_NO").toString()));
//				session.setAttribute("basketList", sessionList);
//				mv.addObject("basketList",sessionList);
//				System.out.println("로그인 세션 생성=============="+sessionList);

		    	String loginGrade = (String)session.getAttribute("loginGrade");
		    	
		    	if(loginGrade.equals("Admin")) {
		    	return "redirect:/adminMain.pulu";
		    	} else {
		    		log.info("로그인 성공");
		    		//return "main_tiles";
		    		return "redirect:/main.pulu";
		    	}
		    } else {	    		
		    	log.info("비번 다름"); 
		    	mv.addObject("message", "비밀번호가 일치하지 않습니다.");
		    	return "loginForm";
		    	}    
		}
	}
	
	// 로그아웃
	@RequestMapping(value = "/logout")
	public String logout(Model model, HttpServletRequest request, HttpSession session, CommandMap commandMap) {
		// getSession(false) : 현재 세션이 존재하면 기존 세션 리턴, 없으면 null값 리턴
		session = request.getSession(false);
		// 현재 세션이 존재하면
		if (session != null) {
			// 세션 소멸
			log.info("세션 소멸");
			session.invalidate();
		}
		return "redirect:/main.pulu";
	}
	
	 //선아: 아이디 찾기 
	@RequestMapping(value="/findIdForm")
	public ModelAndView findIdForm(HttpServletRequest request, CommandMap commandMap) throws Exception {
	 	   ModelAndView mav = new ModelAndView();
		mav.setViewName("findIdForm");
		return mav;
	}
	
   //아이디 찾기 완료
	
   @RequestMapping(value="/findIdForm2", method = RequestMethod.POST )
	public ModelAndView findIdForm2(HttpServletRequest request, CommandMap commandMap) throws Exception {
	   ModelAndView mav = new ModelAndView();
	   
	   Map<String, Object> memberMap=new HashMap<String, Object>();
	   memberMap=commandMap.getMap();
	   
	   Map<String, Object> chk = memberService.findFail0(memberMap);
	      if (chk == null) {	//아이디 값이 없으면
	         mav.setViewName("findIdForm");
	         mav.addObject("message", "조회된 정보가 없습니다. 다시 입력해주세요.");
	         return mav;
	      }else {
	   
	   String findId = memberService.findId(memberMap);

	   log.info(memberMap);
	   System.out.println("findID>>" + findId);
	   mav.addObject("findId", findId);
	   mav.setViewName("findIdForm2");
	   return mav;
	      }
	}
   
   
   //비밀번호 찾기
   
   @RequestMapping(value="/findPwForm")
	public ModelAndView findPwForm(HttpServletRequest request, CommandMap commandMap) throws Exception {
	 	   ModelAndView mav = new ModelAndView();
		mav.setViewName("findPwForm");
		return mav;
	}
   
   //비밀번호 찾기 완료
  
  @RequestMapping(value="/findPwForm2", method = RequestMethod.POST )
	public ModelAndView findPwForm2(HttpServletRequest request, CommandMap commandMap) throws Exception {
	   ModelAndView mav = new ModelAndView();
	   Map<String, Object> memberMap=new HashMap<String, Object>();
	   memberMap=commandMap.getMap();
	   
	   Map<String, Object> chk = memberService.findFail(memberMap);
	      if (chk == null) {	//아이디 값이 없으면
	         mav.setViewName("findPwForm");
	         mav.addObject("message", "조회된 정보가 없습니다. 다시 입력해주세요.");
	         return mav;
	      }else {
			   String findPw = memberService.findPw(memberMap);

			   mav.addObject("findPw", findPw);
			   mav.setViewName("findPwForm2");
			   return mav;	
	      }
	   
  }
  
      
   /* ---------------------- (3) 마이페이지 ---------------------- */
  
    @RequestMapping(value="/myPage")
	public ModelAndView myPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("myPage");
		return mav;
	}
    
    @RequestMapping(value="/memberInfo")
	public ModelAndView memberInfo(CommandMap commandMap, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		String ID = (String) session.getAttribute("ID");
		mav.addObject("ID",ID);
		commandMap.put("ID", ID);
		
		Map<String,Object> memberInfo = memberService.memberInfo(commandMap.getMap());
		 System.out.println(memberInfo);
		 mav.addObject("M",memberInfo);
		mav.setViewName("memberInfo");
		return mav;
	}
    
    @RequestMapping(value="/deleteForm")
	public ModelAndView deleteForm() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("deleteForm");
		return mav;
	}
	
    
	@RequestMapping(value = "/deleteForm", method = RequestMethod.POST)
	@ResponseBody	
	public ModelAndView deleteMember(CommandMap commandMap) throws Exception {
		
		ModelAndView mv = new ModelAndView("redirect:/deleteForm.pulu");
		
		log.info("str_Num = " + commandMap.getMap().get("str_Num")); 
		log.info("str_Pw  = " + commandMap.getMap().get("str_Pw")); 
		
		memberService.memberDelete(commandMap.getMap());
		log.info("ModelAndView 데이터를 가지고 deleteForm.jsp로 리다이렉트");
		return mv;
	}
	
	
	
	
    
	
	/* ---------------------- (4) 관리자 ---------------------- */
	
	// 관리자 메인페이지로 이동
		@RequestMapping(value = "/adminMain")
		public String adminMain(ModelAndView mv, HttpSession session) {
			
			mv.addObject("loginGrade", (String)session.getAttribute("loginGrade"));  
			return "/admin/adminMain";
		}
		
	/* ---------------------- (5) ArgumentResolver Test ---------------------- */
		
	// 컨트롤러에 도착하기 전, 파라미터를 받아 CommandMap객체에 넣는 ArgumentResolver 테스트
	@RequestMapping(value = "testMapArgumentResolver.pulu")
	public ModelAndView testMapArgumentResolver(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("");

		if (commandMap.isEmpty() == false) {
			Iterator<Entry<String, Object>> iterator = commandMap.getMap().entrySet().iterator();
			Entry<String, Object> entry = null;
			while (iterator.hasNext()) {
				entry = iterator.next();
				log.debug("key : " + entry.getKey() + ", value : " + entry.getValue());
			}
		}
		return mv;
	}
		
}
