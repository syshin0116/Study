package kr.co.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;

/**
* @packageName    : kr.co.main.controller
* @fileName       : MainController.java
* @author         : HYE
* @date           : 2022.08.28
* @description    : 메인화면 컨트롤러
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2022.08.28        Hye      			최초 생성
*/
@Slf4j
@Controller
public class MainController {
	
	@Autowired
	BoardService boardService;
    
    /**
    * @methodName    : main
    * @description 	 : 메인화면 페이지로 이동
    * @author        : Hye
    * @date        : 2022.08.27
    * @param 
    * @return
    * @throws Exception
    */  
    @GetMapping("/main")
	public String main(Model model) throws Exception{

    	log.info("MainController.java > main() 호출");
		
    	return "/main/main"; //생성한 jsp명 (list.jsp)
	}
}
