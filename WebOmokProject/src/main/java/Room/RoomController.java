package Room;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;



@WebServlet("/roomController/*")
public class RoomController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    RoomDAO roomDAO;   
   
    protected void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if(roomDAO == null)
    		roomDAO = new RoomDAO();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String action = request.getPathInfo();
		System.out.println("action: "+action);
		PrintWriter writer = response.getWriter();
		System.out.println("==========doHandle in ROOMController Called=============");
		if(roomDAO == null)
			roomDAO = new RoomDAO();
		//addRoom : 방 만들기, nextPage = listRoom
		if("/addRoom.do".equals(action)) {
			System.out.println("==========room add=============");
			RoomVO roomVO = new RoomVO();
			//roomVO.setOwner_id(Integer.parseInt(request.getParameter("OWNER_NO")));
			int owner_id = (int)request.getSession().getAttribute("memberno");
			roomVO.setOwner_id(owner_id);
			String sr = request.getParameter("ROOM_NM");
			if(sr.isEmpty() || sr.equals("")) {
				roomVO.setRoom_nm("너만 오면 ㄱ");
			}else {
				roomVO.setRoom_nm(sr);
			}
			String pw = request.getParameter("ROOM_PW");
			if(pw.isEmpty() || pw.equals("")) {
				roomVO.setRoom_pw(null);
			}else {
				roomVO.setRoom_pw(pw);
			}
			//1. 이미 다른 방에 들어갔는지 확인
			boolean temp = false;
			try {
				temp = roomDAO.RoomCheck(owner_id);
			}
			catch(Exception e) {
				temp = false;
			}
			if(!temp) {
				writer.print("A");
			}
			else {
				try {
					roomDAO.addRoom(roomVO);
					//룸 넘버를 받아와서 저장
					int roomno = roomDAO.getRoomIdWhatIOwned(owner_id);
					HttpSession session = request.getSession();
					session.setAttribute("roomid", roomno);
					writer.print("T");
				}
				catch(Exception e) {
					writer.print("F");
				}
			}
			System.out.println("==========room add=============");
			return;
		}
		//delRoom: 방 삭제 이후 방 생성 화면으로 이동 (nextPage = listRoom) 
		else if("/delRoom.do".equals(action)) {
			System.out.println("==========room deleted=============");
			int OWNER_ID = Integer.parseInt(request.getParameter("OWNER_ID"));
			roomDAO.delRoom(OWNER_ID);
			request.setAttribute("msg", "deleted");
			System.out.println("==========room deleted=============");
		}
		//랭킹 리스트 출력하는 함수
		else if("/getRanking.do".equals(action)) {
			System.out.println("==========rank list=============");
			System.out.println("==========rank list=============");
			
		}
		else if("/playerJoined.do".equals(action)) {
			System.out.println("==========playerJoined=============");
			int JOINED_NO = Integer.parseInt(request.getParameter("JOINED_NO"));
			int ROOM_NO = Integer.parseInt(request.getParameter("ROOM_NO"));
			if(!roomDAO.isPossibleToJoin(ROOM_NO)) {
				writer.print("F");
				return;
			}
			roomDAO.playerJoined(ROOM_NO,JOINED_NO);
			HttpSession session = request.getSession();
			session.setAttribute("roomid", ROOM_NO);
			writer.print("T");
			
			System.out.println("==========playerJoined=============");
			return;
			
		}
		else if("/playerExited.do".equals(action)){
			int Exited_NO = Integer.parseInt(request.getParameter("JOINED_NO"));
			System.out.println("==========playerExited=============");
			request.getSession().removeAttribute("roomid");
			System.out.println("==========playerExited=============");	
		}
		else if("/roomList.do".equals(action)) {
			System.out.println("==========roomList=============");
			List<RoomVO> list =roomDAO.listRoom();
			JSONArray jsonArr = new JSONArray();
			for(RoomVO romm : list) {
				JSONObject obj = new JSONObject();
				obj.put("room_no", romm.getRoom_id());
				obj.put("owner_id", romm.getOwner_id());
				obj.put("joined_no", romm.getJoined_no());
				obj.put("room_nm", romm.getRoom_nm());
				obj.put("room_pw", romm.getRoom_pw());
				obj.put("created_date", romm.getDateString());
				obj.put("option_val", romm.getOption_val());
				obj.put("owner_nm", romm.getOwner_nm());
				obj.put("joined_nm", romm.getJoined_nm());
				jsonArr.put(obj);
			}
			System.out.println(jsonArr.toString());
			writer.print(jsonArr.toString());
			
			
			System.out.println("==========roomList=============");
			return;
			
		}

	}
	
}
