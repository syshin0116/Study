package com.paw.tgt.board.controller;

import com.paw.tgt.board.entity.Board;
import com.paw.tgt.board.entity.BoardCate;
import com.paw.tgt.board.service.BoardService;
import com.paw.tgt.common.domain.CommandMap;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Resource(name="boardService")
    private BoardService boardService;

    //게시판별 리스트
    @GetMapping("/{boardCate}")
    public List<Board> boardList(@PathVariable(required = false) BoardCate boardCate, Pageable pageable)throws Exception {
        return boardService.boardListByBoardCate(boardCate, pageable);
    }


    // 멍멍왈왈 게시판 리스트
//    @GetMapping(value = "/list")
//    public ModelAndView boardList(CommandMap commandMap, HttpServletRequest request) throws Exception {
//        ModelAndView mv = new ModelAndView("board_comm/board_list");
//        List<Map<String, Object>> list = boardService.boardList(commandMap.getMap());
//
//        mv.addObject("list", list); //글번호,제목,조회수,작성자,작성날짜 담아줌
//        return mv;
//    }
//    @GetMapping("/list")
//    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
//                    @RequestParam(required = false, defaultValue = "") String content) {
//        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
//            return repository.findAll();
//        } else {
//            return repository.findByTitleOrContent(title, content);
//        }
//    }


//    // 멍멍왈왈 게시판 글 등록폼
//    @RequestMapping(value="/board/writeForm")
//    public ModelAndView boardWriteForm(CommandMap commandMap) throws Exception{
//        ModelAndView mv = new ModelAndView("/board_comm/board_write");
//        return mv;
//    }
//
//    // 멍멍왈왈 게시판 글 등록
//    @RequestMapping(value = "/board/write")//RedirectAttributes 클래스를 이용해 등록 후 alert창 띄우기
//    public ModelAndView boardWrite(CommandMap commandMap, RedirectAttributes redirect) throws Exception {
//        //RedirectAttributes에 String 문자열을 전달 할 수 있음
//        ModelAndView mv = new ModelAndView("redirect:/board/list.paw");
//
//        boardService.boardWrite(commandMap.getMap());
//
//        redirect.addFlashAttribute("success", "글 작성이 완료되었습니다.");
//        //addFlashAttribute 경우 데이터가 post방식으로 전달해줌. 한번만 사용 할 수 있는 휘발성임
//        return mv;
//    }
//
//    //게시글 상세보기
//    @RequestMapping(value="/board/detail")
//    public ModelAndView BoardDetail(CommandMap commandMap) throws Exception{
//        ModelAndView mv = new ModelAndView("/board_comm/board_detail");
//
//
//        Map<String,Object> map = boardService.boardDetail(commandMap.getMap());
//        mv.addObject("map", map);
//
//
//        return mv;
//    }
//
//
//    //게시글 수정하기폼
//    @RequestMapping(value="/board/modifyForm")
//    public ModelAndView boardModifyForm(CommandMap commandMap) throws Exception {
//        ModelAndView mv = new ModelAndView("/board_comm/board_modify");
//
//        Map<String,Object> map = boardService.boardDetail(commandMap.getMap());
//        mv.addObject("map", map);
//
//
//        return mv;
//    }
//
//    // 게시글 수정하기
//    @RequestMapping(value = "/board/modify", method = RequestMethod.POST)//RedirectAttributes 클래스를 이용해 수정 후 alert창 띄우기
//    public ModelAndView boardModify(CommandMap commandMap, RedirectAttributes redirect) throws Exception {
//        ModelAndView mv = new ModelAndView("redirect:/board/list.paw");
//
//        boardService.boardModify(commandMap.getMap());
//        mv.addObject("BC_IDX", commandMap.get("BC_IDX"));
//        redirect.addFlashAttribute("info", "글 수정이 완료되었습니다.");
//
//        return mv;
//    }
//
//
//    //게시글 삭제하기
//    @RequestMapping(value = "/board/delete", method = RequestMethod.POST )//RedirectAttributes 클래스를 이용해 삭제 후 alert창 띄우기
//    public ModelAndView boardDelete(CommandMap commandMap, RedirectAttributes redirect) throws Exception {
//        ModelAndView mv = new ModelAndView("redirect:/board/list.paw");
//
//        boardService.boardDelete(commandMap.getMap());
//        redirect.addFlashAttribute("warning", "글 삭제가 완료되었습니다.");
//        return mv;
//    }


}
