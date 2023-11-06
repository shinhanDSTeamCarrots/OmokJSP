package Rank;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
		
	}
	
	//승률계산
	public void winRate(LogVO countVo) {
		
	}
}
