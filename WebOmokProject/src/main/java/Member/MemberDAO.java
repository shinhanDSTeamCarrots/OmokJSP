package Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env");
			dataFactory = (DataSource)envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//아이디 중복 여부
	public boolean overlappedID(String id) {
		boolean result = false;
		try {
			con = dataFactory.getConnection();
			String query = "SELECT count(*) AS cnt FROM MEMBER_TB WHERE id = ?";
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next(); //결과 행이 하나 이상 있는 경우, 첫 번째 행으로 이동
			
			if (rs.getInt("cnt") > 0) { //rs의 열 값이 0보다 크다면
				result = true;
			} else {
				result = false;
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//사용자 추가
	public void addMember(MemberVO memberVO) {
		try {
			con = dataFactory.getConnection();
			String query = "INSERT INTO MEMBER_TB (,,,,) VALUES(?,?,?,?)";
			pstmt = con.prepareStatement(query);
			
			pstmt.executeUpdate();  //데이터베이스에 해당 SQL 쿼리가 실행
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//사용자 존재 여부 확인
	public boolean isExisted(MemberVO memberVO) {
		boolean result = false;

		try {
			con = dataFactory.getConnection();
			String query = "SELECT DECODE(count(*),1,'true','false') AS result FROM MEMBER_TB WHERE id=? AND pwd=?";
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, memberVO.getId());
			pstmt.setString(2, memberVO.getPwd());
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			
			result = Boolean.parseBoolean(rs.getString("result")); //문자열을 불리언으로 변환하는 정적 메서드
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;	
	}
}
