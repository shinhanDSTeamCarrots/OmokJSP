package Game;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class gamelogic
 */
@WebServlet("/gamelogic")
public class gamelogic extends HttpServlet {
	// 나중에 ajax 양방향 통신을 위해 json.simpple-1.1.1.jar 라이브러리 가져올 것
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 int count = 0;
		 int x = Integer.parseInt(request.getParameter("x_input"));  // JSP로 부터 x값을 가져옴. JSP에 <input type="text" name="id"> 있었다면
	     int y = Integer.parseInt(request.getParameter("y_input"));	 // 서블릿에서 request.getParameter("y_input") 와 같은 방식으로, 클라이언트가 입력한 ID가 뭐였는지 알 수 있다
	     String black = request.getParameter("black_input");
	     String white = request.getParameter("white_input");
	     
		 HttpSession session = request.getSession();		// 서버에 데이터 저장하기 위해 세션 객체 생성
		 
		 Board board = (Board) session.getAttribute("board");  
	        if (board == null) {
	            board = new Board();
	            session.setAttribute("board", board);  // 서블릿은 데이터를 JSP에 보내줘야함. 그런데 어떻게??
	            									   // session.setAttribute("board", board);로 속성을 집어넣고
	            									   // JSP에서 <% String strStone = (String)request.getAttribute("board"); %>로 속성을 얻는다.
	        }
	        
	        boolean nowPlayer = board.who(black, white);
	        
	        char currentPlayer = board.getCurrentPlayer(nowPlayer);
		    
		    boolean success = board.put(x, y, currentPlayer);
		        
		        if(success) {
		        	response.setContentType("application/json");
		        	response.setCharacterEncoding("UTF-8");
		        }
		        
		    // put을 했으면 오복판 업데이트 하기
		     
	        // 플레이어가 '흑' 인지 '백' 인지
	        
	        // 오목인지 아닌지
	        
			// 육목인지 아닌지
	        
	        // 쌍삼인지 아닌지
	        
	        
	
	}

}
