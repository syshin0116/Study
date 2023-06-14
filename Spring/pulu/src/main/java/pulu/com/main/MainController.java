package pulu.com.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String test(Locale locale, ModelAndView mv) {
		return "main_tiles";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String test2(Locale locale, ModelAndView mv) {
		
		return "join";
	}
	
	@RequestMapping(value = "/goodslist", method = RequestMethod.GET)
	public String test3(Locale locale, ModelAndView mv) {
		
		return "goodslist";
	}
	
	
//	@RequestMapping(value = "/main", method = RequestMethod.GET)
//	public String main(ModelAndView mv ,HttpSession session) throws Exception {
//		
//		if(session.getAttribute("MEMBER_NO")==null){
//		session.setAttribute("MEMBER_ID", "visitor");
//		}else if(Integer.parseInt(session.getAttribute("MEMBER_NO").toString())!=0){
//			
//			//사이드 장바구니 처리
//			List<Map<String,Object>> sessionList = new ArrayList<Map<String,Object>>();
//	        sessionList = goodsService.BascketMemberSelect( Integer.parseInt(session.getAttribute("MEMBER_NO").toString()));
//			session.setAttribute("basketList", sessionList);
//			System.out.println("로그인 세션 생성=============="+sessionList);
//		}
//		
//		model.addAttribute("goodsList",list);
//		model.addAttribute("currentPage",currentPage);
//		return "main_tiles";
//	}
	
} 
