package Member;

import java.math.BigInteger;
import java.security.MessageDigest;
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
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 아이디 중복 여부
	public boolean overlappedID(String member_id) {
		boolean result = false;
		try {
			con = dataFactory.getConnection();
			String query = "SELECT count(*) AS cnt FROM MEMBER_TB WHERE member_id = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, member_id);
			ResultSet rs = pstmt.executeQuery();
			rs.next(); // 결과 행이 하나 이상 있는 경우, 첫 번째 행으로 이동

			if (rs.getInt("cnt") > 0) { // rs의 열 값이 0보다 크다면
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

	// 사용자 추가
	public void addMember(MemberVO memberVO) {
		
		try {
			con = dataFactory.getConnection();
			String query = "INSERT INTO MEMBER_TB (member_no,member_id,member_pw,member_nm,email) "
					+ "VALUES(MEMBER_SEQ.NEXTVAL,?,?,?,?)";
			pstmt = con.prepareStatement(query);

			
			pstmt.setInt(1,memberVO.getMember_no());
			pstmt.setString(2, memberVO.getMember_id());
			pstmt.setString(3, memberVO.getMember_pw());
			pstmt.setString(4, memberVO.getMember_nicknm());
			pstmt.setString(5, memberVO.getEmail());

			pstmt.executeUpdate(); // 데이터베이스에 해당 SQL 쿼리가 실행
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 사용자 존재 여부 확인 및 확인되면 사용자 정보 result에 저장
	public MemberVO isExisted(MemberVO memberVO) {
		MemberVO result = null;

		try {
			con = dataFactory.getConnection();
			String query = "SELECT * FROM MEMBER_TB WHERE member_id=? AND member_pw=?";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, memberVO.getMember_id());
			pstmt.setString(2, memberVO.getMember_pw());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				result = new MemberVO();
				result.setMember_no(rs.getInt("member_no"));
				result.setMember_id(rs.getString("member_id"));
				result.setMember_nm(rs.getString("member_nm"));
				result.setJoin_date(rs.getDate("join_date"));
				result.setEmail(rs.getString("email"));
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
