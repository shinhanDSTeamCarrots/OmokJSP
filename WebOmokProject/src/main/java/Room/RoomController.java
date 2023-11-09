package Room;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/roomController/*")
public class RoomController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    RoomDAO roomDAO;   
   
    public RoomController() {
        super();
        // TODO Auto-generated constructor stub
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
		//addRoom : 방 정보 더해줌, nextPage = listRoom
		if("/addRoom.do".equals(action)) {
			System.out.println("==========room add=============");
			RoomVO roomVO = new RoomVO();
			roomVO.setOwner_id(Integer.parseInt(request.getParameter("OWNER_ID")));
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
			Boolean res= false;
			try {
				roomDAO.addRoom(roomVO);
				res= true;
			}
			catch(Exception e) {
				res=false;
			}
			writer.print(res?"T":"F");
			System.out.println("==========room add=============");
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
			int JOINED_NO = Integer.parseInt(request.getParameter("JOINED_NO"));
			System.out.println("==========playerJoined=============");
			System.out.println("==========playerJoined=============");
			
		}
		else if("/playerExited.do".equals(action)){
			int Exited_NO = Integer.parseInt(request.getParameter("JOINED_NO"));
			System.out.println("==========playerExited=============");	
			System.out.println("==========playerExited=============");	
		}

	}
}
