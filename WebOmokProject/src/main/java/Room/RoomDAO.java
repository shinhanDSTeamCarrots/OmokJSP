package Room;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class RoomDAO {
	private PreparedStatement pstmt;
	private Connection con;
	private DataSource dataFactory;
	private Statement stmt;
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
   	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String user = "CARROT";
    
	
	
	
	private void connDB() {
		try {
			Class.forName(driver);
			System.out.println("Oracle 드라이버 로딩 성공");
			con = DriverManager.getConnection(url);
            System.out.println("Connection 생성 성공");
            stmt = con.createStatement();
			
			}
		catch(Exception e){
			e.printStackTrace();
				}
	}
	public List<RoomVO> listRoom(){
		List <RoomVO> roomList = new ArrayList<RoomVO>();
		try {
			connDB();
			String query = "SELECT * FROM ROOM_TB";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				int ROOM_ID = rs.getInt("ROOM_ID");
				int OWNER_ID = rs.getInt("OWNER_ID");
				int JOINED_NO = rs.getInt("JOINED_NO");
				String ROOM_NM = rs.getString("ROOM_NM");
				Date CREATED_DATE= rs.getDate("CREATED_DATE");
				String ROOM_PW = rs.getString("ROOM_PW");
				RoomVO vo = new RoomVO();
	            vo.setRoom_id(ROOM_ID);
	            vo.setOwner_id(OWNER_ID);
	            vo.setJoined_no(JOINED_NO);
	            vo.setRoom_nm(ROOM_NM);
	            vo.setCreated_date(CREATED_DATE);
	            vo.setRoom_pw(ROOM_PW);
	            roomList.add(vo);
	            			}
	            rs.close();
	            stmt.close();
	            con.close();	            
	        				}
		catch (Exception e) {
	            e.printStackTrace();
	        				}
				return roomList;
			}
		public void addRoom(RoomVO r) {
			try {
				con = dataFactory.getConnection();
				//int ROOM_ID = r.getRoom_id();
				int OWNER_ID = r.getOwner_id();
				//int JOINED_NO = r.getJoined_no();
				String ROOM_NM = r.getRoom_nm();
				String ROOM_PW = r.getRoom_pw();
				String query = "INSERT INTO ROOM_TB(ROOM_ID, OWNER_ID, ROOM_NM, ROOM_PW"
						+ "VALUES(ROOM_SEQ.NEXTVAL, ?, ?, ?)";
				System.out.println(query);
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, OWNER_ID);
				
				pstmt.setString(2, ROOM_NM);
				pstmt.setString(3, ROOM_PW);
				pstmt.executeUpdate();
				pstmt.close();
				con.close();		
				} 
			catch(Exception e) {
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
				} 
			catch(Exception e) {
				e.printStackTrace();
					}
		}
		public void playerJoined(int ROOM_ID, int JOINED_NO) {
			try {
				con = dataFactory.getConnection();
				String query = "INSERT INTO ROOM_TB WHERE ROOM_ID = ?";
				System.out.println(query);
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, JOINED_NO);
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
				} catch(Exception e) {
					e.printStackTrace();
				}
		}
			
			
			
			
	}