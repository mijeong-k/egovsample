package egov.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import egov.service.MemberService;

// 어노테이션(시스템주석)
@Controller
public class MemberController {

	@Resource(name="memberService")
	public MemberService memberService;
	
	
	@RequestMapping("/memberWrite.do")
	public String memberWrite() {
		return "member/memberWrite";
	}
	
	@RequestMapping("/useridChk.do")
	// @ResponseBody :: 비동기방식에서 메세지 전달을 위한 수단
	@ResponseBody
	public String selectMemberUseridCnt(String userid) throws Exception{
		
		// cnt = 0; :: 사용가능 / = 1; :: 중복
		// 숫자+영문자+"_-" 만 허용 =>2 / cnt = 2; :: 특수문자, 자릿수 미달초과 등
		int cnt = memberService.selectMemberUseridCnt(userid);
		
		// {1} 첫번째 자리는 a~z, A~Z 이고, 5개부터 11개 사이는 0~9, a~z, A~Z
		String pattern = "[a-zA-Z]{1}[0-9a-zA-Z_-]{5,11}";
		boolean result = userid.matches(pattern);
		if(result == false) {
			cnt = 2;
		}
		
		return cnt+"";
	}
	
	@RequestMapping("/loginWrite.do")
	public String loginWrite(HttpSession session) {
		
		// session.setAttribute("변수명", "값") == Session SessionID = "사용자ID";
		session.setAttribute("SessionID", "test123");
		
		return "redirect:/sample.do";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		
		// session.setAttribute("변수명", "값") == Session SessionID = "사용자ID";
		session.removeAttribute("SessionID");
		
		return "redirect:/sample.do";
	}	
}
