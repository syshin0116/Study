package com.medi.pubmed.board;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("/community/*")
@Controller
public class CommunityController {

	// Logger 추가
	private static final Logger logger = LoggerFactory.getLogger(CommunityController.class);

	@Autowired
	private CommunityService communitySvc;
	//저널 목록 조회
	@GetMapping("list")
	public String getJournalList(@RequestParam HashMap<String, Object> param, HttpServletRequest req, ModelMap modelmap) {
	
			HttpSession session = req.getSession();

			// 멤버 부분 고쳐지면 아래 두 줄 삭제할것!!!!!!!!!!
			session.setAttribute("userNm", "주수빈");
			session.setAttribute("userId", "subin3322@naver.com");

			// 세션에서 유저 정보 불러오기
			String user_nm=(String) session.getAttribute("userNm");
			String user_email=(String) session.getAttribute("userId");
//			String user_pass=(String) session.getAttribute("user_pass");
//			String comm_postid = (String) param.get("comm_postid");
			
			param.put("user_email",user_email);
//			param.put("user_pass",user_pass);
			param.put("user_nm",user_nm);
//			param.put("postid", comm_postid);

			// Logger으로 출력 확인
			logger.info("list param: " + String.valueOf(param));

			List<HashMap<String, Object>>jl = communitySvc.getCommunityList(param);
			modelmap.addAttribute("cl", jl);


		
		return ("community/boardList");
	}
	
	@GetMapping("writecomm")
	public String getCommunityWrite(@RequestParam HashMap<String, Object>param, HttpServletRequest req, ModelMap modelmap) {
		
		return "community/boardReg";
	}
	
	@PostMapping("writecomm")
	public String insertCommunity (ModelMap modelMap, HttpServletRequest req, @RequestParam HashMap<String, Object> param) {
	
		String title = (String) param.get("title");
		String memo = (String) param.get("CommunityContent");
		
		HttpSession session = req.getSession();
		String user_email=(String) session.getAttribute("userId");
		
		param.put("user_email", user_email);		
		param.put("title", title);
		param.put("CommunityContent", memo);

		logger.info("writecomm param: " + param);
		communitySvc.insertCommunity(param);
		return ("redirect:list");
		
	}
	
	@GetMapping("updatecomm")
	public String getCommunityUpdate(@RequestParam HashMap<String, Object>param, HttpServletRequest req, ModelMap modelmap) {
		HashMap<String, Object>cdetail = communitySvc.detailCommunity(param);
		modelmap.addAttribute("cd", cdetail);
		return "community/cmmUpdate";
	}
	
	@PostMapping("updatecomm")
	public String updateCommunity (ModelMap modelMap, HttpServletRequest req, @RequestParam HashMap<String, Object> param) {
		
		System.out.println("title : "+param.get("title"));
		System.out.println("memo : "+param.get("updateContent"));
		System.out.println("user_nm : "+param.get("user_nm"));
		System.out.println("user_email : "+param.get("user_email"));
		System.out.println("postid : "+param.get("postid"));
		
		String title = (String) param.get("title");
		String memo = (String) param.get("updateContent");
		String user_nm = (String) param.get("user_nm");
		String user_email = (String) param.get("user_email");
		
		 String comm_postid = (String) param.get("postid");
		    if (comm_postid != null && !comm_postid.isEmpty()) {
		        try {
		            // 필요한 경우 Integer로 변환
		            int postIdInt = Integer.parseInt(comm_postid);
		            param.put("postid", postIdInt);
		        } catch (NumberFormatException e) {
		            // postid 형변환 중 오류 발생시 처리
		            System.err.println("Invalid postid format: " + comm_postid);
		            modelMap.addAttribute("message", "잘못된 게시글 번호입니다.");
		            return "redirect:list";
		        }
		    } else {
		        // postid가 없는 경우 처리
		        System.err.println("postid is missing");
		        modelMap.addAttribute("message", "게시글 번호가 없습니다.");
		        return "redirect:list";
		    }
		
		
		param.put("title", title);
		param.put("updateContent", memo);
		param.put("user_nm", user_nm);
		param.put("user_email", user_email);
		
		int result = communitySvc.updateCommunity(param);
		if (result > 0) {
            modelMap.addAttribute("message", "커뮤니티가 성공적으로 업데이트되었습니다.");
        } else {
            modelMap.addAttribute("message", "커뮤니티 업데이트에 실패했습니다.");
        }
		
		System.out.println("result : " + result);
		return ("redirect:list");
		
	}
	
