package Member;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("/member/*")
public class MemberController extends HttpServlet {

	MemberService memberService;
	
	public void init (ServletConfig config) throws ServletException{
		memberService = new MemberService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = null;
		String action = request.getPathInfo();
		System.out.println("action: "+action);
		
		//아이디 중복 여부
		if("/duplicateMember.do".equals(action)) {
			request.setAttribute("msg", "T");
		} else if(!"/duplicateMember.do".equals(action)) { //사용 중인 아이디
			request.setAttribute("msg", "F");
		} else if("/joinMember.do".equals(action)) { // 회원 가입(사용자 추가)
			//비밀번호 암호화
			
			MemberVO memberVO = new MemberVO();
			
			memberVO.setMember_id(request.getParameter("signId"));
			memberVO.setMember_pw(request.getParameter("signPw"));
			memberVO.setMember_nicknm(request.getParameter("signName"));
			memberVO.setEmail(request.getParameter("signEmail"));
			memberService.joinMember(memberVO);

			nextPage = "/WebOmokProject/.jsp"; //로그인 페이지로 이동
		} else if("/loginMember.do".equals(action)) { //로그인
			MemberVO memberVO = new MemberVO();
			
			memberVO.setMember_id(request.getParameter("loginId"));
			memberVO.setMember_pw(request.getParameter("loginPw"));
			memberService.loginMember(memberVO);
			
			nextPage = "/WebOmokProject/room/listRoom.jsp"; //대기실로 이동
		} else if(!"/loginMember.do".equals(action)) { //로그인 실패
			request.setAttribute("msg", "F");
		}
	}
	
	void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberVO memberVO = memberService.loginMember(null);
		HttpSession session = request.getSession();
		session.setAttribute("myvo", memberVO);
	}
	//비밀번호 암호화 함수 추가
}
