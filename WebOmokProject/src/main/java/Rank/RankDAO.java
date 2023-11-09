package Rank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import Member.MemberVO;

public class RankDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public RankDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env");
			dataFactory = (DataSource)envContext.lookup("jdbc/oracle");
			con = dataFactory.getConnection();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//게임종료시 db에 저장
	public void addLog(LogVO addVo) {
		try {
			con=dataFactory.getConnection();
			String query= "INSERT INTO LOG_TB(log_id, winner_no, loser_no, log_date)"
					+"VALUES (LOG_SEQ.NEXTVAL, ?, ?, TO_DATE(SYSDATE,'MM-DD-YYYY HH24:MI:SS')";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, addVo.getLog_id());
			pstmt.setInt(1, addVo.getWinner_no());
			pstmt.setInt(1, addVo.getLoser_no());
			pstmt.setDate(1, addVo.getLog_date());
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//membervo에서 memberno기준으로 memberid 찾는거
	public String notoid(int member_no) {
		String findid=null;
		try {
			con=dataFactory.getConnection();
			String query = "SELECT MEMBER_ID FROM MEMBER_TB WHERE MEMBER_NO = ?";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, member_no);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				findid=rs.getString("member_id");
			}
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return findid;
	}
	
	
	//승률계산
	public Map<Integer, Double> winRate(int member_no) {
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        Map<Integer, Double> result = new HashMap<>();
        
		try {
			con=dataFactory.getConnection();
						
			String winquery="SELECT COUNT(WINNER_NO) AS WIN_CNT FROM LOG_TB WHERE WINNER_NO=?";
			pstmt=con.prepareStatement(winquery);		
			pstmt.setInt(1, member_no);
			rs=pstmt.executeQuery();
			rs.next();
			int winCnt=rs.getInt("WIN_CNT");
			result.put(1, (double) winCnt);
			
			String losequery="SELECT COUNT(LOSER_NO) AS LOSE_CNT FROM LOG_TB WHERE LOSER_NO=?";
			pstmt=con.prepareStatement(losequery);		
			pstmt.setInt(1, member_no);
			rs=pstmt.executeQuery();
			rs.next();
			int loseCnt=rs.getInt("LOSE_CNT");
			result.put(2, (double) loseCnt);
			
			double winRate=0.0;
			if(winCnt + loseCnt > 0) {
				winRate = (double)winCnt / (winCnt + loseCnt) * 100.0;
			}
			result.put(3, winRate);
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		
		return result;
	}
	//memberno기준으로 승률과 memerid찾아서 띄우기
	

}
