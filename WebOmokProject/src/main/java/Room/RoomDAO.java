package omok;

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
			String query = "select * from room_tb";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				String room_id = rs.getString("room_id");
				String owner_id = rs.getString("owner_id");
				String joinplayer_id = rs.getString("joinplayer_id");
				String room_nm = rs.getString("room_nm");
				Date created_date= rs.getDate("created_date");
				String room_pw = rs.getString("room_pw");
				RoomVO vo = new RoomVO();
	            vo.setRoom_id(room_id);
	            vo.setOwner_id(owner_id);
	            vo.setJoinplayer_id(joinplayer_id);
	            vo.setRoom_nm(room_nm);
	            vo.setCreated_date(created_date);
	            vo.setRoom_pw(room_pw);
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
				String room_id = r.getRoom_id();
				String owner_id = r.getOwner_id();
				String joinplayer_id = r.getJoinplayer_id();
				String room_pw = r.getRoom_pw();
				String query = "INSERT INTO ROOM_TB(room_id, owner_id, joinplayer_id, room_pw"
						+ "VALUES(?, ?, ?, ?)";
				System.out.println(query);
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, room_id);
				pstmt.setString(2, owner_id);
				pstmt.setString(3, joinplayer_id);
				pstmt.setString(4, room_pw);
				pstmt.executeUpdate();
				pstmt.close();
				con.close();		
				} 
			catch(Exception e) {
				e.printStackTrace();
								}
										}
		public void delRoom(String room_id) {
			try {
				con = dataFactory.getConnection();
				String query = "delete from room_tb where id = ?";
				System.out.println(query);
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, room_id);
				pstmt.executeUpdate();
				} 
			catch(Exception e) {
				e.printStackTrace();
								}
			
		}
				
 }	

	