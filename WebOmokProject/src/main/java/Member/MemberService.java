package Member;

import java.io.File;
import java.util.List;


public class MemberService {
	MemberDAO memberDAO;
	
	public MemberService() {
		memberDAO = new MemberDAO();
	}
	
	// 아이디 중복 여부
	public boolean duplicateMember(String member_id) {
		return memberDAO.overlappedID(member_id);
	}
	
	//사용자 추가
	public void joinMember(MemberVO memberVO) {
		memberDAO.addMember(memberVO);
	}
	
	//로그인
	public MemberVO loginMember(MemberVO memberVO) {
		return memberDAO.isExisted(memberVO);
	}
}
