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
	
	@RequestMapping(value="/replyWrite.do")
	public String replyWrite(BoardVO vo, ModelMap model) {
		
		model.addAttribute("unq", vo.getUnq());
		model.addAttribute("thread", vo.getThread());
		
		return "board/replyWrite";
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

	   
	   //@ResponseBody 가 있어야만 웹주소값이 아닌 값(여기선 msg)을 보낼 수 있음
	   //비동기 전송방식을 통해 데이터를 전달 하기위한 목적을 가진 어노테이션
	   
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
	   
	   @RequestMapping(value="/replyWriteSave.do")
	   @ResponseBody
	   public String insertReBoardReply(BoardVO vo) throws Exception{
		   
		   // unq = 1;		   
		   int unq = vo.getUnq();
		   BoardVO vo1 = boardService.selectReBoardFid(unq);
		   System.out.println("**********");
		   
		   String thread = null;
		   String mythread = "";
		   
		   // 답글이 하나도 없을 때(== null)
		   if(vo1 == null) {
			   mythread = vo.getThread()+"a";
		   }else {
			   // ex : ac => ad
			   thread = vo1.getThread();  //ac => c => d => "a"+"d"
			   char lastword = ' ';
			   lastword = thread.charAt(thread.length()-1);
			   lastword++;		   
			   
			   mythread = thread.substring(0, thread.length()-1) + lastword;
		   }
		   
		   String msg = "";
		   vo.setThread(mythread);
		   String result = boardService.insertReBoardReply(vo);
		   
		   // 정상 저장 :: (result == null)
		   if(result == null) {
			   msg = "ok";
		   }
			   
		   return msg;
	   }
	   
	   @RequestMapping(value="/reboardList.do")
	   public String selectReBoardList(BoardVO vo, ModelMap model) throws Exception{
	      
	      List<?> list = boardService.selectReBoardList(vo);
	      model.addAttribute("resultList", list);
	      
	      return "board/reboardList";
	   }
	   
		@RequestMapping(value="reboardDetail.do")
		public String selectReBoardDetail(int unq, ModelMap model) throws Exception{
			
			BoardVO vo = boardService.selectReBoardDetail(unq);	
			
			// 줄바꿈, 띄어쓰기 자바어로 적용
			String content = vo.getContent();
			content = content.replace("\n", "<br>");
			content = content.replace(" ", "&nbsp;");	
			vo.setContent(content);
			
			model.addAttribute("resultDetail", vo);
			
			// 조회수 증가
			// int cnt = boardService.updateBoardHits(unq);
			
			return "board/reboardDetail";
		}
		
		
		
		
		//레이아웃 추가 후 작성하는 컨트롤러
		@RequestMapping(value="/sample.do")
		public String sample() {
			
			return "main/sample";
		}
}





