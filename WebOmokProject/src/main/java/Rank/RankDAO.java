package Rank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

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
	
	//승률계산
	public double winRate(int member_no) {
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
		try {
			con=dataFactory.getConnection();
						
			String winquery="SELECT COUNT(WINNER_NO) AS WIN_CNT FROM LOG_TB WHERE WINNER_NO=?";
			pstmt=con.prepareStatement(winquery);		
			pstmt.setInt(1, member_no);
			rs=pstmt.executeQuery();
			rs.next();
			int winCnt=rs.getInt("WIN_CNT");
			
			String losequery="SELECT COUNT(LOSER_NO) AS LOSE_CNT FROM LOG_TB WHERE LOSER_NO=?";
			pstmt=con.prepareStatement(losequery);		
			pstmt.setInt(1, member_no);
			rs=pstmt.executeQuery();
			rs.next();
			int loseCnt=rs.getInt("LOSE_CNT");
			
			double winRate=0.0;
			if(winCnt + loseCnt > 0) {
				winRate = (double)winCnt / (winCnt + loseCnt) * 100.0;
			}
			return winRate;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return 0.0;
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
	}
}
