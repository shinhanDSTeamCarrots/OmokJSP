package Member;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

public class MemberDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public MemberDAO() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//아이디 중복 여부
	public boolean overlappedID(String id) {
		boolean result = false;
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//사용자 추가
	public void addMember(MemberVO memberVO) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//사용자 존재 여부
	public boolean isExisted(MemberVO memberVO) {
		boolean result = false;
		String id = memberVO.getId();
		String pwd = memberVO.getPwd();
		
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;	
	}
}
