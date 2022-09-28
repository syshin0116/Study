package com.kosta.finalproject.controller;

import java.util.Optional;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.finalproject.dto.DriverBoard;
import com.kosta.finalproject.dto.PageBean;
import com.kosta.finalproject.dto.ResultBean;
import com.kosta.finalproject.exception.FindException;
import com.kosta.finalproject.service.DriverBoardService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("final/*")
public class BoardRestController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DriverBoardService service;
	
	@Autowired
	private ServletContext sc;
	
	// GET /backboard/board/list/페이지번호
	@GetMapping(value = {"list", "list/{optCp}"})
	public ResultBean<PageBean<DriverBoard>> list(@PathVariable Optional<Integer> optCp){
		ResultBean<PageBean<DriverBoard>> rb = new ResultBean<>();
		try {
			int currentPage;
			if(optCp.isPresent()) {
				currentPage = optCp.get();
			} else {
				currentPage =1;
			}
			PageBean<DriverBoard> pb = service.boardList(currentPage);
			rb.setStatus(1);
			rb.setT(pb);
		} catch(FindException e) {
			e.printStackTrace();
			rb.setStatus(0);
			rb.setMsg(e.getMessage());
		}
		return rb;
	}
	
	@GetMapping(value= {"search/{optword}", "search/{optWord}/{optCp}"})
	public ResultBean<PageBean<DriverBoard>> search(
			@PathVariable Optional<Integer> optCp,
			@PathVariable Optional<String> optWord){
		ResultBean<PageBean<DriverBoard>> rb = new ResultBean<>();
		try {
			PageBean<DriverBoard> pb;
			String word;
			if(optWord.isPresent()) {
				word = optWord.get();
			} else {
				word = "";
			}
			
			int currentPage = 1;
			if(optCp.isPresent()) {
				currentPage = optCp.get();
			}
			if("".equals(word)) {
				pb= service.boardList(currentPage);
			} else {
				pb = service.searchBoard(word, currentPage);
			}
			rb.setStatus(1);
			rb.setT(pb);
		} catch(FindException e) {
			e.printStackTrace();
			rb.setStatus(0);
			rb.setMsg(e.getMessage());
		}
		return rb;
	}
	@GetMapping("{boardNo}")
	public ResultBean<DriverBoard> viewBoard(@PathVariable long carBoardNo){
		ResultBean<DriverBoard> rb = new ResultBean<>();
		try {
			DriverBoard b = service.viewBoard(carBoardNo);
			rb.setStatus(1);
			rb.setT(b);
		} catch(FindException e) {
			e.printStackTrace();
			rb.setStatus(0);
			rb.setMsg(e.getMessage());
		}
		return rb;
	}
	
//	@PostMapping("write")
//	public ResponseEntity<?> write(
//			@RequestPart(required = false) List<MultipartFile> letterFiles,
//			@RequestPart(required = false) MultipartFile imageFile,
//			Board board, String greeting, HttpSession session){
//		logger.info("요청전달데이터 title=" + board.getBoardTitle() + ", content=" + board.getBoardContent());
//		logger.info("파일개수 : letterFiles.size()=" + letterFiles.size());
//		logger.info("파일크기 : imageFile.getSize()=" + imageFile.getSize() + ", 업로드된 파일 이름 : imageFile.getOriginalFileName()=" + imageFile.getOriginalFilename());
//		logger.info(greeting);
//		
//		// 게시글 내용 DB에 저장
//		try {
//			String loginedId = "id1";
//			
//			board.setBoardId(loginedId);
//			service.writeBoard(board);
//		} catch(AddException e1) {
//			e1.printStackTrace();
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
//		// 파일 경로 생성
//		String saveDirectory = sc.getInitParameter("filePath");
//		logger.error(sc.toString());
//		if(!new File(saveDirectory).exists()) {
//			logger.info("업로드 실제경로생성");
//			new File(saveDirectory).mkdirs();
//		}
//		long wroteBoardNo = board.getBoardNo();
//		
		// letterFiles 저장
//		int savedletterFileCnt = 0; // 서버에 저장된 파일 수
//		if(letterFiles != null) {
//			for(MultipartFile letterFile: letterFiles) {
//				long letterFileSize = letterFile.getSize();
//				if(letterFileSize>0) {
//					String letterOriginFileName = letterFile.getOriginalFilename();  // 자소서 파일 원본 이름 얻기
//					// 지원서 파일들 저장하기
//					logger.info("지원서 파일이름: " + letterOriginFileName +" 파일크기: " + letterFile.getSize());
//					//저장할 파일이름을 지정한다 ex) 글번호_letter_XXXX_원본이름
//					String letterfileName = wroteBoardNo + "_letter_" + UUID.randomUUID() + "_" + letterOriginFileName;
//					File savedLetterFile = new File(saveDirectory, letterfileName);//파일생성
//					try {
//						FileCopyUtils.copy(letterFile.getBytes(), savedLetterFile);
//						logger.info("지원서 파일저장:" + savedLetterFile.getAbsolutePath());
//					} catch(IOException e) {
//						e.printStackTrace();
//						return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR;)
//					}
//					savedletterFileCnt++;
//				}
//			}
//		}
//		logger.info("저장된 letter 파일개수 :"+savedletterFileCnt);
//		
//		// 이미지 파일 저장
//		File thumbnailFile = null;
//		long imageFileSize = imageFile.getSize();
//		int imageFileCnt = 0;
//		if(imageFileSize > 0) {
//			// 이미지 파일 저장하기
//			String imageOriginFileName = imageFile.getOriginalFilename();  // 이미지 파일 원본 이름 얻기
//			logger.info("이미지 파일이름 :" + imageOriginFileName +", 파일크기 :"+imageFile.getSize());
//			
//			// 저장할 파일이름을 저장한다
//			String imageFileName = wroteBoardNo + "_image+" + UUID.randomUUID() + "_" + imageOriginFileName;
//			// 이미지 파일 생성
//			File savedImageFile = new File(saveDirectory, imageFileName);
//			
//			try {
//				FileCopyUtils.copy(imageFile.getBytes(), savedImageFile);
//				logger.info("이미지 파일저장 :" + savedImageFile.getAbsolutePath());
//				
//				// 파일형식 확인
//				String contentType = imageFile.getContentType();
//				if(!contentType.contains("image/")) {
//					// 이미지 파일 형식이 아닌 경우
//					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR;)
//				}
//				
//				// 이미지 파일인 경우 섬네일 파일을 만듦
//				String thumbnailName = "s_" + imageFileName;
//				thumbnailFile = new File(saveDirectory, thumbnailName);
//				FileOutputStream thumbnailOS;
//				thumbnailOS = new FileOutputStream(thumbnailFile);
//				InputStream imageFileIS = imageFile.getInputStream();
//				int width = 100;
//				int height = 100;
//				Thumbnailator.createThumbnail(imageFileIS, thumbnailOS, width, height);
//				logger.info("섬네일파일 저장:" + thumbnailFile.getAbsolutePath() + ", 섬네일파일 크기:" + thumbnailFile.length());
//			}
//		}
//	}
}
