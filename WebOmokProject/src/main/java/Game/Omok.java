package Game;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;


@WebServlet("/omok")
public class Omok extends HttpServlet {
	// 나중에 ajax 양방향 통신을 위해 json.simpple-1.1.1.jar 라이브러리 가져올 것
	
	String dbURL = "jdbc:oracle:thin:@hostname:port:sid"; // 호스트명, 포트, SID에 해당하는 부분은 나중에 변경해야 함
	String dbUser = "username"; // DB 사용자 이름
	String dbPass = "password"; // DB 비밀번호
	
	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 결과를 파라미터에서 가져옴 (예: result= 승 or result= 패배)
	    String result = request.getParameter("result");

	    // 세션에서 사용자 정보를 가져옴
	    HttpSession session = request.getSession();
	    String username = (String) session.getAttribute("username"); 
	    
	    // DAO를 사용하여 결과를 DB에 저장
	    
	    // Dao dao = new Dao(dbURL, dbUser, dbPass);
	    boolean updateSuccess = dao.updateGameResult(username, result);
	    
	    // JSON 객체로 결과 생성
	    JSONObject jsonResponse = new JSONObject();
	    if (updateSuccess) {
	        jsonResponse.put("message", "결과가 성공적으로 업데이트 되었습니다.");
	    } else {
	        jsonResponse.put("message", "결과 업데이트에 실패하였습니다.");
	    }
	    
	    // JSON 응답 전송
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter out = response.getWriter();
	    out.print(jsonResponse.toString());
	    out.flush();
	}

	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 GameLogic gamelogic = new GameLogic();
		 
		 // JSP로 부터 x값을 가져옴. JSP에 <input type="text" name="id"> 있었다면
		 // 서블릿에서 request.getParameter("y_input") 와 같은 방식으로, 클라이언트가 입력한 ID가 뭐였는지 알 수 있다
		 int x = Integer.parseInt(request.getParameter("x_input")); 
		 int y = Integer.parseInt(request.getParameter("y_input")); 
		 
		 // x,y와 똑같은 방식으로 가져옴
	     String black = request.getParameter("black_player");
	     String white = request.getParameter("white_player");
	     
	     // 여러 요청에 걸쳐 게임 상태를 서버에 저장하고 검색하기 위해 HttpSession을 사용 (상태유지 행위).
		 HttpSession session = request.getSession();	
		 
		 // 게임 상태를 유지하기 위해 Board 객체가 세션에 저장됨
		 Board board = (Board) session.getAttribute("board");  
	        if (board == null) {
	            board = new Board();
	            session.setAttribute("board", board);  // 서블릿은 데이터를 JSP에 보내줘야함. 그런데 어떻게??
	            									   // session.setAttribute("board", board);로 속성을 집어넣고
	            									   // JSP에서 <% String strStone = (String)request.getAttribute("board"); %>로 속성을 얻는다.
	        }
	        
	        // 현재 플레이어
	        String currentPlayer = board.isBlackTurn(black, white);
	        
	        // 검은 플레이어면 이면 "B", 흰 플레이어면 "W"를 사용한다.
	        String stone = currentPlayer.equals("black_player") ? "B" : "W"; 
	        
	        //  이 메소드는 일반적으로 현재 플레이어가 (x, y) 위치에 돌을 놓으려고 시도
		    boolean success = board.play(x, y, stone); 
		    
		    // 현재 오목판 출력
		    // board.print();
		    
		    boolean Omok = false;    // 오목 여부
		    boolean Six = false;     // 육목 여부
		    boolean DoubleThree = false; // 쌍삼 여부
		    String message = "";

		    // 돌을 놓은 후의 상태 체크
		    if (success) {
		    	Omok = gamelogic.checkOmok(x, y, currentPlayer, board.getMap());
		    	Six = gamelogic.checkSix(x, y, currentPlayer, board.getMap());
		    	DoubleThree = gamelogic.checkDoubleThree(x, y, currentPlayer, board.getMap());
		        
		        if (Omok) {
		            message = currentPlayer + " 승리!";
		        } else if (Six) {
		            message = "육목은 허용되지 않습니다!";
		            
		            // 육목이면 성공을 false로 변경
		            success = false; 
		        } else if (DoubleThree) {
		            message = "쌍삼은 허용되지 않습니다!";
		            
		            // 쌍삼이면 성공을 false로 변경
		            success = false; 
		        } else {
		        	// 이것도 저것도 아니면 다음 메세지 출력
		            message = "돌 놓기 성공.";  
		        }
		    } else {
		    	// success가 실패이면 다음 메세지 출력
		        message = "돌 놓기 실패.";  
		    }
		    
		    // JSON 객체 생성 및 세팅
		    JSONObject jsonResponse = new JSONObject();
		    
		    // boolean을 문자열로 변환하여 넣음
		    jsonResponse.put("success", String.valueOf(success));
		    jsonResponse.put("message", message);
		    if (success) {
		        jsonResponse.put("currentPlayer", currentPlayer);
		    }
	        
	        // 응답 타입을 JSON으로 설정
	        response.setContentType("application/json");
	        
	        //응답 문자열의 인코딩을 UTF-8로 설정함
	        response.setCharacterEncoding("UTF-8");

	        // PrintWriter는 JSON 응답 전송하기 위함
	        PrintWriter out = response.getWriter();
	        out.print(jsonResponse.toString());
	        
	        // 버퍼에 있는 모든 출력 데이터를 클라이언트로 즉시 전송하고 버퍼를 비움
	        out.flush();

	}

}
