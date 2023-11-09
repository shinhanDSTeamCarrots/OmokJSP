package Rank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.eclipse.jdt.internal.compiler.ast.ReturnStatement;

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
			pstmt.setInt(1, addVo.getWinner_no());
			pstmt.setInt(2, addVo.getLoser_no());
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

//	public double winRate(int member_no) {
//		Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//		try {
//			con=dataFactory.getConnection();
//
//			String winquery="SELECT COUNT(WINNER_NO) AS WIN_CNT FROM LOG_TB WHERE WINNER_NO=?";
//			pstmt=con.prepareStatement(winquery);		
//			pstmt.setInt(1, member_no);
//			rs=pstmt.executeQuery();
//			rs.next();
//			int winCnt=rs.getInt("WIN_CNT");
//
//			String losequery="SELECT COUNT(LOSER_NO) AS LOSE_CNT FROM LOG_TB WHERE LOSER_NO=?";
//			pstmt=con.prepareStatement(losequery);		
//			pstmt.setInt(1, member_no);
//			rs=pstmt.executeQuery();
//			rs.next();
//			int loseCnt=rs.getInt("LOSE_CNT");
//
//			double winRate=0.0;
//			if(winCnt + loseCnt > 0) {
//				winRate = (double)winCnt / (winCnt + loseCnt) * 100.0;
//			}
//			return winRate;
//
//		}catch (SQLException e) {
//			e.printStackTrace();
//			return 0.0;
//		} finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//		}
//	}
//}
//	
		
//	// 승률계산 memberno별 memberid, wincnt, losecnt, winrate 
//	// 0 - member_id
//	// 1 - win_cnt
//	// 2 - lose_cnt
//	// 3- win_rate
//	public Map<Integer, Object> winRate(int member_no) {
//		Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        
//        Map<Integer, Object> result = new HashMap<>();
//        
//		try {
//			con=dataFactory.getConnection();
//			
//			String query = "SELECT MEMBER_ID FROM MEMBER_TB WHERE MEMBER_NO = ?";
//			pstmt=con.prepareStatement(query);
//			pstmt.setInt(1, member_no);
//			rs=pstmt.executeQuery();
//			rs.next();
//			String memberId=rs.getString("MEMBER_ID");
//			result.put(0, memberId);
//			
//			String winquery="SELECT COUNT(WINNER_NO) AS WIN_CNT FROM LOG_TB WHERE WINNER_NO=?";
//			pstmt=con.prepareStatement(winquery);		
//			pstmt.setInt(1, member_no);
//			rs=pstmt.executeQuery();
//			rs.next();
//			int winCnt=rs.getInt("WIN_CNT");
//			result.put(1, winCnt);
//			
//			String losequery="SELECT COUNT(LOSER_NO) AS LOSE_CNT FROM LOG_TB WHERE LOSER_NO=?";
//			pstmt=con.prepareStatement(losequery);		
//			pstmt.setInt(1, member_no);
//			rs=pstmt.executeQuery();
//			rs.next();
//			int loseCnt=rs.getInt("LOSE_CNT");
//			result.put(2, loseCnt);
//			
//			double winRate=0.0;
//			if(winCnt + loseCnt > 0) {
//				winRate = (double)winCnt / (winCnt + loseCnt) * 100.0;
//			}
//			result.put(3, winRate);
//			
//		}catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//		}		
//		return result;
//	}
//	
//	public List<Map<Integer, Object>> getAllWinRates(List<Integer> memberNos){
//		List<Map<Integer, Object>> allWinRates =new ArrayList<>();
//		
//		RankDAO rankDAO =new RankDAO();
//		
//		for(Integer memberNo : memberNos) {
//			Map<Integer, Object> winRate = rankDAO.winRate(memberNo);
//			allWinRates.add(winRate);
//		}
//		
//		//승률 기준으로 내림차순으로 정렬
//		Collections.sort(allWinRates, new Comparator<Map<Integer, Object>>() {
//	        @Override
//	        public int compare(Map<Integer, Object> user1, Map<Integer, Object> user2) {
//	            Double winRate1 = (Double) user1.get(3);
//	            Double winRate2 = (Double) user2.get(3);
//
//	            // 내림차순으로 정렬
//	            return Double.compare(winRate2, winRate1);
//	        }
//		});
//		return allWinRates;
//	}
	
	public List<Map<String, Object>> getAllMemberWinRates() {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<Map<String, Object>> result = new ArrayList<>();
	    try {
	        conn = dataFactory.getConnection();
	        String query = "SELECT M.MEMBER_ID, L1.WINCNT, L2.LOSECNT, (L1.WINCNT / (L1.WINCNT + L2.LOSECNT)) * 100 AS WINRATE\r\n"
	        		+ "FROM MEMBER_TB M\r\n"
	        		+ "JOIN (\r\n"
	        		+ "    SELECT WINNER_NO AS MN, COUNT(*) AS WINCNT\r\n"
	        		+ "    FROM LOG_TB\r\n"
	        		+ "    WHERE WINNER_NO IS NOT NULL\r\n"
	        		+ "    GROUP BY WINNER_NO\r\n"
	        		+ ") L1 ON L1.MN = M.MEMBER_NO\r\n"
	        		+ "JOIN (\r\n"
	        		+ "    SELECT LOSER_NO AS MN, COUNT(*) AS LOSECNT\r\n"
	        		+ "    FROM LOG_TB\r\n"
	        		+ "    WHERE LOSER_NO IS NOT NULL\r\n"
	        		+ "    GROUP BY LOSER_NO\r\n"
	        		+ ") L2 ON L2.MN = M.MEMBER_NO\r\n"
	        		+ "WHERE 0=0 "
	        		+ "ORDER BY WINRATE DESC"; // 승률을 내림차순으로 정렬

	        pstmt = conn.prepareStatement(query);	        
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            String memberId = rs.getString("MEMBER_ID");
	            int winCnt = rs.getInt("WINCNT");
	            int loseCnt = rs.getInt("LOSECNT");
	            double winRate = rs.getDouble("WINRATE");

	            Map<String, Object> resultMap = new HashMap<>();
	            resultMap.put("MEMBER_ID", memberId);
	            resultMap.put("WINCNT", winCnt);
	            resultMap.put("LOSECNT", loseCnt);
	            resultMap.put("WINRATE", winRate);
	            result.add(resultMap);
	        }
	    } catch (SQLException e) {
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
	
}
