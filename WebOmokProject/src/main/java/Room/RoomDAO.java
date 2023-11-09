package Room;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class RoomDAO {
	private PreparedStatement pstmt;
	private Connection con;
	private DataSource dataFactory;
	private Statement stmt;

	public RoomDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<RoomVO> listRoom() {
		List<RoomVO> roomList = new ArrayList<RoomVO>();
		try {
			con = dataFactory.getConnection();
			String query = "SELECT R.ROOM_ID, R.OWNER_NO as OWNER_ID, R.JOINED_NO, R.ROOM_NM, R.CREATED_DATE, R.ROOM_PW, NVL(P1.MEMBER_NICKNM, P1.MEMBER_NM) AS OWNER_NM, NVL(P2.MEMBER_NICKNM, P2.MEMBER_NM) AS JOINED_NM, R.OPTION_VAL\r\n"
					+ "FROM ROOM_TB R LEFT JOIN MEMBER_TB P1 ON P1.MEMBER_NO = R.OWNER_NO LEFT JOIN MEMBER_TB P2 ON P2.MEMBER_NO = R.JOINED_NO";
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int ROOM_ID = rs.getInt("ROOM_ID");
				int OWNER_ID = rs.getInt("OWNER_ID");
				int JOINED_NO = rs.getInt("JOINED_NO");
				String ROOM_NM = rs.getString("ROOM_NM");
				Date CREATED_DATE = rs.getDate("CREATED_DATE");
				String ROOM_PW = rs.getString("ROOM_PW");
				String OWNER_NM = rs.getString("OWNER_NM");
				String JOINED_NM = rs.getString("JOINED_NM");
				int OPTION = rs.getInt("OPTION_VAL");
				RoomVO vo = new RoomVO();
				vo.setRoom_id(ROOM_ID);
				vo.setOwner_id(OWNER_ID);
				vo.setJoined_no(JOINED_NO);
				vo.setRoom_nm(ROOM_NM);
				vo.setCreated_date(CREATED_DATE);
				vo.setRoom_pw(ROOM_PW);
				vo.setOption_val(OPTION);
				vo.setOwner_nm(OWNER_NM);
				vo.setJoined_nm(JOINED_NM);
				roomList.add(vo);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomList;
	}
	
	public boolean isPossibleToJoin(int roomid) {
		boolean res = false;
		try {
			con = dataFactory.getConnection();
			String query = "SELECT NVL(JOINED_NO,-1) AS VAL FROM ROOM_TB WHERE ROOM_ID = ?";
			System.out.println(query);
			pstmt =con.prepareStatement(query);
			pstmt.setInt(1, roomid);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int JOINED_NO = rs.getInt("VAL");
			if(JOINED_NO == -1)
				res = true;
			rs.close();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	public boolean RoomCheck(int player_no) {
		boolean result = false;
		try {
			con = dataFactory.getConnection();
			String query = "SELECT COUNT(*) AS CNT FROM ROOM_TB\r\n"
						 + " WHERE (OWNER_NO = ? OR JOINED_NO = ?) ";
			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, player_no);
			pstmt.setInt(2, player_no);
			ResultSet rs = pstmt.executeQuery();
			rs.next(); // 결과 행이 하나 이상 있는 경우, 첫 번째 행으로 이동

			if (rs.getInt("cnt") > 0) { // rs의 열 값이 0보다 크다면
				result = false;
			} else {
				result = true;
			}
			pstmt.close();
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	public void addRoom(RoomVO r) {
		try {
			con = dataFactory.getConnection();
			int OWNER_ID = r.getOwner_id();
			String ROOM_NM = r.getRoom_nm();
			String ROOM_PW = r.getRoom_pw();
			String query = "INSERT INTO ROOM_TB(ROOM_ID, OWNER_NO, ROOM_NM, ROOM_PW) "
					+ "VALUES(ROOM_SEQ.NEXTVAL, ?, ?, ?) ";
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, OWNER_ID);
			pstmt.setString(2, ROOM_NM);
			pstmt.setString(3, ROOM_PW);
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delRoom(int ROOM_ID) {
		try {
			con = dataFactory.getConnection();
			String query = "DELETE FROM ROOM_TB WHERE ID = ?";
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, ROOM_ID);

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playerJoined(int ROOM_ID, int JOINED_NO) {
		try {
			con = dataFactory.getConnection();
			String query = "UPDATE ROOM_TB SET JOINED_NO = ? WHERE ROOM_ID = ?";
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, JOINED_NO);
			pstmt.setInt(2, ROOM_ID);
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playerExit(int ROOM_ID, int JOINED_NO) {
		try {
			con = dataFactory.getConnection();
			String query = "DELETE FROM ROOM_TB WHERE ROOM_ID = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, JOINED_NO);
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
