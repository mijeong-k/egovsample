package egov.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import egov.service.BoardService;
import egov.service.BoardVO;

// 어노테이션(주석)
@Controller
public class BoardController {

	@Resource(name = "boardService")
	public BoardService boardService;
	
	@RequestMapping(value="/boardWrite.do")
	public String boardWrite() {
		
		return "board/boardWrite";
	}
	
	@RequestMapping(value="/boardWriteSave.do")
	@ResponseBody
	public String insertBoard(BoardVO vo) throws Exception {

		String msg = "";
		String result = boardService.insertBoard(vo);
		if(result == null) {
			System.out.println("============ 저장완료 ===========");
			msg = "ok";
		} else {
			System.out.println("============ 저장실패 ===========");
			msg = "fail";
		}

		return msg;
	}
	
	@RequestMapping(value="/boardList.do")
	public String selectBoardList(BoardVO vo, ModelMap model) throws Exception {
		
		System.out.println("=== page : " + vo.getPage());
			
		int unit = 5;
		int page = vo.getPage();
		
		// 1 :: 1 , 2 :: 6 or 11, 3 :: 11 or 21 
		// int startno = (page-1)*10 +1;
		int startno = (page-1)*unit +1;
		// endno = startno+9;   or   endno = startno+(10-1)
		int endno = page*unit;	
		
		vo.setStartno(startno);
		vo.setEndno(endno);
		
		int total = boardService.selectBoardTotal(vo);
		List<?> list = boardService.selectBoardList(vo);
		
		// 마지막페이지 번호(total/페이지 당 보여줄 갯수를 올림한 수)
		// 또는 int lastpage = (int) Math.ceil( total/5.0);
		int lastpage = (int) Math.ceil((double) total/unit);

		// page별 시작번호
		int pageno = total-((page-1)*unit);
		
		// ("jsp에서 사용할 이름", 실제데이터)
		model.addAttribute("resultList", list);
		model.addAttribute("total",total);
		model.addAttribute("lastpage",lastpage);	
		model.addAttribute("pageno",pageno);	
		
		return "board/boardList";
	}
	
	@RequestMapping(value="boardDetail.do")
	public String selectBoardDetail(int unq, ModelMap model) throws Exception{
		
		BoardVO vo = boardService.selectBoardDetail(unq);	
		
		// 줄바꿈, 띄어쓰기 자바어로 적용
		String content = vo.getContent();
		content = content.replace("\n", "<br>");
		content = content.replace(" ", "&nbsp;");	
		vo.setContent(content);
		
		model.addAttribute("resultDetail", vo);
		
		// 조회수 증가
		int cnt = boardService.updateBoardHits(unq);
		
		return "board/boardDetail";
	}
	
	@RequestMapping(value="/boardPassWrite.do")
	public String boardPassWrite(int unq, ModelMap model) {
		
		model.addAttribute("unq", unq);
		
		return "board/boardPassWrite";
	}
	
	@RequestMapping(value="/boardDelete.do")
	// int unq도 받고, password도 받아야 함. 2개 이상을 받아야 하면 vo에 저장.
	public String deleteBoard(BoardVO vo) throws Exception {
		
		// 암호 확인 서비스
		int cnt = boardService.selectBoardPass(vo);
		
		if(cnt == 1) {
			// 삭제 서비스
			System.out.println("======= 암호일치 =======");
			int result = boardService.deleteBoard(vo);
			
			if(result == 1) {
				System.out.println("======= 삭제성공 =======");		
			}else {
				System.out.println("======= 삭제실패 =======");
			}

		}else {
			System.out.println("======= 암호불일치 =======");
		}
		
		return "redirect:/boardList.do";
	}
	
	   @RequestMapping(value = "/reboardWrite.do")
	   public String reboardWrite() {
	      return "board/reboardWrite";
	   }

	   @RequestMapping(value = "/reBoardWriteSave.do")
	   @ResponseBody
	   public String insertReBoard(BoardVO vo) throws Exception {

	      String msg = "";
	      String result = boardService.insertReBoard(vo);
	      if (result == null) {
	         System.out.println("===== 저장완료 =====");
	         msg = "ok";
	      } else {
	         System.out.println("===== 저장실패 =====");
	         msg = "fail";
	      }

	      return msg;
	   }
	   
	   @RequestMapping(value="/reboardList.do")
	   public String selectReBoardList(BoardVO vo, ModelMap model) throws Exception{
	      
	      List<?> list = boardService.selectReBoardList(vo);
	      model.addAttribute("resultList", list);
	      
	      return "board/reboardList";
	   }
}