	@GetMapping("detailcomm")
	public String detailCommunity(@RequestParam HashMap<String, Object>param, HttpServletRequest req, ModelMap modelmap) {
		
//		// HashMap에서 'postid' 키를 사용하여 값을 가져옴
		String comm_postid = (String) param.get("postid");

		// postid 값이 제대로 가져와졌는지 확인
		if (comm_postid != null && !comm_postid.isEmpty()) {
		    try {
		        // postid 값을 Integer로 변환 (필요한 경우)
		        int postIdInt = Integer.parseInt(comm_postid);
		        // 변환된 값을 사용하거나 다시 param에 저장
		        // 예: param.put("postid", postIdInt);
		    } catch (NumberFormatException e) {
		        // postid 값이 정수로 변환되지 않을 경우의 오류 처리
		        System.err.println("Invalid postid format: " + comm_postid);
		        // 적절한 오류 처리를 수행
		    }
		} else {
		    // postid 값이 null이거나 비어있을 경우의 처리
		    System.err.println("postid is missing or empty");
		    // 적절한 오류 처리를 수행
		}

		
		HashMap<String, Object>cdetail = communitySvc.detailCommunity(param);
		modelmap.addAttribute("cd", cdetail);
		
		return "community/boardDetail";
	}
	
	//댓글 입력, 삭제, 수정
		@ResponseBody
		@PostMapping("reply")
		public HashMap<String, Object> insertReply(ModelMap modelMap, HttpServletRequest req, @RequestBody HashMap<String, Object> param) {
			
			System.out.println("cmm_postid : " + param.get("comm_postid"));
			System.out.println("comm_writernm : " + param.get("comm_writernm"));
			System.out.println("cmm_rememo : " + param.get("comm_rememo"));
			
			String comm_postid = (String) param.get("comm_postid");
			String comm_rememo = (String) param.get("comm_rememo");
			String user_email = req.getSession().getAttribute("user_email").toString();

			param.put("comm_postid", comm_postid);
			param.put("comm_rememo", comm_rememo);
			param.put("user_email", user_email);
			
			communitySvc.insertReply(param);
			
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put("status", "success");
			
			response.put("message", "Reply added successfully");
			
			return response;
		}
		
		@ResponseBody
		@RequestMapping(value="delreply", method = RequestMethod.DELETE)
		public HashMap<String, Object> deleteReply(ModelMap modelMap, HttpServletRequest req, @RequestBody HashMap<String, Object> param) {
			
			communitySvc.deleteReply(param);
			
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put("status", "success");
			
			response.put("message", "Reply deleted successfully");
			
			return response;
			
		}
		
		@ResponseBody
		@PostMapping("subreply")
		public HashMap<String, Object> insertSubreply(ModelMap modelMap, HttpServletRequest req, @RequestBody HashMap<String, Object> param) {
			
			System.out.println("jnlno : " + param.get("jnlno"));
			System.out.println("reno : " + param.get("reno"));
			System.out.println("usernm : " + param.get("usernm"));
			System.out.println("submemo : " + param.get("rememo"));

			String userId = req.getSession().getAttribute("userId").toString();

			communitySvc.insertSubreply(param);
			
			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put("status", "success");
			
			response.put("message", "Reply added successfully");
			
			return response;
		}
		
	
}
