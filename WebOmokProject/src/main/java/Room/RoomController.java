package Room;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/RoomController/*")
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
		String nextPage = null;
		String action = request.getPathInfo();
		System.out.println("action: "+action);
		//addRoom : 방 정보 더해줌, nextPage = listRoom
		if("/addRoom.do".equals(action)) {			
			RoomVO roomVO = new RoomVO();
			roomVO.setOwner_id(Integer.parseInt(request.getParameter("OWNER_ID")));
			roomVO.setRoom_nm(request.getParameter("ROOM_NM"));
			roomVO.setRoom_pw(request.getParameter("ROOM_PW"));
			roomDAO.addRoom(roomVO);
			request.setAttribute("msg", "added");
			nextPage = "/WebOmokProject/room/listRoom.jsp";
		}
		//delRoom: 방 삭제 이후 방 생성 화면으로 이동 (nextPage = listRoom) 
		else if("/delRoom.do".equals(action)) {
			int OWNER_ID = Integer.parseInt(request.getParameter("OWNER_ID"));
			roomDAO.delRoom(OWNER_ID);
			request.setAttribute("msg", "deleted");
			nextPage = "/WebOmokProject/room/listRoom.jsp";
		}
		else if("/playerJoined.do".equals(action)) {
			int JOINED_NO = (Integer.parseInt(request.getParameter("JOINED_NO")));
			
		}
	}

}
