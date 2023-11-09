package Member;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
		String action = request.getPathInfo();
		System.out.println("action: "+action);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		
		//아이디 중복 여부
		if("/duplicateMember.do".equals(action)) {
			//아이디를 먼저 받아온다
			String id = (String) request.getParameter("id");
			//아이디로 중복 검사를 한다
			boolean isdup = memberService.duplicateMember(id);
			//그 값에 따라 T F 를 보낸다.
			if(isdup) {
				//중복임
				writer.print("F");
				return;
			}
			else {
				//중복 아님
				writer.print("T");
				return;
			}
		} else if("/joinMember.do".equals(action)) { // 회원 가입(사용자 추가)
			//비밀번호 암호화
			String hashedPwd = hashPassword(request.getParameter("signPw"));
			
			MemberVO memberVO = new MemberVO();
			
			memberVO.setMember_id(request.getParameter("signId"));
			memberVO.setMember_pw(hashedPwd);
			memberVO.setMember_nm(request.getParameter("signName"));
			memberVO.setMember_nicknm(request.getParameter("nicknm"));
			memberVO.setEmail(request.getParameter("signEmail"));
			memberService.joinMember(memberVO);
			return;
		} else if("/loginMember.do".equals(action)) { //로그인
			//비밀번호 암호화
			String hashedPwd = hashPassword(request.getParameter("loginPw"));
			
			MemberVO memberVO = new MemberVO();
			
			memberVO.setMember_id(request.getParameter("loginId"));
			memberVO.setMember_pw(hashedPwd);
			MemberVO logined = memberService.loginMember(memberVO);
			if(logined == null) {
				//로그인 실패
				writer.print("F");
			}
			else {
				HttpSession session = request.getSession();
				session.setAttribute("myvo", logined);
				writer.print("T");
				//로그인 성공
			}
			return;
		}
	}
		
	//비밀번호 암호화
	private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            // 바이트 배열을 16진수 문자열로 변환
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
